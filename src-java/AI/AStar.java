package AI;

import model.Cell;
import model.Node;

import java.util.*;

public class AStar extends SearchAlgo {

    private final Node startNode;

    public AStar(Node node) {
        this.startNode = node;
    }

    @Override
    public SearchResult search() {
        Node start = new Node(startNode);
        Map<Node, Integer> traversedPathCost = new HashMap<>();

        Queue<Node> fringe = new PriorityQueue<>(
                Comparator.comparingInt(node -> traversedPathCost.get(node) + heuristic(node))
        );

        fringe.add(start);
        traversedPathCost.put(start, 0);

        while(!fringe.isEmpty()) {
            var current = fringe.poll();

            if(current.isGoal()) {
                return buildResult(current);
            }

            int cost = traversedPathCost.get(current);

            var children = current.successor();
            for(var child : children) {
                int childPathCost = cost + child.pathCost();
                traversedPathCost.put(child, childPathCost);
                fringe.add(child);
            }
        }

        return SearchResult.failure();
    }

    private int heuristic(Node node) {
        Cell goal = node.board.getGoal();
        Cell cell = node.currentCell;
        return Math.abs(goal.row - cell.row) + Math.abs(goal.col - cell.col);
    }

    // TODO : I don't know if it' needed
    private boolean existsInFringe(Node node, Queue<Node> fringe, Map<Node, Integer> pathCost, int childPathCost) {
        return fringe.contains(node) && pathCost.get(node) == childPathCost;
    }

}
