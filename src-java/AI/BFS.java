package AI;

import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends SearchAlgo {

    public SearchResult search(Node startNode) {
        Queue<Node> frontier = new LinkedList<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (startNode.isGoal()) {
            return buildResult(startNode);
        }
        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);
        while (!frontier.isEmpty()) {
            Node temp = frontier.poll();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(), true);
            ArrayList<Node> children = temp.successor();
            for (Node child : children) {
                if (!inFrontier.containsKey(child.hash()) && !explored.containsKey(child.hash())) {
                    if (child.isGoal()) {
                        return buildResult(child);
                    }
                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
                }
            }
        }

        return SearchResult.failure();

    }

}
