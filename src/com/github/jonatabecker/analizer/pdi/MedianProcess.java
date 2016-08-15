package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class resposible for the median calculation
 *
 * @author JonataBecker
 */
public class MedianProcess extends PixelProcess {

    /** Pixel list */
    private final List<Integer> pixels;

    /**
     * Construtor of the median calculation
     *
     * @param image Image information
     */
    public MedianProcess(Image image) {
        super(image);
        pixels = new ArrayList<>();
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
        pixels.add(pixel);
    }

    /**
     * Return the median value
     *
     * @return int
     */
    public int getMedian() {
        process();
        Collections.sort(pixels);
        return pixels.get((int) Math.ceil(pixels.size() / 2));
    }

}
