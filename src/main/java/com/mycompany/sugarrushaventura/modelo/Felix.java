package com.mycompany.sugarrushaventura.modelo;

import java.awt.Color;
import java.util.Random;

public class Felix extends Corredor {

    public Felix(String jugador) {

        super(
            "Felix",
            jugador,
            new Color(40, 115, 220),
            new Color(241, 196, 54)
        );
    }

    @Override
    public int calcularAvance(Random random) {

        // Félix mantiene un avance estable
        return 3 + random.nextInt(4);
    }
}
