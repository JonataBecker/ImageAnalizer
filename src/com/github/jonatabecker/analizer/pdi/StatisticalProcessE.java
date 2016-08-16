package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a statistic process
 *
 * Set the white pixel to the pixels with values greater than the median and 
 * pixels Less than the average receives the black pixel
 *
 * @author JonataBecker
 */
public class StatisticalProcessE extends PixelProcess implements ProcessImage {

    /** The median pixel value */
    private final int median;
    /** The average pixel value */
    private final int average;

    /**
     * Construtor responsible for a statistic process
     *
     * @param image
     */
    public StatisticalProcessE(Image image) {
        super(image);
        this.median = new MedianProcess(image).getMedian();
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
        if (pixel > median) {
            getImage().setPixel(x, y, 255);
        }
        if (pixel < average) {
            getImage().setPixel(x, y, 0);
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
