package content;

import org.hexworks.zircon.api.component.Component;
import org.hexworks.zircon.api.component.Fragment;
import org.hexworks.zircon.api.component.TextArea;
import org.hexworks.zircon.api.component.VBox;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.header;
import static org.hexworks.zircon.api.Components.vbox;

public class LoginPanel implements Fragment {
        private VBox root;

        public LoginPanel(Size size, TextArea loginArea, TextArea passwordArea, Position position) {
            root = vbox()
                    .withSize(size)
                    .withPosition(position)
                    .withDecorations(box(BoxType.BASIC,"Login"))
                    .build();
            root.addComponent(loginArea);
            root.addComponent(header().withText("+Password+----------------"));
            root.addComponent(passwordArea);

        }

        public Component getRoot() {
            return root;
        }

}
