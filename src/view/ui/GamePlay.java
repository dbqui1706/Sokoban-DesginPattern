package view.ui;

import controller.BackgroundMusic;
import controller.ISokobanController;
import model.Direction;
import model.SokobanModel;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePlay extends JPanel {
    private SokobanModel model;
    private ISokobanController controller;
    private MapPanel mapPanel;
    private FunctionPanel functionPanel;
    private Image backgroundImage;

    public GamePlay(SokobanModel model, ISokobanController controller){
        this.model = model;
        this.controller = controller;

        //set background
        try {
            backgroundImage = resize(SokobanView.PATH_GAME_BACKGROUND).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        setPreferredSize(new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this) - 100));

        //Init
        setLayout(new BorderLayout());
        mapPanel = new MapPanel(model, controller);
        mapPanel.setOpaque(false);
        functionPanel = new FunctionPanel(model, controller);
        functionPanel.setOpaque(false);

        this.add(functionPanel, BorderLayout.NORTH);
        this.add(mapPanel, BorderLayout.CENTER);
        keyBinding(); //KeyBinding
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                controller.playBGM(BackgroundMusic.BGM.GAME_BGM);
                functionPanel.updateView();
                GamePlay.this.requestFocusInWindow();
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

    private void keyBinding(){
        //Ref: https://stackoverflow.com/questions/22741215/how-to-use-key-bindings-instead-of-key-listeners
        //Ở đây ta dùng KeyBinding thay cho KeyListener là bởi vì một số trục trặc liên quan
        //tới Focus của các component làm cho Panel khó có thể handle được khi user nhấn phím
        //Với việc sử dụng phương pháp này thì user nhấn phím sẽ "ăn" hơn
        int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke("W"), "UP");
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke("UP"), "UP");
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke("S"), "DOWN");
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke("DOWN"), "DOWN");
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke("A"), "LEFT");
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke("LEFT"), "LEFT");
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke("D"), "RIGHT");
        this.getInputMap(AFC).put(KeyStroke.getKeyStroke("RIGHT"), "RIGHT");

        this.getActionMap().put("UP", new KeyHandler("UP"));
        this.getActionMap().put("DOWN", new KeyHandler("DOWN"));
        this.getActionMap().put("LEFT", new KeyHandler("LEFT"));
        this.getActionMap().put("RIGHT", new KeyHandler("RIGHT"));

    }

    private class KeyHandler extends AbstractAction{
        String action;
        KeyHandler(String action){
            this.action = action;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action){
                case "UP":
                    controller.movePlayer(Direction.UP);
                    break;
                case "DOWN":
                    controller.movePlayer(Direction.DOWN);
                    break;
                case "LEFT":
                    controller.movePlayer(Direction.LEFT);
                    break;
                case "RIGHT":
                    controller.movePlayer(Direction.RIGHT);
                    break;
            }
        }
    }
}
