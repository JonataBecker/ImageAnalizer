package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a free transformation
 *
 * @author JonataBecker
 */
public class FreeProcess extends TransformationProcess {

    /**
     * Contrutor of a rotation process
     *
     * @param image Image
     */
    public FreeProcess(Image image, Double p1, Double p2, Double p3, Double p4, Double p5, Double p6, Double p7, Double p8, Double p9) {
        super(image, new double[][]{{p1, p2, p3},
        {p4, p5, p6},
        {p7, p8, p9}
        });
    }

}
