package view.ui;

import controller.ISokobanController;
import model.SokobanModel;
import model.observer.ModelObserver;
import view.optionUI.CricullarButton;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FunctionPanel extends JPanel implements ModelObserver, ActionListener {
    private SokobanModel model;
    private ISokobanController controller;
    private JLabel moveLabel, levelLabel, myScoreLabel, highScoreLabel;
    private JButton undoBtn, redoBtn, restartBtn, musicBtn, menuBtn;
    private JPanel statuspanel, btnPanel;


    public FunctionPanel(SokobanModel model, ISokobanController controller){
        this.model = model;
        this.controller = controller;

        //Init
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(this.getWidth(), 80));
        model.addModelObserver(this);

        statuspanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        statuspanel.setOpaque(false);
        btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);

        Font fontHeader = new Font("Stencil", Font.BOLD, 24);
        Color color =  Color.WHITE;
        levelLabel = new JLabel("Level: " + model.getLevel());
        levelLabel.setFont(fontHeader);
        levelLabel.setForeground(color);
        levelLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 15));

        moveLabel = new JLabel("Move: " + model.getCountTurn());
        moveLabel.setFont(fontHeader);
        moveLabel.setForeground(color);
        moveLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 15));

        highScoreLabel = new JLabel("High score: " + model.getBestUserMove());
        highScoreLabel.setFont(fontHeader);
        highScoreLabel.setForeground(color);
        highScoreLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 15));

        undoBtn = new JButton(new ImageIcon(SokobanView.PATH_UNDO_ICON));
        undoBtn.addActionListener(this);
        undoBtn.setPreferredSize(new Dimension(60, 60));
        undoBtn.setBackground(Color.WHITE);
        undoBtn.setUI(new CricullarButton());

        redoBtn = new JButton(new ImageIcon(SokobanView.PATH_REDO_ICON));
        redoBtn.addActionListener(this);
        redoBtn.setPreferredSize(new Dimension(60, 60));
        redoBtn.setBackground(Color.WHITE);
        redoBtn.setUI(new CricullarButton());

        restartBtn = new JButton(new ImageIcon(SokobanView.PATH_RESTART_ICON));
        restartBtn.addActionListener(this);
        restartBtn.setPreferredSize(new Dimension(60, 60));
        restartBtn.setBackground(Color.WHITE);
        restartBtn.setUI(new CricullarButton());

        musicBtn = new JButton(new ImageIcon(SokobanView.PATH_UNMUTE_SPEAKER_ICON));
        musicBtn.addActionListener(this);
        musicBtn.setPreferredSize(new Dimension(60, 60));
        musicBtn.setBackground(Color.WHITE);
        musicBtn.setUI(new CricullarButton());

        menuBtn = new JButton(new ImageIcon(SokobanView.PATH_MENU_ICON));
        menuBtn.addActionListener(this);
        menuBtn.setPreferredSize(new Dimension(60, 60));
        menuBtn.setBackground(Color.WHITE);
        menuBtn.setUI(new CricullarButton());

        statuspanel.add(levelLabel);
        statuspanel.add(moveLabel);
        statuspanel.add(highScoreLabel);
        btnPanel.add(undoBtn);
        btnPanel.add(redoBtn);
        btnPanel.add(restartBtn);
        btnPanel.add(musicBtn);
        btnPanel.add(menuBtn);

        this.add(statuspanel, BorderLayout.WEST);
        this.add(btnPanel, BorderLayout.EAST);
    }

    @Override
    public void update() {
        levelLabel.setText("Level: " + model.getLevel());
        moveLabel.setText("Move: " + model.getCountTurn());
        highScoreLabel.setText("High score: " + model.getBestUserMove());
    }

    public void updateView(){
        if(!controller.isMute()){
            musicBtn.setIcon(new ImageIcon(SokobanView.PATH_UNMUTE_SPEAKER_ICON));
        }
        else{
            musicBtn.setIcon(new ImageIcon(SokobanView.PATH_MUTE_SPEAKER_ICON));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == undoBtn){
            controller.undo();
        }
        else if(source == redoBtn){
            controller.redo();
        }
        else if(source == restartBtn){
            controller.restart();
        }
        else if(source == musicBtn){
            controller.switchAudioState();
            updateView();
        }
        else if(source == menuBtn){
            controller.switchPanel("option_level");
        }
    }
}
