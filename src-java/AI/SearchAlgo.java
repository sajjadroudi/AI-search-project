package AI;

import model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SearchAlgo {

    public abstract SearchResult search(Node startNode);

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
        List<Node> path = new ArrayList<>();
        Node node = goalNode;
        for(int depth = 0; node.parent != null; depth++) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return SearchResult.success(path);
    }

}
