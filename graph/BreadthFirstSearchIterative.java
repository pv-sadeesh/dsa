package graph;

import java.util.*;;

public class BFS_Iterative {

    /*********************************************************************************************************************************
     * Time Complexity      : O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity     : O(V), due to the queue and visited array.
     *********************************************************************************************************************************
     * 1. Initialize:
     *      Create a Queue to track nodes for processing.
     *      Create a visited array to avoid revisiting nodes.
     * 2. Enqueue Start Node:
     *      Mark the start node as visited.
     *      Enqueue the start node.
     * 3. Process Queue:
     *      While the queue is not empty:
     *      Dequeue a node, process it (e.g., print or store it).
     *      For each unvisited neighbor, mark it visited and enqueue it.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public BFS_Iterative(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize adjacency list for each vertex
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination) {
        // Add destination to the adjacency list of source if not already present
        if (!this.adj[source].contains(destination)) {
            this.adj[source].add(destination);
        }

        // If the graph is undirected, add the reverse edge as well
        if (!this.directed && !this.adj[destination].contains(source)) {
            this.adj[destination].add(source);
        }
    }

    public void execute() {
        boolean[] visited = new boolean[this.vertices];

        // Perform BFS from each unvisited node to cover all components
        for (int i = 0; i < this.vertices; i++) {
            if (!visited[i]) {
                // Call BFS for each unvisited node
                execute(i, visited);
            }
        }
    }

    private void execute(int startNode, boolean[] visited) {
        // Queue for BFS traversal
        Queue<Integer> queue = new LinkedList<>();

        visited[startNode] = true; // Mark the start node as visited
        queue.offer(startNode); // Add the start node to the queue

        // Loop until the queue is empty
        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + ", ");

            // Visit each adjacent node of the current node
            for (int adjNode : this.adj[current]) {
                // Check if the adjacent node has been visited
                if (!visited[adjNode]) {
                    visited[adjNode] = true; // Mark it as visited
                    queue.offer(adjNode); // Add it to the queue
                }
            }
        }
    }

    public static void main(String[] args) {
        BFS_Iterative graph = new BFS_Iterative(6, true);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        graph.execute();
        // Result: 0, 1, 2, 3, 4, 5,
    }
}
