package map;

import java.util.*;

public class Edge {
    private Graph parent;
    private Node firstVertex;
    private Node secondVertex;
    private HashSet<Node> vertices;

    public Edge(Graph parent, Node firstNode, Node secondNode) {
        this.parent = parent;
        this.firstVertex = firstNode;
        this.secondVertex = secondNode;
        this.vertices = new HashSet<>(Arrays.asList(firstNode, secondNode));
    }

    public Graph getParent() {
        return parent;
    }

    public HashSet<Node> getVertices() {
        return vertices;
    }

    public Node getFirstVertex() {
        return firstVertex;
    }

    public Node getSecondVertex() {
        return secondVertex;
    }

    @Override
    public String toString() {
        return String.format("Edge between N = (" + firstVertex.getPosition().x + ", " + firstVertex.getPosition().y + ") and N = (" + secondVertex.getPosition().x + ", " + secondVertex.getPosition().y + ").");
    }
}
