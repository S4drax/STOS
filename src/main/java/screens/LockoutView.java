package screens;

import lombok.SneakyThrows;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.view.base.BaseView;

public class LockoutView extends BaseView {


    public LockoutView(TileGrid tileGrid, ColorTheme theme) {
        super(tileGrid, theme);
    }


    @SneakyThrows
    @Override
    public void onDock() {
        Thread.sleep(100);
        System.out.println("Docking Lockout View.");
    }

    @SneakyThrows
    @Override
    public void onUndock() {
        Thread.sleep(100);
        System.out.println("Undocking Lockout View.");
    }
}

