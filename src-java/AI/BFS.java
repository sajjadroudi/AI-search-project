package AI;

import model.Board;
import model.Node;

import java.util.*;

public class BFS extends SearchAlgo {

    public BFS(Node start) {
        super(start);
    }

    public SearchResult search() {
        return search(Integer.MAX_VALUE);
    }

    public SearchResult search(int maxDepth) {
        Node startNode = Node.copy(this.startNode);

        Queue<Node> frontier = new LinkedList<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Set<Node> visitedNodes = new HashSet<>();
        Map<Node, Integer> nodeDepths = new HashMap<>();

        if (startNode.isGoal()) {
            return buildResult(startNode);
        }
        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);
        nodeDepths.put(startNode, 0);
        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            inFrontier.remove(current.hash());
            int depth = nodeDepths.get(current);

            if(depth >= maxDepth) {
                return SearchResult.failure();
            }

            visitedNodes.add(current);
            List<Node> children = current.successor();
            for (Node child : children) {
                if (!inFrontier.containsKey(child.hash()) && !visitedNodes.contains(child)) {
                    if (child.isGoal()) {
                        return buildResult(child);
                    }
                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
                    nodeDepths.put(child, depth + 1);
                }
            }
        }

        return SearchResult.failure();
    }

    public void searchOneDepth(Queue<Node> fringe, Set<Node> visitedNodes) {
        if(!fringe.isEmpty()) {
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
    }

}
