package pl.edu.agh.mownit.lab2;

import java.util.stream.Collectors;

/**
 * Created by Paweł Grochola on 06.11.2017.
 */
public class TextOutputGenerator {
    private final Graph graph;
    private final double[] edgeValues;

    public TextOutputGenerator(final Graph graph, final double[] edgeValues) {
        this.graph = graph;
        this.edgeValues = edgeValues;
    }

    public String generate() {
        return graph.edgeSet().stream()
                .map(edge -> edge.getFirstVertex() + " " + edge.getSecondVertex() + edgeValues[edge.getIndex()])
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
