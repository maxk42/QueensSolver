/*
 * Defines an object for storing coordinates on the chessboard
 */
package dev.maxkatz.queenssolver;

public class Coords {
    /**
     * x coordinate
     */
    public int x;

    /**
     * y coordinate
     */
    public int y;
    
    /**
     * Creates a Coords object from two integers.
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Creates a Coords object from another Coords object.
     * 
     * @param fromCoords Object to be cloned
     */
    public Coords(Coords fromCoords) {
        this.x = fromCoords.x;
        this.y = fromCoords.y;
    }
    
    /**
     * Tests two Coords objects for equality.
     * 
     * @param c Object to compare with
     * @return boolean indicating equality
     */
    public boolean equals(Coords c) {
        return this.x == c.x && this.y == c.y;
    }
}

