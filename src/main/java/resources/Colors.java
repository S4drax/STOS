package resources;


import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.component.ColorTheme;


public class Colors {

    ColorTheme capitol = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#57c183"))
            .withPrimaryForegroundColor(TileColor.fromString("#57c183"))
            .withSecondaryForegroundColor(TileColor.fromString("#57c183"))
            .withPrimaryBackgroundColor(TileColor.fromString("#103621"))
            .withSecondaryBackgroundColor(TileColor.fromString("#103621"))
            .build();

    ColorTheme mojave = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#a48e54"))
            .withPrimaryForegroundColor(TileColor.fromString("#a48e54"))
            .withSecondaryForegroundColor(TileColor.fromString("#a48e54"))
            .withPrimaryBackgroundColor(TileColor.fromString("#000000"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000000"))
            .build();

    ColorTheme zgon = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#ffffff"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#ffffff"))
            .withPrimaryBackgroundColor(TileColor.fromString("#283891"))
            .withSecondaryBackgroundColor(TileColor.fromString("#283891"))
            .build();

    ColorTheme shpery = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#ffffff"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#ffffff"))
            .withPrimaryBackgroundColor(TileColor.fromString("#000000"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000000"))
            .build();

    ColorTheme cyfroni = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#000000"))
            .withPrimaryForegroundColor(TileColor.fromString("#000000"))
            .withSecondaryForegroundColor(TileColor.fromString("#000000"))
            .withPrimaryBackgroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryBackgroundColor(TileColor.fromString("#ffffff"))
            .build();

    ColorTheme arystokracja = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#540113"))
            .withPrimaryForegroundColor(TileColor.fromString("#aaaca9"))
            .withSecondaryForegroundColor(TileColor.fromString("#aaaca9"))
            .withPrimaryBackgroundColor(TileColor.fromString("#540113"))
            .withSecondaryBackgroundColor(TileColor.fromString("#540113"))
            .build();

    ColorTheme kosmodrom = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#6e91c1"))
            .withPrimaryForegroundColor(TileColor.fromString("#000000"))
            .withSecondaryForegroundColor(TileColor.fromString("#000000"))
            .withPrimaryBackgroundColor(TileColor.fromString("#6e91c1"))
            .withSecondaryBackgroundColor(TileColor.fromString("#6e91c1"))
            .build();

    ColorTheme system = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#57c0a1"))
            .withPrimaryForegroundColor(TileColor.fromString("#57c0a1"))
            .withSecondaryForegroundColor(TileColor.fromString("#57c0a1"))
            .withPrimaryBackgroundColor(TileColor.fromString("#004938"))
            .withSecondaryBackgroundColor(TileColor.fromString("#004938"))
            .build();

    ColorTheme AMMO = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#eeffcc"))
            .withPrimaryForegroundColor(TileColor.fromString("#bedc7f"))
            .withSecondaryForegroundColor(TileColor.fromString("#4d8061"))
            .withPrimaryBackgroundColor(TileColor.fromString("#112318"))
            .withSecondaryBackgroundColor(TileColor.fromString("#040c06"))
            .build();

    ColorTheme CYBERPUNK = ColorThemes.newBuilder()
            .withAccentColor(TileColor.fromString("#61D6C4"))
            .withPrimaryForegroundColor(TileColor.fromString("#71918C"))
            .withSecondaryForegroundColor(TileColor.fromString("#3D615F"))
            .withPrimaryBackgroundColor(TileColor.fromString("#25343B"))
            .withSecondaryBackgroundColor(TileColor.fromString("#212429"))
            .build();




    public ColorTheme getCapitol() {
        return capitol;
    }

    public ColorTheme getMojave() {
        return mojave;
    }

    public ColorTheme getZgon() {
        return zgon;
    }

    public ColorTheme getShpery() {
        return shpery;
    }

    public ColorTheme getCyfroni() {
        return cyfroni;
    }

    public ColorTheme getArystokracja() {
        return arystokracja;
    }

    public ColorTheme getKosmodrom() {
        return kosmodrom;
    }

    public ColorTheme getSystem() {
        return system;
    }

    public ColorTheme getAMMO() {
        return AMMO;
    }

    public ColorTheme getCYBERPUNK() {
        return CYBERPUNK;
    }


}
