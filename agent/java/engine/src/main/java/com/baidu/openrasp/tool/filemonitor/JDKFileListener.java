package com.baidu.openrasp.tool.filemonitor;

import java.util.EventListener;

public interface JDKFileListener extends EventListener {

    public void fileCreated();

    public void fileDeleted();

    public void fileModified();

    public void fileRenamed();

}
