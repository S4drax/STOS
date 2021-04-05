import commhandler.CommandHandler;
import commhandler.SoundBase;
import config.AppProperties;
import config.LabelProperties;
import content.*;
import dynamic.DynamicComponentService;
import hacking.HackingLogic;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import saving.Save;
import saving.SaveableDocumentsService;
import screens.HackingView;
import screens.LockoutView;
import screens.LoginView;
import screens.MainView;
import userbase.AccessLevelLoader;
import userbase.AccessMap;
import userbase.User;
import userbase.UserBaseLoader;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.hexworks.zircon.api.ComponentDecorations.box;
import static org.hexworks.zircon.api.Components.*;

public class Main {
    private static DynamicComponentService dcs = new DynamicComponentService();
    private static SaveableDocumentsService sds = new SaveableDocumentsService();
    private static CommandHandler commandHandler = new CommandHandler();
    private static HackingLogic hackingLogic = new HackingLogic();
    private static SoundBase soundBase = new SoundBase();
    private static Save save = new Save();
    private static UserBaseLoader ubs = new UserBaseLoader();
    private static AccessLevelLoader accessLevelLoader = new AccessLevelLoader();
    public static void main(String[] args) {
        AppProperties appProperties = new AppProperties();
        appProperties.loadProperties();
        LabelProperties labelProperties = new LabelProperties();
        labelProperties.loadProperties(appProperties.getLanguage());
        List<String> chosenDuds = new ArrayList<>();
        ScheduledExecutorService soundExecutor = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService backGround = Executors.newScheduledThreadPool(1);
        backGround.scheduleAtFixedRate(soundBase.getHum(), 0, 1, TimeUnit.SECONDS);
        TilesetResource tilesetResource = CP437TilesetResources.loadTilesetFromFilesystem(20, 20, "rex_paint_20x20_pl.png");
        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                        .withSize(Size.create(96, 54))
                        .withDefaultTileset(tilesetResource)
                        .fullScreen()
                        .build());
        Map<String, MenuElement> menuElementMap = new HashMap<>();
        Map<String, Folder> directory = dcs.initialise(menuElementMap, labelProperties);
        AccessMap accessMap = accessLevelLoader.loadElementAccessLevels(menuElementMap, directory);
        if(appProperties.getPassword().isEmpty()) {
            appProperties.setPassword("");
        }
        if(appProperties.getLanguage().isEmpty()){
            appProperties.setLanguage("PL");
        }
        MenuPanelService menuPanelService = new MenuPanelService();
        List<User> userBase = ubs.loadUsers();
        Map<String, DynamicBox> saveables = sds.initialiseSaveable(labelProperties);

        //LOGIN START
        VBox loginVbox = vbox()
                .withSize(Size.create(96, 54))
                .build();
        Panel loginPanel = panel()
                .withSize(Size.create(96, 54))
                .build();
        TextArea loginArea = Components.textArea()
                .withSize(26, 1)
                .withText("")
                .build();

        TextArea passwordArea = Components.textArea()
                .withSize(26, 1)
                .withText("")
                .build();
        loginPanel.addFragment(new LoginPanel(Size.create(28, 5), loginArea, passwordArea, Position.create(34, 25)));
        loginVbox.addComponent(loginPanel);

        /*INIT HACKING SCREEN*/
        VBox hackVbox = vbox()
                .withSize(Size.create(96, 54))
                .build();
        LogArea hackLog = Components.logArea()
                .withLogRowHistorySize(41)
                .withSize(18, 41)
                .withPosition(0, 0)
                .build();
        List<Label> hackLabelList = initHackList();
        hackingLogic.initHakcing(hackLabelList, appProperties.getPassword(), appProperties.getLanguage(), chosenDuds);

