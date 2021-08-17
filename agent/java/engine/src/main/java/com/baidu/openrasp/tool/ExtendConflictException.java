package com.baidu.openrasp.tool;

public class ExtendConflictException extends Exception {

    public ExtendConflictException(String message) {
        super(message);
    }

    public ExtendConflictException(Throwable cause) {
        super(cause);
    }

    public ExtendConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
