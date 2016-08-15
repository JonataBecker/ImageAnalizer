package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for images processes
 *
 * @author JonataBecker
 */
public abstract class ImageProcess {

    /** Image information */
    private final Image image;

    /**
     * Constructor responsible for images processes
     *
     * @param image
     */
    public ImageProcess(Image image) {
        this.image = image;
    }

    /**
     * Return the image information
     *
     * @return Image
     */
    protected Image getImage() {
        return image;
    }
}
