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
        int halfX = getImage().getWidth() / 2;
        int halfY = getImage().getHeight()/ 2;
        int tmpX = x - halfX;
        int tmpY = y - halfY;
        int newX = (int) Math.round(tmpX * mat[0][0] + tmpY * mat[0][1] + 1 * mat[0][2]);
        int newY = (int) Math.round(tmpX * mat[1][0] + tmpY * mat[1][1] + 1 * mat[1][2]);
        newX += halfX;
        newY += halfY;
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
