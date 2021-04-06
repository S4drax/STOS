package content;

import config.LabelProperties;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Size;


import static org.hexworks.zircon.api.Components.vbox;

public class Help implements Fragment {

    private VBox root;


    public Help(Size size, LabelProperties lp) {
        root = vbox()
                .withSize(size)
                .build();
        TextBox textBox = Components.textBox(size.getWidth()-2)
                .addHeader(lp.getHelp1())
                .addHeader(lp.getHelp2())
                .addHeader(lp.getHelp3())
                .addHeader(lp.getHelp4())
                .addHeader(lp.getHelp5())
                .addHeader(lp.getHelp6())
                .addHeader(lp.getHelp7())
                .addHeader(lp.getHelp8())
                .addHeader(lp.getHelp9())
                .build();
        root.addComponent(textBox);
    }




    public Component getRoot() {
        return root;
    }
}
