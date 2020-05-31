package Supermarket.Interfaces;

import Supermarket.Agent;

import java.util.List;
import java.util.Vector;

public interface Caja {
    public int whereToGo();
    public Vector position(int index);
    public boolean hasFreeSpace(int index);
    public List<Integer> status();
    public void add(int index , int elems , Agent agent);
    Vector getPositionOf(int agentid);
    Vector getSectorPosition();
    public static Caja getInstance() {
        return null;
    }
}
