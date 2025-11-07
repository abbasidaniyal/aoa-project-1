import java.io.*;
import java.util.*;

public class ClosestPairSimulator {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java ClosestPairSimulator <csv_file> <num_records>");
            return;
        }

        String csvFile = args[0];
        int numRecords = Integer.parseInt(args[1]);
        List<ClosestPair3D.Point3D> points = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // skip header
            int count = 0;
            while ((line = br.readLine()) != null && count < numRecords) {
                String[] tokens = line.split(",");
                if (tokens.length < 4) continue; // skip invalid rows
                String flightNo = tokens[0];
                double x = Double.parseDouble(tokens[1]);
                double y = Double.parseDouble(tokens[2]);
                double z = Double.parseDouble(tokens[3]);
                points.add(new ClosestPair3D.Point3D(flightNo, x, y, z));
                count++;
            }
        }

        if (points.size() < 2) {
            System.out.println("Not enough data points for simulation.");
            return;
        }

        long startTime = System.nanoTime();
        double minDist = ClosestPair3D.closestPair(points);
        long endTime = System.nanoTime();
        double runtimeMs = (endTime - startTime) / 1e6;

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long usedMemoryKB = (runtime.totalMemory() - runtime.freeMemory()) / 1024;

        System.out.println("----------------------------------------");
        System.out.println("Running simulation with " + points.size() + " planes...");
        System.out.println("Closest distance: " + minDist);
        System.out.println("Runtime: " + runtimeMs + " ms");
        System.out.println("Memory used: " + usedMemoryKB + " KB");
        System.out.println("----------------------------------------");
    }
}
