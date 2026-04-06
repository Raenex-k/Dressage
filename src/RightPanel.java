package gui;

import javax.swing.*;

import config.AppConfiguration;

import java.awt.*;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JProgressBar barProprete;
	private JProgressBar barDiscipline;
	private JProgressBar barObeissance;
	private JProgressBar barAction;
	private JLabel labelActions;
	private JTextArea logArea;
	
	//  NOUVELLES VARIABLES POUR LES FORMULES DE CALCUL 
	private JLabel labelNombreActions;
	private JLabel labelCalculBonnesActions;
	private JPanel panelBonnesActionsEvolution;
	private JLabel labelCalculCompetences;
	private JProgressBar barFatigue;
	private JProgressBar barMemoire;
	private JPanel panelProprete;
	private JPanel panelDiscipline;
	private JPanel panelObeissance;

	//  COULEURS 
	private static final Color BACKGROUND_COLOR = new Color(160, 164, 222);
	private static final Color BAR_COLOR = new Color(37, 52, 181);
	private static final Color TEXT_COLOR = Color.BLACK;

	//  NOUVELLES COULEURS 
	private static final Color COLOR_FATIGUE = new Color(255, 140, 0);
	private static final Color COLOR_MEMOIRE = new Color(100, 150, 220);
	private static final Color COLOR_GAIN = new Color(80, 200, 100);
	private static final Color GREEN = new Color(80, 200, 100);
	private static final Color RED = new Color(220, 100, 100);
	private static final Color MAUVE_FONCE = new Color(120, 100, 180);

	public RightPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBackground(BACKGROUND_COLOR);
		init();
	}

	private void init() {

		//  NOMBRE ACTIONS 
		labelNombreActions = new JLabel("Nombre Actions: 0");
		labelNombreActions.setFont(AppConfiguration.textfont);
		labelNombreActions.setForeground(TEXT_COLOR);
		labelNombreActions.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelNombreActions);
		add(Box.createRigidArea(new Dimension(0, 15)));

		//  BONNES ACTIONS 
		JLabel labelBonnesActionsTitle = new JLabel("Bonnes Actions");
		labelBonnesActionsTitle.setFont(AppConfiguration.textfont);
		labelBonnesActionsTitle.setForeground(TEXT_COLOR);
		labelBonnesActionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelBonnesActionsTitle);

		// Jauge 1 : Formule de calcul (JLabel simple)
		labelCalculBonnesActions = new JLabel("Log(1+Mém)×coeff(deg) - (Fat×0.08) + Bruit");
		labelCalculBonnesActions.setFont(new Font("Arial", Font.BOLD, 10));
		labelCalculBonnesActions.setForeground(Color.WHITE);
		labelCalculBonnesActions.setOpaque(true);
		labelCalculBonnesActions.setBackground(MAUVE_FONCE);
		labelCalculBonnesActions.setPreferredSize(new Dimension(320, 45));
		labelCalculBonnesActions.setMaximumSize(new Dimension(320, 45));
		labelCalculBonnesActions.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelCalculBonnesActions.setHorizontalAlignment(JLabel.CENTER);
		labelCalculBonnesActions.setVerticalAlignment(JLabel.CENTER);
		add(labelCalculBonnesActions);
		add(Box.createRigidArea(new Dimension(0, 10)));

		// Jauge 2 : Évolution bonnes actions (GAIN + ÉVOLUTION)
		panelBonnesActionsEvolution = createDividedBar("[+0.00]", "[0%]");
		add(panelBonnesActionsEvolution);
		add(Box.createRigidArea(new Dimension(0, 15)));

		// COMPÉTENCES - NOUVELLE SECTION
		JLabel labelCompetencesTitle = new JLabel("Compétences");
		labelCompetencesTitle.setFont(AppConfiguration.textfont);
		labelCompetencesTitle.setForeground(TEXT_COLOR);
		labelCompetencesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelCompetencesTitle);

		// Jauge calcul général compétences (JLabel simple)
		labelCalculCompetences = new JLabel("(Imp×Diff×Log(1+|Imp|)) + Bonus - (|deg|×0.4) + Bruit");
		labelCalculCompetences.setFont(new Font("Arial", Font.BOLD, 10));
		labelCalculCompetences.setForeground(Color.WHITE);
		labelCalculCompetences.setOpaque(true);
		labelCalculCompetences.setBackground(MAUVE_FONCE);
		labelCalculCompetences.setPreferredSize(new Dimension(320, 45));
		labelCalculCompetences.setMaximumSize(new Dimension(320, 45));
		labelCalculCompetences.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelCalculCompetences.setHorizontalAlignment(JLabel.CENTER);
		labelCalculCompetences.setVerticalAlignment(JLabel.CENTER);
		add(labelCalculCompetences);
		add(Box.createRigidArea(new Dimension(0, 15)));

		//  FACTEURS COMMUNS 
		JLabel labelFacteursTitle = new JLabel("Facteurs Communs");
		labelFacteursTitle.setFont(AppConfiguration.textfont);
		labelFacteursTitle.setForeground(TEXT_COLOR);
		labelFacteursTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelFacteursTitle);

		JPanel panelFacteurs = new JPanel();
		panelFacteurs.setLayout(new BoxLayout(panelFacteurs, BoxLayout.X_AXIS));
		panelFacteurs.setBackground(BACKGROUND_COLOR);
		panelFacteurs.setMaximumSize(new Dimension(320, 40));
		panelFacteurs.setAlignmentX(Component.CENTER_ALIGNMENT);

		barFatigue = new JProgressBar(0, 100);
		barFatigue.setValue(0);
		barFatigue.setStringPainted(true);
		barFatigue.setString("Fatigue: 0.00");
		barFatigue.setFont(new Font("Arial", Font.BOLD, 9));
		barFatigue.setPreferredSize(new Dimension(160, 35));
		barFatigue.setMaximumSize(new Dimension(160, 35));
		barFatigue.setForeground(COLOR_FATIGUE);
		barFatigue.setBackground(Color.WHITE);
		panelFacteurs.add(barFatigue);

		barMemoire = new JProgressBar(0, 100);
		barMemoire.setValue(0);
		barMemoire.setStringPainted(true);
		barMemoire.setString("Mémoire: 0.00");
		barMemoire.setFont(new Font("Arial", Font.BOLD, 9));
		barMemoire.setPreferredSize(new Dimension(160, 35));
		barMemoire.setMaximumSize(new Dimension(160, 35));
		barMemoire.setForeground(COLOR_MEMOIRE);
		barMemoire.setBackground(Color.WHITE);
		panelFacteurs.add(barMemoire);

		add(panelFacteurs);
		add(Box.createRigidArea(new Dimension(0, 15)));

		// COMPÉTENCES 
		barProprete = createCompetencePanel("Propreté");
		barDiscipline = createCompetencePanel("Discipline");
		barObeissance = createCompetencePanel("Obéissance");
		barAction = createCompetencePanel("Bonnes Actions");

		add(Box.createRigidArea(new Dimension(0, 10)));

		// JOURNAL 
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

	//  MÉTHODE ( ya une petite modification) 
	private JProgressBar createCompetencePanel(String nom) {

		JLabel label = new JLabel(nom);
		label.setFont(AppConfiguration.textfont);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);

		// Jauge divisée GAIN + ÉVOLUTION 
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(BACKGROUND_COLOR);
		panel.setMaximumSize(new Dimension(320, 40));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Partie GAIN (30%)
		JProgressBar barGain = new JProgressBar(0, 100);
		barGain.setValue(0);
		barGain.setStringPainted(true);
		barGain.setString("[+0.00]");
		barGain.setFont(new Font("Arial", Font.BOLD, 9));
		barGain.setPreferredSize(new Dimension(96, 35));
		barGain.setMaximumSize(new Dimension(96, 35));
		barGain.setForeground(COLOR_GAIN);
		barGain.setBackground(Color.WHITE);
		panel.add(barGain);

		// Partie ÉVOLUTION (70%)
		JProgressBar barEvolution = new JProgressBar(0, 100);
		barEvolution.setValue(0);
		barEvolution.setStringPainted(true);
		barEvolution.setString("[0%]");
		barEvolution.setFont(new Font("Arial", Font.BOLD, 9));
		barEvolution.setPreferredSize(new Dimension(224, 35));
		barEvolution.setMaximumSize(new Dimension(224, 35));
		barEvolution.setForeground(BAR_COLOR);
		barEvolution.setBackground(Color.WHITE);
		panel.add(barEvolution);

		add(panel);
		add(Box.createRigidArea(new Dimension(0, 15)));

		//  Stocker les références 
		if (nom.equals("Propreté")) {
			panelProprete = panel;
		} else if (nom.equals("Discipline")) {
			panelDiscipline = panel;
		} else if (nom.equals("Obéissance")) {
			panelObeissance = panel;
		}

		return barEvolution;
	}

	// Méthode pour mettre à jour les données 
	public void updateData(double bonnesactions,
						   int actions,
						   double pr,
						   double ds,
						   double ob,
						   //  Nouveaux paramètres
						   double prCoeff, double prFatigue,
						   double dsCoeff, double dsFatigue,
						   double obCoeff, double obFatigue,
						   double fatigue, double memoryErrors,
						   boolean isReward, int rewardDegree) {

		// Mise à jour nombre actions
		labelNombreActions.setText("Nombre Actions: " + actions);

		// NOUVEAUX : Mise à jour facteurs communs 
		barFatigue.setValue(Math.min(100, (int) (fatigue * 5)));
		barFatigue.setString(String.format("Fatigue: %.2f", fatigue));
		
		barMemoire.setValue(Math.min(100, (int) (memoryErrors * 3)));
		barMemoire.setString(String.format("Mémoire: %.2f", memoryErrors));

		// MODIFIÉE : Mise à jour compétences avec nouvelles méthodes 
		updateBonnesActionsEvolution(bonnesactions, 40);
		updateCompetenceRow(panelProprete, pr, 20);
		updateCompetenceRow(panelDiscipline, ds, 0);
		updateCompetenceRow(panelObeissance, ob, 0);
		
		barAction.setValue((int) bonnesactions);
	}
	
	public void addMessage(String message) {
		logArea.append(message + "\n---\n");
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}

	public void clearLog() {
		logArea.setText("");
	}

	//Nouvelles méthodes 

	private JPanel createDividedBar(String gainLabel, String evolutionLabel) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(BACKGROUND_COLOR);
		panel.setMaximumSize(new Dimension(320, 40));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JProgressBar barGain = new JProgressBar(0, 100);
		barGain.setValue(0);
		barGain.setStringPainted(true);
		barGain.setString(gainLabel);
		barGain.setFont(new Font("Arial", Font.BOLD, 9));
		barGain.setPreferredSize(new Dimension(96, 35));
		barGain.setMaximumSize(new Dimension(96, 35));
		barGain.setForeground(COLOR_GAIN);
		barGain.setBackground(Color.WHITE);
		panel.add(barGain);

		JProgressBar barEvolution = new JProgressBar(0, 100);
		barEvolution.setValue(0);
		barEvolution.setStringPainted(true);
		barEvolution.setString(evolutionLabel);
		barEvolution.setFont(new Font("Arial", Font.BOLD, 9));
		barEvolution.setPreferredSize(new Dimension(224, 35));
		barEvolution.setMaximumSize(new Dimension(224, 35));
		barEvolution.setForeground(BAR_COLOR);
		barEvolution.setBackground(Color.WHITE);
		panel.add(barEvolution);

		return panel;
	}

	private void updateBonnesActionsEvolution(double currentScore, double previousScore) {
		double gain = currentScore - previousScore;
		
		Component[] components = panelBonnesActionsEvolution.getComponents();
		if (components.length >= 2) {
			JProgressBar barGain = (JProgressBar) components[0];
			JProgressBar barEvolution = (JProgressBar) components[1];
			
			Color gainColor = gain > 0 ? GREEN : RED;
			barGain.setForeground(gainColor);
			barGain.setString(String.format("[%+.2f]", gain));
			barGain.setValue(Math.min(100, (int) Math.abs(gain * 5)));
			
			barEvolution.setValue((int) currentScore);
			barEvolution.setString(String.format("[%d%%]", (int) currentScore));
		}
	}

	private void updateCompetenceRow(JPanel panel, double currentScore, double previousScore) {
		double gain = currentScore - previousScore;
		
		Component[] components = panel.getComponents();
		if (components.length >= 2) {
			JProgressBar barGain = (JProgressBar) components[0];
			JProgressBar barEvolution = (JProgressBar) components[1];
			
			Color gainColor = gain > 0 ? GREEN : RED;
			barGain.setForeground(gainColor);
			barGain.setString(String.format("[%+.2f]", gain));
			barGain.setValue(Math.min(100, (int) Math.abs(gain * 5)));
			
			barEvolution.setValue((int) currentScore);
			barEvolution.setString(String.format("[%d%%]", (int) currentScore));
		}
	}
}