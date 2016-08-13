/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jonatabecker.analizer.pdi;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author JonataBecker
 */
public class ImageConverterGray {
    
    public int[][] convert(BufferedImage imagem) {
        int [][] map = new int[imagem.getHeight()][imagem.getWidth()];
        
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
