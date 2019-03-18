package sample;

import java.util.Arrays;

public class MD {

    private int nAtoms; //liczba atomów
    private double boxWidth; //szerokość box'u, w którym cząstki się odpychają
    private double boxHeight;
    private double[] x, y, vx, vy, ax, ay; //tablice
    private int stepCounter; //ile krokow sie zrobilo
    private double ePot;
    private double eKin;
    private double elasticEnergy;

    private final double rCut2 = 16.0;
    private final double wallStifness = 200;


    public MD(double[] xStart, double[] yStart, double[] vxStart, double[] vyStart, double boxWidth, double boxHeight) {

        nAtoms = xStart.length; //ilość atomow równa długości tablicy
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;

        // x=xStart.clone(); // to działa dla prostych tablic tak jak w rozważanym przypadku
        x = Arrays.copyOf(xStart, xStart.length);
        y = Arrays.copyOf(yStart, yStart.length);
        vx = Arrays.copyOf(vxStart, vxStart.length);
        vy = Arrays.copyOf(vyStart, vyStart.length);

        //tablica przyspieszen
        ax = new double[nAtoms];
        ay = new double[nAtoms];

        calculateAcceleration();
        calculateKineticEnergy();

    }

    //dt - długość kroku całkowania
    public void verletStep(double dt) {
        calculateAcceleration();

        for (int i = 0; i < nAtoms; i++) {
            vx[i] = vx[i] + dt * ax[i] * 0.5;
            vy[i] = vy[i] + dt * ay[i] * 0.5;
            x[i] = x[i] + dt * vx[i];
            y[i] = y[i] + dt * vy[i];
        }
        calculateAcceleration();
        for (int i = 0; i < nAtoms; i++) {
            vx[i] = vx[i] + dt * ax[i] * 0.5;
            // System.out.println(vx[i]);
            vy[i] = vy[i] + dt * ay[i] * 0.5;
        }

        calculateKineticEnergy();


    }

    private void calculateElasticEnergy() {

        elasticEnergy = 0;

        for (int i = 0; i < nAtoms; i++) {

            double d = 0;

            if (x[i] < 0.5) {
                d = 0.5 - x[i];
                ax[i] += wallStifness * d * d;
                elasticEnergy += 0.5 * wallStifness * d * d;
            }
            if (x[i] > boxWidth - 0.5) {
                d = (boxWidth - 0.5 - x[i]);
                ax[i] += wallStifness * d;
                elasticEnergy += 0.5 * wallStifness * d * d;
            }
            if (y[i] < 0.5) {
                d = 0.5 - y[i];
                ay[i] += wallStifness * d * d;
                elasticEnergy += 0.5 * wallStifness * d * d;
            }
            if (y[i] > boxHeight - 0.5) {
                d = (boxHeight - 0.5 - y[i]);
                ay[i] += wallStifness * d;
                elasticEnergy += 0.5 * wallStifness * d * d;
            }

        }
    }

    private void calculateKineticEnergy() {
        eKin = 0;
        for (int i = 0; i < nAtoms; i++) {
            eKin += Math.sqrt(vx[i] * vx[i] + vy[i] * vy[i]) * 0.5;
        }

    }

    private void calculateAcceleration() {
        elasticEnergy = 0;
        for (int i = 0; i < nAtoms; i++) {
            ax[i] = 0;    //wyzerowanie starych przyspieszen
            ay[i] = 0;

            double d = 0;

            if (x[i] < 0.5) {
                d = 0.5 - x[i];
                ax[i] += wallStifness * d;
                elasticEnergy += 0.5 * wallStifness * d * d;
            }

            if (x[i] > boxWidth - 0.5) {
                d = (boxWidth - 0.5 - x[i]);
                ax[i] += wallStifness * d;
                elasticEnergy += 0.5 * wallStifness * d * d;
            }

            if (y[i] < 0.5) {
                d = 0.5 - y[i];
                ay[i] += wallStifness * d;
                elasticEnergy += 0.5 * wallStifness * d * d;
            }

            if (y[i] > boxHeight - 0.5) {
                d = (boxHeight - 0.5 - y[i]);
                ay[i] += wallStifness * d;
                elasticEnergy += 0.5 * wallStifness * d * d;
            }
        }

        ePot = 0;


        for (int i = 0; i < nAtoms - 1; i++) {

            for (int j = i + 1; j < nAtoms; j++) {
                //liczymy sile odzialywania miedzy "i" i "j" atomem

                double dx = x[i] - x[j];
                double dy = y[i] - y[j];
                double rij2 = dx * dx + dy * dy;

                if (rij2 < rCut2) {
                    double fr2 = 1. / rij2;
                    double fr6 = fr2 * fr2 * fr2;
//                   double fr = 48.0 * fr2 * fr6 * (fr6 - 0.5);
                    //w dodatku inaczej
                    double fr = 48.0 * fr6 * (fr6 - 0.5) / rij2;
                    double fxi = fr * dx;
                    double fyi = fr * dy;

                    ax[i] += fxi;
                    ay[i] += fyi;
                    ax[j] -= fxi;
                    ay[j] -= fyi;

                    ePot += 4 * fr6 * (fr6 - 1.0);
                    // System.out.println("epot\t"+ePot);
                }

            }

        }


    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double[] getVx() {
        return vx;
    }

    public double[] getVy() {
        return vy;
    }

    public double getePot() {
        return ePot;
    }

    public double geteKin() {
        return eKin;
    }

    public double getElasticEnergy() {
        return elasticEnergy;
    }
}
