package model;

import model.entity.Box;
import model.entity.Entity;
import model.entity.Player;
import model.entity.Target;
import model.entity.Wall;
import model.observer.MoveObserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SokobanMap {
    private int columns = 0;
    private int rows = 0;
    private List<Wall> walls = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
    private List<Box> boxes = new ArrayList<>();
    private Player player = null;

    public SokobanMap(String level) throws IOException {
        loadMapFromFile(level);
    }

//    public SokobanMap(int columns, int rows, List<Wall> walls, List<Target> targets,
//                      List<Box> boxes, Player player, int level, int countMove) {
//        this.columns = columns;
//        this.rows = rows;
//        this.walls = walls;
//        this.targets = targets;
//        this.boxes = boxes;
//        this.player = player;
//        this.level = level;
//        this.countMove = countMove;
//    }

    public void loadMapFromFile(String file_path) throws IOException {
        File file = new File(file_path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = reader.readLine()) != null) {
            int row = this.rows;
            this.rows++;
            String[] line_elements = line.split("");
            this.columns = Math.max(this.columns, line_elements.length);
            for (int col = 0; col < line_elements.length; col++) {
                String element = line_elements[col];
                createEntity(col, row, element);
            }
        }
        reader.close();
    }

    private void createEntity(int col, int row, String ch) {
        switch (ch) {
            case " ":
                return;
            case "#":
                walls.add(new Wall(col, row, this));
                return;
            case "$":
                boxes.add(new Box(col, row, this));
                return;
            case ".":
                targets.add(new Target(col, row, this));
                return;
            case "+":
                targets.add(new Target(col, row, this));
                player = new Player(col, row, this);
                return;
            case "*":
                targets.add(new Target(col, row, this));
                boxes.add(new Box(col, row, this));
                return;
            case "@":
                player = new Player(col, row, this);
                return;
        }
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getEntity(int col, int row) {
        Entity entity = null;

        entity = getWall(col, row);
        if (entity != null) {
            return entity;
        }
        entity = getBox(col, row);
        if (entity != null) {
            return entity;
        }
        entity = getTarget(col, row);
        if (entity != null) {
            return entity;
        }
        entity = getPlayer(col, row);
        if (entity != null) {
            return entity;
        }

        return null;
    }

    public Wall getWall(int col, int row) {
        for (Wall wall : walls) {
            if (wall.getCol() == col && wall.getRow() == row) {
                return wall;
            }
        }
        return null;
    }

    public Target getTarget(int col, int row) {
        for (Target target : targets) {
            if (target.getCol() == col && target.getRow() == row) {
                return target;
            }
        }
        return null;
    }

    public Box getBox(int col, int row) {
        for (Box box : boxes) {
            if (box.getCol() == col && box.getRow() == row) {
                return box;
            }
        }
        return null;
    }

    public Player getPlayer(int col, int row) {
        if (player.getCol() == col && player.getRow() == row) {
            return player;
        }
        return null;
    }

    public void addMoveObserver(MoveObserver moveObserver){
        player.addMoveObserver(moveObserver);
        for(Box box : boxes){
            box.addMoveObserver(moveObserver);
        }
    }

    public void removeMoveObserver(MoveObserver moveObserver){
        player.removeMoveObserver(moveObserver);
        for(Box box : boxes){
            box.removeMoveObserver(moveObserver);
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public boolean isWon() {
        int count = this.targets.size();
        for (Box box : this.boxes) {
            for (Target target : this.targets) {
                if (box.getCol() == target.getCol() && box.getRow() == target.getRow()) {
                    count--;
                }
            }
        }
        if (count == 0) return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(columns + " " + rows + "\n");
        stringBuffer.append("Walls: " + walls + "\n");
        stringBuffer.append("Targets: " + targets + "\n");
        stringBuffer.append("Boxes: " + boxes + "\n");
        stringBuffer.append("Player: " + player + "\n");
        return stringBuffer.toString();
    }
}
