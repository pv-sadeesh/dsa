package graph;

import java.util.*;

public class DFS_Recursive {

    /*********************************************************************************************************************************
     * Time Complexity      : O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity     : O(V), due to the recursive call stack and the visited array.
     *********************************************************************************************************************************
     * 1. Define Recursive Helper:
     *      Mark the current node as visited.
     *      Process the current node.
     *      Recursively call DFS on each unvisited neighbor.
     * 2. Call Helper:
     *      For each unvisited node, call the helper function to handle disconnected components.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public DFS_Recursive(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize adjacency list for each vertex
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination) {
        // Add the destination to the adjacency list of source
        if (!this.adj[source].contains(destination)) {
            this.adj[source].add(destination);
        }

        // If the graph is undirected, also add the source to the adjacency list of
        // destination
        if (!this.directed && !this.adj[destination].contains(source)) {
            this.adj[destination].add(source);
        }
    }

    public void execute() {
        // To track visited nodes
        boolean[] visited = new boolean[this.vertices];

        // Iterate through all the vertices to handle disconnected components
        for (int i = 0; i < this.vertices; i++) {
            // If the vertex hasn't been visited yet, start a DFS traversal from it
            if (!visited[i]) {
                execute(i, visited);
            }
        }
    }

    private void execute(int node, boolean[] visited) {
        // If the node is not visited, process it
        if (!visited[node]) {
            visited[node] = true;
            System.out.print(node + ", ");

            // Recursively call DFS on all unvisited neighbors of the current node
            for (int adjNode : this.adj[node]) {
                if (!visited[adjNode]) {
                    // Recurse for each unvisited neighbor
                    execute(adjNode, visited);
                }
            }
        }
    }

    public static void main(String[] args) {
        DFS_Recursive graph = new DFS_Recursive(6, true);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        graph.execute();
        // Result: 0, 1, 3, 5, 4, 2
    }
}
