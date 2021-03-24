import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class LoadCommand implements Command {
    private String path;

    public LoadCommand(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void run(Catalog catalog) {
        int logIndex = CommandLogger.log("Command: Load catalog from path \"" + path + "\"");
        try {
            if (!path.endsWith(".myctg")) {
                path += ".myctg";
            }

            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            catalog.setName(line);
            catalog.setPath(path);
            catalog.setItems(new ArrayList<>());

            AddCommand add = new AddCommand(null);

            while ((line = br.readLine()) != null) {
                line = line.substring(0, line.length() - 1);
                String[] splat = line.split("\\{");
                String[] attributes = splat[1].split(",");
                switch (splat[0]) {
                    case "Other":
                        add.setEntry(new Other((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1])));
                        add.run(catalog);
                        break;
                    case "Book":
                        add.setEntry(new Book((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (Integer) parseAttribute(attributes[3])));
                        add.run(catalog);
                        break;
                    case "Image":
                        add.setEntry(new Image((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (Double) parseAttribute(attributes[2]), (Double) parseAttribute(attributes[3])));
                        add.run(catalog);
                        break;
                    case "Movie":
                        add.setEntry(new Movie((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (LocalDate) parseAttribute(attributes[3]), (Double) parseAttribute(attributes[4])));
                        add.run(catalog);
                        break;
                    case "Song":
                        add.setEntry(new Song((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (LocalDate) parseAttribute(attributes[3]), (Double) parseAttribute(attributes[4])));
                        add.run(catalog);
                        break;
                }
            }
            br.close();
        }
        catch (NullPointerException e) {
            CommandLogger.logResult(logIndex, "Null Pointer Exception", false);
            return;
        }
        catch (IOException e) {
            CommandLogger.logResult(logIndex, "IO Exception", false);
            return;
        }

        CommandLogger.logResult(logIndex, "Success", true);
    }

    @Override
    public void runWithoutLogger(Catalog catalog) {
        try {
            if (!path.endsWith(".myctg")) {
                path += ".myctg";
            }

            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            catalog.setName(line);
            catalog.setPath(path);
            catalog.setItems(new ArrayList<>());

            AddCommand add = new AddCommand(null);

            while ((line = br.readLine()) != null) {
                line = line.substring(0, line.length() - 1);
                String[] splat = line.split("\\{");
                String[] attributes = splat[1].split(",");
                switch (splat[0]) {
                    case "Other" -> {
                        add.setEntry(new Other((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1])));
                        add.run(catalog);
                    }
                    case "Book" -> {
                        add.setEntry(new Book((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (Integer) parseAttribute(attributes[3])));
                        add.run(catalog);
                    }
                    case "Image" -> {
                        add.setEntry(new Image((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (Double) parseAttribute(attributes[2]), (Double) parseAttribute(attributes[3])));
                        add.run(catalog);
                    }
                    case "Movie" -> {
                        add.setEntry(new Movie((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (LocalDate) parseAttribute(attributes[3]), (Double) parseAttribute(attributes[4])));
                        add.run(catalog);
                    }
                    case "Song" -> {
                        add.setEntry(new Song((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (LocalDate) parseAttribute(attributes[3]), (Double) parseAttribute(attributes[4])));
                        add.run(catalog);
                    }
                }
            }
            br.close();
        }
        catch (IOException | NullPointerException e) {
            throw new MyException("Catalog not loaded, exception:\n\t" + e.getMessage() + "\n");
        }
    }

    public static Object parseAttribute(String string) {
        String attribute = string.split("=")[0].trim();
        String value = string.split("=")[1].replace("'", "").trim();

        return switch (attribute) {
            case "name", "path", "author", "director", "singer" -> value;
            case "salesNumber" -> Integer.parseInt(value);
            case "widthPx", "heightPx", "runningTime", "duration" -> Double.parseDouble(value);
            case "releaseDate" -> LocalDate.parse(value);
            default -> null;
        };
    }
}
