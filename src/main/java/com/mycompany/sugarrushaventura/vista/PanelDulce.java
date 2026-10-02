package com.mycompany.sugarrushaventura.vista;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import javax.swing.JPanel;

class PanelDulce extends JPanel {

    @Override
    protected void paintComponent(Graphics grafico) {

        super.paintComponent(grafico);

        Graphics2D dibujo =
                (Graphics2D) grafico.create();

        dibujo.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Fondo degradado rosado y morado.
        GradientPaint fondo = new GradientPaint(
                0,
                0,
                new Color(84, 31, 137),
                getWidth(),
                getHeight(),
                new Color(245, 91, 173)
        );

        dibujo.setPaint(fondo);
        dibujo.fillRect(0, 0, getWidth(), getHeight());

        // Nubes de algodón de azúcar.
        for (int i = 0; i < 5; i++) {

            int x = 40 + i * Math.max(
                    150,
                    getWidth() / 5
            );

            int y = 48 + (i % 2) * 45;

            dibujo.setColor(
                    new Color(255, 226, 244, 115)
            );

            dibujo.fillOval(x, y, 76, 38);
            dibujo.fillOval(x + 27, y - 18, 62, 52);
            dibujo.fillOval(x + 62, y + 2, 68, 36);
        }

        // Montañas de chocolate.
        int base = getHeight();

        Polygon montanas = new Polygon();

        montanas.addPoint(0, base);
        montanas.addPoint(0, base - 115);
        montanas.addPoint(getWidth() / 6, base - 255);
        montanas.addPoint(getWidth() / 3, base - 120);
        montanas.addPoint(getWidth() / 2, base - 280);
        montanas.addPoint(getWidth() * 2 / 3, base - 125);
        montanas.addPoint(getWidth() * 5 / 6, base - 245);
        montanas.addPoint(getWidth(), base - 110);
        montanas.addPoint(getWidth(), base);

        dibujo.setColor(new Color(83, 38, 67, 72));
        dibujo.fillPolygon(montanas);

        // Confites de distintos colores.
        Color[] coloresConfites = {
            new Color(255, 232, 84, 150),
            new Color(83, 235, 213, 145),
            new Color(255, 255, 255, 135),
            new Color(255, 137, 185, 145)
        };

        for (int i = 0; i < 30; i++) {

            int x = (i * 137 + 37)
                    % Math.max(1, getWidth());

            int y = (i * 73 + 31)
                    % Math.max(1, getHeight());

            dibujo.setColor(
                    coloresConfites[
                            i % coloresConfites.length
                    ]
            );

            double inclinacion =
                    (i % 4) * Math.PI / 8;

            dibujo.rotate(inclinacion, x, y);

            dibujo.fillRoundRect(
                    x,
                    y,
                    14,
                    5,
                    5,
                    5
            );

            dibujo.rotate(-inclinacion, x, y);
        }

        dibujo.dispose();
    }
}