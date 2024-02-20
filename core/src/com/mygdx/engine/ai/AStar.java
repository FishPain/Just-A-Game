package com.mygdx.engine.ai;

import java.util.*;

public class AStar {
    private GridMap grid;

    public AStar(GridMap grid) {
        this.grid = grid;
    }

    public List<Node> findPath(int startX, int startY, int endX, int endY) {
        Node startNode = grid.getNode(startX, startY);
        Node endNode = grid.getNode(endX, endY);

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();

            if (currentNode == endNode) {
                return retracePath(startNode, endNode);
            }

            closedSet.add(currentNode);

            for (Node neighbor : grid.getNeighbors(currentNode)) {
                if (!neighbor.walkable || closedSet.contains(neighbor)) {
                    continue;
                }

                double newMovementCostToNeighbor = currentNode.gCost + getDistance(currentNode, neighbor);
                if (newMovementCostToNeighbor < neighbor.gCost || !openSet.contains(neighbor)) {
                    neighbor.gCost = newMovementCostToNeighbor;
                    neighbor.hCost = getDistance(neighbor, endNode);
                    neighbor.parent = currentNode;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<>(); // No path found
    }

    private List<Node> retracePath(Node startNode, Node endNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = endNode;

        while (currentNode != startNode) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }

        Collections.reverse(path);
        return path;
    }

    private double getDistance(Node nodeA, Node nodeB) {
        int distX = Math.abs(nodeA.x - nodeB.x);
        int distY = Math.abs(nodeA.y - nodeB.y);

        if (distX > distY)
            return 14 * distY + 10 * (distX - distY);
        return 14 * distX + 10 * (distY - distX);
    }
}


