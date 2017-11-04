package pl.edu.agh.mownit.lab2;

import java.io.FileNotFoundException;

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
        equations.solve();
        final String graphString = new GraphSaver(graph, equations.getResult()).save();
        System.out.println(graphString);
    }
}
