package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class resposible for a vertical mirror
 *
 * @author JonataBecker
 */
public class MirrorVeriticalProcess extends TransformationProcess {

    /**
     * Construtor of a vertical mirror
     *
     * @param image Imagem
     */
    public MirrorVeriticalProcess(Image image) {
        super(image, new double[][]{{1, 0, 0},
        {0, -1, 0},
        {0, 0, 1}});
    }

}
