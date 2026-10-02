package com.mycompany.sugarrushaventura.vista;

import com.mycompany.sugarrushaventura.modelo.Corredor;
import com.mycompany.sugarrushaventura.modelo.Trampa;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

class PistaPanel extends JPanel {

    private final Corredor[] corredores;
    private final Trampa[][] trampas;
    private final boolean[] saltando;

    private Corredor ganador;

    public PistaPanel(
            Corredor[] corredores,
            Trampa[][] trampas,
            boolean[] saltando
    ) {
        this.corredores = corredores;
        this.trampas = trampas;
        this.saltando = saltando;

        setOpaque(false);
        setPreferredSize(new Dimension(880, 390));
    }

    public void setGanador(Corredor ganador) {
        this.ganador = ganador;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics grafico) {

        super.paintComponent(grafico);

        Graphics2D dibujo =
                (Graphics2D) grafico.create();

        dibujo.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        dibujarDecoracion(dibujo);

        int margen = 48;
        int anchoPista = getWidth() - margen * 2;

        for (int i = 0; i < corredores.length; i++) {

            int y = 58 + i * 150;

            // Borde de glaseado.
            if (i == 0) {
                dibujo.setColor(new Color(255, 166, 207));
            } else {
                dibujo.setColor(new Color(93, 225, 207));
            }

            dibujo.fill(
                    new RoundRectangle2D.Double(
                            margen - 7,
                            y - 7,
                            anchoPista + 14,
                            103,
                            32,
                            32
                    )
            );

            // Camino de chocolate.
            dibujo.setColor(new Color(87, 49, 73));

            dibujo.fill(
                    new RoundRectangle2D.Double(
                            margen,
                            y,
                            anchoPista,
                            89,
                            27,
                            27
                    )
            );

            dibujo.setColor(new Color(121, 72, 96));

            dibujo.fill(
                    new RoundRectangle2D.Double(
                            margen + 7,
                            y + 8,
                            anchoPista - 14,
                            72,
                            22,
                            22
                    )
            );

            // Línea central hecha de caramelos.
            Color[] caramelos = {
                new Color(255, 232, 76),
                new Color(255, 112, 171),
                new Color(63, 224, 205),
                Color.WHITE
            };

            int numeroCaramelo = 0;

            for (int x = margen + 24;
                    x < margen + anchoPista - 48;
                    x += 44) {

                dibujo.setColor(
                        caramelos[
                                numeroCaramelo % caramelos.length
                        ]
                );

                dibujo.fillRoundRect(
                        x,
                        y + 43,
                        25,
                        7,
                        7,
                        7
                );

                numeroCaramelo++;
            }

            // Meta de cuadros blancos y negros.
            dibujarMeta(
                    dibujo,
                    margen + anchoPista - 39,
                    y + 7,
                    27,
                    75
            );

            Corredor corredor = corredores[i];

            int recorrido = anchoPista - 128;

            // Dibujar las trampas.
            for (Trampa trampa : trampas[i]) {

                if (!trampa.isSuperada()) {

                    int xTrampa = margen
                            + 10
                            + recorrido * trampa.getPosicion() / 100
                            + 26;

                    dibujarTrampa(
                            dibujo,
                            xTrampa,
                            y + 48
                    );
                }
            }

            // Calcular la posición del carrito.
            int xCarrito = margen
                    + 10
                    + recorrido * corredor.getPosicion() / 100;

            int alturaSalto;

            if (saltando[i]) {
                alturaSalto = 27;
            } else {
                alturaSalto = 0;
            }

            dibujarCarrito(
                    dibujo,
                    xCarrito,
                    y + 20 - alturaSalto,
                    corredor
            );

            // Nombre del jugador y personaje.
            dibujo.setFont(
                    new Font("SansSerif", Font.BOLD, 15)
            );

            dibujo.setColor(Color.WHITE);

            String nombre = corredor.getJugador()
                    + " • "
                    + corredor.getPersonaje();

            dibujo.drawString(
                    nombre,
                    margen,
                    y - 13
            );

            String progreso =
                    corredor.getPosicion() + " / 100 dulces";

            int anchoTexto =
                    dibujo.getFontMetrics().stringWidth(progreso);

            dibujo.drawString(
                    progreso,
                    margen + anchoPista - anchoTexto,
                    y - 13
            );
        }

        // Si existe ganador, mostrar confeti.
        if (ganador != null) {
            dibujarConfeti(dibujo);
        }

        dibujo.dispose();
    }

