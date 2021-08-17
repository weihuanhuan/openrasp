package com.baidu.openrasp.detector.helper;

import com.baidu.openrasp.request.AbstractRequest;

public abstract class RequestSupplier implements Supplier {

    protected final AbstractRequest request;

    public RequestSupplier(AbstractRequest request) {
        this.request = request;
    }
}
