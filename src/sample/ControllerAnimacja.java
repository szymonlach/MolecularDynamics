package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAnimacja implements Initializable {

    @FXML
    private Button btnStart;

    @FXML
    private Pane box;
    @FXML
    private Slider sliderAtoms;
    @FXML
    private Label pasekInfoDol;
    MD md;
    @FXML
    private ScatterChart<Number, Number> figure;
    AnimationTimer atimer;

    int j = 0;
    int nAtoms = 500;
    Losowania los = new Losowania(nAtoms, 100, 100);
    double[] x = los.getX();
    double[] y = los.getY();
    double[] vx = los.losujV(nAtoms);
    double[] vy = los.losujV(nAtoms);
    double[] xNew = new double[nAtoms];
    double[] yNew = new double[nAtoms];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void start() {

        btnStart.setDisable(true);
        double dt = 0.001;
        pasekInfoDol.setText("Liczba atomów " + String.valueOf((int) sliderAtoms.getValue()));
        box.getChildren().clear();

        List<Circle> circles = new ArrayList<>();
        for (int i = 0; i < nAtoms; i++) {
            circles.add(new Circle(2, Color.color(Math.random(), Math.random(), Math.random())));
        }
        box.getChildren().addAll(circles);

        md = new MD(x, y, vx, vy, 100, 100);
        XYChart.Series seria1 = new XYChart.Series();
        XYChart.Series seria2 = new XYChart.Series();
        XYChart.Series seria3 = new XYChart.Series();
        XYChart.Series seria4 = new XYChart.Series();


        figure.getData().add(seria1);
        figure.getData().add(seria2);
        figure.getData().add(seria3);
        figure.getData().add(seria4);

        for (int i = 0; i < 6; i++) {

            atimer = new AnimationTimer() {
                private long lastUpdate;

                @Override
                public void handle(long now) {

                    if (now - lastUpdate > 50_000_000) {

                        for (int i = 0; i < nAtoms; i++) {
                            circles.get(i).relocate(xNew[i], yNew[i]);
                        }
                        lastUpdate = now;
                    } else {
                        j++;
                        md.verletStep(dt);
                        figure.getData().get(0).getData().add(new XYChart.Data<Number, Number>(j * dt, md.geteKin()));
                        figure.getData().get(1).getData().add(new XYChart.Data<Number, Number>(j * dt, md.getePot()));
                        figure.getData().get(2).getData().add(new XYChart.Data<Number, Number>(j * dt, md.getElasticEnergy()));
                        figure.getData().get(3).getData().add(new XYChart.Data<Number, Number>(j * dt, md.getElasticEnergy() + md.geteKin() + md.getePot()));

                        xNew = Wspolrzedne.zmienX(md.getX(), box.getWidth());
                        yNew = Wspolrzedne.zmienX(md.getY(), box.getHeight());
                        figure.getData().get(0).setName("Kin");
                        figure.getData().get(1).setName("Pot");
                        figure.getData().get(2).setName("Ela");
                        figure.getData().get(3).setName("Cał");


                    }
                }
            };
            atimer.start();
        }
    }


}
