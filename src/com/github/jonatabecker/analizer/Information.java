package com.github.jonatabecker.analizer;

import com.github.jonatabecker.analizer.commons.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class responsible for the image information
 *
 * @author JonataBecker
 */
public class Information {

    /** The image */
    private final Image image;

    /**
     * Construtor responsible for the image information
     *
     * @param image The image
     */
    public Information(Image image) {
        this.image = image;
    }

    /**
     * Return the avarege
     *
     * @return int
     */
    private int getAverage() {
        int ini = image.getWidth() / 2;
        int total = 0;
        for (int x = ini; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                total += image.getPixel(x, y);
            }
        }
        return total / (ini * image.getHeight());
    }

    /**
     * Return the median
     *
     * @return int
     */
    private int getMedian() {
        int max = image.getWidth() / 2;
        List<Integer> pixels = new ArrayList<>();
        for (int x = 0; x < max; x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pixels.add(image.getPixel(x, y));
            }
        }
        Collections.sort(pixels);
        return pixels.get((int) Math.ceil(pixels.size() / 2));
    }

    /**
     * Return the mode
     *
     * @return int
     */
    private int getMode() {
        int[] histogram = new int[256];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = y; x < image.getWidth(); x++) {
                histogram[image.getPixel(x, y)]++;
            }
        }
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

    /**
     * Return de variance
     *
     * @return int
     */
    private int getVariance() {
        int totalAverage = 0;
        int num = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = x; y < image.getHeight(); y++) {
                totalAverage += image.getPixel(x, y);
                num++;
            }
        }
        int average = totalAverage / num;
        int total = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = x; y < image.getHeight(); y++) {
                total += Math.pow(image.getPixel(x, y) - average, 2);
            }
        }
        return total / num;
    }

    /**
     * Return the image information
     *
     * @return String
     */
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Média da metade direita: ").append(getAverage()).append("\n");
        sb.append("Mediana da metade esquerda: ").append(getMedian()).append("\n");
        sb.append("Moda para parte superior da diagonal principal: ").append(getMode()).append("\n");
        sb.append("Variância para parte inferior da diagonal principal: ").append(getVariance()).append("\n");
        return sb.toString();
    }

}
