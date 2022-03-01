package AI;

import model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SearchAlgo {

    public abstract SearchResult search();

    public void printResult(Node node, int depthCounter) {
        if (node.parent == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }

        System.out.println(node);
//        node.drawState();
        printResult(node.parent, depthCounter + 1);
    }

    public SearchResult buildResult(Node goalNode) {
        return SearchResult.success(buildPath(goalNode));
    }

    protected List<Node> buildPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        Node node = endNode;
        while(node.parent != null) {
            path.add(node);
            node = node.parent;
        }
        path.add(node);

        Collections.reverse(path);
        return path;
    }

}
