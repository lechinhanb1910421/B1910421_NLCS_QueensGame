package com.everett.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.everett.controllers.MyColor;
import com.everett.controllers.MyIcon;
import com.everett.controllers.RankController;

public class RankPage extends JPanel {
    public RankController cBoardController;
    public int max;
    public JLabel title;
    public JPanel titlePanel;
    public JButton[][] chessButtons;
    public JPanel chessBContainer;
    public JPanel chessBoard;
    public JButton backButton;
    public JButton resetButton;
    public JButton rankStartBtn;
    public JPanel timerPanel;
    public JLabel timerLabel;
    public JPanel timerControllerPane;
    public JButton pauseTimerButton;
    public JDialog wonDialog;
    public JLabel wonLabel;
    public JLabel scoreLabel;
    public JTextField nameField;
    public JComboBox<String> cBC_colorBox;
    public JComboBox<String> cIcon_iconBox;
    public ImageIcon pieceIcon;
    public Color boardBGColor = MyColor.DARK_BLUE_CTNER;
    public Color boardBtnColor = MyColor.DARK_BLUE;
    private ImageIcon[] pieceIcons = { MyIcon.QUEEN, MyIcon.CHEEMS, MyIcon.GANYU, MyIcon.SUN };
    private Color[] boardColors = { MyColor.DARK_BLUE, MyColor.BROWN, MyColor.BLACK, MyColor.DARK_GREEN };
    private Color[] boardBackgroudColors = { MyColor.DARK_BLUE_CTNER, MyColor.BROWN_CTNER, MyColor.BLACK_CTNER,
            MyColor.DARK_GREEN_CTNER };

