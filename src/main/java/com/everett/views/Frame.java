package com.everett.views;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame extends JFrame {
    public static EntityManagerFactory ENTITY = null;
    public SandboxPage sandBoxPage;
    public RankPage rankPage;
    public TopRecordsPage topRecordsPage;
    private WelcomePage welcomePage = new WelcomePage(this);
    public CardLayout cards;
    public JButton exiButton;
    private ImageIcon icon;
    public static Thread createEntityThread;

    static {
        createEntityThread = new Thread(() -> {
            try {
                ENTITY = Persistence.createEntityManagerFactory("queengame");
            } catch (Exception e) {
            }
        });
        createEntityThread.start();
    }

    private Frame() {

        cards = new CardLayout();
        this.setSize(new Dimension(1200, 950));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.sandBoxPage = null;
        this.rankPage = null;
        this.topRecordsPage = null;
        this.setLayout(cards);
        this.setTitle("N Queens Game");
        icon = new ImageIcon("src/main/resources/pictures/taskbarIcon.png");
        Image frameIcon = icon.getImage();
        this.setIconImage(frameIcon);
        cards.show(this.getContentPane(), "welcomePage");
        this.add(welcomePage, "welcomePage");
    }

    public static void main(String[] args) {
        Frame game = new Frame();
        game.setLocationRelativeTo(null);
        game.setVisible(true);

    }
}
