package content;


import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;

import java.util.ArrayList;
import java.util.List;

import static org.hexworks.zircon.api.Components.vbox;

public class DynamicBox implements Fragment {
    private String name;
    private VBox root;
    private Integer id;
    private String back;
    private Label progLab;
    private String style;
    private String text;
    private int level;
    private List<TextArea> saveTA;

    public DynamicBox(Size size, Component component, Position position, Integer newId, String newBack, String newName, List<TextArea> saveTA, String styl, String txt, int al) {
        root = vbox()
                .withSize(size)
                .withPosition(position)
                .build();
        root.addComponent(component);
        id = newId;
        back = newBack;
        name = newName;
        if(saveTA!=null){
            this.saveTA = saveTA;
        }
        style = styl;
        text = txt;
        level = al;
    }

    public VBox getRoot() {
        return root;
    }

    public Integer getId(){
        return id;
    }

    public String getBack(){
        return back;
    }

    public String getName(){
        return name;
    }

    public void setProgLab(Label lab){
        this.progLab=lab;
    }

    public Label getProgLab() {
        return progLab;
    }

    public String getStyle(){
        return style;
    }

    public String getText(){
        return text;
    }

    public int getLevel() {
        return level;
    }

    public List<TextArea> getSaveTA() {
        if (saveTA!=null){
            return saveTA;
        } else {
            return new ArrayList<>();
        }
    }

}
