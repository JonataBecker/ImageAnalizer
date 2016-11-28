/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 *
 * @author JonataBecker
 */
public class CrescimentoRegiaoProcess extends PixelProcess {

    private final CrescimentoRegiao regiao;
    private final Image img;
    private int indexLabel = 2;
    private Regiao item;
    
    
    
    public CrescimentoRegiaoProcess(Image image) {
        super(image);
        regiao = new CrescimentoRegiao();
        img = new Image(image);
    }

    @Override
    public void process(int x, int y, int pixel) {
        if (img.getPixel(x, y) == 255) {
            item = new Regiao();
            compareLabel(x, y, indexLabel++);
            regiao.add(item);
        }
    }

    private void compareLabel(int x, int y, int index) {
        if (x < 0 || x >= img.getWidth() || y < 0 || y >= img.getHeight() || index == 255) {
            return;
        }
        if (img.getPixel(x, y) == 255) {
            img.setPixel(x, y, index);
            item.add(x, y);
            compareLabel(x - 1, y, index);
            compareLabel(x, y - 1, index);
            compareLabel(x, y + 1, index);
            compareLabel(x + 1, y, index);
        }
    }    
    
    
    public CrescimentoRegiao get() {
        process();
        return regiao;
    }
}
