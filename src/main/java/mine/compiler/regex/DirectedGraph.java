package mine.compiler.regex;

import java.util.ArrayList;
import java.util.List;

/**
 * DirectedGraph
 */
public class DirectedGraph {
    /** V个节点 */
    private int V;
    /** DFS标记，每次调用DFS之前初始化，清零 */
    private boolean[] marked;

    public DirectedGraph(int V) {
        this.V = V;
        lists = new List[V];
    }

    private List<Integer>[] lists;

    private List<Integer> adj(int v) {
        return lists[v];
    }

    public void addEdge(DirectedEdge edge) {
        int from = edge.from;
        if (lists[from] == null)
            lists[from] = new ArrayList<>();
        lists[from].add(edge.to);
    }

    /**
     * 从节点v开始深度优先搜索，能够到达的所有结点的集合
     * 
     * @param v :开始搜寻的节点
     * @return sources: {@link List}{@code<Integer>} 能够到达的所有节点的集合
     */
    public List<Integer> directedDFS(int v) {
        List<Integer> sources = new ArrayList<>();
        marked = new boolean[V];
        for (var vertex : adj(v)) {
            dfs(vertex, sources);
        }
        return sources;
    }

    private void dfs(int vertex, List<Integer> sources) {
        marked[vertex] = true;
        sources.add(vertex);
        for (var v : adj(vertex)) {
            dfs(v, sources);
        }
    }
}
