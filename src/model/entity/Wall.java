package model.entity;

import model.SokobanMap;
import model.strategy.MoveNoWay;

public class Wall extends Entity{
    public Wall(int col, int row, SokobanMap map) {
        super(col, row, map);
        this.moveStrategy = new MoveNoWay(this);
        this.blockable = true;
    }
}
