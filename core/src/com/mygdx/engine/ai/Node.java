package com.mygdx.engine.ai;

public class Node implements Comparable<Node> {
    public int x;
    public int y;
    public boolean walkable;

    public Node parent;
    public double gCost; // Cost from start node
    public double hCost; // Heuristic - estimated cost to end node
    public double fCost; // Total cost (gCost + hCost)

    public Node(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.fCost, other.fCost);
    }
}
