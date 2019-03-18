package sample;

import java.util.Random;

public class Losowania {

    private Random rand = new Random();

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    private double x[];
    private double y[];


    public Losowania(int nAtoms, int boxWidth, int boxHeight) {
        x = new double[nAtoms];
        y = new double[nAtoms];
        losujWsp(nAtoms,boxWidth,boxHeight);
    }


    private void losujWsp(int nAtoms, int boxWidth, int boxHeight) {
        for (int i = 0; i < x.length; i++) {
            boolean check = true;
            int a=0;

            while (check) {
                a=0;
                double tryX = rand.nextDouble() * boxWidth;
                double tryY = rand.nextDouble() * boxHeight;
                    for (int j = 0; j<x.length;j++){
                        if(Math.sqrt(Math.abs(x[j]-tryX)*Math.abs(x[j]-tryX)+Math.abs(y[j]-tryY)*Math.abs(y[j]-tryY))<3){
                            a=1;
                        }
                    }

                    if (a!=1){
                        check=false;
                        x[i]=tryX;
                        y[i]=tryY;
                    }
            }
        }
    }


    public double[] losujV(int nAtoms) {
        double[] v = new double[nAtoms];
        for (int i = 0; i < v.length; i++) {
            v[i] = rand.nextGaussian() * 3;
        }

        return v;
    }

}
