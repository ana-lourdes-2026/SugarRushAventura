package com.mycompany.sugarrushaventura.modelo;

import java.awt.Color;
import java.util.Random;

public abstract class Corredor {

    private final String personaje;
    private final String jugador;
    private final Color colorPrincipal;
    private final Color colorSecundario;
    private int posicion;

    public Corredor(
            String personaje,
            String jugador,
            Color colorPrincipal,
            Color colorSecundario) {

        this.personaje = personaje;
        this.jugador = jugador;
        this.colorPrincipal = colorPrincipal;
        this.colorSecundario = colorSecundario;
        this.posicion = 0;
    }

    public abstract int calcularAvance(Random random);

    public void avanzar(int cantidad) {
        posicion = Math.min(
                100,
                posicion + Math.max(0, cantidad)
        );
    }

    public void retroceder(int cantidad) {
        posicion = Math.max(
                0,
                posicion - Math.max(0, cantidad)
        );
    }

    public void reiniciar() {
        posicion = 0;
    }

    public String getPersonaje() {
        return personaje;
    }

    public String getJugador() {
        return jugador;
    }

    public Color getColorPrincipal() {
        return colorPrincipal;
    }

    public Color getColorSecundario() {
        return colorSecundario;
    }

    public int getPosicion() {
        return posicion;
    }
}