        TextArea hackTerm = Components.textArea()
                .withSize(18, 1)
                .withPosition(0, 0)
                .build();
        Panel top = panel()
                .withSize(Size.create(96, 10))
                .withDecorations(box(BoxType.DOUBLE))
                .build();
        hackVbox.addComponent(top);
        HBox bot = hbox()
                .withSize(Size.create(96, 44))
                .build();
        Label hackHeaderLabel = Components.label()
                .withText(labelProperties.getHackingtop())
                .withPosition(0, 0)
                .build();
        Label hackHeaderLabel2 = Components.label()
                .withText(labelProperties.getEnterpwd())
                .withPosition(0,1)
                .build();
        top.addComponent(hackHeaderLabel);
        top.addComponent(hackHeaderLabel2);
        Label hackAttempts = Components.label()
                .withText(labelProperties.getAttemptsleft())
                .withPosition(0,6)
                .build();
        top.addComponent(hackAttempts);
        VBox botLeft = vbox()
                .withSize(Size.create(76, 44))
                .withDecorations(box(BoxType.DOUBLE))
                .build();
        VBox botRight = vbox()
                .withSize(Size.create(20, 44))
                .withDecorations(box(BoxType.DOUBLE))
                .build();
        botRight.addComponent(hackLog);
        botRight.addComponent(hackTerm);
        bot.addComponent(botLeft);
        bot.addComponent(botRight);
        for (Label l:hackLabelList) {
            botLeft.addComponent(l);
        }
        hackVbox.addComponent(bot);

        //lockout
        Panel lockoutPanel = Components.panel()
                .withSize(Size.create(96, 54))
                .build();
        lockoutPanel.addFragment(new LockoutPanel());

        //MAIN
        VBox vBox = vbox()
                .withSize(Size.create(96, 54))
                .build();
        HBox headerHbox = hbox()
                .withSize(96,5)
                .build();
        Panel header = menuPanelService.headerSetup(labelProperties, appProperties);
        //Header Panel
        headerHbox.addComponent(header);
        Panel header2 = panel()
                .withSize(30,5)
                .withDecorations(box())
                .build();
        headerHbox.addComponent(header2);
        Clock clock = Clock.system(ZoneId.systemDefault());
        Label clockLab = Components.label()
                .withText(clock.instant().atOffset(ZoneOffset.ofHours(1)).toString().substring(11,19))
                .build();
        Runnable tickTock = new Runnable(){
            public void run(){
                clockLab.setText(clock.instant().atOffset(ZoneOffset.ofHours(1)).toString().substring(11,19));
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(tickTock,0,1, TimeUnit.SECONDS);

        header2.addComponent(clockLab);
        vBox.addComponent(headerHbox);
        //Text Panel
        Panel mainText = panel()
                .withSize(96, 46)
                .withDecorations(box())
                .build();

        vBox.addComponent(mainText);
        //Terminal Panel
        Panel terminal = panel()
                .withSize(96, 3)
                .withDecorations(box())
                .build();
        vBox.addComponent(terminal);
        TextArea term = Components.textArea()
                .withSize(terminal.getSize().getWidth() - 2, 1)
                .withText("")
                .build();
        terminal.addComponent(term);

        //Views, handler and the rest
        MainView mainView = new MainView(tileGrid, appProperties.getTheme());
        mainView.getScreen().addComponent(vBox);
        HackingView hackingView = new HackingView(tileGrid, appProperties.getTheme());
        hackingView.getScreen().addComponent(hackVbox);
        LockoutView lockoutView = new LockoutView(tileGrid, appProperties.getTheme());
        lockoutView.getScreen().addComponent(lockoutPanel);
        LoginView loginView = new LoginView(tileGrid, appProperties.getTheme());
        loginView.getScreen().addComponent(loginVbox);
        loginView.dock();
        soundExecutor.schedule(soundBase.getSLaunch(),0,TimeUnit.SECONDS);
        commandHandler.setCurrentWindow("login");
        commandHandler.initTermListener(appProperties, term, tileGrid, mainText, mainView,
                loginArea, passwordArea, loginView, mainView, hackingView, hackTerm, hackLog, hackAttempts,
                hackLabelList, appProperties.getPassword(), lockoutView, chosenDuds, soundExecutor, labelProperties, saveables, userBase, menuPanelService,
                accessMap);
        loginArea.requestFocus();
    }

    private static List<Label> initHackList (){
        List<Label> hackList = new ArrayList<>();
        for(int i=0; i<42; i++){
            hackList.add(Components.label().withSize(74,1).withText("").build());
        }
        return hackList;
    }



}