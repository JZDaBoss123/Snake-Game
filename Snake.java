import java.util.Deque;
import java.util.LinkedList;

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
        d = Direction.UP;
    }
    
    public void setDirection(Direction x) {
        this.d = x;
    }
    
    public Coordinate next() {
        Coordinate head = body.peekFirst();
        switch (d) {
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
        for (int i = 0; i < body.size(); i++) {
            if (copy.pollFirst().equals(x)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean collision(Coordinate x) {
        Deque<Coordinate> copy = body;
        copy.pollFirst();
        while (!copy.isEmpty()) {
            if (copy.pollFirst().equals(x)) {
                return true;
            }
        }
        return false;
    }
    
    public Coordinate move() {
        Coordinate head = body.peekFirst();
        switch (d) {
            case UP:
                body.addFirst(new Coordinate(head.getX(), head.getY() - 1));
                return body.pollLast();
            case DOWN:
                body.addFirst(new Coordinate(head.getX(), head.getY() + 1));
                return body.pollLast();
            case RIGHT:
                body.addFirst(new Coordinate(head.getX() + 1, head.getY()));
                return body.pollLast();
            case LEFT:
                body.addFirst(new Coordinate(head.getX() - 1, head.getY()));
                return body.pollLast();
        }
        return null; //dummy
    }
    
    public Coordinate grow() {
        Coordinate head = body.peekFirst();
        switch (d) {
            case UP:
                body.addFirst(new Coordinate(head.getX(), head.getY() - 1));
                return body.peekFirst();
            case DOWN:
                body.addFirst(new Coordinate(head.getX(), head.getY() + 1));
                return body.peekFirst();
            case RIGHT:
                body.addFirst(new Coordinate(head.getX() + 1, head.getY()));
                return body.peekFirst();
            case LEFT:
                body.addFirst(new Coordinate(head.getX() - 1, head.getY()));
                return body.peekFirst();
        }
        return null; //dummy
    }
}
