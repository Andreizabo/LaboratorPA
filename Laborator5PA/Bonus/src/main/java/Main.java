import java.io.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Catalog> activeCatalogues = new ArrayList<>();

    public static void main(String[] args) {
        CommandLogger.init();

        try {
            activeCatalogues.add(new Catalog("Random items", "saved-catalogues/", new ArrayList<>()));
            AddCommand add = new AddCommand(null);
            add.setEntry(new Image("Harta Romaniei", "images\\ro.png", 1024.0, 1024.0));
            add.run(activeCatalogues.get(0));
            add.setEntry(new Movie("Cat dancing", "movies\\cat.mp4", "Unknown", LocalDate.now(), 69.0));
            add.run(activeCatalogues.get(0));
            add.setEntry(new Book("I Like Java", "books\\likeJava.txt", "Me", 3));
            add.run(activeCatalogues.get(0));
            add.setEntry(new Other("My script", "others\\myScript.bat"));
            add.run(activeCatalogues.get(0));

            /*
            Scanner scanner = new Scanner(System.in);

            System.out.print("\nEnter a command:\n");
            String line = scanner.nextLine();

            while (!(line.trim()).equalsIgnoreCase("Exit")) {
                parseCommand(line);
                System.out.print("\nEnter a command:\n");
                line = scanner.nextLine();
            }
            */

            Catalog catalog2 = CatalogGenerator.generateCatalog(20, 10);
            Algorithm algorithm = new Algorithm();
            Solution solution = algorithm.solve(catalog2);
            solution.printSolution();

            //ListCommand listCommand = new ListCommand("\n", "\n");
            //listCommand.run(catalog2);
            //CommandLogger.printLog();
        }
        catch (MyException e) {
            System.err.print(e.getMessage() + "\n");
            CommandLogger.printLog();
        }
    }

    /**
     * Will parse a command read from the console
     * It must have the following format:
     * command "arg1" "arg2" "arg3" "argn"
     * Examples:
     * add "catalog3" "Image 'cat dancing' 'movies\cat.mp4' 'Unknown' '2021-03-24' '42.0'"
     * save "catalog3"
     * It is important to always put space between arguments, put arguments inside "" (or '' for new item arguments)
     * and never put space before a " if its not a new argument
     * The parser will not work otherwise
     * @param string The line read from the shell
     */
    private static void parseCommand(String string) throws MyException {
        Command command = null;
        String[] args = string.split(" \"");
        for(int i = 1; i < args.length; ++i) {
            args[i] = args[i].substring(0, args[i].length() - 1);
        }

        Catalog catalog = getCatalog(args[1]);

        switch (args[0]) {
            case "new" -> {
                activeCatalogues.add(new Catalog(args[1], args[2], new ArrayList<>()));
                return;
            }
            case "add" -> {
                String[] entryArgs = args[2].split(" '");
                for (int i = 1; i < entryArgs.length; ++i) {
                    entryArgs[i] = entryArgs[i].substring(0, entryArgs[i].length() - 1);
                }
                switch (entryArgs[0]) {
                    case "Other" -> command = new AddCommand(new Other(entryArgs[1], entryArgs[2]));
                    case "Book" -> command = new AddCommand(new Book(entryArgs[1], entryArgs[2], entryArgs[3], Integer.parseInt(entryArgs[4])));
                    case "Image" -> command = new AddCommand(new Image(entryArgs[1], entryArgs[2], Double.parseDouble(entryArgs[3]), Double.parseDouble(entryArgs[4])));
                    case "Movie" -> command = new AddCommand(new Movie(entryArgs[1], entryArgs[2], entryArgs[3], LocalDate.parse(entryArgs[4]), Double.parseDouble(entryArgs[5])));
                    case "Song" -> command = new AddCommand(new Song(entryArgs[1], entryArgs[2], entryArgs[3], LocalDate.parse(entryArgs[4]), Double.parseDouble(entryArgs[5])));
                }
            }
            case "list" -> command = new ListCommand("\n", "\n");
            case "load" -> command = new LoadCommand(args[2]);
            case "play" -> command = new PlayCommand(Integer.parseInt(args[2]));
            case "save" -> command = new SaveCommand(Boolean.parseBoolean(args[2]));
            case "report" -> command = new ReportCommand();
            case "info" -> command = new InfoCommand(Integer.parseInt(args[2]));
            default -> throw new MyException("\nCommand unrecognized. Available commands:\n\t new, add, list, load, play, save\n");
        }
        try {
            command.run(catalog);
        }
        catch (NullPointerException e) {
            if(catalog != null) {
                throw new MyException("Unrecognised command");
            }
            else {
                throw new MyException("Unknown catalog");
            }
        }
    }

    private static Catalog getCatalog(String name) {
        for(Catalog catalog : activeCatalogues) {
            if(catalog.getName().equals(name)) {
                return catalog;
            }
        }
        return null;
    }
}
