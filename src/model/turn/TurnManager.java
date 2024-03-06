package model.turn;

import model.entity.Entity;
import model.entity.EntityPosition;
import model.observer.MoveObserver;

import java.util.Stack;

public class TurnManager implements MoveObserver {
    private Stack<Turn> undoStack, redoStack;
    private Turn currentTurn;

    public TurnManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        currentTurn = new Turn();
    }

    public boolean undo() {
        if (!undoStack.isEmpty()) {
            Turn undoTurn = undoStack.pop();
            redoStack.add(undoTurn);
            undoTurn.undo();
            return true;
        }
        return false;
    }

    public boolean redo() {
        if (!redoStack.isEmpty()) {
            Turn redoTurn = redoStack.pop();
            undoStack.add(redoTurn);
            redoTurn.redo();
            return true;
        }
        return false;
    }

    public void endTurn() {
        if (!currentTurn.isEmpty()) {
            undoStack.add(currentTurn);
            currentTurn = new Turn();
        }
    }

    public int getCountTurn(){
        return undoStack.size();
    }

    public void restart(){
        while(!undoStack.isEmpty()){
            undo();
        }
        redoStack.clear();
    }

    @Override
    public void moveHandle(Entity moveEntity, EntityPosition oldPosition, EntityPosition newPosition) {
        redoStack.clear();
        currentTurn.addSnapshot(moveEntity, oldPosition, newPosition);
    }
}
