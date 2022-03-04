package AI;

import model.Node;

import java.util.*;

public class DFS extends SearchAlgo {

    private final Node startNode;

    public DFS(Node startNode) {
        this.startNode = new Node(startNode);
    }

    @Override
    public SearchResult search() {
        return search(Integer.MAX_VALUE);
    }

    public SearchResult search(final int maxDepth) {
        Node startNode = new Node(this.startNode);

        Stack<Node> fringe = new Stack<>();
        Map<Node, Integer> nodeDepths = new HashMap<>();

        if (startNode.isGoal()) {
            return buildResult(startNode);
        }

        fringe.push(startNode);
        nodeDepths.put(startNode, 0);

        int maxDepthTraversed = 0;

        while(!fringe.isEmpty()) {
            Node current = fringe.pop();
            int depth = nodeDepths.get(current);
            nodeDepths.remove(current);

            maxDepthTraversed = Math.max(depth, maxDepthTraversed);

            if(depth >= maxDepth) {
                continue;
            }

            List<Node> children = current.successor();
            Collections.reverse(children); // To convert to desired visit order

            for(Node child : children) {
                if(fringe.contains(child))
                    continue;

                if(child.isGoal()) {
                    return buildResult(child);
                }

                fringe.push(child);
                nodeDepths.put(child, depth + 1);
            }

        }

        SearchResult result = SearchResult.failure();
        result.setMaxDepthTraversed(maxDepthTraversed);
        return result;
    }

}
