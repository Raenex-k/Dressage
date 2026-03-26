package gui;

import engine.mobile.AnimalType;

import javax.swing.*;

import config.AppConfiguration;

import java.awt.*;

public class MenuGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private JButton boutonChat;
    private JButton boutonChien;

    public MenuGUI() {

        setTitle("Accueil - Dressage");
        Dimension windowSize = new Dimension(
                AppConfiguration.COLUMN_COUNT * AppConfiguration.CELL_SIZE ,
                AppConfiguration.LINE_COUNT * AppConfiguration.CELL_SIZE 
        );

        setSize(windowSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(200,205,220));
        mainPanel.setLayout(new BorderLayout());

        add(mainPanel);

        //------------------ HAUT ------------------

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);

        JLabel logo = new JLabel(loadIcon("/images/logo.png", 60, 60));
        JLabel titre = new JLabel("Dressage d’Animaux");

        titre.setFont(new Font("Arial", Font.BOLD, 36));
        titre.setForeground(new Color(20,20,150));

        topPanel.add(logo);
        topPanel.add(titre);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        //------------------ CENTRE ------------------

        JPanel centrePanel = new JPanel();
        centrePanel.setOpaque(false);
        centrePanel.setLayout(new GridLayout(1,2,80,0));

        centrePanel.setBorder(BorderFactory.createEmptyBorder(50,100,50,100));

        // CHAT
        JPanel chatPanel = new JPanel();
        chatPanel.setOpaque(false);
        chatPanel.setLayout(new BorderLayout());

        JLabel chatImage = new JLabel(loadIcon("/images/chatmenu.png", 200, 200));
        chatImage.setHorizontalAlignment(SwingConstants.CENTER);

        boutonChat = new JButton("Chat");
        boutonChat.setFont(new Font("Arial", Font.BOLD, 20));
        boutonChat.setBackground(new Color(80,90,200));
        boutonChat.setForeground(Color.WHITE);

        chatPanel.add(chatImage, BorderLayout.CENTER);
        chatPanel.add(boutonChat, BorderLayout.SOUTH);

        // CHIEN
        JPanel chienPanel = new JPanel();
        chienPanel.setOpaque(false);
        chienPanel.setLayout(new BorderLayout());

        JLabel chienImage = new JLabel(loadIcon("/images/chienmenu.png", 200, 200));	
        chienImage.setHorizontalAlignment(SwingConstants.CENTER);

        boutonChien = new JButton("Chien");
        boutonChien.setFont(new Font("Arial", Font.BOLD, 20));
        boutonChien.setBackground(new Color(80,90,200));
        boutonChien.setForeground(Color.WHITE);

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

     
        ;

        setVisible(true);
    }
    private ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}