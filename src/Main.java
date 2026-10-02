public class Main {
    private static int passed;

    public static void main(String[] args) {
        if (args.length != 1 || !"--demo".equals(args[0])) {
            System.out.println("Usage: java -cp out Main --demo");
            return;
        }

        passed = 0;
        check("T1", "Circle + VectorRenderer", () -> {
            Circle circle = new Circle("C1", 2, new VectorRenderer());
            return new CheckResult("VECTOR circle radius=2", circle.execute());
        });

        check("T2", "Circle + RasterRenderer", () -> {
            Circle circle = new Circle("C1", 2, new RasterRenderer());
            return new CheckResult("RASTER circle radius=2", circle.execute());
        });

        check("T3", "Square + VectorRenderer", () -> {
            Square square = new Square("S1", 3, new VectorRenderer());
            return new CheckResult("VECTOR square side=3", square.execute());
        });

        check("T4", "Square + RasterRenderer", () -> {
            Square square = new Square("S1", 3, new RasterRenderer());
            return new CheckResult("RASTER square side=3", square.execute());
        });

        check("T5", "same Circle object; VectorRenderer -> RasterRenderer", () -> {
            Circle circle = new Circle("C1", 2, new VectorRenderer());
            Shape originalReference = circle;
            String before = circle.execute();
            String idBefore = circle.getId();
            int radiusBefore = circle.getRadius();

            circle.setImplementation(new RasterRenderer());

            Shape afterReference = circle;
            String after = circle.execute();
            boolean sameObject = originalReference == afterReference;
            boolean stateUnchanged = idBefore.equals(circle.getId()) && radiusBefore == circle.getRadius();
            String expectedBefore = "VECTOR circle radius=2";
            String expectedAfter = "RASTER circle radius=2";
            boolean pass = sameObject && stateUnchanged
                    && expectedBefore.equals(before) && expectedAfter.equals(after);
            String actual = "sameObject=" + sameObject
                    + " | stateUnchanged=" + stateUnchanged
                    + " | before=" + before + " | after=" + after;
            return new CheckResult(pass, "sameObject=true | stateUnchanged=true | before="
                    + expectedBefore + " | after=" + expectedAfter, actual);
        });

        System.out.println("SUMMARY: " + passed + "/7 PASS");
    }
    private static void check(String id, String participants, CheckAction action) {
        CheckResult result;
        try {
            result = action.run();
        } catch (RuntimeException ex) {
            result = new CheckResult(false, "no exception", "exception=" + ex.getClass().getSimpleName()
                    + ": " + ex.getMessage());
        }

        if (result.pass()) {
            passed++;
            System.out.println(id + " PASS | " + participants + " | result=" + result.actual());
        } else {
            System.out.println(id + " FAIL | " + participants + " | actual=" + result.actual()
                    + " | expected=" + result.expected());
        }
    }

    @FunctionalInterface
    private interface CheckAction {
        CheckResult run();
    }

    private record CheckResult(boolean pass, String expected, String actual) {
        CheckResult(String expected, String actual) {
            this(expected.equals(actual), expected, actual);
        }
    }
}
