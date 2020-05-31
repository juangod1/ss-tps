package Supermarket.Interfaces;

import java.util.Vector;

public interface Graph {

    Vector nextPoint(Vector from, Vector to);

    Vector goBackToAisle(Vector from);

    static Graph getInstance() {
        return null;
    }
}
