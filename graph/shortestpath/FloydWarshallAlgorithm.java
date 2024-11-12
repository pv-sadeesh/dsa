package graph.shortestpath;

import java.util.ArrayList;
import java.util.List;

public class FloydWarshallAlgorithm {

    /*********************************************************************************************************************************
     * Time Complexity  : O(v^3), where V is the number of vertices.
     * Space Complexity : O(v^2), for the dist[][] and next[][] matrices.
     *********************************************************************************************************************************
     * 1. Initialization:
     *      Create a matrix dist[][] where dist[i][j] represents the shortest distance from vertex i to j.
     *      Set dist[i][i] = 0 for all vertices i (distance to self is zero).
     *      For each edge (u, v) with weight w, set dist[u][v] = w (or both dist[u][v] and dist[v][u] = w for undirected graphs).
     *      If there's no edge between u and v, set dist[u][v] = ∞.
     * 2. Main Computation:
     *      For each vertex k (acting as an intermediate vertex):
     *      For each pair of vertices (i, j):
     *      Update dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]).
     *      This checks if the path from i to j through k is shorter than the current known path.
     * 3. Path Reconstruction (Optional):
     *      Use a next[][] matrix to track the next node in the shortest path for each pair (i, j).
     *      Initialize next[i][j] = j if dist[i][j] has a direct edge.
     *      Update next[i][j] = next[i][k] whenever dist[i][j] is updated via k.
     *      To reconstruct the path from i to j, start at i and repeatedly follow next[i][j] until reaching j.
     * 4. Result:
     *      After processing all vertices, dist[i][j] holds the shortest distance between each pair (i, j).
     *      next[i][j] can be used to reconstruct the path if needed.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private int[][] graph;

    public FloydWarshallAlgorithm(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.graph = new int[vertices][vertices];
    }

    public void addEdge(int source, int destination, int weight) {
        if (source >= this.vertices) {
            throw new IllegalArgumentException("Source index out of bounds");
        }

        if (destination >= this.vertices) {
            throw new IllegalArgumentException("Destination indes out of bounds");
        }

        this.graph[source][destination] = weight;

        // If the graph is undirected, add an edge in both directions
        if (!this.directed) {
            this.graph[destination][source] = weight;
        }
    }

    public ShortestPathInfo[][] execute() {
        // Initialize output matrix to store shortest path info (distance and next vertex)
        ShortestPathInfo[][] output = new ShortestPathInfo[this.vertices][this.vertices];

        // Step 1: Initialize distances based on direct edges or zero for same vertex
        for (int i = 0; i < this.vertices; i++) {
            for (int j = 0; j < this.vertices; j++) {
                // Initialize each path as unreachable with distance 'infinity'
                output[i][j] = new ShortestPathInfo(-1, Integer.MAX_VALUE);

                // Distance from a vertex to itself is zero
                if (i == j) {
                    output[i][j].distance = 0;
                    output[i][j].next = i;
                }
                // If there is an edge between i and j, initialize distance and next
                else if (graph[i][j] != 0) {
                    output[i][j].distance = graph[i][j];
                    output[i][j].next = j;
                }
            }
        }

        // Step 2: Update paths using intermediate vertices
        for (int k = 0; k < this.vertices; k++) {
            for (int i = 0; i < this.vertices; i++) {
                for (int j = 0; j < this.vertices; j++) {
                    // If a shorter path exists through vertex 'k', update distance and path
                    if (isSmallerPath(output, i, j, k)) {
                        output[i][j].distance = output[i][k].distance + output[k][j].distance;
                        output[i][j].next = output[i][k].next;
                    }
                }
            }
        }

        return output;
    }

    private boolean isSmallerPath(ShortestPathInfo[][] output, int source, int destination, int via) {
        // Return false if either segment of the path is unreachable
        if (output[source][via].distance == Integer.MAX_VALUE)
            return false;
        if (output[via][destination].distance == Integer.MAX_VALUE)
            return false;

        // Calculate the new path distance via 'via' vertex and compare
        int viaDistance = output[source][via].distance + output[via][destination].distance;
        return viaDistance < output[source][destination].distance;
    }

    public List<Integer> getPath(int source, int destination, ShortestPathInfo[][] output) {
        List<Integer> path = new ArrayList<>();

        // If no path exists, return an empty list
        if (output[source][destination].next == -1) {
            return path;
        }

        // Follow the 'next' pointers to build the path list
        int current = source;
        while (current != destination) {
            path.add(current);
            current = output[current][destination].next;
        }

        path.add(destination);

        return path;
    }

    public static void main(String[] args) {
        FloydWarshallAlgorithm graph = new FloydWarshallAlgorithm(4, true);
        graph.addEdge(0, 1, 3);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 7);
        graph.addEdge(3, 0, 2);
        ShortestPathInfo[][] output = graph.execute();

        // Display the shortest paths and distances for all vertex pairs
        if (output != null) {
            for (int i = 0; i < output.length; i++) {
                for (int j = 0; j < output[i].length; j++) {
                    if (output[i][j].distance != Integer.MAX_VALUE) {
                        System.out.println(i + " -> " + j
                                + ", Distance: " + output[i][j].distance
                                + ", Path: " + graph.getPath(i, j, output));
                    }
                }
            }
        }

        // (0)----3---->(1)----2---->(2)----7---->(3)
        //  ^                                      |
        //  |                                      |
        //  ------------------2---------------------

        // 0 -> 0, Distance: 0,     Path: []
        // 0 -> 1, Distance: 3,     Path: [0, 1]
        // 0 -> 2, Distance: 5,     Path: [0, 1, 2]
        // 0 -> 3, Distance: 12,    Path: [0, 1, 2, 3]
        // 1 -> 0, Distance: 11,    Path: [1, 2, 3, 0]
        // 1 -> 1, Distance: 0,     Path: []
        // 1 -> 2, Distance: 2,     Path: [1, 2]
        // 1 -> 3, Distance: 9,     Path: [1, 2, 3]
        // 2 -> 0, Distance: 9,     Path: [2, 3, 0]
        // 2 -> 1, Distance: 12,    Path: [2, 3, 0, 1]
        // 2 -> 2, Distance: 0,     Path: []
        // 2 -> 3, Distance: 7,     Path: [2, 3]
        // 3 -> 0, Distance: 2,     Path: [3, 0]
        // 3 -> 1, Distance: 5,     Path: [3, 0, 1]
        // 3 -> 2, Distance: 7,     Path: [3, 0, 1, 2]
        // 3 -> 3, Distance: 0,     Path: []
    }

    static class ShortestPathInfo {
        int next;
        int distance;

        public ShortestPathInfo(int next, int distance) {
            this.next = next;
            this.distance = distance;
        }
    }
}
