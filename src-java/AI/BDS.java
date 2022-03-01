package AI;

import model.Board;
import model.Node;

import java.util.*;

public class BDS extends SearchAlgo {

    private final Node startForwardNode;
    private final Node startBackwardNode;

    public BDS(Board board) {
        Hashtable<String, Boolean> initHash = new Hashtable<>();
        initHash.put(board.getStart().toString(), true);
        startForwardNode = Node.create(board.getStart(), board.getStart().getValue(), board.getGoal().getValue(), board, null, initHash);

        Board reverseBoard = board.reverse();
        Hashtable<String, Boolean> initHash2 = new Hashtable<>();
        initHash2.put(reverseBoard.getStart().toString(), true);
        startBackwardNode = Node.create(reverseBoard.getStart(), reverseBoard.getStart().getValue(), reverseBoard.getGoal().getValue(), reverseBoard, null, initHash2);
    }

    @Override
    public SearchResult search() {
        BFS startSide = new BFS(startForwardNode);
        BFS goalSide = new BFS(startBackwardNode);

        Set<Node> visitedNodes = new HashSet<>();

        Queue<Node> startSideFringe = new LinkedList<>();
        startSideFringe.add(startForwardNode);

        Queue<Node> goalSideFringe = new LinkedList<>();
        goalSideFringe.add(startBackwardNode);

        while(!startSideFringe.isEmpty() && !goalSideFringe.isEmpty()) {
            var result = checkIfFinished(startSideFringe, goalSideFringe, startBackwardNode);
            if(result != null)
                return result;

            startSide.searchOneDepth(startSideFringe, visitedNodes);
            result = checkIfFinished(startSideFringe, goalSideFringe, startBackwardNode);
            if(result != null)
                return result;

            goalSide.searchOneDepth(goalSideFringe, visitedNodes);
            result = checkIfFinished(startSideFringe, goalSideFringe, startBackwardNode);
            if(result != null)
                return result;
        }

        return SearchResult.failure();
    }

    private SearchResult checkIfFinished(Collection<Node> startSideFringe, Collection<Node> goalSideFringe, Node goal) {
        Node common = getCommonItem(startSideFringe, goalSideFringe);
        if(common != null) {
            List<Node> path = buildWholePath(startSideFringe, goalSideFringe);
            if(isSolutionFound(path, goal)) {
                return SearchResult.success(path);
            }
        }

        return null;
    }

    private Node getCommonItem(Collection<Node> first, Collection<Node> second) {
        return first.stream()
                .filter(second::contains)
                .findFirst()
                .orElse(null);
    }

    private List<Node> buildWholePath(Collection<Node> first, Collection<Node> second) {
        Node firstNode = getCommonItem(first, second);
        Node secondNode = getCommonItem(second, first);

        List<Node> result = new ArrayList<>(buildPath(firstNode));

        List<Node> secondPath = buildPath(secondNode);
        secondPath.remove(secondNode);
        Collections.reverse(secondPath);

        result.addAll(secondPath);

        return result;
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
