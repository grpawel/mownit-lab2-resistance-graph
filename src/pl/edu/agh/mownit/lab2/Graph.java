package pl.edu.agh.mownit.lab2;

import org.jgrapht.alg.cycle.PatonCycleBase;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */
public class Graph extends SimpleWeightedGraph<String, Edge> {
    public Graph() {
        super(Edge.class);
    }

    private List<List<String>> findVertexCycles() {
        final PatonCycleBase<String, Edge> patonCycleBase = new PatonCycleBase<>();
        patonCycleBase.setGraph(this);
        return patonCycleBase.findCycleBase();
    }

    public List<List<Edge>> findEdgeCycles() {
        final Set<Edge> edges = edgeSet();
        final List<List<String>> vertexCycles = findVertexCycles();
        final List<List<Edge>> edgeCycles = new ArrayList<>();
        for (final List<String> vertexCycle : vertexCycles) {
            final List<Edge> edgeCycle = new ArrayList<>();
            for (int i = 0; i < vertexCycle.size(); i++) {
                final String vertex1 = vertexCycle.get(i);
                final String vertex2 = vertexCycle.get((i + 1) % vertexCycle.size());
                Edge edge = edges.stream()
                        .filter(e -> e.containsVertices(vertex1, vertex2))
                        .findFirst()
                        .orElseThrow(() -> new AssertionError("Cycle has edge that does not exist"));
                if(edge.getFirstVertex().equals(vertex2) && edge.getSecondVertex().equals(vertex1)) {
                    edge = edge.reversedOrder();
                }
                edgeCycle.add(edge);
            }
            edgeCycles.add(edgeCycle);
        }
        return edgeCycles;
    }
}
