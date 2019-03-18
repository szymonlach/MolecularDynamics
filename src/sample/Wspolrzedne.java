package sample;

public class Wspolrzedne {

    //  private static double hEkran;
    //  private static double wEkran;
    private static double tablicaEkranowaWsp[];

    public static double[] zmienX(double[] tablica, double ekran) {

        tablicaEkranowaWsp = new double[tablica.length];
        for (int i = 0; i < tablica.length; i++) {
            tablicaEkranowaWsp[i] = tablica[i] * ekran / 100;
        }
        return tablicaEkranowaWsp;
    }



}
