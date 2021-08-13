package com.baidu.openrasp.plugin.checker;

import com.baidu.openrasp.messaging.ErrorType;
import com.baidu.openrasp.messaging.LogTool;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CheckLevel {

    private static final Properties mapping = new Properties();

    static {
        ClassLoader classLoader = CheckLevel.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("level.properties")) {
            mapping.load(inputStream);
        } catch (IOException e) {
            LogTool.error(ErrorType.RUNTIME_ERROR, "failed to read attack level mapping file", e);
        }
    }

    public static Level getCheckLevel(CheckParameter.Type type) {
        if (type == null || StringUtils.isEmpty(type.getName())) {
            return Level.UNKNOWN;
        }

        String mappingProperty = mapping.getProperty(type.getName());
        return Level.toLevel(mappingProperty);
    }

    public enum Level {

        HIGH("high"), MEDIUM("medium"), LOW("low"), NONE("none"), UNKNOWN("unknown");

        private String name;

        Level(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Level toLevel(String name) {
            for (Level level : Level.values()) {
                if (level.name.equalsIgnoreCase(name)) {
                    return level;
                }
            }
            return UNKNOWN;
        }
    }
}
