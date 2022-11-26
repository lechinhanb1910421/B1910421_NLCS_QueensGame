package com.everett.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.everett.controllers.MyColor;

public class WelcomePage extends JPanel implements ActionListener {
    private JButton startSandboxBtn;
    private JButton startRankBtn;
    private JButton how2PlayButton;
    private JButton topRecords;
    private JButton levelButtons[];
    private JPanel btnsPanel;
    private JPanel levelBtnPanel;
    private JPanel levelBtnTitlePanel;
    private Frame mainframe;
    private CardLayout cards;

    WelcomePage(Frame frame) {
        this.mainframe = frame;
        this.levelButtons = new JButton[6];
        this.setPreferredSize(new Dimension(1250, 1000));
        this.setLayout(null);
        for (int index = 0; index < 6; index++) {
            levelButtons[index] = createLevelButton((index + 4) + " queens");
        }
        JLabel welcome = createJLabel("Welcome to", 55, 660, 50);
        JLabel gameName = createJLabel("N Queens Game", 77, 660, 120);
        JLabel devName = createJLabel("Le Nguyen Chi Nhan", 35, 660, 50);
        JLabel devStCode = createJLabel("B1910421", 30, 660, 50);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBounds(490, 120, 660, 500);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.setOpaque(false);

        titlePanel.add(welcome);
        titlePanel.add(gameName);
        titlePanel.add(devName);
        titlePanel.add(devStCode);

        startRankBtn = new JButton("Rank Mode");
        startRankBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        startRankBtn.setPreferredSize(new Dimension(280, 60));
        startRankBtn.setBackground(MyColor.GREEN);
        startRankBtn.setFocusable(false);
        startRankBtn.setName("startRank");
        startRankBtn.addActionListener(this);

        startSandboxBtn = new JButton("Sandbox Mode");
        startSandboxBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        startSandboxBtn.setPreferredSize(new Dimension(280, 60));
        startSandboxBtn.setBackground(MyColor.CYAN);
        startSandboxBtn.setFocusable(false);
        startSandboxBtn.setName("startSandbox");
        startSandboxBtn.addActionListener(this);

        JLabel levelBtnTitle = new JLabel("Select number of Queens");
        levelBtnTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        levelBtnTitle.setForeground(Color.WHITE);

        levelBtnTitlePanel = new JPanel();
        levelBtnTitlePanel.setBounds(610, 550, 410, 60);
        levelBtnTitlePanel.add(levelBtnTitle);
        levelBtnTitlePanel.setBackground(MyColor.DARK_BLUE);
        levelBtnTitlePanel.setVisible(false);

        levelBtnPanel = new JPanel();
        levelBtnPanel.setBounds(610, 620, 410, 200);
        GridLayout levelBtnsLayout = new GridLayout(3, 2);
        levelBtnsLayout.setHgap(10);
        levelBtnsLayout.setVgap(10);
        levelBtnPanel.setLayout(levelBtnsLayout);
        levelBtnPanel.setOpaque(false);
        levelBtnPanel.setVisible(false);

        for (int i = 0; i < 6; i++) {
            levelBtnPanel.add(levelButtons[i]);
        }

        topRecords = new JButton("Top Records");
        topRecords.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        topRecords.setPreferredSize(new Dimension(280, 60));
        topRecords.setBackground(MyColor.ORANGE);
        topRecords.setFocusable(false);
        topRecords.addActionListener(this);
        topRecords.setName("topRecords");

        how2PlayButton = new JButton("How to play");
        how2PlayButton.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        how2PlayButton.setPreferredSize(new Dimension(280, 60));
        how2PlayButton.setBackground(Color.YELLOW);
        how2PlayButton.setFocusable(false);
        how2PlayButton.addActionListener(this);
        how2PlayButton.setName("how2play");

        btnsPanel = new JPanel();
        btnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnsPanel.setBounds(670, 550, 280, 300);
        btnsPanel.setOpaque(false);

        btnsPanel.add(startRankBtn);
        btnsPanel.add(startSandboxBtn);
        btnsPanel.add(topRecords);
        btnsPanel.add(how2PlayButton);

        File img = new File("src/main/resources/pictures/background2.png");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon backgroundImage = new ImageIcon(bufferedImage);
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, 1250, 1000);

