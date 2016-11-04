package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;

/**
 * Class responsible for the morphology processes
 *
 * @author JonataBecker
 */
public abstract class MorphologyProcess extends ImageProcess implements ProcessImage {

    /** Image */
    private final Image img;

    public MorphologyProcess(Image image) {
        super(image);
        this.img = new Image(image.getBufferdImage());
    }

    /**
     * Process the image
     */
    private void process() {
        for (int x = 1; x < getImage().getWidth() - 1; x++) {
            for (int y = 1; y < getImage().getHeight() - 1; y++) {
                processKernel(x, y);
            }
        }
    }

    /**
     * Process Kernel
     *
     * @param x
     * @param y
     */
    protected abstract void processKernel(int x, int y);

    /**
     * Returns the new image
     *
     * @return Image
     */
    protected Image getImg() {
        return img;
    }

    @Override
    public Image execute() {
        process();
        return img;
    }

    protected void executeKernel(Exec exec, int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i == 0 && j == 0) || (i == 0 && j == 2)
                        || (i == 2 && j == 0) || (i == 2 && j == 2)) {
                    continue;
                }
                exec.exec(getImage().getPixel(x + (i - 1), y + (j - 1)), i, j);
            }
        }
    }

    public interface Exec {

        public void exec(int pixel, int i, int j);
    }

}
