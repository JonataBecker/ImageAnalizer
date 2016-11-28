package com.github.jonatabecker.analizer.pdi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JonataBecker
 */
public class CrescimentoRegiao {

    private final List<Regiao> regioes;

    public CrescimentoRegiao() {
        this.regioes = new ArrayList<>();
    }

    public void add(Regiao regiao) {
        regioes.add(regiao);
    }

    public List<Regiao> getRegioes() {
        return regioes;
    }
}
