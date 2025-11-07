import java.util.*;

public class ClosestPair3D {

    public static class Point3D {
        String flightNo;
        double x, y, z;

        public Point3D(String flightNo, double x, double y, double z) {
            this.flightNo = flightNo;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static double dist3D(Point3D a, Point3D b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        double dz = a.z - b.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static double closestPair(List<Point3D> points) {
        points.sort(Comparator.comparingDouble(p -> p.x));
        return closestUtil(points);
    }

    private static double closestUtil(List<Point3D> X) {
        int n = X.size();
        if (n == 2) return dist3D(X.get(0), X.get(1));
        if (n == 3) {
            return Math.min(
                    dist3D(X.get(0), X.get(1)),
                    Math.min(dist3D(X.get(1), X.get(2)), dist3D(X.get(0), X.get(2)))
            );
        }

        int mid = n / 2;
        Point3D midPoint = X.get(mid);

        double dL = closestUtil(X.subList(0, mid));
        double dR = closestUtil(X.subList(mid, n));
        double d = Math.min(dL, dR);

        // Combine step
        List<Point3D> strip = new ArrayList<>();
        for (Point3D p : X) {
            if (Math.abs(p.x - midPoint.x) < d) {
                strip.add(p);
            }
        }

        strip.sort(Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < Math.min(strip.size(), i + 15); j++) {
                double dist = dist3D(strip.get(i), strip.get(j));
                if (dist < d) {
                    d = dist;
                }
            }
        }

        return d;
    }
}
