package com.mycompany.sugarrushaventura.modelo;

public class Trampa {

    private final int posicion;
    private boolean superada;

    public Trampa(int posicion) {
        this.posicion = posicion;
        this.superada = false;
    }

    public boolean fueAtravesada(
            int posicionAnterior,
            int posicionActual) {

        return !superada
                && posicionAnterior < posicion
                && posicionActual >= posicion;
    }

    public void marcarSuperada() {
        superada = true;
    }

    public void reiniciar() {
        superada = false;
    }

    public int getPosicion() {
        return posicion;
    }

    public boolean isSuperada() {
        return superada;
    }
}