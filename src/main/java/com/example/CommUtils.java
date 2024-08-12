package com.example;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 读取ph.properties配置文件
 *
 */
@Slf4j
public class CommUtils {

        private static final String configPath = "/ph.properties";
        private static Properties prop = null;

        private static void init() throws Exception {
            InputStream in = null;
            try {
                in = CommUtils.class.getResourceAsStream(configPath);
                if (in != null) {
                    prop = new Properties();
                    prop.load(in);
                    in.close();
                    in = null;
                }
                if (prop == null) {
                    throw new Exception("read [" + configPath + "] failed!");
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }

        public static String getString(String key) {
            try {
                if (prop == null) {
                    init();
                }
                return prop.getProperty(key);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return null;
        }

        public static Integer getInt(String key) {
            try {
                if (prop == null) {
                    init();
                }
                return Integer.valueOf(prop.getProperty(key));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return null;
        }

        public static boolean getBoolean(String key) {
            String val = getString(key);
            return "true".equalsIgnoreCase(val) || "1".equalsIgnoreCase(val) || "Y".equalsIgnoreCase(val);
        }

        public static boolean getBoolean(String key, boolean defValue) {
            String val = getString(key);
            if (val == null) {
                return defValue;
            } else {
                return getBoolean(key);
            }
        }

        public static String getString(String key, String defValue) {
            try {
                if (prop == null) {
                    init();
                }
                if (prop.containsKey(key)) {
                    return prop.getProperty(key);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return defValue;
        }

        public static Properties getProperty() {
            try {
                if (prop == null) {
                    init();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return prop;
        }
}
