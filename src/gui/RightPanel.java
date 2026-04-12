/*package gui;

import javax.swing.*;

import config.AppConfiguration;
import engine.process.GameManager;

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
	private double oldPr = 0;
	private double oldDs = 0;
	private double oldOb = 0;
	private double oldBonnes = 40;

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
		labelCalculBonnesActions = new JLabel("Log(1 + Mémoire) × coeff(deg) - (Fatigue × 0.1) + Bonus + Passif \n Passif : est (Propreté + Discipline + Obéissance) / 600 \n Bonus: est ajoutee si le nombre de bonnes actions depasse 12 \n deg: correspond à la punition ou la récompense");
		labelCalculBonnesActions.setFont(new Font("Arial", Font.BOLD, 12));
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
		labelCalculCompetences = new JLabel("Gain = Impact × Log(1 + |Impact|) - (|deg| × 0.4) \n Impact: peut être positif (action bénéfique) ou négatif (action maladroite) \n deg: correspond à la punition ou la récompense ");
		labelCalculCompetences.setFont(new Font("Arial", Font.BOLD, 12));
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
	public void updateData(GameManager gm) {

		labelNombreActions.setText("Nombre Actions: " + gm.getActions());

		barFatigue.setValue(Math.min(100, (int)(gm.getFatigue() * 5)));
		barFatigue.setString(String.format("Fatigue: %.2f", gm.getFatigue()));

		barMemoire.setValue(Math.min(100, (int)(gm.getMemoireErreurs() * 3)));
		barMemoire.setString(String.format("Mémoire: %.2f", gm.getMemoireErreurs()));

		updateBonnesActionsEvolution(gm.getBonnesAct(), 40);

		updateBonnesActionsEvolution(gm.getBonnesAct(), oldBonnes);
		updateCompetenceRow(panelProprete, gm.getScoreProprete(), oldPr);
		updateCompetenceRow(panelDiscipline, gm.getScoreDiscipline(), oldDs);
		updateCompetenceRow(panelObeissance, gm.getScoreObeissance(), oldOb);

		// update old values
		oldPr = gm.getScoreProprete();
		oldDs = gm.getScoreDiscipline();
		oldOb = gm.getScoreObeissance();
		oldBonnes = gm.getBonnesAct();
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
}*/



package gui;

