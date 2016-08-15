package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for calculate de average process
 *
 * @author JonataBecker
 */
public class AverageProcess extends PixelProcess {

    /** The sum total */
    private long total;

    /**
     * Constructor of average process
     *
     * @param image Image information
     */
    public AverageProcess(Image image) {
        super(image);
    }

    /**
     * Execute de pixel process
     *
     * @param pixel Pixel value
     * @param x Coordinate
     * @param y Coordinate
     */
    @Override
    public void process(int x, int y, int pixel) {
        total += pixel;
    }

    /**
     * Return the image average
     *
     * @return int
     */
    public int getAverage() {
        process();
        return (int) total / (getImage().getWidth() * getImage().getHeight());
    }

}
