package com.github.jonatabecker.analizer.commons;

import com.github.jonatabecker.analizer.pdi.ImageConverterGray;
import java.awt.image.BufferedImage;

/**
 * Class responsible for the image information
 *
 * @author JonataBecker
 */
public class Image {

    /** Image width */
    private final int width;
    /** Image height */
    private final int height;
    /** Gray converter */
    private final ImageConverterGray grayConverter;
    /** Image data */
    private final int[][] data;

    /**
     * Image information contruction
     *
     * @param imagem
     */
    public Image(BufferedImage imagem) {
        this.width = imagem.getWidth();
        this.height = imagem.getHeight();
        this.grayConverter = new ImageConverterGray();
        this.data = convertImage(imagem);
    }

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new int[width][height];
        this.grayConverter = new ImageConverterGray();
    }

    /**
     * Execute conversion to gray
     *
     * @param imagem
     * @return int[][]
     */
    private int[][] convertImage(BufferedImage imagem) {
        return grayConverter.convert(imagem);
    }

    /**
     * Return the image information
     *
     * @return BufferedImage
     */
    public BufferedImage getBufferdImage() {
        return grayConverter.convertToBuffer(data, width, height);
    }

    /**
     * Return de image width
     *
     * @return int
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the imagem height
     *
     * @return int
     */
    public int getHeight() {
        return height;
    }

    /**
     * Return the pixel value
     *
     * @param x
     * @param y
     * @return int
     */
    public int getPixel(int x, int y) {
        return data[x][y];
    }

    /**
     * Define a pixel value
     *
     * @param x
     * @param y
     * @param pixel
     */
    public void setPixel(int x, int y, int pixel) {
        if (width > x && height > y && pixel <= 255) {
            data[x][y] = pixel;
        }
    }

}
