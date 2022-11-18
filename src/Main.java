/* Avi Walia and Nikhil Sachdev
 * March 23rd, 2022
 *
 * This program allows the user to select from one of three mazes and try to solve it.
 * It will then solve the maze recursively and then show the user the path to the end
 * as well as display the moves required to solve it.
 * This algorithm works for any maze, solvable and unsolvable.
 *
 * The mazes are hardcoded but can be changed before running
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        //Test case 1
        char[][] maze1 = new char[][]{
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', '$', 'X', 'X'},
                {'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', ' ', 'X', ' ', 'X', 'X'},
                {'X', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X'},
                {'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', 'X', ' ', 'X', 'X'},
                {'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X', 'X'},
                {'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X'},
                {'X', ' ', ' ', 'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                {'X', 'X', ' ', 'X', ' ', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
                {'X', 'X', ' ', 'X', ' ', 'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', 'X', ' ', 'X', ' ', ' ', ' ', 'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X'},
                {'X', 'X', ' ', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', 'X', ' ', ' ', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X', ' ', 'X'},
                {'X', ' ', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', '@', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};

        System.out.println("Maze 1");
        printArray(maze1, 0, 0);
        System.out.println("\n");
        //Test Case 2
        char[][] maze2 = {
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                {'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X'},
                {'X', 'X', ' ', 'X', 'X', 'X', ' ', 'X', 'X', 'X'},
                {'X', 'X', ' ', ' ', ' ', 'X', ' ', 'X', 'X', 'X'},
                {'X', 'X', 'X', 'X', ' ', 'X', ' ', 'X', 'X', 'X'},
                {'X', ' ', ' ', ' ', ' ', 'X', ' ', 'X', 'X', 'X'},
                {'X', '@', 'X', 'X', 'X', 'X', ' ', ' ', ' ', 'X'},
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', '$', 'X'}};
        System.out.println("Maze 2");
        printArray(maze2, 0, 0);
        System.out.println("\n");

        //Test Case 3 - Unsolvable
        char[][] maze3 = {
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
                {'X', '@', 'X', ' ', ' ', ' ', 'X', 'X', 'X', 'X'},
                {'X', ' ', 'X', 'X', 'X', ' ', 'X', '$', 'X', 'X'},
                {'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X'},
                {'X', 'X', 'X', 'X', ' ', 'X', 'X', ' ', 'X', 'X'},
                {'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
                {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};
        System.out.println("Maze 3");
        printArray(maze3, 0, 0);
        System.out.println("\n");

        char[][][] mazes = {maze1, maze2, maze3};

        boolean running = true;

        while(running){
            //Asking user which maze they would like to solve
            System.out.print("Which maze would you like to solve? (1, 2, or 3): ");
            int index = input.nextInt() - 1;
            input.nextLine();


            char[][] maze = mazes[index];
            System.out.println("This is the maze that we will try to solve:");
            printArray(maze, 0, 0); //displaying unsolved maze to user
            System.out.println();

            System.out.println("Enter the moves that you think are necessary to solve this maze. ");
            System.out.println("Format your answer so that is all in one line. For example: 'drrrlu'.");
            System.out.println("If the maze is unsolvable, enter 'No solution'");
            String userMoves = input.nextLine().toLowerCase(); //getting the user's guess
            System.out.println();

            int[] start = findStart(maze); //Find start (@)
            if (solveMaze(maze, start[0], start[1])){ //checks if maze is solvable

                System.out.println("This maze is solvable! Here is the path:");
                printArray(maze, 0, 0); //Display solution path
                System.out.print("\nMoves: ");
                findPath(maze, start[0], start[1]); //Display moves taken to reach end
                System.out.println();

                if (checkUser(maze, userMoves, start[0], start[1])){ //checking if the user's input was correct
                    System.out.println("Congratulations, you were right!");
                } else {
                    System.out.println("Sorry! You were wrong.");
                }
            } else { //If maze cannot be solved

                System.out.println("This maze is unsolvable!");

                if (userMoves.equals("no solution")){ //checking if the user's input was correct
                    System.out.println("Congratulations, you were right!");
                } else {
                    System.out.println("Sorry! You were wrong.");
                }
            }

            //Asking the user if they want to try again on a different maze.
            System.out.print("\n\nDo you want to try again on another maze? ('y' or 'n'): ");
            String run = input.next().toLowerCase();
            boolean flag = true;
            while (flag){ //making sure input given is correct ('y' for yes and 'n' for no)
                if (run.equals("y")){
                    flag = false;
                }
                else if (run.equals("n")){
                    running = false;
                    flag = false;
                } else {
                    System.out.print("Invalid Input! Try again: ");
                    run = input.next().toLowerCase();
                }
            }
        }
        input.close();
    }

    /**
     * <p>Displays the maze using recursion. Does not return anything.</p>
     * @param arr the maze that we want to print.
     * @param index1 the starting index for rows.
     * @param index2 the starting index for columns.
     */

    public static void printArray (char[][] arr, int index1, int index2) {
        if (index1 == arr.length){ //Base case: if you reach the end of the array, print nothing.
            System.out.print("");
        } else {                   // General Case
            if (index2 == arr[index1].length){ //if you reach the last element of the 1-D array
                System.out.println();
                printArray(arr, index1+1, 0); //start at next row
            } else {
                System.out.print(arr[index1][index2] + " ");
                printArray(arr, index1, index2 + 1); //continue on to next cell.
            }
        }
    }

    /**
     * <p>Attempts to solve the maze and returns whether it can be solved or not. If the maze is solvable,
     * it alters the array so that there is a path.</p>
     * @param maze the maze that we wish to solve.
     * @param r the row of the starting point (represented by '@').
     * @param c the column of the starting point(represented by '@').
     * @return a boolean if the maze is solvable or not.
     */
    public static boolean solveMaze(char[][] maze, int r, int c) {
        boolean solution = false;
        char[] possibleMoves = moves(maze, r, c); //find the possible moves (right, down, left, up)

        //Base Case 1 - if any possible move leads to finish.
        if (possibleMoves[0] == '$' || possibleMoves[1] == '$' || possibleMoves[2] == '$' || possibleMoves[3] == '$') {
            maze[r][c] = '-';
            return true;
        } else { //General Case
            if (possibleMoves[0] == ' ') { //if you can move right.
                if (maze[r][c] != '@'){ //if it isn't the starting spot.
                    maze[r][c] = '-'; //replace the ' ' with a dash.
                }
                solution = solveMaze(maze, r, c + 1); //call method on new position.
            }
            if (possibleMoves[1] == ' ' && !solution) { //if you can move down and the maze has not yet been solved.
                if (maze[r][c] != '@'){
                    maze[r][c] = '-';
                }
                solution = solveMaze(maze, r + 1, c);
            }
            if (possibleMoves[2] == ' ' && !solution) { //if you can move left and the maze has not yet been solved.
                if (maze[r][c] != '@'){
                    maze[r][c] = '-';
                }
                solution = solveMaze(maze, r, c - 1);
            }
            if (possibleMoves[3] == ' ' && !solution) { //if you can move up and the maze has not yet been solved.
                if (maze[r][c] != '@'){
                    maze[r][c] = '-';
                }
                solution = solveMaze(maze, r - 1, c);
            }

            if (!solution){ //if there are no moves to be made in that spot.
                maze[r][c] = ' '; //replace the block with an empty space since it is not the path
            }
        }
        return solution; //Base Case 2 - returns false when there are no moves to be made.
    }

    /**
     * <p>Moves returns the 4 possible moves of each position (left, right, up, down).</p>
     * @param maze the maze that we are trying to solve
     * @param r the index of the row of the current position.
     * @param c the index of the column of the current position.
     * @return a 1-D array of chars of the blocks that surround the inputted position.
     */
    public static char[] moves(char[][] maze, int r, int c){
        char right;
        char down;
        char left;
        char up;

        try { //Checks to see if there is a possible move right
            right = maze[r][c+1];
        } catch(IndexOutOfBoundsException e) { //if there isn't, assign the value for right as null
            right = 0;
        }
        try { //Checks for possible move down
            down = maze[r+1][c];
        } catch(IndexOutOfBoundsException e) {
            down = 0;
        }
        try { //Checks for possible move left
            left = maze[r][c-1];
        } catch(IndexOutOfBoundsException e) {
            left = 0;
        }
        try { //Checks for possible move up
            up = maze[r-1][c];
        } catch(IndexOutOfBoundsException e) {
            up = 0;
        }
        return new char[] {right, down, left, up}; //return 1-D array of cells that are right, down left, and up.
    }


    /**
     * <p>Finds the location of the starting spot in the maze.</p>
     * @param maze the maze that we are trying to solve.
     * @return returns a 1-D array of the position (row, column) of the starting block which
     * is the '@' symbol.
     */
    public static int[] findStart(char[][] maze){
        for (int i = 0; i < maze.length; i++){
            for (int j = 0; j < maze[i].length; j++){
                if (maze[i][j] == '@'){ //if the value of the cell is equal to @ (the starting spot)
                    return new int[] {i, j}; //return its index
                }
            }
        }
        return new int[] {-1, -1}; //if the cell does not contain a starting point
    }

    /**
     * <p>Traces a solved maze and displays the moves required to get to the end.</p>
     * @param maze an already solved maze (the path has been replaced with '-')
     * @param r the index of the row of the starting spot.
     * @param c the index of the column of the starting spot.
     */
    public static void findPath(char[][] maze, int r, int c){
        char[] possibleMoves = moves(maze, r, c);
        if (maze[r][c] == '$') { //Base Case - if we are at the end of the maze
            System.out.print(""); //print nothing and stop the method
        } else { //General Case - if there is a move to be made
            if (possibleMoves[0] == '-' || possibleMoves[0] == '$'){ //if the right cell is the next move
                System.out.print("R ");
                if (maze[r][c] != '@'){ //if it isn't the starting spot.
                    maze[r][c] = ' '; //replace solved path with empty path so the method does not return to that spot
                }
                findPath(maze, r, c+1); //call the method on cell to the right
            } if (possibleMoves[1] == '-' || possibleMoves[1] == '$') { //if down cell is the next move
                System.out.print("D ");
                if (maze[r][c] != '@'){
                    maze[r][c] = ' ';
                }
                findPath(maze, r+1, c);
            } if (possibleMoves[2] == '-' || possibleMoves[2] == '$') { //if left cell is the next move
                System.out.print("L ");
                if (maze[r][c] != '@'){
                    maze[r][c] = ' ';
                }
                findPath(maze, r, c-1);
            } if (possibleMoves[3] == '-' || possibleMoves[3] == '$'){ //if up cell is the next move
                System.out.print("U ");
                if (maze[r][c] != '@'){
                    maze[r][c] = ' ';
                }
                findPath(maze, r-1, c);
            }
        }
    }

    /**
     * <p>This method checks if the user's guess of how to solve the is possible or not. This only works
     * for solvable mazes.</p>
     * @param maze The maze that we are trying to solve (2-D array)
     * @param userMoves A string that contains what the user inputted as their solution to the maze.
     * @param r the index of the row of the starting spot.
     * @param c the index of the column of the starting spot.
     * @return a boolean (true or false) as to whether the user's guess was correct or not.
     */
    public static boolean checkUser(char[][] maze, String userMoves, int r, int c){
        boolean flag = true; //flag for whether the user's guess is correct or not

        for (int i = 0; i < userMoves.length(); i++){ //for each move in the user's guess
            char move = userMoves.charAt(i);
            try { //makes sure if the user inputs a move that is out of bounds, the program does not return an error
                if (move == 'r'){ //if the user's move is right
                    if (maze[r][c+1] == ' ' || maze[r][c+1] == '$'){ //checks if the right space is empty or the end
                        c++;
                    } else { //if it is not a possible move, return false
                        flag = false;
                    }
                } else if (move == 'd') { //if the user's move is down
                    if (maze[r+1][c] == ' ' || maze[r+1][c] == '$') {
                        r++;
                    } else {
                        flag = false;
                    }
                } else if (move == 'l') { //if the user's move is left
                    if (maze[r][c-1] == ' ' || maze[r][c-1] == '$') {
                        c--;
                    } else {
                        flag = false;
                    }
                } else if (move == 'u') { //if the user's move is up
                    if (maze[r-1][c] == ' ' || maze[r-1][c] == '$') {
                        r--;
                    } else {
                        flag = false;
                    }
                } else { //if the move is not right, down, left, or up, return false
                    flag = false;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                flag = false;
            }
        }

        if (maze[r][c] != '$'){ //if after all the moves, the user has not reached the end, return false
            flag = false;
        }
        return flag; //return flag (will return true if all the correct moves have been entered)
    }
}