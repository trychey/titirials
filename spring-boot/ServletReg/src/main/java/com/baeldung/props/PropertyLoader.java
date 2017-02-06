package com.baeldung.props;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private static final Logger log = LoggerFactory.getLogger(PropertyLoader.class);

    public Properties getProperties(String file) {
        Properties prop = new Properties();
        InputStream input = null;
        input = getClass().getResourceAsStream(file);
        prop.load(input);

        if (input != null) {
            input.close();
        }

        return prop;
    }
}
