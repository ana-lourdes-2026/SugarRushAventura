package com.mycompany.sugarrushaventura.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

final class EstiloDulce {

    static final Color MORADO = new Color(88, 42, 125);
    static final Color ROSADO = new Color(236, 78, 151);
    static final Color TURQUESA = new Color(38, 196, 190);
    static final Color CREMA = new Color(255, 247, 232);

    static final Font TITULO =
            new Font("SansSerif", Font.BOLD, 30);

    static final Font TEXTO =
            new Font("SansSerif", Font.PLAIN, 14);

    private EstiloDulce() {
    }

    static JButton boton(String texto, Color color) {
        return new BotonDulce(texto, color);
    }

    private static class BotonDulce extends JButton {

        private final Color colorNormal;
        private Color colorActual;

        public BotonDulce(String texto, Color color) {
            super(texto);

            colorNormal = color;
            colorActual = color;

            setFont(new Font("SansSerif", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);

            setBorder(new EmptyBorder(12, 22, 12, 22));

            setCursor(
                    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            );

            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent evento) {
                    colorActual = colorNormal.brighter();
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent evento) {
                    colorActual = colorNormal;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics grafico) {

            Graphics2D dibujo =
                    (Graphics2D) grafico.create();

            dibujo.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if (isEnabled()) {
                dibujo.setColor(colorActual);
            } else {
                dibujo.setColor(new Color(150, 135, 155));
            }

            dibujo.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    26,
                    26
            );

            dibujo.setColor(
                    new Color(
                            255,
                            255,
                            255,
                            isEnabled() ? 105 : 45
                    )
            );

            dibujo.setStroke(new BasicStroke(2));

            dibujo.drawRoundRect(
                    1,
                    1,
                    getWidth() - 3,
                    getHeight() - 3,
                    26,
                    26
            );

            dibujo.dispose();

            super.paintComponent(grafico);
        }
    }
}
