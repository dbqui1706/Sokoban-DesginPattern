package model.strategy;

import model.Direction;
import model.entity.Entity;

public abstract class AbstractMove implements MoveStrategy{
    protected Entity entity;
    public AbstractMove(Entity entity){
        this.entity = entity;
    }

    protected int diffCol(Direction direction) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return 0;
        }
        if (direction == Direction.LEFT) {
            return -1;
        } else {
            return 1;
        }
    }

    protected int diffRow(Direction direction) {
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            return 0;
        }
        if (direction == Direction.UP) {
            return -1;
        } else {
            return 1;
        }
    }
}
