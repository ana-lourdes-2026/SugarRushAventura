package com.mycompany.sugarrushaventura.vista;

import com.mycompany.sugarrushaventura.excepciones.SeleccionInvalidaException;
import com.mycompany.sugarrushaventura.modelo.Corredor;
import com.mycompany.sugarrushaventura.modelo.FabricaCorredores;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaInicio extends JFrame {

    private final JComboBox<String> selectorModo =
            new JComboBox<>(
                    new String[]{
                        "Dos jugadores",
                        "Jugador contra computadora"
                    }
            );

    private final JTextField nombreJugador1 =
            new JTextField("Jugador 1", 14);

    private final JTextField nombreJugador2 =
            new JTextField("Jugador 2", 14);

    private final JComboBox<String> personajeJugador1 =
            crearSelectorPersonajes();

    private final JComboBox<String> personajeJugador2 =
            crearSelectorPersonajes();

    private final JLabel etiquetaJugador2 =
            new JLabel("Nombre del jugador 2");

    public VentanaInicio() {

        configurarVentana();
        crearContenido();
    }

    private void configurarVentana() {

        setTitle(
                "Sugar Rush - Carrera por el Reino Dulce"
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setSize(820, 610);

        setMinimumSize(
                new Dimension(760, 560)
        );

        setLocationRelativeTo(null);
    }

    private void crearContenido() {

        PanelDulce fondo = new PanelDulce();

        fondo.setLayout(new GridBagLayout());

        setContentPane(fondo);

        JPanel tarjeta = new JPanel(
                new BorderLayout(18, 18)
        );

        tarjeta.setBackground(
                new Color(255, 250, 242)
        );

        tarjeta.setBorder(
                new EmptyBorder(28, 38, 28, 38)
        );

        tarjeta.setPreferredSize(
                new Dimension(650, 475)
        );

        crearEncabezado(tarjeta);
        crearFormulario(tarjeta);
        crearBotones(tarjeta);

        fondo.add(tarjeta);
    }

    private void crearEncabezado(JPanel tarjeta) {

        JLabel titulo = new JLabel(
                "SUGAR RUSH",
                SwingConstants.CENTER
        );

        titulo.setFont(
                EstiloDulce.TITULO.deriveFont(40f)
        );

        titulo.setForeground(EstiloDulce.ROSADO);

        JLabel subtitulo = new JLabel(
                "Carrera por el Reino Dulce • Selecciona tu kart",
                SwingConstants.CENTER
        );

        subtitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        subtitulo.setForeground(EstiloDulce.MORADO);

        JPanel encabezado = new JPanel(
                new GridLayout(2, 1, 0, 4)
        );

        encabezado.setOpaque(false);

        encabezado.add(titulo);
        encabezado.add(subtitulo);

        tarjeta.add(
                encabezado,
                BorderLayout.NORTH
        );
    }

    private void crearFormulario(JPanel tarjeta) {

        JPanel formulario = new JPanel(
                new GridBagLayout()
        );

        formulario.setOpaque(false);

        GridBagConstraints posicion =
                new GridBagConstraints();

        posicion.insets =
                new Insets(7, 8, 7, 8);

        posicion.fill =
                GridBagConstraints.HORIZONTAL;

        agregarFila(
                formulario,
                posicion,
                0,
                "Modalidad",
                selectorModo
        );

        agregarFila(
                formulario,
                posicion,
                1,
                "Nombre del jugador 1",
                nombreJugador1
        );

        agregarFila(
                formulario,
                posicion,
                2,
                "Corredor del jugador 1",
                personajeJugador1
        );

        posicion.gridx = 0;
        posicion.gridy = 3;
        posicion.weightx = 0.42;

        etiquetaJugador2.setFont(EstiloDulce.TEXTO);

        formulario.add(
                etiquetaJugador2,
                posicion
        );

        posicion.gridx = 1;
        posicion.weightx = 0.58;

        formulario.add(
                nombreJugador2,
                posicion
        );

        agregarFila(
                formulario,
                posicion,
                4,
                "Corredor del jugador 2",
                personajeJugador2
        );

        tarjeta.add(
                formulario,
                BorderLayout.CENTER
        );
    }

    private void crearBotones(JPanel tarjeta) {

        JButton botonIniciar =
                EstiloDulce.boton(
                        "INICIAR CARRERA",
                        EstiloDulce.ROSADO
                );

        JButton botonManual =
                EstiloDulce.boton(
                        "MANUAL DEL PILOTO",
                        EstiloDulce.MORADO
                );

        JPanel acciones = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        14,
                        0
                )
        );

        acciones.setOpaque(false);

        acciones.add(botonManual);
        acciones.add(botonIniciar);

        tarjeta.add(
                acciones,
                BorderLayout.SOUTH
        );

        selectorModo.addActionListener(
                evento -> actualizarModo()
        );

        botonManual.addActionListener(
                evento -> mostrarManual()
        );

        botonIniciar.addActionListener(
                evento -> iniciarCarrera()
        );
    }

    private static JComboBox<String>
            crearSelectorPersonajes() {

        return new JComboBox<>(
                new String[]{
                    "Vanellope",
                    "Ralph",
                    "Felix",
                    "Rey Candy"
                }
        );
    }

    private static void agregarFila(
            JPanel panel,
            GridBagConstraints posicion,
            int fila,
            String texto,
            JComponent componente
    ) {

        posicion.gridx = 0;
        posicion.gridy = fila;
        posicion.weightx = 0.42;

        JLabel etiqueta = new JLabel(texto);

        etiqueta.setFont(EstiloDulce.TEXTO);

        panel.add(etiqueta, posicion);

        posicion.gridx = 1;
        posicion.weightx = 0.58;

        panel.add(componente, posicion);
    }

    private void actualizarModo() {

        boolean dosJugadores =
                selectorModo.getSelectedIndex() == 0;

        nombreJugador2.setEnabled(dosJugadores);

        if (dosJugadores) {

            etiquetaJugador2.setText(
                    "Nombre del jugador 2"
            );

            nombreJugador2.setText("Jugador 2");

        } else {

            etiquetaJugador2.setText("Rival");

            nombreJugador2.setText("Computadora");
        }
    }

    private void iniciarCarrera() {

        try {

            String nombre1 =
                    nombreJugador1.getText().trim();

            String nombre2 =
                    nombreJugador2.getText().trim();

            String personaje1 =
                    (String) personajeJugador1
                            .getSelectedItem();

            String personaje2 =
                    (String) personajeJugador2
                            .getSelectedItem();

            validarSeleccion(
                    nombre1,
                    nombre2,
                    personaje1,
                    personaje2
            );

            Corredor corredor1 =
                    FabricaCorredores.crear(
                            personaje1,
                            nombre1
                    );

            Corredor corredor2 =
                    FabricaCorredores.crear(
                            personaje2,
                            nombre2
                    );

            boolean contraComputadora =
                    selectorModo.getSelectedIndex() == 1;

            VentanaCarrera carrera =
                    new VentanaCarrera(
                            this,
                            corredor1,
                            corredor2,
                            contraComputadora
                    );

            carrera.setVisible(true);

            setVisible(false);

        } catch (SeleccionInvalidaException error) {

            JOptionPane.showMessageDialog(
                    this,
                    error.getMessage(),
                    "No se puede iniciar",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception error) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ocurrió un error inesperado: "
                    + error.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void validarSeleccion(
            String nombre1,
            String nombre2,
            String personaje1,
            String personaje2
    ) throws SeleccionInvalidaException {

        if (nombre1.isEmpty() || nombre2.isEmpty()) {

            throw new SeleccionInvalidaException(
                    "Escribe el nombre de los participantes."
            );
        }

        if (personaje1.equals(personaje2)) {

            throw new SeleccionInvalidaException(
                    "Cada participante debe elegir "
                    + "un corredor diferente."
            );
        }
    }

    private void mostrarManual() {

        String mensaje =
                "BIENVENIDOS A SUGAR RUSH\n\n"
                + "1. Elijan la modalidad de juego.\n"
                + "2. Escriban sus nombres.\n"
                + "3. Seleccionen corredores distintos.\n"
                + "4. Pulsen INICIAR CARRERA.\n"
                + "5. Luego pulsen ARRANCAR.\n\n"
                + "DOS JUGADORES:\n"
                + "Jugador 1: W acelera y S salta.\n"
                + "Jugador 2: ARRIBA acelera "
                + "y ABAJO salta.\n\n"
                + "CONTRA LA COMPUTADORA:\n"
                + "ARRIBA acelera y ABAJO salta.\n\n"
                + "Salten las trampas para no "
                + "retroceder 10 dulces.\n"
                + "Gana quien llegue primero "
                + "a 100 dulces.\n\n"
                + "HABILIDADES:\n"
                + "Vanellope: Glitch dulce.\n"
                + "Ralph: Supergolpe.\n"
                + "Felix: Ritmo estable.\n"
                + "Rey Candy: Turbo real.";

        JTextArea area = new JTextArea(
                mensaje,
                20,
                48
        );

        area.setEditable(false);
        area.setOpaque(false);
        area.setFont(EstiloDulce.TEXTO);

        JOptionPane.showMessageDialog(
                this,
                area,
                "Manual del piloto",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}