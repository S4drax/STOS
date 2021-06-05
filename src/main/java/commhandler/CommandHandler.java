package commhandler;

import config.AppProperties;
import config.LabelProperties;
import content.*;
import dynamic.DynamicComponentService;
import hacking.HackingLogic;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.uievent.KeyCode;
import org.hexworks.zircon.api.uievent.KeyboardEvent;
import org.hexworks.zircon.api.uievent.KeyboardEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.hexworks.zircon.api.view.base.BaseView;
import saving.Save;
import screens.HackingView;
import screens.LockoutView;
import screens.LoginView;
import screens.MainView;
import userbase.AccessMap;
import userbase.User;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CommandHandler {
    private String currentlyViewed = "home";
    private String backState = "home";
    private String currentWindow = "";
    private static DynamicComponentService dynamicComponentService = new DynamicComponentService();
    private HackingLogic hackingLogic = new HackingLogic();
    private SoundBase soundBase = new SoundBase();
    private ScheduledFuture sched = null;
    private Label prog = null;
    private Save save = new Save();
    private String termcmd = "";
    private String fold = "";
    private String nam = "";
    private int level = 5;
    private User loggedUser = User.builder().accessLevel(5).build();

    public void initTermListener(AppProperties appProperties, TextArea term, TileGrid tileGrid, Panel mainText, BaseView screen, TextArea login, TextArea pswd, LoginView loginView, MainView mainView, HackingView hackingView, TextArea hackTerm, LogArea hackLog, Label hackAttempts, List<Label> hackLabels, String password, LockoutView lockoutView, List<String> chosenDuds, ScheduledExecutorService soundExecutor, LabelProperties labelProperties, Map<String, DynamicBox> saveables, List<User> userBase, MenuPanelService menuPanelService, AccessMap accessMap) {
        tileGrid.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED, ((event, phase) ->
                checkConditions(appProperties, event, term, mainText, accessMap.getAccessFolderMap().get(String.valueOf(loggedUser.getAccessLevel())), screen, accessMap.getAccessElementMap().get(String.valueOf(loggedUser.getAccessLevel())), login, pswd, loginView, mainView, hackingView, hackTerm, hackLog, hackAttempts, hackLabels, password, lockoutView, chosenDuds, soundExecutor, labelProperties, saveables, userBase, menuPanelService, accessMap)
        ));
    }

    private UIEventResponse checkConditions(AppProperties appProperties, KeyboardEvent event, TextArea term, Panel mainText, Map<String, Folder> folderMap, BaseView screen, Map<String, MenuElement> menuElementMap, TextArea login, TextArea pswd, LoginView loginView, MainView mainView, HackingView hackingView, TextArea hackTerm, LogArea hackLog, Label hackAttempts, List<Label> hackLabels, String password, LockoutView lockoutView, List<String> chosenDuds, ScheduledExecutorService soundExecutor, LabelProperties labelProperties, Map<String, DynamicBox> saveables, List<User> userBase, MenuPanelService menuPanelService, AccessMap accessMap) {
        switch (currentWindow) {
            case "main":
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (term.getText().contains("\r")) {
                        term.setText(term.getText().replace("\r", ""));
                    }
                    if (term.getText().contains("\n")) {
                        term.setText(term.getText().replace("\n", ""));
                    }
                    if (term.getText().equalsIgnoreCase("stos")) {
                        mainText.clear();
                        mainText.addFragment(new TestArea(Size.create(94, 44)));
                    }
                    if (term.getText().equalsIgnoreCase("help")) {
                        mainText.clear();
                        mainText.addFragment(new Help(Size.create(94, 44),labelProperties));
                    }
                    if (term.getText().equalsIgnoreCase("clear")) {
                        mainText.clear();
                    }
                    for (Map.Entry<String, Folder> folder : folderMap.entrySet()) {
                        for (DynamicBox entry : folder.getValue().getDocuments()) {
                            if ((term.getText().equalsIgnoreCase(entry.getName()) || term.getText().equalsIgnoreCase(entry.getId().toString())) && folder.getKey().equals(currentlyViewed)) {
                                mainText.clear();
                                if (entry.getRoot().getAbsolutePosition().getX() != 0 || entry.getRoot().getAbsolutePosition().getY() != 0)
                                    entry.getRoot().moveTo(Position.create(0, 0));
                                mainText.addFragment(entry);
                                backState = currentlyViewed;
                                currentlyViewed = entry.getName();
                            }
                        }
                    }
                    for (Map.Entry<String, MenuElement> entry : menuElementMap.entrySet()) {
                        if ((term.getText().equalsIgnoreCase(entry.getKey())) && currentlyViewed.equals("home")) {
                            mainText.clear();
                            if (entry.getValue().getRoot().getAbsolutePosition().getX() != 0 || entry.getValue().getRoot().getAbsolutePosition().getY() != 0)
                                entry.getValue().getRoot().moveTo(Position.create(0, 0));
                            mainText.addFragment(entry.getValue());
                            currentlyViewed = entry.getKey();
                            term.requestFocus();
                        } else if ((term.getText().equalsIgnoreCase("back"))) {
                            if (entry.getKey().equals(backState)) {
                                mainText.clear();
                                if (entry.getValue().getRoot().getAbsolutePosition().getX() != 0 || entry.getValue().getRoot().getAbsolutePosition().getY() != 0)
                                    entry.getValue().getRoot().moveTo(Position.create(0, 0));
                                mainText.addFragment(entry.getValue());
                                currentlyViewed = entry.getKey();
                                backState="home";
                                term.requestFocus();
                            }
                        }
                    }
                    if (term.getText().equalsIgnoreCase("play") && currentlyViewed.contains(".wav")) {
                        for (Map.Entry<String, Folder> folder : folderMap.entrySet()) {
                            for (DynamicBox entry : folder.getValue().getDocuments()) {
                                if (currentlyViewed.equals(entry.getName())) {
                                    if(sched!=null) {
                                        sched.cancel(true);
                                        sched = null;
                                    }
                                    if(currentlyViewed.contains(".wav")) {
                                        Runnable playSound = new Runnable(){
                                            public void run(){
                                                soundBase.getSoundPlayer().playSound("audio/"+currentlyViewed);
                                            }
                                        };
                                        sched = soundExecutor.schedule(playSound, 0, TimeUnit.SECONDS);
                                    }
                                }
                            }
                        }
                    }
                    if (term.getText().contains("add") || term.getText().contains("Add")) {
                        mainText.clear();
                        if (term.getText().contains("-m")) {
                            saveables.get("med").getRoot().moveTo(Position.create(0, 0));
                            mainText.addFragment(saveables.get("med"));
                            currentlyViewed="medSaveable";
                        } else if (term.getText().contains("-r")){
                            saveables.get("reportSaveable").getRoot().moveTo(Position.create(0, 0));
                            mainText.addFragment(saveables.get("report"));
                            currentlyViewed="reportSaveable";
                        } else if (term.getText().contains("-s")) {
                            saveables.get("schematic").getRoot().moveTo(Position.create(0, 0));
                            mainText.addFragment(saveables.get("schematic"));
                            currentlyViewed="schematicSaveable";
                        } else {
                            saveables.get("doc").getRoot().moveTo(Position.create(0, 0));
                            mainText.addFragment(saveables.get("doc"));
                            currentlyViewed="docSaveable";
                        }
                        term.setText("");
                    }
                    if (term.getText().contains("save") || term.getText().contains("Save")) {
                        termcmd = term.getText();
                        String[] split = termcmd.split(" ");
                        if(split.length>=3) {
                            fold = split[1];
                            nam = split[2];
                            if (split.length>=4){
                                level=Integer.parseInt(split[3]);
                            }
                            switch (currentlyViewed) {
                                case "docSaveable":
                                    save.save(folderMap, fold, "doc_compact", nam, saveables.get("doc").getSaveTA(), labelProperties, menuElementMap, level);
                                    break;
                                case "medSaveable":
                                    save.save(folderMap, fold, "med_chart", nam, saveables.get("med").getSaveTA(), labelProperties, menuElementMap, level);
                                    break;
                                case "reportSaveable":
                                    save.save(folderMap, fold, "status_report_c", nam, saveables.get("report").getSaveTA(), labelProperties, menuElementMap, level);
                                    break;
                                case "schematicSaveable":
                                    save.save(folderMap, fold, "schematic", nam, saveables.get("schematic").getSaveTA(), labelProperties, menuElementMap, level);
                                    break;
                                default:
                                    save.save(folderMap, fold, "doc_compact", nam, saveables.get("doc").getSaveTA(), labelProperties, menuElementMap, level);
                                    break;
                            }
                        } else {
                            term.setText("save FOLDER_NAME FILE_NAME (ACCESS_LEVEL)");
                        }
                        termcmd ="";
                        fold="";
                        nam="";
                    }
                    if (term.getText().equalsIgnoreCase("startup")) {
                        if(sched==null) {
                            sched = soundExecutor.schedule(soundBase.getSoundHonk(), 0, TimeUnit.SECONDS);
                        }
                    }
                    if (term.getText().equalsIgnoreCase("stop")) {
                        if(sched!=null) {
                            sched.cancel(true);
                            sched = null;
                        }
                    }
                    if (term.getText().equalsIgnoreCase("exit")) {
                        System.exit(0);
                    }
                    term.setText("");
                    return UIEventResponse.processed();
                } else if (event.getCode().equals(KeyCode.KEY_L) && event.getCtrlDown() && event.getAltDown()) {
                    mainText.clear();
                    loginView.dock();
                    login.requestFocus();
                    reset(hackLog, hackAttempts, labelProperties);
                    hackLog.clear();
                    currentWindow = "login";
                } else {
                    return UIEventResponse.pass();
                }

            case "login":
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (login.getText().contains("\r")) {
                        login.setText(login.getText().replace("\r", ""));
                    }
                    if (login.getText().contains("\n")) {
                        login.setText(login.getText().replace("\n", ""));
                    }
                    if (pswd.getText().contains("\r")) {
                        pswd.setText(pswd.getText().replace("\r", ""));
                    }
                    if (pswd.getText().contains("\n")) {
                        pswd.setText(pswd.getText().replace("\n", ""));
                    }
                    for(User u:userBase) {
                        if ((login.getText().equals(u.getLogin())) && (pswd.getText().equals(u.getPassword()))) {
                            menuPanelService.setHeaderLabel3(labelProperties.getAccess()+" " + u.getAccessLevel());
                            loggedUser=u;
                            currentWindow = "main";
                            login.setText("");
                            pswd.setText("");
                            initMenu(accessMap.getAccessElementMap().get(String.valueOf(u.getAccessLevel())),mainText);
                            mainView.dock();
                            term.requestFocus();
                        } else {
                        }
                    }
                    login.setText("");
                    pswd.setText("");
                    return UIEventResponse.processed();
                } else if (event.getCode().equals(KeyCode.KEY_H) && event.getCtrlDown() && event.getAltDown()) {
                    //TODO INIT HACK HERE
                    hackingView.dock();
                    hackTerm.requestFocus();
                    currentWindow = "hack";
                } else {
                    return UIEventResponse.pass();
                }

            case "hack":
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (hackTerm.getText().contains("\r")) {
                        hackTerm.setText(hackTerm.getText().replace("\r", ""));
                    }
                    if (hackTerm.getText().contains("\n")) {
                        hackTerm.setText(hackTerm.getText().replace("\n", ""));
                    }
                    if (hackTerm.getText().equalsIgnoreCase(password)) {
                        loggedUser=User.builder().accessLevel(5).build();
                        menuPanelService.setHeaderLabel3(labelProperties.getAccess()+" HACKED" );
                        soundExecutor.schedule(soundBase.getSCorrect(),0, TimeUnit.SECONDS);
                        initMenu(accessMap.getAccessElementMap().get(String.valueOf(5)),mainText);
                        mainView.dock();
                        currentWindow = "main";
                    }
                    hackingLogic.findPhrase(hackLabels, hackTerm.getText(), hackLog, password, hackAttempts, chosenDuds, soundExecutor, soundBase);
                    hackTerm.setText("");
                    if (!hackAttempts.getText().contains("\u2588")) {
                        hackTerm.clearFocus();
                        soundExecutor.schedule(soundBase.getSLocked(),0, TimeUnit.SECONDS);
                        lockoutView.dock();
                        currentWindow = "lock";
                    }
                    if (currentWindow.equals("hack")) {
                        hackTerm.requestFocus();
                    } else {
                        term.requestFocus();
                    }
                    return UIEventResponse.processed();
                } else {
                    return UIEventResponse.pass();
                }
            case "lock":
                if (event.getCode().equals(KeyCode.KEY_L) && event.getCtrlDown() && event.getAltDown()) {
                    loginView.dock();
                    login.requestFocus();
                    reset(hackLog, hackAttempts, labelProperties);
                    hackLog.clear();
                    currentWindow = "login";
                }
                return UIEventResponse.pass();
            default:
                return UIEventResponse.pass();
        }

    }

    public void setCurrentWindow(String currentWindow) {
        this.currentWindow = currentWindow;
    }

    private void reset(LogArea hackLog, Label hackAttempts, LabelProperties labelProperties) {
        hackAttempts.setText(labelProperties.getAttemptsleft());
        hackLog.addHeader(labelProperties.getAtreset(), true);
    }

    private String updateProgressTime(String text){
        Time time = Time.valueOf(text);
        time.toInstant().plusSeconds(1);
        return time.toString();
    }

    private static void initMenu(Map<String, MenuElement> menuElementMap, Panel mainText) {
        mainText.clear();
        for (Map.Entry<String, MenuElement> entry : menuElementMap.entrySet()) {
            if (entry.getKey().equals("home")) {
                entry.getValue().getRoot().moveTo(Position.create(0, 0));
                mainText.clear();
                mainText.addFragment(entry.getValue());
            }
        }
    }

}
