/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] grid;
    private final int n;
    private int opensites = 0, flag = 0;

    private final WeightedQuickUnionUF uf;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n * n + 2];
        for (int i = 0; i < n * n + 2; i++) {
            grid[i] = false;
        }
        grid[0] = true;
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        validityCheck(row, col);
		
        if (!isOpen(row, col)) {
            grid[twotoone(row, col)] = true;

            if (twotoone(row - 1, col) != 0 && grid[twotoone(row - 1, col)]) {
                uf.union(twotoone(row - 1, col), twotoone(row, col));
                if (row == 1) {
                    uf.union(0, col);
                }
            }
            if (twotoone(row + 1, col) != 0 && grid[twotoone(row + 1, col)]) {
                uf.union(twotoone(row + 1, col), twotoone(row, col));
                if (row == 1) {
                    uf.union(0, col);
                }
            }
            if (twotoone(row, col - 1) != 0 && grid[twotoone(row, col - 1)]) {
                uf.union(twotoone(row, col - 1), twotoone(row, col));
                if (row == 1) {
                    uf.union(0, col);
                }
            }
            if (twotoone(row, col + 1) != 0 && grid[twotoone(row, col + 1)]) {
                uf.union(twotoone(row, col + 1), twotoone(row, col));
                if (row == 1) {
                    uf.union(0, col);
                }
            }
            if (row == 1 && isOpen(row, col)) {
                uf.union(twotoone(row, col), 0);
                uf.union(0, col);
            }
            if (row == n && isOpen(row, col) && flag == 0) {
                uf.union(twotoone(row, col), n * n + 1);
            }

            opensites++;
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        validityCheck(row, col);

        return grid[twotoone(row, col)];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        validityCheck(row, col);

        if (uf.connected(0, twotoone(row, col))) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return opensites;
    }

    public boolean percolates()              // does the system percolate?
    {
        if (uf.connected(0, n * n + 1))
            flag = 1;
        return uf.connected(0, n * n + 1);
    }

    private int twotoone(int row, int col) {
        if (row >= 1 && row <= n && col >= 1 && col <= n)
            return (((row - 1) * n) + col);
        else
            return 0;
    }

    private void validityCheck(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException();
    }
}
