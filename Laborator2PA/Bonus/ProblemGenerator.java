import java.util.Random;

/**
 * Class that will generate problems to be solved
 */
public class ProblemGenerator {
    private int minSupply, minDemand, maxSupply, maxDemand;
    private int minSources, minDestinations, maxSources, maxDestinations;
    private int totalUnits;

    private Source[] sources;
    private Destination[] destinations;
    private int[][] costs;

    /**
     * Creates a problem generator with default parameters
     */
    public ProblemGenerator() {
        Random random = new Random(System.nanoTime());
        minSupply = random.nextInt(20);
        minDemand = random.nextInt(20);
        maxSupply = random.nextInt(100) + minSupply;
        maxDemand = random.nextInt(100) + minDemand;
        minSources = random.nextInt(3);
        minDestinations = random.nextInt(3);
        maxSources = random.nextInt(50) + minSources;
        maxDestinations = random.nextInt(50) + minDestinations;
    }

    /**
     * Creates a problem generator with custom parameters
     * @param minSupply Minimum supply in a source
     * @param minDemand Minimum demand in a destination
     * @param maxSupply Maximum supply in a source, may be overruled if needed
     * @param maxDemand Maximum demand in a  destination, may be overruled if needed
     * @param minSources Minimum number of sources
     * @param minDestinations Minimum number of destinations
     * @param maxSources Maximum number of sources
     * @param maxDestinations Maximum number of destinations
     */
    public ProblemGenerator(int minSupply, int minDemand, int maxSupply, int maxDemand, int minSources, int minDestinations, int maxSources, int maxDestinations) {
        this.minSupply = minSupply;
        this.minDemand = minDemand;
        this.maxSupply = maxSupply;
        this.maxDemand = maxDemand;
        this.minSources = minSources;
        this.minDestinations = minDestinations;
        this.maxSources = maxSources;
        this.maxDestinations = maxDestinations;
    }

    /**
     * Generates a new problem with the current parameters
     * @return The generated problem
     */
    public Problem generate() {
        generateSources();
        generateDestinations();
        generateCosts();
        return new Problem(sources, destinations, costs);
    }

    private void generateSources() {
        Random random = new Random(System.nanoTime());

        totalUnits = 0;
        int length = minSources + random.nextInt(maxSources + minSources);
        sources = new Source[length];

        for(int i = 0; i < length; ++i) {
            int units = minSupply + random.nextInt(maxSupply + minSupply);
            totalUnits += units;
            if(random.nextInt(100) < 50) {
                sources[i] = new Warehouse("S" + i, units);
            }
            else {
                sources[i] = new Factory("S" + i, units);
            }
        }
    }

    private void generateDestinations() {
        Random random = new Random(System.nanoTime());

        int demandUnits = 0;
        int length = minDestinations + random.nextInt(maxDestinations + minDestinations);
        destinations = new Destination[length];

        for(int i = 0; i < length; ++i) {
            int units = minDemand + random.nextInt(maxDemand + minDemand);
            demandUnits += units;
            destinations[i] = new Destination("D" + i, units);
        }
        if(demandUnits < totalUnits) {
            demandUnits = totalUnits - demandUnits;
            int all = demandUnits / length;
            int some = demandUnits % length;
            for(int i = 0; i < length; ++i) {
                destinations[i].setDemand(destinations[i].getDemand() + all);
            }
            int modifyDestinations = cmmdc(some, length);
            some /= modifyDestinations;
            for(int i = 0; i < modifyDestinations; ++i) {
                int rand = random.nextInt(length);
                destinations[rand].setDemand(destinations[rand].getDemand() + some);
            }
        }
        else if (demandUnits > totalUnits) {
            demandUnits = demandUnits - totalUnits;
            int all = demandUnits / sources.length;
            int some = demandUnits % sources.length;
            for(int i = 0; i < sources.length; ++i) {
                sources[i].setSupply(sources[i].getSupply() + all);
            }
            int modifySources = cmmdc(some, sources.length);
            some /= modifySources;
            for(int i = 0; i < modifySources; ++i) {
                int rand = random.nextInt(sources.length);
                sources[rand].setSupply(sources[rand].getSupply() + some);
            }
        }

    }

    private void generateCosts() {
        Random random = new Random(System.nanoTime());

        costs = new int[sources.length][destinations.length];
        for(int i = 0; i < sources.length; ++i) {
            for(int j = 0; j < destinations.length; ++j) {
                costs[i][j] = random.nextInt(100);
            }
        }
    }

    private int cmmdc(int a, int b) {
        if(a < 1) {
            return b;
        }
        if(b < 1) {
            return a;
        }
        while (a != b) {
            if(a > b) {
                a -= b;
            }
            else {
                b -= a;
            }
        }
        return b;
    }
}
