package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the dilatation process
 *
 * @author JonataBecker
 */
public class DilationProcess extends MorphologyProcess {

    private int maior;

    public DilationProcess(Image image) {
        super(image);
    }

    @Override
    protected void processKernel(int x, int y) {
        maior = 0;
        executeKernel((pixel, i, j) -> {
            int value = pixel + getKernel()[i][j];
            if (value > maior) {
                maior = value;
            }
        }, x, y);
        if (maior > 255) {
            maior = 255;
        }
        if (maior > 10) {
            executeKernel((pixel, i, j) -> {
                getImg().setPixel(x, y, maior);
            }, x, y);
        }
    }

    private int[][] getKernel() {
        return new int[][]{
            {0, 10, 0},
            {10, 10, 10},
            {0, 10, 0}
        };
    }

}
