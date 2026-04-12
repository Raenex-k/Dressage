package gui;

import engine.mobile.AnimalType;

import javax.swing.*;

import java.awt.*;

public class MenuGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JButton boutonChat;
    private JButton boutonChien;

    public MenuGUI() {

        setTitle("Accueil - Dressage");
        Dimension windowSize = new Dimension(1300, 850);

        setSize(windowSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(54, 54, 178));
        mainPanel.setLayout(new BorderLayout());

        add(mainPanel);

        //------------------ HAUT ------------------

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel(loadIcon("/images/logo.png", 200, 133));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titre = new JLabel("Dressage d’Animaux");
        titre.setFont(new Font("Arial", Font.BOLD, 42));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 👉 SLOGAN AJOUTÉ
        JLabel slogan = new JLabel("Bienvenue ! Veuillez choisir un animal à dresser.");
        slogan.setFont(new Font("Arial", Font.ITALIC, 18));
        slogan.setForeground(Color.WHITE);
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(logo);
        topPanel.add(titre);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10))); // espace
        topPanel.add(slogan);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        //------------------ CENTRE ------------------

        JPanel centrePanel = new JPanel();
        centrePanel.setOpaque(false);
        centrePanel.setLayout(new GridLayout(1, 2, 80, 0));

        centrePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        // CHAT
        JPanel chatPanel = new JPanel();
        chatPanel.setOpaque(false);
        chatPanel.setLayout(new BorderLayout());

        JLabel chatImage = new JLabel(loadIcon("/images/chatmenu.png", 250, 300));
        chatImage.setHorizontalAlignment(SwingConstants.CENTER);

        boutonChat = new JButton("Chat");
        boutonChat.setFont(new Font("Arial", Font.BOLD, 20));
        boutonChat.setBackground(new Color(234, 255, 246));
        boutonChat.setForeground(Color.BLACK);

        chatPanel.add(chatImage, BorderLayout.CENTER);
        chatPanel.add(boutonChat, BorderLayout.SOUTH);

        // CHIEN
        JPanel chienPanel = new JPanel();
        chienPanel.setOpaque(false);
        chienPanel.setLayout(new BorderLayout());

        JLabel chienImage = new JLabel(loadIcon("/images/chienmenu.png", 250, 300));
        chienImage.setHorizontalAlignment(SwingConstants.CENTER);

        boutonChien = new JButton("Chien");
        boutonChien.setFont(new Font("Arial", Font.BOLD, 20));
        boutonChien.setBackground(new Color(234, 255, 246));
        boutonChien.setForeground(Color.BLACK);

        chienPanel.add(chienImage, BorderLayout.CENTER);
        chienPanel.add(boutonChien, BorderLayout.SOUTH);

        centrePanel.add(chatPanel);
        centrePanel.add(chienPanel);

        mainPanel.add(centrePanel, BorderLayout.CENTER);

        //------------------ ACTIONS ------------------

        boutonChat.addActionListener(e -> {
            new MainGUI("DRESSAGE", AnimalType.CHAT);
            dispose();
        });

        boutonChien.addActionListener(e -> {
            new MainGUI("DRESSAGE", AnimalType.CHIEN);
            dispose();
        });

        setVisible(true);
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}