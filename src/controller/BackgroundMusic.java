package controller;

public class BackgroundMusic {
    public enum BGM{
        HOME_BGM, GAME_BGM
    }
    public enum SFX{
        WIN, MOVE, ILLEGAL_MOVE
    }
    private MusicThread currentBGM; //Nhạc nền đang chạy (mỗi lần chỉ có 1 nhạc nền chạy)
    private boolean unmuteMode;
    private MusicThread homeSoundtrack; //Nhạc nền ở menu home
    private MusicThread gameSoundtrack; // Nhạc nền trong game
    private MusicThread winSoundEffect; //Sound effect khi qua màn chơi
    private MusicThread moveSoundEffect; //Sound effect khi nhân vật di chuyển
    private MusicThread illegalmoveSoundEffect; //Sound effect khi người chơi di chuyển không hợp lệ

    public BackgroundMusic(){
        unmuteMode = true;
        homeSoundtrack = new MusicThread("resource/bgm/home_bgm.wav", true);
        gameSoundtrack = new MusicThread("resource/bgm/ingame_bgm.wav", true);
        winSoundEffect = new MusicThread("resource/bgm/levelcleared.wav", false);
        moveSoundEffect = new MusicThread("resource/bgm/footstep.wav", false);
        illegalmoveSoundEffect = new MusicThread("resource/bgm/illegal.wav", false);
    }

    public void playBGM(BGM bgm){
        switch (bgm){
            case HOME_BGM:
                playBGM(homeSoundtrack);
                break;
            case GAME_BGM:
                playBGM(gameSoundtrack);
                break;
            default:
                System.out.println("DEBUG: input issue");
        }
    }

    public void playSFX(SFX sfx){
        switch (sfx){
            case WIN:
                playSFX(winSoundEffect);
                break;
            case MOVE:
                playSFX(moveSoundEffect);
                break;
            case ILLEGAL_MOVE:
                playSFX(illegalmoveSoundEffect);
                break;
            default:
                System.out.println("DEBUG: input issue");
        }
    }

    private void playBGM(MusicThread bgm){
        if(currentBGM == bgm){
            return;
        }
        if(currentBGM != null){
            currentBGM.turnOff();
        }
        currentBGM = bgm;
        if(unmuteMode){
            currentBGM.turnOn();
        }
    }

    private void playSFX(MusicThread soundEffect){
        if(unmuteMode){
            soundEffect.turnOn();
        }
    }

    public void playHomeSoundtrack(){
        playBGM(homeSoundtrack);
    }

    public void playGameSoundtrack(){
        playBGM(gameSoundtrack);
    }

    public void playWinSoundEffect(){
        playSFX(winSoundEffect);
    }

    public void playMoveSoundEffect(){
        playSFX(moveSoundEffect);
    }

    public void mute(){
        unmuteMode = false;
        currentBGM.turnOff();
    }

    public void unmute(){
        unmuteMode = true;
        currentBGM.turnOn();
    }

    public boolean isMute(){
        return !unmuteMode;
    }
}
