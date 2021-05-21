/**
 * @file BoardT.java
 * @author Mohammad Omar Zahir - zahirm1
 * @brief Contains the Abstract Data Type for creating and operating with BoardT objects.
 * @date April 12, 2021
 */

package src;

import java.util.ArrayList;

/**
* @brief An abstract data type for the game state of 2048.
* @details The game is represented by the board and properties for that board,
* namely score and status
*/
public class BoardT{
    private ArrayList<ArrayList<Integer>> board;
    private boolean status;
    private Integer score = 0;
    private Integer size = 4;
    private Integer number = 2;

    /**
     * @brief Contructs the BoardT object.
     * @param gameSize The square board dimensions of the game
     * @param gameNumber The base game number to be played with
     */
    public BoardT(int gameSize, int gameNumber) {
        this.size = gameSize;
        this.number = gameNumber;
        this.status = true;
        this.board = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> emptyArr = emptyArray();
        for (int i = 0; i < size; i++) {
            this.board.add(emptyArr);
        }
        addCell();
        addCell();
    }

    /**
     * @brief Gets the board from the BoardT object.
     * @return The board of the game.
     */
    public ArrayList<ArrayList<Integer>> getBoard() {
        return this.board;
    }

    /**
     * @brief Gets the current score of the game.
     * @return The score of the game.
     */
    public int getScore(){
        return this.score;
    }

    /**
     * @brief Gets the current status of the game.
     * @return The status of the game.
     */
    public boolean getStatus(){
        this.status = isPlayable();
        return this.status;
    }

    /**
     * @brief Adds a value at an empty position on the board.
     */
    public void addCell() {
        ArrayList<Integer> cell = availableCell();
        if (cell.isEmpty())
            return;
        int x = cell.get(0);
        int y = cell.get(1);
        setValue(x,y,randomValue());
    }

