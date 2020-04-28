import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel {
    private Snake snake;
    private BoardState[][] board;
    private Coordinate food;
    private JLabel status;
    private boolean playing = false;
    
    public Board(JLabel status) {
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

        this.status = status;
    }
    
    
    public void reset() {
        snake = new Snake();
        board = new BoardState[20][20];
        food = this.randomize();
        board[food.getX()][food.getY()] = BoardState.FOOD;
        board[9][8] = BoardState.SNAKE;
        board[9][9] = BoardState.SNAKE;
        board[9][10] = BoardState.SNAKE;
        board[9][7] = BoardState.SNAKE;
        board[9][11] = BoardState.SNAKE;
        playing = true;
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
    
    public void emptify(Coordinate x) {
        board[x.getX()][x.getY()] = BoardState.EMPTY;
    }
    
    void tick() {
        if (playing) {
            // advance the square and snitch in their current direction.
            Snake copy = snake;
            copy.move();
            if (copy.getHead().equals(food)) {
                Coordinate x = snake.grow();
                board[x.getX()][x.getY()] = BoardState.SNAKE;
                board[food.getX()][food.getY()] = BoardState.EMPTY;
                Coordinate newFood = this.randomize();
                board[newFood.getX()][newFood.getY()] = BoardState.FOOD;
            } else {
                Coordinate x = snake.move();
                this.emptify(x);
            }
            // check for the game end conditions
            Coordinate head = snake.getHead();
            if (snake.getHead().getX() < 0
                    || snake.getHead().getX() >= board.length 
                    || snake.getHead().getY() < 0
                    || snake.getHead().getY() >= board.length ) {
                playing = false;
                status.setText("You lose!");
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
                        g.fillRect(i * 20, j * 20, 
                                20, 20);
                }
                if (board[i][j] == BoardState.SNAKE) {
                    g.setColor(Color.BLUE);
                    g.fillRect(i * 20, j * 20, 
                            20, 20);
                }
                if (board[i][j] == BoardState.FOOD) {
                    g.setColor(Color.YELLOW);
                    g.fillOval(i * 20, j * 20, 
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
