package com.baidu.openrasp.detector.helper;

public class TomcatHelper extends ServerHelper {

    @Override
    protected Supplier instanceNameSupplier() {
        return new TomcatInstanceNameSupplier();
    }

    @Override
    protected Supplier clusterNameSupplier() {
        return new TomcatClusterNameSupplier();
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

}
