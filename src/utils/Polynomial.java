package utils;

import java.util.List;

public class Polynomial {

    private List<Monome> polynome;

    public Polynomial(List<Monome> monomes) {
        this.polynome = monomes;
    }

    public List<Monome> getPolynome() {
        return polynome;
    }
}
