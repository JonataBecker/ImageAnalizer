package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class responsible for execute the median filter
 *
 * @author JonataBecker
 */
public class MedianFilterProcess extends ConvolutionProcess {

    public MedianFilterProcess(Image image) {
        super(image);
    }

    @Override
    protected void processKernel(int x, int y) {
        List<Integer> pixels = new ArrayList<>();   
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pixels.add(getImage().getPixel(x + (i - 1), y + (j - 1)));
            }
        }
        Collections.sort(pixels);
        int median = pixels.get((int) Math.ceil(pixels.size() / 2));
        getImg().setPixel(x, y, median);
    }

    @Override
    protected int[][] getKernel() {
        return new int[][]{};
    }
    
}
