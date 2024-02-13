package org.example;
public class CleverXO {
    public static final int DIMENSIONE = 3;
    public static final char VUOTO = '-';
    public static final char GIOCATORE_X = 'X';
    public static final char GIOCATORE_O = 'O';

    private final char[][] tabellone;
    private int ultimaRiga;
    private int ultimaColonna;

    public CleverXO() {
        tabellone = new char[DIMENSIONE][DIMENSIONE];
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                tabellone[i][j] = VUOTO;
            }
        }
    }

    public boolean isFinito() {
        return getVincitore() != VUOTO || isTabellonePieno();
    }

    public boolean isTabellonePieno() {
        for (char[] riga : tabellone) {
            for (char cella : riga) {
                if (cella == VUOTO) {
                    return false;
                }
            }
        }
        return true;
    }

    public char getVincitore() {
        for (int i = 0; i < DIMENSIONE; i++) {
            if (controllaVittoria(i, 0, 0, 1)) {
                return tabellone[i][0];
            }
            if (controllaVittoria(0, i, 1, 0)) {
                return tabellone[0][i];
            }
        }
        if (controllaVittoria(0, 0, 1, 1)) {
            return tabellone[0][0];
        }
        if (controllaVittoria(0, DIMENSIONE - 1, 1, -1)) {
            return tabellone[0][DIMENSIONE - 1];
        }
        return VUOTO;
    }

    private boolean controllaVittoria(int rigaIniziale, int colonnaIniziale, int deltaRiga, int deltaColonna) {
        char simbolo = tabellone[rigaIniziale][colonnaIniziale];
        if (simbolo == VUOTO) {
            return false;
        }
        for (int i = 0; i < DIMENSIONE; i++) {
            int riga = rigaIniziale + i * deltaRiga;
            int colonna = colonnaIniziale + i * deltaColonna;
            if (tabellone[riga][colonna] != simbolo) {
                return false;
            }
        }
        return true;
    }

    public boolean eseguiMossa(int riga, int colonna, char giocatore) {
        if (riga >= 0 && riga < DIMENSIONE && colonna >= 0 && colonna < DIMENSIONE && tabellone[riga][colonna] == VUOTO) {
            tabellone[riga][colonna] = giocatore;
            ultimaRiga = riga;
            ultimaColonna = colonna;
            return true;
        }
        return false;
    }

    public void effettuaMossaAI() {
        int bestScore = Integer.MIN_VALUE;
        int bestMoveRow = -1;
        int bestMoveCol = -1;
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                if (tabellone[i][j] == VUOTO) {
                    tabellone[i][j] = GIOCATORE_X;
                    int score = minimax(tabellone, 0, false);
                    tabellone[i][j] = VUOTO;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMoveRow = i;
                        bestMoveCol = j;
                    }
                }
            }
        }
        eseguiMossa(bestMoveRow, bestMoveCol, GIOCATORE_X);
    }

    private int minimax(char[][] board, int depth, boolean isMaximizing) {
        char result = getVincitore();
        if (result != VUOTO) {
            return result == GIOCATORE_X ? 1 : -1;
        }

        if (isTabellonePieno()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < DIMENSIONE; i++) {
                for (int j = 0; j < DIMENSIONE; j++) {
                    if (board[i][j] == VUOTO) {
                        board[i][j] = GIOCATORE_X;
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = VUOTO;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < DIMENSIONE; i++) {
                for (int j = 0; j < DIMENSIONE; j++) {
                    if (board[i][j] == VUOTO) {
                        board[i][j] = GIOCATORE_O;
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = VUOTO;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public int getUltimaRiga() {
        return ultimaRiga;
    }

    public int getUltimaColonna() {
        return ultimaColonna;
    }
}