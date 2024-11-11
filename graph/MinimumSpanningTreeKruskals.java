package graph;

import java.util.*;

public class MinimumSpanningTreeKruskals {

    /*********************************************************************************************************************************
     * Time Complexity  : O(E * log E + E * α(V)) ≈ O(E * log E)
     *                    Sorting edges takes O(E * log E) time, where E is the number of edges. Union-Find operations have nearly 
     *                    constant time complexity, O(α(V)), for each union/find due to path compression and union by rank.
     * Space Complexity : O(E + V)
     *                    Storing the graph as an adjacency list requires O(E) space. Additional space is used for the MST edge list 
     *                    and the disjoint set structure.
     *********************************************************************************************************************************
     * 1. Graph Initialization:
     *      Initialize the graph with a specified number of vertices and a directed/undirected flag.
     *      Create an adjacency list for each vertex to store edges and weights.
     * 2. Add Edges:
     *      For a directed graph: Add an edge from the source to the destination.
     *      For an undirected graph: Add both the forward and reverse edges.
     * 3. Initialize Edge Queue:
     *      Add all edges to a priority queue sorted by weight to prioritize edges with the smallest weights.
     * 4. MST Construction using Kruskal's Algorithm:
     *      Initialize MST weight and an empty list for MST edges.
     *      While the queue is not empty:
     *          Extract the minimum-weight edge from the priority queue.
     *          Check if this edge forms a cycle using the Disjoint Set data structure.
     *          If no cycle is formed, add the edge to the MST and merge the components.
     * 5. Output:
     *      Print the total weight and edges of the Minimum Spanning Tree (MST).
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public MinimumSpanningTreeKruskals(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize adjacency list for each vertex
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        this.adj[source].add(new Edge(source, destination, weight));

        // For undirected graph, add the reverse edge as well
        if (!this.directed) {
            this.adj[destination].add(new Edge(destination, source, weight));
        }
    }

    public void execute() {
        int mstWeight = 0;
        List<Edge> mst = new ArrayList<>();
        Queue<Edge> queue = new PriorityQueue<>();

        // Add all edges to the priority queue
        for (int i = 0; i < this.vertices; i++) {
            for (Edge edge : this.adj[i]) {
                queue.offer(edge);
            }
        }

        // Initialize Disjoint Set for cycle detection and merging components
        DisjointSetByRank disjointSet = new DisjointSetByRank(this.vertices);

        // While there are edges in the queue and MST is not complete
        while (!queue.isEmpty()) {
            Edge current = queue.poll();

            // Check if the edge forms a cycle
            if (disjointSet.isConnected(current.source, current.destination)) {
                continue;
            }

            // If no cycle is formed, add the edge to the MST
            mst.add(current);
            mstWeight += current.weight;
            disjointSet.union(current.source, current.destination);
        }

        // Print the MST weight and path
        System.out.println("MST Weight: " + mstWeight);
        System.out.print("MST Path: ");
        for (Edge edge : mst) {
            System.out.print("{" + edge.source + "-" + edge.destination + " : " + edge.weight + "}, ");
        }
    }

    public static void main(String[] args) {
        MinimumSpanningTreeKruskals graph = new MinimumSpanningTreeKruskals(5, true);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 3, 6);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 8);
        graph.addEdge(1, 4, 5);
        graph.addEdge(2, 4, 7);
        graph.addEdge(3, 4, 9);
        graph.execute();

        // MST Weight : 16
        // MST Path : {0-1 : 2}, {1-2 : 3}, {1-4 : 5}, {0-3 : 6},
    }

    static class Edge implements Comparable<Edge> {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return this.weight - edge.weight;
        }
    }
}
