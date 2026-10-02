package com.mycompany.sugarrushaventura.principal;

import com.mycompany.sugarrushaventura.vista.VentanaInicio;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception error) {
                System.out.println(
                        "Se utilizará el estilo visual predeterminado."
                );
            }

            VentanaInicio ventana = new VentanaInicio();
            ventana.setVisible(true);
        });
    }
}
