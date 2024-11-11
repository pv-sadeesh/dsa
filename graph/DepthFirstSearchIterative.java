package graph;

import java.util.*;

public class DFS_Iterative {

    /*********************************************************************************************************************************
     * Time Complexity      : O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity     : O(V), due to the visited array and stack used during traversal.
     *********************************************************************************************************************************
     * 1. Initialize:
     *      Use a Stack to track nodes.
     *      Create a visited array.
     * 2. Push Start Node:
     *      Mark the start node as visited.
     *      Push it onto the stack.
     * 3. Process Stack:
     *      While the stack is not empty:
     *          Pop a node, process it.
     *          For each unvisited neighbor, mark it visited and push it onto the stack.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public DFS_Iterative(int vertices, boolean directed) {
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
        boolean[] visited = new boolean[this.vertices];

        // Iterate through all the vertices to handle disconnected components
        for (int i = 0; i < this.vertices; i++) {
            // If the vertex hasn't been visited yet, start a DFS traversal from it
            if (!visited[i]) {
                execute(i, visited);
            }
        }
    }

    private void execute(int startNode, boolean[] visited) {
        // Stack to track the nodes for DFS traversal
        Stack<Integer> stack = new Stack<>();

        // Push the start node onto the stack
        stack.push(startNode);

        // Continue until the stack is empty
        while (!stack.isEmpty()) {
            // Pop a node from the stack
            int current = stack.pop();

            // If the node has not been visited yet, process it
            if (!visited[current]) {
                visited[current] = true;
                System.out.print(current + ", ");

                // Push all unvisited neighbors of the current node onto the stack
                for (int adjNode : this.adj[current]) {
                    if (!visited[adjNode]) {
                        stack.push(adjNode);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        DFS_Iterative graph = new DFS_Iterative(6, true);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        graph.execute();
        // Result: 0, 2, 4, 5, 1, 3,
    }
}
