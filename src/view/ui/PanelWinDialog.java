package view.ui;

import controller.BackgroundMusic;
import controller.ISokobanController;
import model.SokobanModel;

import view.optionUI.CricullarButton;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PanelWinDialog extends JDialog implements ActionListener, WindowListener {
    private static PanelWinDialog instance;
    private JPanel headerPanel, centerPanel, bottomPanel, mainPanel;
    private JButton nextLevelBtn, restartBtn, menuBtn;
    private JLabel moveLabel, bestMoveLabel, levelLabel, valueMove, valueBestMove, valueLevel;
    private JLabel clearLabel;
    private SokobanModel model;
    private ISokobanController controller;

    //Áp dụng mẫu Singleton, chỉ khởi tạo 1 lần, khi dialog kích hoạt thì lấy data từ model về để hiển thị
    public static PanelWinDialog getInstance(){
        return instance;
    }

    //Set các thuộc tính khởi đầu (do Singleton ko hỗ trợ cái này nên làm tạm)
    public static void setAttribute(JFrame view, SokobanModel model, ISokobanController controller){
        instance = new PanelWinDialog(view, model, controller);
    }

    private PanelWinDialog(JFrame view, SokobanModel model, ISokobanController controller) {
        super(view, "You win!", true);
        this.model = model;
        this.controller = controller;
        setResizable(false);
        setSize(500, 300);
        setLocationRelativeTo(null);
        init();
        getContentPane().add(mainPanel);
        setVisible(false);
    }

    private void init() {
        addWindowListener(this);
        // panel
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel = new JPanel(new GridLayout(3, 2, 5, 10));
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // button
        nextLevelBtn = new JButton(new ImageIcon(SokobanView.PATH_NEXT_LEVEL_ICON));
        nextLevelBtn.addActionListener(this);
        menuBtn = new JButton(new ImageIcon(SokobanView.PATH_MENU_ICON));
        menuBtn.addActionListener(this);
        restartBtn = new JButton(new ImageIcon(SokobanView.PATH_RESTART_ICON));
        restartBtn.addActionListener(this);

        // label
        clearLabel = new JLabel("Cleared Level");
        levelLabel = new JLabel("Level:");
        valueLevel = new JLabel();
        bestMoveLabel = new JLabel("Best move:");
        valueBestMove = new JLabel();
        moveLabel = new JLabel("Your move:");
        valueMove = new JLabel();

        // decorate for panel
        headerPanel.setBackground(new Color(237, 204, 133));
        headerPanel.setPreferredSize(new Dimension(50, 50));

        centerPanel.setBackground(new Color(184, 87, 52));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        bottomPanel.setBackground(new Color(237, 204, 133));
        // decorate for label
        Font fontHeader = new Font("Stencil", Font.BOLD, 30);
        Font fontNormal = new Font("Time new Roman", Font.PLAIN, 20);
        Font fontBold = new Font("Stencil", Font.BOLD, 20);

        clearLabel.setFont(fontHeader);
        clearLabel.setForeground(Color.BLACK);

        levelLabel.setFont(fontBold);
        valueLevel.setFont(fontBold);
        levelLabel.setForeground(Color.WHITE);
        valueLevel.setForeground(Color.WHITE);

        bestMoveLabel.setFont(fontBold);
        valueBestMove.setFont(fontBold);
        bestMoveLabel.setForeground(Color.WHITE);
        valueBestMove.setForeground(Color.WHITE);

        moveLabel.setFont(fontBold);
        valueMove.setFont(fontBold);
        moveLabel.setForeground(Color.WHITE);
        valueMove.setForeground(Color.WHITE);

        // decorate for btn
        nextLevelBtn.setPreferredSize(new Dimension(60, 60));
        nextLevelBtn.setBackground(new Color(252, 230, 180));
        nextLevelBtn.setUI(new CricullarButton());

        restartBtn.setPreferredSize(new Dimension(60, 60));
        restartBtn.setBackground(new Color(252, 230, 180));
        restartBtn.setUI(new CricullarButton());

        menuBtn.setPreferredSize(new Dimension(60, 60));
        menuBtn.setBackground(new Color(252, 230, 180));
        menuBtn.setUI(new CricullarButton());

        // panel add
        headerPanel.add(clearLabel);

        centerPanel.add(levelLabel);
        centerPanel.add(valueLevel);
        centerPanel.add(bestMoveLabel);
        centerPanel.add(valueBestMove);
        centerPanel.add(moveLabel);
        centerPanel.add(valueMove);

        bottomPanel.add(menuBtn);
        bottomPanel.add(restartBtn);
        bottomPanel.add(nextLevelBtn);
        // add
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == nextLevelBtn){
            controller.loadNextLevel();
            this.dispose();
        }
        else if(source == menuBtn){
            controller.switchPanel("option_level");
            this.dispose();
        }
        else if(source == restartBtn){
            controller.restart();
            this.dispose();
        }
    }

    //Khi dialog này hiện ra thông qua setVisible(true) thì sẽ kích hoạt cái này
    @Override
    public void windowActivated(WindowEvent e) {
        setLocationRelativeTo(null); //căn giữa
        controller.playSFX(BackgroundMusic.SFX.WIN);
        valueLevel.setText(String.valueOf(model.getLevel()));
        valueBestMove.setText(String.valueOf(model.getBestUserMove()));
        valueMove.setText(String.valueOf(model.getCountTurn()));
    }

    //Khi người dùng ấn nút "X" ở góc phải trên cùng thì sẽ kích hoạt cái này
    @Override
    public void windowClosing(WindowEvent e) {
        controller.switchPanel("option_level");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}
}
