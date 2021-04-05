package content;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;

import static org.hexworks.zircon.api.Components.vbox;

public class TestArea implements Fragment {
    private VBox root;
    Label label0 = Components.label()
            .withText(" ________  _________  ________  ________")
            .withPosition(Position.create(0,0))
            .build();
    Label label1 = Components.label()
            .withText("|\\   ____\\|\\___   ___\\\\   __  \\|\\   ____\\  ")
            .withPosition(Position.create(0,1))
            .build();
    Label label2 = Components.label()
            .withText("\\ \\  \\___|\\|___ \\  \\_\\ \\  \\|\\  \\ \\  \\___|_ ")
            .withPosition(Position.create(0,2))
            .build();
    Label label3 = Components.label()
            .withText(" \\ \\_____  \\   \\ \\  \\ \\ \\  \\\\\\  \\ \\_____  \\ ")
            .withPosition(Position.create(0,3))
            .build();
    Label label4 = Components.label()
            .withText("  \\|____|\\  \\   \\ \\  \\ \\ \\  \\\\\\  \\|____|\\  \\ ")
            .withPosition(Position.create(0,4))
            .build();
    Label label5 = Components.label()
            .withText("    ____\\_\\  \\   \\ \\__\\ \\ \\_______\\____\\_\\  \\  ")
            .withPosition(Position.create(0,5))
            .build();
    Label label6 = Components.label()
            .withText("   |\\_________\\   \\|__|  \\|_______|\\_________\\  ")
            .withPosition(Position.create(0,6))
            .build();
    Label label7 = Components.label()
            .withText("   \\|_________|                   \\|_________|    ")
            .withPosition(Position.create(0,7))
            .build();

    public TestArea (Size size) {
            root = vbox()
                    .withSize(size)
                    .build();
            root.addComponent(label0);
            root.addComponent(label1);
            root.addComponent(label2);
            root.addComponent(label3);
            root.addComponent(label4);
            root.addComponent(label5);
            root.addComponent(label6);
            root.addComponent(label7);
    }

    public Component getRoot() {
        return root;
    }
}
