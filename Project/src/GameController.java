/**
 * @file GameController.java
 * @author Mohammad Omar Zahir - zahirm1
 * @brief Contains the GameController for initializing and playing the game
 * @date April 12, 2021
 */

package src;

import java.util.Scanner;

/**
* @brief An abstract object for dealing with the user input and playing the game.
* @details The game leverages and combines the functionalities of BoardT and UserInterface.
*/

public class GameController {

    //Define State Variables
    private BoardT model;
    private UserInterface view;
    private static GameController controller = null;

    // Define environment variable
    private Scanner keyboard = new Scanner(System.in);

    /**
     * @brief Constructor for the Game Controller
     * @param model Model module of type BoardT
     * @param view View module of type UserInterface
     */
    private GameController(BoardT model, UserInterface view){
        this.model = model;
        this.view = view;
    }

    /**
     * @brief Public static method for obtaining a single instance.
     * @param model The game model.
     * @param view The UserInterface view
     * @return The single GameController object.
     */
    public static GameController getInstance(BoardT model, UserInterface view)
    {
        if (controller == null)
            controller = new GameController(model, view);
        return controller;
    }

    /**
     * @brief Initializes the game.
     * @param size The size of the board
     * @param number The game's base number
     */
    public void initializeGame(int size, int number){
        this.model = new BoardT(size, number);
    }

    /**
     * @brief Get string input.
     * @return The input.
     */
    public String readInput(){
        String input = "";
        input = keyboard.nextLine();
        return input;
    }

    /**
     * @brief Adds a cell to the model object (game board).
     */
    public void addCell(){
        model.addCell();
    }

    /**
     * @brief Slides board in given direction.
     * @param direction The direction to move the board.
     */
    public void move(DirectionT direction){ model.move(direction); }

    /**
     * @brief Determines the game's status.
     * @return The status of the game.
     */
    public boolean getStatus(){
        return this.model.getStatus();
    }

    /**
     * @brief Updates the view module to display a welcome message.
     */
    public void displayWelcomeMessage(){
        view.printWelcomeMessage();
    }

    /**
     * @brief Updates the view module to display the board.
     */
    public void displayBoard(){
        view.printBoard(model);
    }

    /**
     * @brief Updates the view module to display an ending message.
     */
    public void displayEnding(){
        view.printEndingMessage();
    }

    /**
     * @brief Updates the view module to display the controls for the game.
     */
    public void displayControls(){
        view.printGameControlsPrompt();
    }

    /**
     * @brief Updates the view module to display the score of the game.
     */
    public void displayScore() {
        view.printScore(model);
    }

    /**
     * @brief Updates the view module to display the game over message.
     */
    public void displayGameOver(){
        view.printGameOver();
    }

    /**
     * @brief Updates the view module to display the win message.
     */
    public void displayGameWon(){
        view.printWin();
    }

    /**
     * @brief Determines whether the game has been won.
     * @return True when the game has been won, and false if it has not.
     */
    public boolean checkWin() {
        return model.hasWon();
    }

    /**
     * @brief Determines if the model module (board) can be moved in the given direction.
     * @param direction The direction in which to determine whether the module can be moved.
     * @return True if the board can be moved, or False when it can not.
     */
    public boolean canMove(DirectionT direction){
        return model.canMove(direction);
    }

    /**
     * @brief Runs the game
     * @details Checks for multiple forms of conditions and inputs and makes decisions based on them.
     */
    public void runGame(){
        String input = "";
        //Keeps track of it the game has been won
        boolean hasWon = false;
        //Variables to keep track of a custom game
        int size = 0;
        int number = 0;
        displayWelcomeMessage();
        displayControls();
        System.out.println("Would you like to play the Regular Game or a Custom Game? Press c for custom: ");
        input = readInput();
        //Done only when a custom game would like to be played
        if (input.equals("c")){
            System.out.println("Enter a board size");
            input = readInput();
            //Lets user select the size, but only accepts valid inputs, or allows the game to be quit
            while (!(input.equals("q"))){
                try {
                    int x = Integer.parseInt(input);
                    if (x >= 4 && x <= 10) {
                        size = x;
                        break;
                    }
                    else{
                        System.out.println("Size must be between 4 and 10 (inclusive)");
                    }
                }catch(NumberFormatException e) {
                    System.out.println("Input is not an integer value");
                }
                System.out.println("Enter a board size");
                input = readInput();
            }
            if(input.equals("q")) {
                displayEnding();
                System.exit(0);
            }
            System.out.println("Enter a game number (default is 2)");
            input = readInput();
            //Lets user select the number, but only accepts valid inputs, or allows the game to be quit
            while (!(input.equals("q"))){
                try {
                    int x = Integer.parseInt(input);
                    if (x >= 2 && x <= 10) {
                        number = x;
                        break;
                    }
                    else{
                        System.out.println("Number must be between 2 and 10 (inclusive)");
                    }
                }catch(NumberFormatException e) {
                    System.out.println("Input is not an integer value");
                }
                System.out.println("Enter a game number (default is 2)");
                input = readInput();
            }
            if(input.equals("q")) {
                displayEnding();
                System.exit(0);
            }
            //Initializes game based on values previously selected
            initializeGame(size, number);
        }
        //If a custom game was not chosen
        else{
            initializeGame(4,2);
        }
        System.out.println("Press any button to begin");
        input = readInput();
        //Starts the loop for the turns until the game cannot be played
        //Lets the user play even after they have won
        while (getStatus() && !(input.equals("q") || input.equals("n"))){
            displayScore();
            displayBoard();
            //Takes an input for the direction of the user and only moves if it is a valid move
            try{
                System.out.print("Enter a Direction: ");
                input = readInput();
                if (!(input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d") || input.equals("q") || input.equals("n"))) {
                    throw new IllegalArgumentException();
                }
                if (input.equals("w")) {
                    if (canMove(DirectionT.Up)){
                        move(DirectionT.Up);
                        addCell();
                    }
                }
                else if (input.equals("s")) {
                    if (canMove(DirectionT.Down)){
                        move(DirectionT.Down);
                        addCell();
                    }
                }
                else if (input.equals("d")) {
                    if (canMove(DirectionT.Right)){
                        move(DirectionT.Right);
                        addCell();
                    }
                }
                else if (input.equals("a")) {
                    if (canMove(DirectionT.Left)){
                        move(DirectionT.Left);
                        addCell();
                    }
                }
            }
            catch (IllegalArgumentException e){
                System.out.println("Invalid Input");
            }
            //Checks the game after every move to see if the game has been won, and display the message once
            if (checkWin() && !(hasWon)) {
                displayScore();
                displayBoard();
                displayGameWon();
                hasWon = true;
            }
        }
        //If the game was ended because game over
        if (!getStatus()) {
            displayScore();
            displayBoard();
            displayGameOver();
            System.out.print("Play Again? y/n: ");
            input = readInput();
            if (input.equals("y"))
                runGame();
            else {
                displayEnding();
                System.exit(0);
            }
        }
        //If the game was ended because a new game was to be started
        else if (input.equals("n")){
            runGame();
        }
        //Any thing else would mean the game would close
        else {
            displayEnding();
            System.exit(0);
        }
    }
}
