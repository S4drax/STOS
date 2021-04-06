package dynamic;

import config.LabelProperties;
import content.DynamicBox;
import content.Folder;
import content.MenuElement;
import org.hexworks.zircon.api.Components;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hexworks.zircon.api.builder.component.TextBoxBuilder;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import translator.TranslatorService;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.*;

public class DynamicComponentService {
    private int menu = 1;
    private TranslatorService translatorService = new TranslatorService();
    public Map<String, Folder> initialise(Map<String, MenuElement> menuElementMap, LabelProperties labelProperties){
        return readFile("directory.json", menuElementMap, labelProperties);
    }

    private Map<String,Folder> readFile (String fileName, Map<String, MenuElement> menuElementMap, LabelProperties labelProperties) {
        JSONParser jsonParser = new JSONParser();
        Map<String,Folder> folderMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"))){
            Object obj = jsonParser.parse(reader);
            JSONArray directory = (JSONArray) obj;
            directory.forEach( folder -> parseDirectory( (JSONObject) folder , folderMap, menuElementMap, labelProperties) );
            createMainMenu(menuElementMap ,folderMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return folderMap;
    }

    private void parseDirectory(JSONObject directory, Map<String, Folder> folderMap, Map<String, MenuElement> menuElementMap, LabelProperties labelProperties){
        menu = 1;
        JSONObject folderObject = (JSONObject) directory.get("folder");
        List<DynamicBox> fragmentList = new ArrayList<>();
        JSONArray fragments = (JSONArray) folderObject.get("fragments");
        String fname = folderObject.get("fname").toString();
        fragments.forEach(fragment -> parseFolder((JSONObject) fragment, fragmentList, menu++, fname, labelProperties));
        Folder folder = Folder.builder()
                .name(fname)
                .documents(fragmentList)
                .build();
        folderMap.put(folderObject.get("fname").toString(), folder);
        createSubMenu(folderObject.get("fname").toString(), folder, menuElementMap);
    }

    private void parseFolder(JSONObject folder, List<DynamicBox> dynamicBoxList, int menu, String folderName, LabelProperties labelProperties) {
        JSONObject fragmentObject = (JSONObject) folder.get("fragment");
        JSONObject tBox = (JSONObject) fragmentObject.get("textBox");
        int al = Integer.parseInt(tBox.get("level").toString());
        String text = tBox.get("addHeader").toString();
        String text2 = text;
        text = translatorService.translateToTile(text);
        TextBoxBuilder textBox = Components.textBox(94)
                .withPosition(0,0);

        switch (tBox.get("style").toString()){
            case "doc_compact":
                docCompact(text,textBox);
                dynamicBoxList.add(new DynamicBox(Size.create(94,44), textBox.build(), Position.create(0,0), menu, folderName,fragmentObject.get("name").toString(), null, "doc_compact", text2, al));
                break;
            case "doc_spread":
                docSpread(text,textBox);
                dynamicBoxList.add(new DynamicBox(Size.create(94,44), textBox.build(), Position.create(0,0), menu, folderName,fragmentObject.get("name").toString(), null, "doc_spread", text2, al));
                break;
            case "med_chart":
                dynamicBoxList.add(new DynamicBox(Size.create(94,44), medicalChart(text, labelProperties), Position.create(0,0), menu, folderName,fragmentObject.get("name").toString(), null, "med_chart", text2, al));
                break;
            case "status_report_c":
                dynamicBoxList.add(new DynamicBox(Size.create(94,44), statusReportCompact(text, labelProperties), Position.create(0,0), menu, folderName,fragmentObject.get("name").toString(), null, "status_report_c", text2, al));
                break;
            case "status_report_s":
                dynamicBoxList.add(new DynamicBox(Size.create(94,44), statusReportSpread(text, labelProperties), Position.create(0,0), menu, folderName,fragmentObject.get("name").toString(), null, "status_report_s", text2, al));
                break;
            case "schematic":
                dynamicBoxList.add(new DynamicBox(Size.create(94,44), schematic(text, labelProperties), Position.create(0,0), menu, folderName, fragmentObject.get("name").toString(), null, "schematic", text2, al));
                break;
            case "audio":
                dynamicBoxList.add(audio(text, fragmentObject.get("name").toString(), folderName, menu, labelProperties, al));
                break;
            default:
                docCompact(text,textBox);
                dynamicBoxList.add(new DynamicBox(Size.create(94,44), textBox.build(), Position.create(0,0), menu, folderName,fragmentObject.get("name").toString(), null, "doc_compact", text2, al));
                break;
        }
    }

    private static void createMainMenu(Map<String, MenuElement> menuElementMap, Map<String,Folder> directory) {
        List<String> menuElements = new ArrayList<>();
        for (Map.Entry<String, Folder> folder : directory.entrySet()) {
            menuElements.add(folder.getKey());
        }
        menuElementMap.put("home", new MenuElement(Size.create(94,44),Position.create(0,0),menuElements));
    }
    private static void createSubMenu(String name, Folder folder, Map<String, MenuElement> menuElementMap){
        List<String> subMenuElements = new ArrayList<>();
        for (DynamicBox entry : folder.getDocuments()){
            subMenuElements.add(entry.getId().toString()+") "+entry.getName());
        }
        menuElementMap.put(name, new MenuElement(Size.create(94,44),Position.create(0,0), subMenuElements));
    }

    public void docCompact (String text, TextBoxBuilder textBox){
        String tmp;
        int i = 0;
        int j = 92;
        do {
            if (text.substring(0, 1).equals(" ")){
                text=text.substring(1);
            }
            if(j<text.length()) {
                tmp = text.substring(i, j);
            } else {
                tmp = text.substring(i,text.length());
            }
            textBox.addHeader(tmp, false);
            if(j<text.length())
                text=text.substring(j);
        } while (text.length()>j);
        textBox.addHeader(text,false);
    }

    private void docSpread (String text, TextBoxBuilder textBox){
        String tmp;
        int i = 0;
        int j = 92;

        do {
            if (text.substring(0, 1).equals(" ")){
                text=text.substring(1);
            }
            if(j<text.length()) {
                tmp = text.substring(i, j);
            } else {
                tmp = text.substring(i,text.length());
            }
            textBox.addHeader(tmp, true);
            if(j<text.length())
                text=text.substring(j);
        } while (text.length()>j);
        textBox.addHeader(text,false);
    }

    public VBox medicalChart (String text, LabelProperties labelProperties){
        VBox chart = vbox().withSize(94, 44).build();
        //line 1: Name and Admission date
        HBox line1 = hbox().withSize(94,3).build();
        //name
        TextBoxBuilder textBoxName = Components.textBox(45).withDecorations(box(BoxType.SINGLE));
        textBoxName.addHeader(labelProperties.getName()+" "+text.substring(0,text.indexOf("%")), false);
        text = text.substring(text.indexOf("%")+1);
        line1.addComponent(textBoxName.build());
        //admission date
        TextBoxBuilder textBoxDate = Components.textBox(45).withDecorations(box(BoxType.SINGLE));
        textBoxDate.addHeader(labelProperties.getAdmidate()+" "+text.substring(0,text.indexOf("%")), false);
        line1.addComponent(textBoxDate.build());
        chart.addComponent(line1);
        //line 2: Gender and patient status
        HBox line2 = hbox().withSize(94,3).build();
        text = text.substring(text.indexOf("%")+1);
        //gender
        TextBoxBuilder textBoxGender= Components.textBox(45).withDecorations(box(BoxType.SINGLE));
        textBoxGender.addHeader(labelProperties.getGender()+" "+text.substring(0,text.indexOf("%")), false);
        line2.addComponent(textBoxGender.build());
        text = text.substring(text.indexOf("%")+1);
        //status
        TextBoxBuilder textBoxStatus= Components.textBox(45).withDecorations(box(BoxType.SINGLE));
        textBoxStatus.addHeader("Status: "+text.substring(0,text.indexOf("%")), false);
        line2.addComponent(textBoxStatus.build());
        chart.addComponent(line2);
        text = text.substring(text.indexOf("%")+1);
        //caretaker
        TextBoxBuilder textBoxCaretaker = Components.textBox(92).withDecorations(box(BoxType.SINGLE));
        textBoxCaretaker.addHeader(labelProperties.getCaretaker()+" "+text.substring(0,text.indexOf("%")), false);
        chart.addComponent(textBoxCaretaker.build());
        text = text.substring(text.indexOf("%")+1);
        //cause of visit block
        Panel cause = panel()
                .withSize(94, 5)
                .withDecorations(box())
                .build();
        TextBoxBuilder textBoxCause = Components.textBox(92);
        textBoxCause.addHeader(labelProperties.getCauseov(), false);
        textBoxCause.addHeader(text.substring(0,text.indexOf("%")), false);
        cause.addComponent(textBoxCause.build());
        chart.addComponent(cause);
        text = text.substring(text.indexOf("%")+1);
        //threatment block
        Panel threatment = panel()
                .withSize(94, 30)
                .withDecorations(box())
                .build();
        TextBoxBuilder textBoxThreatment = Components.textBox(92);
        textBoxThreatment.addHeader(labelProperties.getTreatment(), false);
        do {
            if (text.substring(0, 1).equals(" ")){
                text=text.substring(1);
            }
            if(text.contains(",")) {
                textBoxThreatment.addHeader("-" + text.substring(0, text.indexOf(",")), false);
                text = text.substring(text.indexOf(",") + 1);
            }
        } while (text.contains(","));
        if (text.substring(0, 1).equals(" ")){
            text=text.substring(1);
        }
        textBoxThreatment.addHeader("-"+text, false);
        threatment.addComponent(textBoxThreatment.build());
        chart.addComponent(threatment);
        return chart;
    }
    public VBox statusReportCompact(String text, LabelProperties labelProperties) {
        String tmp;
        VBox report = vbox().withSize(94, 44).build();
        //Header
        TextBoxBuilder textBoxHeader = Components.textBox(92).withDecorations(box(BoxType.SINGLE));
        tmp = labelProperties.getReportby()+" "+text.substring(0,text.indexOf("%"));
        text = text.substring(text.indexOf("%")+1);
        tmp = tmp+ labelProperties.getFromday()+" " + text.substring(0,text.indexOf("%"));
        textBoxHeader.addHeader(tmp, false);
        report.addComponent(textBoxHeader);
        text = text.substring(text.indexOf("%")+1);
        //rest of the text
        TextBoxBuilder textBox = Components.textBox(94);
        int i = 0;
        int j = 92;
        do {
            if (text.substring(0, 1).equals(" ")){
                text=text.substring(1);
            }
            if(j<text.length()) {
                tmp = text.substring(i, j);
            } else {
                tmp = text.substring(i,text.length());
            }
            textBox.addHeader(tmp, false);
            if(j<text.length())
                text=text.substring(j);
        } while (text.length()>j);
        textBox.addHeader(text,false);
        report.addComponent(textBox);
        return report;
    }

    private VBox statusReportSpread(String text, LabelProperties labelProperties) {
        String tmp;
        VBox report = vbox().withSize(94, 44).build();
        //Header
        TextBoxBuilder textBoxHeader = Components.textBox(92).withDecorations(box(BoxType.SINGLE));
        tmp = labelProperties.getReportby()+" "+text.substring(0,text.indexOf("%"));
        text = text.substring(text.indexOf("%")+1);
        tmp = tmp+ labelProperties.getFromday()+" " + text.substring(0,text.indexOf("%"));
        textBoxHeader.addHeader(tmp, false);
        report.addComponent(textBoxHeader);
        text = text.substring(text.indexOf("%")+1);
        //rest of the text
        TextBoxBuilder textBox = Components.textBox(94);
        int i = 0;
        int j = 92;
        do {
            if (text.substring(0, 1).equals(" ")){
                text=text.substring(1);
            }
            if(j<text.length()) {
                tmp = text.substring(i, j);
            } else {
                tmp = text.substring(i,text.length());
            }
            textBox.addHeader(tmp, true);
            if(j<text.length())
                text=text.substring(j);
        } while (text.length()>j);
        textBox.addHeader(text,false);
        report.addComponent(textBox);
        return report;
    }

    public VBox schematic(String text, LabelProperties labelProperties){
        String tmp;
        VBox schematic = vbox().withSize(94, 44).build();
        //line 1: Name and Time
        HBox line1 = hbox().withSize(94,3).build();
        //name
        TextBoxBuilder textBoxName = Components.textBox(45).withDecorations(box(BoxType.SINGLE));
        textBoxName.addHeader(labelProperties.getRecname()+" "+text.substring(0,text.indexOf("%")), false);
        text = text.substring(text.indexOf("%")+1);
        line1.addComponent(textBoxName);
        //time
        TextBoxBuilder textBoxDate = Components.textBox(45).withDecorations(box(BoxType.SINGLE));
        textBoxDate.addHeader(labelProperties.getRectime()+" "+text.substring(0,text.indexOf("%")), false);
        line1.addComponent(textBoxDate);
        schematic.addComponent(line1);
        text = text.substring(text.indexOf("%")+1);
        //materials
        tmp = text.substring(0,text.indexOf("%")+1);
        Panel materials = panel()
                .withSize(94, 10)
                .withDecorations(box())
                .build();
        TextBoxBuilder textBoxMats = Components.textBox(92);
        textBoxMats.addHeader(labelProperties.getRecmaterials(), false);
        do {
            if (tmp.substring(0, 1).equals(" ")){
                tmp=tmp.substring(1);
            }
            if(tmp.contains(",")) {
                textBoxMats.addHeader("-" + text.substring(0, tmp.indexOf(",")), false);
                tmp = tmp.substring(text.indexOf(",") + 1);
            }
        } while (tmp.contains(","));
        if (tmp.substring(0, 1).equals(" ")){
            tmp=tmp.substring(1);
        }
        textBoxMats.addHeader("-"+tmp.substring(0,tmp.indexOf("%")), false);
        materials.addComponent(textBoxMats);
        schematic.addComponent(materials);
        text = text.substring(text.indexOf("%")+1);
        //results
        Panel cause = panel()
                .withSize(94, 8)
                .withDecorations(box())
                .build();
        TextBoxBuilder textBoxCause = Components.textBox(92);
        textBoxCause.addHeader(labelProperties.getRecresult(), false);
        textBoxCause.addHeader(text.substring(0,text.indexOf("%")), false);
        cause.addComponent(textBoxCause);
        schematic.addComponent(cause);
        text = text.substring(text.indexOf("%")+1);
        //lore
        Panel lore = panel()
                .withSize(94,23)
                .withDecorations(box())
                .build();
        TextBoxBuilder textBoxLore = Components.textBox(92);
        textBoxLore.addHeader(labelProperties.getRecdescr(), false);
        textBoxLore.addHeader(text, false);
        lore.addComponent(textBoxLore);
        schematic.addComponent(lore);
        return schematic;
    }

    //text"wav_file_name.wav||length
    private DynamicBox audio(String text, String dynName, String folderName , int menu, LabelProperties labelProperties, int al){
        Label progresNumber = label()
                .withText(labelProperties.getAudiolength())
                .withSize(18,1)
                .withPosition(0,2)
                .build();

        VBox audio = vbox().withSize(94, 44).build();
        Panel audioPanel = panel()
                .withSize(40, 10)
                .withDecorations(box(BoxType.DOUBLE,text))
                .withPosition(27,10)
                .build();
        audioPanel.addComponent(progresNumber);
        String dur = "";
        try {
            File file = new File("audio/" + text);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            double durationInSeconds = (frames + 0.0) / format.getFrameRate();
            audioInputStream.close();
            dur = parseDuration(durationInSeconds);
            Label total = label()
                    .withText(" " + dur)
                    .withPosition(20,2)
                    .build();
            audioPanel.addComponent(total);
        } catch (Exception e){
        }
        audioPanel.addComponent(Components.label().withText(" >PLAY            >STOP").withPosition(0,5).build());
        audio.addComponent(audioPanel);
        DynamicBox tmpDyn = new DynamicBox(Size.create(94,44), audio, Position.create(0,0), menu, folderName, dynName, null, "audio", text, al);
        return tmpDyn;
    }

    private String parseDuration(double duration){
        int min =0;
        int hour =0;
        while (duration>60){
            min++;
            duration=duration-60;
        }
        while (min>60){
            hour++;
            min=min-60;
        }
        String seconds =Double.toString(duration);
        if (seconds.substring(0,2).contains(".")){
            seconds="0"+seconds;
        }
        String minutes = Integer.toString(min);
        if (minutes.length()==1){
            minutes="0"+minutes;
        }
        String hours = Integer.toString(hour);
        if (hours.length()==1){
            hours="0"+hours;
        }
        return hours +":"+ minutes +":"+ seconds.substring(0,6);
    }
}
