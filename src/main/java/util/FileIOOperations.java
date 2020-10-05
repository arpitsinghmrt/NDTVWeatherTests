package util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileIOOperations {
    private static Logger logger = Logger.getLogger(GenericActions.class);

    public String readPropertyFromConfig(String propertyKey) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties config = new Properties();
        try {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config.getProperty(propertyKey);
    }

    public void deleteScreenshotFolder() {
        try {
            FileUtils.deleteDirectory(new File("screenshots"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Screenshots deleted successfully");
    }
}


