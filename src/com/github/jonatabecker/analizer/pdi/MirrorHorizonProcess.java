package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a horizon mirror
 *
 * @author JonataBecker
 */
public class MirrorHorizonProcess extends TransformationProcess {

    /**
     * Construtor of a horizon mirror
     *
     * @param image Image
     */
    public MirrorHorizonProcess(Image image) {
        super(image, new double[][]{{-1, 0, 0},
        {0, 1, 0},
        {0, 0, 1}});
    }

}
