package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the sobel process
 *
 * @author JonataBecker
 */
public class SobelProcess extends ConvolutionProcess {

    private final Integer threshold;

    public SobelProcess(Image image, Integer threshold) {
        super(image, new Image(image.getWidth(), image.getHeight()));
        this.threshold = threshold;
    }

    /**
     * Process Kernel
     *
     * @param x
     * @param y
     */
    @Override
    protected void processKernel(int x, int y) {
        int valueKernelX = 0;
        int valueKernelY = 0;
        int[][] kernelX = getKernel();
        int[][] kernelY = getKernelY();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = getImage().getPixel(x + (i - 1), y + (j - 1));
                valueKernelX += value * kernelX[i][j];
                valueKernelY += value * kernelY[i][j];
            }
        }
        int pixel = 0;
        double value = Math.sqrt(Math.pow(valueKernelX, 2) + Math.pow(valueKernelY, 2));
        if (value > threshold) {
            pixel = 255;
        }
        getImg().setPixel(x, y, pixel);
    }

    @Override
    protected int[][] getKernel() {
        return new int[][]{
            {1, 0, -1}, {2, 0, -2}, {1, 0, -1}
        };
    }

    protected int[][] getKernelY() {
        return new int[][]{
            {1, 2, 1}, {0, 0, 0}, {-1, -2, -1}
        };
    }

}
