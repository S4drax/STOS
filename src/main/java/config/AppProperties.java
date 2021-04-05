package config;


import lombok.Getter;
import org.hexworks.zircon.api.component.ColorTheme;
import resources.Colors;
import translator.TranslatorService;

import java.io.*;
import java.util.Properties;


@Getter
public class AppProperties {
    //TUTAJ TWORZYC PROPERTASY
    private static Colors colors = new Colors();
    String test="";
    String language="";
    String translated="";
    String login="";
    String password="";
    String version="";
    ColorTheme theme = colors.getCapitol();
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
            switch (prop.getProperty("theme")){
                case "capitol":
                    break;
                case "mojave":
                    theme = colors.getMojave();
                    break;
                case "zgon":
                    theme = colors.getZgon();
                    break;
                case "shpery":
                    theme = colors.getShpery();
                    break;
                case "cyfroni":
                    theme = colors.getCyfroni();
                    break;
                case "arystokracja":
                    theme = colors.getArystokracja();
                    break;
                case "kosmodrom":
                    theme = colors.getKosmodrom();
                    break;
                case "system":
                    theme = colors.getSystem();
                    break;
                case "ammo":
                    theme = colors.getAMMO();
                    break;
                case "cyberpunk":
                    theme = colors.getCYBERPUNK();
                    break;
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


