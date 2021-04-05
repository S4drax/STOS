package content;

import org.hexworks.zircon.api.component.Component;
import org.hexworks.zircon.api.component.Fragment;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.header;
import static org.hexworks.zircon.api.Components.vbox;

public class LockoutPanel implements Fragment {
        private VBox root;

        public LockoutPanel() {
            root = vbox()
                    .withSize(Size.create(36,5))
                    .withPosition(Position.create(30,20))
                    .withDecorations(box(BoxType.BASIC))
                    .build();
            root.addComponent(header().withText("     You have been locked out     ").build());
            root.addComponent(header().withText("").build());
            root.addComponent(header().withText("Please contact your administrator.").build());
        }

        public Component getRoot() {
            return root;
        }

}
