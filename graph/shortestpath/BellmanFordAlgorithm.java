package graph.shortestpath;

import java.util.*;

public class BellmanFordAlgorithm {

    /*********************************************************************************************************************************
     * Time Complexity  : O(V * E) + O(E) = O(V * E)
     * Space Complexity : O(V + E)
     *********************************************************************************************************************************
     * 1. Initialization:
     *      Create an array distance[] to store the shortest distance from the source to each vertex. 
     *      Set all distances to infinity (Integer.MAX_VALUE), except the source vertex which is set to 0.
     *      Create an array from[] to store the predecessor of each vertex, initialized to -1.
     * 2. Relaxation:
     *      Repeat the following for (vertices - 1) times:
     *      For each edge (u, v) with weight w:
     *      If distance[u] + w < distance[v], update distance[v] = distance[u] + w and from[v] = u.
     * 3. Negative Cycle Detection:
     *      After the relaxation step, for each edge (u, v), check if distance[u] + w < distance[v]:
     *      If true, it indicates a negative weight cycle in the graph.
     * 4. Path Reconstruction:
     *      To find the shortest path to any vertex, trace back using the from[] array from the destination vertex to the source.
     * 5. Result:
     *      Return the shortest distances and paths from the source to all other vertices.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Edge> edges;

    public BellmanFordAlgorithm(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int source, int destination, int weight) {
        // Check if the source and destination are within bounds
        if (source >= this.vertices) {
            throw new IllegalArgumentException("Source index out of bounds");
        }

        if (destination >= this.vertices) {
            throw new IllegalArgumentException("Destination indes out of bounds");
        }

        // Add the edge from source to destination
        this.edges.add(new Edge(source, destination, weight));

        // If the graph is undirected, add the reverse edge as well
        if (!this.directed) {
            this.edges.add(new Edge(destination, source, weight));
        }
    }

    public ShortestPathInfo[] execute(int source) {
        // Array to store shortest path information for each vertex
        ShortestPathInfo[] output = new ShortestPathInfo[this.vertices];

        // Initialize all vertices with infinite distance and no 'from' vertex
        for (int i = 0; i < this.vertices; i++) {
            output[i] = new ShortestPathInfo(-1, Integer.MAX_VALUE);
        }

        // Set the distance to the source vertex as 0
        output[source].distance = 0;

        // Relax all edges (vertices-1) times
        for (int k = 1; k < this.vertices; k++) {
            // Iterate through all edges
            for (Edge edge : this.edges) {
                ShortestPathInfo sourceInfo = output[edge.source];
                ShortestPathInfo destinationInfo = output[edge.destination];

                // If a shorter path is found, update the destination vertex's distance and
                // 'from' vertex
                if (sourceInfo.distance != Integer.MAX_VALUE
                        && sourceInfo.distance + edge.weight < destinationInfo.distance) {
                    destinationInfo.distance = sourceInfo.distance + edge.weight;
                    destinationInfo.from = edge.source;
                }
            }
        }

        // Check for negative weight cycles in the graph
        for (Edge edge : this.edges) {
            ShortestPathInfo sourceInfo = output[edge.source];
            ShortestPathInfo destinationInfo = output[edge.destination];

            // If a shorter path is found, it indicates a negative weight cycle
            if (sourceInfo.distance != Integer.MAX_VALUE
                    && sourceInfo.distance + edge.weight < destinationInfo.distance) {
                throw new IllegalArgumentException("Graph contains negative weight cycles.");
            }
        }

        return output;
    }

    public int[] getPathTo(int destination, ShortestPathInfo[] data) {
        if (destination >= data.length) {
            throw new IllegalArgumentException("Destination index out of bounds.");
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(destination);

        // Trace the path back from destination to source using the 'from' information
        ShortestPathInfo info = data[destination];
        while (info.from != -1) {
            stack.push(info.from);
            info = data[info.from];
        }

        // Convert the path stack to an array
        int[] path = new int[stack.size()];
        int index = 0;
        while (!stack.isEmpty()) {
            path[index++] = stack.pop();
        }

        return path;
    }

    public static void main(String[] args) {
        BellmanFordAlgorithm graph = new BellmanFordAlgorithm(6, true);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(2, 3, 5);
        graph.addEdge(1, 3, 1);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 5, 1);
        ShortestPathInfo[] output = graph.execute(0);

        // Print shortest path from source node '0' to all other nodes
        System.out.println("Shortest Path From Node '0'");
        for (int i = 0; i < output.length; i++) {
            ShortestPathInfo info = output[i];
            System.out.println("Node: " + i +
                    ", From: " + info.from +
                    ", Distance: " + info.distance +
                    ", Path: " + Arrays.toString(graph.getPathTo(i, output)));
        }

        // Node: 0, From: -1, Distance: 0, Path: [0]
        // Node: 1, From: 2, Distance: 3, Path: [0, 2, 1]
        // Node: 2, From: 0, Distance: 1, Path: [0, 2]
        // Node: 3, From: 1, Distance: 4, Path: [0, 2, 1, 3]
        // Node: 4, From: 3, Distance: 7, Path: [0, 2, 1, 3, 4]
        // Node: 5, From: 4, Distance: 8, Path: [0, 2, 1, 3, 4, 5]
    }

    static class ShortestPathInfo {
        int from;
        int distance;

        public ShortestPathInfo(int from, int distance) {
            this.from = from;
            this.distance = distance;
        }
    }

    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
}
