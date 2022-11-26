package com.everett.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.everett.controllers.MyColor;
import com.everett.controllers.MyIcon;
import com.everett.controllers.SandboxController;

public class SandboxPage extends JPanel {
    public JButton[][] chessButtons;
    private SandboxController cBController;
    public ImageIcon pieceIcon;
    public int max;
    public JLabel title;
    public JPanel titlePanel;
    public JPanel chessBoard;
    public JLabel numRM;
    public JLabel wonMessage;
    public JButton backButton;
    public JButton btnRM;
    public JPanel autoSolvePanel;
    public JLabel aS_remainSolution;
    public JLabel aS_founded;
    public JPanel chessBContainer;
    public JButton btnAutoSolve;
    public JButton btnReset;
    public JButton as_btnExit;
    public JButton resumeAutoSolve;
    public JComboBox<String> cBC_colorBox;
    public JComboBox<String> cIcon_iconBox;
    public JSlider aS_speedSlider;
    public Color boardBGColor = MyColor.DARK_BLUE_CTNER;
    public Color boardBtnColor = MyColor.DARK_BLUE;
    private ImageIcon[] pieceIcons = { MyIcon.QUEEN, MyIcon.CHEEMS, MyIcon.GANYU, MyIcon.SUN };
    private Color[] boardColors = { MyColor.DARK_BLUE, MyColor.BROWN, MyColor.BLACK, MyColor.DARK_GREEN };
    private Color[] boardBackgroudColors = { MyColor.DARK_BLUE_CTNER, MyColor.BROWN_CTNER, MyColor.BLACK_CTNER,
            MyColor.DARK_GREEN_CTNER };

