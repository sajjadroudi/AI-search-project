package AI;

import model.Node;

import java.util.*;

public class BFS extends SearchAlgo {

    private final Node start;

    public BFS(Node startNode) {
        start = new Node(startNode);
    }

    @Override
    public SearchResult search() {
        return search(Integer.MAX_VALUE);
    }

    public SearchResult search(int maxDepth) {
        Node startNode = new Node(this.start);

        Queue<Node> fringe = new LinkedList<>();
        Set<Node> visitedNodes = new HashSet<>();
        Map<Node, Integer> nodeDepths = new HashMap<>();

        if (startNode.isGoal()) {
            return buildResult(startNode);
        }

        fringe.add(startNode);
        nodeDepths.put(startNode, 0);
        while (!fringe.isEmpty()) {
            Node current = fringe.poll();
            int depth = nodeDepths.get(current);

            if(depth >= maxDepth) {
                return SearchResult.failure();
            }

            visitedNodes.add(current);

            List<Node> children = current.successor();
            for (Node child : children) {
                if (!fringe.contains(child) && !visitedNodes.contains(child)) {
                    if (child.isGoal()) {
                        return buildResult(child);
                    }
                    fringe.add(child);
                    nodeDepths.put(child, depth + 1);
                }
            }
        }

        return SearchResult.failure();
    }

}
