package Estructuras_;

import Mundo.Tile;

import java.util.HashMap;
import java.util.Map;

public class GraphSystem {
    /// Atributos
    protected final Map<GraphNode, Tile> system;
    protected GraphNode root;

    /// Constructor
    public GraphSystem() {
        this.system = new HashMap<>();
        this.root = null;
    }

    /// Getters
    public int getIntegrity() {
        int total = 0;
        for (GraphNode graphNode: system.keySet())
            total += graphNode.getIntegrity();
        return total/system.size();

    }

    public int size() {
        return system.
                size();
    }

    public String infrastructure() {
        return String.format("Tamaño: %d | Integridad: %d",
                system.size(),getIntegrity());
    }

    /// Funciones de añadido
    public void addCentralNode(GraphNode node, Tile tile) {
        addNode(node,tile);
        root = node;
    }

    public void addNode(GraphNode node, Tile tile) {
        system.put(node,tile);
        tile.setUnderground(node);
    }

    public void removeNode(GraphNode node) {
        if (!system.containsKey(node)) return;
        for (GraphNode n: system.keySet()) {
            if (n.contains(node)) n.removeEdge(node);
        } node.isolateSelf(); system.remove(node);
    }

    /// Funciones de conección
    public void linkNodes(GraphNode nodeA, GraphNode nodeB) {
        linkNodes(nodeA,nodeB,true);
    }

    public void linkNodes(GraphNode nodeA, GraphNode nodeB, boolean bidirectional) {
        if (!system.containsKey(nodeA)) return;
        nodeA.addEdge(nodeB);
        if (bidirectional) nodeB.addEdge(nodeA);
    }

    public void unlinkNodes(GraphNode nodeA, GraphNode nodeB) {
        unlinkNodes(nodeA,nodeB,true);
    }

    public void unlinkNodes(GraphNode nodeA, GraphNode nodeB, boolean bidirectional) {
        if (!system.containsKey(nodeA)) return;
        nodeA.removeEdge(nodeB);
        if (bidirectional) nodeB.removeEdge(nodeA);
    }
}
