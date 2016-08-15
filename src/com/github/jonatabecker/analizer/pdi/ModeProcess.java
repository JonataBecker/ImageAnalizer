package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class reponsible for the mode calculation
 *
 * @author JonataBecker
 */
public class ModeProcess extends PixelProcess {

    /** Image histogram */
    private final int[] histogram;

    /**
     * Construtor of the mode calculation
     *
     * @param image Image information
     * @param histogram Image histogram
     */
    public ModeProcess(Image image, int[] histogram) {
        super(image);
        this.histogram = histogram;
    }

    @Override
    public void process(int x, int y, int pixel) {
    }

    /**
     * Retorn the image mode
     *
     * @return int
     */
    public int getMode() {
        int mode = 0;
        int index = 0;
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > mode) {
                mode = histogram[i];
                index = i;
            }
        }
        return index;
    }
}
