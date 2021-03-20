public class Main {
    public static void main(String[] args) {
        CustomProblem customProblem = ProblemGenerator.generateCustomProblem(10, 3);
        //customProblem.printProblem("\n", "\n");

        Algorithm galeShapley = new GaleShapley();
        Solution solution = galeShapley.solve(customProblem);
        solution.printSolution("\n", "\n");
    }
}
