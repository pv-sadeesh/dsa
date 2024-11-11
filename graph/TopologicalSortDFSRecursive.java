package graph;

import java.util.*;

public class TopologicalSortDFSRecursive {

    /*********************************************************************************************************************************
     * Time Complexity      : O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity     : O(V), due to the visited array and the recursion stack used during DFS traversal.
     *********************************************************************************************************************************
     * 1. Graph Initialization:
     *      Initialize the graph with the number of vertices and the directed/undirected type.
     *      Create an adjacency list for each vertex.
     * 2. Add Edges:
     *      For directed graph: Add an edge from source to destination.
     *      For undirected graph: Add the reverse edge as well.
     * 3. Topological Sort Execution:
     *      Initialize a visited array to track visited nodes.
     *      Initialize an empty stack `sorted` to store topologically sorted nodes.
     * 4. DFS Recursion (for each unvisited vertex):
     *      Start DFS from an unvisited vertex.
     *      Mark the current node as visited.
     *      Recursively call DFS for each unvisited adjacent node.
     *      After all adjacent nodes are visited (i.e., the node has no more unvisited neighbors),
     *      push the current node onto the `sorted` stack.
     * 5. Print Topologically Sorted Nodes:
     *      After all nodes are processed, pop the nodes from the `sorted` stack and print them in topological order.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public TopologicalSortDFSRecursive(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize the adjacency list for each vertex
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination) {
        // Add edge from source to destination if not already present
        if (!this.adj[source].contains(destination)) {
            this.adj[source].add(destination);
        }

        // For undirected graph, add the reverse edge as well
        if (!this.directed && !this.adj[destination].contains(source)) {
            this.adj[destination].add(source);
        }
    }

    public void execute() {
        boolean[] visited = new boolean[this.vertices];
        Stack<Integer> sorted = new Stack<>();

        // Call DFS for each unvisited node
        for (int i = 0; i < this.vertices; i++) {
            if (!visited[i]) {
                execute(i, visited, sorted);
            }
        }

        // Print the topologically sorted nodes
        System.out.println("Topological Sorted Nodes:");
        while (!sorted.isEmpty()) {
            System.out.print(sorted.pop() + ", ");
        }
    }

    private void execute(int node, boolean[] visited, Stack<Integer> sorted) {
        // If the node is not visited, visit it
        if (!visited[node]) {
            visited[node] = true;
            System.out.print(node + ", ");

            // Recurse for all unvisited adjacent nodes
            for (int adjNode : this.adj[node]) {
                if (!visited[adjNode]) {
                    execute(adjNode, visited, sorted);
                }
            }

            // After exploring all adjacent nodes, push the current node to the sorted stack
            sorted.push(node);
        }
    }

    public static void main(String[] args) {
        TopologicalSortDFSIterative graph = new TopologicalSortDFSIterative(6, true);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        graph.execute();

        // DFS Output : 0, 1, 2, 3, 4, 5
        // Topological Sort Output : 5, 4, 2, 3, 1, 0
    }
}
