package model.strategy;

import model.Direction;
import model.entity.Entity;
import model.entity.EntityPosition;

public class MoveNormally extends AbstractMove{
    public MoveNormally(Entity entity) {
        super(entity);
    }

    @Override
    public boolean move(Direction direction) {
        //Get affected entity on the way of this entity
        int affected_col = entity.getCol() + diffCol(direction);
        int affected_row = entity.getRow() + diffRow(direction);
        Entity affected_entity = entity.getMap().getEntity(affected_col, affected_row);

        if (affected_entity == null || affected_entity.isBlockable() == false) {
            //The entity can move if there no blocking entity
            entity.moveTo(affected_col, affected_row);
            return true;
        }
        else {
            return false;
        }
    }
}
