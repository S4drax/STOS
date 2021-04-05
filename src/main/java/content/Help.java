package content;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Size;


import static org.hexworks.zircon.api.Components.vbox;

public class Help implements Fragment {

    private VBox root;


    public Help(Size size) {
        root = vbox()
                .withSize(size)
                .build();
        TextBox textBox = Components.textBox(size.getWidth()-2)
                .addHeader("Witaj w systemie STOS, zaprojektowanego do łatwej obsługi terminali.")
                .addHeader("Aktualnie dostępne komendy:")
                .addHeader("help/HELP - przywołuje tą informację")
                .addHeader("stos/STOS - przywołuje zacne logo 3d")
                .addHeader("clear/CLEAR - czyści ekran")
                .addHeader("back/BACK - wraca do głównego menu")
                .addHeader("add/Add (-m/-r/-s) - otwiera pole zapisu nowego dokumentu")
                .addHeader("exit/EXIT - wychodzi z aplikacji")
                .build();
        root.addComponent(textBox);
    }




    public Component getRoot() {
        return root;
    }
}
