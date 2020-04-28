import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Iterator;

public class Board extends JPanel {
    private Snake snake;
    private BoardState[][] board;
    private Coordinate food;
    private JLabel status;
    private JLabel scoreLabel;
    private boolean playing = false;
    private int score;
    private boolean aiMode = false;
    
    public Board(JLabel status, JLabel score) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); 

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (snake.getDirection() == Direction.RIGHT) {
                        return;
                    }
                    snake.setDirection(Direction.LEFT);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (snake.getDirection() == Direction.LEFT) {
                        return;
                    }
                    snake.setDirection(Direction.RIGHT);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (snake.getDirection() == Direction.UP) {
                        return;
                    }
                    snake.setDirection(Direction.DOWN);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (snake.getDirection() == Direction.DOWN) {
                        return;
                    }
                    snake.setDirection(Direction.UP);
                }
            }
        });
        this.scoreLabel = score;
        this.status = status;
    }
    
    
    public void reset() {
        snake = new Snake();
        board = new BoardState[20][20];
        food = this.randomize();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = BoardState.EMPTY;
            }
        }
        board[food.getX()][food.getY()] = BoardState.FOOD;
        board[7][9] = BoardState.SNAKE;
        board[8][9] = BoardState.SNAKE;
        board[9][9] = BoardState.SNAKE;
        board[10][9] = BoardState.SNAKE;
        board[11][9] = BoardState.SNAKE;
        playing = true;
        aiMode = false;
        score = 0;
        status.setText("Running... | ");
        repaint();
        requestFocusInWindow();
    }
    
    public Coordinate randomize() {
        int x = (int) (Math.random() * 20);
        int y = (int) (Math.random() * 20);
        Coordinate ret = new Coordinate(x, y);
        if (snake.isInSnake(ret)) {
            return this.randomize();
        }
        return ret;
    }
    
    public void flipAI() {
        aiMode = !aiMode;
    }
    
    public void giveup() {
        playing = false;
        aiMode = false;
        status.setText("AI gives up :(");
        return;
    }
    
    
    void tick() {
        if (aiMode) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = BoardState.EMPTY;
                }
            }
            Deque<Coordinate> deque = snake.getDeque();
            for (Coordinate c : deque) {
                board[c.getY()][c.getX()] = BoardState.SNAKE;
            }
            //no direction needed
            Coordinate next = snake.aiNext(food, board);
            if (next == null) {
                giveup();
                return;
            }
            if (next.equals(food)) {
                snake.aiMove(food, board, true);
                Coordinate newFood = this.randomize();
                this.score += 10;
                this.food = newFood;
            } else {
                snake.aiMove(this.food, this.board, false);
            }
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = BoardState.EMPTY;
                }
            }
            board[food.getY()][food.getX()] = BoardState.FOOD;
            // check for the game end conditions
            if (snake.getHead().getX() < 0 || snake.collision()
                    || snake.getHead().getX() >= board.length 
                    || snake.getHead().getY() < 0
                    || snake.getHead().getY() >= board.length ) {
                playing = false;
                aiMode = false;
                status.setText("AI loses!");
                return;
            } 
            Deque<Coordinate> deque2 = snake.getDeque();
            for (Coordinate c : deque2) {
                board[c.getY()][c.getX()] = BoardState.SNAKE;
            }
            // update the display
            repaint();
            return;
        }
        if (playing) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = BoardState.EMPTY;
                }
            }
            if (snake.getDirection() == Direction.WAITING) {
                return;
            }
            if (snake.next().equals(food)) {
                snake.grow();
                Coordinate newFood = this.randomize();
                this.score += 10;
                this.food = newFood;
            } else {
                snake.move();
            }
            board[food.getY()][food.getX()] = BoardState.FOOD;
            // check for the game end conditions
            if (snake.getHead().getX() < 0 || snake.collision()
                    || snake.getHead().getX() >= board.length 
                    || snake.getHead().getY() < 0
                    || snake.getHead().getY() >= board.length ) {
                playing = false;
                status.setText("You lose!");
                return;
            } 
            Deque<Coordinate> deque = snake.getDeque();
            for (Coordinate c : deque) {
                board[c.getY()][c.getX()] = BoardState.SNAKE;
            }
            // update the display
            repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == BoardState.EMPTY) {
                        g.setColor(Color.WHITE);
                        g.fillRect(j * 20, i * 20, 
                                20, 20);
                }
                if (board[i][j] == BoardState.SNAKE) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * 20, i * 20, 
                            20, 20);
                }
                if (board[i][j] == BoardState.FOOD) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(j * 20, i * 20, 
                            20, 20);
                }
            }
        }
        scoreLabel.setText("Score = " + this.score);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
    
}
