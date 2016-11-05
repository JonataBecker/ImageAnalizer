package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 *
 * @author JonataBecker
 */
public class FechamentoProcess  implements ProcessImage {

    private final Image image;

    public FechamentoProcess(Image image) {
        this.image = image;
    }

    @Override
    public Image execute() {
        return new ErosionProcess(new DilationProcess(image).execute()).execute();
    }
    
}
