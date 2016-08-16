package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a statistic process
 *
 * Set the average pixel value to the pixels with values greater 128
 *
 * @author JonataBecker
 */
public class StatisticalProcessA extends PixelProcess implements ProcessImage {

    /** The average pixel value */
    private final int average;

    /**
     * Construtor responsible for a statistic process
     *
     * @param image
     */
    public StatisticalProcessA(Image image) {
        super(image);
        this.average = new AverageProcess(image).getAverage();
    }

    /**
     * Process a pixel image
     *
     * @param x
     * @param y
     * @param pixel
     */
    @Override
    public void process(int x, int y, int pixel) {
        if (pixel >= 128) {
            getImage().setPixel(x, y, average);
        }
    }

    /**
     * Process the image
     *
     * @return Image
     */
    @Override
    public Image execute() {
        process();
        return getImage();
    }

}
