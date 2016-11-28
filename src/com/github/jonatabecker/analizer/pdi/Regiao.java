package com.github.jonatabecker.analizer.pdi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JonataBecker
 */
public class Regiao {

    private final List<Item> itens;

    public Regiao() {
        this.itens = new ArrayList<>();
    }
    
    public void add(int x, int y) {
        itens.add(new Item(x, y));
    }
    
    public List<Item> getItens() {
        return itens;
    }
    
    public int getArea() {
        return itens.size();
    }
    
    public static class Item {
        
        private final int x;
        private final int y;

        public Item(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    
    }    
            
}
