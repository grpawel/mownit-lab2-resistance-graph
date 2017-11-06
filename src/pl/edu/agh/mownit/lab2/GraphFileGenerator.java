package pl.edu.agh.mownit.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Paweł Grochola on 06.11.2017.
 */
public abstract class GraphFileGenerator {
    protected final Graph graph;
    protected final double[] edgeValues;

    public GraphFileGenerator(final Graph graph, final double[] edgeValues) {
        this.graph = graph;
        this.edgeValues = Arrays.copyOf(edgeValues, edgeValues.length);
    }

    public abstract String generate();

    protected List<Edge> reverseReversedEdges() {
        final List<Edge> edgesList = new ArrayList<>(graph.edgeSet());
        edgesList.sort(Comparator.comparingInt(Edge::getIndex));
        for (int i = 0; i < edgesList.size(); i++) {
            if(edgeValues[i] < 0) {
                edgeValues[i] *= -1;
                final Edge oldEdge = edgesList.get(i);
                final Edge newEdge = new Edge(oldEdge.getSecondVertex(), oldEdge.getFirstVertex(), oldEdge.getType(), oldEdge.getValue(), oldEdge.getIndex());
                edgesList.set(i, newEdge);
            }
        }
        return edgesList;
    }
}
