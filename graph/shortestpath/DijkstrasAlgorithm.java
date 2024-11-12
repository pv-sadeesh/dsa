package graph.shortestpath;

import java.util.*;

public class DijkstrasAlgorithm {

    /*********************************************************************************************************************************
     * Time Complexity  : O((V+E)log V) = O(E Log V)
     * Space Complexity : O(V+E)
     *********************************************************************************************************************************
     * 1. Initialization:
     *      Set up the graph with a specified number of vertices and edges.
     *      Initialize an adjacency list for each vertex to store edges.
     *      Set up a ShortestPathInfo array to store distance and previous node for each vertex.
     * 2. Add Edges:
     *      Add edges between vertices, with weights.
     *      For undirected graphs, add edges in both directions.
     * 3. Execute Dijkstra's Algorithm:
     *      Set the distance to the source vertex as 0 and all others to infinity.
     *      Use a priority queue to explore the graph by selecting the vertex with the smallest distance.
     *      For each neighboring vertex, if a shorter path is found, update its distance and previous node.
     * 4. Retrieve Shortest Path:
     *      For each destination vertex, trace the path back to the source using the from field in ShortestPathInfo.
     *      Store the path in a stack and then convert it to an array.
     * 5. Print Results:
     *      Output the shortest distance, the previous node, and the path for each vertex.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public DijkstrasAlgorithm(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize each adjacency list for each vertex
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) {
        if (source >= this.vertices) {
            throw new IllegalArgumentException("Source index is out of bounds.");
        }
        if (destination >= this.vertices) {
            throw new IllegalArgumentException("Destination index is out of bounds.");
        }

        this.adj[source].add(new Edge(destination, weight));

        // If undirected, add an edge in the opposite direction
        if (!this.directed) {
            this.adj[destination].add(new Edge(source, weight));
        }
    }

    public ShortestPathInfo[] execute(int source) {
        ShortestPathInfo[] output = new ShortestPathInfo[this.vertices];

        // Initialize distances to infinity and source distance to zero
        for (int i = 0; i < this.vertices; i++) {
            output[i] = new ShortestPathInfo(-1, Integer.MAX_VALUE);
        }
        output[source].distance = 0;

        // Priority queue to explore nodes in increasing distance
        Queue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(source, 0));

        while (!queue.isEmpty()) {
            Edge current = queue.poll();
            ShortestPathInfo currentInfo = output[current.node];

            // Explore all neighbors of the current node
            for (Edge adjacencyEdge : this.adj[current.node]) {
                ShortestPathInfo adjacencyEdgeInfo = output[adjacencyEdge.node];

                // Relaxation step to check if a shorter path exists
                if (currentInfo.distance != Integer.MAX_VALUE
                        && adjacencyEdgeInfo.distance > currentInfo.distance + adjacencyEdge.weight) {
                    adjacencyEdgeInfo.from = current.node;
                    adjacencyEdgeInfo.distance = currentInfo.distance + adjacencyEdge.weight;
                    queue.add(new Edge(adjacencyEdge.node, adjacencyEdgeInfo.distance));
                }
            }
        }

        return output;
    }

    public int[] getPath(int destination, ShortestPathInfo[] data) {
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
        DijkstrasAlgorithm graph = new DijkstrasAlgorithm(6, true);
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
                    ", Path: " + Arrays.toString(graph.getPath(i, output)));
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

    static class Edge implements Comparable<Edge> {
        int node;
        int weight;

        public Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return this.weight - edge.weight;
        }
    }
}
