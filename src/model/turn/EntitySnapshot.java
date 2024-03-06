package model.turn;

import model.entity.Entity;
import model.entity.EntityPosition;

public class EntitySnapshot {
    private Entity entity;
    private EntityPosition newPosition, oldPosition;

    public EntitySnapshot(Entity entity, EntityPosition oldPosition, EntityPosition newPosition) {
        this.entity = entity;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public void undo() {
        entity.setPosition(oldPosition);
    }

    public void redo() {
        entity.setPosition(newPosition);
    }
}
