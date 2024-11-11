package graph;

public class DisjointSetBySize {

    /*********************************************************************************************************************************
     * Time Complexity      : O(1), comstant time, O(α(n)) (inverse Ackermann function).
     * Space Complexity     : O(n) for storing parents and ranks.
     *********************************************************************************************************************************
     * 1. Initialize:
     *      parent[i] = i (each node is its own parent).
     *      size[i] = 1
     * 2. Find with Path Compression:
     *      Recursively find root and set each node’s parent directly to root.
     *      Complexity: ~O(1) due to path compression.
     * 3. Union by Size/Rank:
     *      Find roots of both nodes.
     *      Attach smaller tree to root of larger tree, update size/rank.
     *      Complexity: ~O(1).
     * 4. Check Connectivity:
     *      find(x) == find(y) checks if x and y are connected.
     *********************************************************************************************************************************/

    private int[] parent;
    private int[] size;

    public DisjointSetBySize(int vertices) {
        this.parent = new int[vertices];
        this.size = new int[vertices];

        // Initially, each element is its own parent, and size of each set is 1.
        for (int i = 0; i < vertices; i++) {
            this.parent[i] = i;
            this.size[i] = 1;
        }
    }

    public int find(int x) {
        // If x is not its own parent, recursively find the root, compressing the path
        if (this.parent[x] != x) {
            // Path compression for efficiency
            this.parent[x] = find(parent[x]);
        }

        return parent[x];
    }

    public void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);

        // If they are already in the same set, no need to union
        if (xRoot == yRoot) {
            return;
        }

        // Union by size: attach the smaller tree under the larger tree
        if (this.size[xRoot] > this.size[yRoot]) {
            this.parent[yRoot] = xRoot;
            this.size[xRoot] += this.size[yRoot];
        } else {
            this.parent[xRoot] = yRoot;
            this.size[yRoot] += this.size[xRoot];
        }
    }

    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }

    public static void main(String[] args) {
        DisjointSetBySize disjointSet = new DisjointSetBySize(6);

        disjointSet.union(0, 1);
        disjointSet.union(1, 2);
        disjointSet.union(3, 4);

        System.out.println(disjointSet.isConnected(0, 2)); // Output: true
        System.out.println(disjointSet.isConnected(0, 3)); // Output: false

        disjointSet.union(2, 3);
        System.out.println(disjointSet.isConnected(0, 3)); // Output: true
    }
}
