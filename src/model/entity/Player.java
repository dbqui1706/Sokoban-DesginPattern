package model.entity;

import model.Direction;
import model.SokobanMap;
import model.strategy.MoveAndPush;

public class Player extends Entity{
    private Direction player_direction;
    public Player(int col, int row, SokobanMap map) {
        super(col, row, map);
        player_direction = Direction.DOWN; //Default when game start
        moveStrategy = new MoveAndPush(this);
        blockable = true;
    }

    @Override
    public boolean move(Direction direction){
        boolean moveStatus = super.move(direction);
        if(moveStatus == true){
            this.player_direction = direction;
        }
        return moveStatus;
    }

    public Direction getDirection(){
        return player_direction;
    }
}
