package screens;


import lombok.SneakyThrows;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.view.base.BaseView;
import org.jetbrains.annotations.NotNull;

public class HackingView extends BaseView {

    public HackingView(@NotNull TileGrid tileGrid, @NotNull ColorTheme theme) {
        super(tileGrid, theme);
    }

    @SneakyThrows
    @Override
    public void onDock() {
        Thread.sleep(100);
        System.out.println("Docking Hacking View.");
    }

    @SneakyThrows
    @Override
    public void onUndock() {
        Thread.sleep(100);
        System.out.println("Undocking Hacking View.");
    }
}