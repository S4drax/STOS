package userbase;

import content.DynamicBox;
import content.Folder;
import content.MenuElement;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessLevelLoader {
    public AccessMap loadElementAccessLevels(Map<String, MenuElement> menuElementMap, Map<String, Folder> directory){
        Map<String, Map<String,MenuElement>> accessElementMap = new HashMap<>();
        Map<String, Map<String, Folder>> accessFolderMap = new HashMap<>();
        Map<String, Folder> tmpDir;
        //Access Level 1
        accessElementMap.put("1", menuElementMap);
        accessFolderMap.put("1", directory);
        //Access Level 2
        tmpDir=buildDirectory(2,directory);
        accessElementMap.put("2", buildElementmap(tmpDir));
        accessFolderMap.put("2", tmpDir);
        //Access Level 3
        tmpDir=buildDirectory(3,directory);
        accessElementMap.put("3", buildElementmap(tmpDir));
        accessFolderMap.put("3", tmpDir);
        //Access Level 4
        tmpDir=buildDirectory(4,directory);
        accessElementMap.put("4", buildElementmap(tmpDir));
        accessFolderMap.put("4", tmpDir);
        //Access Level 5
        tmpDir=buildDirectory(5,directory);
        accessElementMap.put("5", buildElementmap(tmpDir));
        accessFolderMap.put("5", tmpDir);

        return AccessMap.builder()
                .accessElementMap(accessElementMap)
                .accessFolderMap(accessFolderMap)
                .build();
    }

    private Map<String, Folder> buildDirectory(int level, Map<String, Folder> directory) {
        Map<String, Folder> tmpDir = new HashMap<>();
        boolean is = false;
        for (Folder f : directory.values()) {
            Folder fol = Folder.builder().name(f.getName()).build();
            List<DynamicBox> list = new ArrayList<>();
            for(DynamicBox d : f.getDocuments()){
                if(d.getLevel()>=level){
                    list.add(d);
                    is=true;
                }
            }
            if(is){
                fol.setDocuments(list);
                tmpDir.put(f.getName(),fol);
            }
        }
        return tmpDir;
    }

    private Map<String,MenuElement> buildElementmap(Map<String,Folder> directory){
        Map<String,MenuElement> elementMap = new HashMap<>();
        createMainMenu(elementMap, directory);
        for (Folder f: directory.values()) {
            createSubMenu(f.getName(), f, elementMap);
        }
        return elementMap;
    }

    private void createMainMenu(Map<String, MenuElement> menuElementMap, Map<String,Folder> directory) {
        List<String> menuElements = new ArrayList<>();
        for (Map.Entry<String, Folder> folder : directory.entrySet()) {
            menuElements.add(folder.getKey());
        }
        menuElementMap.put("home", new MenuElement(Size.create(94,44), Position.create(0,0),menuElements));
    }
    private void createSubMenu(String name, Folder folder, Map<String, MenuElement> menuElementMap){
        List<String> subMenuElements = new ArrayList<>();
        for (DynamicBox entry : folder.getDocuments()){
            subMenuElements.add(entry.getId().toString()+") "+entry.getName());
        }
        menuElementMap.put(name, new MenuElement(Size.create(94,44),Position.create(0,0), subMenuElements));
    }
}
