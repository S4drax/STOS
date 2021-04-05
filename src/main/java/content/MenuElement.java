package content;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.Component;
import org.hexworks.zircon.api.component.Fragment;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;

import java.util.List;

import static org.hexworks.zircon.api.Components.vbox;

public class MenuElement implements Fragment {
    private VBox root;


    public MenuElement(Size size, Position position, List<String> commands) {
        root = vbox()
                .withSize(size)
                .withPosition(position)
                .build();
        if(commands!=null || !commands.isEmpty()) {
            for (String text : commands) {
                root.addComponent(Components.header().withText(text).build());
                root.addComponent(Components.header().withText("").build());
            }
        }
    }

    public Component getRoot() {
        return root;
    }

    public void addComponent(String text){
        root.addComponent(Components.header().withText(text).build());
        root.addComponent(Components.header().withText("").build());
    }
}
