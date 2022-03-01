package AI;

import model.Board;
import model.Node;

import java.util.*;

public class BDS extends SearchAlgo {

    private final Node start;
    private final Node goal;

    public BDS(Board board) {
        start = Node.create(board.getStart(), board.getStart().getValue(), board.getGoal().getValue(), board, null);

        Board reverseBoard = board.reverse();
        goal = Node.create(reverseBoard.getStart(), reverseBoard.getStart().getValue(), reverseBoard.getGoal().getValue(), reverseBoard, null);
    }

    @Override
    public SearchResult search() {
        Set<Node> visitedNodes = new HashSet<>();

        Queue<Node> startSideFringe = new LinkedList<>();
        startSideFringe.add(start);

        Queue<Node> goalSideFringe = new LinkedList<>();
        goalSideFringe.add(this.goal);

        while(!startSideFringe.isEmpty() && !goalSideFringe.isEmpty()) {
            var result = getResultIfFinished(startSideFringe, goalSideFringe, goal);
            if(result != null)
                return result;

            searchOneDepth(startSideFringe, visitedNodes);
            result = getResultIfFinished(startSideFringe, goalSideFringe, goal);
            if(result != null)
                return result;

            searchOneDepth(goalSideFringe, visitedNodes);
            result = getResultIfFinished(startSideFringe, goalSideFringe, goal);
            if(result != null)
                return result;
        }

        return SearchResult.failure();
    }

    private void searchOneDepth(Queue<Node> fringe, Set<Node> visitedNodes) {
        if(fringe.isEmpty())
            return;

        Node current = fringe.poll();
        while(visitedNodes.contains(current)) {
            current = fringe.poll();
        }

        if(current == null)
            return;

        visitedNodes.add(current);

        List<Node> children = current.successor();
        for(Node child : children) {
            if(!fringe.contains(child) && !visitedNodes.contains(child)) {
                fringe.add(child);
            }
        }
    }

    private SearchResult getResultIfFinished(Collection<Node> startSideFringe, Collection<Node> goalSideFringe, Node goal) {
        Node common = getCommonNode(startSideFringe, goalSideFringe);
        if(common != null) {
            List<Node> path = buildWholePath(startSideFringe, goalSideFringe);
            if(isSolutionFound(path, goal)) {
                return SearchResult.success(path);
            }
        }

        return null;
    }

    private Node getCommonNode(Collection<Node> first, Collection<Node> second) {
        return first.stream()
                .filter(second::contains)
                .findFirst()
                .orElse(null);
    }

    private List<Node> buildWholePath(Collection<Node> first, Collection<Node> second) {
        Node firstNode = getCommonNode(first, second);
        Node secondNode = getCommonNode(second, first);

        List<Node> firstHalfPath = buildPath(firstNode);
        List<Node> wholePath = new ArrayList<>(firstHalfPath);

        List<Node> secondHalfPath = buildPath(secondNode);
        secondHalfPath.remove(secondNode); // Remove repetitive existing in the first half
        Collections.reverse(secondHalfPath); // Because we have traversed reverse board :)

        wholePath.addAll(secondHalfPath);

        return wholePath;
    }

    private boolean isSolutionFound(List<Node> path, Node goal) {
        int goalScore = goal.currentCell.getValue();
        int currentScore = 0;

        for(Node node : path) {
            if(node.equals(goal))
                break;

            int value = node.currentCell.getValue();
            switch (node.currentCell.getOperationType()) {
                case MINUS -> currentScore -= value;
                case ADD -> currentScore += value;
                case START -> currentScore = value;
                case MULT -> currentScore *= value;
                case POW -> currentScore = (int) Math.pow(currentScore, value);
                case DECREASE_GOAL -> goalScore -= value;
                case INCREASE_GOAL -> goalScore += value;
            }
        }

        return currentScore >= goalScore;
    }

}
