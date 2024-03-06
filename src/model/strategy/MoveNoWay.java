package model.strategy;

import model.Direction;
import model.entity.Entity;

public class MoveNoWay extends AbstractMove {

    public MoveNoWay(Entity entity) {
        super(entity);
    }

    @Override
    public boolean move(Direction direction) {
        return false;
    }
}
