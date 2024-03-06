package view.ui;

import controller.BackgroundMusic;
import controller.ISokobanController;
import model.SokobanModel;
import view.optionUI.CricullarButton;
import view.optionUI.LevelButton;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class OptionLevelPanel extends JPanel implements ActionListener {
    private SokobanModel model;
    private ISokobanController controller;
    private JButton levelButtons[] = new JButton[20];
    private Image backgroundImage;
    private JPanel panelContainLevels;
    private JButton nextPageLevel, previousPageLevel, musicBtn, returnMenu;

    public OptionLevelPanel(SokobanModel model, ISokobanController controller){
        this.model = model;
        this.controller = controller;

        //Set background
        try {
            backgroundImage = resize(SokobanView.PATH_HOME2_BACKGROUND).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this)));

        //Init
        this.setLayout(null);
        panelContainLevels = new JPanel(new GridLayout(4, 5, 30, 40));
        panelContainLevels.setSize(new Dimension(450, 450));
        int x = (backgroundImage.getWidth(this) - 400) / 2;
        int y = (backgroundImage.getHeight(this) - 400) / 2;
        panelContainLevels.setBounds(x, y, 400, 400);

        // create levels
        this.panelContainLevels.setOpaque(false);
        Font font = new Font("Stencil", Font.BOLD, 20);
        for (int i = 0; i < this.levelButtons.length; i++) {
            final int index = i + 1;
            levelButtons[i] = new JButton("" + index);
            levelButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //e.getActionCommand();
                    controller.loadLevel(index);
                    controller.switchPanel("gameplay");
                }
            });
            levelButtons[i].setForeground(new Color(254, 242, 216, 255));
            levelButtons[i].setSize(20, 20);
            levelButtons[i].setFont(font);
            levelButtons[i].setUI(new LevelButton());
            panelContainLevels.add(levelButtons[i]);
        }

        // btn next level
        nextPageLevel = new JButton(new ImageIcon(SokobanView.PATH_NEXT_PAGE_ICON));
        nextPageLevel.addActionListener(this);
        nextPageLevel.setBounds(this.backgroundImage.getWidth(this) / 2 + panelContainLevels
                .getWidth() / 2 + 50, y + panelContainLevels.getHeight() / 2 - 25, 50, 50);
        nextPageLevel.setUI(new CricullarButton());

        // btn pre level
        previousPageLevel = new JButton(new ImageIcon(SokobanView.PATH_PREVIOUS_PAGE_ICON));
        previousPageLevel.addActionListener(this);
        previousPageLevel.setBounds(x - 100, y + panelContainLevels.getHeight() / 2 - 25, 50, 50);
        previousPageLevel.setUI(new CricullarButton());

        // btn return
        returnMenu = new JButton(new ImageIcon(SokobanView.PATH_HOME_ICON));
        returnMenu.addActionListener(this);
        returnMenu.setBounds(this.backgroundImage.getWidth(this) - 200, 40, 50, 50);
        returnMenu.setUI(new CricullarButton());

        // btn music
        musicBtn = new JButton(new ImageIcon(SokobanView.PATH_UNMUTE_SPEAKER_ICON));
        musicBtn.addActionListener(this);
        musicBtn.setBounds(this.backgroundImage.getWidth(this) - 125, 40, 50, 50);
        musicBtn.setUI(new CricullarButton());

        // add
        this.add(musicBtn);
        this.add(returnMenu);
        this.add(nextPageLevel);
        this.add(previousPageLevel);
        this.add(panelContainLevels);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                List<Integer> allLevelsCleared = model.getAllLevelsCleared();
                for(int level : allLevelsCleared){
                    levelButtons[level - 1].setBackground(new Color(124, 255, 48));
                }
                updateView();
                controller.playBGM(BackgroundMusic.BGM.HOME_BGM);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    public ImageIcon resize(String imagePath) {
        try {
            Image originalImage = ImageIO.read(new File(imagePath));
            Image resizedImage = originalImage.getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
            BufferedImage bufferedImage = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(resizedImage, 0, 0, null);
            graphics.dispose();
            return new ImageIcon(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateView(){
        if(!controller.isMute()){
            musicBtn.setIcon(new ImageIcon(SokobanView.PATH_UNMUTE_SPEAKER_ICON));
        }
        else{
            musicBtn.setIcon(new ImageIcon(SokobanView.PATH_MUTE_SPEAKER_ICON));
        }
    }

    private void showLevelPage(int page){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == nextPageLevel){
            //TODO
        }
        else if(source == previousPageLevel){
            //TODO
        }
        else if(source == returnMenu){
            controller.switchPanel("home");
        }
        else if(source == musicBtn){
            controller.switchAudioState();
            updateView();
        }
    }
}