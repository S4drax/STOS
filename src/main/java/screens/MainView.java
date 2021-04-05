package screens;

import lombok.SneakyThrows;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.view.base.BaseView;

public class MainView extends BaseView {


    public MainView(TileGrid tileGrid, ColorTheme theme) {
        super(tileGrid, theme);
    }


    public void setZeeTheme(ColorTheme theme){
        this.setTheme(theme);
    }

    @SneakyThrows
    @Override
    public void onDock() {
        Thread.sleep(100);
        System.out.println("Docking Main View.");
    }

    @SneakyThrows
    @Override
    public void onUndock() {
        Thread.sleep(100);
        System.out.println("Undocking Main View.");
    }
}

