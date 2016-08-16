package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a statistic process
 *
 * Set the mode pixel value to the pixels with values greater 128
 *
 * @author JonataBecker
 */
public class StatisticalProcessB extends PixelProcess implements ProcessImage {

    /** The mode pixel value */
    private final int mode;

    /**
     * Construtor responsible for a statistic process
     *
     * @param image
     */
    public StatisticalProcessB(Image image) {
        super(image);
        this.mode = new ModeProcess(image, new HistogramProcess(image).getHistogram()).getMode();
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
            getImage().setPixel(x, y, mode);
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
