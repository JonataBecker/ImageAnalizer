package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the varience calculation
 *
 * @author JonataBecker
 */
public class VarianceProcess extends PixelProcess {

    /** Imagem average */
    private final int average;
    /** Total value */
    private int total;

    /**
     * Construtor of the variance calculation
     *
     * @param image Image information
     * @param average Imagem Avwrage
     */
    public VarianceProcess(Image image, int average) {
        super(image);
        this.average = average;
    }

    /**
     * Execute the pixel process
     *
     * @param pixel Pixel value
     * @param x Coordinate
     * @param y Coordinate
     */
    @Override
    public void process(int x, int y, int pixel) {
        total += Math.pow(pixel - average, 2);
    }

    /**
     * Return the image variance
     *
     * @return int
     */
    public int getVariance() {
        process();
        return total / (getImage().getWidth() * getImage().getHeight());
    }

}
