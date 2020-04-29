import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

class SnakeTest {
    private Board b;
    
    @BeforeEach
    public void init() {
        b = new Board(new JLabel("blank"), new JLabel("test"));
        b.reset();
    }
    
    @Test
    public void initialStateTest() {
        assertEquals(5, b.getSnake().getDeque().size());
        BoardState[][] board = b.getBoard();
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        assertEquals(BoardState.SNAKE, board[11][9]);
        //make sure food is here
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(1, foodCount);
        //make sure snake isn't moving
        assertEquals(Direction.WAITING, b.getSnake().getDirection());
        assertEquals(20, b.getBoard().length);
        assertTrue(b.isPlaying());
    }
    
    @Test
    public void moveSnakeUp() {
        //we set the direction and tick once to make sure it moves up as we expect
        if (b.getBoard()[6][9] == BoardState.FOOD) {
            b.setFood(0, 0);
        }
        b.setSnakeDirection(Direction.UP);
        b.tick();
        BoardState[][] board = b.getBoard();
        assertEquals(5, b.getSnake().getDeque().size());
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        assertEquals(BoardState.SNAKE, board[6][9]);
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(1, foodCount); //again making sure moving up does not 
        //break already working conditions.
        assertEquals(Direction.UP, b.getSnake().getDirection()); //we should still be 
        //going up, as game does not pause
        assertTrue(b.isPlaying());
    }
    
    @Test
    public void moveSnakeRight() {
        //we set the direction and tick once to make sure it moves up as we expect
        if (b.getBoard()[7][10] == BoardState.FOOD) {
            b.setFood(0, 0);
        }
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        BoardState[][] board = b.getBoard();
        assertEquals(5, b.getSnake().getDeque().size());
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        assertEquals(BoardState.SNAKE, board[7][10]);
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(1, foodCount); //again making sure moving up does not 
        //break already working conditions.
        assertEquals(Direction.RIGHT, b.getSnake().getDirection()); //we should still be 
        //going right, as game does not pause
        assertTrue(b.isPlaying());
    }
    
