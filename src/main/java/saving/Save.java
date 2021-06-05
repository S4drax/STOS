package saving;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import config.LabelProperties;
import content.DynamicBox;
import content.Folder;
import content.MenuElement;
import dynamic.DynamicComponentService;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.builder.component.TextBoxBuilder;
import org.hexworks.zircon.api.component.TextArea;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import translator.TranslatorService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Save {
    private DynamicComponentService dcs = new DynamicComponentService();
    private String text ="";
    private TranslatorService translatorService = new TranslatorService();
    public void save (Map<String, Folder> folderMap, String folder, String type, String name, List<TextArea> lta, LabelProperties lp, Map<String, MenuElement> menuElementMap, int level){
        String style = "";
        if(folderMap.get(folder)==null){
            folderMap.put(folder, Folder.builder().documents(new ArrayList<>()).name(folder).build());
        }
        if(menuElementMap.get(folder)==null){
            menuElementMap.put(folder, new MenuElement(Size.create(94,44),Position.create(0,0),new ArrayList<>()));
            menuElementMap.get("home").addComponent(folder);
        }
        if(!lta.isEmpty()) {
            int menu = folderMap.get(folder).getDocuments().size() + 1;
            VBox vBox = Components.vbox().withSize(Size.create(94, 44)).build();

            switch (type) {
                case "doc_compact":
                    style="doc_compact";
                    initDoc(vBox, lta);
                    break;
                case "med_chart":
                    style="med_chart";
                    vBox=initMedical(lta, lp);
                    break;
                case "schematic":
                    style="schematic";
                    vBox=initSchematic(lta, lp);
                    break;
                case "status_report_c":
                    style="status_report_c";
                    vBox=initStatus(lta, lp);
                    break;
                default:
                    style="doc_compact";
                    initDoc(vBox, lta);
                    break;
            }
            folderMap.get(folder).getDocuments().add(new DynamicBox(Size.create(94, 44), vBox, Position.create(0, 0), menu, folder, name, null,style,text, level));
            menuElementMap.get(folder).addComponent(menu+") "+name);
            saveJson(folderMap);
        } else {
            System.out.println("something went wrong with TextArea list");
        }

    }

    public void saveJson(Map<String, Folder> folderMap){
        JSONArray folders = new JSONArray();
        //for each folder map it to JSONObject
        for (Folder f: folderMap.values()) {
            JSONObject foldUp = new JSONObject();
            JSONObject folder = new JSONObject();
            folder.put("fname", f.getName());
            JSONArray fragments = new JSONArray();
            for (DynamicBox d: f.getDocuments()) {
                JSONObject fragUp = new JSONObject();
                JSONObject doc = new JSONObject();
                JSONObject textBox = new JSONObject();
                textBox.put("level", d.getLevel());
                textBox.put("positionX", 0);
                textBox.put("positionY", 0);
                textBox.put("style", d.getStyle());
                textBox.put("addHeader", translatorService.translateFromTile(d.getText()));
                doc.put("name", translatorService.translateFromTile(d.getName()));
                doc.put("textBox", textBox);
                fragUp.put("fragment", doc);
                fragments.add(fragUp);
            }
            folder.put("fragments", fragments);
            foldUp.put("folder", folder);
            folders.add(foldUp);
        }
        //Write JSON file
        try (Writer file = new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir")+"/directory.json"), StandardCharsets.UTF_8)) {
            //We can write any JSONArray or JSONObject instance to the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(folders.toJSONString());
            String prettyJsonString = gson.toJson(je);

            file.write(prettyJsonString);
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDoc(VBox vBox, List<TextArea> lta){
        TextBoxBuilder tb = Components.textBox(94)
                .withPosition(0,0);
        text=lta.get(0).getText();
        dcs.docCompact(text, tb);
        vBox.addComponent(tb.build());
    }

    private VBox initMedical(List<TextArea> lta, LabelProperties lp){
        text=lta.get(0).getText();
        text=text+"%"+lta.get(1).getText();
        text=text+"%"+lta.get(2).getText();
        text=text+"%"+lta.get(3).getText();
        text=text+"%"+lta.get(4).getText();
        text=text+"%"+lta.get(5).getText();
        text=text+"%"+lta.get(6).getText();
        return dcs.medicalChart(text, lp);
    }

    private VBox initSchematic(List<TextArea> lta, LabelProperties lp){
        text=lta.get(0).getText();
        text=text+"%"+lta.get(1).getText();
        text=text+"%"+lta.get(2).getText();
        text=text+"%"+lta.get(3).getText();
        text=text+"%"+lta.get(4).getText();
        return dcs.schematic(text, lp);
    }

    private VBox initStatus(List<TextArea> lta, LabelProperties lp){
        text=lta.get(0).getText();
        text=text.replace(lp.getReportby(), "");
        text=text.replace(lp.getFromday(), "");
        text=text.replace(" ", "%");
        text=text+"%"+lta.get(1).getText();
        return dcs.statusReportCompact(text, lp);
    }
}
