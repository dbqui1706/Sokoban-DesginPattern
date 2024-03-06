package model.observer;

import model.entity.Entity;
import model.entity.EntityPosition;

public interface MoveSubject {
    public void addMoveObserver(MoveObserver moveObserver);

    public void removeMoveObserver(MoveObserver moveObserver);

    public void notifyMoveObservers(EntityPosition oldPosition, EntityPosition newPosition);

}
