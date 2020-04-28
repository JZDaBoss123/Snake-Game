import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Deque;

final public class BFS {
    private BFS() {}
    static Coordinate[][] parent;
    static boolean[][] discovered;
    static Deque<Coordinate> deque;
    static int width;
    static int height;
    static BoardState[][] gameArray;

    public static List<Coordinate> snakeFind(BoardState[][] board, Coordinate head, Coordinate food) {
        gameArray = board;
        parent = new Coordinate[board.length][board[0].length];
        discovered = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                discovered[i][j] = false;
            }
        }
        width = board[0].length;
        height = board.length;
        bfs(head);
        List<Coordinate> retList = new LinkedList<Coordinate>();
        Coordinate current = food;
        while (current != head) {
            if (current == null) {
                return new LinkedList<Coordinate>();
            } else {
                retList.add(current);
                current = parent[current.getY()][current.getX()];
            }
        }
        retList.add(head);
        Collections.reverse(retList);
        return retList;
    }
    
    
    public static void bfs(Coordinate head) {
        deque = new LinkedList<Coordinate>();
        deque.addFirst(head);
        discovered[head.getY()][head.getX()] = true;
        Coordinate curr = null;
        Coordinate[] neighbors = new Coordinate[4];
        Coordinate temp = null;
        int numNeighbors = 0;
        while (deque.size() > 0) {
            curr = deque.pollFirst();
            if (curr.getX() == 0) {
                if (curr.getY() == 0) {
                    neighbors[numNeighbors] = new Coordinate(curr.getX() + 1, 0);
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), 1);
                    numNeighbors++;
                } else if (curr.getY() == height - 1) {
                    neighbors[numNeighbors] = new Coordinate(curr.getX() + 1, curr.getY());
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() - 1);
                    numNeighbors++;
                } else {
                    //left column, any height
                    neighbors[numNeighbors] = new Coordinate(curr.getX() + 1, curr.getY());
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() + 1);
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() - 1);
                    numNeighbors++;
                }
            } else if (curr.getY() == 0) {
                if (curr.getX() == width - 1) {
                    neighbors[numNeighbors] = new Coordinate(curr.getX() - 1, curr.getY());
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() + 1);
                    numNeighbors++;
                } else {
                    //top row, not left column
                    neighbors[numNeighbors] = new Coordinate(curr.getX() + 1, curr.getY());
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() + 1);
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX() - 1, curr.getY());
                    numNeighbors++;
                }
            } else if (curr.getX() == width - 1) {
                if (curr.getY() == height - 1) {
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() - 1);
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX() - 1, curr.getY());
                    numNeighbors++;
                } else {
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() - 1);
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX() - 1, curr.getY());
                    numNeighbors++;
                    neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() + 1);
                    numNeighbors++;
                }
            } else if (curr.getY() == height - 1) {
                neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() - 1);
                numNeighbors++;
                neighbors[numNeighbors] = new Coordinate(curr.getX() - 1, curr.getY());
                numNeighbors++;
                neighbors[numNeighbors] = new Coordinate(curr.getX() + 1, curr.getY());
                numNeighbors++;
            } else {
                neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() - 1);
                numNeighbors++;
                neighbors[numNeighbors] = new Coordinate(curr.getX() - 1, curr.getY());
                numNeighbors++;
                neighbors[numNeighbors] = new Coordinate(curr.getX() + 1, curr.getY());
                numNeighbors++;
                neighbors[numNeighbors] = new Coordinate(curr.getX(), curr.getY() + 1);
                numNeighbors++;
            }
            
            for (int i = 0; i < numNeighbors; i++) {
                temp = neighbors[i];
                if (temp == null) {
                    continue;
                }
                if (!discovered[temp.getY()][temp.getX()] && 
                        gameArray[temp.getY()][temp.getX()] != BoardState.SNAKE) {
                    discovered[temp.getY()][temp.getX()] = true;
                    deque.addLast(temp);
                    parent[temp.getY()][temp.getX()] = curr;
                }
            }
            neighbors = new Coordinate[4];
            numNeighbors = 0;
        }
    }
}