package com.mycompany.sugarrushaventura.modelo;

import java.awt.Color;
import java.util.Random;

public class Vanellope extends Corredor {

    public Vanellope(String jugador) {

        super(
            "Vanellope",
            jugador,
            new Color(32, 214, 188),
            new Color(108, 43, 135)
        );
    }

    @Override
    public int calcularAvance(Random random) {

        int avance = 2 + random.nextInt(5);

        // 18 % de probabilidad de activar el glitch
        if (random.nextInt(100) < 18) {
            avance = avance + 3;
        }

        return avance;
    }
}
