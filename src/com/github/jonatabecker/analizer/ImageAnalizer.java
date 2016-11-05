package com.github.jonatabecker.analizer;

import com.github.jonatabecker.analizer.pdi.HistogramProcess;
import com.github.jonatabecker.analizer.commons.Image;
import com.github.jonatabecker.analizer.pdi.AberturaProcess;
import com.github.jonatabecker.analizer.pdi.AverageProcess;
import com.github.jonatabecker.analizer.pdi.DilationProcess;
import com.github.jonatabecker.analizer.pdi.EnlargmentProcess;
import com.github.jonatabecker.analizer.pdi.ErosionProcess;
import com.github.jonatabecker.analizer.pdi.FechamentoProcess;
import com.github.jonatabecker.analizer.pdi.FreeProcess;
import com.github.jonatabecker.analizer.pdi.GaussProcess;
import com.github.jonatabecker.analizer.pdi.HeltProcess;
import com.github.jonatabecker.analizer.pdi.MedianFilterProcess;
import com.github.jonatabecker.analizer.pdi.MedianProcess;
import com.github.jonatabecker.analizer.pdi.MirrorHorizonProcess;
import com.github.jonatabecker.analizer.pdi.MirrorVeriticalProcess;
import com.github.jonatabecker.analizer.pdi.ModeProcess;
import com.github.jonatabecker.analizer.pdi.ProcessImage;
import com.github.jonatabecker.analizer.pdi.ReductionProcess;
import com.github.jonatabecker.analizer.pdi.RotationProcess;
import com.github.jonatabecker.analizer.pdi.SobelProcess;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessA;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessB;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessC;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessD;
import com.github.jonatabecker.analizer.pdi.StatisticalProcessE;
import com.github.jonatabecker.analizer.pdi.ThresholdProcess;
import com.github.jonatabecker.analizer.pdi.TranslationProcess;
import com.github.jonatabecker.analizer.pdi.VarianceProcess;
import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.util.Pair;

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
    private LineChart lineChart;
    private TextField textAverage;
    private TextField textMedian;
    private TextField textMode;
    private TextField textVariance;
    private boolean stopCamera = true;
    private Webcam webCam;
    private Class lastProcess;
    private Object[] lastParameters;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initComponents();
        stage.setMaximized(true);
        stage.setScene(new Scene(pane));
        stage.show();
        loadImage(new File(getClass().getResource("/lena.jpg").getFile()));
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
     * Method responsible dor open the WebCam
     */
    private void openWebCam() {
        stopCamera = false;
        if (Webcam.getWebcams().isEmpty()) {
            return;
        }
        webCam = Webcam.getWebcams().get(0);
        webCam.getDevice().setResolution(new Dimension(500, 485));
        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                webCam.open();
                startWebCamStream();
                return null;
            }
        };
        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();
    }

    /**
     * Method responsible for close the WebCam
     */
    private void closeWebCam() {
        if (webCam != null) {
            webCam.close();
        }
        stopCamera = true;
    }

    /**
     * Method responsible for start the WebCam
     */
    protected void startWebCamStream() {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (!stopCamera) {
                    try {
                        BufferedImage tmp = webCam.getImage();
                        if (tmp != null) {
                            reader = tmp;
                            Image image = new Image(reader);
                            Platform.runLater(() -> {
                                original.setImage(SwingFXUtils.toFXImage(reader, null));
                            });
                            laodStatistics(image);
                            executeProcess(lastProcess, image, lastParameters);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
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
        Menu file = new Menu("Arquivo");
        MenuItem fileOpen = new MenuItem("Novo");
        fileOpen.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            loadImage(fileChooser.showOpenDialog(null));
        });
        file.getItems().addAll(fileOpen);
        // Statictics
        Menu statistics = new Menu("Estatísticas");
        MenuItem staticticInfo = new MenuItem("Informações");
        staticticInfo.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText(new Information(new Image(reader)).getInfo());
            alert.showAndWait();
        });
        MenuItem statisticalA = new MenuItem("a) Valores maiores ou iguais a 128 de toda a imagem recebem a média de tonalidades de cinza da imagem.");
        statisticalA.setOnAction((ActionEvent event) -> {
            executeProcessImage(StatisticalProcessA.class, new Image(reader));
        });
        MenuItem statisticalB = new MenuItem("b) Valores maiores ou iguais a 128 de toda a imagem recebem a moda de tonalidades de cinza da imagem.");
        statisticalB.setOnAction((ActionEvent event) -> {
            executeProcessImage(StatisticalProcessB.class, new Image(reader));
        });
        MenuItem statisticalC = new MenuItem("c) Valores maiores ou iguais a 128 de toda a imagem recebem a mediana de tonalidades de cinza da imagem.");
        statisticalC.setOnAction((ActionEvent event) -> {
            executeProcessImage(StatisticalProcessC.class, new Image(reader));
        });
        MenuItem statisticalD = new MenuItem("d) Valores menores que a média de toda a imagem recebem preto.");
        statisticalD.setOnAction((ActionEvent event) -> {
            executeProcessImage(StatisticalProcessD.class, new Image(reader));
        });
        MenuItem statisticalE = new MenuItem("e) Valores maiores que a mediana de toda a imagem recebem branco e menores que a média recebem preto.");
        statisticalE.setOnAction((ActionEvent event) -> {
            executeProcessImage(StatisticalProcessE.class, new Image(reader));
        });
        statistics.getItems().addAll(staticticInfo, statisticalA, statisticalB, statisticalC, statisticalD, statisticalE);
        // Transformation
        Menu transformation = new Menu("Transformações");
        MenuItem transformationTranslation = new MenuItem("Translação");
        transformationTranslation.setOnAction((ActionEvent event) -> {
            executeTranslation();
        });
        MenuItem transformationRotation = new MenuItem("Rotação");
        transformationRotation.setOnAction((ActionEvent event) -> {
            executeRotation();
        });
        MenuItem transformationEnlargment = new MenuItem("Ampliação");
        transformationEnlargment.setOnAction((ActionEvent event) -> {
            executeEnlargment();
        });
        MenuItem transformationReduction = new MenuItem("Redução");
        transformationReduction.setOnAction((ActionEvent event) -> {
            executeReduction();
        });
        Menu transformationMirror = new Menu("Espelhamento");
        MenuItem transformationMirrorHor = new MenuItem("Horizontal");
        transformationMirrorHor.setOnAction((ActionEvent event) -> {
            executeProcessImage(MirrorHorizonProcess.class, new Image(reader));
        });
        MenuItem transformationMirrorVer = new MenuItem("Vertical");
        transformationMirrorVer.setOnAction((ActionEvent event) -> {
            executeProcessImage(MirrorVeriticalProcess.class, new Image(reader));
        });
        transformationMirror.getItems().addAll(transformationMirrorHor, transformationMirrorVer);
        MenuItem transformationFree = new MenuItem("Livre");
        transformationFree.setOnAction((ActionEvent event) -> {
            executeFree();
        });
        transformation.getItems().addAll(transformationTranslation, transformationRotation, transformationEnlargment,
                transformationReduction, transformationMirror, transformationFree);
        // Filtros
        Menu filtro = new Menu("Filtros");
        MenuItem filtroMedian = new MenuItem("Mediana");
        filtroMedian.setOnAction((ActionEvent event) -> {
            executeProcessImage(MedianFilterProcess.class, new Image(reader));
        });
        MenuItem filtroGaus = new MenuItem("Gauss");
        filtroGaus.setOnAction((ActionEvent event) -> {
            executeProcessImage(GaussProcess.class, new Image(reader));
        });
        MenuItem filtroLimirializacao = new MenuItem("Limirialização");
        filtroLimirializacao.setOnAction((ActionEvent event) -> {
            executeThreshold();
        });
        MenuItem filtroDeteccaoBordas = new MenuItem("Detecção de bordas");
        filtroDeteccaoBordas.setOnAction((ActionEvent event) -> {
            executeDeteccao();
        });
        // Morfologia 
        Menu morfologia = new Menu("Morfologia");
        MenuItem morfologiaDilatacao = new MenuItem("Dilatação");
        morfologiaDilatacao.setOnAction((ActionEvent event) -> {
            executeProcessImage(DilationProcess.class, new Image(reader));
        });
        MenuItem morfologiaErosao = new MenuItem("Erosão");
        morfologiaErosao.setOnAction((ActionEvent event) -> {
            executeProcessImage(ErosionProcess.class, new Image(reader));
        });
        MenuItem morfologiaAbertura = new MenuItem("Abertura");
        morfologiaAbertura.setOnAction((ActionEvent event) -> {
            executeProcessImage(AberturaProcess.class, new Image(reader));
        });
        MenuItem morfologiaFechamento = new MenuItem("Fechamento");
        morfologiaFechamento.setOnAction((ActionEvent event) -> {
            executeProcessImage(FechamentoProcess.class, new Image(reader));
        });
        MenuItem morfologiaAfinamento = new MenuItem("Afinamento");
        morfologiaAfinamento.setOnAction((ActionEvent event) -> {
            executeProcessImage(HeltProcess.class, new ThresholdProcess(new Image(reader), 150).execute());
        });
        morfologia.getItems().addAll(morfologiaDilatacao, morfologiaErosao, morfologiaAbertura, morfologiaFechamento,
                morfologiaAfinamento);
        // Add menu itens
        menuBar.getMenus().addAll(file, statistics, transformation, filtro, morfologia);
        pane.setTop(menuBar);
    }

    /**
     * Execute the translation process
     */
    private void executeTranslation() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Translação");
        // Set the button types.
        ButtonType buttonOk = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonOk, ButtonType.CANCEL);
        // Create the labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField x = new TextField();
        TextField y = new TextField();
        grid.add(new Label("x:"), 0, 0);
        grid.add(x, 1, 0);
        grid.add(new Label("y:"), 0, 1);
        grid.add(y, 1, 1);
        dialog.getDialogPane().setContent(grid);
        // Request focus
        Platform.runLater(() -> x.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonOk) {
                return new Pair<>(x.getText(), y.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(tranlation -> {
            Integer posX = Integer.valueOf(tranlation.getKey());
            Integer posY = Integer.valueOf(tranlation.getValue());
            executeProcessImage(TranslationProcess.class, new Image(reader), posX, posY);
        });
    }

    /**
     * Execute the rotation process
     */
    private void executeRotation() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Ângulo:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> executeProcessImage(RotationProcess.class, new Image(reader), Integer.valueOf(name)));
    }

    /**
     * Execute tje enlargment process
     */
    private void executeEnlargment() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Tamanho:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> executeProcessImage(EnlargmentProcess.class, new Image(reader), Double.valueOf(name)));
    }

    /**
     * Execute the reduction process
     */
    private void executeReduction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Tamanho:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> executeProcessImage(ReductionProcess.class, new Image(reader), Double.valueOf(name)));
    }

    /**
     * Execute the free transformation
     */
    private void executeFree() {
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.setTitle("Livre");
        // Set the button types.
        ButtonType buttonOk = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonOk, ButtonType.CANCEL);
        // Create the labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField[][] emls = new TextField[3][3];
        grid.setPadding(new Insets(20, 150, 10, 10));
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                TextField eml = new TextField();
                eml.setText("0");
                emls[x][y] = eml;
                grid.add(eml, x, y);
            }
        }
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonOk) {
                Double[] values = new Double[9];
                int c = 0;
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        values[c++] = Double.valueOf(emls[x][y].getText());
                    }
                }
                return values;
            }
            return null;
        });
        Optional<Double[]> result = dialog.showAndWait();
        result.ifPresent(tranlation -> {
            executeProcessImage(FreeProcess.class, new Image(reader), (Object[]) tranlation);
        });
    }

    /**
     * Execute the threshold process
     */
    private void executeThreshold() {
        Threshold th = new Threshold();
        th.addObserver((threshold) -> {
            executeProcessImage(ThresholdProcess.class, new Image(reader), threshold);
        });
        th.setValue(150);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
    }

    /**
     * Execute the border detection process
     */
    private void executeDeteccao() {
        Threshold th = new Threshold();
        th.addObserver((threshold) -> {
            executeProcessImage(SobelProcess.class, new Image(reader), threshold);
        });
        th.setValue(200);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Detecção de bordas");
        dialog.getDialogPane().setContent(th);
        dialog.show();
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
        original.setFitHeight(465);
        original.setFitWidth(620);
        original.setPreserveRatio(true);
        BorderPane boxImageLeft = new BorderPane();
        boxImageLeft.setPadding(new Insets(10));
        BorderPane boxImageLeftInner = new BorderPane();
        boxImageLeftInner.setCenter(original);
        boxImageLeftInner.setStyle("-fx-background-color:#EEEEEE; -fx-border-color: #CCC; -fx-border:1px;");
        ToggleButton button = new ToggleButton("WebCam");
        button.setOnAction((ActionEvent event) -> {
            if (stopCamera) {
                openWebCam();
            } else {
                closeWebCam();
            }
        });
        BorderPane titleOriginal = new BorderPane();
        titleOriginal.setLeft(createTitle("Imagem original"));
        titleOriginal.setRight(button);
        boxImageLeft.setTop(titleOriginal);
        boxImageLeft.setCenter(boxImageLeftInner);
        // Create modifyed imagem
        modify = new ImageView();
        modify.setFitHeight(465);
        modify.setFitWidth(620);
        modify.setPreserveRatio(true);
        BorderPane boxImageRight = new BorderPane();
        boxImageRight.setPrefHeight(465);
        boxImageRight.setPadding(new Insets(10));
        BorderPane boxImageRightInner = new BorderPane();
        boxImageRightInner.setCenter(modify);
        boxImageRightInner.setStyle("-fx-background-color:#EEEEEE; -fx-border-color: #CCC; -fx-border:1px;");
        boxImageRight.setTop(createTitle("Imagem modificada"));
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
        lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setCreateSymbols(false);
        lineChart.setPrefHeight(80);
        lineChart.setAnimated(false);
        paneChart.setCenter(lineChart);
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
        Label averageLabel = new Label("Média:");
        textAverage = new TextField();
        textAverage.setDisable(true);
        averageBox.setTop(averageLabel);
        averageBox.setCenter(textAverage);
        boxInfo.addColumn(0, averageBox);
        // Create the median field
        BorderPane medianBox = new BorderPane();
        medianBox.setPadding(new Insets(0, 0, 10, 5));
        Label mediaLabel = new Label("Mediana:");
        textMedian = new TextField();
        textMedian.setDisable(true);
        medianBox.setTop(mediaLabel);
        medianBox.setCenter(textMedian);
        boxInfo.addColumn(1, medianBox);
        // Create the mode field
        BorderPane modeBox = new BorderPane();
        modeBox.setPadding(new Insets(0, 5, 0, 0));
        Label modeLabel = new Label("Moda:");
        textMode = new TextField();
        textMode.setDisable(true);
        modeBox.setTop(modeLabel);
        modeBox.setCenter(textMode);
        boxInfo.addColumn(0, modeBox);
        // Create the variance field
        BorderPane varianceBox = new BorderPane();
        varianceBox.setPadding(new Insets(0, 0, 0, 5));
        Label varianceLabel = new Label("Variância:");
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
        Platform.runLater(() -> {
            XYChart.Series nodo = new XYChart.Series();
            int c = 0;
            for (int obj : histogram) {
                nodo.getData().add(new XYChart.Data(String.valueOf(++c), obj));
            }
            lineChart.getData().clear();
            lineChart.getData().addAll(nodo);
        });
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
        Platform.runLater(() -> {
            textAverage.setText(String.valueOf(average));
            textMedian.setText(String.valueOf(median));
            textVariance.setText(String.valueOf(variace));
            textMode.setText(String.valueOf(mode));
        });
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
     * @param param
     */
    private void executeProcessImage(Class process, Image image, Object... param) {
        lastProcess = process;
        lastParameters = param;
        if (stopCamera) {
            executeProcess(process, image, param);
        }
    }

    /**
     * Execute a image process
     *
     * @param process
     * @param param
     */
    private void executeProcess(Class process, Image image, Object... param) {
        try {
            BufferedImage buff;
            if (process != null) {
                List<Class> list = new ArrayList<>();
                list.add(Image.class);
                if (param != null) {
                    for (Object p : param) {
                        list.add(p.getClass());
                    }
                }
                Class[] t = list.toArray(new Class[list.size()]);
                List<Object> values = new ArrayList<>();
                values.add(image);
                Collections.addAll(values, param);
                Object[] val = values.toArray(new Object[values.size()]);
                ProcessImage proc = (ProcessImage) process.getConstructor(t).newInstance(val);
                buff = proc.execute().getBufferdImage();
            } else {
                buff = image.getBufferdImage();
            }
            Platform.runLater(() -> {
                modify.setImage(SwingFXUtils.toFXImage(buff, null));
            });
        } catch (ReflectiveOperationException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
