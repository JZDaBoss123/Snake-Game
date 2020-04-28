
public class Coordinate {
    private int x;
    private int y;
    
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    
    
    @Override
    public boolean equals(Object obj) { 
        if (obj == this) { 
            return true; 
        }

        Coordinate coord = (Coordinate) obj; 

        return this.x == coord.getX() && this.y == coord.getY();
    }
}
