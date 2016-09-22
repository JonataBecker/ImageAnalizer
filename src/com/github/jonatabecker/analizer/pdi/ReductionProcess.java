package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for a reduction process
 * 
 * @author JonataBecker
 */
public class ReductionProcess extends TransformationProcess {
    
    /**
     * Construtor of a reduction process
     * 
     * @param image Imagem
     * @param size The image size
     */
    public ReductionProcess(Image image, Double size) {
        super(image, new double[][]{{(10 - size) / 10,0,0}, 
                                {0,(10 - size)/ 10,0}, 
                                {0,0,1}});
    }
    
}
