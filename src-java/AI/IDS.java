package AI;

import model.Node;

public class IDS extends SearchAlgo {

    public IDS(Node startNode) {
        super(startNode);
    }

    @Override
    public SearchResult search() {
        DFS dfs = new DFS(Node.copy(this.startNode));
        int lastMaxTraversedDepth = -1;
        int depth = 0;
        while(true) {
            SearchResult result = dfs.search(depth);

            if(result.isSuccessful())
                return result;

            if(result.getMaxDepthTraversed() == lastMaxTraversedDepth)
                return SearchResult.failure();

            lastMaxTraversedDepth = result.getMaxDepthTraversed();

            depth++;
        }
    }

}
