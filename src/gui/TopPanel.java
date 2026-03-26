package gui;

import javax.swing.*;

import config.AppConfiguration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopPanel extends JPanel {
	

    private static final long serialVersionUID = 1L;

    private MainGUI mainGUI;
    private JButton btnPause;
    
    
    // VARIABLES DE COULEUR
    private static final Color BACKGROUND_COLOR = new Color(230, 230, 255);
    private static final Color BUTTON_COLOR = new Color(21, 28, 117);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    

    public TopPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;

        setLayout(new FlowLayout());
        setBackground(BACKGROUND_COLOR); // Couleur du fond

        // Bouton Pause/Resume
        btnPause = new JButton("Start");
        styleButton(btnPause);
        btnPause.addActionListener(new PauseButtonListener());

        // Bouton Reset
        JButton btnReset = new JButton("Reset");
        styleButton(btnReset);
        btnReset.addActionListener(new ResetButtonListener());

        add(btnPause);
        add(btnReset);
    }
    
    
    // Méthode pour appliquer le style aux boutons
    private void styleButton(JButton button) {
    	button.setFont(AppConfiguration.textfont); 
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
    }
    

    // RESET
    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainGUI.resetGame();
            btnPause.setText("Start");
        }
    }

    // PAUSE
    private class PauseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!mainGUI.isRunning()) {
                btnPause.setText("Pause");
                mainGUI.startThread();
            } else {
                btnPause.setText("Start");
                mainGUI.stopThread();
            }
        }
    }
}