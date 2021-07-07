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

package com.baidu.openrasp.tool.filemonitor;

import com.baidu.openrasp.config.Config;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyy on 4/17/17.
 * 文件事件的监视器，监视单位为文件夹
 */
public class FileScanMonitor {

    static {
        if (!Config.getConfig().getCloudSwitch()) {
            JDKNotify.init(Config.baseDirectory);
        }
    }

    /**
     * 增加监视器
     *
     * @param path     监听的文件夹路径
     * @param listener 事件回调接口
     * @return 监听器的id
     * @throws JDKNotifyException {@link JDKNotifyException}
     */
    public static int addMonitor(String path, FileAlterationListener listener) throws JDKNotifyException {
        if (!Config.getConfig().getCloudSwitch()) {
            File file = new File(path);
            FileAlterationObserver observer = new FileAlterationObserver(file);
            observer.checkAndNotify();
            observer.addListener(listener);

            List<WatchEvent.Kind<Path>> kindList = new ArrayList<>();
            kindList.add(StandardWatchEventKinds.ENTRY_CREATE);
            kindList.add(StandardWatchEventKinds.ENTRY_DELETE);
            kindList.add(StandardWatchEventKinds.ENTRY_MODIFY);
            int watch = JDKNotify.addWatch(path, kindList, false, new FileEventListener(observer));
            return watch;
        }
        return 0;
    }

    /**
     * 移除某一个文件夹监听
     *
     * @param watchId 增加文件夹监听的时候返回的监听器id
     */
    public static void removeMonitor(int watchId) {
        if (!Config.getConfig().getCloudSwitch()) {
            try {
                JDKNotify.removeWatch(watchId);
            } catch (JDKNotifyException e) {
                e.printStackTrace();
            }
        }
    }


}
