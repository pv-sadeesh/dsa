package graph.shortestpath;

import java.util.*;

public class AStarAlgorithm {

    /*********************************************************************************************************************************
     * Best Case    - Time Complexity: O(E) where E is the number of edges  Space Complexity: O(V) where V is the number of vertices
     * Average Case	- Time Complexity: O(E + V log V)	                    Space Complexity: O(V)
     * Worst Case	- Time Complexity: O(E log V)	                        Space Complexity: O(V)
     *********************************************************************************************************************************
     * 1. Initialize:
     *      Create two sets: open set (nodes to be evaluated) and closed set (nodes already evaluated).
     *      Add the start node to the open set.
     * 2. Loop:
     *      While open set is not empty:
     *          Choose the node current with the lowest f value (where f = g + h).
     *          If current is the goal node, reconstruct the path from start to goal.
     *          Remove current from the open set and add it to the closed set.
     *          For each neighbor of current:
     *              If the neighbor is in the closed set, skip it.
     *              Calculate the g (cost from start to the neighbor) and h (heuristic cost from neighbor to goal).
     *              If the neighbor is not in the open set or has a lower f value, update its values and add it to the open set.
     * 3. Reconstruct Path:
     *      If the goal node is reached, trace back through the parent nodes to reconstruct the path from start to goal.
     *********************************************************************************************************************************/

    static class Node implements Comparable<Node> {
        int x;
        int y;
        int gCost; // const to reach this node from start node (start node -> this node)
        int hCost; // heuristic based on Manhattan formula (this node -> goal node)
        int fCost; // final cost which is gCost + hCost (start node - this node) + (this node - goal node)
        Node parent; // to reconstruct the path
        boolean isObstacle;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.gCost = Integer.MAX_VALUE; // Set initial gCost to a high value
            this.hCost = 0;
            this.fCost = 0;
        }

        @Override
        public int compareTo(Node node) {
            return this.fCost - node.fCost;
        }

        @Override
        public String toString() {
            return "{" + this.x + "," + this.y + "}";
        }
    }

    // Diagonal movements are not considered, so using only four neighbours without including diagonal cells.
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    int size;
    Node[][] grid;

    public AStarAlgorithm(int size) {
        this.size = size;
        this.grid = new Node[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid[i][j] = new Node(i, j);
            }
        }
    }

    public void setObstacle(int x, int y) {
        if (!isInBounds(x, y)) {
            throw new IllegalArgumentException("Cell position is out of bounds.");
        }

        this.grid[x][y].isObstacle = true;
    }

    public List<Node> execute(Node start, Node target) {
        start = this.grid[start.x][start.y];
        if (start.isObstacle) {
            throw new IllegalArgumentException("Start cell is defined as an obstacle.");
        }

        target = this.grid[target.x][target.y];
        if (target.isObstacle) {
            throw new IllegalArgumentException("Target cell is defined as an obstacle.");
        }

        Set<String> visited = new HashSet<>();
        Queue<Node> queue = new PriorityQueue<>();
        start.gCost = 0;
        start.hCost = getHCost(start, target);
        start.fCost = start.gCost + start.hCost;
        queue.offer(start);

        // A* search loop
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.equals(target)) {
                return getPath(current);
            }

            visited.add(current.toString());

            // Explore each valid neighbor
            for (int[] direction : DIRECTIONS) {
                int x = current.x + direction[0];
                int y = current.y + direction[1];

                // Check if neighbor is within bounds
                if (isInBounds(x, y)) {
                    Node neighbour = this.grid[x][y];

                    // Skip obstacles or already visited nodes
                    if (neighbour.isObstacle || visited.contains(neighbour.toString())) {
                        continue;
                    }

                    int tentativeGCost = current.gCost + 1;

                    // Update neighbor's costs if a lower gCost path is found
                    if (tentativeGCost < neighbour.gCost) {
                        if (!queue.contains(neighbour)) {
                            neighbour.parent = current;
                            neighbour.gCost = tentativeGCost;
                            neighbour.hCost = getHCost(neighbour, target);
                            neighbour.fCost = neighbour.gCost + neighbour.hCost;

                            queue.offer(neighbour);
                        }
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < this.size && y < this.size;
    }

    private List<Node> getPath(Node target) {
        List<Node> path = new ArrayList<>();

        while (target != null) {
            path.add(target);
            target = target.parent;
        }

        // Reverse path to get start to target order
        Collections.reverse(path);
        return path;
    }

    // If diagonal movements are not considered then heuristic cost will get calculated based on Manhattan formula ELSE Euclidean will be used.
    private int getHCost(Node start, Node end) {
        return Math.abs(end.x - start.x) + Math.abs(end.y - start.y);
    }

    public static void main(String[] args) {
        AStarAlgorithm graph = new AStarAlgorithm(5);
        graph.setObstacle(1, 1);
        graph.setObstacle(1, 3);
        graph.setObstacle(2, 1);
        graph.setObstacle(3, 3);
        graph.setObstacle(4, 2);
        List<Node> path = graph.execute(new Node(0, 0), new Node(4, 4));

        System.out.println("Shortest Path:");
        for (Node node : path) {
            System.out.print(node.toString() + ", ");
        }

        // Shortest Path: {0,0}, {0,1}, {0,2}, {1,2}, {2,2}, {2,3}, {2,4}, {3,4}, {4,4},
    }
}