    private void dibujarCarrito(
            Graphics2D dibujo,
            int x,
            int y,
            Corredor corredor
    ) {

        // Sombra.
        dibujo.setColor(new Color(35, 15, 30, 65));
        dibujo.fillOval(x + 2, y + 48, 82, 16);

        // Parachoques.
        dibujo.setColor(corredor.getColorSecundario());

        dibujo.fillRoundRect(
                x - 4,
                y + 31,
                88,
                18,
                12,
                12
        );

        // Cuerpo del carrito.
        dibujo.setColor(corredor.getColorPrincipal());

        dibujo.fillRoundRect(
                x,
                y + 14,
                78,
                38,
                18,
                18
        );

        // Asiento.
        dibujo.setColor(corredor.getColorSecundario());

        dibujo.fillRoundRect(
                x + 19,
                y + 2,
                39,
                28,
                14,
                14
        );

        // Rostro del personaje.
        Color piel;

        if (corredor.getPersonaje().equals("Ralph")) {
            piel = new Color(222, 142, 95);
        } else {
            piel = new Color(247, 198, 158);
        }

        dibujo.setColor(piel);
        dibujo.fillOval(x + 29, y - 10, 22, 23);

        // Cabello.
        if (corredor.getPersonaje().equals("Vanellope")) {
            dibujo.setColor(new Color(45, 28, 46));
        } else {
            dibujo.setColor(new Color(91, 49, 33));
        }

        dibujo.fillArc(
                x + 27,
                y - 13,
                26,
                20,
                0,
                180
        );

        // Confites en el cabello de Vanellope.
        if (corredor.getPersonaje().equals("Vanellope")) {

            dibujo.setColor(new Color(255, 80, 142));
            dibujo.fillOval(x + 47, y - 10, 7, 7);

            dibujo.setColor(new Color(69, 223, 195));
            dibujo.fillOval(x + 25, y - 9, 6, 6);
        }

        // Luces de dulce.
        dibujo.setColor(new Color(255, 244, 133));
        dibujo.fillOval(x + 7, y + 23, 8, 8);
        dibujo.fillOval(x + 64, y + 23, 8, 8);

        // Llantas.
        dibujo.setColor(new Color(30, 30, 36));
        dibujo.fillOval(x + 8, y + 43, 20, 20);
        dibujo.fillOval(x + 53, y + 43, 20, 20);

        // Centro de las llantas.
        dibujo.setColor(Color.WHITE);
        dibujo.fillOval(x + 14, y + 49, 8, 8);
        dibujo.fillOval(x + 59, y + 49, 8, 8);
    }

    private void dibujarMeta(
            Graphics2D dibujo,
            int x,
            int y,
            int ancho,
            int alto
    ) {

        int medidaCuadro = 8;

        for (int fila = 0;
                fila < alto / medidaCuadro;
                fila++) {

            for (int columna = 0;
                    columna < ancho / medidaCuadro;
                    columna++) {

                if ((fila + columna) % 2 == 0) {
                    dibujo.setColor(Color.WHITE);
                } else {
                    dibujo.setColor(Color.BLACK);
                }

                dibujo.fillRect(
                        x + columna * medidaCuadro,
                        y + fila * medidaCuadro,
                        medidaCuadro,
                        medidaCuadro
                );
            }
        }
    }

    private void dibujarTrampa(
            Graphics2D dibujo,
            int x,
            int y
    ) {

        // Sombra del obstáculo.
        dibujo.setColor(new Color(74, 35, 48));
        dibujo.fillOval(x - 4, y + 13, 34, 10);

        // Caramelo puntiagudo.
        Polygon carameloDuro = new Polygon();

        carameloDuro.addPoint(x, y + 17);
        carameloDuro.addPoint(x + 7, y - 2);
        carameloDuro.addPoint(x + 14, y + 17);
        carameloDuro.addPoint(x + 21, y - 7);
        carameloDuro.addPoint(x + 28, y + 17);

        dibujo.setColor(new Color(246, 71, 132));
        dibujo.fillPolygon(carameloDuro);

        // Puntos amarillos.
        dibujo.setColor(new Color(255, 235, 91));
        dibujo.fillOval(x + 5, y + 5, 5, 5);
        dibujo.fillOval(x + 20, y + 2, 5, 5);
    }

    private void dibujarDecoracion(Graphics2D dibujo) {

        // Suelo de galleta.
        int suelo = Math.max(
                330,
                getHeight() - 42
        );

        dibujo.setColor(new Color(230, 161, 113, 125));

        dibujo.fillRoundRect(
                12,
                suelo,
                getWidth() - 24,
                34,
                26,
                26
        );

        // Chispas de chocolate.
        dibujo.setColor(new Color(103, 53, 74, 120));

        for (int x = 28; x < getWidth() - 25; x += 62) {
            dibujo.fillOval(x, suelo + 10, 8, 8);
        }

        // Paletas gigantes.
        dibujarPaleta(
                dibujo,
                18,
                8,
                new Color(255, 99, 170)
        );

        dibujarPaleta(
                dibujo,
                getWidth() - 54,
                10,
                new Color(68, 225, 202)
        );
    }

    private void dibujarPaleta(
            Graphics2D dibujo,
            int x,
            int y,
            Color color
    ) {

        // Palito de la paleta.
        dibujo.setColor(new Color(245, 218, 175));

        dibujo.fillRoundRect(
                x + 17,
                y + 35,
                7,
                49,
                6,
                6
        );

        // Dulce de la paleta.
        dibujo.setColor(color);
        dibujo.fillOval(x, y, 42, 42);

        dibujo.setColor(
                new Color(255, 255, 255, 150)
        );

        dibujo.setStroke(new BasicStroke(4));

        dibujo.drawArc(
                x + 7,
                y + 7,
                28,
                28,
                30,
                240
        );
    }

    private void dibujarConfeti(Graphics2D dibujo) {

        Color[] colores = {
            Color.YELLOW,
            new Color(255, 92, 169),
            new Color(51, 224, 199),
            Color.WHITE
        };

        for (int i = 0; i < 45; i++) {

            int x = (i * 83 + 19)
                    % Math.max(1, getWidth());

            int y = (i * 47 + 7)
                    % Math.max(1, getHeight());

            dibujo.setColor(
                    colores[i % colores.length]
            );

            dibujo.fillOval(
                    x,
                    y,
                    7 + i % 5,
                    7 + i % 5
            );
        }
    }
}
