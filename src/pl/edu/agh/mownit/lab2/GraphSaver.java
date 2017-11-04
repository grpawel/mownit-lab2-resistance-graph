package pl.edu.agh.mownit.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Paweł Grochola on 04.11.2017.
 */
public class GraphSaver {
    private final Graph graph;
    private final double[] edgeValues;
    private final List<Edge> edges;
    private final double maxValue;
    private final double minValue;
    private final double valueSpan;
    final double minLineWidth = 0.5;
    final double maxLineWidth = 5.0;
    final double lineWidthSpan = maxLineWidth - minLineWidth;

    public GraphSaver(final Graph graph, final double[] edgeValues) {
        this.graph = graph;
        this.edgeValues = edgeValues;
        this.edges = reverseReversedEdges();
        this.maxValue = Arrays.stream(edgeValues).map(Math::abs).max().orElseThrow(() -> new AssertionError("Result is empty"));
        this.minValue = Arrays.stream(edgeValues).map(Math::abs).min().orElseThrow(() -> new AssertionError("Result is empty"));
        this.valueSpan = maxValue - minValue;
    }

    public String save() {
        final Stream<String> verticesStream = edges.stream()
                .map(edge -> edge.getFirstVertex() + " -> " + edge.getSecondVertex());
        final Stream<String> lineWidthStream = edges.stream()
                .map(edge -> getLineWidth(edge.getIndex()))
                .map(lineWidth -> "penwidth=" + lineWidth);
        final Stream<String> labelsStream = edges.stream()
                .map(edge -> edgeValues[edge.getIndex()])
                .map(edgeValue -> "label=\"" + String.format("%.3f", edgeValue) + "\"");
        final Stream<String> emfStream = edges.stream()
                .map(edge -> edge.getType() == Edge.Type.EMF)
                .map(isEmf -> isEmf ? "color=red, fontcolor=red" : "color=black");
        Stream<String> annotationsStream = Utils.zip(lineWidthStream, labelsStream, (s, s2) -> "[ " + s + "," + s2);
        annotationsStream = Utils.zip(annotationsStream, emfStream, (s, s2) -> s + "," + s2 + "]");

        final Stream<String> resultStream = Utils.zip(verticesStream, annotationsStream, (s, s2) -> s + "\t" + s2);

        return resultStream.collect(Collectors.joining(System.lineSeparator()));
    }

    private double getLineWidth(final int index) {
        final double value = Math.abs(edgeValues[index]);
        return (value - minValue) * lineWidthSpan / valueSpan + minLineWidth;
    }

    private List<Edge> reverseReversedEdges() {
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
