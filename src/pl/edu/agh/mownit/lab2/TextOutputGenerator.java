package pl.edu.agh.mownit.lab2;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paweł Grochola on 06.11.2017.
 */
public class TextOutputGenerator extends GraphFileGenerator {

    public TextOutputGenerator(final Graph graph, final double[] edgeValues) {
        super(graph, edgeValues);
    }

    @Override
    public String generate() {
        final List<Edge> edges = reverseReversedEdges();
        return edges.stream()
                .map(edge -> edge.getFirstVertex() + " " + edge.getSecondVertex() + " " + edgeValues[edge.getIndex()])
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
