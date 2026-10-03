import java.util.ArrayList;
import java.util.List;

public class Main {

    private static int passed = 0;
    private static int total = 0;

    @FunctionalInterface
    private interface CheckAction {
        CheckResult run();
    }

    private record CheckResult(String actual, String expected) {
    }

    public static void main(String[] args) {
        if (args.length != 1 || !"--demo".equals(args[0])) {
            System.out.println("Usage: java Main --demo");
            return;
        }

        runDemo();
        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
    }

    private static void runDemo() {

        // T1
        check("T1", "Circle + VectorRenderer", () -> {
            Circle circle = new Circle("C1", 2, new VectorRenderer());

            String actual = circle.execute();
            String expected = "VECTOR circle radius=2";

            return new CheckResult(actual, expected);
        });

        // T2
        check("T2", "Circle + RasterRenderer", () -> {
            Circle circle = new Circle("C1", 2, new RasterRenderer());

            String actual = circle.execute();
            String expected = "RASTER circle radius=2";

            return new CheckResult(actual, expected);
        });

        // T3
        check("T3", "Square + VectorRenderer", () -> {
            Square square = new Square("S1", 3, new VectorRenderer());

            String actual = square.execute();
            String expected = "VECTOR square side=3";

            return new CheckResult(actual, expected);
        });

        // T4
        check("T4", "Square + RasterRenderer", () -> {
            Square square = new Square("S1", 3, new RasterRenderer());

            String actual = square.execute();
            String expected = "RASTER square side=3";

            return new CheckResult(actual, expected);
        });

        // T5
        Circle circle = new Circle("C1", 2, new VectorRenderer());

        List<Shape> shapes = new ArrayList<>();
        shapes.add(circle);

        Shape originalReference = shapes.get(0);

        String idBefore = originalReference.getId();
        int radiusBefore = circle.getRadius();
        String before = originalReference.execute();

        originalReference.setImplementation(new RasterRenderer());

        Shape afterReference = shapes.get(0);

        String idAfter = afterReference.getId();
        int radiusAfter = circle.getRadius();
        String after = afterReference.execute();

        boolean sameObject = circle == afterReference;
        boolean stateUnchanged =
                idBefore.equals(idAfter) &&
                        radiusBefore == radiusAfter;

        check("T5", "same Circle object; VectorRenderer -> RasterRenderer", () -> {
            String actual =
                    "sameObject=" + sameObject +
                            " | stateUnchanged=" + stateUnchanged +
                            " | before=" + before +
                            " | after=" + after;

            String expected =
                    "sameObject=true" +
                            " | stateUnchanged=true" +
                            " | before=VECTOR circle radius=2" +
                            " | after=RASTER circle radius=2";

            return new CheckResult(actual, expected);
        });


        // T6
        check("T6", "Circle + AsciiRenderer", () -> {
            Circle asciiCircle = new Circle("C1", 2, new AsciiRenderer());

            String actual = asciiCircle.execute();
            String expected = "ASCII circle radius=2";

            return new CheckResult(actual, expected);
        });

        // T7
        check("T7", "Square + AsciiRenderer", () -> {
            Square square = new Square("S1", 3, new AsciiRenderer());

            String actual = square.execute();
            String expected = "ASCII square side=3";

            return new CheckResult(actual, expected);
        });
    }

    private static void check(
            String testId,
            String description,
            CheckAction action
    ) {
        total++;

        try {
            CheckResult result = action.run();

            boolean success = result.expected().equals(result.actual());

            if (success) {
                passed++;
                System.out.println(
                        testId
                                + " PASS | "
                                + description
                                + " | result="
                                + result.actual()
                );
            } else {
                System.out.println(
                        testId
                                + " FAIL | "
                                + description
                                + " | actual="
                                + result.actual()
                                + " | expected="
                                + result.expected()
                );
            }

        } catch (Exception e) {
            System.out.println(
                    testId
                            + " FAIL | "
                            + description
                            + " | exception="
                            + e.getMessage()
            );
        }
    }
}