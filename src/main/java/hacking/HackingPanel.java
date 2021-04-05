package hacking;

import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.game.GameComponent;
import org.hexworks.zircon.api.graphics.BoxType;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.hbox;
import static org.hexworks.zircon.api.Components.vbox;

public class HackingPanel implements Fragment {
    private VBox root;

    public HackingPanel(Size size, Component passArea, Position position) {
        root = vbox()
                .withSize(size)
                .withPosition(position)
                .build();
        HBox top = hbox()
                .withSize(size.getWidth(),10)
                .withPosition(0,0)
                .withDecorations(box(BoxType.DOUBLE))
                .build();
        root.addComponent(top);
        HBox bot = hbox()
                .withSize(size.getWidth(), size.getHeight()-10)
                .build();

        VBox botLeft = vbox()
                .withSize(size.getWidth()-20, size.getHeight()-10)
                .withDecorations(box(BoxType.DOUBLE))
                .build();
        VBox botRight = vbox()
                .withSize(20, size.getHeight()-10)
                .withDecorations(box(BoxType.DOUBLE))
                .build();
        botLeft.addComponent(passArea);
        bot.addComponent(botLeft);
        bot.addComponent(botRight);
        root.addComponent(bot);


    }

    public Component getRoot() {
        return root;
    }

}

