package com.github.jonatabecker.analizer;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

/**
 * View responsible for the threshold process
 *
 * @author JonataBecker
 */
public class Threshold extends BorderPane {

    public static final int INIT = 150;

    private final List<ThresholdListener> observers = new ArrayList();
    private final Slider slider;
    
    public Threshold() {
        slider = new Slider();
        slider.setMin(1);
        slider.setMax(255);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueChangingProperty().addListener((event, event1, event2) -> {
            fire((int) slider.getValue());
        });
        setCenter(slider);
        setPrefWidth(900);
    }

    public void setValue(int value) {
        slider.setValue(value);
        fire(value);
    }
    
    private void fire(int value) {
        observers.forEach((observer) -> {
            observer.run(value);
        });
    }

    public void addObserver(ThresholdListener observer) {
        observers.add(observer);
    }

    public interface ThresholdListener {

        public void run(int threshold);
    }
}
