package com.baidu.openrasp.detector.helper;

import com.baidu.openrasp.config.Config;
import com.baidu.openrasp.request.AbstractRequest;
import com.baidu.openrasp.tool.Reflection;
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

    @Override
    protected Supplier appNameSupplier(AbstractRequest request) {
        return new BESAppNameSupplier(request);
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

    static private class BESAppNameSupplier extends RequestSupplier {

        protected static final Class[] EMPTY_CLASS = new Class[]{};
        protected static final Class[] STRING_CLASS = new Class[]{String.class};
        protected static final String GET_SERVLET_CONTEXT = "getServletContext";
        protected static final String GET_INIT_PARAMETER = "getInitParameter";
        protected static final String MODULE_ID_PARAMETER_NAME = "com.bes.enterprise.webapp.moduleId";

        public BESAppNameSupplier(AbstractRequest request) {
            super(request);
        }

        @Override
        public String getValue() {
            if (request == null) {
                return "";
            }

            Object rawRequest = request.getRequest();
            Object servletContext = Reflection.invokeMethod(rawRequest, GET_SERVLET_CONTEXT, EMPTY_CLASS);
            String parameter = Reflection.invokeStringMethod(servletContext, GET_INIT_PARAMETER, STRING_CLASS, MODULE_ID_PARAMETER_NAME);
            String appName = parameter == null ? request.getContextPath() : parameter;
            return appName;
        }
    }
}