        this.add(titlePanel);
        this.add(btnsPanel);
        this.add(levelBtnPanel);
        this.add(levelBtnTitlePanel);
        this.add(background);

    }

    private JLabel createJLabel(String text, int fontSize, int dimWidth, int dimHeight) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
        label.setPreferredSize(new Dimension(dimWidth, dimHeight));
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;

    }

    private JButton createLevelButton(String text) {
        JButton res = new JButton(text);
        res.setBackground(MyColor.CYAN);
        res.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        res.setPreferredSize(new Dimension(200, 60));
        res.setFocusable(false);
        res.addActionListener(this);
        return res;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 6; i++) {
            if (e.getSource() == this.levelButtons[i]) {
                mainframe.sandBoxPage = new SandboxPage(i + 4);
                mainframe.add(mainframe.sandBoxPage, "sandboxPage");
                mainframe.cards.show(mainframe.getContentPane(), "sandboxPage");
                mainframe.sandBoxPage.backButton.addActionListener(e3 -> {
                    showGoHomeDialog("Do you want to go back Homepage?", "Back to Title", 25);
                });
                this.btnsPanel.setVisible(true);
                this.levelBtnPanel.setVisible(false);
                this.levelBtnTitlePanel.setVisible(false);
                break;
            }
        }
        JButton thisButton = (JButton) e.getSource();
        String name = thisButton.getName();
        if ((name != "" || name != null) && (name == "startRank")) {
            mainframe.rankPage = new RankPage(8);
            mainframe.add(mainframe.rankPage, "rankPage");
            mainframe.cards.show(mainframe.getContentPane(), "rankPage");
            mainframe.rankPage.backButton.addActionListener(e2 -> {
                showGoHomeDialog("Do you want to go back Homepage?", "Back to Title", 25);
            });
            this.btnsPanel.setVisible(true);
            this.levelBtnPanel.setVisible(false);
            this.levelBtnTitlePanel.setVisible(false);
        }
        if ((name != "" || name != null) && (name == "startSandbox")) {
            this.btnsPanel.setVisible(false);
            this.levelBtnPanel.setVisible(true);
            this.levelBtnTitlePanel.setVisible(true);
        }
        if ((name != "" || name != null) && (name == "topRecords")) {
            try {
                Frame.createEntityThread.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            mainframe.topRecordsPage = new TopRecordsPage();
            mainframe.add(mainframe.topRecordsPage, "topRecordsPage");
            mainframe.cards.show(mainframe.getContentPane(), "topRecordsPage");

            mainframe.topRecordsPage.backButton.addActionListener(e3 -> {
                showGoHomeDialog("Do you want to go back Homepage?", "Back to Title", 25);
            });
        }
        if ((name != "" || name != null) && (name == "how2play")) {
            createHow2PlayDialog();
        }
    }

    private void createHow2PlayDialog() {
        JDialog theDialog = new JDialog();
        theDialog.setTitle("How to play");
        theDialog.setSize(new Dimension(700, 460));
        theDialog.setLocationRelativeTo(null);
        theDialog.setResizable(false);
        theDialog.setLayout(new BorderLayout());

        // Top panel contains switch between 3 mode
        JLabel title = new JLabel("What is N Queens Game ?");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 27));

        JPanel titleJPanel = new JPanel();
        titleJPanel.setPreferredSize(new Dimension(700, 60));
        titleJPanel.setLayout(new FlowLayout());
        titleJPanel.add(title, JLabel.CENTER);
        // End of Top panel contains switch between 2 mode

        // Middle panel explain details
        JLabel explain1 = new JLabel("<html>In N Queens Game, we have to place N chess queens<br>" +
                "on an NxN chessboard so that no two queens<br>attack each other</html>");
        explain1.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        JLabel explain2 = new JLabel("<html>There are a huge variety of functions in this game<br>" +
                "and explaining in detail every button would be <br>" +
                "a redundant work that costs a huge amount of time.<br>"
                + "So I will let you players enjoy the game by yourself</html>");
        explain2.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        JLabel explain3 = new JLabel("Good luck ;)))");
        explain3.setFont(new Font("Comic Sans MS", Font.BOLD, 25));

        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new FlowLayout());
        generalPanel.add(explain1);
        generalPanel.add(explain2);
        generalPanel.add(explain3);

        JPanel middlePanel = new JPanel();
        cards = new CardLayout();
        middlePanel.setLayout(cards);
        // End of Middle panel explain details

        // Bottom panel contains understood button
        JButton understood = new JButton("Understood !");
        understood.setPreferredSize(new Dimension(500, 50));
        understood.setBackground(MyColor.GREEN);
        understood.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        understood.setFocusable(false);
        understood.addActionListener(e -> {
            theDialog.dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(800, 70));
        bottomPanel.add(understood);
        // End of Bottom panel contains understood button

        theDialog.add(titleJPanel, BorderLayout.NORTH);
        theDialog.add(generalPanel, BorderLayout.CENTER);
        theDialog.add(bottomPanel, BorderLayout.SOUTH);
        theDialog.setModal(true);
        theDialog.setVisible(true);

    }

    private void showGoHomeDialog(final String message, final String title, int fontSize) {
        JDialog theDialog = new JDialog();
        theDialog.setTitle(title);
        theDialog.setSize(new Dimension(500, 200));
        theDialog.setLocationRelativeTo(null);
        theDialog.setResizable(false);
        theDialog.setLayout(new BorderLayout());

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
            theDialog.dispose();
            mainframe.cards.show(mainframe.getContentPane(), "welcomePage");
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
        theDialog.setModal(true);
        theDialog.setVisible(true);
    }

}
