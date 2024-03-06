package model.entity;

import model.Direction;
import model.SokobanMap;
import model.observer.MoveObserver;
import model.observer.MoveSubject;
import model.strategy.MoveStrategy;

import java.util.ArrayList;

public abstract class Entity implements MoveSubject {
    protected EntityPosition currentPosition;
    protected SokobanMap map; //Entities should be aware of the world around them
    protected MoveStrategy moveStrategy; //Entity in some cases (maybe game update) can change move behaviour
    protected ArrayList<MoveObserver> moveObservers;
    protected boolean blockable; //Blockable mean the Entity can affected by player force/push (it like a real world object)

    public Entity(int col, int row, SokobanMap map) {
        this.currentPosition = new EntityPosition(col, row);
        this.map = map;
        this.moveObservers = new ArrayList<>(4); //Set default capacity
    }

    public int getCol() {
        return currentPosition.getCol();
    }

    public int getRow() {
        return currentPosition.getRow();
    }

    public void setPosition(int col, int row){
        this.currentPosition = new EntityPosition(col, row);
    }

    public void setPosition(EntityPosition position){
        this.currentPosition = position;
    }

    public void moveTo(int col, int row){
        moveTo(new EntityPosition(col, row));
    }

    public void moveTo(EntityPosition newPosition){
        EntityPosition oldPosition = currentPosition;
        this.currentPosition = newPosition;
        notifyMoveObservers(oldPosition, newPosition);
    }

    public boolean isBlockable(){
        return blockable;
    }

    public SokobanMap getMap() {
        return map;
    }

    public boolean move(Direction direction){
        boolean moveStatus = moveStrategy.move(direction);
        return moveStatus;
    }

    public void setMoveStrategy(MoveStrategy ms){
        this.moveStrategy = ms;
    }

    @Override
    public String toString(){
        return "[" + currentPosition.getCol() + ", " + currentPosition.getRow() + "]";
    }

    @Override
    public void addMoveObserver(MoveObserver moveObserver) {
        moveObservers.add(moveObserver);
    }

    @Override
    public void removeMoveObserver(MoveObserver moveObserver) {
        moveObservers.remove(moveObserver);
    }

    @Override
    public void notifyMoveObservers(EntityPosition oldPosition, EntityPosition newPosition) {
        for(MoveObserver moveObserver : moveObservers){
            moveObserver.moveHandle(this, oldPosition, newPosition);
        }
    }
}
