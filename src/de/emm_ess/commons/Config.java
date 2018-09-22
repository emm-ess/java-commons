package de.emm_ess.commons;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Config {
    private final static String DEFAULT_CONFIG_FILENAME = "config.properties";

    private CompositeConfiguration config = new CompositeConfiguration();

    private static Config instance;

    private Config(final String filename) {
        createConfig(filename);
    }

    private Config(final String filename, final boolean saveValuesIfNotPresent) {
        if (saveValuesIfNotPresent) {
            initConfig(filename);
        }
        createConfig(filename);
    }

    private Config(final String[] filenames) {
        for (String filename : filenames) {
            createConfig(filename);
        }
    }

    private Config(final String[] filenames, final String defaultFilename) {
        initConfig(defaultFilename);
        createConfig(filenames);
    }


    public static Config loadConfig() throws ConfigAlreadyLoadedException {
        if (instance != null) {
            throw new ConfigAlreadyLoadedException();
        }

        loadEmptyConfig();

        return instance;
    }

    public static Config loadConfig(final String filename) throws ConfigAlreadyLoadedException {
        if (instance != null) {
            throw new ConfigAlreadyLoadedException();
        }

        instance = new Config(filename);
        return instance;
    }

    public static Config loadConfig(final String filename, final boolean saveIfNotPresent) throws ConfigAlreadyLoadedException {
        if (instance != null) {
            throw new ConfigAlreadyLoadedException();
        }

        instance = new Config(filename, saveIfNotPresent);
        return instance;
    }

    public static Config loadConfig(final String[] files) throws ConfigAlreadyLoadedException {
        if (instance != null) {
            throw new ConfigAlreadyLoadedException();
        }

        instance = new Config(files);
        return instance;
    }

    public static Config loadConfig(final String[] files, final String defaultFilename) throws ConfigAlreadyLoadedException {
        if (instance != null) {
            throw new ConfigAlreadyLoadedException();
        }

        instance = new Config(files, defaultFilename);
        return instance;
    }


    public static Config getConfig() {
        return instance;
    }

    public static Config getConfig(final boolean createIfNeeded) {
        if (instance == null && createIfNeeded) {
            loadEmptyConfig();
        }

        return instance;
    }

    private static void loadEmptyConfig(){
        String sb = System.getProperty("user.home") +
                File.separator +
                DEFAULT_CONFIG_FILENAME;
        instance = new Config(sb, true);
    }


    private void initConfig(final String filename) {
        final File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final Parameters params = new Parameters();
        //for saving values
        FileBasedConfigurationBuilder<FileBasedConfiguration> defaultConfigBuilder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class, null, true)
                .configure(params.properties().setFile(file));
        defaultConfigBuilder.setAutoSave(true);

        try {
            config = new CompositeConfiguration(defaultConfigBuilder.getConfiguration());
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createConfig(final String[] filenames) {
        for (String filename : filenames) {
            createConfig(filename);
        }
    }

    private void createConfig(final String filename) {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFileName(filename));
        try {
            config.addConfiguration(builder.getConfiguration());
        } catch (ConfigurationException cex) {
            cex.printStackTrace();
        }
    }


    public int getInt(final String key) {
        return config.getInt(key);
    }

    public int getInt(final String key, final int defaultValue) {
        if (config.containsKey(key)) {
            return config.getInt(key);
        } else {
            config.addProperty(key, defaultValue);
            return defaultValue;
        }
    }

    public int getInt(final String[] keys, final int defaultValue) {
        if (keys.length == 1) {
            return getInt(keys[0], defaultValue);
        } else if (config.containsKey(keys[0])) {
            return config.getInt(keys[0]);
        } else {
            String[] newKeys = Arrays.copyOfRange(keys, 1, keys.length);
            return getInt(newKeys, defaultValue);
        }
    }

    public float getFloat(final String key) {
        return config.getFloat(key);
    }

    public float getFloat(final String key, final float defaultValue) {
        if (config.containsKey(key)) {
            return config.getFloat(key);
        } else {
            config.addProperty(key, defaultValue);
            return defaultValue;
        }
    }

    public float getFloat(final String[] keys, final float defaultValue) {
        if (keys.length == 1) {
            return getFloat(keys[0], defaultValue);
        } else if (config.containsKey(keys[0])) {
            return config.getFloat(keys[0]);
        } else {
            String[] newKeys = Arrays.copyOfRange(keys, 1, keys.length);
            return getFloat(newKeys, defaultValue);
        }
    }

    public String getString(final String key) {
        return config.getString(key);
    }

    public String getString(final String key, final String defaultValue) {
        if (config.containsKey(key)) {
            return config.getString(key);
        } else {
            config.addProperty(key, defaultValue);
            return defaultValue;
        }
    }

    public String getString(final String[] keys, final String defaultValue) {
        if (keys.length == 1) {
            return getString(keys[0], defaultValue);
        } else if (config.containsKey(keys[0])) {
            return config.getString(keys[0]);
        } else {
            String[] newKeys = Arrays.copyOfRange(keys, 1, keys.length);
            return getString(newKeys, defaultValue);
        }
    }

    public boolean getBoolean(final String key) {
        return config.getBoolean(key);
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        if (config.containsKey(key)) {
            return config.getBoolean(key);
        } else {
            config.addProperty(key, defaultValue);
            return defaultValue;
        }
    }

    public boolean getBoolean(final String[] keys, final boolean defaultValue) {
        if (keys.length == 1) {
            return getBoolean(keys[0], defaultValue);
        } else if (config.containsKey(keys[0])) {
            return config.getBoolean(keys[0]);
        } else {
            String[] newKeys = Arrays.copyOfRange(keys, 1, keys.length);
            return getBoolean(newKeys, defaultValue);
        }
    }

    public void set(final String key, final int value) {
        config.setProperty(key, value);
    }

    public void set(final String key, final float value) {
        config.setProperty(key, value);
    }

    public void set(final String key, final String value) {
        config.setProperty(key, value);
    }

    public void set(final String key, final boolean value) {
        config.setProperty(key, value);
    }
}
