package model;

public class Cell {
    int row;
    int col;
    private int value;
    private OPERATION_TYPE operationType;
    public String op;
    private static Cell start;
    private static Cell goal;

    public Cell(int i, int j, int value, String op) {
        this.row = i;
        this.col = j;
        this.op = op;
        this.value = value;
        this.operationType = OPERATION_TYPE.getOperation(op);
        if (this.operationType == OPERATION_TYPE.GOAL) goal = this;
        if (this.operationType == OPERATION_TYPE.START) start = this;
    }

    public int getValue() {
        return value;
    }

    public static Cell getGoal() {
        return goal;
    }

    public static Cell getStart() {
        return start;
    }

    public OPERATION_TYPE getOperationType() {
        return operationType;
    }

    @Override
    public String toString() {
        return "(" + this.row + "," + this.col + ")";
    }
}
