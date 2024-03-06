package view.ui;

import controller.BackgroundMusic;
import controller.ISokobanController;
import model.SokobanModel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;

public class SokobanView extends JFrame {
    public static final String PATH_HOME1_BACKGROUND = "resource/image/ui/home_background1.jpg";
    public static final String PATH_HOME2_BACKGROUND = "resource/image/ui/home_background2.png";
    public static final String PATH_GAME_BACKGROUND = "resource/image/ui/game_background.png";
    public static final String PATH_NEXT_PAGE_ICON = "resource/image/ui/nextpage_icon.png";
    public static final String PATH_PREVIOUS_PAGE_ICON = "resource/image/ui/previouspage_icon.png";
    public static final String PATH_HOME_ICON = "resource/image/ui/home_icon.png";
    public static final String PATH_UNDO_ICON = "resource/image/ui/undo_icon.png";
    public static final String PATH_REDO_ICON = "resource/image/ui/redo_icon.png";
    public static final String PATH_RESTART_ICON = "resource/image/ui/restart_icon.png";
    public static final String PATH_UNMUTE_SPEAKER_ICON = "resource/image/ui/unmute_icon.png";
    public static final String PATH_MUTE_SPEAKER_ICON = "resource/image/ui/mute_icon.png";
    public static final String PATH_MENU_ICON = "resource/image/ui/menu_icon.png";
    public static final String PATH_NEXT_LEVEL_ICON = "resource/image/ui/nextlevel_icon.png";
    private SokobanModel model;
    private ISokobanController controller;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HomePanel homePanel;
    private OptionLevelPanel optionLevelPanel;
    private GamePlay gamePlay;
    private AboutPanel aboutPanel;
    public SokobanView(SokobanModel model, ISokobanController controller){
        this.model = model;
        this.controller = controller;

        //Init
        PanelWinDialog.setAttribute(this, model, controller);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setPreferredSize(new Dimension(1280, 720));
        homePanel = new HomePanel(model, controller);
        optionLevelPanel = new OptionLevelPanel(model, controller);
        gamePlay = new GamePlay(model, controller);
        aboutPanel = new AboutPanel(model, controller);

        //Add to card layout
        mainPanel.add(homePanel, "home");
        mainPanel.add(optionLevelPanel, "option_level");
        mainPanel.add(aboutPanel, "information");
        mainPanel.add(gamePlay, "gameplay");
        add(mainPanel);

        //Setting frame size
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controller.playBGM(BackgroundMusic.BGM.HOME_BGM);
        setVisible(true);

    }

    public void switchPanel(String thePanelYouWant){
        cardLayout.show(mainPanel, thePanelYouWant);
    }
}
