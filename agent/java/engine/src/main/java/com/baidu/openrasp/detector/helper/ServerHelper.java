package com.baidu.openrasp.detector.helper;

import com.baidu.openrasp.config.Config;
import com.baidu.openrasp.request.AbstractRequest;

public abstract class ServerHelper {

    private final String supplierInstanceName;

    private final String supplierClusterName;

    public ServerHelper() {
        supplierInstanceName = instanceNameSupplier().getValue();
        supplierClusterName = clusterNameSupplier().getValue();
    }

    public String getInstanceName() {
        String instanceName = Config.getConfig().getInstanceName();
        return instanceName == null ? supplierInstanceName : instanceName;
    }

    public String getClusterName() {
        String clusterName = Config.getConfig().getClusterName();
        return clusterName == null ? supplierClusterName : clusterName;
    }

    public String getAppName(AbstractRequest request) {
        return appNameSupplier(request).getValue();
    }

    protected abstract Supplier instanceNameSupplier();

    protected abstract Supplier clusterNameSupplier();

    protected abstract Supplier appNameSupplier(AbstractRequest request);

}
