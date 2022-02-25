package AI;

import model.Node;

import java.util.*;

public class DFS extends SearchAlgo {

    @Override
    public void search(Node startNode) {
        Stack<Node> fringe = new Stack<>();
        Set<Node> visitedNodes = new HashSet<>();

        if (startNode.isGoal()) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        fringe.push(startNode);
        while(!fringe.isEmpty()) {
            Node current = fringe.pop();

            List<Node> children = current.successor();
            Collections.reverse(children); // To convert to desired visit order

            for(Node child : children) {
                if(fringe.contains(child))
                    continue;

                if(child.isGoal()) {
                    printResult(child, 0);
                    System.out.println(child.sum);
                    return;
                }

                fringe.push(child);
            }

        }

        System.out.println("no solution");
    }

}
