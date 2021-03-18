import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Catalog catalog = new Catalog("Random items", "saved-catalogues/", new ArrayList<>());
            catalog.add(new Image("Harta Romaniei", "images\\ro.png", 1024.0, 1024.0));
            catalog.add(new Movie("Cat dancing", "movies\\cat.mp4", "Unknown", LocalDate.now(), 69.0));
            catalog.add(new Book("I Like Java", "books\\likeJava.txt", "Me", 3));
            catalog.add(new Other("My script", "others\\myScript.bat"));

            CatalogUtils.save(catalog, true);

            Catalog catalog2 = CatalogUtils.load("saved-catalogues/Random items.myctg");
            catalog.list("\n", "\n");
            catalog2.list("\n", "\n");

            for (int i = 0; i < catalog2.getItems().size(); ++i) {
                System.out.print("Enter any character to play the next file\n");
                String k = (new Scanner(System.in)).nextLine();
                catalog2.play(i);
            }
        }
        catch (MyException e) {
            System.err.print(e.getMessage() + "\n");
        }
    }
}
