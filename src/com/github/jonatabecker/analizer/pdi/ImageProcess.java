package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 *
 * @author JonataBecker
 */
public abstract class ImageProcess {
    
    private final Image image;

    public ImageProcess(Image image) {
        this.image = image;
    }
    
    protected Image getImage() {
        return image;
    }
}
