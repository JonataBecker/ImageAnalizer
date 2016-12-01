/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jonatabecker.analizer.pdi;

import com.github.jonatabecker.analizer.commons.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JonataBecker
 */
public class CaracteristicaProcess implements ProcessImage {

    private final Image image;
    private int color;
    private final List<Dado> dados;

    public CaracteristicaProcess(Image image) {
        this.image = image;
        this.dados = new ArrayList<>();
    }

    private String processItem(Regiao regiao, int color) {
        regiao.getItens().forEach((item) -> {
            image.setPixel(item.getX(), item.getY(), color);
        });
        int perimetro = 0;
        int rxmin = image.getWidth();
        int rxmax = 0;
        int rymin = image.getHeight();
        int rymax = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getPixel(x, y) == color) {
                    if (rxmin > x) {
                        rxmin = x;
                    }
                    if (rymin > y) {
                        rymin = y;
                    }
                    if (x > rxmax) {
                        rxmax = x;
                    }
                    if (y > rymax) {
                        rymax = y;
                    }

                    if (image.getPixel(x, y - 1) != color || image.getPixel(x, y + 1) != color
                            || image.getPixel(x - 1, y) != color || image.getPixel(x + 1, y) != color) {
                        perimetro++;
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        double retangularidade = regiao.getArea() / ((rymax - rymin + 1) * (rxmax - rxmin + 1));
        double circularidade = Math.pow(perimetro, 2) / (4 * Math.PI * regiao.getArea());
        if (retangularidade == 1) {
            sb.append("Quadrado ").append("\n");
        } else {
            sb.append("Circunferência\n ");
        }
        sb.append("Circularidade: ").append(circularidade).append("\n");
        sb.append("Retangularidade: ").append(retangularidade).append("\n");
        sb.append("Área: ").append(regiao.getArea()).append("\n");
        sb.append("Perimetro: ").append(perimetro).append("\n\n\n");
        return sb.toString();
    }

    private void process() {
        CrescimentoRegiao regiao = new CrescimentoRegiaoProcess(image).get();
        color = 0;
        regiao.getRegioes().forEach((item) -> {
            color += 50;
            dados.add(new Dado(processItem(item, color), color));
        });
    }

    @Override
    public Image execute() {
        process();
        return image;
    }

    public List<Dado> getDado() {
        return dados;
    }

    public static class Dado {

        private final String dado;
        private final int cor;

        public Dado(String dado, int cor) {
            this.dado = dado;
            this.cor = cor;
        }

        public String getDado() {
            return dado;
        }

        public int getCor() {
            return cor;
        }

    }

}
