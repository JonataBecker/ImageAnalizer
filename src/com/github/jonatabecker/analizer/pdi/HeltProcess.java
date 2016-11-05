package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the helt process
 *
 * @author JonataBecker
 */
public class HeltProcess implements ProcessImage {

    private Image image;
    private final Image img;

    public HeltProcess(Image image) {
        this.image = image;
        this.img = new Image(image);
    }

    public int calcula(int[][] pixels, boolean first) {
        int p2 = pixels[1][0] / 255;
        int p3 = pixels[2][0] / 255;
        int p4 = pixels[2][1] / 255;
        int p5 = pixels[2][2] / 255;
        int p6 = pixels[1][2] / 255;
        int p7 = pixels[0][2] / 255;
        int p8 = pixels[0][1] / 255;
        int p9 = pixels[0][0] / 255;
        int np = p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
        int sp = (p2 < p3 ? 1 : 0)
                + (p3 < p4 ? 1 : 0)
                + (p4 < p5 ? 1 : 0)
                + (p5 < p6 ? 1 : 0)
                + (p6 < p7 ? 1 : 0)
                + (p7 < p8 ? 1 : 0)
                + (p8 < p9 ? 1 : 0)
                + (p9 < p2 ? 1 : 0);

        if (
                (np >= 2 || np <= 6) && sp == 1 && 
                (
                    ((p2 + p4 + p6 >= 1) && (p4 + p6 + p8 >= 1))
//                    ((p2 + p4 + p6 >= 1) && (p4 + p6 + p8 >= 1)) &&
//                    ((p8 + p2 + p4 >= 1) && (p2 + p8 + p6 >= 1))
                
                )
                ){
            return 0;
        }
        return pixels[1][1];
    }

    public void process() {
        boolean mudou = true;
        while (mudou) {
            mudou = false;
            for (int z = 0; z < 2; z++) {

                for (int x = 1; x < image.getWidth() - 1; x++) {
                    for (int y = 1; y < image.getHeight() - 1; y++) {
//                        if (image.getPixel(x, y) == 255) {
                            int[][] pixels = new int[3][3];
                            for (int x2 = 0; x2 < 3; x2++) {
                                for (int y2 = 0; y2 < 3; y2++) {
                                    pixels[x2][y2] = image.getPixel(x + x2 - 1, y + y2 - 1);
                                }
                            }
                            int v = Math.max(Math.min(calcula(pixels, z == 0), 255), 0);
                            if (v != image.getPixel(x, y)) {
                                mudou = true;
                            }
                            img.setPixel(x, y, v);
//                        }
                    }
                }
                image = img;
            }
        }
    }

    @Override
    public Image execute() {
        process();
        return img;
    }

}
