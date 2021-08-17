package com.baidu.openrasp.detector.helper;

import com.baidu.openrasp.request.AbstractRequest;

public class TomcatHelper extends ServerHelper {

    @Override
    protected Supplier instanceNameSupplier() {
        return new TomcatInstanceNameSupplier();
    }

    @Override
    protected Supplier clusterNameSupplier() {
        return new TomcatClusterNameSupplier();
    }

    @Override
    protected Supplier appNameSupplier(AbstractRequest request) {
        return new TomcatAppNameSupplier(request);
    }

    static private class TomcatInstanceNameSupplier implements Supplier {

        @Override
        public String getValue() {
            return System.getProperty("catalina.base", "");
        }

    }

    static private class TomcatClusterNameSupplier implements Supplier {

        @Override
        public String getValue() {
            return "";
        }
    }

    static private class TomcatAppNameSupplier extends RequestSupplier {

        public TomcatAppNameSupplier(AbstractRequest request) {
            super(request);
        }

        @Override
        public String getValue() {
            String appName = request == null ? "" : request.getContextPath();
            return appName;
        }
    }

}
