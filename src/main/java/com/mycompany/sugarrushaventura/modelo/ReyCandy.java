package com.mycompany.sugarrushaventura.modelo;

import java.awt.Color;
import java.util.Random;

public class ReyCandy extends Corredor {

    public ReyCandy(String jugador) {

        super(
            "Rey Candy",
            jugador,
            new Color(235, 118, 190),
            new Color(248, 224, 78)
        );
    }

    @Override
    public int calcularAvance(Random random) {

        int avance = 1 + random.nextInt(6);

        // 10 % de probabilidad de activar el turbo
        if (random.nextInt(100) < 10) {
            avance = avance + 5;
        }

        return avance;
    }
}
