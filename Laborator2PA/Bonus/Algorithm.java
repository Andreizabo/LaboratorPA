/**
 * An abstract class which is used as a template by other implemented algorithms
 */
public abstract class Algorithm {
    protected Problem problem;

    /**
     * The only constructor, has one parameter
     * @param problem The problem that must be solved
     */
    public Algorithm(Problem problem) {
        this.problem = problem;
    }

    /**
     * Returns the current problem
     * @return The current problem
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Changes the current problem
     * @param problem The new problem
     */
    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * Abstract method which will be implemented by the algorithms which extend this class
     * @return The solution to the current problem
     */
    public abstract Solution solve();
}
