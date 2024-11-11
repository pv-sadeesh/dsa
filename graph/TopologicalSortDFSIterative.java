package graph;

import java.util.*;

public class TopologicalSortDFSIterative {

    /*********************************************************************************************************************************
     * Time Complexity      : O(V + E), where V is the number of vertices and E is the number of edges.
     * Space Complexity     : O(V), due to the visited array, the stack used during traversal, and the stack for the topologically sorted result.
     *********************************************************************************************************************************
     * 1. Graph Initialization:
     *      Initialize the graph with the number of vertices and directed/undirected type.
     *      Create an adjacency list for each vertex.
     * 2. Add Edges:
     *      For directed graph: Add an edge from source to destination.
     *      For undirected graph: Add the reverse edge as well.
     * 3. Topological Sort Execution:
     *      Initialize a visited array to track visited nodes.
     *      Initialize an empty stack sorted to store topologically sorted nodes.
     * 4. DFS Iteration (for each unvisited vertex):
     *      Push the starting node onto a stack.
     *      For each node, mark it as visited and print it.
     *      Explore all adjacent unvisited nodes, push them onto the stack.
     *      When all adjacent nodes are visited, pop the current node and push it to the sorted stack.
     * 5. Print Topologically Sorted Nodes:
     *      After all nodes are processed, pop the nodes from the sorted stack and print them.
     *********************************************************************************************************************************/

    private int vertices;
    private boolean directed;
    private List<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public TopologicalSortDFSIterative(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adj = new ArrayList[vertices];

        // Initialize adjacency list for each vertex
        for (int i = 0; i < vertices; i++) {
            this.adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int source, int destination) {
        // Add directed edge from source to destination
        if (!this.adj[source].contains(destination)) {
            this.adj[source].add(destination);
        }

        // If the graph is undirected, also add the reverse edge
        if (!this.directed && !this.adj[destination].contains(source)) {
            this.adj[destination].add(source);
        }
    }

    public void execute() {
        boolean[] visited = new boolean[this.vertices];
        Stack<Integer> sorted = new Stack<>();

        // Perform DFS starting from all unvisited nodes
        for (int i = 0; i < this.vertices; i++) {
            if (!visited[i]) {
                execute(i, visited, sorted);
            }
        }

        // Print the topologically sorted nodes
        System.out.println("\nTopological Sorted Nodes:");
        while (!sorted.isEmpty()) {
            System.out.print(sorted.pop() + ", ");
        }
    }

    private void execute(int startNode, boolean[] visited, Stack<Integer> sorted) {
        Stack<Integer> stack = new Stack<>();

        stack.push(startNode);

        while (!stack.isEmpty()) {
            int currentNode = stack.peek();
            boolean isAllAdjNodesExplored = true;

            // Mark the current node as visited and print it
            if (!visited[currentNode]) {
                visited[currentNode] = true;
                System.out.print(currentNode + ", ");
            }

            // Explore all adjacent unvisited nodes
            for (int adjNode : this.adj[currentNode]) {
                if (!visited[adjNode]) {
                    isAllAdjNodesExplored = false;
                    stack.push(adjNode);
                }
            }

            // If all adjacent nodes are visited, pop the current node and add to sorted
            // stack
            if (isAllAdjNodesExplored) {
                stack.pop();
                sorted.push(currentNode);
            }
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

        // DFS Output               : 0, 1, 2, 3, 4, 5
        // Topological Sort Output  : 5, 4, 2, 3, 1, 0
    }
}
