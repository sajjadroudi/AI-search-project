package AI;

import model.Node;

public class IDS extends SearchAlgo {

    @Override
    public SearchResult search(Node startNode) {
        DFS dfs = new DFS();
        int lastMaxTraversedDepth = -1;
        int i = 0;
        while(true) {
            SearchResult result = dfs.search(Node.copy(startNode), i);

            if(result.isSuccessful())
                return result;

            if(result.getMaxDepthTraversed() == lastMaxTraversedDepth)
                return SearchResult.failure();

            lastMaxTraversedDepth = result.getMaxDepthTraversed();

            i++;
        }
    }

}