import javax.swing.*;
import config.AppConfiguration;
import engine.process.GameManager;
import java.awt.*;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JProgressBar barProprete;
	private JProgressBar barDiscipline;
	private JProgressBar barObeissance;
	private JPanel panelBonnesActionsEvolution;
	private JProgressBar barFatigue;
	private JProgressBar barMemoire;
	private JPanel panelProprete;
	private JPanel panelDiscipline;
	private JPanel panelObeissance;

	private JLabel labelNombreActions;
	private JTextArea logArea;

	private double oldPr = 0;
	private double oldDs = 0;
	private double oldOb = 0;
	private double oldBonnes = 40;

	// ── COULEURS ──
	private static final Color BACKGROUND_COLOR = new Color(160, 164, 222);
	private static final Color BAR_COLOR        = new Color(37, 52, 181);
	private static final Color TEXT_COLOR       = Color.BLACK;
	private static final Color COLOR_FATIGUE    = new Color(255, 140, 0);
	private static final Color COLOR_MEMOIRE    = new Color(100, 150, 220);
	private static final Color COLOR_GAIN       = new Color(80, 200, 100);
	private static final Color GREEN            = new Color(80, 200, 100);
	private static final Color RED              = new Color(220, 100, 100);
	private static final Color MAUVE_FONCE      = new Color(120, 100, 180);

	public RightPanel() {
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOR);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		init();
	}

	private void init() {

		// ───────── COLONNE GAUCHE ─────────
		JPanel leftCol = new JPanel();
		leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
		leftCol.setBackground(BACKGROUND_COLOR);
		leftCol.setOpaque(true);

		// Nombre actions
		labelNombreActions = new JLabel("Nombre Actions: 0");
		labelNombreActions.setFont(AppConfiguration.textfont);
		labelNombreActions.setForeground(TEXT_COLOR);
		labelNombreActions.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftCol.add(labelNombreActions);
		leftCol.add(Box.createRigidArea(new Dimension(0, 15)));

		// ── Bonnes Actions ──
		JLabel bonnesTitle = new JLabel("Bonnes Actions");
		bonnesTitle.setFont(AppConfiguration.textfont);
		bonnesTitle.setForeground(TEXT_COLOR);
		bonnesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftCol.add(bonnesTitle);

		leftCol.add(createFormulaPanel(
			"Log(1 + Mémoire) × coeff(deg) - (Fatigue × 0.1) + Bonus + Passif",
			new String[]{
				"Passif → (Propreté + Discipline + Obéissance) / 600",
				"Bonus  → ajouté si bonnes actions > 10",
				"deg    → négatif si punition, positif si récompense"
			}
		));
		leftCol.add(Box.createRigidArea(new Dimension(0, 10)));

		panelBonnesActionsEvolution = createDividedBar("[+0.00]", "[0%]");
		leftCol.add(panelBonnesActionsEvolution);
		leftCol.add(Box.createRigidArea(new Dimension(0, 20)));

		// ── Compétences ──
		JLabel compTitle = new JLabel("Compétences");
		compTitle.setFont(AppConfiguration.textfont);
		compTitle.setForeground(TEXT_COLOR);
		compTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftCol.add(compTitle);

		leftCol.add(createFormulaPanel(
			"Gain = Impact × Log(1 + |Impact|) - (|deg| × 0.4)",
			new String[]{
				"Impact → positif (action bénéfique) ou négatif (action maladroite)",
				"deg    → négatif si punition, positif si récompense"
			}
		));
		leftCol.add(Box.createRigidArea(new Dimension(0, 20)));

		// ── Facteurs Communs ──
		JLabel facteursTitle = new JLabel("Facteurs Communs");
		facteursTitle.setFont(AppConfiguration.textfont);
		facteursTitle.setForeground(TEXT_COLOR);
		facteursTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftCol.add(facteursTitle);

		JPanel facteursPanel = new JPanel();
		facteursPanel.setLayout(new BoxLayout(facteursPanel, BoxLayout.X_AXIS));
		facteursPanel.setBackground(BACKGROUND_COLOR);
		facteursPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

		barFatigue = new JProgressBar(0, 100);
		barFatigue.setStringPainted(true);
		barFatigue.setString("Fatigue: 0.00");
		barFatigue.setFont(new Font("Arial", Font.BOLD, 9));
		barFatigue.setForeground(COLOR_FATIGUE);
		barFatigue.setBackground(Color.WHITE);
		barFatigue.setPreferredSize(new Dimension(150, 35));
		barFatigue.setMinimumSize(new Dimension(150, 35));
		barFatigue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

		barMemoire = new JProgressBar(0, 100);
		barMemoire.setStringPainted(true);
		barMemoire.setString("Mémoire: 0.00");
		barMemoire.setFont(new Font("Arial", Font.BOLD, 9));
		barMemoire.setForeground(COLOR_MEMOIRE);
		barMemoire.setBackground(Color.WHITE);
		barMemoire.setPreferredSize(new Dimension(150, 35));
		barMemoire.setMinimumSize(new Dimension(150, 35));
		barMemoire.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

		facteursPanel.add(barFatigue);
		facteursPanel.add(barMemoire);
		leftCol.add(facteursPanel);
		leftCol.add(Box.createRigidArea(new Dimension(0, 15)));

		// ── Barres compétences ──
		barProprete  = createCompetencePanel("Propreté",   leftCol);
		barDiscipline= createCompetencePanel("Discipline", leftCol);
		barObeissance= createCompetencePanel("Obéissance", leftCol);

		leftCol.add(Box.createVerticalGlue());

		// ───────── COLONNE DROITE (Journal) ─────────
		JPanel rightCol = new JPanel();
		rightCol.setLayout(new BorderLayout());
		rightCol.setBackground(BACKGROUND_COLOR);

		JLabel journalLabel = new JLabel("Journal :");
		journalLabel.setFont(AppConfiguration.textfont);
		journalLabel.setForeground(TEXT_COLOR);
		journalLabel.setHorizontalAlignment(JLabel.CENTER);
		rightCol.add(journalLabel, BorderLayout.NORTH);

		logArea = new JTextArea();
		logArea.setEditable(false);
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);
		logArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
		logArea.setBackground(new Color(240, 241, 255));
		logArea.setForeground(TEXT_COLOR);

		JScrollPane scroll = new JScrollPane(logArea);
		scroll.setPreferredSize(new Dimension(250, 0));
		rightCol.add(scroll, BorderLayout.CENTER);

		// ───────── SPLIT ─────────
		JSplitPane split = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			leftCol,
			rightCol
		);
		split.setResizeWeight(0.7);
		split.setDividerSize(5);
		split.setBackground(BACKGROUND_COLOR);

		add(split, BorderLayout.CENTER);
	}

	// ── Panneau formule (titre jaune + explications blanches sur fond mauve) ──
	private JPanel createFormulaPanel(String formule, String[] explications) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(MAUVE_FONCE);
		panel.setOpaque(true);
		panel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel title = new JLabel(formule);
		title.setFont(new Font("Arial", Font.BOLD, 12));
		title.setForeground(Color.YELLOW);
		title.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(title);
		panel.add(Box.createRigidArea(new Dimension(0, 4)));

		for (String s : explications) {
			JLabel l = new JLabel(s);
			l.setFont(new Font("Arial", Font.PLAIN, 11));
			l.setForeground(Color.WHITE);
			l.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel.add(l);
		}

		return panel;
	}

	// ── Ligne compétence : label + barre gain (verte) + barre évolution (bleue) ──
	private JProgressBar createCompetencePanel(String nom, JPanel container) {

		JLabel label = new JLabel(nom);
		label.setFont(AppConfiguration.textfont);
		label.setForeground(TEXT_COLOR);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(label);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(BACKGROUND_COLOR);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Partie GAIN (30 %)
		JProgressBar gain = new JProgressBar(0, 100);
		gain.setValue(0);
		gain.setStringPainted(true);
		gain.setString("[+0.00]");
		gain.setFont(new Font("Arial", Font.BOLD, 9));
		gain.setForeground(COLOR_GAIN);
		gain.setBackground(Color.WHITE);
		gain.setPreferredSize(new Dimension(96, 35));
		gain.setMinimumSize(new Dimension(96, 35));
		gain.setMaximumSize(new Dimension(96, 35));
		panel.add(gain);

		// Partie ÉVOLUTION (70 %)
		JProgressBar evo = new JProgressBar(0, 100);
		evo.setValue(0);
		evo.setStringPainted(true);
		evo.setString("[0%]");
		evo.setFont(new Font("Arial", Font.BOLD, 9));
		evo.setForeground(BAR_COLOR);
		evo.setBackground(Color.WHITE);
		evo.setPreferredSize(new Dimension(224, 35));
		evo.setMinimumSize(new Dimension(224, 35));
		evo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		panel.add(evo);

		container.add(panel);
		container.add(Box.createRigidArea(new Dimension(0, 15)));

		if (nom.equals("Propreté"))   panelProprete   = panel;
		if (nom.equals("Discipline")) panelDiscipline = panel;
		if (nom.equals("Obéissance")) panelObeissance = panel;

		return evo;
	}

	// ── Barre divisée générique (utilisée pour Bonnes Actions en haut) ──
	private JPanel createDividedBar(String gainLabel, String evolutionLabel) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBackground(BACKGROUND_COLOR);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JProgressBar gain = new JProgressBar(0, 100);
		gain.setValue(0);
		gain.setStringPainted(true);
		gain.setString(gainLabel);
		gain.setFont(new Font("Arial", Font.BOLD, 9));
		gain.setForeground(COLOR_GAIN);
		gain.setBackground(Color.WHITE);
		gain.setPreferredSize(new Dimension(96, 35));
		gain.setMinimumSize(new Dimension(96, 35));
		gain.setMaximumSize(new Dimension(96, 35));
		panel.add(gain);

		JProgressBar evo = new JProgressBar(0, 100);
		evo.setValue(0);
		evo.setStringPainted(true);
		evo.setString(evolutionLabel);
		evo.setFont(new Font("Arial", Font.BOLD, 9));
		evo.setForeground(BAR_COLOR);
		evo.setBackground(Color.WHITE);
		evo.setPreferredSize(new Dimension(224, 35));
		evo.setMinimumSize(new Dimension(224, 35));
		evo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		panel.add(evo);

		return panel;
	}

	// ── Mise à jour des données ──
	public void updateData(GameManager gm) {

		labelNombreActions.setText("Nombre Actions: " + gm.getActions());

		barFatigue.setValue(Math.min(100, (int)(gm.getFatigue() * 5)));
		barFatigue.setString(String.format("Fatigue: %.2f", gm.getFatigue()));

		barMemoire.setValue(Math.min(100, (int)(gm.getMemoireErreurs() * 3)));
		barMemoire.setString(String.format("Mémoire: %.2f", gm.getMemoireErreurs()));

		updateRow(panelBonnesActionsEvolution, gm.getBonnesAct(), oldBonnes);
		updateRow(panelProprete,   gm.getScoreProprete(),   oldPr);
		updateRow(panelDiscipline, gm.getScoreDiscipline(), oldDs);
		updateRow(panelObeissance, gm.getScoreObeissance(), oldOb);

		oldPr     = gm.getScoreProprete();
		oldDs     = gm.getScoreDiscipline();
		oldOb     = gm.getScoreObeissance();
		oldBonnes = gm.getBonnesAct();
	}

	private void updateRow(JPanel panel, double current, double previous) {
		double gain = current - previous;

		JProgressBar barGain = (JProgressBar) panel.getComponent(0);
		JProgressBar barEvo  = (JProgressBar) panel.getComponent(1);

		barGain.setForeground(gain >= 0 ? GREEN : RED);
		barGain.setString(String.format("[%+.2f]", gain));
		barGain.setValue(Math.min(100, (int) Math.abs(gain * 5)));

		barEvo.setValue((int) current);
		barEvo.setString(String.format("[%d%%]", (int) current));
	}

	public void addMessage(String message) {
		logArea.append(message + "\n---\n");
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}

	public void clearLog() {
		logArea.setText("");
	}
}