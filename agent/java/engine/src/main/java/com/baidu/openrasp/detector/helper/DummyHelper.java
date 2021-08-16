package com.baidu.openrasp.detector.helper;

public class DummyHelper extends ServerHelper {

    @Override
    protected Supplier instanceNameSupplier() {
        return new DummySupplier();
    }

    @Override
    protected Supplier clusterNameSupplier() {
        return new DummySupplier();
    }

    static private class DummySupplier implements Supplier {

        @Override
        public String getValue() {
            return "";
        }

    }
}
