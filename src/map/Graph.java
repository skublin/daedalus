package map;

import java.util.*;

public class Graph {
    private HashSet<Node> nodes;
    private HashSet<Edge> edges;

    public Graph() {
        this.nodes = new HashSet<>();
        this.edges = new HashSet<>();
    }

    public void addVertex(Node vertex) {
        this.nodes.add(vertex);
    }

    public Node addNewVertex(Integer pX, Integer pY) {
        Node n = new Node(this, new Position<>(pX, pY));
        this.addVertex(n);

        return n;
    }

    public void removeVertex(Node vertex) {
        this.nodes.remove(vertex);
        List<Edge> edgesToRemove = new ArrayList<>();
        for (Edge e : edges) {
            if (e.getVertices().contains(vertex))
                edgesToRemove.add(e);
        }
        for (Edge e : edgesToRemove)
            this.removeEdge(e);
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public Edge addNewEdge(Node firstVertex, Node secondVertex) {
        Edge e = new Edge(this, firstVertex, secondVertex);
        this.addEdge(e);
        firstVertex.addNeighbour(secondVertex, false);
        secondVertex.addNeighbour(firstVertex, false);
        return e;
    }

    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
        Node n1 = edge.getFirstVertex();
        Node n2 = edge.getSecondVertex();
        n1.removeEdge(edge);
        n2.removeEdge(edge);
        n1.removeNeighbour(n2);
        n2.removeNeighbour(n1);
    }

    public void getGraphMatrix() {

    }

    public void showGraph() {
        System.out.println(this);
        for (Node n : nodes)
            System.out.println(n);
        for (Edge e : edges)
            System.out.println(e);
    }

    @Override
    public String toString() {
        return String.format("Graph with " + nodes.size() + " node(s) and " + edges.size() + " edge(s).");
    }

    public static void main(String[] args) {
        System.out.println("Test Graph class...");

        // create graph test
        System.out.println("Create new graph...");
        Graph g = new Graph();

        Node n1 = g.addNewVertex(1, 1);
        Node n2 = g.addNewVertex(4, 2);
        Node n3 = g.addNewVertex(2, 4);
        Node n4 = g.addNewVertex(0, 3);

        Edge e12 = g.addNewEdge(n1, n2);
        Edge e13 = g.addNewEdge(n1, n3);
        Edge e23 = g.addNewEdge(n2, n3);
        Edge e14 = g.addNewEdge(n1, n4);
        Edge e34 = g.addNewEdge(n3, n4);

        g.showGraph();

        // remove Edge test
        System.out.println("Remove edge from n1 to n4...");
        g.removeEdge(e14);
        g.showGraph();

        // remove Node
        System.out.println("Remove vertex n4...");
        g.removeVertex(n4);
        g.showGraph();
    }
}
