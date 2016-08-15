package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for image process
 * 
 * @author JonataBecker
 */
public interface ProcessImage {
    
    /**
     * Execute the image process
     * 
     * @return Image
     */
    public Image execute();
    
}
