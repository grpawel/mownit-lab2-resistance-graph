package pl.edu.agh.mownit.lab2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */
public class Main {
    public static void main(final String[] args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.out.println("Please specify file with graph in first argument");
            return;
        }
        final String fileName = args[0];
        final Graph graph = new GraphReader(fileName).readFromFile();
        final LinearEquationSystem equations = new LinearEquationSystem(graph.edgeSet().size());
        new GraphSolver(equations, graph).applyKirchoffLaws();
        equations.solve();
        generateGraphvizOutput(graph, equations.getResult(), args[0]);
        generateTextOutput(graph, equations.getResult(), args[0]);
    }

    private static void generateGraphvizOutput(final Graph graph, final double[] edgeValues, final String fileName) throws IOException, InterruptedException {
        final String graphString = new GraphvizInputGenerator(graph, edgeValues).generate();
        final File tempFile = File.createTempFile(java.util.UUID.randomUUID().toString(), ".dot");
        Files.write(Paths.get(tempFile.getAbsolutePath()), graphString.getBytes());
        final String fileNameWithoutExtension = new File(fileName).getAbsolutePath().replaceFirst("[.][^.]+$", "");
        final Process process = new ProcessBuilder(
                "C:\\Program Files (x86)\\Graphviz2.38\\bin\\neato.exe",
                tempFile.getAbsolutePath(),
                "-Tpng",
                "-o" + fileNameWithoutExtension + ".png").start();

        final InputStream is = process.getErrorStream();
        final InputStreamReader isr = new InputStreamReader(is);
        final BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static void generateTextOutput(final Graph graph, final double[] edgeValues, final String fileName) throws IOException {
        final String fileNameWithoutExtension = new File(fileName).getAbsolutePath().replaceFirst("[.][^.]+$", "");
        final String graphString = new TextOutputGenerator(graph, edgeValues).generate();
        Files.write(Paths.get(fileNameWithoutExtension + ".graph"), graphString.getBytes());
    }
}
