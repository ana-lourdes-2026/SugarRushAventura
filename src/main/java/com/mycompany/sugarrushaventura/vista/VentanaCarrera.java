package com.mycompany.sugarrushaventura.vista;

import com.mycompany.sugarrushaventura.modelo.Corredor;
import com.mycompany.sugarrushaventura.modelo.Trampa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.InputMap;
import javax.swing.border.EmptyBorder;

public class VentanaCarrera extends JFrame {

    private final JFrame ventanaInicio;
    private final Corredor[] corredores;
    private final Trampa[][] trampas;

    private final boolean[] saltando = new boolean[2];

    private final PistaPanel pista;
    private final Timer temporizador;
    private final boolean contraComputadora;
    private final Random random = new Random();

    private final JLabel estado = new JLabel(
            "Preparados para conquistar el Reino Dulce",
            SwingConstants.CENTER
    );

    private final JButton botonArrancar =
            EstiloDulce.boton(
                    "ARRANCAR",
                    EstiloDulce.ROSADO
            );

    private boolean carreraActiva;

    public VentanaCarrera(
            JFrame ventanaInicio,
            Corredor corredor1,
            Corredor corredor2,
            boolean contraComputadora
    ) {

        this.ventanaInicio = ventanaInicio;
        this.contraComputadora = contraComputadora;

        // Polimorfismo: el arreglo almacena distintos personajes.
        this.corredores = new Corredor[]{
            corredor1,
            corredor2
        };

        // Cada corredor tiene tres trampas.
        this.trampas = new Trampa[][]{
            {
                new Trampa(28),
                new Trampa(53),
                new Trampa(78)
            },
            {
                new Trampa(34),
                new Trampa(61),
                new Trampa(84)
            }
        };

        pista = new PistaPanel(
                corredores,
                trampas,
                saltando
        );

        // Mueve automáticamente a la computadora.
        temporizador = new Timer(
                420,
                evento -> moverComputadora()
        );

        configurarVentana();
        crearContenido();
        configurarControles();
    }

    private void configurarVentana() {

        setTitle("Sugar Rush - Pista del Reino Dulce");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(980, 620);

        setMinimumSize(
                new Dimension(820, 540)
        );

        setLocationRelativeTo(null);
    }

    private void crearContenido() {

        PanelDulce fondo = new PanelDulce();

        fondo.setLayout(
                new BorderLayout(20, 15)
        );

        fondo.setBorder(
                new EmptyBorder(22, 28, 25, 28)
        );

        setContentPane(fondo);

        // Título de la carrera.
        JLabel titulo = new JLabel(
                "GRAN PREMIO SUGAR RUSH",
                SwingConstants.CENTER
        );

        titulo.setFont(
                EstiloDulce.TITULO.deriveFont(31f)
        );

        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel(
                "La carrera más dulce del arcade",
                SwingConstants.CENTER
        );

        subtitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        subtitulo.setForeground(
                new Color(255, 238, 132)
        );

        JPanel encabezado = new JPanel(
                new GridLayout(2, 1, 0, 2)
        );

        encabezado.setOpaque(false);
        encabezado.add(titulo);
        encabezado.add(subtitulo);

        fondo.add(encabezado, BorderLayout.NORTH);
        fondo.add(pista, BorderLayout.CENTER);

        crearPanelInferior(fondo);
    }

