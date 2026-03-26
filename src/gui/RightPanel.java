package gui;

import javax.swing.*;

import config.AppConfiguration;

import java.awt.*;

public class RightPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar barProprete;
    private JProgressBar barDiscipline;
    private JProgressBar barObeissance;
    private JProgressBar barAction;
    private JLabel labelActions;
    private JTextArea logArea;
    
    
    //COULEUR 
    private static final Color BACKGROUND_COLOR = new Color(160, 164, 222);
    private static final Color BAR_COLOR = new Color(37, 52, 181);
    private static final Color TEXT_COLOR = Color.BLACK;


    public RightPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(BACKGROUND_COLOR);
        init();
    }

    private void init() {

        labelActions = new JLabel("Actions : 0");
        labelActions.setFont(AppConfiguration.textfont);  
        labelActions.setForeground(TEXT_COLOR);
        labelActions.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(labelActions);
        add(Box.createRigidArea(new Dimension(0, 20)));

        barProprete = createCompetencePanel("Propreté");
        barDiscipline = createCompetencePanel("Discipline");
        barObeissance = createCompetencePanel("Obéissance");
        barAction = createCompetencePanel("Bonnes Actions");

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel labelLog = new JLabel("Journal :");
        labelLog.setFont(AppConfiguration.textfont);
        labelLog.setForeground(TEXT_COLOR);
        labelLog.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(labelLog);

        add(Box.createRigidArea(new Dimension(0, 5)));

        logArea = new JTextArea(7, 16);
        logArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBackground(new Color(200, 205, 240));
        logArea.setForeground(TEXT_COLOR);


        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setMaximumSize(new Dimension(320, 280));
        scroll.setPreferredSize(new Dimension(280, 260));
        add(scroll);
    }

    private JProgressBar createCompetencePanel(String nom) {

        JLabel label = new JLabel(nom);
        label.setFont(AppConfiguration.textfont);  
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setFont(AppConfiguration.textfont);  
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(300, 35));
        progressBar.setMaximumSize(new Dimension(300, 35));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        progressBar.setForeground(BAR_COLOR);   
        progressBar.setBackground(Color.WHITE);

        add(label);
        add(progressBar);
        
        
       
        add(Box.createRigidArea(new Dimension(0, 15)));

        return progressBar;
    }

    //  Méthode pour mettre à jour les données
    public void updateData(double bonnesactions,
    					   int actions,
                           double pr,
                           double ds,
                           double ob// double act
                            ) {

        labelActions.setText("Actions: "  + actions);

        barProprete.setValue((int) pr);
        barDiscipline.setValue((int) ds);
        barObeissance.setValue((int) ob);
        barAction.setValue((int) bonnesactions);
    }
    
    public void addMessage(String message) {
        logArea.append(message + "\n---\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public void clearLog() {
        logArea.setText("");
    }
    
}