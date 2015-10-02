package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Gaurab Pradhan
 */
public class PropertiesUtil {

    private static final String APPLICATION_PROPERTY_FILE = "conf/application.properties";

    private static String webUrl;
    private static String tableTag;
    private static String anchorTag;
    private static String filePath;

    public static String getWebUrl() {
        return webUrl;
    }

    public static String getTableTag() {
        return tableTag;
    }

    public static String getAnchorTag() {
        return anchorTag;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void loadPropertiesFile() {

        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(APPLICATION_PROPERTY_FILE));
            webUrl = prop.getProperty("WebUrl");
            tableTag = prop.getProperty("TableTag");
            anchorTag = prop.getProperty("AnchorTag");
            filePath=prop.getProperty("filePath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
