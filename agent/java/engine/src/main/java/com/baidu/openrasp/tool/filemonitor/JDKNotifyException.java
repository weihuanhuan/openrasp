package com.baidu.openrasp.tool.filemonitor;

import java.io.IOException;

public class JDKNotifyException extends IOException {

    public JDKNotifyException() {
    }

    public JDKNotifyException(String message) {
        super(message);
    }

    public JDKNotifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDKNotifyException(Throwable cause) {
        super(cause);
    }
}