    @Test
    public void moveSnakeDown() {
        //this has been blocked out in board.java when a key is pressed when game is starting
        //so we move snake first to test this.
        //also a good test for moving in two different directions
        if (b.getBoard()[7][10] == BoardState.FOOD) {
            b.setFood(0, 0);
        }
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        if (b.getBoard()[8][10] == BoardState.FOOD) {
            b.setFood(0, 0);
        }
        b.setSnakeDirection(Direction.DOWN);
        b.tick();
        BoardState[][] board = b.getBoard();
        assertEquals(5, b.getSnake().getDeque().size());
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[8][10]);
        assertEquals(BoardState.SNAKE, board[7][10]);
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(1, foodCount); //again making sure moving up does not 
        //break already working conditions.
        assertEquals(Direction.DOWN, b.getSnake().getDirection()); //we should still be 
        //going down, as game does not pause
        assertTrue(b.isPlaying());
    }
    
    @Test
    public void moveSnakeLeft() {
        //we set the direction and tick once to make sure it moves up as we expect
        if (b.getBoard()[7][8] == BoardState.FOOD) {
            b.setFood(0, 0);
        }
        b.setSnakeDirection(Direction.LEFT);
        b.tick();
        BoardState[][] board = b.getBoard();
        assertEquals(5, b.getSnake().getDeque().size());
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        assertEquals(BoardState.SNAKE, board[7][8]);
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(1, foodCount); //again making sure moving up does not 
        //break already working conditions.
        assertEquals(Direction.LEFT, b.getSnake().getDirection()); //we should still be 
        //going down, as game does not pause
        assertTrue(b.isPlaying()); //make sure we still playing 
    }
    
    @Test 
    public void growSnakeUp() {
      //we set the direction and tick once to make sure it moves up as we expect
        b.setFood(9, 6);
        b.setSnakeDirection(Direction.UP);
        b.tick(); //should be growing here
        BoardState[][] board = b.getBoard();
        assertEquals(6, b.getSnake().getDeque().size());
        assertEquals(BoardState.SNAKE, board[6][9]);
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        assertEquals(BoardState.SNAKE, board[11][9]);
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(1, foodCount); //again making sure moving up does not 
        //break already working conditions, food should respawn somewhere else
        //not disappear
        assertEquals(10, b.getScore());
        assertEquals(Direction.UP, b.getSnake().getDirection()); //we should still be 
        //going up, as game does not pause
        assertTrue(b.isPlaying());
    }
    
    @Test 
    public void growSnakeRight() {
      //we set the direction and tick once to make sure it moves up as we expect
        b.setFood(10, 7);
        b.setSnakeDirection(Direction.RIGHT);
        b.tick(); //should be growing here
        BoardState[][] board = b.getBoard();
        assertEquals(6, b.getSnake().getDeque().size());
        assertEquals(BoardState.SNAKE, board[7][10]);
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        assertEquals(BoardState.SNAKE, board[11][9]);
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(10, b.getScore());
        assertEquals(1, foodCount); //again making sure moving up does not 
        //break already working conditions, food should respawn somewhere else
        //not disappear
        assertEquals(Direction.RIGHT, b.getSnake().getDirection()); //we should still be 
        //going right, as game does not pause
        assertTrue(b.isPlaying());
    }
    
    @Test
    public void moveThenGrow() {
        //since we can't physically move down upon construction, we can test multiple 
        //moves here
        if (b.getBoard()[7][10] == BoardState.FOOD) {
            b.setFood(0, 0);
        }
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        b.setFood(10, 8);
        b.setSnakeDirection(Direction.DOWN);
        b.tick();
        BoardState[][] board = b.getBoard();
        assertEquals(6, b.getSnake().getDeque().size());
        assertEquals(BoardState.SNAKE, board[8][10]);
        assertEquals(BoardState.SNAKE, board[7][10]);
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(10, b.getScore());
        assertEquals(1, foodCount); //again making sure moving up does not 
        //break already working conditions, food should respawn somewhere else
        //not disappear
        assertEquals(Direction.DOWN, b.getSnake().getDirection()); //we should still be 
        //going down, as game does not pause
        assertTrue(b.isPlaying());
    }
    
    @Test 
    public void growSnakeLeft() {
      //we set the direction and tick once to make sure it moves up as we expect
        b.setFood(8, 7);
        b.setSnakeDirection(Direction.LEFT);
        b.tick(); //should be growing here
        BoardState[][] board = b.getBoard();
        assertEquals(6, b.getSnake().getDeque().size());
        assertEquals(BoardState.SNAKE, board[7][8]);
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        assertEquals(BoardState.SNAKE, board[11][9]);
        int foodCount = 0;
        for (int i = 0; i < b.getBoard().length; i++) {
            for (int j = 0; j < b.getBoard().length; j++) {
                if (b.getBoard()[i][j] == BoardState.FOOD) {
                    foodCount++;
                }
            }
        }
        assertEquals(10, b.getScore());
        assertEquals(1, foodCount); //again making sure moving up does not 
        //break already working conditions, food should respawn somewhere else
        //not disappear
        assertEquals(Direction.LEFT, b.getSnake().getDirection()); //we should still be 
        //going left, as game does not pause
        assertTrue(b.isPlaying());
    }
    
    @Test
    public void goodFoodSpawn() {
        //we are making sure food never spawns in our snake.
        //there is a 5/400 chance for food to spawn in our snake randomly
        //so in expectation, 80 randomizes before it spawns in our snake.
        //so, we will try 1000 times which is more than enough.
        boolean inSnake = false;
        BoardState[][] board = b.getBoard();
        for (int i = 0; i < 1000; i++) {
            b.setFood(b.randomize());
            if (board[b.getFood().getY()][b.getFood().getX()] == BoardState.SNAKE) {
                //we know our board updates right as tested earlier
                inSnake = true;
            }
        }
        assertFalse(inSnake);
    }
    
    //test game end (Losing)
    @Test
    public void outRightTest() {
        b.setSnakeDirection(Direction.RIGHT);
        for (int i = 0; i < 20; i++) {
            b.tick();
        }
        assertFalse(b.isPlaying());
    }
    
    @Test
    public void outLeftTest() {
        b.setSnakeDirection(Direction.LEFT);
        for (int i = 0; i < 20; i++) {
            b.tick();
        }
        assertFalse(b.isPlaying());
    }
    
    @Test
    public void outUpTest() {
        b.setSnakeDirection(Direction.UP);
        for (int i = 0; i < 20; i++) {
            b.tick();
        }
        assertFalse(b.isPlaying());
    }
    
    @Test
    public void outDownTest() {
        b.setSnakeDirection(Direction.LEFT);
        b.tick();
        b.setSnakeDirection(Direction.DOWN);
        for (int i = 0; i < 20; i++) {
            b.tick();
        }
        assertFalse(b.isPlaying());
    }
    
    //collision tests crashing into different tiles
    
    @Test public void collisionTestRight() {
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.LEFT);
        b.tick();
        b.setSnakeDirection(Direction.DOWN);
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        assertFalse(b.isPlaying());
    }
    
    @Test public void collisionTestLeft() {
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        b.setSnakeDirection(Direction.DOWN);
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.LEFT);
        b.tick();
        assertFalse(b.isPlaying());
    }
    
    @Test public void collisionTestUp() {
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        b.tick();
        b.setSnakeDirection(Direction.DOWN);
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.LEFT);
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.UP);
        b.tick();
        assertFalse(b.isPlaying());
    }
    
    @Test public void collisionTestDown() {
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        b.tick();
        b.setSnakeDirection(Direction.UP);
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.LEFT);
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.DOWN);
        b.tick();
        assertFalse(b.isPlaying());
    }
    
    @Test 
    public void collideTail() {
        b.setFood(9, 6);
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.UP);
        b.tick();
        b.setFood(8, 6);
        b.setSnakeDirection(Direction.LEFT);
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.DOWN);
        b.tick();
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        assertFalse(b.isPlaying());
    }
    
    @Test
    public void noCollision() {
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.UP);
        b.tick();
        b.setSnakeDirection(Direction.RIGHT);
        b.tick();
        assertTrue(b.isPlaying());
        b.setSnakeDirection(Direction.DOWN);
        b.tick();
        assertTrue(b.isPlaying());
    }
    
    @Test
    public void aiTest() {
        //not much testing needed since it is not really part of my game, 
        //and the BFS works for sure.
        b.flipAI();
        assertTrue(b.aiMode());
        b.setFood(10, 6);
        b.tick();
        BoardState[][] board = b.getBoard(); //aimove
        assertEquals(BoardState.SNAKE, board[7][9]);
        assertEquals(BoardState.SNAKE, board[8][9]);
        assertEquals(BoardState.SNAKE, board[9][9]);
        assertEquals(BoardState.SNAKE, board[10][9]);
        assertEquals(BoardState.SNAKE, board[6][9]);
        b.tick();
        BoardState[][] board2 = b.getBoard(); //aigrow
        assertEquals(BoardState.SNAKE, board2[6][10]);
        assertEquals(BoardState.SNAKE, board2[7][9]);
        assertEquals(BoardState.SNAKE, board2[7][9]);
        assertEquals(BoardState.SNAKE, board2[8][9]);
        assertEquals(BoardState.SNAKE, board2[9][9]);
        assertEquals(BoardState.SNAKE, board2[10][9]);
        assertEquals(BoardState.SNAKE, board2[6][9]);
        b.setFood(10, 5);
        b.tick();
        b.setFood(10, 4);
        b.tick();
        b.setFood(10, 3);
        b.tick();
        b.setFood(10, 2);
        b.tick();
        b.setFood(10, 1);
        b.tick();
        b.setFood(10, 0);
        b.tick();
        b.setFood(11, 0);
        b.tick();
        b.setFood(12, 0);
        b.tick();
        b.setFood(12, 1);
        b.tick();
        b.setFood(12, 2);
        b.tick();
        b.setFood(12, 2);
        b.tick();
        b.setFood(11, 2);
        b.tick();
        b.setFood(11, 1);
        b.tick(); //AI is trapped
        assertFalse(b.isPlaying());
        assertFalse(b.aiMode());
    }

}
