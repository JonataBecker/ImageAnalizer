package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the histogram process
 * 
 * @author JonataBecker
 */
public class HistogramProcess extends PixelProcess {

    /** The histogram information */
    private final int[] data;
    
    /**
     * Constructor of the histogram process
     * 
     * @param image Image information
     */
    public HistogramProcess(Image image) {
        super(image);
        data = new int[255];
    }
    
    /**
     * Execute the pixel process
     * 
     * @param pixel Pixel value
     */
    @Override
    public void process(int pixel) {
        data[pixel]++;
    }
    
    /**
     * Return the histogram data.
     * 
     * @return int[] List of 255 positions
     */
    public int[] getHistogram() {
        process();
        return data;
    }
    
}
