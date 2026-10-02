package com.mycompany.sugarrushaventura.modelo;

import java.awt.Color;
import java.util.Random;

public class Ralph extends Corredor {

    public Ralph(String jugador) {

        super(
            "Ralph",
            jugador,
            new Color(194, 58, 42),
            new Color(105, 55, 35)
        );
    }

    @Override
    public int calcularAvance(Random random) {

        int avance = 2 + random.nextInt(4);

        // 14 % de probabilidad de usar el supergolpe
        if (random.nextInt(100) < 14) {
            avance = avance + 5;
        }

        return avance;
    }
}
