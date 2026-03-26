package gui;

import javax.swing.*;
import config.AppConfiguration;
import engine.mobile.AnimalType;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EndGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BUTTON_COLOR      = new Color(21, 28, 117);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    private double scoreProprete;
    private double scoreDiscipline;
    private double scoreObeissance;

    private ScorePanel scorePanel;
    
    //pour recuperer la taille de l'écrant 
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = screenSize.width;
    private int screenHeight = screenSize.height;
    
    public EndGUI(double proprete, double discipline, double obeissance,
                  List<Double> histProprete,
                  List<Double> histDiscipline,
                  List<Double> histObeissance,
                  AnimalType animalType) {

        super("Fin de simulation - Bilan Final");

        this.scoreProprete   = proprete;
        this.scoreDiscipline = discipline;
        this.scoreObeissance = obeissance;

        scorePanel = new ScorePanel(proprete, discipline, obeissance,
                histProprete, histDiscipline, histObeissance, animalType);

        initLayout();
    }

    private void initLayout() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scorePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton retourButton = new JButton("Retour à l'accueil");
        retourButton.setFont(AppConfiguration.textfont);
        retourButton.setBackground(BUTTON_COLOR);
        retourButton.setForeground(BUTTON_TEXT_COLOR);
        retourButton.setFocusPainted(false);
        retourButton.setPreferredSize(new Dimension(220, 45));
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGUI();
            }
        });



        bottomPanel.add(retourButton);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setSize(screenWidth-200, screenHeight -200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
