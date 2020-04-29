import java.util.Deque;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

public class Snake {
    private Direction d;
    private Deque<Coordinate> body;
    
    public Snake() {
        //create a new snake when playing
        body = new LinkedList<Coordinate>();
        body.addFirst(new Coordinate(9, 11));
        body.addFirst(new Coordinate(9, 10));
        body.addFirst(new Coordinate(9, 9));
        body.addFirst(new Coordinate(9, 8));
        body.addFirst(new Coordinate(9, 7));
        d = Direction.WAITING;
    }
    
    public void setDirection(Direction x) {
        this.d = x;
    }
    
    public Coordinate next() {
        Coordinate head = body.peekFirst();
        switch (d) {
        case WAITING: 
            return null;
        case UP:
            return new Coordinate(head.getX(), head.getY() - 1);
        case DOWN:
            return new Coordinate(head.getX(), head.getY() + 1);
        case RIGHT:
            return new Coordinate(head.getX() + 1, head.getY());
        case LEFT:
            return new Coordinate(head.getX() - 1, head.getY());
        }
    return null;
    }
    
    public Deque<Coordinate> getDeque() {
        return this.body;
    }
    
    public Direction getDirection() {
        return this.d;
    }
    
    public Coordinate getHead() {
        return body.peekFirst();
    }
    
    public boolean isInSnake(Coordinate x) {
        Deque<Coordinate> copy = body;
        for (Coordinate c : copy) {
            if (c.equals(x)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean collision() {
        Iterator<Coordinate> iter = body.iterator();
        iter.next();
        while (iter.hasNext()) {
            if (this.getHead().equals(iter.next())) {
                return true;
            }
        }
        return false;
    }
    
    public void move() {
        Coordinate head = body.peekFirst();
        switch (d) {
            case WAITING:
                return;
            case UP:
                body.addFirst(new Coordinate(head.getX(), head.getY() - 1));
                body.removeLast();
                return;
            case DOWN:
                body.addFirst(new Coordinate(head.getX(), head.getY() + 1));
                body.removeLast();
                return;
            case RIGHT:
                body.addFirst(new Coordinate(head.getX() + 1, head.getY()));
                body.removeLast();
                return;
            case LEFT:
                body.addFirst(new Coordinate(head.getX() - 1, head.getY()));
                body.removeLast();
                return;
        }
    }
    
    public void grow() {
        Coordinate head = body.peekFirst();
        switch (d) {
            case WAITING:
                return;
            case UP:
                body.addFirst(new Coordinate(head.getX(), head.getY() - 1));
                break;
            case DOWN:
                body.addFirst(new Coordinate(head.getX(), head.getY() + 1));
                break;
            case RIGHT:
                body.addFirst(new Coordinate(head.getX() + 1, head.getY()));
                break;
            case LEFT:
                body.addFirst(new Coordinate(head.getX() - 1, head.getY()));
                break;
        }
    }
    
    public void aiMove(Coordinate food, BoardState[][] board, boolean grow) {
        Coordinate head = body.peekFirst();
        List<Coordinate> path = BFS.snakeFind(board, head, food);
        if (path.size() == 0|| path.get(1) == null) {
            body.addFirst(new Coordinate(head.getX(), head.getY() - 1));
        } else {
            body.addFirst(path.get(1));
        }
        if (!grow) {
            body.removeLast();
        }
    }
    
    public Coordinate aiNext(Coordinate food, BoardState[][] board) {
        Coordinate head = body.peekFirst();
        List<Coordinate> path = BFS.snakeFind(board, head, food);
        if (path.size() == 0) {
            return null;
        }
        return path.get(1);
    }
    
}
