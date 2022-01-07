package rushhour;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Algorithm {

    public String BFS(Board input) {
        Queue<Board> queue = new LinkedList<>();
        HashMap<String, Integer> visited = new HashMap<>();
        queue.add(input);
        visited.put(input.getKey(), 1);
        while (!queue.isEmpty()) {
            Board temp = queue.poll();
            List<Board> l = temp.successor();
            for (Board board : l) {
                if (board.isGoal()) {
                    return board.getSloution();
                }
                if (!visited.containsKey(board.getKey())) {
                    queue.add(board);
                    visited.put(board.getKey(), 1);
                }
            }
        }
        return "NOT Solved";
    }

    public String DFS(Board input) {
        Stack<Board> stack = new Stack<>();
        HashMap<String, Integer> visited = new HashMap<>();a
        stack.add(input);
        visited.put(input.getKey(), 1);
        while (!stack.isEmpty()) {
            Board temp = stack.pop();
            List<Board> l = temp.successor();
            for (Board board : l) {
                if (board.isGoal()) {
                    return board.getSloution();
                }
                if (!visited.containsKey(board.getKey())) {
                    stack.add(board);
                    visited.put(board.getKey(), 1);
                }
            }
        }
        return "NOT Solved";
    }

    public String A_Star(Board input) {
        Comparator<Board> boardComparator = new Comparator<Board>() {
            @Override
            public int compare(Board s1, Board s2) {
                return s1.getfScore() - s2.getfScore();
            }
        };
        PriorityQueue<Board> priorityQueue = new PriorityQueue<>(boardComparator);
        HashMap<String, Integer> visited = new HashMap<>();
        priorityQueue.add(input);
        visited.put(input.getKey(), 1);
        while (!priorityQueue.isEmpty()) {
            Board temp = priorityQueue.poll();
            List<Board> l = temp.successor();
            for (Board board : l) {
                if (board.isGoal()) {
                    return board.getSloution();
                }
                if (!visited.containsKey(board.getKey())) {
                    // Here the program compute the f score and assign the score to the board
                    // object.
                    int h = heuristic(board);
                    int g = temp.getgScore() + 1;
                    board.setfScore(h + g);
                    board.setgScore(g);
                    priorityQueue.add(board);
                    visited.put(board.getKey(), 1);
                }
            }
        }
        return "NOT Solved";
    }

    public int heuristic(Board board) {
        // In this method, I use the 'Count freedom of movement' heuristic
        // Count freedom of movement: Count the number of cars that have a move.
        // Similarly, count
        // for each car how many moves it has.
        return board.getFreedomCount();
    }
}
