package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responstible for an enlargment process
 *  
 * @author JonataBecker
 */
public class EnlargmentProcess extends TransformationProcess {
    
    /**
     * Construtor of an enlargment process
     * 
     * @param image Imagem
     * @param size The image size
     */
    public EnlargmentProcess(Image image, Double size) {
        super(image, new double[][]{{size,0,0}, 
                                {0,size,0}, 
                                {0,0,1}});
    }
    
}
