package com.everett.controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.everett.models.Record;
import com.everett.views.Frame;
import com.everett.views.RankPage;

public class RankController implements ActionListener {
    private int[][] currentCB;
    private RankPage rankPage;
    private int currentPieces;
    private int max;
    private Border border;
    private boolean isStarted = false;
    private int timeLeft = 60;
    private Timer timer = null;
    private int score = 0;
    private boolean postComplete = false;

    public RankController(RankPage rankPage) {
        this.rankPage = rankPage;
        this.max = rankPage.max;
        this.currentCB = new int[max][max];
        this.currentPieces = 0;
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                this.currentCB[i][j] = 0;
            }
        }
        border = BorderFactory.createLineBorder(Color.BLACK, 1);
    }

    private void resetBoard() {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                this.currentCB[i][j] = 0;
                this.currentPieces = 0;
                this.rankPage.chessButtons[i][j].setIcon(null);
                if ((i + j) % 2 == 0) {
                    this.rankPage.chessButtons[i][j].setBackground(Color.WHITE);
                } else {
                    this.rankPage.chessButtons[i][j].setBackground(rankPage.boardBtnColor);
                }
            }
        }
    }

    private boolean isConflict(int[][] board, int row, int col) {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (board[i][j] != 0) {
                    if (row == i && col == j) {
                        continue;
                    }
                    if (row == i || col == j || Math.abs(i - row) == Math.abs(col - j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private synchronized void setWonPosition() {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (currentCB[i][j] != 0) {
                    rankPage.chessButtons[i][j].setBackground(MyColor.GREEN);
                    rankPage.chessButtons[i][j].setBorder(border);

                }
            }

        }
        rankPage.wonLabel.setText("You have solved this chess board in " + (60 - timeLeft) + " seconds");
        int scoreSalt = (int) (Math.random() * 27);
        score = timeLeft * 272 + scoreSalt;
        rankPage.scoreLabel.setText("Your score is: " + score);
        rankPage.wonDialog.setVisible(true);
    }

    public boolean validateChessBoard() {
        boolean flag = true;
        currentPieces = 0;
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (currentCB[i][j] != 0) {
                    currentPieces++;
                    if (isConflict(currentCB, i, j)) {
                        rankPage.chessButtons[i][j].setBackground(MyColor.RED);
                        rankPage.chessButtons[i][j].setBorder(border);
                        flag = false;
                    } else {
                        if ((i + j) % 2 == 0) {
                            rankPage.chessButtons[i][j].setBackground(Color.WHITE);
                        } else {
                            rankPage.chessButtons[i][j].setBackground(rankPage.boardBtnColor);
                        }
                        rankPage.chessButtons[i][j].setBorder(BorderFactory.createEmptyBorder());
                    }
                }
            }
        }
        if (flag) {
            if (currentPieces == 0) {
                rankPage.title.setText("Click any square to start");
                rankPage.titlePanel.setBackground(MyColor.TOP_TITLE);
            } else if (currentPieces == max - 1) {
                rankPage.title.setText("The last piece to your glory victory!");
                rankPage.titlePanel.setBackground(MyColor.TOP_TITLE);
            } else if (currentPieces == max) {
                rankPage.title.setText("Congratulation!! You are on fire.");
                rankPage.titlePanel.setBackground(new Color(0, 200, 0));
            } else {
                rankPage.title.setText((max - currentPieces) + " queens to go, come on.");
                rankPage.titlePanel.setBackground(MyColor.TOP_TITLE);
            }
        } else {
            rankPage.title.setText("Some queens are under attack!!");
            rankPage.titlePanel.setBackground(new Color(235, 17, 27));
        }
        if (flag && currentPieces == max) {
            setWonPosition();
        }
        return flag;
    }

    private void boardAccessHandler(int i, int j) {
        if (!postComplete) {
            if (currentCB[i][j] == 0 && currentPieces < max) {
                rankPage.chessButtons[i][j].setIcon(rankPage.pieceIcon);
                currentCB[i][j] = 1;
                currentPieces++;
            } else {
                rankPage.chessButtons[i][j].setIcon(null);
                currentCB[i][j] = 0;
                if ((i + j) % 2 == 0) {
                    rankPage.chessButtons[i][j].setBackground(Color.WHITE);
                } else {
                    rankPage.chessButtons[i][j].setBackground(rankPage.boardBtnColor);
                }
                currentPieces--;
            }
            if (validateChessBoard() && currentPieces == max) {
                timer.stop();
            }
        }

    }

    private void showNotifyMessage(final String message, final String title) {
        JDialog theDialog = new JDialog();
        theDialog.setTitle(title);
        theDialog.setSize(new Dimension(500, 200));
        theDialog.setLocationRelativeTo(null);
        theDialog.setResizable(false);
        theDialog.setLayout(new BorderLayout());

        JLabel bodyLabel = new JLabel(message);
        bodyLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 26));

        JPanel bodyPanel = new JPanel();
        bodyPanel.setPreferredSize(new Dimension(400, 130));
        bodyPanel.setLayout(new GridBagLayout());
        bodyPanel.add(bodyLabel, new GridBagConstraints());

        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(250, 40));
        okButton.setFont(new Font("Comic Sans MS", Font.BOLD, 23));
        okButton.setBackground(MyColor.GREEN);
        okButton.setFocusable(false);
        okButton.addActionListener(e -> {
            theDialog.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 70));
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(okButton, new GridBagConstraints());

        theDialog.add(bodyPanel, BorderLayout.CENTER);
        theDialog.add(buttonPanel, BorderLayout.SOUTH);
        theDialog.setModal(true);
        theDialog.setVisible(true);
    }

    private void showResetDialog(final String message, final String title, int fontSize) {
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
            resetBoard();
            rankPage.title.setText("Click any square to start");
            rankPage.titlePanel.setBackground(MyColor.TOP_TITLE);
            isStarted = false;
            timer.stop();
            rankPage.rankStartBtn.setVisible(true);
            rankPage.timerLabel.setText("60s");
            rankPage.timerLabel.setVisible(false);
            rankPage.timerControllerPane.setVisible(false);
            timeLeft = 60;
            rankPage.pauseTimerButton.setText("Pause");
            rankPage.pauseTimerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            rankPage.pauseTimerButton.setName("pauseTimer");
            theDialog.dispose();
            postComplete = false;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (e.getSource() == rankPage.chessButtons[i][j]) {
                    if (isStarted) {
                        boardAccessHandler(i, j);
                    } else {
                        rankPage.title.setText("Hit the start button to play");
                    }
                }

            }
        }
        JButton thisButton = (JButton) e.getSource();
        String name = thisButton.getName();
        if ((name != "" || name != null) && name == "resetBoard") {
            showResetDialog("Do you want to reset this board?", "Reset Board", 25);
        }
        if ((name != "" || name != null) && name == "rankStart") {
            isStarted = true;
            rankPage.rankStartBtn.setVisible(false);
            rankPage.timerLabel.setVisible(true);
            rankPage.timerControllerPane.setVisible(true);
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeLeft--;
                    if (timeLeft < 0) {
                        showResetDialog("Time out! Do you want to try again?", "Time out", 23);
                        isStarted = false;
                        timer.stop();
                    } else {
                        rankPage.timerLabel.setText(timeLeft + "s");
                    }
                }
            });
            timer.start();
        }

        if ((name != "" || name != null) && name == "pauseTimer") {
            isStarted = false;
            timer.stop();
            thisButton.setText("Resume");
            thisButton.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
            thisButton.setName("resumeTimer");
        }
        if ((name != "" || name != null) && name == "resumeTimer") {
            isStarted = true;
            timer.start();
            thisButton.setText("Pause");
            thisButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            thisButton.setName("pauseTimer");
        }
        if ((name != "" || name != null) && name == "quitTimer") {
            isStarted = false;
            timer.stop();
            rankPage.resetButton.doClick();
        }
        if ((name != "" || name != null) && name == "closeWonDialog") {
            rankPage.wonDialog.dispose();
        }
        if ((name != "" || name != null) && name == "submitWonDialog") {
            String playerName = rankPage.nameField.getText();
            if (rankPage.nameField.getText().equals("")) {
                showNotifyMessage("Please enter your name", "Missing name");
            } else {
                EntityManager em = null;
                EntityTransaction et = null;
                try {
                    em = Frame.ENTITY.createEntityManager();
                    et = em.getTransaction();
                } catch (Exception ex) {
                    System.err.println("Can not create save record");
                    return;
                }

                try {
                    et.begin();
                    int solveTime = (60 - timeLeft);
                    Record record = new Record(playerName, score, solveTime);
                    em.persist(record);
                    et.commit();
                    rankPage.wonDialog.dispose();
                    showNotifyMessage("Your record has been saved", "Save record");
                    postComplete = true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    et.rollback();
                }
            }

        }
    }

}
