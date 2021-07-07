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

import com.baidu.openrasp.config.Config;
import com.baidu.openrasp.config.ConfigItem;
import com.baidu.openrasp.messaging.ErrorType;
import com.baidu.openrasp.messaging.LogTool;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * 修改rasp相关配置
 */
public class JSRASPConfig extends BaseFunction {
    /**
     * @param cx
     * @param scope
     * @param thisObj
     * @param args
     * @return
     * @see BaseFunction#call(Context, Scriptable, Scriptable, Object[])
     */
    @Override
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args) {
        if (args.length < 2) {
            return Context.getUndefinedValue();
        }

        Object javaObjectOne = Context.jsToJava(args[0], String.class);
        Object javaObjectTwo = Context.jsToJava(args[1], String.class);
        if (!(javaObjectOne instanceof String) || !(javaObjectTwo instanceof String)) {
            return Context.getUndefinedValue();
        }

        String configKey = (String) javaObjectOne;
        String configValue = (String) javaObjectTwo;
        ConfigItem configItem = getConfigItem(configKey);
        try {
            return Config.getConfig().setConfig(configItem, configValue, false);
        } catch (Exception e) {
            String message = "Configuration item " + configKey + " has invalid value " + configValue;
            LogTool.warn(ErrorType.CONFIG_ERROR, message + ", reason: " + e.getMessage(), e);
            return false;
        }
    }

    // config项赋值前检测key是否是rasp规定的字段
    public ConfigItem getConfigItem(String key) {
        if (!StringUtils.isEmpty(key)) {
            for (ConfigItem item : ConfigItem.values()) {
                if (item.toString().equals(key)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * 提供获取该对象默认值的方法
     * console.log(thisObj) 即会输出此方法返回的值
     *
     * @param hint
     * @return
     * @see Scriptable#getDefaultValue(Class)
     */
    @Override
    public Object getDefaultValue(Class<?> hint) {
        return "[Function: set_rasp_config]";
    }
}
