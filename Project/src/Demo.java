/**
 * @file Demo.java
 * @author Mohammad Omar Zahir - zahirm1
 * @brief Initializes the Board and UserInterface, and runs the game from GameController
 * @date April 12, 2021
 */

package src;

/**
* @brief A class that the user uses to play the game.
* @details Initializes the BoardT object and calls instances of the UserInterface and GameController.
*/

public class Demo
{
    public static void main(String[] args) {
        BoardT boardT = new BoardT(4,2);
        UserInterface UI = UserInterface.getInstance();
        GameController game = GameController.getInstance(boardT, UI);
        game.runGame();
    }
}