package dev.maxkatz.queenssolver;

import lombok.Getter;   // Use lombok to support @Getter annotations for testing purposes

/**
 * This class represents an n-by-n chessboard, and provides utilities
 * for placing or removing queens and for analyzing the current
 * state of the board.
 * 
 * @author Max Katz
 */
public class Board {
    /**
     * Width / height of the board
     */
    public int size;
    
    /**
     * Array of booleans indicating the current state of the board: e.g. is the
     * row indicated by a given index into this array occupied by a queen?
     */
    @Getter
    private boolean[] rows;
    
    /**
     * Array of booleans indicating the current state of the columns of the
     * board
     */
    @Getter
    private boolean[] cols;
    
    /**
     * Array of main diagonal intercepts.  In matrix math, the main diagonal
     * runs from the upper-left to lower-right corner like so: ↘
     */
    @Getter
    private boolean[] mains;
    
    /**
     * Again, in matrix math the anti diagonal runs from upper-right to
     * lower-left corner like so: ↙
     */
    @Getter
    private boolean[] antis;
    
    /**
     * Array storing the coordinates of each Queen placed on the board
     */
    public Coords[] queens;
    
    /**
     * Set size and initialize properties
     * 
     * @param size The size of the board to be created
     */
    public Board(int size) {
        // Set size and initialize properties
        this.size = size;
        this.rows = new boolean[size];
        this.cols = new boolean[size];
        this.mains = new boolean[2 * size - 1];
        this.antis = new boolean[2 * size - 1];
        this.queens = new Coords[size];
        for(int i = 0; i < size; i++) {
            this.rows[i] = false;
            this.cols[i] = false;
            this.queens[i] = null;
        }
        for(int i = 0; i < 2 * size - 1; i++) {
            this.mains[i] = false;
            this.antis[i] = false;
        }
    }
    
    /**
     * Return the count of queens placed on this board
     * 
     * @return integer count of queens on the board
     */
    public int queenCount() {
        int count = 0;
        for(Coords c : this.queens) {
            if(c != null) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Place a queen on the board
     * 
     * @param square The coordinates at which to place this queen
     */
    public void placeQueen(Coords square) {
        int idx = this.getLastQueenIndex();
        idx++;
        this.queens[idx] = square;
        this.rows[square.x] = true;
        this.cols[square.y] = true;
        this.mains[this.getMainDiagIdx(square)] = true;
        this.antis[this.getAntiDiagIdx(square)] = true;
    }
    
    /**
     * Internal function for mapping the main diagonal to its
     * x-intercept.
     * 
     * Combining the main diagonal, anti-diagonal, row and column
     * allow us to uniquely identify all positions to which it may
     * legally move.
     * 
     * @param c The coordinates representing a square on the board
     * for which to calculate the main diagonal.
     * 
     * @return Integer representing an index in the range [0, n - 1]
     * into the mains[] array.
     */
    private int getMainDiagIdx(Coords c) {
        return c.x + c.y;
    }
    
    /**
     * Internal function for mapping the anti-diagonal to its
     * x-intercept.
     * 
     * Again, like getmainDiagIdx() this allows us to identify all
     * positions to which a queen placed at the given coordinates
     * may move.
     * 
     * @param c The coordinates of the square whose diagonal we are
     * evaluating.
     * 
     * @return Integer representing an index in the range [0, n - 1]
     * into the antis[] array.
     */
    private int getAntiDiagIdx(Coords c) {
        return c.x - c.y + this.size - 1;           // We add (this.size - 1) to transform this into an array index in
                                                    // the range [0, n - 1]
    }
    
    /**
     * Returns the index into the queens[] array of the last queen placed there.
     * 
     * @return Integer in the range [0, n] or -1 if no queens are present.
     */
    public int getLastQueenIndex() {
        for(int i = this.size - 1; i >= 0; i--) {
            if(this.queens[i] != null) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Get a Coords object representing the last queen placed on the board.
     * 
     * @return An object bearing the x and y coordinates of the last-placed
     * queen on the board, or null if no queens are on the board.
     */
    public Coords getLastQueen() {
        int lastQueenIndex = this.getLastQueenIndex();
        if(lastQueenIndex == -1) {
            return null;
        }
        return this.queens[lastQueenIndex];
    }
    
    /**
     * Determine whether a queen may be placed at the given coordinates
     * 
     * @return boolean
     */
    public boolean testValidPosition(Coords position) {
        return !(
                this.rows[position.x]
                | this.cols[position.y]
                | this.mains[position.x + position.y]
                | this.antis[position.x - position.y + this.size - 1]
        );
    }
    
    /**
     * Produce a string representation of the board.
     * 
     * @return Information about the board and a graphical representation of
     * its contents.
     */
    public String toString() {
        StringBuilder board = new StringBuilder();      // Representation of the board as a string
        // Iterate over all cells, checking against any placed queens
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                if(j > 0) {                             // Prepend a space if this isn't the first character to prettify
                    board.append(" ");
                }
                boolean queenPresent = false;
                for(Coords c : this.queens) {
                    if(c == null) {
                        continue;
                    }
                    Coords boardSquare = new Coords(j, i);
                    if(boardSquare.equals(c)) {
                        queenPresent = true;
                        break;
                    }
                }
                board.append(queenPresent ? "Q" : "."); // If the cell being inspected has a queen present, print a "Q".
                                                        // Otherwise "."
                if(j == this.size - 1) {
                    board.append("\n");
                }
            }
        }
        return board.toString();
    }
}

