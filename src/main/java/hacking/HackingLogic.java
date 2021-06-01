package hacking;


import commhandler.SoundBase;
import org.hexworks.zircon.api.component.Label;
import org.hexworks.zircon.api.component.LogArea;
import translator.TranslatorService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class HackingLogic {
    private SpecialHackingDict specialDict = new SpecialHackingDict();
    BufferedReader bufferedReader;
    private static TranslatorService translatorService = new TranslatorService();

    public void initHakcing(List<Label> hackLabels, String password, String lang, List<String> chosenDuds){
        int randomNum = ThreadLocalRandom.current().nextInt(256, 3595);
        List<String> baseDict = loadDictionary(password.length(), lang);
        password=password.toUpperCase();
        List<String> duds = getMatching(password,baseDict);
        String startHex = "0xF"+Integer.toHexString(randomNum).toUpperCase();
        boolean passPutIn = false;
        int i =0;
        String temp ="";
        for (Label l:hackLabels) {
            temp = generateRandomInsides(duds, password, passPutIn, 0, chosenDuds);
            if(temp.contains(password.toUpperCase())){
                passPutIn = true;
            }
            l.setText(" "+startHex+" " +temp);
            randomNum=randomNum+ThreadLocalRandom.current().nextInt(1,5);
            startHex = "0xF"+Integer.toHexString(randomNum).toUpperCase();
        }
        for (Label l:hackLabels) {
            temp = generateRandomInsides(duds, password, passPutIn, i, chosenDuds);
            if(temp.contains(password.toUpperCase())){
                passPutIn = true;
            }
            l.setText(l.getText()+ "  "+startHex+" "+temp);
            randomNum=randomNum+ThreadLocalRandom.current().nextInt(1,5);
            startHex = "0xF"+Integer.toHexString(randomNum).toUpperCase();
            i++;
        }
    }

    public boolean findPhrase(List<Label> hackLabels, String phrase, LogArea hackLog, String pswd, Label attempts, List<String> chosenDuds, ScheduledExecutorService soundExecutor, SoundBase soundBase) {
        int i;
        String replace="";
        for (Label l : hackLabels) {
            if (l.getText().contains(phrase.toUpperCase())){
                if(phrase.equalsIgnoreCase(pswd)){
                    hackLog.addHeader(">"+phrase.toUpperCase(), false);
                    return true;
                } else if(containsAny(phrase)) {
                    if (((phrase.substring(0,1).equalsIgnoreCase("[") && phrase.substring(phrase.length()-1,phrase.length()).equalsIgnoreCase("]")) ||
                            (phrase.substring(0,1).equalsIgnoreCase("(") && phrase.substring(phrase.length()-1,phrase.length()).equalsIgnoreCase(")")) ||
                            (phrase.substring(0,1).equalsIgnoreCase("<") && phrase.substring(phrase.length()-1,phrase.length()).equalsIgnoreCase(">")) ||
                            (phrase.substring(0,1).equalsIgnoreCase("{") && phrase.substring(phrase.length()-1,phrase.length()).equalsIgnoreCase("}")))
                            && (!phrase.contains(pswd) || !containsAnyDud(phrase,chosenDuds))){
                        for(i=0;i<phrase.length();i++) {
                            replace += ".";
                        }
                        hackLog.addHeader(">"+phrase.toUpperCase(), false);
                        bracketAction(hackLabels,attempts,chosenDuds,hackLog);
                        l.setText(l.getText().replace(phrase, replace));
                        return false;
                    }
                } else {
                        if(attempts.getText().contains("\u2588")){
                            attempts.setText(attempts.getText().substring(0,attempts.getText().length()-2));
                        }
                    soundExecutor.schedule(soundBase.getSIncorrect(),0, TimeUnit.SECONDS);
                    hackLog.addHeader(">"+phrase.toUpperCase(), false);
                        hackLog.addHeader(comparePP(phrase, pswd)+"/"+pswd.length()+" correct.", true);
                        return false;
                    }
                }
            }
        soundExecutor.schedule(soundBase.getSIncorrect(),0, TimeUnit.SECONDS);
        hackLog.addHeader(">"+phrase.toUpperCase(), false);
        hackLog.addHeader("Phrase not found", true);
        return false;
    }

    private void bracketAction(List<Label> hackLabels, Label attempts, List<String> chosenDuds,LogArea hackLog){
        String dud = chosenDuds.get(ThreadLocalRandom.current().nextInt(0, chosenDuds.size()));
        String rep ="";
        int i;
        for(i=0;i<dud.length();i++) {
            rep += ".";
        }
        if(ThreadLocalRandom.current().nextInt(0, 11)==0){
            hackLog.addHeader("Attempts reset", true);
            attempts.setText("ATTEMPT(S) LEFT:  \u2588 \u2588 \u2588 \u2588");
        } else {
            hackLog.addHeader("Dud removed", true);
            for (Label l : hackLabels) {
                if (l.getText().contains(dud.toUpperCase())) {
                    l.setText(l.getText().replace(dud,rep));
                    chosenDuds.remove(dud);
                }
            }
        }
    }

    private boolean containsAnyDud(String phrase, List<String> chosenDuds) {
        for (String dud : chosenDuds) {
            if (phrase.contains(dud)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsAny(String phrase) {
        if(phrase.contains("[") || phrase.contains("]") || phrase.contains("{") || phrase.contains("}") || phrase.contains("(") || phrase.contains(")") || phrase.contains("<") || phrase.contains(">")){
            return true;
        } else {
            return false;
        }
    }

    private List<String> getMatching(String password, List<String> full){
        List<String> zero = new ArrayList<>();
        List<String> one = new ArrayList<>();
        List<String> two = new ArrayList<>();
        List<String> three = new ArrayList<>();
        List<String> four = new ArrayList<>();
        List<String> five = new ArrayList<>();
        List<String> six = new ArrayList<>();
        List<String> seven = new ArrayList<>();
        List<String> eight = new ArrayList<>();
        List<String> nine = new ArrayList<>();
        List<String> ten = new ArrayList<>();
        for (String dud:full) {
            switch (comparePP(dud,password)){
                case "0":
                    zero.add(dud);
                    break;
                case "1":
                    one.add(dud);
                    break;
                case "2":
                    two.add(dud);
                    break;
                case "3":
                    three.add(dud);
                    break;
                case "4":
                    four.add(dud);
                    break;
                case "5":
                    five.add(dud);
                    break;
                case "6":
                    six.add(dud);
                    break;
                case "7":
                    seven.add(dud);
                    break;
                case "8":
                    eight.add(dud);
                    break;
                case "9":
                    nine.add(dud);
                    break;
                case "10":
                    ten.add(dud);
                    break;
            }
        }
        List<String> duds = new ArrayList<>();
        int i;
        if(zero.size()>1) {
            for (i = 0; i < 5; i++) {
                duds.add(zero.get(ThreadLocalRandom.current().nextInt(0, zero.size())));
            }
        }
        if(password.length()>1 && one.size()>1) {
            for (i = 0; i < 15; i++) {
                duds.add(one.get(ThreadLocalRandom.current().nextInt(0, one.size())));
            }
        }
        if(password.length()>2 && two.size()>1) {
            for (i = 0; i < 15; i++) {
                duds.add(two.get(ThreadLocalRandom.current().nextInt(0, two.size())));
            }
        }
        if(password.length()>3 && three.size()>1) {
            for (i = 0; i < 15; i++) {
                duds.add(three.get(ThreadLocalRandom.current().nextInt(0, three.size())));
            }
        }
        if(password.length()>4 && four.size()>1){
            for(i=0; i<20; i++){
                duds.add(four.get(ThreadLocalRandom.current().nextInt(0, four.size())));
            }
        }
        if(password.length()>5 && five.size()>1){
            for(i=0; i<15; i++){
                duds.add(five.get(ThreadLocalRandom.current().nextInt(0, five.size()+1)));
            }
        }
        if(password.length()>6 && six.size()>1){
            for(i=0; i<15; i++){
                duds.add(six.get(ThreadLocalRandom.current().nextInt(0, six.size()+1)));
            }
        }
        if(password.length()>7 && seven.size()>1){
            for(i=0; i<15; i++){
                duds.add(seven.get(ThreadLocalRandom.current().nextInt(0, seven.size()+1)));
            }
        }
        if(password.length()>8 && eight.size()>1){
            for(i=0; i<15; i++){
                duds.add(eight.get(ThreadLocalRandom.current().nextInt(0, eight.size()+1)));
            }
        }
        if(password.length()>9 && nine.size()>1){
            for(i=0; i<15; i++){
                duds.add(nine.get(ThreadLocalRandom.current().nextInt(0, nine.size()+1)));
            }
        }
        if(password.length()>10 && ten.size()>1){
            for(i=0; i<15; i++){
                duds.add(ten.get(ThreadLocalRandom.current().nextInt(0, ten.size()+1)));
            }
        }
        return duds;
    }

    private List<String> loadDictionary(int passwordLength, String language){
        List<String> dict = new ArrayList<>();
        String tmpLine ="";
        try {
            String dictFileName = "dict/"+language+"/"+passwordLength+".txt";
            bufferedReader =  new BufferedReader(new InputStreamReader(new FileInputStream(dictFileName),"UTF8"));
            tmpLine=bufferedReader.readLine();
            while(tmpLine!= null) {
                dict.add(tmpLine);
                tmpLine=bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return dict;
    }

    private String generateRandomInsides(List<String> duds, String password, boolean passPutIn, int k, List<String> chosenDuds){
        //insides is 29 chars long
        List<String> chars = specialDict.getCHARACTERS();
        String dud = "";
        String inside ="";
        boolean dudPutIn = false;
        int i, j, n;
        if (ThreadLocalRandom.current().nextInt(0,4)==0 && !duds.isEmpty()){
            if((ThreadLocalRandom.current().nextInt(0,6)==0 && !passPutIn)||k==42){
                dud = password;
            } else {
                i = ThreadLocalRandom.current().nextInt(0, duds.size());
                dud = duds.get(i);
                chosenDuds.add(dud);
                duds.remove(i);
            }
        }

        i=29-dud.length();
        for(j=0;j<i;j++){
            if(dudPutIn || ThreadLocalRandom.current().nextInt(0,5)>0) {
                n = ThreadLocalRandom.current().nextInt(0, chars.size());
                inside = inside + chars.get(n);
            } else {
                inside = inside+dud;
                dudPutIn=true;
            }
        }
        return inside;
    }

    private String comparePP(String phrase, String pswd){
        int i;
        int n = 0;
        int min = Math.min(pswd.length(), phrase.length());
        for(i=0;i<min;i++){
            if(pswd.toUpperCase().substring(i,i+1).equals(phrase.toUpperCase().substring(i,i+1))){
                n++;
            }
        }
        return String.valueOf(n);
    }
}
