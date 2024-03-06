package model.strategy;

import model.Direction;
import model.entity.Entity;
import model.entity.EntityPosition;

public class MoveAndRoll extends AbstractMove{
    public MoveAndRoll(Entity entity){
        super(entity);
    }

    @Override
    public boolean move(Direction direction) {
        int affected_col = entity.getCol() + diffCol(direction);
        int affected_row = entity.getRow() + diffRow(direction);
        Entity affected_entity = entity.getMap().getEntity(affected_col, affected_row);
        boolean canMove = false;

        //Entity move until blocking by another entity
        while (affected_entity == null || !affected_entity.isBlockable()){
            canMove = true;
            affected_col += diffCol(direction);
            affected_row += diffRow(direction);
            affected_entity = entity.getMap().getEntity(affected_col, affected_row);
        }

        //Get last position that entity can move and move to it
        affected_col -= diffCol(direction);
        affected_row -= diffRow(direction);
        entity.moveTo(affected_col, affected_row);
        return canMove;
    }
}
