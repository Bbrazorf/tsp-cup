package Algorithms.ACO;

public class Arc {
    private int d;
    private double p;

    public Arc(int d, double p) {
        this.d = d;
        this.p = p;
    }

    public int getD() {
        return d;
    }

    public double getP() {
        return p;
    }

    public void setD(int d) {
        this.d = d;
    }

    public void setP(double p) {
        this.p = p;
    }
}
