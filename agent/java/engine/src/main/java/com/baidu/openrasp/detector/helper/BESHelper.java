package com.baidu.openrasp.detector.helper;

import com.baidu.openrasp.config.Config;
import org.apache.commons.lang3.StringUtils;

public class BESHelper extends ServerHelper {

    @Override
    protected Supplier instanceNameSupplier() {
        return new BESInstanceNameSupplier();
    }

    @Override
    protected Supplier clusterNameSupplier() {
        return new BESClusterNameSupplier();
    }

    static private class BESInstanceNameSupplier implements Supplier {

        @Override
        public String getValue() {
            String name = System.getProperty("com.bes.instanceName");
            if (StringUtils.isBlank(name)) {
                name = System.getProperty("com.bes.installRoot", Config.getConfig().getBaseDirectory());
            }
            return name;
        }
    }

    static private class BESClusterNameSupplier implements Supplier {

        @Override
        public String getValue() {
            String name = System.getProperty("com.bes.clusterName", "");
            return name;
        }
    }
}
