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
    private boolean playing = false;
    
    public Board(JLabel status) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(150, new ActionListener() {
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

        this.status = status;
    }
    
    
    public void reset() {
        snake = new Snake();
//        Deque<Coordinate> d = snake.getDeque();
//        for (Coordinate c : d) {
//            System.out.println(c);
//        }
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
        requestFocusInWindow();
    }
    
    public Coordinate randomize() {
        int x = (int) (Math.random() * 20);
        int y = (int) (Math.random() * 20);
        Coordinate ret = new Coordinate(x, y);
        if (snake.isInSnake(ret)) {
            return this.randomize();
        }
        return  ret;
    }
    
    
    void tick() {
        if (playing) {
            // advance the square and snitch in their current direction.
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = BoardState.EMPTY;
                }
            }
            if (snake.collision(snake.next())) {
                playing = false;
                status.setText("You lose!");
                return;
            }
            if (snake.next().equals(food)) {
                snake.grow();
                Coordinate newFood = this.randomize();
                this.food = newFood;
            } else {
                snake.move();
            }
            board[food.getY()][food.getX()] = BoardState.FOOD;
            // check for the game end conditions
//            Coordinate head = snake.getHead();
            //snake.collision(head)
            if (snake.getHead().getX() < 0
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
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
    
}
