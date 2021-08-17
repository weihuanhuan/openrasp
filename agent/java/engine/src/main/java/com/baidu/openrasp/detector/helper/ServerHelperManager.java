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

package com.baidu.openrasp.detector.helper;

import com.baidu.openrasp.detector.BESDetector;
import com.baidu.openrasp.detector.ServerDetector;
import com.baidu.openrasp.detector.TomcatDetector;
import com.baidu.openrasp.tool.model.ApplicationModel;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ServerHelperManager {

    private static final Logger LOGGER = Logger.getLogger(ServerHelperManager.class.getName());

    private static final ServerHelper DUMMY_HELPER = new DummyHelper();

    private static final Map<Class<? extends ServerDetector>, Class<? extends ServerHelper>> helper = new HashMap<>();

    static {
        helper.put(TomcatDetector.class, TomcatHelper.class);
        helper.put(BESDetector.class, BESHelper.class);
    }

    public static void handleServerHelper(Class<?> clazz) {
        try {
            Class<? extends ServerHelper> serverHelperClazz = helper.get(clazz);
            ServerHelper serverHelper = serverHelperClazz.newInstance();
            ApplicationModel.setHelper(serverHelper);
            LOGGER.info("setting server helper class: " + serverHelperClazz);
        } catch (Throwable e) {
            ApplicationModel.setHelper(DUMMY_HELPER);
            e.printStackTrace();
            LOGGER.warn("setting server helper class for class: " + clazz + " failed", e);
        }
    }

    public static ServerHelper getDummyHelper() {
        return DUMMY_HELPER;
    }
}
