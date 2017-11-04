package pl.edu.agh.mownit.lab2;

import javafx.util.Pair;
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

    public enum EdgeDirection {
        NORMAL,
        REVERSED
    }
    public List<List<Pair<Edge, EdgeDirection>>> findEdgeCycles() {
        final Set<Edge> edges = edgeSet();
        final List<List<String>> vertexCycles = findVertexCycles();
        final List<List<Pair<Edge, EdgeDirection>>> edgeCycles = new ArrayList<>();
        for (final List<String> vertexCycle : vertexCycles) {
            final List<Pair<Edge, EdgeDirection>> edgeCycle = new ArrayList<>();
            for (int i = 0; i < vertexCycle.size(); i++) {
                final String vertex1 = vertexCycle.get(i);
                final String vertex2 = vertexCycle.get((i + 1) % vertexCycle.size());
                final Edge edge = edges.stream()
                        .filter(e -> e.containsVertices(vertex1, vertex2))
                        .findFirst()
                        .orElseThrow(() -> new AssertionError("Cycle has edge that does not exist"));
                final EdgeDirection direction = edge.getSmallerVertex().equals(vertex1) ? EdgeDirection.NORMAL : EdgeDirection.REVERSED;
                edgeCycle.add(new Pair<>(edge, direction));
            }
            edgeCycles.add(edgeCycle);
        }
        return edgeCycles;
    }
}