    public RankPage(int max) {
        this.max = max;
        pieceIcon = MyIcon.QUEEN;
        cBoardController = new RankController(this);

        // cBController = new ChessBoardController(this);
        chessButtons = new JButton[max][max];

        // Main chess board
        this.chessBoard = setChessBoard();

        // Group of sidebar buttons at top
        resetButton = new JButton();
        resetButton.setText("Reset Board");
        resetButton.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        resetButton.setBackground(Color.YELLOW);
        resetButton.setPreferredSize(new Dimension(280, 50));
        resetButton.setFocusable(false);
        resetButton.setName("resetBoard");
        resetButton.addActionListener(cBoardController);

        // Welcome pannel for rank mode
        JLabel rank_Title = new JLabel("Welcome to Rank mode!");
        rank_Title.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        rank_Title.setHorizontalAlignment(JLabel.CENTER);

        JLabel rank_Explain = new JLabel("Solve this board in 1 minute.");
        rank_Explain.setHorizontalAlignment(JLabel.CENTER);
        rank_Explain.setFont(new Font("Comic Sans MS", Font.BOLD, 18));

        rankStartBtn = new JButton("Start");
        rankStartBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        rankStartBtn.setBackground(MyColor.GREEN);
        rankStartBtn.setFocusable(false);
        rankStartBtn.setPreferredSize(new Dimension(150, 50));
        rankStartBtn.setName("rankStart");
        rankStartBtn.addActionListener(cBoardController);

        // Timer countdown
        timerLabel = new JLabel("60s");
        timerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
        timerLabel.setVisible(false);

        timerPanel = new JPanel();
        timerPanel.setPreferredSize(new Dimension(280, 70));
        timerPanel.setLayout(new GridBagLayout());
        timerPanel.setOpaque(false);
        timerPanel.add(rankStartBtn, new GridBagConstraints());
        timerPanel.add(timerLabel, new GridBagConstraints());
        // End of Timer countdown

        // Timer controllers
        pauseTimerButton = new JButton("Pause");
        pauseTimerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        pauseTimerButton.setFocusable(false);
        pauseTimerButton.setName("pauseTimer");
        pauseTimerButton.addActionListener(cBoardController);

        JButton stopTimerButton = new JButton("Quit");
        stopTimerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        stopTimerButton.setFocusable(false);
        stopTimerButton.setName("quitTimer");
        stopTimerButton.addActionListener(cBoardController);

        timerControllerPane = new JPanel();
        timerControllerPane.setPreferredSize(new Dimension(200, 40));
        timerControllerPane.setLayout(new GridLayout(0, 2));
        timerControllerPane.setVisible(false);
        timerControllerPane.add(pauseTimerButton);
        timerControllerPane.add(stopTimerButton);

        // Timer controllers

        JPanel rankPanel = new JPanel();
        rankPanel.setPreferredSize(new Dimension(280, 200));
        rankPanel.setBackground(Color.LIGHT_GRAY);
        rankPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        rankPanel.add(rank_Title);
        rankPanel.add(rank_Explain);
        rankPanel.add(timerPanel);
        rankPanel.add(timerControllerPane);

        // End of welcome pannel for rank mode

        // End of Group of sidebar buttons at top

        JPanel topButtonsGroup = new JPanel();
        topButtonsGroup.setLayout(new FlowLayout(FlowLayout.CENTER));
        topButtonsGroup.setBackground(MyColor.DARK_BLUE);
        topButtonsGroup.setPreferredSize(new Dimension(300, 600));

        topButtonsGroup.add(rankPanel);
        topButtonsGroup.add(resetButton);

        // Group of sidebar buttons at bottom
        JPanel bottomButtonsGroup = new JPanel();
        bottomButtonsGroup.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomButtonsGroup.setBackground(MyColor.DARK_BLUE);
        bottomButtonsGroup.setPreferredSize(new Dimension(300, 210));

        // Button change board color
        JLabel cBC_title = new JLabel("Board Color: ");
        cBC_title.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        cBC_title.setPreferredSize(new Dimension(130, 30));
        cBC_title.setHorizontalAlignment(JLabel.CENTER);

        String[] cBC_options = { "Blue", "Brown", "Black", "Green" };

        cBC_colorBox = new JComboBox<>(cBC_options);
        cBC_colorBox.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        cBC_colorBox.setSelectedIndex(0);
        cBC_colorBox.setPreferredSize(new Dimension(110, 30));
        cBC_colorBox.setName("changeColor");
        cBC_colorBox.addActionListener(e -> {
            int option = this.cBC_colorBox.getSelectedIndex();
            boardBtnColor = boardColors[option];
            boardBGColor = boardBackgroudColors[option];
            setBoardColor(option);
        });

        JPanel cBC_colorPanel = new JPanel();
        cBC_colorPanel.setBackground(MyColor.BLUE);
        cBC_colorPanel.setPreferredSize(new Dimension(280, 40));
        cBC_colorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        cBC_colorPanel.add(cBC_title);
        cBC_colorPanel.add(cBC_colorBox);
        // End of Button change board color

        // Button change piece icon
        JLabel cIcon_title = new JLabel("Piece Icon: ");
        cIcon_title.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        cIcon_title.setPreferredSize(new Dimension(120, 30));
        cIcon_title.setHorizontalAlignment(JLabel.CENTER);

        String[] cIcon_options = { "Queen", "Chemes", "Ganyu", "Sun" };

        cIcon_iconBox = new JComboBox<>(cIcon_options);
        cIcon_iconBox.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
        cIcon_iconBox.setSelectedIndex(0);
        cIcon_iconBox.setPreferredSize(new Dimension(120, 30));
        cIcon_iconBox.setName("changeColor");
        cIcon_iconBox.addActionListener(e -> {
            int option = this.cIcon_iconBox.getSelectedIndex();
            pieceIcon = pieceIcons[option];
            changePieceIcon(option);
        });

        JPanel cIcon_Panel = new JPanel();
        cIcon_Panel.setBackground(MyColor.BLUE);
        cIcon_Panel.setPreferredSize(new Dimension(280, 40));
        cIcon_Panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        cIcon_Panel.add(cIcon_title);
        cIcon_Panel.add(cIcon_iconBox);
        // End of Button change piece icon

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

        bottomButtonsGroup.add(cBC_colorPanel);
        bottomButtonsGroup.add(cIcon_Panel);
        bottomButtonsGroup.add(backButton);
        bottomButtonsGroup.add(quitButton);

        // Sidebar
        JPanel sideBar = new JPanel();
        sideBar.setBackground(MyColor.DARK_BLUE);
        sideBar.setLayout(new BorderLayout());
        sideBar.setPreferredSize(new Dimension(300, 900));

        sideBar.add(topButtonsGroup, BorderLayout.NORTH);
        sideBar.add(bottomButtonsGroup, BorderLayout.SOUTH);
        // End of Sidebar

        // Won Dialog and store use info
        wonDialog = new JDialog();
        wonDialog.setTitle("Save your record");
        wonDialog.setSize(new Dimension(700, 420));
        wonDialog.setLocationRelativeTo(null);
        wonDialog.setResizable(false);
        wonDialog.setLayout(new BorderLayout());
        wonDialog.setBackground(MyColor.BLACK_CTNER);

        // Dialog top, title and congratulation
        JLabel congrat = new JLabel("Congratulation!");
        congrat.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        congrat.setPreferredSize(new Dimension(700, 80));
        congrat.setForeground(MyColor.GREEN);
        congrat.setHorizontalAlignment(JLabel.CENTER);

        wonLabel = new JLabel();
        wonLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        wonLabel.setPreferredSize(new Dimension(700, 45));
        wonLabel.setHorizontalAlignment(JLabel.CENTER);

        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        scoreLabel.setPreferredSize(new Dimension(700, 45));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel dialogTitlePanel = new JPanel();
        dialogTitlePanel.setLayout(new FlowLayout());
        dialogTitlePanel.setPreferredSize(new Dimension(700, 180));
        dialogTitlePanel.add(congrat);
        dialogTitlePanel.add(wonLabel);
        dialogTitlePanel.add(scoreLabel);
        // End of Dialog top, title and congratulation

        // Dialog middle form for storing name
        JLabel forminfoLabel = new JLabel("Enter your name below to save your record");
        forminfoLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        forminfoLabel.setBorder(new EmptyBorder(0, 40, 0, 0));
        forminfoLabel.setPreferredSize(new Dimension(700, 50));
        forminfoLabel.setHorizontalAlignment(JLabel.LEADING);

        nameField = new JTextField();
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (nameField.getText().length() >= 20) {
                    e.consume();
                }
            }
        });
        nameField.setPreferredSize(new Dimension(450, 50));
        nameField.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        nameField.setBackground(MyColor.BLACK_CTNER);
        nameField.setCaretColor(MyColor.ORANGE);

        JPanel dialogFormPanel = new JPanel();
        dialogFormPanel.setLayout(new FlowLayout());
        dialogFormPanel.setPreferredSize(new Dimension(700, 90));
        dialogFormPanel.add(forminfoLabel);
        dialogFormPanel.add(nameField);
        // End of Dialog middle form for storing name

        // Dialog bottom contains buttons
        JButton cancelDialog = new JButton("Cancel");
        cancelDialog.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        cancelDialog.setPreferredSize(new Dimension(220, 50));
        cancelDialog.setBackground(MyColor.RED);
        cancelDialog.setFocusable(false);
        cancelDialog.setName("closeWonDialog");
        cancelDialog.addActionListener(cBoardController);

        JButton submitDialog = new JButton("Submit");
        submitDialog.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        submitDialog.setPreferredSize(new Dimension(220, 50));
        submitDialog.setBackground(MyColor.GREEN);
        submitDialog.setFocusable(false);
        submitDialog.setName("submitWonDialog");
        submitDialog.addActionListener(cBoardController);

        JPanel dialogBotButtons = new JPanel();
        FlowLayout dialogBotLayout = new FlowLayout();
        dialogBotLayout.setHgap(30);
        dialogBotButtons.setLayout(dialogBotLayout);
        dialogBotButtons.setPreferredSize(new Dimension(700, 70));

        dialogBotButtons.add(cancelDialog);
        dialogBotButtons.add(submitDialog);
        // End of Dialog bottom contains buttons

        wonDialog.add(dialogTitlePanel, BorderLayout.NORTH);
        wonDialog.add(dialogFormPanel, BorderLayout.CENTER);
        wonDialog.add(dialogBotButtons, BorderLayout.SOUTH);
        wonDialog.setVisible(false);
        // End of Won Dialog and store use info

        /**
         * Main part which contains the chess board
         */

        chessBContainer = new JPanel();
        chessBContainer.setBackground(MyColor.DARK_BLUE_CTNER);
        chessBContainer.setLayout(new GridBagLayout());
        chessBContainer.add(chessBoard, new GridBagConstraints());

        title = new JLabel("Welcome to rank mode");
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);
        title.setAlignmentY(JLabel.CENTER_ALIGNMENT);

        titlePanel = new JPanel();
        titlePanel.add(title);
        titlePanel.setPreferredSize(new Dimension(100, 40));
        titlePanel.setBackground(new Color(1, 20, 40));

        this.setBounds(0, 0, 1250, 1000);
        this.setLayout(new BorderLayout());
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(chessBContainer, BorderLayout.CENTER);
        this.add(sideBar, BorderLayout.EAST);
    }

    private void showQuitDialog(final String message, final String title, int fontSize) {
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
        theDialog.setModal(true);
        theDialog.setVisible(true);
    }

    JPanel setChessBoard() {
        JPanel chessBoard = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.WHITE, 3);
        chessBoard.setPreferredSize(new Dimension(max * 90 + 6, max * 90 + 6));
        chessBoard.setLayout(new GridLayout(max, max));
        chessBoard.setBorder(border);
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                chessButtons[i][j] = new JButton();
                if ((i + j) % 2 == 0) {
                    chessButtons[i][j].setBackground(Color.white);
                } else {
                    chessButtons[i][j].setBackground(boardBtnColor);
                }
                chessButtons[i][j].addActionListener(cBoardController);
                chessButtons[i][j].setFocusable(false);
                chessButtons[i][j].setBorder(BorderFactory.createEmptyBorder());
                chessButtons[i][j].setName(null);
                chessButtons[i][j].setRolloverEnabled(false);
                chessBoard.add(chessButtons[i][j]);
            }
        }
        return chessBoard;
    }

    private synchronized void setBoardColor(int num) {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if ((i + j) % 2 == 0) {
                    this.chessButtons[i][j].setBackground(Color.WHITE);
                } else {
                    this.chessButtons[i][j].setBackground(boardColors[num]);
                }
            }
        }
        chessBContainer.setBackground(boardBackgroudColors[num]);
        cBoardController.validateChessBoard();
    }

    private synchronized void changePieceIcon(int num) {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (chessButtons[i][j].getIcon() != null) {
                    chessButtons[i][j].setIcon(pieceIcons[num]);
                }
            }
        }
        cBoardController.validateChessBoard();
    }
}
