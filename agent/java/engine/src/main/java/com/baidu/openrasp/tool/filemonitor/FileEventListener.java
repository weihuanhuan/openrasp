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


import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * Created by tyy on 6/7/17.
 * 可用于监听某一个文件夹事件的监听器
 * 使用系统事件作为驱动，实时性高
 * 将事件传递给观察者，由观察者扫描该文件夹来进一步确定事件事件具体类型
 */
public class FileEventListener implements JDKFileListener {

    private FileAlterationObserver observer;

    /**
     * constructor
     *
     * @param observer 关注某一个文件夹事件的观察者
     */
    public FileEventListener(FileAlterationObserver observer) {
        this.observer = observer;
    }

    /**
     * 文件重命名事件回调接口
     */
    @Override
    public void fileRenamed() {
        observer.checkAndNotify();
    }

    /**
     * 文件修改事件回调接口
     */
    @Override
    public void fileModified() {
        observer.checkAndNotify();
    }

    /**
     * 文件文件删除事件回调接口
     */
    @Override
    public void fileDeleted() {
        observer.checkAndNotify();
    }

    /**
     * 文件创建事件回调接口
     */
    @Override
    public void fileCreated() {
        observer.checkAndNotify();
    }

}
