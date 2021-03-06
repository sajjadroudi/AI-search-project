package model;

import core.Constants;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Node {
    public final Board board;
    protected final int sum;
    public final Node parent;
    public final Cell currentCell;
    private final Cell[][] cells;
    private final int goalValue;
    private final Hashtable<String, Boolean> repeatedStates;

    private Node(Cell currentCell, int currentValue, int goalValue, Board board, Node parent, Hashtable<String, Boolean> repeated, boolean copy) {
        this.currentCell = currentCell;
        this.sum = currentValue;
        this.board = board;
        this.cells = board.getCells();
        this.parent = parent;

        if(copy) {
            this.goalValue = goalValue;
            this.repeatedStates = new Hashtable<>(repeated);
        } else {
            this.repeatedStates = new Hashtable<>(repeated);
            this.repeatedStates.put(this.toString(), true);

            if (currentCell.getOperationType() == OPERATION_TYPE.DECREASE_GOAL)
                goalValue -= currentCell.getValue();

            if (currentCell.getOperationType() == OPERATION_TYPE.INCREASE_GOAL)
                goalValue += currentCell.getValue();

            this.goalValue = goalValue;
        }
    }

    public Node(Cell currentCell, int currentValue, int goalValue, Board board, Node parent, Hashtable<String, Boolean> repeated) {
        this(currentCell, currentValue, goalValue, board, parent, repeated, false);
    }

    public Node(Cell currentCell, int currentValue, int goalValue, Board board, Node parent) {
        this(currentCell, currentValue, goalValue, board, parent, new Hashtable<>(), false);
    }

    public Node(Node node) {
        this(node.currentCell, node.sum, node.goalValue, node.board, node.parent, new Hashtable<>(node.repeatedStates), true);
    }

    public List<Node> successor() {
        ArrayList<Node> result = new ArrayList<Node>();
        if (canMoveRight()) {
            Cell rightCell = this.cells[this.currentCell.row][this.currentCell.col + 1];
            if (isValidMove(rightCell)) {
                int calculatedValue = calculate(rightCell);
                Node rightNode = new Node(rightCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(rightNode);
            }
        }
        if (canMoveLeft()) {
            Cell leftCell = this.cells[this.currentCell.row][this.currentCell.col - 1];
            if (isValidMove(leftCell)) {
                int calculatedValue = calculate(leftCell);
                Node leftNode = new Node(leftCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(leftNode);
            }
        }
        if (canMoveDown()) {
            Cell downCell = this.cells[this.currentCell.row + 1][this.currentCell.col];
            if (isValidMove(downCell)) {
                int calculatedValue = calculate(downCell);
                Node downNode = new Node(downCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(downNode);
            }

        }
        if (canMoveUp()) {
            Cell upCell = this.cells[this.currentCell.row - 1][this.currentCell.col];
            if (isValidMove(upCell)) {
                int calculatedValue = calculate(upCell);
                Node upNode = new Node(upCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(upNode);
            }
        }
        return result;
    }

    private boolean canEnterGoal(Cell downCell) {
        if (downCell != board.getGoal()) return true;
        else {
            return sum >= goalValue;
        }
    }


    private int calculate(Cell cell) {
        return switch (cell.getOperationType()) {
            case MINUS -> sum - cell.getValue();
            case ADD -> sum + cell.getValue();
            case POW -> (int) Math.pow(sum, cell.getValue());
            case MULT -> sum * cell.getValue();
            default -> sum;
        };

    }

    private boolean isWall(Cell cell) {
        return cell.getOperationType() == OPERATION_TYPE.WALL;
    }

    private boolean canMoveRight() {
        return this.currentCell.col < this.board.getRow() - 1;
    }

    private boolean canMoveLeft() {
        return this.currentCell.col > 0;
    }

    private boolean canMoveUp() {
        return this.currentCell.row > 0;
    }

    private boolean canMoveDown() {
        return this.currentCell.row < this.board.getCol() - 1;
    }

    private Boolean isValidMove(Cell destCell) {
        return destCell != board.getStart() && canEnterGoal(destCell) && !isWall(destCell) && !repeatedStates.containsKey(destCell.toString());
    }

    public boolean isGoal() {
        if (currentCell.getOperationType() == OPERATION_TYPE.GOAL) {
            return sum >= goalValue;
        }
        return false;
    }

    public String hash() {
        StringBuilder hash = new StringBuilder();
        hash.append("i:")
                .append(currentCell.row)
                .append("j:")
                .append(currentCell.col)
                .append("sum:")
                .append(sum)
                .append("op:")
                .append(currentCell.op)
                .append("val:")
                .append(currentCell.getValue());

        return hash.toString();
    }

    public void drawState() {

        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                if (cells[i][j].getOperationType() == OPERATION_TYPE.GOAL) {
                    System.out.print(Constants.ANSI_BRIGHT_GREEN +
                            OPERATION_TYPE.getOperationTag(cells[i][j].getOperationType())
                            + goalValue + spaceRequired(cells[i][j])
                    );
                    continue;
                }
                if (currentCell.col == j && currentCell.row == i) {
                    System.out.print(Constants.ANSI_BRIGHT_GREEN + Constants.PLAYER + sum + spaceRequired(cells[i][j]));
                } else {
                    System.out.print(Constants.ANSI_BRIGHT_GREEN +
                            OPERATION_TYPE.getOperationTag(cells[i][j].getOperationType())
                            + cells[i][j].getValue() + spaceRequired(cells[i][j])
                    );
                }
            }
            System.out.println();

        }
        System.out.println("-----------------------------------------");

    }

    public int pathCost() {
        return switch (currentCell.getOperationType()) {
            case MINUS, INCREASE_GOAL, GOAL -> 1;
            case ADD, DECREASE_GOAL -> 2;
            case MULT -> 5;
            case POW -> 11;
            default -> 0;
        };
    }

    private String spaceRequired(Cell cell) {
        int length = String.valueOf(cell.getValue()).length();
        String result = " ".repeat(5 - length);
        if (cell.op.equals("+") || cell.op.equals("-") || cell.op.equals("*") || cell.op.equals("^")) {
            result += " ";
        }
        return result;
    }

    @Override
    public String toString() {
        return "(" + this.currentCell.row + "," + this.currentCell.col + ")";
    }

    public boolean equalsCoordinates(Node node) {
        return currentCell.row == node.currentCell.row && currentCell.col == node.currentCell.col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return node.hash().equals(hash());
    }

    @Override
    public int hashCode() {
        return hash().hashCode();
    }
}
