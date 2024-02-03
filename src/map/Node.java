package map;

import java.util.*;

public class Node {
    public Graph parent;
    public HashSet<Edge> edges;
    public HashSet<Node> neighbours;
    public Position<Integer, Integer> position;
    public Boolean isVisited;

    public Node(Graph parent, Position<Integer, Integer> position) {
        this.parent = parent;
        this.edges = new HashSet<>();
        this.neighbours = new HashSet<>();
        this.position = position;
        this.isVisited = false;
    }

    public Graph getParent() {
        return parent;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public Position<Integer, Integer> getPosition() {
        return position;
    }

    public void addNeighbour(Node neighbour, Boolean createEdge) {
        this.neighbours.add(neighbour);
        if (createEdge) {
            Edge e = new Edge(parent, this, neighbour);
            this.edges.add(e);
        }
    }

    public void removeNeighbour(Node neighbour) {
        this.neighbours.remove(neighbour);
    }

    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
    }

    public void setAsVisited() {
        this.isVisited = true;
    }

    public HashSet<Node> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return String.format("Node (" + position.x + ", " + position.y + "), with " + neighbours.size() + " neighbours, on G = " + parent);
    }
}
