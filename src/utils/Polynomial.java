package utils;

import java.util.List;

public class Polynomial {

    private final List<Monome> polynome;

    public Polynomial(List<Monome> monomes) {
        this.polynome = monomes;
    }

    public List<Monome> getPolynome() {
        return polynome;
    }
}
