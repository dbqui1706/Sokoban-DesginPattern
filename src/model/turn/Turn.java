package model.turn;

import model.entity.Entity;
import model.entity.EntityPosition;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private List<EntitySnapshot> snapshots;

    public Turn() {
        this.snapshots = new ArrayList<>(2);
    }

    public void addSnapshot(Entity entity, EntityPosition oldPosition, EntityPosition newPosition) {
        this.snapshots.add(new EntitySnapshot(entity, oldPosition, newPosition));
    }

    public void undo() {
        for (EntitySnapshot entitySnapshot : this.snapshots) {
            entitySnapshot.undo();
        }
    }

    public void redo() {
        for (EntitySnapshot entitySnapshot : this.snapshots) {
            entitySnapshot.redo();
        }
    }

    public boolean isEmpty() {
        return this.snapshots.isEmpty();
    }
}
