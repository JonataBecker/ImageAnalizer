package com.github.jonatabecker.analizer.pdi;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class responsible for convert a image to gray
 * 
 * @author JonataBecker
 */
public class ImageConverterGray {

    /**
     * Convert a image to gray
     * 
     * @param imagem
     * @return int[][]
     */
    public int[][] convert(BufferedImage imagem) {
        int [][] map = new int[imagem.getWidth()][imagem.getHeight()];
        for (int x = 0; x < imagem.getWidth(); x++) {
            for (int y = 0; y < imagem.getHeight(); y++) {
                Color color = new Color(imagem.getRGB(x, y), false);
                map[x][y] = (int)((color.getRed() * 0.2126) + 
                        (color.getGreen() * 0.7152) + 
                        (color.getBlue() * 0.0722)) ;
            }
        }
        return map;
    }
    
    /**
     * Convert a image data to a BufferdImage
     * 
     * @param image
     * @param width
     * @param height
     * @return BufferedImage
     */
    public BufferedImage convertToBuffer(int[][] image, int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.OPAQUE);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                buffer.setRGB(x, y, new Color(image[x][y], image[x][y], image[x][y]).getRGB());
            }
        }
        return buffer;
    }
 
}
