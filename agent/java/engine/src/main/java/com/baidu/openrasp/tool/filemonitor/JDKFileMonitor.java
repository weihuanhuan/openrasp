package com.baidu.openrasp.tool.filemonitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class JDKFileMonitor implements Runnable {

    private File folder;
    private List<WatchEvent.Kind<Path>> kinds;
    private boolean recursion;

    private List<JDKFileListener> listeners = new ArrayList<>();

    private Thread thread;

    public JDKFileMonitor(File folder, List<WatchEvent.Kind<Path>> kinds, boolean recursion) {
        this.folder = folder;
        this.kinds = kinds;
        this.recursion = recursion;
    }

    public void start(int watchId) throws JDKNotifyException {
        valid();

        thread = new Thread(this, "jdk-file-monitor-" + watchId);
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    private void registerDirectory(final WatchService watchService) throws IOException {
        Path path = Paths.get(folder.getAbsolutePath());
        if (!recursion) {
            registerDirectory(watchService, path);
            return;
        }

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirectory(watchService, dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void registerDirectory(WatchService watchService, Path dir) throws IOException {
        dir.register(watchService, kinds.toArray(new WatchEvent.Kind[0]));
    }

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            registerDirectory(watchService);

            boolean isKeyReset = true;
            while (!Thread.currentThread().isInterrupted() && isKeyReset) {
                isKeyReset = pollEvents(watchService);
            }
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
                return;
            }
            e.printStackTrace();
        }
    }

    private boolean pollEvents(WatchService watchService) throws InterruptedException {
        WatchKey key = watchService.take();
        for (WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(event.kind());
        }
        return key.reset();
    }

    private void notifyListeners(WatchEvent.Kind<?> kind) {
        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
            for (JDKFileListener listener : listeners) {
                listener.fileCreated();
            }
        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
            for (JDKFileListener listener : listeners) {
                listener.fileModified();
            }
        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
            for (JDKFileListener listener : listeners) {
                listener.fileDeleted();
            }
        }
    }

    private void valid() throws JDKNotifyException {
        if (kinds == null || kinds.isEmpty()) {
            throw new JDKNotifyException("There is no events to register");
        }

        if (!folder.exists()) {
            throw new JDKNotifyException(String.format("%s is not exist!", folder));
        }

        if (!folder.isDirectory()) {
            throw new JDKNotifyException(String.format("%s is not a directory!", folder));
        }
    }

    public synchronized void addListener(JDKFileListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(JDKFileListener listener) {
        listeners.remove(listener);
    }
}