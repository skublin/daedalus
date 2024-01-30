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

    private void addNeighbour(Node neighbour) {
        this.neighbours.add(neighbour);
        Edge e = new Edge(parent, this, neighbour);
        this.edges.add(e);
    }
}
