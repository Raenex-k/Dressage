package gui;

import javax.swing.*;
import config.AppConfiguration;
import engine.mobile.AnimalType;
import java.awt.*;
import java.util.List;

public class ScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color TITLE_COLOR      = new Color(20, 20, 150);
    private static final Color PROPRETE_COLOR   = new Color(80, 180, 120);
    private static final Color DISCIPLINE_COLOR = new Color(70, 130, 200);
    private static final Color OBEISSANCE_COLOR = new Color(220, 100, 80);
    private static final Color PANEL_BG         = new Color(255, 255, 255);
    private static final Color BORDER_COLOR     = new Color(200, 200, 220);

    private double scoreProprete;
    private double scoreDiscipline;
    private double scoreObeissance;

    private List<Double> histProprete;
    private List<Double> histDiscipline;
    private List<Double> histObeissance;

    private AnimalType animalType;

    public ScorePanel(double proprete, double discipline, double obeissance,
                      List<Double> histProprete,
                      List<Double> histDiscipline,
                      List<Double> histObeissance,
                      AnimalType animalType) {

        this.scoreProprete   = proprete;
        this.scoreDiscipline = discipline;
        this.scoreObeissance = obeissance;
        this.histProprete    = histProprete;
        this.histDiscipline  = histDiscipline;
        this.histObeissance  = histObeissance;
        this.animalType      = animalType;

        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        initComponents();
    }

    private void initComponents() {

        JLabel title = new JLabel("Bilan final du dressage", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(TITLE_COLOR);
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setOpaque(false);

        EvolutionGraphPanel graphPanel = new EvolutionGraphPanel(histProprete, histDiscipline, histObeissance);
        centerPanel.add(graphPanel);

        JPanel gaugesPanel = buildGaugesPanel();
        centerPanel.add(gaugesPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel buildGaugesPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel subtitle = new JLabel("Scores finaux", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.BOLD, 22));
        subtitle.setForeground(TITLE_COLOR);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitle);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        String animalName = (animalType == AnimalType.CHAT) ? "Chat" : "Chien";
        JLabel animalLabel = new JLabel("Animal : " + animalName, SwingConstants.CENTER);
        animalLabel.setFont(AppConfiguration.textfont);
        animalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(animalLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        addSkillGauge(panel, "Propreté",   scoreProprete,   PROPRETE_COLOR);
        addSkillGauge(panel, "Discipline", scoreDiscipline, DISCIPLINE_COLOR);
        addSkillGauge(panel, "Obéissance", scoreObeissance, OBEISSANCE_COLOR);

        return panel;
    }

    private void addSkillGauge(JPanel panel, String nom, double score, Color color) {
        JLabel label = new JLabel(nom + " : " + String.format("%.1f", score) + " / 100");
        label.setFont(AppConfiguration.textfont);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue((int) score);
        bar.setStringPainted(true);
        bar.setString(String.format("%.1f", score) + " %");
        bar.setForeground(color);
        bar.setBackground(new Color(230, 230, 235));
        bar.setFont(AppConfiguration.textfont);
        bar.setPreferredSize(new Dimension(300, 28));
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        bar.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(bar);
        panel.add(Box.createRigidArea(new Dimension(0, 22)));
    }

    private class EvolutionGraphPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        private List<Double> pr;
        private List<Double> ds;
        private List<Double> ob;

        private static final int MARGIN_LEFT   = 70;
        private static final int MARGIN_RIGHT  = 20;
        private static final int MARGIN_TOP    = 50;
        private static final int MARGIN_BOTTOM = 50;

        public EvolutionGraphPanel(List<Double> pr, List<Double> ds, List<Double> ob) {
            this.pr = pr;
            this.ds = ds;
            this.ob = ob;
            setBackground(PANEL_BG);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int graphW = w - MARGIN_LEFT - MARGIN_RIGHT;
            int graphH = h - MARGIN_TOP - MARGIN_BOTTOM;

            g2.setColor(TITLE_COLOR);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("Évolution des compétences", MARGIN_LEFT, MARGIN_TOP - 15);

            g2.setColor(new Color(220, 220, 230));
            g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, new float[]{4f}, 0f));
            for (int i = 0; i <= 4; i++) {
                int y = MARGIN_TOP + graphH - (i * graphH / 4);
                g2.drawLine(MARGIN_LEFT, y, MARGIN_LEFT + graphW, y);
            }

            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(MARGIN_LEFT, MARGIN_TOP, MARGIN_LEFT, MARGIN_TOP + graphH);
            g2.drawLine(MARGIN_LEFT, MARGIN_TOP + graphH, MARGIN_LEFT + graphW, MARGIN_TOP + graphH);

            Graphics2D g2r = (Graphics2D) g2.create();
            g2r.setColor(TITLE_COLOR);
            g2r.setFont(new Font("Arial", Font.BOLD, 12));
            g2r.rotate(-Math.PI / 2, 15, MARGIN_TOP + graphH / 2);
            g2r.drawString("Score (0 - 100)", 15 - 40, MARGIN_TOP + graphH / 2);
            g2r.dispose();

            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            g2.setColor(Color.DARK_GRAY);
            for (int i = 0; i <= 4; i++) {
                int val = i * 25;
                int y = MARGIN_TOP + graphH - (i * graphH / 4);
                g2.drawString(val + "", MARGIN_LEFT - 35, y + 5);
            }

            g2.setColor(TITLE_COLOR);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("Actions", MARGIN_LEFT + graphW / 2 - 20, MARGIN_TOP + graphH + 40);

            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            g2.setColor(Color.DARK_GRAY);
            int nbPoints = AppConfiguration.NBR_MAX_ACTIONS;           
            for (int i = 0; i <= 4; i++) {
                int xVal = (int) ((double) i / 4 * nbPoints);
                int x = MARGIN_LEFT + (int) ((double) i / 4 * graphW);
                g2.drawString(xVal + "", x - 5, MARGIN_TOP + graphH + 20);
            }

            drawCurve(g2, pr, PROPRETE_COLOR,   graphW, graphH);
            drawCurve(g2, ds, DISCIPLINE_COLOR, graphW, graphH);
            drawCurve(g2, ob, OBEISSANCE_COLOR, graphW, graphH);

            drawLegend(g2);
        }

        private void drawCurve(Graphics2D g2, List<Double> data, Color color, int graphW, int graphH) {
            if (data == null || data.size() < 2) return;

            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int n = data.size();
            int prevX = -1, prevY = -1;

            for (int i = 0; i < n; i++) {
                int x = MARGIN_LEFT + (int) ((double) i / (n - 1) * graphW);
                int y = MARGIN_TOP + graphH - (int) (data.get(i) / 100.0 * graphH);

                if (prevX >= 0) {
                    g2.drawLine(prevX, prevY, x, y);
                }
                prevX = x;
                prevY = y;
            }
        }

        private void drawLegend(Graphics2D g2) {
            String[] labels = {"Propreté", "Discipline", "Obéissance"};
            Color[] colors  = {PROPRETE_COLOR, DISCIPLINE_COLOR, OBEISSANCE_COLOR};

            g2.setFont(new Font("Arial", Font.BOLD, 12));
            int legendX = MARGIN_LEFT + 10;
            int legendY = MARGIN_TOP + 15;

            for (int i = 0; i < labels.length; i++) {
                g2.setColor(colors[i]);
                g2.fillRect(legendX, legendY + i * 20, 14, 14);
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(labels[i], legendX + 20, legendY + i * 20 + 12);
            }
        }
    }
}
