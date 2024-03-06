package model;

import model.entity.Box;
import model.entity.Player;
import model.entity.Target;
import model.entity.Wall;
import model.observer.ModelObserver;
import model.observer.ModelSubject;
import model.observer.MoveObserver;
import model.turn.TurnManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SokobanModel implements ModelSubject {
    private SokobanMap map;
    private int level;
    private TurnManager turnManager;
    private int bestUserMove;
    private List<ModelObserver> modelObservers = new ArrayList<>(2);

    public void loadLevel(int level) throws IOException {
        this.map = new SokobanMap(getLevelPath(level));
        loadUserData(level);
        this.level = level;
        this.turnManager = new TurnManager();
        addMoveObserver(turnManager);
        notifyModelObserver();
    }

    private void loadUserData(int level) throws IOException {
        File file = new File(getUserSavePath(level));
        if (!file.exists()) {
            bestUserMove = 0;
        } else {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            bestUserMove = Integer.parseInt(br.readLine());
            fr.close();
            br.close();
        }
    }

    private String getLevelPath(int level) {
        return "resource/map/level" + level + ".txt";
    }

    private String getUserSavePath(int level) {
        return "resource/user_save/level" + level + ".txt";
    }

    public int getColumns() {
        return map.getColumns();
    }

    public int getRows() {
        return map.getRows();
    }

    public int getCountTurn() {
        return turnManager.getCountTurn();
    }

    public int getLevel() {
        return this.level;
    }

    public int getBestUserMove() {
        return this.bestUserMove;
    }

    public boolean movePlayer(Direction direction) {
        Player player = map.getPlayer();
        boolean isMoved = player.move(direction);
        if (isMoved) {
            turnManager.endTurn();
        }
        notifyModelObserver();
        return isMoved;
    }

    public boolean isWon() {
        return map.isWon();
    }

    public void saveRecord() {
        if(bestUserMove == 0){
            //Chưa có best user (aka người chơi chưa qua màn trước đấy)
            bestUserMove = Integer.MAX_VALUE; // Làm như thế này để xuống dòng if phía dưới
        }

        if (bestUserMove > getCountTurn()) {
            bestUserMove = getCountTurn();
            try {
                FileWriter fw = new FileWriter(getUserSavePath(level), false);
                fw.write(getCountTurn());
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void undo() {
        turnManager.undo();
        notifyModelObserver();
    }

    public void redo() {
        turnManager.redo();
        notifyModelObserver();
    }

    public void restart() {
        turnManager.restart();
        notifyModelObserver();
    }

    public List<Integer> getAllLevelsCleared() {
        File dir = new File("resource/user_save/");
        File[] files = dir.listFiles();
        List<Integer> result = new ArrayList<>();
        for (File file : files) {
            result.add(getLevelAsNumber(file.getName()));
        }

        return result;
    }

    private int getLevelAsNumber(String file_name) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(file_name);
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            return Integer.parseInt(file_name.substring(start, end));
        }

        return -1;
    }

    public List<Wall> getWalls() {
        return map.getWalls();
    }

    public List<Target> getTargets() {
        return map.getTargets();
    }

    public List<Box> getBoxes() {
        return map.getBoxes();
    }

    public Player getPlayer() {
        return map.getPlayer();
    }

    //Move Observer
    public void addMoveObserver(MoveObserver moveObserver) {
        map.addMoveObserver(moveObserver);
    }

    public void removeMoveObserver(MoveObserver moveObserver) {
        map.removeMoveObserver(moveObserver);
    }

    //Model Observer
    @Override
    public void addModelObserver(ModelObserver modelObserver) {
        modelObservers.add(modelObserver);
    }

    @Override
    public void removeModelObserver(ModelObserver modelObserver) {
        modelObservers.remove(modelObserver);
    }

    @Override
    public void notifyModelObserver() {
        for (ModelObserver modelObserver : modelObservers) {
            modelObserver.update();
        }
    }
}
