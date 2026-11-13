package Estructuras.Generales;

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

    /// Getters
    public int getIntegrity() {
        return integrity;
    }

    public Set<GraphNode> getConnections() {
        return connections;
    }

    /// Métodos
    public void addEdge(GraphNode node) {
        connections.add(node);
    }

    public void removeEdge(GraphNode node) {
        connections.remove(node);
    }

    /// Otros
    public boolean contains(GraphNode node) {
        return connections.contains(node);
    }
}
