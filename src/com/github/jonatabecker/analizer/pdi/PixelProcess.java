package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the pixel process
 *
 * @author JonataBecker
 */
public abstract class PixelProcess extends ImageProcess {

    /**
     * Construtor responsible for the pixel process
     *
     * @param image Image information
     */
    public PixelProcess(Image image) {
        super(image);
    }

    /**
     * Execute the process
     */
    protected void process() {
        for (int x = 0; x < getImage().getWidth(); x++) {
            for (int y = 0; y < getImage().getHeight(); y++) {
                process(x, y, getImage().getPixel(x, y));
            }
        }
    }

    /**
     * Process a pixel
     *
     * @param x
     * @param y
     * @param pixel
     */
    public abstract void process(int x, int y, int pixel);
}
