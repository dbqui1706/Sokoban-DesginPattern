package view.ui;

import controller.BackgroundMusic;
import controller.ISokobanController;
import model.SokobanModel;
import view.optionUI.CricullarButton;
import view.optionUI.ScrollBarCustom;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AboutPanel extends JPanel implements ActionListener {
    private SokobanModel model;
    private ISokobanController controller;
    private JButton musicBtn, returnMenu;
    private Image backgroundImage;
    private JPanel infoPanel;

    public AboutPanel(SokobanModel model, ISokobanController controller){
        this.model = model;
        this.controller = controller;

        //Set background
        try {
            backgroundImage = resize(SokobanView.PATH_HOME1_BACKGROUND).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this)));

        //Init
        this.setLayout(null);
        // information panel
        infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setSize(new Dimension(800, 500));
        int x = (backgroundImage.getWidth(this) - 750) / 2;
        int y = (backgroundImage.getHeight(this) - 450) / 2;
        infoPanel.setBounds(x, y, 800, 500);

        // Text area
        JTextArea textArea = new JTextArea();
        JLabel label = new JLabel();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("resource/inforation game/info.txt"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            textArea.setText(sb.toString());
            textArea.setFont(new Font("Time new roman", Font.PLAIN, 14));
            textArea.setBorder(null);
            textArea.setEditable(false);
            textArea.setRows(30);
            textArea.setWrapStyleWord(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());

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

        this.add(musicBtn);
        this.add(returnMenu);
        this.add(infoPanel);
        infoPanel.add(scrollPane);
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

    private ImageIcon resize(String imagePath) {
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
        if(source == returnMenu){
            controller.switchPanel("home");
        }
        else if(source == musicBtn){
            controller.switchAudioState();
            updateView();
        }
    }
}
