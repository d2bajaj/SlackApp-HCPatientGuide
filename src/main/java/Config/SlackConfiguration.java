package Config;

import java.util.Properties;

public class SlackConfiguration {
    Properties configurationFile;

    public SlackConfiguration() {
        configurationFile = new Properties();
        try {
            configurationFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch(Exception e) {
            //
        }
    }

    public String getBotToken() {
        return this.configurationFile.getProperty("botToken");
    }

    public String getAppToken() {
        return this.configurationFile.getProperty("appToken");
    }
}
