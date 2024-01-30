package map;

import java.util.*;

public class Edge {
    private Graph parent;
    private HashSet<Node> vertices;

    public Edge(Graph parent, Node firstNode, Node secondNode) {
        this.parent = parent;
        this.vertices = new HashSet<>(Arrays.asList(firstNode, secondNode));
    }
}
