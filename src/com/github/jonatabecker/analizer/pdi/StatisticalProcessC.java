package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a statistic process
 *
 * Set the median pixel value to the pixels with values greater 128
 *
 * @author JonataBecker
 */
public class StatisticalProcessC extends PixelProcess implements ProcessImage {

    /** The median pixel value */
    private final int median;

    /**
     * Construtor responsible for a statistic process
     *
     * @param image
     */
    public StatisticalProcessC(Image image) {
        super(image);
        this.median = new MedianProcess(image).getMedian();
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
            getImage().setPixel(x, y, median);
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
