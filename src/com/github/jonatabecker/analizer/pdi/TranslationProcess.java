package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a translation process
 *
 * @author JonataBecker
 */
public class TranslationProcess extends TransformationProcess {

    /**
     * Construtor of a translation process
     *
     * @param image Image
     * @param x Translation X
     * @param y Translation Y
     */
    public TranslationProcess(Image image, Integer x, Integer y) {
        super(image, new double[][]{{1, 0, x},
        {0, 1, y},
        {0, 0, 1}});
    }

}
