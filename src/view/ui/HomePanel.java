package view.ui;

import controller.BackgroundMusic;
import controller.ISokobanController;
import model.SokobanModel;
import view.optionUI.ButtonGameUI;
import view.optionUI.CricullarButton;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static java.awt.Color.WHITE;

public class HomePanel extends JPanel implements ActionListener {
    private SokobanModel model;
    private ISokobanController controller;
    private JButton playGame, about, musicBtn;
    private Image backgroundImage;

    public HomePanel(SokobanModel model, ISokobanController controller){
        this.model = model;
        this.controller = controller;

        //Set background image
        try {
            backgroundImage = new ImageIcon(SokobanView.PATH_HOME1_BACKGROUND).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        setPreferredSize(new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this)));

        //Init
        this.setLayout(null);
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        int buttonWidth = 200;
        int buttonHeight = 80;
        int buttonX = (backgroundImage.getWidth(this) - buttonWidth) / 2;
        int buttonY = (backgroundImage.getHeight(this) - buttonHeight) / 2;

        playGame = new JButton("Play Game");
        playGame.addActionListener(this);
        playGame.setFont(new Font("Stencil", Font.ITALIC, 24));
        playGame.setForeground(WHITE);
        playGame.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        playGame.setBackground(new Color(0x09FDAB));
        playGame.setUI(new ButtonGameUI());

        about = new JButton("About");
        about.addActionListener(this);
        about.setFont(new Font("Stencil", Font.ITALIC, 24));
        about.setForeground(WHITE);
        about.setBounds(buttonX, buttonY + 100, buttonWidth, buttonHeight);
        about.setBackground(new Color(0x09FDAB));
        about.setUI(new ButtonGameUI());

        musicBtn = new JButton(new ImageIcon(SokobanView.PATH_UNMUTE_SPEAKER_ICON));
        musicBtn.addActionListener(this);
        musicBtn.setBounds(this.backgroundImage.getWidth(this) - 125, 40, 50, 50);
        musicBtn.setUI(new CricullarButton());

        this.add(playGame);
        this.add(about);
        this.add(musicBtn);

        //Kích hoạt khi card layout chuyển tới panel này
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
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

    public void updateView(){
        if(!controller.isMute()){ //Unmute mode
            musicBtn.setIcon(new ImageIcon(SokobanView.PATH_UNMUTE_SPEAKER_ICON));
        }
        else{
            musicBtn.setIcon(new ImageIcon(SokobanView.PATH_MUTE_SPEAKER_ICON));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == playGame){
            controller.switchPanel("option_level");
        }
        else if(source == about){
            controller.switchPanel("information");
        }
        else if(source == musicBtn){
            controller.switchAudioState();
            updateView();
        }
    }
}
