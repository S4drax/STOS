package config;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hexworks.zircon.api.component.ColorTheme;
import resources.Colors;
import translator.TranslatorService;

import java.io.*;
import java.util.Locale;
import java.util.Properties;


@Slf4j
@Getter
public class AppProperties {
    //TUTAJ TWORZYC PROPERTASY
    String test="";
    String language="";
    String translated="";
    String login="";
    String password="";
    String version="";
    ColorTheme theme;
    InputStream inputStream;
    BufferedReader bufferedReader;
    private static TranslatorService translatorService = new TranslatorService();

    public void loadProperties(){
        try {
            Properties prop = new Properties();
            String propFileName = "application.properties";
            bufferedReader =  new BufferedReader(new InputStreamReader(new FileInputStream(propFileName),"UTF8"));
            prop.load(bufferedReader);
            //TUTAJ PRZYPISYWAC PROPERTASY
            login = prop.getProperty("login");
            password = prop.getProperty("password");
            test = translatorService.translateToTile(prop.getProperty("test"));
            language = translatorService.translateToTile(prop.getProperty("language"));
            translated = translatorService.translateToTile("zazółć gęślą jaźń");
            version = prop.getProperty("version");
            String themeProperty = prop.getProperty("theme");
            try {
                theme = Colors.valueOf(themeProperty.toUpperCase(Locale.ROOT)).getColorTheme();
            } catch (IllegalArgumentException ex) {
                log.error("Theme {} unrecognized, setting to default.", themeProperty);
                theme = Colors.CAPITOL.getColorTheme();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setLanguage(String language){
        this.language = language;
    }
}


