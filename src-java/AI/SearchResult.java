package AI;

import model.Node;

import java.util.List;

public class SearchResult {

    private List<Node> path;
    private int maxDepthTraversed = -1;

    private SearchResult(List<Node> path) {
        this.path = path;
    }

    public Node[] getPath() {
        if(!isSuccessful())
            return null;
        return path.toArray(new Node[0]);
    }

    public String getFormattedPath() {
        if(!isSuccessful())
            return "-";

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < path.size(); i++) {
            builder.append(path.get(i));
            if(i != path.size() - 1) {
                builder.append(" -> ");
            }
        }

        return builder.toString();
    }

    public boolean isSuccessful() {
        return path != null && !path.isEmpty();
    }

    public int getDepth() {
        if(!isSuccessful())
            return -1;

        return path.size();
    }

    public void setMaxDepthTraversed(int maxDepthTraversed) {
        this.maxDepthTraversed = maxDepthTraversed;
    }

    public int getMaxDepthTraversed() {
        return maxDepthTraversed;
    }

    public static SearchResult failure() {
        return new SearchResult(null);
    }

    public static SearchResult success(List<Node> path) {
        return new SearchResult(path);
    }

}
