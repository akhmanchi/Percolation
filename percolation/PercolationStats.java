/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // private final Percolation p;

    // private final int trials;
    private double calcMean, totstdev;
    private double con;
    private final double[] mean;

    public PercolationStats(int n,
                            int trials) {    // perform trials independent experiments on an n-by-n grid
        int random1, random2;
        int[] opensites;
        boolean perc;
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        // this.n = n;
        // this.trials = trials;
        opensites = new int[trials];
        mean = new double[trials];
        // p = new Percolation[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            perc = p.percolates();
            while (!perc) {
                random1 = StdRandom.uniform(n) + 1;
                random2 = StdRandom.uniform(n) + 1;
                p.open(random1, random2);
                perc = p.percolates();
            }
            opensites[i] = p.numberOfOpenSites();

            mean[i] = opensites[i] / (double) (n * n);
            // calcMean = StdStats.mean(mean);
            // totstdev = StdStats.stddev(mean);
            con = ((1.96 * totstdev) / Math.sqrt(trials));
            // conlo = calcMean - con;
            // conhi = calcMean + con;
        }

    }

    public double mean()                          // sample mean of percolation threshold
    {
        calcMean = StdStats.mean(mean);
        return calcMean;
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        totstdev = StdStats.stddev(mean);
        return totstdev;
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {

        double conlo = calcMean - con;
        return conlo;
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {

        double conhi = calcMean + con;
        return conhi;
    }

    public static void main(String[] args)        // test client (described below)
    {
        int no = Integer.parseInt(args[0]);

        int tr = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(no, tr);

        double myMean = ps.mean();

        double myStddev = ps.stddev();

        double myLo = ps.confidenceLo();

        double myHi = ps.confidenceHi();

        StdOut.println("mean = " + myMean);
        StdOut.println("stddev = " + myStddev);
        StdOut.println("95% confidence level = [" + myLo + "," + myHi + "]");
    }
}
