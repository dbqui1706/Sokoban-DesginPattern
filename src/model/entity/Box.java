package model.entity;

import model.SokobanMap;
import model.strategy.MoveNormally;

public class Box extends Entity{

    public Box(int col, int row, SokobanMap map) {
        super(col, row, map);
        this.moveStrategy = new MoveNormally(this);
        this.blockable = true;
    }

    public boolean isOnTarget(){
        Target target = map.getTarget(getCol(), getRow());
        if(target != null){
            return true;
        }
        return false;
    }
}
