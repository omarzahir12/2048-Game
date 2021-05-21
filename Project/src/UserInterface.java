/**
 * @file UserInterface.java
 * @author Mohammad Omar Zahir - zahirm1
 * @brief Contains various messages to be printed during the game, as well as a method
 * for printing the board of the game.
 * @date April 12, 2021
 */

package src;

import java.util.ArrayList;

/**
* @brief An abstract object for representing the game board
* and printing related messages.
* @details Includes methods that prints useful messages pertaining to the game board
* and a size adapting board printing method.
*/

public class UserInterface {
    private static UserInterface visual = null;
    private UserInterface(){};

    public static UserInterface getInstance(){
        if (visual == null)
            return visual = new UserInterface();
        return visual;
    }

    /**
     * @brief Displays a welcome message.
     */
    public void printWelcomeMessage(){
        System.out.println("-------------------------------------------");
        System.out.println("              Welcome to 2048              ");
        System.out.println("-------------------------------------------");
    }

    /**
     * @brief Displays a prompt showing the controls of the game.
     */
    public void printGameControlsPrompt(){
        System.out.println("Basic Rules:");
        System.out.println("New Game: n | Quit Game: q");
        System.out.println("Move Up: w | Move Down: s | Move Right: d | Move left: a");
    }

    /**
     * @brief Displays a prompt showing the current score of the game.
     */
    public static void printScore(BoardT model){
        System.out.println("Score: " + model.getScore());
    }

    /**
     * @brief Displays a prompt showing the game over message.
     */
    public void printGameOver(){
        System.out.println("!!!!!!!!!!!!!!!!!!! Game Over !!!!!!!!!!!!!!!!!!!!");
    }

    /**
     * @brief Displays a prompt showing the game win message.
     */
    public void printWin(){
        System.out.println("~~~~~~~~~~~~~~~~~~~ You Won! ~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * @brief Displays an ending message after player chooses to exit the game.
     */
    public void printEndingMessage(){
        System.out.println("-------------------------------------------------");
        System.out.println("             Thank You For Playing !!!           ");
        System.out.println("-------------------------------------------------");
    }

    /**
     * @brief Displays the board on the screen.
     * @param model The game board
     */
    public static void printBoard(BoardT model) {
        ArrayList<ArrayList<Integer>> board = model.getBoard();
        System.out.print("||");
        for (int i = 0; i < board.size()-1; i++){
            System.out.print("|||||");
            System.out.print("|");
        }
        System.out.print("|||||");
        System.out.println("||");
        for (int i = 0; i < board.size(); i++) {
            System.out.print("||");
            for (int j = 0; j < board.size(); j++) {
                int value = board.get(i).get(j);
                int length = String.valueOf(value).length();
                if (j == board.size()-1) {
                    if (value == 0)
                        System.out.print("     ");
                    else{
                        System.out.print(value);
                        spaces(5-length);
                    }
                }
                else{
                    if (value == 0)
                        System.out.print("     |");
                    else{
                        System.out.print(value);
                        spaces(5-length);
                        System.out.print("|");
                    }
                }
            }
            System.out.print("||");
            System.out.println("");
        }
        System.out.print("||");
        for (int i = 0; i < board.size()-1; i++){
            System.out.print("|||||");
            System.out.print("|");
        }
        System.out.print("|||||");
        System.out.println("||");
    }

    //Used for dynamically adjusting the spaces
    private static void spaces(int l){
        for (int i = 0; i < l; i++){
            System.out.print(" ");
        }
    }
}
