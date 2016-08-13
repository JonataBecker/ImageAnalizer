package com.github.jonatabecker.analizer;

import com.github.jonatabecker.analizer.pdi.HistogramProcess;
import com.github.jonatabecker.analizer.commons.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import static javafx.application.Application.launch;

/**
 * Class responsible for image view 
 *
 * @author Jonata Becker
 */
public class ImageAnalizer extends Application {

    private BorderPane pane;
    private ImageView original;
    private ImageView modify;
    private BorderPane paneChart;

    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        stage.setMaximized(true);
        stage.setScene(new Scene(pane));
        stage.show();
        BufferedImage reader = ImageIO.read(new File("C:\\Users\\jonata-pc\\Documents\\NetBeansProjects\\ImageAnalizer\\res\\lena.jpg"));

        Image image = new Image(reader);
        original.setImage(SwingFXUtils.toFXImage(reader, null));
        modify.setImage(SwingFXUtils.toFXImage(image.getBufferdImage(), null));
        
        
        System.out.println(image.getAverage());
        createHistogramer(image);
    }

    /**
     * Method responsible for initializing the components
     */
    private void initComponents() {
        pane = new BorderPane();
        pane.setPrefSize(990, 700);
        initComponentsMenu();
        initComponentsBoxImage();
        initComponentsBoxInfo();
    }

    /**
     * Method responsible for creating the menu
     */
    private void initComponentsMenu() {
        // Create menu
        MenuBar menuBar = new MenuBar();
        // File menu
        Menu file = new Menu("File");
        MenuItem fileOpen = new MenuItem("New");
        file.getItems().addAll(fileOpen);
        // Add menu itens
        menuBar.getMenus().addAll(file);
        pane.setTop(menuBar);
    }

    /**
     * Method responsible for creating the imagens boxes
     */
    private void initComponentsBoxImage() {
        GridPane boxImage = new GridPane();
        boxImage.setPadding(new Insets(10));
        // Set constraints
        ColumnConstraints comConstraints = new ColumnConstraints();
        comConstraints.setPercentWidth(50);
        boxImage.getColumnConstraints().addAll(comConstraints, comConstraints);
        // Create original image
        original = new ImageView();
        BorderPane boxImageLeft = new BorderPane();
        boxImageLeft.setStyle("-fx-background-color:#CCC");
        boxImageLeft.setPadding(new Insets(10));
        boxImageLeft.setCenter(original);
        // Create modifyed imagem
        modify = new ImageView();
        BorderPane boxImageRight = new BorderPane();
        boxImageRight.setStyle("-fx-background-color:#CCC");
        boxImageRight.setPrefHeight(500);
        boxImageRight.setPadding(new Insets(10));
        boxImageRight.setCenter(modify);
        // Add imagens in the grid
        boxImage.addColumn(0, boxImageLeft);
        boxImage.addColumn(1, boxImageRight);
        pane.setCenter(boxImage);
    }
   
    /**
     * Method resposible for creating de info box
     */
    private void initComponentsBoxInfo() {
        GridPane boxInfo = new GridPane();
        boxInfo.setPadding(new Insets(10));
        // Set constraints
        ColumnConstraints comConstraints = new ColumnConstraints();
        comConstraints.setPercentWidth(50);
        boxInfo.getColumnConstraints().addAll(comConstraints);
        // Create chart box
        paneChart = new BorderPane();
        BorderPane boxInfoLeft = new BorderPane();
        boxInfoLeft.setCenter(paneChart);
        // Create info box
        BorderPane boxInfoRight = new BorderPane();
        boxInfoRight.setPrefHeight(150);
        boxInfoRight.setPadding(new Insets(10));
        // Add imagens in the grid
        boxInfo.addColumn(0, boxInfoLeft);
        boxInfo.addColumn(1, boxInfoRight);
        pane.setBottom(boxInfo);
    }

    /**
     * Method resposible for creating a histogram chart  
     * 
     * @param image Image information
     */
    private void createHistogramer(Image image) {
        LineChart graficoLinha = new LineChart<>(new CategoryAxis(), new NumberAxis());
        graficoLinha.setCreateSymbols(false);
        graficoLinha.setPrefHeight(100);
        XYChart.Series nodo = new XYChart.Series();
        HistogramProcess histogramer = new HistogramProcess(image);
        int c = 0;
        for (int obj : histogramer.getHistogram()) {
            nodo.getData().add(new XYChart.Data(String.valueOf(++c), obj));
        }
        graficoLinha.getData().addAll(nodo);
        paneChart.setBottom(graficoLinha);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
