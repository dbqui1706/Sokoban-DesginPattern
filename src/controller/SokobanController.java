package controller;

import model.Direction;
import model.SokobanModel;
import view.ui.PanelWinDialog;
import view.ui.SokobanView;

import java.io.IOException;

public class SokobanController implements ISokobanController{
    private SokobanModel model;
    private SokobanView view;
    private BackgroundMusic backgroundMusic;


    public SokobanController(SokobanModel model){
        backgroundMusic = new BackgroundMusic();
        this.model = model;
        view = new SokobanView(model, this);
    }

    @Override
    public void switchPanel(String thePanelYouWant) {
        view.switchPanel(thePanelYouWant);
    }

    @Override
    public boolean isMute() {
        return backgroundMusic.isMute();
    }

    @Override
    public void switchAudioState() {
        if(backgroundMusic.isMute()){
            backgroundMusic.unmute();
        }
        else{
            backgroundMusic.mute();
        }
    }

    @Override
    public void playBGM(BackgroundMusic.BGM bgm) {
        backgroundMusic.playBGM(bgm);
    }

    @Override
    public void playSFX(BackgroundMusic.SFX sfx) {
        backgroundMusic.playSFX(sfx);
    }

    @Override
    public void loadLevel(int level) {
        try {
            model.loadLevel(level);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadNextLevel() {
        int currentLevel = model.getLevel();
        loadLevel(currentLevel + 1);
    }

    @Override
    public void movePlayer(Direction direction) {
        boolean moved = model.movePlayer(direction);
        if(moved){
            playSFX(BackgroundMusic.SFX.MOVE);
        }
        else{
            playSFX(BackgroundMusic.SFX.ILLEGAL_MOVE);
        }
        if(model.isWon()){
            model.saveRecord();
            PanelWinDialog.getInstance().setVisible(true);
        }
    }

    @Override
    public void undo() {
        model.undo();
    }

    @Override
    public void redo() {
        model.redo();
    }

    @Override
    public void restart() {
        model.restart();
    }
}
