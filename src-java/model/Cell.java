package model;

public class Cell {
    final int row;
    final int col;
    private final int value;
    private final OPERATION_TYPE operationType;
    public final String op;

    public Cell(int i, int j, int value, String op) {
        this.row = i;
        this.col = j;
        this.op = op;
        this.value = value;
        this.operationType = OPERATION_TYPE.getOperation(op);
    }

    public int getValue() {
        return value;
    }

    public OPERATION_TYPE getOperationType() {
        return operationType;
    }

    @Override
    public String toString() {
        return "(" + this.row + "," + this.col + ")";
    }
}
