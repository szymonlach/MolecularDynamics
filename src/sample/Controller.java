package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField krokCalkowania;
    @FXML
    private TextField iloscKrokow;
    @FXML
    private ScatterChart<?, ?> figure1;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CheckBox polozenieX;
    @FXML
    private CheckBox polozenieY;
    @FXML
    private CheckBox predkoscX;
    @FXML
    private CheckBox predkoscY;
    @FXML
    private CheckBox energia;

    int nAtoms = 200;
    Losowania los = new Losowania(nAtoms, 100,100);
    double[] x = los.getX(); //pierwsza z czastek poczatkowa wspolrzedna druga 90
    double[] y = los.getY();
    double[] vx = los.losujV(nAtoms); //pierwsza z lewa na prawo
    double[] vy = los.losujV(nAtoms);

    MD md = new MD(x, y, vx, vy, 100, 100);

    public void rysujWykres() {

        figure1.getData().clear();
        double krokCal = Double.parseDouble(krokCalkowania.getText());
        XYChart.Series seria1 = new XYChart.Series();
        XYChart.Series seria2 = new XYChart.Series();
        XYChart.Series seria3 = new XYChart.Series();
        seria1.getData().clear();
        seria2.getData().clear();
        seria3.getData().clear();
        if (polozenieX.isSelected()) {
            for (int i = 0; i < Integer.parseInt(iloscKrokow.getText()); i++) {
                md.verletStep(krokCal);
                seria1.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getX()[0]));
                seria2.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getX()[1]));
            }
            figure1.getData().add(seria1);
            figure1.getData().add(seria2);
            seria1.setName("atom 1");
            seria2.setName("atom 2");

        }

        if (polozenieY.isSelected()) {
            for (int i = 0; i < Integer.parseInt(iloscKrokow.getText()); i++) {
                md.verletStep(krokCal);
                seria1.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getY()[0]));
                seria2.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getY()[1]));
            }
            figure1.getData().add(seria1);
            figure1.getData().add(seria2);
            seria1.setName("atom 1");
            seria2.setName("atom 2");


        }

        if (predkoscX.isSelected()) {
            for (int i = 0; i < Integer.parseInt(iloscKrokow.getText()); i++) {
                md.verletStep(krokCal);
                seria1.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getVx()[0]));
                seria2.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getVx()[1]));
            }
            figure1.getData().add(seria1);
            figure1.getData().add(seria2);
            seria1.setName("atom 1");
            seria2.setName("atom 2");


        }

        if (predkoscY.isSelected()) {
            for (int i = 0; i < Integer.parseInt(iloscKrokow.getText()); i++) {
                md.verletStep(krokCal);
                seria1.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getVy()[0]));
                seria2.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getVy()[1]));
            }
            figure1.getData().add(seria1);
            figure1.getData().add(seria2);
            seria1.setName("atom 1");
            seria2.setName("atom 2");
        }


        if (energia.isSelected()) {
            for (int i = 0; i < Integer.parseInt(iloscKrokow.getText()); i++) {
                md.verletStep(krokCal);
                seria1.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.geteKin()));
                seria2.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getePot()));
                seria3.getData().add(new XYChart.Data<Number, Number>(i * krokCal, md.getePot() + md.geteKin()));
            }
            figure1.getData().add(seria1);
            figure1.getData().add(seria2);
            figure1.getData().add(seria3);
            seria1.setName("kinetyczna");
            seria2.setName("potencjalna");
            seria3.setName("całkowita");
        }


        figure1.getXAxis().setAutoRanging(true);
        figure1.getYAxis().setAutoRanging(true);
        md = new MD(x, y, vx, vy, 100, 100);

    }

    public void ch1() {
        if (polozenieX.isSelected()) {
            polozenieY.setSelected(false);
            predkoscX.setSelected(false);
            predkoscY.setSelected(false);
            energia.setSelected(false);
        }
    }

    public void ch2() {
        if (polozenieY.isSelected()) {
            polozenieX.setSelected(false);
            predkoscX.setSelected(false);
            predkoscY.setSelected(false);
            energia.setSelected(false);
        }
    }

    public void ch3() {
        if (predkoscX.isSelected()) {
            polozenieX.setSelected(false);
            polozenieY.setSelected(false);
            predkoscY.setSelected(false);
            energia.setSelected(false);
        }
    }

    public void ch4() {
        if (predkoscY.isSelected()) {
            polozenieX.setSelected(false);
            polozenieY.setSelected(false);
            predkoscX.setSelected(false);
            energia.setSelected(false);
        }
    }

    public void ch5() {
        if (energia.isSelected()) {
            polozenieX.setSelected(false);
            polozenieY.setSelected(false);
            predkoscY.setSelected(false);
            predkoscX.setSelected(false);
        }
    }
}
