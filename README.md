# QueensSolver

    Solver for n-queens problem which strips solutions containing colinear
    triplets.  Written in Java utilizing the Gradle build system.  Utilizes
    Lombok for getter annotations and Apache Commons CombinatoricsUtils for
    iterating over queen positions in solution boards to find colinear
    triplets.
    
    Every n-queens tutorial I read on the internet covered the recursive
    solution, so I decided to build a solution that was non-recursive for
    the added challenge.  It makes use of a flag to determine whether it
    should be backtracking or adding new queens.
    
    It also keeps track of the current state of the board by recording all
    occupied rows, columns and diagonals (both northwest-to-southeast and
    northeast-to-southwest, dubbed "main" and "anti" internally after the
    matrix math terms).  This simplifies the test for whether a square
    would constitute a valid position to place a new queen to a single
    conditional statement.

## Usage

You can buld and run on the command-line with gradle by issuing a statement
of the form:

```./gradlew run --args=N````

where N is an integer representing the size of the board you'd like to test.

## Testing

```./gradlew test [-i]```

Use the -i flag to display logs to stdout.

