package com.everett.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.everett.controllers.MyColor;
import com.everett.models.Record;

public class TopRecordsPage extends JPanel {
    public JButton backButton;
    private static EntityManagerFactory ENTITY = Frame.ENTITY;
    private JPanel middleRecordsPanel;

    TopRecordsPage() {
        // Top Title
        JLabel title = new JLabel();
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1250, 40));
        topPanel.setBackground(MyColor.TOP_TITLE);
        topPanel.add(title);
        // End of top title

        // Middle panel contains top records
        JLabel topTitle = new JLabel("Top Records Board");
        topTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 55));
        topTitle.setForeground(Color.WHITE);

        JPanel topRecordsPanel = new JPanel();
        topRecordsPanel.setBounds(0, 0, 700, 100);
        topRecordsPanel.setOpaque(false);
        topRecordsPanel.add(topTitle);

        // Table titile
        JLabel idLabel = new JLabel("Rank");
        idLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        idLabel.setPreferredSize(new Dimension(90, 50));

        JLabel nameLabel = new JLabel("Player Name");
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setPreferredSize(new Dimension(330, 50));

        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setPreferredSize(new Dimension(150, 50));

        JLabel solveTimeLabel = new JLabel("Time");
        solveTimeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
        solveTimeLabel.setHorizontalAlignment(JLabel.CENTER);
        solveTimeLabel.setPreferredSize(new Dimension(80, 50));

        JPanel tableTitlePanel = new JPanel();
        tableTitlePanel.setPreferredSize(new Dimension(700, 50));
        tableTitlePanel.setBackground(Color.WHITE);
        tableTitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tableTitlePanel.add(idLabel);
        tableTitlePanel.add(nameLabel);
        tableTitlePanel.add(scoreLabel);
        tableTitlePanel.add(solveTimeLabel);
        // end of table title

        middleRecordsPanel = new JPanel();
        middleRecordsPanel.setBounds(0, 100, 700, 550);
        GridLayout middleRecLayout = new GridLayout(11, 0);
        middleRecLayout.setVgap(5);
        middleRecordsPanel.setOpaque(false);

        middleRecordsPanel.setLayout(middleRecLayout);
        middleRecordsPanel.add(tableTitlePanel);

        loadRecords();

        JPanel recordPanel = new JPanel();
        recordPanel.setPreferredSize(new Dimension(700, 680));
        recordPanel.setBackground(MyColor.DARK_BLUE);
        recordPanel.setLayout(null);

        recordPanel.add(topRecordsPanel);
        recordPanel.add(middleRecordsPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(MyColor.DARK_BLUE_CTNER);
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(recordPanel, new GridBagConstraints());
        // End of Middle panel contains top records

        // Sidebar contains controller buttons
        JPanel bottomButtonsGroup = new JPanel();
        bottomButtonsGroup.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomButtonsGroup.setBackground(MyColor.DARK_BLUE);
        bottomButtonsGroup.setPreferredSize(new Dimension(300, 130));

        // Quit game button
        JButton quitButton = new JButton();
        quitButton.setText("Quit game");
        quitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        quitButton.setBackground(MyColor.RED);
        quitButton.setPreferredSize(new Dimension(280, 50));
        quitButton.setFocusable(false);
        quitButton.setName("quitGame");
        quitButton.addActionListener(e -> {
            showQuitDialog("Do you want to quit game?", "Quit game", 25);
        });
        // End of Quit game button

        // Back to welcome page
        backButton = new JButton();
        backButton.setText("Back home");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        backButton.setBackground(MyColor.ORANGE);
        backButton.setPreferredSize(new Dimension(280, 50));
        backButton.setFocusable(false);
        backButton.setName("backHome");
        // End of Back to welcome page

        bottomButtonsGroup.add(backButton);
        bottomButtonsGroup.add(quitButton);

        JPanel sideBar = new JPanel();
        sideBar.setPreferredSize(new Dimension(300, 1000));
        sideBar.setBackground(MyColor.DARK_BLUE);
        sideBar.setLayout(new BorderLayout());

        sideBar.add(bottomButtonsGroup, BorderLayout.SOUTH);
        // Sidebar contains controller buttons

        this.setBounds(0, 0, 1250, 1000);
        this.setLayout(new BorderLayout());

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(sideBar, BorderLayout.EAST);
    }

    private void loadRecords() {
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            em = ENTITY.createEntityManager();
            et = em.getTransaction();
        } catch (Exception e) {
            return;
        }

        List<Record> recordList = null;
        try {
            et.begin();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Record> criteria = builder.createQuery(Record.class);
            Root<Record> root = criteria.from(Record.class);
            criteria.select(root).orderBy(builder.desc(root.get("score")));

            TypedQuery<Record> query = em.createQuery(criteria).setMaxResults(10);
            recordList = query.getResultList();
            int i = 1;
            for (Record rec : recordList) {
                rec.setRank(i);
                JPanel thisPanel = createRecPanel(rec.getRank(), rec.getPlayerName(), rec.getScore(),
                        rec.getSolveTime());
                Thread addToPage = new Thread(() -> {
                    middleRecordsPanel.add(thisPanel);
                });
                addToPage.start();
                i++;
            }

            et.commit();
        } catch (Exception ex) {
            System.err.println("Can not retrieve records");
            et.rollback();
            return;
        }
    }

    private JPanel createRecPanel(final int id, final String playerName, final int score, final int solveTime) {
        JLabel idLabel = new JLabel(id + "");
        idLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        idLabel.setPreferredSize(new Dimension(90, 50));

        JLabel nameLabel = new JLabel(playerName);
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setPreferredSize(new Dimension(330, 50));

        JLabel scoreLabel = new JLabel(score + "");
        scoreLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setPreferredSize(new Dimension(150, 50));

        JLabel solveTimeLabel = new JLabel(solveTime + "");
        solveTimeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        solveTimeLabel.setHorizontalAlignment(JLabel.CENTER);
        solveTimeLabel.setPreferredSize(new Dimension(80, 50));

        JPanel thisPanel = new JPanel();
        thisPanel.setPreferredSize(new Dimension(700, 70));
        thisPanel.setBackground(MyColor.GREEN);
        thisPanel.setLayout(new FlowLayout());
        thisPanel.add(idLabel);
        thisPanel.add(nameLabel);
        thisPanel.add(scoreLabel);
        thisPanel.add(solveTimeLabel);
        return thisPanel;
    }

    private void showQuitDialog(final String message, final String title, int fontSize) {
        JDialog theDialog = new JDialog();
        theDialog.setTitle(title);
        theDialog.setSize(new Dimension(500, 200));
        theDialog.setLocationRelativeTo(null);
        theDialog.setResizable(false);
        theDialog.setLayout(new BorderLayout());
        theDialog.addWindowFocusListener(new WindowFocusListener() {
            public void windowLostFocus(WindowEvent e) {
                theDialog.dispose();
            }

            public void windowGainedFocus(WindowEvent e) {
            }
        });
        JLabel titleLabel = new JLabel(message);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(450, 150));
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(titleLabel, new GridBagConstraints());

        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(150, 40));
        okButton.setFont(new Font("Comic Sans MS", Font.BOLD, 21));
        okButton.setBackground(MyColor.GREEN);
        okButton.setFocusable(false);
        okButton.addActionListener(e1 -> {
            System.exit(0);
            theDialog.dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.setFont(new Font("Comic Sans MS", Font.BOLD, 21));
        cancelButton.setBackground(MyColor.RED);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(e2 -> {
            theDialog.dispose();
        });

        JPanel dialogButtonPanel = new JPanel();
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(30);
        dialogButtonPanel.setLayout(layout);
        dialogButtonPanel.setPreferredSize(new Dimension(450, 70));
        dialogButtonPanel.add(okButton);
        dialogButtonPanel.add(cancelButton);

        theDialog.add(titlePanel, BorderLayout.CENTER);
        theDialog.add(dialogButtonPanel, BorderLayout.SOUTH);
        theDialog.setVisible(true);
    }

}
