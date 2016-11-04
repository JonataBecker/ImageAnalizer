package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the erosion process
 *
 * @author JonataBecker
 */
public class ErosionProcess extends MorphologyProcess {

    private int menor;

    public ErosionProcess(Image image) {
        super(image);
    }

    @Override
    protected void processKernel(int x, int y) {
        menor = Integer.MAX_VALUE;
        executeKernel((pixel, i, j) -> {
            int value = pixel + getKernel()[i][j];
            if (value < menor) {
                menor = value;
            }
        }, x, y);
        executeKernel((pixel, i, j) -> {
           getImg().setPixel(x, y, menor);
        }, x, y);
    }

    private int[][] getKernel() {
        return new int[][]{
            {0, 10, 0},
            {10, 10, 10},
            {0, 10, 0}
        };
    }

}
