package AI;

import model.Node;

public abstract class SearchAlgo {

    public abstract void search(Node startNode);

    public void printResult(Node node, int depthCounter) {
        if (node.parent == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }

        System.out.println(node);
//        node.drawState();
        printResult(node.parent, depthCounter + 1);
    }

}
