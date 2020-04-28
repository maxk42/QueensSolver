/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dev.maxkatz.queenssolver;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.math3.util.CombinatoricsUtils;

public class QueensSolver {
    public static void main(String[] args) {
        int size = 8;
        if(args.length > 0) {
            size = Integer.parseInt(args[0]);
        }
        Board board = new Board(size);
        ArrayList<Board> solutions = new ArrayList<Board>();
        solve(board, solutions);
        stripColinearTriplets(solutions);
        System.out.println("Done.  " + (solutions.size() == 0 ? "No" : solutions.size()) + " solutions found.");
        printSolutions(solutions);
        
    }
    
    /**
     * Solve for all n-queens solutions.
     * 
     * I've chosen to go with a non-recursive solution since every example I
     * found online shows only recursive approaches.  It's a little harder to
     * follow but not too complex.
     * 
     * The key insight here is we're either filling the board with new queens
     * until we can't any longer or removing queens and testing whether we
     * need to backtrack further.  The `fill` flag indicates which of these
     * strategies we're about to employ on each iteration through the search
     * space.
     * 
     * @param board The Board object we're solving for
     * @param solutions A reference to ArrayList<Board> for storing solutions
     * 
     */
    public static void solve(Board board, ArrayList<Board> solutions) {
        boolean fill = true;    // flag indicating whether we should be filling or clearing queens
        Coords nextCoords = new Coords(0, 0);   // nextCoords will always contain the next
        Coords tmpCoords = null;                // Used for temporary variables
        while(true) {
            if(fill) {          // Attempt to place more queens
                board.placeQueen(new Coords(nextCoords));   // Clone nextCoords onto the board
                if(board.queenCount() == board.size) {
                    solutions.add((Board)((Board) board).clone());  // Record this solution
                    fill = false;
                } else {
                    nextCoords.y++;
                    tmpCoords = solveRow(board, new Coords(0, nextCoords.y));
                    if(tmpCoords != null) {
                        nextCoords.x = tmpCoords.x;
                    } else {
                        fill = false;
                    }
                }
            } else {            // Attempt to backtrack
                nextCoords = board.clearQueen();
                if(nextCoords == null) {
                    return;
                }
                nextCoords.x++;
                if(nextCoords.x == board.size) {
                    continue;           // Remove another queen
                }
                tmpCoords = solveRow(board, nextCoords);
                if(tmpCoords != null) {
                    nextCoords.x = tmpCoords.x;
                    fill = true;
                }
            }
        }
    }
    
    /**
     * Remove solutions containing any combination of three or more colinear
     * points.
     * 
     * @param solutions List of solutions to the n-queens problem that are to
     * be tested for colinear triplets.
     */
    public static void stripColinearTriplets(ArrayList<Board> solutions) {
        ArrayList<Board> solutionsToRemove = new ArrayList<Board>();
        for(Board solution: solutions) {
            if(solution.queens.size() < 3) {    // Can't have three points in a row if there aren't three points.
                continue;
            }
            Iterator<int[]> tripletIterator = CombinatoricsUtils.combinationsIterator(solution.queens.size(), 3);
            while(tripletIterator.hasNext()) {
                int[] i = tripletIterator.next();
                // Pull out our points and check for identical slopes
                Coords p1 = solution.queens.get(i[0]);
                Coords p2 = solution.queens.get(i[1]);
                Coords p3 = solution.queens.get(i[2]);
                if( (p3.y - p2.y) * (p2.x - p1.x) == (p2.y - p1.y) * (p3.x - p2.x) ) {
                    solutionsToRemove.add(solution);
                }
            }
        }
        solutions.removeAll(solutionsToRemove);
    }
    
    /**
     * Find all solutions on a single row beginning at the coordinates given by
     * startingPoint
     * 
     * @param startingPoint Specifies which row and column to begin searching
     * for a solution on.
     * @return Coords specifying the coordinates of the next solution or null
     * if none exist.
     */
    public static Coords solveRow(Board board, Coords startingPoint) {
        Coords search = new Coords(startingPoint);      // Clone starting point to prevent mutation
        while(!board.testValidPosition(search)) {
            search.x++;
            if(search.x == board.size) {                // No further solutions exist in this row
                return null;
            }
        }
        return search;
    }
    
    public static void clearLinearResults() {
    }
    
    public static void printSolutions(ArrayList<Board> solutions) {
        int len = solutions.size();
        for(int i = 0; i < len; i++) {
            System.out.println("Solution " + (i + 1) + ":\n" + solutions.get(i) + "\n\n");
        }
    }
    
    /**
     * Produce solutions to n-Queens for the given n.
     * 
     * @param n Size of the board to test.
     * @return An ArrayList<Board> of solutions
     */
    public ArrayList<Board> testQueens(int n) {
        Board board = new Board(n);
        ArrayList<Board> solutions = new ArrayList<Board>();
        solve(board, solutions);
        return solutions;
    }
    
}

