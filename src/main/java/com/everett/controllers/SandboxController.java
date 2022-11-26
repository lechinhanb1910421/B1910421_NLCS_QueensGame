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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.everett.views.SandboxPage;

public class SandboxController implements ActionListener {
    private int[][] currentCB;
    private int[][] copyCB;
    private SandboxPage sandboxPage;
    private int currentPieces;
    private int max;
    private int movesLeft = 0;
    private boolean flag;
    private Border border;
    private boolean onAuto = false;
    public int solveSpeed = 270;
    private int solutionFounded = 0;
    private Thread autoSolveThread;

    public SandboxController(SandboxPage sandboxPage) {
        this.sandboxPage = sandboxPage;
        this.currentCB = new int[sandboxPage.max][sandboxPage.max];
        this.copyCB = new int[sandboxPage.max][sandboxPage.max];
        this.currentPieces = 0;
        this.max = sandboxPage.max;
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                this.currentCB[i][j] = 0;
                this.copyCB[i][j] = 0;
            }
        }
        border = BorderFactory.createLineBorder(Color.BLACK, 1);
    }

    private void resetBoard() {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                this.currentCB[i][j] = 0;
                this.copyCB[i][j] = 0;
                this.currentPieces = 0;
                this.sandboxPage.chessButtons[i][j].setIcon(null);
                if ((i + j) % 2 == 0) {
                    this.sandboxPage.chessButtons[i][j].setBackground(Color.WHITE);
                } else {
                    this.sandboxPage.chessButtons[i][j].setBackground(sandboxPage.boardBtnColor);
                }
            }
        }
        sandboxPage.title.setText("Click any square to start");
        sandboxPage.titlePanel.setBackground(MyColor.TOP_TITLE);
        countMoves();
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

    public synchronized boolean validateChessBoard() {
        currentPieces = 0;
        flag = true;
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (currentCB[i][j] != 0) {
                    currentPieces++;
                    if (isConflict(currentCB, i, j)) {
                        sandboxPage.chessButtons[i][j].setBackground(MyColor.RED);
                        sandboxPage.chessButtons[i][j].setBorder(border);
                        flag = false;
                    } else {
                        if ((i + j) % 2 == 0) {
                            sandboxPage.chessButtons[i][j].setBackground(Color.WHITE);
                        } else {
                            sandboxPage.chessButtons[i][j].setBackground(sandboxPage.boardBtnColor);
                        }
                        sandboxPage.chessButtons[i][j].setBorder(BorderFactory.createEmptyBorder());
                    }
                }
            }
        }
        if (!onAuto) {
            if (flag) {
                if (currentPieces == 0) {
                    sandboxPage.title.setText("Click any square to start");
                    sandboxPage.titlePanel.setBackground(MyColor.TOP_TITLE);
                } else if (currentPieces == max - 1) {
                    sandboxPage.title.setText("The last piece to your glory victory!");
                    sandboxPage.titlePanel.setBackground(MyColor.TOP_TITLE);
                } else if (currentPieces == max) {
                    sandboxPage.title.setText("Congratulations!! You are on fire.");
                    sandboxPage.titlePanel.setBackground(new Color(0, 200, 0));
                } else {
                    sandboxPage.title.setText((max - currentPieces) + " queens to go, come on.");
                    sandboxPage.titlePanel.setBackground(MyColor.TOP_TITLE);
                }
            } else {
                sandboxPage.title.setText("Some queens are under attack!!");
                sandboxPage.titlePanel.setBackground(new Color(235, 17, 27));
            }
        } else {
            sandboxPage.title.setText("Auto mode is on");
            sandboxPage.titlePanel.setBackground(MyColor.TOP_TITLE);
        }
        if (flag && currentPieces == max) {
            setWonPosition();

        }
        return flag;
    }

    private void autoSolve(int[][] copyCB, int N) {
        if (N == max) {
            setWonPosition();
            solutionFounded++;
            if (solutionFounded < 2) {
                sandboxPage.aS_founded.setText("Founded solution: " + solutionFounded);
            } else {
                sandboxPage.aS_founded.setText("Founded solutions: " + solutionFounded);
            }
            sandboxPage.resumeAutoSolve.setEnabled(true);
            while (!sandboxPage.resumeAutoSolve.getModel().isPressed()) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    setCopyBoard(copyCB, currentCB);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            return;
        }
        for (int i = 0; i < max; i++) {
            if (copyCB[N][i] != 0) {
                autoSolve(copyCB, N + 1);
                return;
            }
        }
        for (int i = 0; i < max; i++) {
            copyCB[N][i] = 1;
            currentCB[N][i] = 1;
            sandboxPage.chessButtons[N][i].setIcon(sandboxPage.pieceIcon);
            validateChessBoard();
            try {
                Thread.sleep(1007 - solveSpeed);
            } catch (InterruptedException e1) {
                setCopyBoard(copyCB, currentCB);
                Thread.currentThread().interrupt();
                break;
            }
            if (!isConflict(copyCB, N, i)) {
                autoSolve(copyCB, N + 1);
            }
            if (Thread.currentThread().isInterrupted()) {
                setCopyBoard(copyCB, currentCB);
                Thread.currentThread().interrupt();
                return;
            }
            copyCB[N][i] = 0;
            currentCB[N][i] = 0;

            sandboxPage.chessButtons[N][i].setIcon(null);
            if ((i + N) % 2 == 0) {
                sandboxPage.chessButtons[N][i].setBackground(Color.WHITE);
            } else {
                sandboxPage.chessButtons[N][i].setBackground(sandboxPage.boardBtnColor);
            }
            sandboxPage.chessButtons[N][i].setBorder(BorderFactory.createEmptyBorder());
            validateChessBoard();
            if (Thread.currentThread().isInterrupted()) {
                setCopyBoard(copyCB, currentCB);
                Thread.currentThread().interrupt();
                return;
            }
        }
        return;
    }

    private void solve(int[][] copyCB, int N) {
        if (N == max) {
            movesLeft++;
            return;
        }

        for (int i = 0; i < max; i++) {
            if (copyCB[N][i] != 0) {
                solve(copyCB, N + 1);
                return;
            }
        }

        for (int i = 0; i < max; i++) {
            copyCB[N][i] = 1;
            currentCB[N][i] = 1;
            sandboxPage.chessButtons[N][i].setIcon(sandboxPage.pieceIcon);
            if (!isConflict(copyCB, N, i)) {
                solve(copyCB, N + 1);
            }
            copyCB[N][i] = 0;
            currentCB[N][i] = 0;
            sandboxPage.chessButtons[N][i].setIcon(null);

        }
        return;
    }

    private void setCopyBoard(int[][] source, int[][] dest) {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                dest[i][j] = source[i][j];
            }
        }
    }

    void countMoves() {
        movesLeft = 0;
        setCopyBoard(currentCB, copyCB);
        solve(copyCB, 0);
        sandboxPage.getNumRM().setText("" + movesLeft);
    }

    private synchronized void setWonPosition() {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (currentCB[i][j] != 0) {
                    sandboxPage.chessButtons[i][j].setBackground(MyColor.GREEN);
                    sandboxPage.chessButtons[i][j].setBorder(border);

                }
            }
        }
    }

    private void boardAccessHandler(int i, int j) {
        if (!onAuto) {
            if (currentCB[i][j] == 0 && currentPieces < max) {
                sandboxPage.chessButtons[i][j].setIcon(sandboxPage.pieceIcon);
                currentCB[i][j] = 1;
                currentPieces++;
            } else {
                sandboxPage.chessButtons[i][j].setIcon(null);
                currentCB[i][j] = 0;
                if ((i + j) % 2 == 0) {
                    sandboxPage.chessButtons[i][j].setBackground(Color.WHITE);
                } else {
                    sandboxPage.chessButtons[i][j].setBackground(sandboxPage.boardBtnColor);
                }
                currentPieces--;
            }
            if (!validateChessBoard()) {
                movesLeft = 0;
                sandboxPage.getNumRM().setText("" + movesLeft);
            } else {
                countMoves();
                if (currentPieces == max) {
                    sandboxPage.wonMessage.setVisible(true);
                    // setWonPosition();
                } else {
                    sandboxPage.wonMessage.setVisible(false);
                }
            }
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (e.getSource() == sandboxPage.chessButtons[i][j]) {
                    boardAccessHandler(i, j);
                }

            }
        }

        JButton thisButton = (JButton) e.getSource();
        String name = thisButton.getName();
        if ((name != "" || name != null) && name == "resetBoard") {
            showResetDialog("Do you want to reset this board?", "Reset Board", 25);

        }
        if ((name != "" || name != null) && name == "showRM") {
            countMoves();
            sandboxPage.getNumRM().setVisible(true);
            thisButton.setBackground(MyColor.ORANGE);
            thisButton.setText("Hide remain solution(s)");
            thisButton.setName("hideRM");
        }
        if ((name != "" || name != null) && name == "hideRM") {
            sandboxPage.getNumRM().setVisible(false);
            thisButton.setBackground(MyColor.CYAN);
            thisButton.setText("Show remain solution(s)");
            thisButton.setName("showRM");
        }
        if ((name != "" || name != null) && name == "autoSolve") {
            if (!onAuto && validateChessBoard()) {
                onAuto = true;
                countMoves();
                if (movesLeft < 2) {
                    sandboxPage.aS_remainSolution.setText("Total solution: " + movesLeft);
                } else {
                    sandboxPage.aS_remainSolution.setText("Total solutions: " + movesLeft);
                }
                sandboxPage.aS_founded.setText("Founded solution: 0");
                setCopyBoard(currentCB, copyCB);
                solutionFounded = 0;
                autoSolveThread = new Thread(() -> {
                    setCopyBoard(currentCB, copyCB);
                    autoSolve(copyCB, 0);
                    onAuto = false;
                    sandboxPage.as_btnExit.doClick();
                });
                autoSolveThread.start();

                sandboxPage.aS_speedSlider.setValue(27);
                sandboxPage.btnReset.setEnabled(false);
                thisButton.setText("Solving...");
                thisButton.setBackground(MyColor.ORANGE);
                thisButton.setEnabled(false);
                sandboxPage.btnRM.setVisible(false);
                sandboxPage.numRM.setVisible(false);
                sandboxPage.autoSolvePanel.setVisible(true);

            }
        }
        if ((name != "" || name != null) && name == "resumeAutoSolve") {
            thisButton.setEnabled(false);
        }
        if ((name != "" || name != null) && name == "exitAS") {
            // isStoped = true;
            onAuto = false;
            setCopyBoard(copyCB, currentCB);
            autoSolveThread.interrupt();
            sandboxPage.resumeAutoSolve.setEnabled(false);
            sandboxPage.btnRM.setVisible(true);
            sandboxPage.autoSolvePanel.setVisible(false);
            sandboxPage.btnAutoSolve.setText("Auto Solve");
            sandboxPage.btnAutoSolve.setBackground(MyColor.GREEN);
            sandboxPage.btnReset.setEnabled(true);
            sandboxPage.btnAutoSolve.setEnabled(true);
            if (currentPieces == max && validateChessBoard()) {
                sandboxPage.wonMessage.setVisible(true);
                setWonPosition();
            } else {
                sandboxPage.wonMessage.setVisible(false);
            }
            sandboxPage.btnRM.setBackground(MyColor.CYAN);
            sandboxPage.btnRM.setText("Show remain solution(s)");
            sandboxPage.btnRM.setName("showRM");

        }
    }

}
