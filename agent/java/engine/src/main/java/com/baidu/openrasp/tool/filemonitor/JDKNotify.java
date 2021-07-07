package com.baidu.openrasp.tool.filemonitor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDKNotify {

    public static File baseFile;

    public static int watchId = 0;
    public static Map<Integer, JDKFileMonitor> watches = new HashMap<>();

    public static void init(String directory) {
        baseFile = new File(directory);
    }

    public static synchronized int addWatch(String path, List<WatchEvent.Kind<Path>> kinds, boolean recursion, JDKFileListener fileEventListener) throws JDKNotifyException {
        File file = new File(path);
        if (!file.isAbsolute()) {
            file = new File(baseFile, path);
        }

        JDKFileMonitor watcher = new JDKFileMonitor(file, kinds, recursion);
        watcher.addListener(fileEventListener);

        int nextWatchId = getNextWatchId();
        watcher.start(nextWatchId);

        watches.put(nextWatchId, watcher);
        return nextWatchId;
    }

    public static synchronized void removeWatch(int watchId) throws JDKNotifyException {
        JDKFileMonitor watcher = watches.remove(watchId);
        if (watcher != null) {
            watcher.stop();
        }
    }

    private static int getNextWatchId() {
        return ++watchId;
    }
}
