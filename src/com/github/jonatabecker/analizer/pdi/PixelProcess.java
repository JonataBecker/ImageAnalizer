package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 *
 * @author JonataBecker
 */
public abstract class PixelProcess extends ImageProcess {

    public PixelProcess(Image image) {
        super(image);
    }

    protected void process() {
        for (int x = 0; x < getImage().getWidth(); x++) {
            for (int y = 0; y < getImage().getHeight(); y++) {
                process(getImage().getPixel(x, y));
            }
        }
    }
    
    public abstract void process(int pixel);
}
