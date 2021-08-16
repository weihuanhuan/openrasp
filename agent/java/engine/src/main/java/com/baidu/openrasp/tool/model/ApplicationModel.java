/*
 * Copyright 2017-2020 Baidu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baidu.openrasp.tool.model;

import com.baidu.openrasp.HookHandler;
import com.baidu.openrasp.detector.helper.ServerHelper;
import com.baidu.openrasp.messaging.ErrorType;
import com.baidu.openrasp.messaging.LogTool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tyy on 18-8-13.
 *
 * 缓存服务器进程基本信息
 */
public class ApplicationModel {

    private static Map<String, String> applicationInfo;

    private static Map<String, String> systemEnvInfo;

    private static final String SERVER_TYPE_PATH = "/proc/self/cgroup";

    private static ServerHelper helper;

    static {
        systemEnvInfo = System.getenv();
        if (systemEnvInfo == null) {
            systemEnvInfo = new HashMap<String, String>();
        }
        applicationInfo = new HashMap<String, String>(8);
        String serverName = System.getProperty("os.name");
        if (serverName != null && serverName.startsWith("Linux")) {
            applicationInfo.put("os", "Linux");
        } else if (serverName != null && serverName.startsWith("Windows")) {
            applicationInfo.put("os", "Windows");
        } else if (serverName != null && serverName.startsWith("Mac")) {
            applicationInfo.put("os", "Mac");
        } else {
            applicationInfo.put("os", serverName);
        }
        applicationInfo.put("language", "java");
        applicationInfo.put("server", "");
        applicationInfo.put("version", "");
        applicationInfo.put("extra", "");
        applicationInfo.put("StandardStart", "false");
    }

    public static ServerHelper getHelper() {
        return helper;
    }

    public static void setHelper(ServerHelper helper) {
        ApplicationModel.helper = helper;
    }

    public static synchronized void setServerInfo(String serverName, String version) {
        serverName = (serverName == null ? "" : serverName);
        version = (version == null ? "" : version);
        applicationInfo.put("server", serverName);
        applicationInfo.put("version", version);
        HookHandler.LOGGER.info("detect server: " + serverName + "/" + version);
    }

    public static synchronized void setExtraInfo(String extra, String extraVersion) {
        extra = (extra == null ? "" : extra);
        applicationInfo.put("extra", extra);
        extraVersion = (extraVersion == null ? "" : extraVersion);
        applicationInfo.put("extraVersion", extraVersion);
        HookHandler.LOGGER.info("detect extra server info: " + extra + "/" + extraVersion);
    }

    public static synchronized void setStartUpInfo(String startUpInfo) {
        applicationInfo.put("StandardStart", startUpInfo);
    }

    public static Map<String, String> getApplicationInfo() {
        return applicationInfo;
    }

    public static Map<String, String> getSystemEnv() {
        return systemEnvInfo;
    }

    public static String getVersion() {
        String result = applicationInfo.get("version");
        if (StringUtils.isEmpty(result)) {
            result = applicationInfo.get("extraVersion");
        }
        return result;
    }

    public static String getServerName() {
        String result = applicationInfo.get("server");
        if (StringUtils.isEmpty(result)) {
            result = applicationInfo.get("extra");
        }
        return result;
    }

    public static boolean getStartUpInfo() {
        String result = applicationInfo.get("StandardStart");
        return Boolean.parseBoolean(result);
    }

    public static String getVMType() {
        String type = null;
        if (isLinux()) {
            File file = new File(SERVER_TYPE_PATH);
            if (file.exists() && file.isFile() && file.canRead()) {
                try {
                    String content = FileUtils.readFileToString(file);
                    if (StringUtils.contains(content, "docker")) {
                        type = "docker";
                    }
                } catch (IOException e) {
                    LogTool.warn(ErrorType.DETECT_SERVER_ERROR, "get VM type failed: " + e.getMessage(), e);
                }
            }
        }
        return type;
    }

    private static boolean isLinux() {
        String serverName = System.getProperty("os.name");
        return StringUtils.startsWith(serverName, "Linux");
    }

}
