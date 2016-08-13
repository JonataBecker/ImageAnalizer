package com.github.jonatabecker.analizer.commons;

import com.github.jonatabecker.analizer.pdi.ImageConverterGray;
import java.awt.image.BufferedImage;

/**
 *
 * @author JonataBecker
 */
public class Image {

    private final int width;
    private final int height;
    private final ImageConverterGray grayConverter;
    private int[][] data;

    public Image(BufferedImage imagem) {
        this.width = imagem.getWidth();
        this.height = imagem.getHeight();
        this.grayConverter = new ImageConverterGray();
        this.data = convertImage(imagem);
    }

    private int[][] convertImage(BufferedImage imagem) {
        return grayConverter.convert(imagem);
    }

    public BufferedImage getBufferdImage() {
        return grayConverter.convertToBuffer(data, width, height);
    }

    public int[][] getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int getPixel(int x, int y) {
        return data[x][y];
    }
    
    public int getAverage() {
        int total = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                total += data[x][y];
            }
        }
        return total / (width * height);
    }

}
