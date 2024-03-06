package model.strategy;

import model.Direction;
import model.entity.Entity;
import model.entity.EntityPosition;


public class MoveAndPush extends AbstractMove {
    public MoveAndPush(Entity entity) {
        super(entity);
    }

    @Override
    public boolean move(Direction direction) {
        //Get affected entity on the way of this entity
        int affected_col = entity.getCol() + diffCol(direction);
        int affected_row = entity.getRow() + diffRow(direction);
        Entity affected_entity = entity.getMap().getEntity(affected_col, affected_row);

        if (affected_entity == null
            || affected_entity.isBlockable() == false
            || affected_entity.move(direction)) {
            //Entity try to push the affected_entity (if it exist), if pushable then player will move forward
            //Or maybe there no blockable entity, it also move too.
            entity.moveTo(affected_col, affected_row);
            return true;
        }
        else {
            return false;
        }
    }
}
