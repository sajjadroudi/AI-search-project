package AI;

import model.Cell;
import model.Node;

import java.util.*;

public class IDAStar extends SearchAlgo {

    private final Node startNode;

    public IDAStar(Node start) {
        this.startNode = new Node(start);
    }

    @Override
    public SearchResult search() {
        var cutOff = heuristic(startNode);
        while(cutOff != Integer.MAX_VALUE) {
            var result = dfs(cutOff);
            if(result.isSuccessful()) {
                return result;
            } else {
                cutOff = result.getNextCutOff();
            }
        }

        return SearchResult.failure();
    }

    private SearchResult dfs(int cutOff) {
        Map<Node, Integer> traversedPathCost = new HashMap<>();

        Stack<Node> fringe = new Stack<>();
        Node start = new Node(startNode);
        fringe.push(start);
        traversedPathCost.put(start, 0);

        var nextCutOff = Integer.MAX_VALUE;

        while(!fringe.isEmpty()) {
            Node current = fringe.pop();

            if(current.isGoal()) {
                return buildResult(current);
            }

            var f = heuristic(current) + traversedPathCost.get(current);
            if(f > cutOff) {
                nextCutOff = Math.min(nextCutOff, f);
            } else {
                var children = current.successor();
                for(Node child : children) {
                    fringe.push(child);
                    traversedPathCost.put(child, traversedPathCost.get(current) + child.pathCost());
                }
            }
        }

        var result = SearchResult.failure();
        result.setNextCutOff(nextCutOff);
        return result;
    }

    private int heuristic(Node node) {
        Cell goal = node.board.getGoal();
        Cell cell = node.currentCell;
        return Math.abs(goal.row - cell.row) + Math.abs(goal.col - cell.col);
    }

}
