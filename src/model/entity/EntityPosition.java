package model.entity;

public class EntityPosition {
    private final int col; //In this game, "col" is like "x" in Cartesian coordinate system
    private final int row; //And "row" is like "y". But Oy axis pointing down

    public EntityPosition(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
