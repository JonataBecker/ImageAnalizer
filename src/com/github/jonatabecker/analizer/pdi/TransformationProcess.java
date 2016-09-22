package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for execute transformations process
 *
 * @author JonataBecker
 */
public class TransformationProcess extends PixelProcess implements ProcessImage {
    
    /** Image */
    private final Image img;
    /** Transformation information */
    private final double[][] mat;
    
    /**
     * Construtor of transformations process
     * 
     * @param image Imagem
     * @param mat Transformation information
     */
    public TransformationProcess(Image image, double[][] mat) {
        super(image);
        this.mat = mat;
        this.img = new Image(image.getWidth(), image.getHeight());
    }

    /**
     * Execute de process
     * 
     * @param x Position x
     * @param y Position Y
     * @param pixel Pixel value
     */
    @Override
    public void process(int x, int y, int pixel) {
        int newX = x + 1;
        int newY = y + 1;
        newX = (int) ((double) (newX) * mat[0][0] + (double) (newY) * mat[1][0] + (double) 1 * mat[2][0]);
        newY = (int) ((double) (newX) * mat[0][1] + (double) (newY) * mat[1][1] + (double) 1 * mat[2][1]);
        newX = newX >= 0 ? newX - 1 : getImage().getWidth() + newX;
        newY = newY >= 0 ? newY - 1 : getImage().getHeight() + newY;
        // Pixel position is right
        if (newX < getImage().getWidth() && newY < getImage().getHeight() && newX >= 0 && newY >= 0) {
            img.setPixel(newX, newY, pixel);
        }
    }

    /**
     * Execute the transformation process
     * 
     * @return Image
     */
    @Override
    public Image execute() {
        process();
        return img;
    }
}
