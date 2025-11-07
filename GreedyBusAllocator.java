import java.util.*;

public class GreedyBusAllocator {

    public static class Booking {
        public String startStation;
        public String endStation;

        public Booking(String start, String end) {
            this.startStation = start;
            this.endStation = end;
        }
    }

    public static class Bus {
        public int freeSeats;

        public Bus(int capacity) {
            this.freeSeats = capacity;
        }
    }

    private int busCapacity;

    public GreedyBusAllocator(int capacity) {
        this.busCapacity = capacity;
    }

    public int allocateBuses(List<Booking> bookings) {
        // Sort stations
        Set<String> stationSet = new HashSet<>();
        for (Booking b : bookings) {
            stationSet.add(b.startStation);
            stationSet.add(b.endStation);
        }
        List<String> orderedStations = new ArrayList<>(stationSet);
        Collections.sort(orderedStations);

        // Map of bookings starting or ending at each station
        Map<String, List<Booking>> bookingFrom = new HashMap<>();
        Map<String, List<Booking>> bookingTo = new HashMap<>();
        for (String station : orderedStations) {
            bookingFrom.put(station, new ArrayList<>());
            bookingTo.put(station, new ArrayList<>());
        }
        for (Booking b : bookings) {
            bookingFrom.get(b.startStation).add(b);
            bookingTo.get(b.endStation).add(b);
        }

        List<Bus> buses = new ArrayList<>();
        Map<Booking, Bus> assignments = new HashMap<>();

        for (String station : orderedStations) {

            // Step 1: Free seats for passengers disembarking
            for (Booking b : bookingTo.get(station)) {
                Bus bus = assignments.get(b);
                if (bus != null) {
                    bus.freeSeats += 1;
                }
            }

            // Step 2: Assign new passengers boarding here
            for (Booking b : bookingFrom.get(station)) {
                boolean assigned = false;
                for (Bus bus : buses) {
                    if (bus.freeSeats >= 1) {
                        bus.freeSeats -= 1;
                        assignments.put(b, bus);
                        assigned = true;
                        break;
                    }
                }
                if (!assigned) {
                    Bus newBus = new Bus(busCapacity - 1);
                    buses.add(newBus);
                    assignments.put(b, newBus);
                }
            }
        }

        return buses.size();
    }
}
