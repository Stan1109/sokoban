package com.tipumc;



import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;
import java.util.Stack;

public final class Search {

    public static final class Result
    {
        public Position endPosition;
        public ArrayList<Move> path;
    }

    public static Result dfs(State state, SearchTest test, int startX, int startY)
    {
        /* Create stack to store nodes */
        Stack<Position> nodes = new Stack<Position>();
        /* Create integers that is needed */
        int width = state.getWidth();
        int height = state.getHeight();
        /* Create 2D array to store visited positions */
        Move visitedPositions[][] = new Move[width][height];
        /* Declare an positionobject to pop to from stack */
        Position currentPosition = new Position(startX, startY);
        int x = currentPosition.x, y = currentPosition.y;
        
        /* Push the start position node, for the search on the stack */
        nodes.push(currentPosition);
        
        /* Search for a path to the wanted goal */
        while (!test.isEnd(state, currentPosition.x, currentPosition.y)){
            if(nodes.empty()){
                System.out.println("no path");
                return null;
            } else {
                currentPosition = nodes.pop();
            }
            
            x = currentPosition.x;
            y = currentPosition.y;
            
            /* Create child nodes */
            if (visitedPositions[x][y-1] == null && (state.isFree(x, y-1) || test.isEnd(state, x, y-1))){
                Position possibleStep = new Position(x, y-1);
                visitedPositions[x][y-1] = Move.UP;
                nodes.push(possibleStep);
            }
            if (visitedPositions[x][y+1] == null && (state.isFree(x, y+1) || test.isEnd(state, x, y+1))){
                Position possibleStep = new Position(x, y+1);
                visitedPositions[x][y+1] = Move.DOWN;
                nodes.push(possibleStep);
            }
            if (visitedPositions[x-1][y] == null && (state.isFree(x-1, y) || test.isEnd(state, x-1, y))){
                Position possibleStep = new Position(x-1, y);
                visitedPositions[x-1][y] = Move.LEFT;
                nodes.push(possibleStep);
            }
            if (visitedPositions[x+1][y] == null && (state.isFree(x+1, y) || test.isEnd(state, x+1, y))){
                Position possibleStep = new Position(x+1, y);
                visitedPositions[x+1][y] = Move.RIGHT;
                nodes.push(possibleStep);
            }
        }

        Result result = new Result();
        result.path = getPath(visitedPositions, x, y, startX, startY);
        result.endPosition = new Position(x, y);
        return result;
    }

    private static ArrayList<Move> getPath(Move[][] moves, int fromX, int fromY, int toX, int toY)
    {
        int x = fromX, y = fromY;
        ArrayList<Move> path = new ArrayList<Move>();
        Position dir = new Position();
        while (x != toX && y != toY)
        {
            Move move = moves[x][y];
            path.add(move);
            System.err.println(x + " " + y);
            move.getDirection(dir);
            x -= dir.x;
            y -= dir.y;
        }
        return path;
    }

    public static ArrayList<Position> findAll(State state, SearchTest test, int startX, int startY)
    {
        /* Create stack to store nodes */
        Stack<Position> nodes = new Stack<Position>();
        /* Create integers that is needed */
        int width = state.getWidth();
        int height = state.getHeight();
        /* Create 2D array to store visited positions */
        Move visitedPositions[][] = new Move[width][height];
        /* Declare an positionobject to pop to from stack */
        Position currentPosition = new Position(startX, startY);
        int x = currentPosition.x, y = currentPosition.y;

        /* Push the start position node, for the search on the stack */
        nodes.push(currentPosition);

        ArrayList<Position> result = new ArrayList<Position>();
        /* Search for a path to the wanted goal */
        while (!nodes.empty()){
            currentPosition = nodes.pop();
            x = currentPosition.x;
            y = currentPosition.y;
            if (test.isEnd(state, x, y))
            {
                result.add(new Position(x, y));
            }

            /* Create child nodes */
            testAddPosition(state, test, nodes, visitedPositions, x, y-1, Move.UP);
            testAddPosition(state, test, nodes, visitedPositions, x, y+1, Move.DOWN);
            testAddPosition(state, test, nodes, visitedPositions, x-1, y, Move.LEFT);
            testAddPosition(state, test, nodes, visitedPositions, x+1, y, Move.RIGHT);
        }

        return result;
    }

    private static void testAddPosition(State state, SearchTest test, Stack<Position> nodes, Move[][] visitedPositions, int x, int y, Move move)
    {
        if (visitedPositions[x][y] == null && (state.isFree(x, y) || test.isEnd(state, x, y))){
            Position possibleStep = new Position(x, y);
            visitedPositions[x][y] = Move.RIGHT;
            nodes.push(possibleStep);
        }
    }
}
