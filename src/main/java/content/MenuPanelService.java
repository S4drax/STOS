package content;

import config.AppProperties;
import config.LabelProperties;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.Label;
import org.hexworks.zircon.api.component.Panel;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.panel;

public class MenuPanelService {

    Panel header = panel()
            .withSize(66, 5)
            .withDecorations(box())
            .build();
    Label headerLabel = Components.label()
            .withText("                            ")
            .withPosition(21, 0)
            .build();
    Label headerLabel2 = Components.label()
            .withText("                            ")
            .withPosition(30, 1)
            .build();
    Label headerLabel3 = Components.label()
            .withText("                            ")
            .withPosition(28, 2)
            .build();

     public Panel headerSetup (LabelProperties labelProperties, AppProperties appProperties){
         headerLabel.setText(labelProperties.getWelcome());
         headerLabel2.setText(labelProperties.getVer() + appProperties.getVersion());
         headerLabel3.setText(labelProperties.getAccess());
         header.addComponent(headerLabel);
         header.addComponent(headerLabel2);
         header.addComponent(headerLabel3);
         return header;
     }

     public void setHeaderLabel3(String text){
         headerLabel3.setText(text);
     }
}
