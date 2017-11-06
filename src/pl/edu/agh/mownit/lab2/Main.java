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
        final String graphString = new GraphSaver(graph, equations.getResult()).save();
        generateGraphvizOutput(graphString, args[0]);
    }

    private static void generateGraphvizOutput(final String graphString, final String fileName) throws IOException, InterruptedException {
        final File tempFile = File.createTempFile(java.util.UUID.randomUUID().toString(), ".dot");
        Files.write(Paths.get(tempFile.getAbsolutePath()), graphString.getBytes());
        final String fileNameWithoutExtension = new File(fileName).getAbsolutePath().replaceFirst("[.][^.]+$", "");
        final Process process = new ProcessBuilder(
                "neato",
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
}
