package map;

import java.util.*;

public class Node {
    private Graph parent;
    private HashSet<Edge> edges;
    private HashSet<Node> neighbours;
    private Position<Integer, Integer> position;

    public Node(Graph parent, Position<Integer, Integer> position) {
        this.parent = parent;
        this.edges = new HashSet<>();
        this.neighbours = new HashSet<>();
        this.position = position;
    }

    public Graph getParent() {
        return parent;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public HashSet<Node> getNeighbours() {
        return neighbours;
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

    @Override
    public String toString() {
        return String.format("Node (" + position.x + ", " + position.y + "), with " + neighbours.size() + " neighbours, on G = " + parent);
    }
}
