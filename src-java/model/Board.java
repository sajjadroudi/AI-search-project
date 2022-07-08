package model;

import core.Constants;

public class Board {

    private final int row;
    private final int col;
    private final Cell[][] cells;
    private Cell goal;
    private Cell start;

    public Board(int row, int col, Cell[][] cells) {
        this.row = row;
        this.col = col;
        this.cells = cells;

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                var operation = OPERATION_TYPE.getOperation(cells[i][j].op);
                if(operation == OPERATION_TYPE.GOAL) {
                    goal = cells[i][j];
                } else if(operation == OPERATION_TYPE.START) {
                    start = cells[i][j];
                }
            }
        }
    }

    public Board reverse() {
        Cell[][] result = new Cell[row][col];

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                Cell cell = cells[i][j];
                var optType = cell.op;

                if("g".equals(optType)) {
                    optType = "s";
                } else if("s".equals(optType)) {
                    optType = "g";
                }

                result[i][j] = new Cell(cell.row, cell.col, cell.getValue(), optType);
            }
        }

        return new Board(row, col, result);
    }

    public Cell getGoal() {
        return goal;
    }

    public Cell getStart() {
        return start;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder map = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map.append(Constants.ANSI_BRIGHT_BLUE)
                        .append(OPERATION_TYPE.getOperationTag(cells[i][j].getOperationType()))
                        .append(cells[i][j].getValue())
                        .append("\t");

            }
            map.append("\n");
        }
        return map.toString();
    }

}
