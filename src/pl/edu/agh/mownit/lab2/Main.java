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
        System.out.println(graph);
    }
}
