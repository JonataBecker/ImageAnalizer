package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a rotation process
 *
 * @author JonataBecker
 */
public class RotationProcess extends TransformationProcess {

    /**
     * Contrutor of a rotation process
     *
     * @param image Image
     * @param angle The angle
     */
    public RotationProcess(Image image, Integer angle) {
        super(image, new double[][]{{Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)) * -1, 0},
        {Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0},
        {0, 0, 1}});
    }

}
