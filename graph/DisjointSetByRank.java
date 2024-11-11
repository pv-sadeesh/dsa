package graph;

public class DisjointSetByRank {

    /*********************************************************************************************************************************
     * Time Complexity      : O(1), comstant time, O(α(n)) (inverse Ackermann function).
     * Space Complexity     : O(n) for storing parents and ranks.
     *********************************************************************************************************************************
     * 1. Initialization:
     *      Set each element as its own parent (self-loop).
     *      Set initial rank to zero for all elements.
     * 2. Find(x):
     *      Recursively find the root of x, applying path compression along the way.
     *      Returns the root of the set containing x.
     * 3. Union(x, y):
     *      Find roots of x and y.
     *      Attach the tree with a lower rank to the root with a higher rank.
     *      If ranks are equal, arbitrarily choose one root and increment its rank.
     * 4. isConnected(x, y):
     *      Check if x and y belong to the same set by comparing their roots.
     *********************************************************************************************************************************/

    private int[] parent;
    private int[] rank;

    public DisjointSetByRank(int vertices) {
        this.parent = new int[vertices];
        this.rank = new int[vertices];

        // Initially, each element is its own parent (self-loop)
        for (int i = 0; i < vertices; i++) {
            this.parent[i] = i;
            // Initialize rank as 0 for all elements
            this.rank[i] = 0;
        }
    }

    public int find(int x) {
        if (this.parent[x] != x) {
            // Recursively find the root and apply path compression
            parent[x] = find(this.parent[x]);
        }

        return parent[x];
    }

    public void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);

        // If both elements have the same root, they are already in the same set
        if (xRoot == yRoot) {
            return;
        }

        // Attach the tree with a lower rank to the root with a higher rank
        if (this.rank[xRoot] > this.rank[yRoot]) {
            this.parent[yRoot] = xRoot;
        } else {
            this.parent[xRoot] = yRoot;
            // Increment parent rank if both have the same rank
            if (this.rank[xRoot] == this.rank[yRoot]) {
                this.rank[yRoot]++;
            }
        }
    }

    public boolean isConnected(int x, int y) {
        // Check if two elements are in the same set
        return find(x) == find(y);
    }

    public static void main(String[] args) {
        DisjointSetByRank disjointSet = new DisjointSetByRank(6);

        disjointSet.union(0, 1);
        disjointSet.union(1, 2);
        disjointSet.union(3, 4);

        System.out.println(disjointSet.isConnected(0, 2)); // Output: true
        System.out.println(disjointSet.isConnected(0, 3)); // Output: false

        disjointSet.union(2, 3);
        System.out.println(disjointSet.isConnected(0, 3)); // Output: true
    }
}
