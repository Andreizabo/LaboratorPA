import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class CatalogGenerator {
    /**
     * Will generate a catalog with movies and songs (they have almost identical attributes
     * @param maxItems Maximum number of items
     * @param maxDifferences Maximum number of different artists etc.
     * @return A catalog
     */
    public static Catalog generateCatalog(int maxItems, int maxDifferences) {
        Faker faker = new Faker();
        Random random = new Random(System.nanoTime());

        LocalDate[] dates = new LocalDate[maxDifferences];
        String[] names = new String[maxDifferences];
        Double[] durations = new Double[maxDifferences];

        IntStream.range(0, maxDifferences).forEach((i) -> {
            dates[i] = LocalDate.ofEpochDay(LocalDate.of(1970, 1, 1).toEpochDay() + (random.nextLong() % 7000)); //From 1970 to 1990
            names[i] = faker.artist().name();
            durations[i] = random.nextDouble() * 240;
        });

        AddCommand addCommand = new AddCommand(null);

        Catalog catalog = new Catalog(faker.animal().name(), "saved-catalogues/", new ArrayList<>());

        IntStream.range(0, maxItems).forEach((i) -> {
            if(i % 2 == 0) {
                addCommand.setEntry(new Movie("M" + (i / 2), "movies\\cat.mp4", names[random.nextInt(maxDifferences)], dates[random.nextInt(maxDifferences)], durations[random.nextInt(maxDifferences)]));
                addCommand.run(catalog);
            }
            else {
                addCommand.setEntry(new Song("S" + (i / 2), "movies\\cat.mp4", names[random.nextInt(maxDifferences)], dates[random.nextInt(maxDifferences)], durations[random.nextInt(maxDifferences)]));
                addCommand.run(catalog);
            }
        });

        return catalog;
    }
}
