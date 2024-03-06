package controller;

import model.Direction;

public interface ISokobanController {
    public void switchPanel(String thePanelYouWant);
    public boolean isMute();
    public void switchAudioState();
    public void playBGM(BackgroundMusic.BGM bgm);
    public void playSFX(BackgroundMusic.SFX sfx);
    public void loadLevel(int level);
    public void loadNextLevel();
    public void movePlayer(Direction direction);
    public void undo();
    public void redo();
    public void restart();
}
