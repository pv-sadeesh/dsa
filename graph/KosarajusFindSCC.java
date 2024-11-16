package graph;

import java.util.*;

public class KosarajusFindSCC {

    /*********************************************************************************************************************************
     * Time Complexity  : O(V+E) - DFS on Original Graph: O(V+E), Transposing the Graph: O(V+E), DFS on Transposed Graph: O(V+E)
     * Space Complexity : O(V+E) - Adjacency List: O(V+E), Visited Array: O(V), Stack for Finish Order: O(V)
     *********************************************************************************************************************************
     * 1. DFS on Original Graph: Record vertices in a stack based on their finish times.
     * 2. Transpose Graph: Reverse all edges of the graph.
     * 3. DFS on Transposed Graph: Process vertices from the stack and find SCCs by exploring connected components.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public KosarajusFindSCC(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize adjacency lists for all vertices
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination) {
        if (source >= this.vertices) {
            throw new IllegalArgumentException("Source index is out of bounds.");
        }
        if (destination >= this.vertices) {
            throw new IllegalArgumentException("Destination index is out of bounds.");
        }

        // Add edge from source to destination
        if (!this.adj[source].contains(destination)) {
            this.adj[source].add(destination);
        }

        // If the graph is undirected, add the reverse edge
        if (!this.directed && !this.adj[destination].contains(source)) {
            this.adj[destination].add(source);
        }
    }

    @SuppressWarnings("unchecked")
    public List<List<Integer>> execute() {
        // Step 1: Perform a DFS to determine the finishing order of vertices
        Stack<Integer> finishingOrder = new Stack<>();
        boolean[] visited = new boolean[this.vertices];

        for (int i = 0; i < this.vertices; i++) {
            if (!visited[i]) {
                fillFinishingOrderUsingDFS(i, visited, finishingOrder);
            }
        }

        // Step 2: Transpose the graph (reverse all edges)
        List<Integer>[] transposedGraph = new ArrayList[this.vertices];
        for (int i = 0; i < this.vertices; i++) {
            transposedGraph[i] = new ArrayList<>();
        }

        // Populate the transposed graph
        for (int i = 0; i < this.vertices; i++) {
            for (int adjNode : this.adj[i]) {
                transposedGraph[adjNode].add(i);
            }
        }

        // Step 3: Perform a DFS on the transposed graph in the order of the finishing
        // stack
        List<List<Integer>> sccs = new ArrayList<>();

        for (int i = 0; i < this.vertices; i++) {
            visited[i] = false;
        }

        while (!finishingOrder.isEmpty()) {
            int current = finishingOrder.pop();
            if (!visited[current]) {
                // Get all vertices in the current SCC
                List<Integer> scc = getStronglyConnectedComponent(transposedGraph, current, visited);
                sccs.add(scc);
            }
        }

        return sccs;
    }

    private List<Integer> getStronglyConnectedComponent(List<Integer>[] graph, int startNode, boolean[] visited) {
        List<Integer> result = new ArrayList<>();

        Stack<Integer> stack = new Stack<>();
        stack.push(startNode);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (!visited[current]) {
                visited[current] = true;
                result.add(current);

                // Add all unvisited adjacent nodes to the stack
                for (int adjNode : graph[current]) {
                    if (!visited[adjNode]) {
                        stack.add(adjNode);
                    }
                }
            }
        }

        return result;
    }

    private void fillFinishingOrderUsingDFS(int node, boolean[] visited, Stack<Integer> order) {
        Stack<Integer> stack = new Stack<>();

        stack.push(node);

        while (!stack.isEmpty()) {
            int current = stack.peek();
            boolean isAllAdjNodesExplored = true;

            if (!visited[current]) {
                visited[current] = true;
            }

            // Explore all adjacent nodes
            for (int adjNode : this.adj[current]) {
                if (!visited[adjNode]) {
                    isAllAdjNodesExplored = false;
                    stack.push(adjNode);
                }
            }

            // If all adjacent nodes are visited, add the current node to the order
            if (isAllAdjNodesExplored) {
                stack.pop();
                order.push(current);
            }
        }
    }

    public static void main(String[] args) {
        // Create a directed graph with 5 vertices
        KosarajusFindSCC graph = new KosarajusFindSCC(5, true);
        graph.addEdge(0, 2);
        graph.addEdge(2, 1);
        graph.addEdge(1, 0);
        graph.addEdge(0, 3);
        graph.addEdge(3, 4);

        // Execute Kosaraju's algorithm to find SCCs
        List<List<Integer>> sccs = graph.execute();

        // Print the strongly connected components
        System.out.println("Strongly Connected Components:");
        for (List<Integer> component : sccs) {
            System.out.println(component);
        }

        // Expected Output:
        // Strongly Connected Components:
        // [0, 1, 2]
        // [3]
        // [4]
    }
}
