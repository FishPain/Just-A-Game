package com.mygdx.engine.ai;

import java.util.List;
import java.util.ArrayList;


public class GridMap {
    private Node[][] grid;
    private int width;
    private int height;

    public GridMap(int width, int height, boolean[][] walkableMap) {
        this.width = width;
        this.height = height;
        grid = new Node[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Node(x, y, walkableMap[x][y]);
            }
        }
    }

    public Node getNode(int x, int y) {
        return grid[x][y];
    }

    public List<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip the node itself
                int newX = node.x + dx;
                int newY = node.y + dy;

                if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                    neighbors.add(grid[newX][newY]);
                }
            }
        }

        return neighbors;
    }
}
