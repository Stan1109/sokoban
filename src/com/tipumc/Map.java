package com.tipumc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Vector;

public final class Map {

    public Map(Vector<String> map)
    {
        startMap = map;
    }
    

    public boolean isEmpty(int x, int y)
    {
        char c = startMap.get(y).charAt(x);
        return (c == ' ');
        
        
    }

    public boolean isWall(int x, int y)
    {
        char c = startMap.get(y).charAt(x);
        return (c == '#');
        
        
    }

    public boolean isGoal(int x, int y)
    {
        char c = startMap.get(y).charAt(x);
        return (c == '.' | c=='*');
    }

    /**
     *
     * @return String representing the map
     */
    public String toString()
    {
        String out = new String("");
        for (int i = 0; i < startMap.size(); i++) {
            if (i==0) {
                out += startMap.get(i);
            } else {
                out += System.getProperty("line.separator")+startMap.get(i);
            }
        }
        return out;
    }
    
    private Vector<String> startMap;
}
