package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Convolution Process
 *
 * @author JonataBecker
 */
public abstract class ConvolutionProcess extends ImageProcess implements ProcessImage {

    /** Image */
    private final Image img;

    public ConvolutionProcess(Image image) {
        super(image);
        this.img = new Image(image.getBufferdImage());
    }

    /**
     * Process the image
     */
    private void process() {
        for (int x = 1; x < getImage().getWidth() - 1; x++) {
            for (int y = 1; y < getImage().getHeight() - 1; y++) {
                processKernel(x, y);
            }
        }
    }

    /**
     * Process Kernel
     * 
     * @param x
     * @param y 
     */
    protected void processKernel(int x, int y) {
        int value = 0;
        int valueKernel = 0;
        int[][] kernel = getKernel();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                value += getImage().getPixel(x + (i - 1), y + (j - 1)) * kernel[i][j];
                valueKernel += kernel[i][j];
            }
        }
        value /= valueKernel;
        getImg().setPixel(x, y, value);
    }

    /**
     * Returns the kernel
     * 
     * @return int[][]
     */
    protected abstract int[][] getKernel();
    
    /**
     * Returns the new image
     * 
     * @return Image
     */
    protected Image getImg() {
        return img;
    }

    @Override
    public Image execute() {
        process();
        return img;

    }

}
