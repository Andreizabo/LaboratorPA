import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

public class CatalogUtils {
    public static void save(Catalog catalog, boolean overwrite) {
        try {
            String fileContent = packData(catalog);
            BufferedWriter writer;
            String fullPath = catalog.getPath() + (catalog.getPath().endsWith("/") ? "" : "/") + catalog.getName() + ".myctg";
            File checkFile = new File(fullPath);
            if(checkFile.exists()) {
                if(!overwrite) {
                    System.err.print("File already exists, use this method with overwrite enabled to overwrite it\n");
                    return;
                }
            }
            else if (checkFile.createNewFile()) {
                System.out.print("Created save file for current catalog\n");
            } else {
                System.err.print("Could not create save file for current catalog\n");
                return;
            }
            writer = Files.newBufferedWriter(Path.of(fullPath), Charset.defaultCharset());
            writer.write(fileContent);
            writer.close();
            System.out.print("Successfully saved current catalog\n");
        }
        catch (IOException e) {
            System.err.print("Catalog not saved, exception: ");
            System.err.print(e.getMessage() + "\n");
        }
    }

    public static Catalog load(String path) throws MyException {
        try {
            if (!path.endsWith(".myctg")) {
                path += ".myctg";
            }

            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            Catalog catalog = new Catalog(line, path, new ArrayList<>());

            while ((line = br.readLine()) != null) {
                line = line.substring(0, line.length() - 1);
                String[] splat = line.split("\\{");
                String[] attributes = splat[1].split(",");
                switch (splat[0]) {
                    case "Other":
                        catalog.add(new Other((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1])));
                        break;
                    case "Book":
                        catalog.add(new Book((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (Integer) parseAttribute(attributes[3])));
                        break;
                    case "Image":
                        catalog.add(new Image((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (Double) parseAttribute(attributes[2]), (Double) parseAttribute(attributes[3])));
                        break;
                    case "Movie":
                        catalog.add(new Movie((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (LocalDate) parseAttribute(attributes[3]), (Double) parseAttribute(attributes[4])));
                        break;
                    case "Song":
                        catalog.add(new Song((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (LocalDate) parseAttribute(attributes[3]), (Double) parseAttribute(attributes[4])));
                        break;
                }
            }
            br.close();

            return catalog;
        }
        catch (IOException | NullPointerException e) {
            throw new MyException("Catalog not loaded, exception:\n\t" + e.getMessage() + "\n");
        }
    }

    private static String packData(Catalog catalog) {
        StringBuilder sb = new StringBuilder(catalog.getName());
        catalog.getItems().forEach((item) -> sb.append("\n").append(item));
        return sb.toString();
    }

    private static Object parseAttribute(String string) {
        String attribute = string.split("=")[0].trim();
        String value = string.split("=")[1].replace("'", "").trim();

        switch (attribute) {
            case "name":
            case "path":
            case "author":
            case "director":
            case "singer":
                return value;
            case "salesNumber":
                return Integer.parseInt(value);
            case "widthPx":
            case "heightPx":
            case "runningTime":
            case "duration":
                return Double.parseDouble(value);
            case "releaseDate":
                return LocalDate.parse(value);
            default:
                return null;
        }
    }
}
