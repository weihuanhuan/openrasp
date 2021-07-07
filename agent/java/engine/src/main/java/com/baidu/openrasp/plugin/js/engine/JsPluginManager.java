/*
 * Copyright 2017-2019 Baidu Inc.
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

package com.baidu.openrasp.plugin.js.engine;

/**
 * Created by tyy on 4/5/17.
 * All rights reserved
 */

/**
 * PluginManager是一个静态类，封装了插件系统的细节，仅对外层暴露init和check方法
 * <p>
 * PluginManager内部管理插件系统实例，监控检测脚本文件变化
 * <p>
 * 必须首先初始化
 */
public class JsPluginManager {

    /**
     * 初始化插件引擎
     *
     * @throws Exception
     */
    public synchronized static void init() throws Exception {
        JSContextFactory.init();
    }

    public synchronized static void release() {
        JSContextFactory.release();
    }

}
