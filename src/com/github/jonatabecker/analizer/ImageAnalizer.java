package com.github.jonatabecker.analizer;

import com.github.jonatabecker.analizer.pdi.HistogramProcess;
import com.github.jonatabecker.analizer.commons.Image;
import com.github.jonatabecker.analizer.pdi.AverageProcess;
import com.github.jonatabecker.analizer.pdi.MedianProcess;
import com.github.jonatabecker.analizer.pdi.ModeProcess;
import com.github.jonatabecker.analizer.pdi.ProcessImage;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessA;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessB;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessC;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessD;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessE;
import com.github.jonatabecker.analizer.pdi.VarianceProcess;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import static javafx.application.Application.launch;

/**
 * Class responsible for image view
 *
 * @author Jonata Becker
 */
public class ImageAnalizer extends Application {

    private BufferedImage reader;
    private BorderPane pane;
    private ImageView original;
    private ImageView modify;
    private BorderPane paneChart;
    private TextField textAverage;
    private TextField textMedian;
    private TextField textMode;
    private TextField textVariance;

    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        stage.setMaximized(true);
        stage.setScene(new Scene(pane));
        stage.show();
        loadImage(new File("C:\\Users\\jonata-pc\\Documents\\NetBeansProjects\\ImageAnalizer\\res\\lena.jpg"));
    }

    /**
     * Method responsible for loading the imagem information
     *
     * @param file Image file
     */
    private void loadImage(File file) {
        try {
            reader = ImageIO.read(file);
            Image image = new Image(reader);
            original.setImage(SwingFXUtils.toFXImage(reader, null));
            modify.setImage(SwingFXUtils.toFXImage(image.getBufferdImage(), null));
            laodStatistics(image);
        } catch (IOException e) {
            System.out.println(e);
        }
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
        fileOpen.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            loadImage(fileChooser.showOpenDialog(null));
        });
        file.getItems().addAll(fileOpen);
        // Statictics
        Menu statistics = new Menu("Statistics");
        MenuItem statisticalA = new MenuItem("a) Valores maiores ou iguais a 128 de toda a imagem recebem a média de tonalidades de cinza da imagem.");
        statisticalA.setOnAction((ActionEvent event) -> {
            executeProcess(new StatisticalProcessA(new Image(reader)));
        });
        MenuItem statisticalB = new MenuItem("b) Valores maiores ou iguais a 128 de toda a imagem recebem a moda de tonalidades de cinza da imagem.");
        statisticalB.setOnAction((ActionEvent event) -> {
            executeProcess(new StatisticalProcessB(new Image(reader)));
        });
        MenuItem statisticalC = new MenuItem("c) Valores maiores ou iguais a 128 de toda a imagem recebem a mediana de tonalidades de cinza da imagem.");
        statisticalC.setOnAction((ActionEvent event) -> {
            executeProcess(new StatisticalProcessC(new Image(reader)));
        });
        MenuItem statisticalD = new MenuItem("d) Valores menores que a média de toda a imagem recebem preto.");
        statisticalD.setOnAction((ActionEvent event) -> {
            executeProcess(new StatisticalProcessD(new Image(reader)));
        });
        MenuItem statisticalE = new MenuItem("e) Valores maiores que a mediana de toda a imagem recebem branco e menores que a média recebem preto.");
        statisticalE.setOnAction((ActionEvent event) -> {
            executeProcess(new StatisticalProcessE(new Image(reader)));
        });
        statistics.getItems().addAll(statisticalA, statisticalB, statisticalC, statisticalD, statisticalE);
        // Add menu itens
        menuBar.getMenus().addAll(file, statistics);
        pane.setTop(menuBar);
    }

    /**
     * Method responsible for creating the imagens boxes
     */
    private void initComponentsBoxImage() {
        GridPane boxImage = new GridPane();
        boxImage.setPadding(new Insets(0, 10, 0, 10));
        // Set constraints
        ColumnConstraints comConstraints = new ColumnConstraints();
        comConstraints.setPercentWidth(50);
        boxImage.getColumnConstraints().addAll(comConstraints, comConstraints);
        // Create original image
        original = new ImageView();
        BorderPane boxImageLeft = new BorderPane();
        boxImageLeft.setPadding(new Insets(10));
        BorderPane boxImageLeftInner = new BorderPane();
        boxImageLeftInner.setCenter(original);
        boxImageLeftInner.setStyle("-fx-background-color:#EEEEEE; -fx-border-color: #CCC; -fx-border:1px;");
        boxImageLeft.setTop(createTitle("Original Image"));
        boxImageLeft.setCenter(boxImageLeftInner);
        // Create modifyed imagem
        modify = new ImageView();
        BorderPane boxImageRight = new BorderPane();
        boxImageRight.setPrefHeight(490);
        boxImageRight.setPadding(new Insets(10));
        BorderPane boxImageRightInner = new BorderPane();
        boxImageRightInner.setCenter(modify);
        boxImageRightInner.setStyle("-fx-background-color:#EEEEEE; -fx-border-color: #CCC; -fx-border:1px;");
        boxImageRightInner.setMaxHeight(490);
        boxImageRight.setTop(createTitle("Modified image"));
        boxImageRight.setCenter(boxImageRightInner);
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
        boxInfo.setStyle("-fx-border-color: #999; -fx-border-top: 1px;");
        // Set constraints
        ColumnConstraints comConstraints = new ColumnConstraints();
        comConstraints.setPercentWidth(50);
        boxInfo.getColumnConstraints().addAll(comConstraints, comConstraints);
        // Create chart box
        paneChart = new BorderPane();
        BorderPane boxInfoLeft = new BorderPane();
        boxInfoLeft.setCenter(paneChart);
        // Create info box
        BorderPane boxInfoRight = new BorderPane();
        boxInfoRight.setPrefHeight(150);
        boxInfoRight.setPadding(new Insets(10));
        boxInfoRight.setCenter(initComponentsBoxInfoStatistics());
        // Add imagens in the grid
        boxInfo.addColumn(0, boxInfoLeft);
        boxInfo.addColumn(1, boxInfoRight);
        pane.setBottom(boxInfo);
    }

    /**
     * Method responsible for creating the statics box
     *
     * @return GridPane
     */
    private GridPane initComponentsBoxInfoStatistics() {
        GridPane boxInfo = new GridPane();
        // Set constraints
        ColumnConstraints comConstraints = new ColumnConstraints();
        comConstraints.setPercentWidth(50);
        boxInfo.getColumnConstraints().addAll(comConstraints, comConstraints);
        // Create the average field
        BorderPane averageBox = new BorderPane();
        averageBox.setPadding(new Insets(0, 5, 10, 0));
        Label averageLabel = new Label("Average:");
        textAverage = new TextField();
        textAverage.setDisable(true);
        averageBox.setTop(averageLabel);
        averageBox.setCenter(textAverage);
        boxInfo.addColumn(0, averageBox);
        // Create the median field
        BorderPane medianBox = new BorderPane();
        medianBox.setPadding(new Insets(0, 0, 10, 5));
        Label mediaLabel = new Label("Median:");
        textMedian = new TextField();
        textMedian.setDisable(true);
        medianBox.setTop(mediaLabel);
        medianBox.setCenter(textMedian);
        boxInfo.addColumn(1, medianBox);
        // Create the mode field
        BorderPane modeBox = new BorderPane();
        modeBox.setPadding(new Insets(0, 5, 0, 0));
        Label modeLabel = new Label("Mode:");
        textMode = new TextField();
        textMode.setDisable(true);
        modeBox.setTop(modeLabel);
        modeBox.setCenter(textMode);
        boxInfo.addColumn(0, modeBox);
        // Create the variance field
        BorderPane varianceBox = new BorderPane();
        varianceBox.setPadding(new Insets(0, 0, 0, 5));
        Label varianceLabel = new Label("Variance:");
        textVariance = new TextField();
        textVariance.setDisable(true);
        varianceBox.setTop(varianceLabel);
        varianceBox.setCenter(textVariance);
        boxInfo.addColumn(1, varianceBox);
        return boxInfo;
    }

    /**
     * Method resposible for creating a histogram chart
     *
     * @param histogram Histogram data
     */
    private void loadHistogramer(int[] histogram) {
        LineChart lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setCreateSymbols(false);
        lineChart.setPrefHeight(80);
        XYChart.Series nodo = new XYChart.Series();
        int c = 0;
        for (int obj : histogram) {
            nodo.getData().add(new XYChart.Data(String.valueOf(++c), obj));
        }
        lineChart.getData().addAll(nodo);
        paneChart.setCenter(lineChart);
    }

    /**
     * Method responsible for loading the image statistics
     *
     * @param imagem Image information
     */
    private void laodStatistics(Image imagem) {
        int[] histogram = new HistogramProcess(imagem).getHistogram();
        int average = new AverageProcess(imagem).getAverage();
        int median = new MedianProcess(imagem).getMedian();
        int variace = new VarianceProcess(imagem, average).getVariance();
        int mode = new ModeProcess(imagem, histogram).getMode();
        loadHistogramer(histogram);
        textAverage.setText(String.valueOf(average));
        textMedian.setText(String.valueOf(median));
        textVariance.setText(String.valueOf(variace));
        textMode.setText(String.valueOf(mode));
    }

    /**
     * Method responsible for creating titles
     *
     * @param title The title
     * @return Label
     */
    private Label createTitle(String title) {
        return createTitle(title, 16);
    }

    /**
     * Method responsible for creating titles
     *
     * @param title The title
     * @param size Font Size
     * @return Label
     */
    private Label createTitle(String title, int size) {
        Label label = new Label(title);
        label.setFont(new Font("Arial", size));
        label.setTextFill(Color.web("#666"));
        label.setPadding(new Insets(0, 0, 5, 0));
        return label;
    }
    
    /**
     * Execute a image process
     * 
     * @param process
     */
    private void executeProcess(ProcessImage process) {
        modify.setImage(SwingFXUtils.toFXImage(process.execute().getBufferdImage(), null)); 
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
