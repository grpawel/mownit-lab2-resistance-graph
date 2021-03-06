package pl.edu.agh.mownit.lab2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Paweł Grochola on 04.11.2017.
 */
public class GraphvizInputGenerator extends GraphFileGenerator {
    private final List<Edge> edges;
    private final double minValue;
    private final double valueSpan;
    private final double minLineWidth = 0.5;
    private final double maxLineWidth = 5.0;
    private final double lineWidthSpan = maxLineWidth - minLineWidth;

    public GraphvizInputGenerator(final Graph graph, final double[] edgeValues) {
        super(graph, edgeValues);
        this.edges = reverseReversedEdges();
        final double maxValue = Arrays.stream(edgeValues).map(Math::abs).max().orElseThrow(() -> new AssertionError("Result is empty"));
        this.minValue = Arrays.stream(edgeValues).map(Math::abs).min().orElseThrow(() -> new AssertionError("Result is empty"));
        this.valueSpan = maxValue - minValue;
    }

    @Override
    public String generate() {
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

        final String graphDefinition = "digraph{" +
                "  node [" +
                "    shape = circle," +
                "    size = 1];" +
                "  esep = 10;" +
                resultStream.collect(Collectors.joining(System.lineSeparator())) +
                "}";
        return graphDefinition;
    }

    private double getLineWidth(final int index) {
        final double value = Math.abs(edgeValues[index]);
        return (value - minValue) * lineWidthSpan / valueSpan + minLineWidth;
    }


}
