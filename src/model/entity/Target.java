package model.entity;

import model.SokobanMap;
import model.strategy.MoveNoWay;

public class Target extends Entity{
    public Target(int col, int row, SokobanMap map) {
        super(col, row, map);
        this.moveStrategy = new MoveNoWay(this);
        this.blockable = false;
    }
}
