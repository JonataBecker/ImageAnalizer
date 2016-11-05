package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 *
 * @author JonataBecker
 */
public class AberturaProcess implements ProcessImage {

    private final Image image;

    public AberturaProcess(Image image) {
        this.image = image;
    }

    @Override
    public Image execute() {
        return new DilationProcess(new ErosionProcess(image).execute()).execute();
    }

}