    public SandboxPage(int max) {
        this.max = max;
        pieceIcon = MyIcon.QUEEN;
        cBController = new SandboxController(this);
        chessButtons = new JButton[max][max];

        // Main chess board
        this.chessBoard = setChessBoard();

        // Side bar contains settings
        JPanel sideBar = new JPanel();
        sideBar.setBackground(MyColor.DARK_BLUE);
        sideBar.setLayout(new BorderLayout());
        sideBar.setPreferredSize(new Dimension(300, 900));

        JPanel topButtonsGroup = new JPanel();
        topButtonsGroup.setLayout(new FlowLayout(FlowLayout.CENTER));
        topButtonsGroup.setBackground(MyColor.DARK_BLUE);
        topButtonsGroup.setPreferredSize(new Dimension(300, 600));

        // Show valid board left
        btnRM = new JButton();
        btnRM.setMargin(new Insets(5, 10, 5, 10));
        btnRM.setText("Show remain solution(s)");
        btnRM.setPreferredSize(new Dimension(280, 50));
        btnRM.setHorizontalAlignment(JButton.CENTER);
        btnRM.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        btnRM.setBackground(MyColor.CYAN);
        btnRM.setFocusable(false);
        btnRM.setName("showRM");
        btnRM.addActionListener(cBController);

        numRM = new JLabel("", SwingConstants.CENTER);
        numRM.setPreferredSize(new Dimension(280, 70));
        numRM.setFont(new Font("Comic Sans MS", Font.BOLD, 45));
        numRM.setForeground(Color.WHITE);
        numRM.setVisible(false);

        // End of Show valid board left

        wonMessage = new JLabel("You Won!", SwingConstants.CENTER);
        wonMessage.setPreferredSize(new Dimension(280, 70));
        wonMessage.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        wonMessage.setForeground(MyColor.GREEN);
        wonMessage.setVisible(false);

        btnReset = new JButton();
        btnReset.setText("Reset Board");
        btnReset.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        btnReset.setBackground(Color.YELLOW);
        btnReset.setPreferredSize(new Dimension(280, 50));
        btnReset.setFocusable(false);
        btnReset.setName("resetBoard");
        btnReset.addActionListener(cBController);

        btnAutoSolve = new JButton();
        btnAutoSolve.setText("Auto Solve");
        btnAutoSolve.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        btnAutoSolve.setBackground(MyColor.GREEN);
        btnAutoSolve.setPreferredSize(new Dimension(280, 50));
        btnAutoSolve.setFocusable(false);
        btnAutoSolve.setName("autoSolve");
        btnAutoSolve.addActionListener(cBController);

        // Auto solve Controllers

        JLabel aS_title = createJLabel("Auto Solving Mode", 27, 300, 50);

        aS_remainSolution = createJLabel("", 19, 280, 30);
        aS_remainSolution.setHorizontalAlignment(JLabel.LEFT);

        aS_founded = createJLabel("Founded solution: 0", 19, 280, 30);
        aS_founded.setHorizontalAlignment(JLabel.LEFT);

        JLabel aS_speedLabel = createJLabel("Solving Speed", 22, 280, 40);

        aS_speedSlider = new JSlider(0, 100, 27);
        aS_speedSlider.setPreferredSize(new Dimension(250, 60));
        aS_speedSlider.setPaintTicks(true);
        aS_speedSlider.setPaintTrack(true);
        aS_speedSlider.setMinorTickSpacing(5);
        aS_speedSlider.setMajorTickSpacing(25);
        aS_speedSlider.setPaintLabels(true);
        aS_speedSlider.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        aS_speedSlider.addChangeListener(e -> {
            int speedValue = aS_speedSlider.getValue();
            cBController.solveSpeed = (speedValue * 10);
        });
        aS_speedSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                double percent = p.x / ((double) aS_speedSlider.getWidth());
                int newVal = (int) Math.ceil(100 * percent);
                aS_speedSlider.setValue(newVal);
            }
        });
        resumeAutoSolve = new JButton();
        resumeAutoSolve.setText("Resume");
        resumeAutoSolve.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        resumeAutoSolve.setBackground(MyColor.GREEN);
        resumeAutoSolve.setPreferredSize(new Dimension(130, 40));
        resumeAutoSolve.setFocusable(false);
        resumeAutoSolve.setName("resumeAutoSolve");
        resumeAutoSolve.setEnabled(false);
        resumeAutoSolve.addActionListener(cBController);

        as_btnExit = new JButton();
        as_btnExit.setText("Exit");
        as_btnExit.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        as_btnExit.setBackground(MyColor.ORANGE);
        as_btnExit.setPreferredSize(new Dimension(130, 40));
        as_btnExit.setFocusable(false);
        as_btnExit.setName("exitAS");
        as_btnExit.addActionListener(cBController);

        JPanel aS_btnPanel = new JPanel();
        aS_btnPanel.setPreferredSize(new Dimension(280, 50));
        aS_btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        aS_btnPanel.add(resumeAutoSolve);
        aS_btnPanel.add(as_btnExit);

        autoSolvePanel = new JPanel();
        autoSolvePanel.setLayout(new FlowLayout());
        autoSolvePanel.setPreferredSize(new Dimension(300, 300));
        autoSolvePanel.setVisible(false);

        autoSolvePanel.add(aS_title);
        autoSolvePanel.add(aS_remainSolution);
        autoSolvePanel.add(aS_founded);
        autoSolvePanel.add(aS_speedLabel);
        autoSolvePanel.add(aS_speedSlider);
        autoSolvePanel.add(aS_btnPanel);
        // End of auto solve pannel

        topButtonsGroup.add(btnReset);
        topButtonsGroup.add(btnAutoSolve);
        topButtonsGroup.add(btnRM);
        topButtonsGroup.add(numRM);
        topButtonsGroup.add(wonMessage);
        topButtonsGroup.add(autoSolvePanel);

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

        backButton = new JButton();
        backButton.setText("Back home");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        backButton.setBackground(MyColor.ORANGE);
        backButton.setPreferredSize(new Dimension(280, 50));
        backButton.setFocusable(false);
        backButton.setName("backHome");

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

        bottomButtonsGroup.add(cBC_colorPanel);
        bottomButtonsGroup.add(cIcon_Panel);
        bottomButtonsGroup.add(backButton);
        bottomButtonsGroup.add(quitButton);

        sideBar.add(topButtonsGroup, BorderLayout.NORTH);
        sideBar.add(bottomButtonsGroup, BorderLayout.SOUTH);

        /**
         * Main part which contains the chess board
         */

        chessBContainer = new JPanel();
        chessBContainer.setBackground(MyColor.DARK_BLUE_CTNER);
        chessBContainer.setLayout(new GridBagLayout());
        chessBContainer.add(chessBoard, new GridBagConstraints());

        title = new JLabel("Welcome to N Queens Game, click any square to start");
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        title.setForeground(Color.WHITE);
        title.setAlignmentY(JLabel.CENTER_ALIGNMENT);

        titlePanel = new JPanel();
        titlePanel.add(title);
        titlePanel.setPreferredSize(new Dimension(100, 40));
        titlePanel.setBackground(MyColor.TOP_TITLE);

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
        theDialog.setModal(true);
        theDialog.setVisible(true);
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
        cBController.validateChessBoard();

    }

    private synchronized void changePieceIcon(int num) {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (chessButtons[i][j].getIcon() != null) {
                    chessButtons[i][j].setIcon(pieceIcons[num]);
                }
            }
        }
        cBController.validateChessBoard();
    }

    private JLabel createJLabel(String text, int fontSize, int dimWidth, int dimHeight) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
        label.setPreferredSize(new Dimension(dimWidth, dimHeight));
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;

    }

    public JLabel getNumRM() {
        return numRM;
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
                chessButtons[i][j].addActionListener(cBController);
                chessButtons[i][j].setFocusable(false);
                chessButtons[i][j].setBorder(BorderFactory.createEmptyBorder());
                chessButtons[i][j].setName(null);
                chessButtons[i][j].setRolloverEnabled(false);
                chessBoard.add(chessButtons[i][j]);
            }
        }
        return chessBoard;
    }

}
