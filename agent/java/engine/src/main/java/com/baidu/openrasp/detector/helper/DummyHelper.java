package com.baidu.openrasp.detector.helper;

import com.baidu.openrasp.request.AbstractRequest;

public class DummyHelper extends ServerHelper {

    @Override
    protected Supplier instanceNameSupplier() {
        return new DummySupplier();
    }

    @Override
    protected Supplier clusterNameSupplier() {
        return new DummySupplier();
    }

    @Override
    protected Supplier appNameSupplier(AbstractRequest unusedRequest) {
        return new DummySupplier();
    }

    static private class DummySupplier implements Supplier {

        @Override
        public String getValue() {
            return "";
        }

    }
}
