package pl.edu.agh.mownit.lab2;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */
public class Main {
    public static void main(final String[] args) throws FileNotFoundException {
        if(args.length != 1) {
            System.out.println("Please specify file with graph in first argument");
            return;
        }
        final String fileName = args[0];
        final Graph graph = new GraphReader(fileName).readFromFile();
        final LinearEquationSystem equations = new LinearEquationSystem(graph.edgeSet().size());
        new GraphSolver(equations, graph).applyKirchoffLaws();
        System.out.println(Arrays.deepToString(equations.getCoefficients()));
        System.out.println(Arrays.toString(equations.getFreeTerms()));
        equations.solve();
        System.out.println(Arrays.toString(equations.getResult()));
    }
}
