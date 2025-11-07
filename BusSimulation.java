import java.io.*;
import java.util.*;

public class BusSimulation {


    public static List<GreedyBusAllocator.Booking> readBookingsFromCSV(String filename, int numBookings) {
        List<GreedyBusAllocator.Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            int count = 0;
            while ((line = br.readLine()) != null && count < numBookings) {
                String[] parts = line.split(",");
                String start = parts[1].trim();  // Boarding Station
                String end = parts[2].trim();    // Drop Station
                bookings.add(new GreedyBusAllocator.Booking(start, end));
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java BusSimulation <csv-file> <no-bookings-to-use>");
            return;
        }

        String csvFile = args[0];
        int numBookingToRun = Integer.parseInt(args[1]);
        int busCapacity = 60;

        List<GreedyBusAllocator.Booking> bookings = readBookingsFromCSV(csvFile, numBookingToRun);

        GreedyBusAllocator allocator = new GreedyBusAllocator(busCapacity);

        // Measure start time
        long startTime = System.currentTimeMillis();

        // Run allocation
        int busesRequired = allocator.allocateBuses(bookings);

        // Measure end time
        long endTime = System.currentTimeMillis();
        long runtimeMs = endTime - startTime;

        // Measure memory usage
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Request garbage collection
        long usedMemoryKB = (runtime.totalMemory() - runtime.freeMemory()) / (1024);

        System.out.println("Total bookings considered: " + bookings.size());
        System.out.println("Bus capacity: " + busCapacity);
        System.out.println("Minimum number of buses required: " + busesRequired);
        System.out.println("Runtime: " + runtimeMs + " ms");
        System.out.println("Memory used: " + usedMemoryKB + " KB");
    }

}
