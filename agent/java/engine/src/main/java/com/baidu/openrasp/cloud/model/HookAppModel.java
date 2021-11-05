package com.baidu.openrasp.cloud.model;

import com.baidu.openrasp.detector.helper.ServerHelper;
import com.baidu.openrasp.messaging.ErrorType;
import com.baidu.openrasp.messaging.LogTool;
import com.baidu.openrasp.plugin.checker.CheckLevel;
import com.baidu.openrasp.request.AbstractRequest;
import com.baidu.openrasp.tool.model.ApplicationModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class HookAppModel {

    private static final String SERVER_ANY_NAME = "any";
    private static final String APP_ANY_NAME = "any";
    private static final String APP_NAME_SEPARATOR = ",";

    private static Set<String> hookWhiteApp;
    private static boolean allowAnyApp;

    public static void init() {
        Properties properties = readWhiteAppProperties();
        if (properties.isEmpty()) {
            return;
        }

        //TODO server name cannot get when reap init engine, only any is valid
        //TODO app name can repeat between difference virtual hosts
        String serverName = ApplicationModel.getServerName();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            //for server name
            if (key == null || key.isEmpty()) {
                continue;
            }

            //indicate current server or any server
            if (!key.equals(serverName) && !key.equals(SERVER_ANY_NAME)) {
                continue;
            }

            //for app name list split with comma
            String value = (String) entry.getValue();
            if (value == null || value.isEmpty()) {
                continue;
            }

            hookWhiteApp = new HashSet<>();
            for (String appName : value.split(APP_NAME_SEPARATOR)) {
                if (appName == null || appName.isEmpty()) {
                    continue;
                }
                hookWhiteApp.add(appName);
            }
            allowAnyApp = hookWhiteApp.contains(APP_ANY_NAME);
        }
    }

    public static boolean isContainApp(AbstractRequest abstractRequest) {
        if (hookWhiteApp == null) {
            return false;
        }
        if (allowAnyApp) {
            return true;
        }
        return isContainAppName(abstractRequest);
    }

    private static boolean isContainAppName(AbstractRequest abstractRequest) {
        if (abstractRequest == null) {
            return false;
        }

        ServerHelper helper = ApplicationModel.getHelper();
        if (helper == null) {
            return false;
        }

        String appName = helper.getAppName(abstractRequest);
        if (appName == null || appName.isEmpty()) {
            return false;
        }
        return hookWhiteApp.contains(appName);
    }

    private static Properties readWhiteAppProperties() {
        Properties properties = new Properties();

        ClassLoader classLoader = CheckLevel.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("app.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            LogTool.error(ErrorType.RUNTIME_ERROR, "failed to read white app file", e);
        }
        return properties;
    }
}
