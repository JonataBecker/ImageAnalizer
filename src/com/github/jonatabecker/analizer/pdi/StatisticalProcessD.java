
package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a statistic process
 *
 * Set the black pixel to pixels with values greater the average
 *
 * @author JonataBecker
 */
public class StatisticalProcessD extends PixelProcess implements ProcessImage {

    /** The average pixel value */
    private final int average;

    /**
     * Construtor responsible for a statistic process
     *
     * @param image
     */
    public StatisticalProcessD(Image image) {
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
