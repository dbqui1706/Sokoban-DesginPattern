package model.observer;

import model.entity.Entity;
import model.entity.EntityPosition;

public interface MoveObserver {
    public void moveHandle(Entity moveEntity, EntityPosition oldPosition, EntityPosition newPosition);
}
