package config;


import lombok.Getter;
import translator.TranslatorService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


@Getter
public class LabelProperties {
    //TUTAJ TWORZYC PROPERTASY
    String welcome="";
    String hackingtop="";
    String enterpwd="";
    String attemptsleft="";
    String ver="";
    String access ="";
    String lockedout="";
    String contactadmin="";
    String atreset="";
    String dudrem="";
    String name="";
    String admidate ="";
    String gender="";
    String caretaker="";
    String causeov="";
    String treatment="";
    String reportby="";
    String fromday ="";
    String recname="";
    String rectime="";
    String recmaterials="";
    String recresult="";
    String recdescr="";
    String audiolength ="";
    String help1 ="";
    String help2 ="";
    String help3 ="";
    String help4 ="";
    String help5 ="";
    String help6 ="";
    String help7 ="";
    String help8 ="";
    String help9 ="";
    String caps = "";
    InputStream inputStream;
    BufferedReader bufferedReader;
    private static TranslatorService translatorService = new TranslatorService();

    public void loadProperties(String language){
        try {
            Properties prop = new Properties();
            String propFileName = "dict/"+language+"/labels.properties";
            bufferedReader =  new BufferedReader(new InputStreamReader(new FileInputStream(propFileName),"UTF8"));
            prop.load(bufferedReader);
            //TUTAJ PRZYPISYWAC PROPERTASY
            welcome = translatorService.translateToTile(prop.getProperty("welcome"));
            hackingtop = translatorService.translateToTile(prop.getProperty("hackingtop"));
            enterpwd = translatorService.translateToTile(prop.getProperty("enterpwd"));
            attemptsleft = translatorService.translateToTile(prop.getProperty("attemptsleft"));
            ver = translatorService.translateToTile(prop.getProperty("ver"));
            access = translatorService.translateToTile(prop.getProperty("access"));
            lockedout = translatorService.translateToTile(prop.getProperty("lockedout"));
            contactadmin = translatorService.translateToTile(prop.getProperty("contactadmin"));
            atreset = translatorService.translateToTile(prop.getProperty("atreset"));
            dudrem = translatorService.translateToTile(prop.getProperty("dudrem"));
            name = translatorService.translateToTile(prop.getProperty("name"));
            admidate = translatorService.translateToTile(prop.getProperty("admidate"));
            gender = translatorService.translateToTile(prop.getProperty("gender"));
            caretaker = translatorService.translateToTile(prop.getProperty("caretaker"));
            causeov = translatorService.translateToTile(prop.getProperty("causeov"));
            treatment = translatorService.translateToTile(prop.getProperty("treatment"));
            reportby = translatorService.translateToTile(prop.getProperty("reportby"));
            fromday = translatorService.translateToTile(prop.getProperty("fromday"));
            recname = translatorService.translateToTile(prop.getProperty("recname"));
            rectime = translatorService.translateToTile(prop.getProperty("rectime"));
            recmaterials = translatorService.translateToTile(prop.getProperty("recmaterials"));
            recresult = translatorService.translateToTile(prop.getProperty("recresult"));
            recdescr = translatorService.translateToTile(prop.getProperty("recdescr"));
            audiolength = translatorService.translateToTile(prop.getProperty("audiolength"));
            help1 = translatorService.translateToTile(prop.getProperty("help1"));
            help2 = translatorService.translateToTile(prop.getProperty("help2"));
            help3 = translatorService.translateToTile(prop.getProperty("help3"));
            help4 = translatorService.translateToTile(prop.getProperty("help4"));
            help5 = translatorService.translateToTile(prop.getProperty("help5"));
            help6 = translatorService.translateToTile(prop.getProperty("help6"));
            help7 = translatorService.translateToTile(prop.getProperty("help7"));
            help8 = translatorService.translateToTile(prop.getProperty("help8"));
            help9 = translatorService.translateToTile(prop.getProperty("help9"));
            caps = translatorService.translateToTile(prop.getProperty("caps"));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}


