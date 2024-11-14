package graph.shortestpath;

import java.util.*;

public class JohnsonsAlgorithm {

    /*********************************************************************************************************************************
     * Time Complexity  : O(V^2 logV + V * E) - for all cases.
     * Space Complexity : O(V^2) - for storing shortest path distances and paths between all node pairs.
     *********************************************************************************************************************************
     * 1. Add a Node                    : Insert a new node "s" with edges of weight 0 to all other nodes.
     * 2. Run Bellman-Ford              : Use Bellman-Ford from "s" to find shortest paths. If there's a negative weight cycle, exit.
     * 3. Reweight Edges                : Adjust each edge (u,v) to ensure non-negative weights: w(u,v) = w(u,v) + h(u) − h(v)
     * 4. Run Dijkstra’s for Each Node  : Use Dijkstra's algorithm from each node to find shortest paths with reweighted edges.
     * 5. Restore Original Distances    : Convert paths back to original weights: d(u,v) = d(u,v) − h(u) + h(v)
     * 6. Output                        : Return the shortest paths between all node pairs.
     *********************************************************************************************************************************/

    static class ShortestPathInfo {
        int distance;
        int[] path;

        public ShortestPathInfo(int distance, int[] path) {
            this.distance = distance;
            this.path = path;
        }
    }

    static class DijkstraResult {
        int[] distances;
        int[] parents;

        public DijkstraResult(int[] distances, int[] parents) {
            this.distances = distances;
            this.parents = parents;
        }

        public int[] getPath(int node) {
            Stack<Integer> stack = new Stack<>();

            int current = node;
            while (current != -1) {
                stack.push(current);
                current = this.parents[current];
            }

            int[] path = new int[stack.size()];
            int index = 0;
            while (!stack.isEmpty()) {
                path[index++] = stack.pop();
            }

            return path;
        }
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

        @Override
        public String toString() {
            return "{" + this.source + "->" + this.destination + "}";
        }
    }

    int vertices;
    boolean directed;
    List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public JohnsonsAlgorithm(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize adjacency list for each vertex
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

        this.adj[source].add(new Edge(source, destination, weight));

        // If graph is undirected, add edge in both directions
        if (!this.directed) {
            this.adj[destination].add(new Edge(destination, source, weight));
        }
    }

    @SuppressWarnings("unchecked")
    public ShortestPathInfo[][] execute() {
        List<Edge>[] modifiedList = new ArrayList[this.vertices + 1];
        int newNodeIndex = this.vertices;

        // Add a new node with edges of weight 0 to all existing nodes
        modifiedList[newNodeIndex] = new ArrayList<>();
        for (int i = 0; i < this.vertices; i++) {
            modifiedList[i] = this.adj[i];
            modifiedList[newNodeIndex].add(new Edge(newNodeIndex, i, 0));
        }

        // Run Bellman-Ford algorithm to find shortest paths from new node
        int[] h = bellmanFord(modifiedList, newNodeIndex);

        // Re-weight edges based on results from Bellman-Ford
        for (int i = 0; i < this.vertices; i++) {
            for (Edge edge : modifiedList[i]) {
                if (h[edge.source] == Integer.MAX_VALUE || h[edge.destination] == Integer.MAX_VALUE) {
                    continue;
                }
                edge.weight = h[edge.source] + edge.weight - h[edge.destination];
            }
        }

        ShortestPathInfo[][] result = new ShortestPathInfo[this.vertices][this.vertices];
        for (int i = 0; i < this.vertices; i++) {
            for (int j = 0; j < this.vertices; j++) {
                result[i][j] = new ShortestPathInfo(Integer.MAX_VALUE, new int[0]);
            }
        }

        // Run Dijkstra's algorithm for each vertex as the source
        for (int source = 0; source < this.vertices; source++) {
            DijkstraResult dijkstraResult = dijkstra(source);
            int[] shortestPaths = dijkstraResult.distances;
            for (int destination = 0; destination < this.vertices; destination++) {
                if (shortestPaths[destination] == Integer.MAX_VALUE) {
                    result[source][destination].distance = Integer.MAX_VALUE;
                } else {
                    result[source][destination].distance = shortestPaths[destination] + (h[destination] - h[source]);
                    result[source][destination].path = dijkstraResult.getPath(destination);
                }
            }
        }

        return result;
    }

    private DijkstraResult dijkstra(int source) {
        int[] distances = new int[this.vertices];
        int[] parents = new int[this.vertices];

        for (int i = 0; i < this.vertices; i++) {
            distances[i] = Integer.MAX_VALUE;
            parents[i] = -1;
        }
        distances[source] = 0;

        Queue<Edge> queue = new PriorityQueue<>();
        queue.offer(new Edge(source, source, 0));

        while (!queue.isEmpty()) {
            Edge current = queue.poll();
            int node = current.destination;

            for (Edge edge : this.adj[node]) {
                if (distances[node] == Integer.MAX_VALUE) {
                    continue;
                }

                if (distances[node] + edge.weight < distances[edge.destination]) {
                    distances[edge.destination] = distances[node] + edge.weight;
                    parents[edge.destination] = edge.source;
                    queue.offer(new Edge(node, edge.destination, distances[edge.destination]));
                }
            }
        }

        return new DijkstraResult(distances, parents);
    }

    private int[] bellmanFord(List<Edge>[] modifiedAdj, int source) {
        int size = modifiedAdj.length;
        int[] distances = new int[size];

        for (int i = 0; i < size; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[source] = 0;

        for (int run = 0; run < size - 1; run++) {
            for (int i = 0; i < size; i++) {
                for (Edge edge : modifiedAdj[i]) {
                    if (distances[edge.source] != Integer.MAX_VALUE
                            && distances[edge.source] + edge.weight < distances[edge.destination]) {
                        distances[edge.destination] = distances[edge.source] + edge.weight;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (Edge edge : modifiedAdj[i]) {
                if (distances[edge.source] != Integer.MAX_VALUE
                        && distances[edge.source] + edge.weight < distances[edge.destination]) {
                    throw new IllegalArgumentException("Graph contains a negative weight cycle.");
                }
            }
        }

        return distances;
    }

    public static void main(String[] args) {
        JohnsonsAlgorithm graph = new JohnsonsAlgorithm(3, true);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, -1);
        graph.addEdge(0, 2, 4);
        ShortestPathInfo[][] result = graph.execute();

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.println(i + "->" + j + ": \tDistance: "
                        + (result[i][j].distance == Integer.MAX_VALUE ? "INF" : result[i][j].distance)
                        + "\tPath: " + Arrays.toString(result[i][j].path));
            }
        }

        // 0->0:    Distance: 0     Path: [0]
        // 0->1:    Distance: 2     Path: [0, 1]
        // 0->2:    Distance: 1     Path: [0, 1, 2]
        // 1->0:    Distance: INF   Path: []
        // 1->1:    Distance: 0     Path: [1]
        // 1->2:    Distance: -1    Path: [1, 2]
        // 2->0:    Distance: INF   Path: []
        // 2->1:    Distance: INF   Path: []
        // 2->2:    Distance: 0     Path: [2]
    }
}
