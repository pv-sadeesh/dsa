package graph;

import java.util.*;

public class TopologicalSortKhansInDegree {

    /*********************************************************************************************************************************
     * Time Complexity      : O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity     : O(V), due to the in-degree array, adjacency list, and the queue used during traversal.
     *********************************************************************************************************************************
     * 1. Calculate In-Degree:
     *      Initialize an array to store in-degrees for all vertices.
     *      For each vertex, traverse its adjacency list and increment the in-degree of adjacent vertices.
     * 2. Initialize Queue:
     *      Add all vertices with in-degree 0 (no dependencies) to a queue.
     * 3. Process Queue:
     *      While the queue is not empty:
     *          Dequeue a vertex, add it to the topologically sorted list.
     *          For each adjacent vertex, reduce its in-degree by 1.
     *          If an adjacent vertex's in-degree becomes 0, add it to the queue.
     * 4. Check for Cycles:
     *      If all vertices are processed (sorted list contains all vertices), print the topological order.
     *      If not, there is a cycle in the graph.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public TopologicalSortKhansInDegree(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize each vertex's adjacency list
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination) {
        // Add edge from source to destination
        if (!this.adj[source].contains(destination)) {
            this.adj[source].add(destination);
        }

        // For undirected graph, add reverse edge from destination to source
        if (!this.directed && !this.adj[destination].contains(source)) {
            this.adj[destination].add(source);
        }
    }

    public void execute() {
        // Step 1: Calculate in-degrees for all vertices
        int[] inDegree = new int[this.vertices];
        for (int i = 0; i < this.vertices; i++) {
            for (int adjNode : this.adj[i]) {
                inDegree[adjNode]++;
            }
        }

        // Step 2: Initialize a queue and add all nodes with in-degree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < this.vertices; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        // Step 3: Process nodes from the queue and update in-degrees of adjacent nodes
        List<Integer> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sorted.add(current);

            // For each adjacent node, reduce its in-degree and add it to the queue if in-degree becomes 0
            for (int adjNode : this.adj[current]) {
                // Decrease the in-degree of the adjacent node
                inDegree[adjNode]--;
                if (inDegree[adjNode] == 0) {
                    // Add to queue if in-degree is 0
                    queue.add(adjNode);
                }
            }
        }

        // Step 4: If all nodes are processed, print the topologically sorted order
        if (sorted.size() == this.vertices) {
            System.out.println("Topological Sorted Nodes:");
            for (int i : sorted) {
                System.out.print(i + ", ");
            }
        } else {
            // If not all nodes are processed, there is a cycle in the graph
            System.out.println("There is a cycle in the graph.");
        }
    }

    public static void main(String[] args) {
        TopologicalSortKhansInDegree graph = new TopologicalSortKhansInDegree(6, true);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        graph.execute();
        // Topological Sort Output: 4, 5, 2, 0, 3, 1
    }
}
