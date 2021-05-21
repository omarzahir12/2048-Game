/**
 * Author: Mohammad Omar Zahir - zahirm1
 * Revised: April 12, 2021
 *
 * Description: Test file for BoardT objects and testing their various methods and properties
 */

package src;

import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TestBoardT {
    BoardT board;

    @Before
    public void setup(){
        board = new BoardT(4,2);
    }

    @After
    public void tearDown(){
        board = null;
    }

    @Test
    //Since a new board will spawn every time, only one test is needed to test functionality, as every iteration
    //will be a new board
    public void spawn_test(){
        int count = 0; //Keeps track of values that are non-zero in board
        for (ArrayList<Integer> i: board.getBoard()){
            for (int j : i){
                if(j != 0){
                    count ++;
                }
            }
        }
        assertTrue(count == 2);
    }

    @Test
    //Regular Test
    public void addCell_test1(){
        board.addCell();
        int count = 0; //Keeps track of values that are non-zero in board
        for (ArrayList<Integer> i: board.getBoard()){
            for (int j : i){
                if(j != 0){
                    count ++;
                }
            }
        }
        assertTrue(count == 3);
    }

    @Test
    //Boundary Case: For maximum amount of cells (16)
    public void addCell_test2(){
        //adds 14 since two are there from spawn
        for (int i = 0; i < 14; i++){
            board.addCell();
        }
        int count = 0; //Keeps track of values that are non-zero in board
        for (ArrayList<Integer> i: board.getBoard()){
            for (int j : i){
                if(j != 0){
                    count ++;
                }
            }
        }
        assertTrue(count == 16);
    }

    @Test
    //Edge Case: Adding more than 16, there should not be more added and board should stay the same
    public void addCell_test3(){
        //adds more cells than spaces on board
        ArrayList<ArrayList<Integer>> copyBoard = null;
        for (int i = 0; i < 20; i++){
            board.addCell();
            if (i == 14){
                copyBoard = board.getBoard(); //Stores the board when it is full to compare later
            }
            if(i > 13){
                //Checks if adding more cells than spaces will change the board
                assertTrue(copyBoard == board.getBoard());
            }
        }
        int count = 0; //Keeps track of values that are non-zero in board
        for (ArrayList<Integer> i: board.getBoard()){
            for (int j : i){
                if(j != 0){
                    count ++;
                }
            }
        }
        assertTrue(count == 16);
    }

    @Test
    //Edge Case: Values should not add altogether
    public void moveUp_test1(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        board.setBoard(values);
        board.move(DirectionT.Up);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Douple Up, values should add altogether
    public void moveUp_test2(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        board.setBoard(values);
        board.move(DirectionT.Up);
        board.move(DirectionT.Up);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(8,8,8,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Existence of Zero should not affect merging
    public void moveUp_test3(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,2,0,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,0,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        board.setBoard(values);
        board.move(DirectionT.Up);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(2,2,2,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Regular Test
    public void moveUp_test4(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,8,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,8,0,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(8,4,2,2)));
        board.setBoard(values);
        board.move(DirectionT.Up);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(2,16,2,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,8,2)));
        expected.add(new ArrayList<Integer>(Arrays.asList(8,0,2,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Regular Test: Nothing should happen
    public void moveUp_test5(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,8,16,32)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,2,8)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,2)));
        board.setBoard(values);
        board.move(DirectionT.Up);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(2,8,16,32)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,2,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,8,2)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Values should not add altogether, tests more boundary
    public void moveUp_test6(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        board.setBoard(values);
        board.move(DirectionT.Up);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Values should not add altogether
    public void moveDown_test1(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        board.setBoard(values);
        board.move(DirectionT.Down);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(8,8,8,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(8,8,8,8)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Double down, values should add together
    public void moveDown_test2(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        board.setBoard(values);
        board.move(DirectionT.Down);
        board.move(DirectionT.Down);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(16,16,16,16)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Existence of Zero should not affect merging
    public void moveDown_test3(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,2,0,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,0,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        board.setBoard(values);
        board.move(DirectionT.Down);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(2,2,2,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Regular Test
    public void moveDown_test4(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,8,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,64,0,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,8,4)));
        board.setBoard(values);
        board.move(DirectionT.Down);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(2,8,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(32,64,2,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(16,8,16,8)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Regular Test: Nothing should happen
    public void moveDown_test5(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,16,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(8,0,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,8,2,8)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2)));
        board.setBoard(values);
        board.move(DirectionT.Down);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,16,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(8,0,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,8,2,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Values should not add altogether, tests more boundary
    public void moveDown_test6(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        board.setBoard(values);
        board.move(DirectionT.Down);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,2,2)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Values should not add altogether
    public void moveRight_test1(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        board.setBoard(values);
        board.move(DirectionT.Right);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,8,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,8,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,8,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,8,8)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Double Right, values should add together
    public void moveRight_test2(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        board.setBoard(values);
        board.move(DirectionT.Right);
        board.move(DirectionT.Right);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,16)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,16)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,16)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,16)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Existence of Zero should not affect merging
    public void moveRight_test3(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,2,0,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,0,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        board.setBoard(values);
        board.move(DirectionT.Right);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,2,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,4,4)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Regular Test
    public void moveRight_test4(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,64,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,8,4)));
        board.setBoard(values);
        board.move(DirectionT.Right);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,2,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,32,128,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,16,16,4)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Regular Test: Nothing should happen
    public void moveRight_test5(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,16,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,8,2,8)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2)));
        board.setBoard(values);
        board.move(DirectionT.Right);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,16,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,8,2,8)));
        expected.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Values should not add altogether, tests more boundary
    public void moveRight_test6(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(0,2,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(8,2,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,4)));
        board.setBoard(values);
        board.move(DirectionT.Right);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,2,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,8,4,4)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,2,4,4)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Values should not add altogether
    public void moveLeft_test1(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(8,8,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(8,8,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(8,8,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(8,8,0,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Double Left, values should add together
    public void moveLeft_test2(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        board.move(DirectionT.Left);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(16,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(16,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(16,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(16,0,0,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Existence of Zero should not affect merging
    public void moveLeft_test3(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,2,0,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,0,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(256,256,256,256)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(4,2,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(512,512,0,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Regular Test
    public void moveLeft_test4(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,64,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,8,4)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,4,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(32,128,4,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(8,4,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(16,16,4,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Regular Test: Nothing should happen
    public void moveLeft_test5(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(32,8,0,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,8,0,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(32,8,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,8,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    //Edge Case: Values should not add altogether, tests more boundary
    public void moveLeft_test6(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,2,8)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,4)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,0,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,2,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,4,8,0)));
        expected.add(new ArrayList<Integer>(Arrays.asList(4,2,4,0)));
        assertTrue(areEqual(board.getBoard(), expected));
    }

    @Test
    public void score_test1(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,2,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,2,8)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,4)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        assertTrue(board.getScore() == 16);
    }

    @Test
    //Edge Case: Nothing should happen
    public void score_test2(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(32,8,0,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,8,0,0)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        assertTrue(board.getScore() == 0);
    }

    @Test
    //Regular Test: Nothing should happen
    public void score_test3(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,8,16,32)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,2,8)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,2)));
        board.setBoard(values);
        board.move(DirectionT.Up);
        assertTrue(board.getScore() == 0);
    }

    @Test
    //Edge Case: Double Up, values should add altogether
    public void score_test4(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,2,2,2)));
        board.setBoard(values);
        board.move(DirectionT.Up);
        board.move(DirectionT.Up);
        assertTrue(board.getScore() == 64);
    }

    @Test
    //Edge Case: Double Right, values should add together
    public void score_test5(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,4,4,4)));
        board.setBoard(values);
        board.move(DirectionT.Right);
        board.move(DirectionT.Right);
        assertTrue(board.getScore() == 128);
    }

    @Test
    //Regular Test: Large number
    public void score_test6(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,64,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,8,4)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        assertTrue(board.getScore() == 148);
    }

    @Test
    //Regular Test
    public void hasWon_test1(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2048,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,64,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,8,4)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        assertTrue(board.hasWon());
    }

    @Test
    //Regular Test
    public void hasWon_test2(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,32,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,64,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,8,4)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        assertFalse(board.hasWon());
    }

    @Test
    //Regular Test: Should still be true for value > 2048 as that means player has gotten 2048 at some point previously
    public void hasWon_test3(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2048,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,64,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,8,4096)));
        board.setBoard(values);
        board.move(DirectionT.Left);
        assertTrue(board.hasWon());
    }

    @Test
    public void canMove_test1(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2048,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,8,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,2,8,4096)));
        board.setBoard(values);
        assertFalse(board.canMove(DirectionT.Right));
    }

    @Test
    public void canMove_test2(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2048,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,8,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,2,8,4096)));
        board.setBoard(values);
        assertFalse(board.canMove(DirectionT.Left));
    }

    @Test
    public void canMove_test3(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2048,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,8,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,2,8,4096)));
        board.setBoard(values);
        assertTrue(board.canMove(DirectionT.Up));
    }

    @Test
    public void canMove_test4(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2048)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,4,32)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,4,16,64)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,8,32)));
        board.setBoard(values);
        assertFalse(board.canMove(DirectionT.Up) && board.canMove(DirectionT.Down) &&
                board.canMove(DirectionT.Right) && board.canMove(DirectionT.Left));
    }

    @Test
    public void canMove_test5(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(2,4,8,2048)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,4,32)));
        values.add(new ArrayList<Integer>(Arrays.asList(2,4,0,64)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,8,32)));
        board.setBoard(values);
        assertTrue(board.canMove(DirectionT.Up) && board.canMove(DirectionT.Down) &&
                board.canMove(DirectionT.Right) && board.canMove(DirectionT.Left));
    }

    @Test
    public void canMove_test6(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2048,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,8,64,8)));
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,2,4096)));
        board.setBoard(values);
        assertFalse(board.canMove(DirectionT.Down));
    }

    @Test
    public void getStatus_test1(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2048,2,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,64,64,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(0,0,8,4)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,8,4096)));
        board.setBoard(values);
        assertTrue(board.getStatus());
    }

    @Test
    public void getStatus_test2(){
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        values.add(new ArrayList<Integer>(Arrays.asList(4,2,4,2)));
        values.add(new ArrayList<Integer>(Arrays.asList(16,8,16,8)));
        values.add(new ArrayList<Integer>(Arrays.asList(32,16,32,16)));
        values.add(new ArrayList<Integer>(Arrays.asList(4096,2048,4096,2048)));
        board.setBoard(values);
        assertFalse(board.getStatus());
    }

    //Local Functions
    private void print(ArrayList<ArrayList<Integer>> arr) {
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i).toString());
        }
    }

    private boolean areEqual(ArrayList<ArrayList<Integer>> a1, ArrayList<ArrayList<Integer>> a2){
        if (a1.size() != a2.size()){
            return false;
        }
        for (int i = 0; i < a1.size(); i++) {
            for (int j = 0; j < a1.size(); j++){
                if ((int) (a1.get(i).get(j)) != (int) (a2.get(i).get(j))){
                    return false;
                }
            }
        }
        return true;
    }
}
