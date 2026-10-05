package Estructuras_;

import java.util.HashSet;
import java.util.Set;

public class GraphNode {
    /// Atributos
    private final Set<GraphNode> connections;
    protected int integrity;

    /// Constructor
    public GraphNode(int integrity) {
        this.connections = new HashSet<>();
        this.integrity = integrity;
    }

    /// Basico
    public int getIntegrity() {
        return integrity;
    }

    public boolean contains(GraphNode node) {
        return connections.contains(node);
    }

    /// Funciones
    public void addEdge(GraphNode node) {
        connections.add(node);
    }

    public void removeEdge(GraphNode node) {
        connections.remove(node);
    }

    public void isolateSelf() {
        connections.clear();
    }
}
