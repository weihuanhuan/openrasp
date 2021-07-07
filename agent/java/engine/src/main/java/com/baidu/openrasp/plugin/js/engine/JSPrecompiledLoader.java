package com.baidu.openrasp.plugin.js.engine;

import com.baidu.openrasp.EngineBoot;
import com.baidu.openrasp.cloud.utils.CloudUtils;
import com.baidu.openrasp.messaging.ErrorType;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSPrecompiledLoader {

    private static final Logger LOGGER = Logger.getLogger(JSPrecompiledLoader.class.getPackage().getName() + ".log");
    private static final String ENVIRONMENT_ENVIRONMENT_TXT = "environment/environment.txt";
    private static final String ENVIRONMENT_FILE_CHARSET = "UTF-8";

    private final JSContext cx;
    private final ScriptableObject globalScope;

    public JSPrecompiledLoader(JSContext cx, ScriptableObject globalScope) {
        this.cx = cx;
        this.globalScope = globalScope;
    }

    public void loadPrecompiled() {
        try {
            doLoad();
        } catch (IOException e) {
            String message = String.format("failed to load precompiled js class!");
            LOGGER.warn(CloudUtils.getExceptionObject(ErrorType.PLUGIN_ERROR, message), e);
        }
    }

    private void doLoad() throws IOException {
        ClassLoader classLoader = EngineBoot.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(ENVIRONMENT_ENVIRONMENT_TXT);
        InputStreamReader streamReader = new InputStreamReader(is, ENVIRONMENT_FILE_CHARSET);

        String line;
        BufferedReader stringReader = new BufferedReader(streamReader);
        while ((line = stringReader.readLine()) != null) {
            String className = line.trim();
            if (className.isEmpty() || className.startsWith("#")) {
                continue;
            }

            execPrecompiled(classLoader, className);
        }
    }

    private void execPrecompiled(ClassLoader classLoader, String className) {
        Object instance;
        try {
            Class<?> precompiledClass = classLoader.loadClass(className);
            instance = precompiledClass.newInstance();
        } catch (Exception e) {
            String message = String.format("cannot initialize precompiled js class %s!", className);
            LOGGER.warn(CloudUtils.getExceptionObject(ErrorType.PLUGIN_ERROR, message), e);
            return;
        }

        if (!(instance instanceof Script)) {
            String message = String.format("cannot execute precompiled js class %s!", className);
            LOGGER.warn(CloudUtils.getExceptionObject(ErrorType.PLUGIN_ERROR, message));
            return;
        }

        Script.class.cast(instance).exec(cx, globalScope);
    }
}
