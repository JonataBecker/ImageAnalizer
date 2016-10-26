package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the threshold process
 * 
 * @author JonataBecker
 */
public class ThresholdProcess extends PixelProcess implements ProcessImage {

    private final Integer threshold;
    
    public ThresholdProcess(Image image, Integer threshold) {
        super(image);
        this.threshold = threshold;
    }

    @Override
    public void process(int x, int y, int pixel) {
        if (pixel > threshold) {
            getImage().setPixel(x, y, 255);
        } else {
            getImage().setPixel(x, y, 0);
        }
    }

    @Override
    public Image execute() {
        process();
        return getImage();        
    }
    
}
