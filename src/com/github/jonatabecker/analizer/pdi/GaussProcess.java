package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for execute the gauss process
 *
 * @author JonataBecker
 */
public class GaussProcess extends ConvolutionProcess {

    public GaussProcess(Image image) {
        super(image);
    }

    @Override
    protected int[][] getKernel() {
        return new int[][]{
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
        };
    }

}