    private void crearPanelInferior(PanelDulce fondo) {

        JPanel panelInferior = new JPanel(
                new BorderLayout(12, 12)
        );

        panelInferior.setOpaque(false);

        estado.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        estado.setForeground(Color.WHITE);

        panelInferior.add(
                estado,
                BorderLayout.NORTH
        );

        JButton botonAyuda =
                EstiloDulce.boton(
                        "AYUDA",
                        EstiloDulce.MORADO
                );

        JButton botonVolver =
                EstiloDulce.boton(
                        "VOLVER AL MENÚ",
                        new Color(53, 126, 145)
                );

        JPanel botones = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        12,
                        0
                )
        );

        botones.setOpaque(false);

        botones.add(botonAyuda);
        botones.add(botonArrancar);
        botones.add(botonVolver);

        panelInferior.add(
                botones,
                BorderLayout.SOUTH
        );

        fondo.add(
                panelInferior,
                BorderLayout.SOUTH
        );

        botonArrancar.addActionListener(
                evento -> comenzarCarrera()
        );

        botonAyuda.addActionListener(
                evento -> mostrarAyuda()
        );

        botonVolver.addActionListener(
                evento -> volverAlMenu()
        );
    }

    private void mostrarAyuda() {

        JOptionPane.showMessageDialog(
                this,
                controlesDelModo()
                + "\n\nPresiona repetidamente la tecla para avanzar."
                + "\nSalta antes de tocar una trampa."
                + "\nSi chocas, retrocederás 10 dulces."
                + "\nGana quien llegue primero a 100 dulces.",
                "Consejo de Vanellope",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void comenzarCarrera() {

        for (Corredor corredor : corredores) {
            corredor.reiniciar();
        }

        for (Trampa[] carril : trampas) {

            for (Trampa trampa : carril) {
                trampa.reiniciar();
            }
        }

        saltando[0] = false;
        saltando[1] = false;

        pista.setGanador(null);

        carreraActiva = true;

        botonArrancar.setEnabled(false);

        if (contraComputadora) {

            estado.setText(
                    "ARRIBA: acelerar • ABAJO: saltar"
            );

            temporizador.start();

        } else {

            estado.setText(
                    "J1: W acelera / S salta • "
                    + "J2: ARRIBA acelera / ABAJO salta"
            );
        }

        pista.repaint();
        requestFocusInWindow();
    }

    private void configurarControles() {

        JRootPane raiz = getRootPane();

        InputMap entradas = raiz.getInputMap(
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        ActionMap acciones = raiz.getActionMap();

        // Jugador 1: tecla W para avanzar.
        entradas.put(
                KeyStroke.getKeyStroke("pressed W"),
                "acelerarJugador1"
        );

        acciones.put(
                "acelerarJugador1",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent evento
                    ) {
                        if (!contraComputadora) {
                            moverJugador(0);
                        }
                    }
                }
        );

        // Jugador 1: tecla S para saltar.
        entradas.put(
                KeyStroke.getKeyStroke("pressed S"),
                "saltarJugador1"
        );

        acciones.put(
                "saltarJugador1",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent evento
                    ) {
                        if (!contraComputadora) {
                            saltar(0);
                        }
                    }
                }
        );

        // Flecha arriba.
        entradas.put(
                KeyStroke.getKeyStroke("pressed UP"),
                "acelerarFlecha"
        );

        acciones.put(
                "acelerarFlecha",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent evento
                    ) {

                        if (contraComputadora) {
                            moverJugador(0);
                        } else {
                            moverJugador(1);
                        }
                    }
                }
        );

        // Flecha abajo.
        entradas.put(
                KeyStroke.getKeyStroke("pressed DOWN"),
                "saltarFlecha"
        );

        acciones.put(
                "saltarFlecha",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent evento
                    ) {

                        if (contraComputadora) {
                            saltar(0);
                        } else {
                            saltar(1);
                        }
                    }
                }
        );
    }

    private void moverJugador(int indice) {

        if (!carreraActiva) {
            return;
        }

        Corredor corredor = corredores[indice];

        int posicionAnterior =
                corredor.getPosicion();

        int avance =
                corredor.calcularAvance(random);

        corredor.avanzar(avance);

        revisarTrampas(
                indice,
                posicionAnterior,
                corredor.getPosicion()
        );

        pista.repaint();

        comprobarGanador(corredor);
    }

    private void moverComputadora() {

        if (!carreraActiva || !contraComputadora) {
            return;
        }

        Corredor computadora = corredores[1];

        int posicionAnterior =
                computadora.getPosicion();

        // La computadora intenta saltar las trampas.
        if (hayTrampaCerca(
                1,
                computadora.getPosicion(),
                9
        ) && random.nextInt(100) < 78) {

            saltar(1);
        }

        int avance = Math.max(
                2,
                computadora.calcularAvance(random) - 1
        );

        computadora.avanzar(avance);

        revisarTrampas(
                1,
                posicionAnterior,
                computadora.getPosicion()
        );

        pista.repaint();

        comprobarGanador(computadora);
    }

    private void saltar(int indice) {

        if (!carreraActiva || saltando[indice]) {
            return;
        }

        saltando[indice] = true;
        pista.repaint();

        Timer finalizarSalto = new Timer(
                650,
                evento -> {

                    saltando[indice] = false;
                    pista.repaint();
                }
        );

        finalizarSalto.setRepeats(false);
        finalizarSalto.start();
    }

    private void revisarTrampas(
            int indice,
            int posicionAnterior,
            int posicionActual
    ) {

        for (Trampa trampa : trampas[indice]) {

            if (trampa.fueAtravesada(
                    posicionAnterior,
                    posicionActual
            )) {

                trampa.marcarSuperada();

                if (saltando[indice]) {

                    estado.setText(
                            corredores[indice].getJugador()
                            + " saltó la trampa"
                    );

                } else {

                    corredores[indice].retroceder(10);

                    estado.setText(
                            corredores[indice].getJugador()
                            + " chocó y retrocedió 10 dulces"
                    );
                }
            }
        }
    }

    private boolean hayTrampaCerca(
            int indice,
            int posicion,
            int distancia
    ) {

        for (Trampa trampa : trampas[indice]) {

            boolean estaAdelante =
                    trampa.getPosicion() >= posicion;

            boolean estaCerca =
                    trampa.getPosicion() - posicion
                    <= distancia;

            if (!trampa.isSuperada()
                    && estaAdelante
                    && estaCerca) {

                return true;
            }
        }

        return false;
    }

    private void comprobarGanador(Corredor corredor) {

        if (corredor.getPosicion() >= 100) {
            finalizarCarrera(corredor);
        }
    }

    private void finalizarCarrera(Corredor ganador) {

        carreraActiva = false;
        temporizador.stop();

        pista.setGanador(ganador);

        estado.setText(
                "CAMPEÓN DEL REINO DULCE: "
                + ganador.getJugador().toUpperCase()
                + " CON "
                + ganador.getPersonaje().toUpperCase()
        );

        botonArrancar.setText("CORRER DE NUEVO");
        botonArrancar.setEnabled(true);

        JOptionPane.showMessageDialog(
                this,
                "¡Dulce victoria!\n\n"
                + ganador.getJugador()
                + " ganó con "
                + ganador.getPersonaje()
                + ".\nEl Reino Dulce tiene un nuevo campeón.",
                "Resultado final",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String controlesDelModo() {

        if (contraComputadora) {

            return "TÚ: ARRIBA acelera y ABAJO salta"
                    + "\nRIVAL: computadora";

        } else {

            return "JUGADOR 1: W acelera y S salta"
                    + "\nJUGADOR 2: ARRIBA acelera "
                    + "y ABAJO salta";
        }
    }

    private void volverAlMenu() {

        carreraActiva = false;
        temporizador.stop();

        dispose();
        ventanaInicio.setVisible(true);
    }

    @Override
    public void dispose() {

        temporizador.stop();
        super.dispose();

        if (!ventanaInicio.isVisible()) {
            ventanaInicio.setVisible(true);
        }
    }
}