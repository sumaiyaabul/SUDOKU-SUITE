# SUDOKU-SUITE

# Overveiw

A Java program to generate, validate and solve a NxN Sudoku puzzle with visualization, tracking and 100% readable code.It has a random board 
generator which sometimes gives you unsolvable boards or boards that take forever to solve.Luckily it also has tracking and visualization 
capabilities for you to track its (occasionally) painful progress.The tracking and visualization makes watching the program solve the puzzle 
more interesting than solving the board yourself. This is in addition to the perfectly modular and readable code which makes it easy to follow 
the logic and optimize / customize it further.

# Sudoku-suit-How it works?

This particular algorithm employs the use of backtracking, one of the more common methods to solve Sudoku puzzles. I've written a simple algorithm to give an idea of how the 
program works.

   1.Start.
   2.We start with the first empty cell.
   3.We generate a list of possible valid values that can be filled in that cell.
   4.We iterate over this list and start with the first value. This value is placed in the required cell.
   5.We move on to the next cell. We again generate a list of possibilities. However, if no list can be generated, then this means that there is something wrong with the 
     value of the previous cell. We then move back to the previous cell and place the next value on the generated list in the cell now. We repeat this step until the current 
     cell has a valid value placed inside it.
   6.We stop when we reach the 81st cell (the last cell in a Sudoku puzzle) and have placed a valid value.
   7.The puzzle has now been solved.
   8.Stop.
   
# Technological Components

Before using this application, ensure you have the following prerequisites installed:
  1. Version Control System
  2. Java Development Kit(JDK)
  3. Integerated Development Environments(IDEs)
     -Eclipse
     -IntelliJ IDEA
     -NetBeans
  
   
