package com.baidu.openrasp.detector.helper;

import com.baidu.openrasp.config.Config;
import org.apache.commons.lang3.StringUtils;

public abstract class ServerHelper {

    public void init() {
        String instanceName = Config.getConfig().getInstanceName();
        if (StringUtils.isBlank(instanceName)) {
            Config.getConfig().setInstanceName(getInstanceName());
        }

        String clusterName = Config.getConfig().getClusterName();
        if (StringUtils.isBlank(clusterName)) {
            Config.getConfig().setClusterName(getClusterName());
        }
    }

    public String getInstanceName() {
        return instanceNameSupplier().getValue();
    }

    public String getClusterName() {
        return clusterNameSupplier().getValue();
    }

    protected abstract Supplier instanceNameSupplier();

    protected abstract Supplier clusterNameSupplier();

}
