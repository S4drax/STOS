package saving;

import config.LabelProperties;
import content.DynamicBox;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.HBox;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.TextArea;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.*;

public class SaveableDocumentsService {

    public Map<String, DynamicBox> initialiseSaveable(LabelProperties labelProperties) {
        Map<String, DynamicBox> saveables = new HashMap<>();
        initDoc(saveables);
        initMedical(saveables, labelProperties);
        initStatus(saveables, labelProperties);
        initSchematic(saveables, labelProperties);


        return saveables;
    }


    private void initDoc(Map<String, DynamicBox> saveables) {
        VBox vBox = Components.vbox().withSize(92, 42).withDecorations(box(BoxType.SINGLE)).build();
        TextArea text = Components.textArea().withSize(90, 40).build();
        vBox.addComponent(text);
        List<TextArea> lta = new ArrayList<>();
        lta.add(text);
        saveables.put("doc", new DynamicBox(Size.create(94, 44), vBox, Position.create(0, 0), 1, "home", "saveable_doc", lta, null, null, 0));
    }

    private void initMedical(Map<String, DynamicBox> saveables, LabelProperties lp) {
        List<TextArea> lta = new ArrayList<>();
        VBox chart = vbox().withSize(94, 44).build();
        //line 1: Name and Admission date
        HBox line1 = hbox().withSize(94, 3).build();
        //name
        TextArea nameArea = Components.textArea().withSize(47, 3).withDecorations(box(BoxType.SINGLE,lp.getName())).build();
        lta.add(nameArea);
        line1.addComponent(nameArea);
        //admission date
        TextArea date = Components.textArea().withSize(47, 3).withDecorations(box(BoxType.SINGLE, lp.getAdmidate())).build();
        line1.addComponent(date);
        lta.add(date);
        chart.addComponent(line1);
        //line 2: Gender and patient status
        HBox line2 = hbox().withSize(94, 3).build();
        //gender
        TextArea gender = Components.textArea().withSize(47, 3).withDecorations(box(BoxType.SINGLE,lp.getGender())).build();
        lta.add(gender);
        line2.addComponent(gender);
        //status
        TextArea status = Components.textArea().withSize(47, 3).withDecorations(box(BoxType.SINGLE,"Status:")).build();
        lta.add(status);
        line2.addComponent(status);
        chart.addComponent(line2);
        //caretaker
        TextArea caretaker = Components.textArea().withSize(94, 3).withDecorations(box(BoxType.SINGLE,lp.getCaretaker())).build();
        lta.add(caretaker);
        chart.addComponent(caretaker);
        //cause of visit block
        Panel cause = panel()
                .withSize(94, 5)
                .withDecorations(box(BoxType.SINGLE, lp.getCauseov()))
                .build();
        TextArea causeArea = Components.textArea().withSize(92, 3).build();
        lta.add(causeArea);
        cause.addComponent(causeArea);
        chart.addComponent(cause);
        //threatment block
        Panel threatment = panel()
                .withSize(94, 30)
                .withDecorations(box(BoxType.SINGLE, lp.getTreatment()))
                .build();
        TextArea threatmentArea = Components.textArea().withSize(92, 28).build();
        lta.add(threatmentArea);
        threatment.addComponent(threatmentArea);
        chart.addComponent(threatment);
        saveables.put("med", new DynamicBox(Size.create(94, 44), chart, Position.create(0, 0), 2, "home", "saveable_med", lta, null, null, 0));

    }

    private void initStatus(Map<String, DynamicBox> saveables, LabelProperties lp) {
        VBox report = vbox().withSize(94, 44).build();
        List<TextArea> lta = new ArrayList<>();
        //Header
        TextArea header = Components.textArea().withSize(92, 3).withText(lp.getReportby() + "   " + lp.getFromday()).withDecorations(box(BoxType.SINGLE)).build();
        lta.add(header);
        report.addComponent(header);
        //rest of the text
        TextArea textArea = Components.textArea().withSize(94, 41).withText("").withDecorations(box(BoxType.SINGLE)).build();
        lta.add(textArea);
        report.addComponent(textArea);
        saveables.put("report", new DynamicBox(Size.create(94, 44), report, Position.create(0, 0), 3, "home", "saveable_report", lta, null, null, 0));
    }

    private void initSchematic(Map<String, DynamicBox> saveables, LabelProperties lp) {
        List<TextArea> lta = new ArrayList<>();
        VBox schematic = vbox().withSize(94, 44).build();
        //line 1: Name and Admission date
        HBox line1 = hbox().withSize(94, 3).build();
        //name
        TextArea name = Components.textArea().withSize(47, 3).withDecorations(box(BoxType.SINGLE,lp.getRecname())).build();

        line1.addComponent(name);
        //time
        TextArea date = Components.textArea().withSize(47, 3).withDecorations(box(BoxType.SINGLE,lp.getRectime())).build();
        line1.addComponent(date);
        schematic.addComponent(line1);
        //materials
        Panel materials = panel()
                .withSize(94, 10)
                .withDecorations(box(BoxType.SINGLE,lp.getRecmaterials()))
                .build();
        TextArea mats = Components.textArea().withSize(92, 8).build();
        materials.addComponent(mats);
        schematic.addComponent(materials);
        //results
        Panel cause = panel()
                .withSize(94, 8)
                .withDecorations(box(BoxType.SINGLE,lp.getRecresult()))
                .build();
        TextArea res = Components.textArea().withSize(92, 6).build();
        cause.addComponent(res);
        schematic.addComponent(cause);
        //lore
        Panel lore = panel()
                .withSize(94, 23)
                .withDecorations(box(BoxType.SINGLE,lp.getRecdescr()))
                .build();
        TextArea loreta = Components.textArea().withSize(92, 21).build();
        lore.addComponent(loreta);
        schematic.addComponent(lore);
        saveables.put("schematic", new DynamicBox(Size.create(94, 44), schematic, Position.create(0, 0), 4, "home", "saveable_schematic", lta, null, null, 0));
    }

}
