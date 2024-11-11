package graph;

import java.util.*;

public class MinimumSpanningTreePrims {

    /*********************************************************************************************************************************
     * Time Complexity  : O((V + E) * log V) where V is the number of vertices and E is the number of edges.
     *                    Each vertex and edge is processed once, and each insertion into the priority queue takes O(log V) time.
     * Space Complexity : O(V + E) for storing the graph (adjacency list) and additional data structures.
     *********************************************************************************************************************************
     * Steps:
     * 1. Graph Initialization:
     *      Initialize the graph with a specified number of vertices and a directed/undirected flag.
     *      Create an adjacency list for each vertex to store edges and weights.
     * 2. Add Edges:
     *      For a directed graph: Add an edge from the source to the destination.
     *      For an undirected graph: Add both the forward and reverse edges.
     * 3. Execute Prim's Algorithm:
     *      Initialize the MST weight, visited array, MST edge list, and priority queue.
     *      Start from an arbitrary vertex (vertex 0).
     * 4. MST Construction Loop:
     *      Continuously extract the minimum-weight edge from the priority queue.
     *      Skip edges leading to already-visited vertices.
     *      Mark the current vertex as visited and add it to the MST.
     *      For each unvisited adjacent vertex, add its edge to the priority queue.
     * 5. Output:
     *      Print the total weight and edges of the Minimum Spanning Tree (MST).
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public MinimumSpanningTreePrims(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize adjacency lists for each vertex
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        this.adj[source].add(new Edge(source, destination, weight));

        // If the graph is undirected, add the reverse edge as well
        if (!this.directed) {
            this.adj[destination].add(new Edge(destination, source, weight));
        }
    }

    public void execute() {
        int mstWeight = 0;
        boolean[] visited = new boolean[this.vertices];
        List<Edge> mst = new ArrayList<>();
        Queue<Edge> queue = new PriorityQueue<>();

        // Start with vertex 0 (arbitrary start point for MST)
        queue.offer(new Edge(0, 0, 0));

        // Process until we have included vertices in MST or all edges are processed
        while (!queue.isEmpty() && mst.size() < this.vertices - 1) {
            Edge current = queue.poll();

            // Skip if the destination vertex is already included in MST
            if (visited[current.destination]) {
                continue;
            }

            // Mark the current vertex as visited and include the edge in MST
            visited[current.destination] = true;
            if (current.source != current.destination) {
                mst.add(current);
                mstWeight += current.weight;
            }

            // Add all edges from the current vertex to the priority queue
            for (Edge edge : this.adj[current.destination]) {
                if (!visited[edge.destination]) {
                    queue.offer(edge);
                }
            }
        }

        // Print the MST weight and path
        System.out.println("MST Weight: " + mstWeight);
        System.out.print("MST Path: ");
        for (Edge edge : mst) {
            System.out.print("{" + edge.source + "-" + edge.destination + " : " + edge.weight + "}, ");
        }
    }

    public static void main(String[] args) {
        MinimumSpanningTreePrims graph = new MinimumSpanningTreePrims(5, true);
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
        // Compare edges based on their weight for priority queue ordering
        public int compareTo(Edge edge) {
            return weight - edge.weight;
        }
    }
}
