/*
 * Copyright 2017-2020 Baidu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baidu.openrasp;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import static com.baidu.openrasp.ModuleLoader.*;

/**
 * Created by tyy on 19-2-26.
 */
public class ModuleContainer implements Module {

    private Module module;
    private String moduleName;

    private static final String UCP_FIELD_NAME = "ucp";
    private static final String ADD_URL_METHOD_NAME = "addURL";

    public ModuleContainer(String jarName) throws Throwable {
        try {
            File originFile = new File(baseDirectory + File.separator + jarName);
            JarFile jarFile = new JarFile(originFile);
            Attributes attributes = jarFile.getManifest().getMainAttributes();
            jarFile.close();
            this.moduleName = attributes.getValue("Rasp-Module-Name");
            String moduleEnterClassName = attributes.getValue("Rasp-Module-Class");
            if (moduleName != null && moduleEnterClassName != null
                    && !moduleName.equals("") && !moduleEnterClassName.equals("")) {
                Class moduleClass;
                if (ClassLoader.getSystemClassLoader() instanceof URLClassLoader && moduleClassLoader instanceof URLClassLoader) {
                    Method method = Class.forName("java.net.URLClassLoader").getDeclaredMethod("addURL", URL.class);
                    method.setAccessible(true);
                    method.invoke(moduleClassLoader, originFile.toURI().toURL());
                    method.invoke(ClassLoader.getSystemClassLoader(), originFile.toURI().toURL());
                    moduleClass = moduleClassLoader.loadClass(moduleEnterClassName);
                    module = (Module) moduleClass.newInstance();
                } else if (ModuleLoader.isModularityJdk()) {
                    moduleClassLoader = ClassLoader.getSystemClassLoader().getParent();

                    Field ucpField = getField(moduleClassLoader, UCP_FIELD_NAME);
                    Object ucpFieldValue = getFieldValue(ucpField, moduleClassLoader);
                    if (ucpFieldValue == null) {
                        ucpFieldValue = createFieldValue(ucpField);
                        setFieldValue(ucpField, moduleClassLoader, ucpFieldValue);
                    }

                    Method addURLMethod = ucpFieldValue.getClass().getMethod(ADD_URL_METHOD_NAME, URL.class);
                    addURLMethod.invoke(ucpFieldValue, originFile.toURI().toURL());

                    moduleClass = moduleClassLoader.loadClass(moduleEnterClassName);
                    module = (Module) moduleClass.newInstance();
                } else if(ModuleLoader.isCustomClassloader()) {
                    moduleClassLoader = ClassLoader.getSystemClassLoader();
                    Method method = moduleClassLoader.getClass().getDeclaredMethod("appendToClassPathForInstrumentation", String.class);
                    method.setAccessible(true);
                    try {
                        method.invoke(moduleClassLoader, originFile.getCanonicalPath());
                    } catch (Exception e) {
                        method.invoke(moduleClassLoader, originFile.getAbsolutePath());
                    }
                    moduleClass = moduleClassLoader.loadClass(moduleEnterClassName);
                    module = (Module) moduleClass.newInstance();
                } else {
                    throw new Exception("[OpenRASP] Failed to initialize module jar: " + jarName);
                }
            }
        } catch (Throwable t) {
            System.err.println("[OpenRASP] Failed to initialize module jar: " + jarName);
            throw t;
        }
    }

    private Field getField(ClassLoader classLoader, String fieldName) throws NoSuchFieldException {
        Class<?> superclass = classLoader.getClass().getSuperclass();
        return superclass.getDeclaredField(fieldName);
    }

    private Object getFieldValue(Field field, ClassLoader classLoader) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(classLoader);
    }

    private Object createFieldValue(Field field) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> type = field.getType();
        Constructor<?> constructor = type.getConstructor(URL[].class, AccessControlContext.class);
        return constructor.newInstance(new URL[0], null);
    }

    private void setFieldValue(Field field, ClassLoader classLoader, Object object) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(classLoader, object);
    }

    @Override
    public void start(String mode, Instrumentation inst) throws Throwable {
        module.start(mode, inst);
    }

    @Override
    public void release(String mode) throws Throwable {
        try {
            if (module != null) {
                module.release(mode);
            }
        } catch (Throwable t) {
            System.err.println("[OpenRASP] Failed to release module: " + moduleName);
            throw t;
        }
    }

}