    /**
     * @brief Determines if the board can be moved in a certain direction.
     * @param direction The direction the board is to be moved in.
     * @return Whether the board can be moved in the given direction.
     */
    public boolean canMove(DirectionT direction){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                int value = getCell(i,j);
                if (value != 0){
                    if (direction == DirectionT.Up) {
                        if (up(i, j) == value || up(i, j) == 0)
                            return true;
                    }
                    if (direction == DirectionT.Down) {
                        if (down(i, j) == value || down(i, j) == 0)
                            return true;
                    }
                    if (direction == DirectionT.Left) {
                        if (left(i, j) == value || left(i, j) == 0)
                            return true;
                    }
                    if (direction == DirectionT.Right) {
                        if (right(i, j) == value || right(i, j) == 0)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @brief Determines whether the game has been won yet.
     * @return The win status of the game.
     */
    public boolean hasWon() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = getCell(i,j);
                if (value >= number*1024) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @brief Moves the board in a given direction.
     * @param direction The direction that the game is to be moved in.
     */
    public void move(DirectionT direction){
        if (direction == DirectionT.Up){
            moveUp();
        }
        else if (direction == DirectionT.Down){
            moveDown();
        }
        else if (direction == DirectionT.Right){
            moveRight();
        }
        else if (direction == DirectionT.Left){
            moveLeft();
        }
    }

    //This method determines if the game is still playable if the a move can be made in any direction
    private boolean isPlayable(){
        if (canMove(DirectionT.Up))
            return true;
        else if (canMove(DirectionT.Down))
            return true;
        else if (canMove(DirectionT.Right))
            return true;
        else if (canMove(DirectionT.Left))
            return true;
        return false;
    }

    //This method moves the board up by iterating through all the values and shifting them if the value above them is
    //0 or the same value, the cells that are merged are kept track of as well
    private void moveUp(){
        ArrayList<ArrayList<Integer>> merged = new ArrayList<ArrayList<Integer>>();
        for (int h = 0; h < (size-1); h++) {
            for (int i = 1; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int value = getCell(i,j);
                    ArrayList<Integer> pos = new ArrayList<Integer>();
                    ArrayList<Integer> currPos = new ArrayList<Integer>();
                    pos.add(i-1);
                    pos.add(j);
                    currPos.add(i);
                    currPos.add(j);
                    if (value != 0) {
                        if (up(i, j) == 0) {
                            int sum = value + up(i, j);
                            setValue(i - 1, j, sum);
                            setValue(i, j, 0);
                        } else if (up(i, j) == value && !(merged.contains(pos)) && !(merged.contains(currPos))) {
                            merged.add(pos);
                            int sum = value + up(i, j);
                            this.score += sum;
                            setValue(i - 1, j, sum);
                            setValue(i, j, 0);
                        }
                    }
                }
            }
        }
    }

    //Determines the value of the cell above a given coordinate position in the board
    private int up(int x, int y){
        if (x == 0)
            return -1;
        else
            return this.board.get(x-1).get(y);
    }

    //This method moves the board down by iterating through all the values and shifting them if the value below them is
    //0 or the same value, the cells that are merged are kept track of as well
    private void moveDown(){
        ArrayList<ArrayList<Integer>> merged = new ArrayList<ArrayList<Integer>>();
        for (int h = 0; h < (size-1); h++) {
            for (int i = (size-2); i > -1; i--) {
                for (int j = 0; j < size; j++) {
                    int value = getCell(i,j);
                    ArrayList<Integer> pos = new ArrayList<Integer>();
                    ArrayList<Integer> currPos = new ArrayList<Integer>();
                    pos.add(i+1);
                    pos.add(j);
                    currPos.add(i);
                    currPos.add(j);
                    if (value != 0) {
                        if (down(i, j) == 0) {
                            int sum = value + down(i, j);
                            setValue(i + 1, j, sum);
                            setValue(i, j, 0);
                        }
                        else if (down(i, j) == value && !(merged.contains(pos)) && !(merged.contains(currPos))) {
                            merged.add(pos);
                            int sum = value + down(i, j);
                            this.score += sum;
                            setValue(i + 1, j, sum);
                            setValue(i, j, 0);
                        }
                    }
                }
            }
        }
    }

    //Determines the value of the cell below a given coordinate position in the board
    private int down(int x, int y){
        if (x == (size-1))
            return -1;
        else
            return this.board.get(x+1).get(y);
    }

    //This method moves the board right by iterating through all the values and shifting them if the value to the right
    // of them is 0 or the same value, the cells that are merged are kept track of as well
    private void moveRight(){
        ArrayList<ArrayList<Integer>> merged = new ArrayList<ArrayList<Integer>>();
        for (int h = 0; h < (size-1); h++) {
            for (int i = 0; i < size; i++) {
                for (int j = (size-2); j > -1; j--) {
                    int value = getCell(i,j);
                    ArrayList<Integer> pos = new ArrayList<Integer>();
                    ArrayList<Integer> currPos = new ArrayList<Integer>();
                    pos.add(i);
                    pos.add(j+1);
                    currPos.add(i);
                    currPos.add(j);
                    if (value != 0){
                        if (right(i, j) == 0) {
                            int sum = value + right(i, j);
                            setValue(i, j + 1, sum);
                            setValue(i, j, 0);
                        }
                        else if (right(i, j) == value && !(merged.contains(pos)) && !(merged.contains(currPos))) {
                            merged.add(pos);
                            int sum = value + right(i, j);
                            this.score += sum;
                            setValue(i, j + 1, sum);
                            setValue(i, j, 0);
                        }
                    }
                }
            }
        }
    }

    //Determines the value of the cell to the right of a given coordinate position in the board
    private int right(int x, int y){
        if (y == size-1)
            return -1;
        else
            return this.board.get(x).get(y+1);
    }

    //This method moves the board left by iterating through all the values and shifting them if the value to the left
    //of them is 0 or the same value, the cells that are merged are kept track of as well
    private void moveLeft(){
        ArrayList<ArrayList<Integer>> merged = new ArrayList<ArrayList<Integer>>();
        for (int h = 0; h < (size-1); h++) {
            for (int i = 0; i < size; i++) {
                for (int j = 1; j < size; j++) {
                    int value = getCell(i,j);
                    ArrayList<Integer> pos = new ArrayList<Integer>();
                    ArrayList<Integer> currPos = new ArrayList<Integer>();
                    pos.add(i);
                    pos.add(j-1);
                    currPos.add(i);
                    currPos.add(j);
                    if (value != 0) {
                        if (left(i, j) == 0) {
                            int sum = value + left(i, j);
                            setValue(i, j - 1, sum);
                            setValue(i, j, 0);
                        } else if (left(i, j) == value && !(merged.contains(pos)) && !(merged.contains(currPos))) {
                            merged.add(pos);
                            int sum = value + left(i, j);
                            this.score += sum;
                            setValue(i, j - 1, sum);
                            setValue(i, j, 0);
                        }
                    }
                }
            }
        }
    }

    //Determines the value of the cell to the left of a given coordinate position in the board
    private int left(int x, int y){
        if (y == 0)
            return -1;
        else
            return this.board.get(x).get(y-1);
    }

    //Determines a random empty position in the board from a list of empty positions
    private ArrayList<Integer> availableCell() {
        ArrayList<ArrayList<Integer>> positions = getZeroes(this.board);
        int pos = (int) (Math.random() * (positions.size()));
        if (positions.isEmpty())
            return new ArrayList<Integer>();
        return positions.get(pos);
    }

    //Gets the cell of the board at a given position
    private int getCell(int x, int y){
        return this.board.get(x).get(y);
    }

    //Sets the value in the board to a given position and value
    private void setValue(int x, int y, int val){
        ArrayList<Integer> e = (ArrayList<Integer>) this.board.get(x).clone();
        e.set(y, val);
        this.board.set(x, e);
    }

    private int randomValue() {
        return Math.random() < 0.9 ? number : (number * 2);
    }

    //Returns all the zero positions from a 2D array
    private static ArrayList<ArrayList<Integer>> getZeroes(ArrayList<ArrayList<Integer>> arr) {
        ArrayList<ArrayList<Integer>> zeroes = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.size(); j++) {
                if ((arr.get(i)).get(j) == 0) {
                    ArrayList<Integer> position = new ArrayList<Integer>();
                    position.add(i);
                    position.add(j);
                    if (!(zeroes.contains(position)))
                        zeroes.add(position);
                }
            }
        }
        return zeroes;
    }

    //Returns an empty array corresponding to the size of the board
    private ArrayList<Integer> emptyArray() {
        ArrayList<Integer> al = new ArrayList<>();
        for (int i = 0; i < size; i++){
            al.add(0);
        }
        return al;
    }

    //For testing of the board by giving it a given value
    protected void setBoard(ArrayList<ArrayList<Integer>> board){
        this.board = board;
    }
}
