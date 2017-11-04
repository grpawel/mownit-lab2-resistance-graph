package pl.edu.agh.mownit.lab2;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */
public class GraphSolver {
    private final LinearEquationSystem equations;
    private final Graph graph;
    private int currentEquation = 0;

    public GraphSolver(final LinearEquationSystem equations, final Graph graph) {
        this.equations = equations;
        this.graph = graph;
    }

    public void applyKirchoffLaws() {
        applyFirstKirchoffsLaw();
        applySecondKirchoffsLaw();

    }

    private void applyFirstKirchoffsLaw() {
        final Set<String> vertices = graph.vertexSet();
        final double[][] coefficients = equations.getCoefficients();
        for (final String vertex : vertices) {
            final Set<Edge> adjacentEdges = graph.edgesOf(vertex);
            for (final Edge edge : adjacentEdges) {
                if (edge.getSmallerVertex().equals(vertex)) {
                    coefficients[currentEquation][edge.getIndex()] = 1d;
                } else {
                    coefficients[currentEquation][edge.getIndex()] = -1d;
                }
            }
            currentEquation++;
        }
        currentEquation--;
        for (int i = 0; i < equations.getSize(); i++) {
            coefficients[currentEquation][i] = 0;
        }
    }

    private void applySecondKirchoffsLaw() {
        final List<List<Edge>> edgeCycles = graph.findEdgeCycles();
        final Set<Integer> unusedEdges = IntStream.range(0, graph.edgeSet().size())
                .boxed()
                .collect(Collectors.toSet());
        final Set<Integer> unusedCycles = IntStream.range(0, edgeCycles.size()).boxed().collect(Collectors.toSet());
        while (!unusedEdges.isEmpty() || currentEquation < equations.getSize()) {
            final List<Long> cycleScores = scoreCycles(edgeCycles, unusedEdges);
            final Integer indexOfCurrentCycle = findUnusedCycleWithMaximumScore(unusedCycles, cycleScores);
            unusedCycles.remove(indexOfCurrentCycle);
            final List<Edge> currentCycle = edgeCycles.get(indexOfCurrentCycle);
            final double[][] coefficients = equations.getCoefficients();
            final double[] freeTerms = equations.getFreeTerms();
            for (final Edge edge : currentCycle) {
                unusedEdges.remove(edge.getIndex());
                switch (edge.getType()) {
                    case RESISTANCE:
                        coefficients[currentEquation][edge.getIndex()] = edge.getValue();
                        break;
                    case EMF:
                        double emf = edge.getValue();
                        freeTerms[currentEquation] += emf;
                        break;
                }
            }
            currentEquation++;
        }
    }

    private List<Long> scoreCycles(final List<List<Edge>> cycles, final Set<Integer> unusedEdges) {
        return cycles.stream()
                .map(cycle -> cycle.stream()
                        .filter(edgePair -> unusedEdges.contains(edgePair.getIndex()))
                        .count())
                .collect(Collectors.toList());
    }

    private Integer findUnusedCycleWithMaximumScore(final Set<Integer> unusedCycles,
                                                    final List<Long> cycleScores) {
        return IntStream.range(0, cycleScores.size())
                .filter(unusedCycles::contains)
                .boxed()
                .max(Comparator.comparing(cycleScores::get))
                .orElseThrow(() -> new AssertionError("All cycles are used and there are edges left"));

    }
}
