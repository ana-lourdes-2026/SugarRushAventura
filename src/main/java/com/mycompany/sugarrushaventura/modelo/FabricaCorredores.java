package com.mycompany.sugarrushaventura.modelo;

import com.mycompany.sugarrushaventura.excepciones.SeleccionInvalidaException;

public final class FabricaCorredores {

    private FabricaCorredores() {
    }

    public static Corredor crear(String personaje, String jugador)
            throws SeleccionInvalidaException {

        return switch (personaje) {
            case "Vanellope" -> new Vanellope(jugador);
            case "Ralph" -> new Ralph(jugador);
            case "Felix" -> new Felix(jugador);
            case "Rey Candy" -> new ReyCandy(jugador);

            default -> throw new SeleccionInvalidaException(
                    "El personaje seleccionado no existe."
            );
        };
    }
}
