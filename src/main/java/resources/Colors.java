package resources;

import lombok.Getter;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.component.ColorTheme;

@SuppressWarnings("unused")
public enum Colors {

    CAPITOL(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#57c183"))
            .withPrimaryForegroundColor(TileColor.fromString("#57c183"))
            .withSecondaryForegroundColor(TileColor.fromString("#57c183"))
            .withPrimaryBackgroundColor(TileColor.fromString("#103621"))
            .withSecondaryBackgroundColor(TileColor.fromString("#103621"))
            .build()),

    MOJAVE(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#a48e54"))
            .withPrimaryForegroundColor(TileColor.fromString("#a48e54"))
            .withSecondaryForegroundColor(TileColor.fromString("#a48e54"))
            .withPrimaryBackgroundColor(TileColor.fromString("#000000"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000000"))
            .build()),

    ZGON(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#ffffff"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#ffffff"))
            .withPrimaryBackgroundColor(TileColor.fromString("#283891"))
            .withSecondaryBackgroundColor(TileColor.fromString("#283891"))
            .build()),

    SHPERY(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#ffffff"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#ffffff"))
            .withPrimaryBackgroundColor(TileColor.fromString("#000000"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000000"))
            .build()),

    CYFRONI(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#000000"))
            .withPrimaryForegroundColor(TileColor.fromString("#000000"))
            .withSecondaryForegroundColor(TileColor.fromString("#000000"))
            .withPrimaryBackgroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryBackgroundColor(TileColor.fromString("#ffffff"))
            .build()),

    ARYSTOKRACJA(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#540113"))
            .withPrimaryForegroundColor(TileColor.fromString("#aaaca9"))
            .withSecondaryForegroundColor(TileColor.fromString("#aaaca9"))
            .withPrimaryBackgroundColor(TileColor.fromString("#540113"))
            .withSecondaryBackgroundColor(TileColor.fromString("#540113"))
            .build()),

    KOSMODROM(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#6e91c1"))
            .withPrimaryForegroundColor(TileColor.fromString("#000000"))
            .withSecondaryForegroundColor(TileColor.fromString("#000000"))
            .withPrimaryBackgroundColor(TileColor.fromString("#6e91c1"))
            .withSecondaryBackgroundColor(TileColor.fromString("#6e91c1"))
            .build()),

    SYSTEM(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#57c0a1"))
            .withPrimaryForegroundColor(TileColor.fromString("#57c0a1"))
            .withSecondaryForegroundColor(TileColor.fromString("#57c0a1"))
            .withPrimaryBackgroundColor(TileColor.fromString("#004938"))
            .withSecondaryBackgroundColor(TileColor.fromString("#004938"))
            .build()),

    AMMO(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#eeffcc"))
            .withPrimaryForegroundColor(TileColor.fromString("#bedc7f"))
            .withSecondaryForegroundColor(TileColor.fromString("#4d8061"))
            .withPrimaryBackgroundColor(TileColor.fromString("#112318"))
            .withSecondaryBackgroundColor(TileColor.fromString("#040c06"))
            .build()),

    CYBERPUNK(ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#61D6C4"))
            .withPrimaryForegroundColor(TileColor.fromString("#71918C"))
            .withSecondaryForegroundColor(TileColor.fromString("#3D615F"))
            .withPrimaryBackgroundColor(TileColor.fromString("#25343B"))
            .withSecondaryBackgroundColor(TileColor.fromString("#212429"))
            .build());

    Colors(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    @Getter
    private final ColorTheme colorTheme;

}
