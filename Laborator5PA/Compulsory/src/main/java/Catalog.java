import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Catalog {
    private List<CatalogItem> items;

    public Catalog(List<CatalogItem> items) {
        this.items = items;
    }

    public void setCatalogItem(int index, CatalogItem catalogItem) {
        try {
            items.set(index, catalogItem);
        }
        catch (IndexOutOfBoundsException e) {
            System.err.print(e.getMessage() + "\n");
        }
    }

    public CatalogItem getCatalogItem(int index) {
        try {
            return items.get(index);
        }
        catch (IndexOutOfBoundsException e) {
            System.err.print(e.getMessage() + "\n");
            return null;
        }
    }

    public List<CatalogItem> getItems() {
        return items;
    }

    public void add(CatalogItem catalogItem) throws MyException {
        try {
            AtomicBoolean exists = new AtomicBoolean(false);
            items.forEach((item) -> exists.set(exists.get() || item == catalogItem));
            if (exists.get()) {
                throw new MyException("The item already exists in this catalog");
            } else {
                items.add(catalogItem);
            }
        }
        catch (NullPointerException e) {
            System.err.print(e.getMessage() + "\n");
        }
    }

    public void list(String prefix, String suffix) {
        try {
            final StringBuilder sb = new StringBuilder((prefix));
            sb.append("Catalog = {");
            items.forEach((item) -> sb.append("\n\t").append(item));
            sb.append("\n}");
            sb.append(suffix);
            System.out.print(sb.toString());
        }
        catch (NullPointerException e) {
            System.err.print(e.getMessage() + "\n");
        }
    }

    public void play(int index) {
        try {
            Desktop.getDesktop().open(new File(items.get(index).getPath()));
        }
        catch (IOException | NullPointerException e) {
            System.err.print(e.getMessage() + "\n");
        }
    }

    public void save() {
        try {
            String fileContent = packData();
            BufferedWriter writer;
            int index = 0;
            File checkFile = new File("saved-catalogues\\catalog" + index + ".myctg");
            while (checkFile.exists() || checkFile.isDirectory()) {
                ++index;
                checkFile = new File("saved-catalogues\\catalog" + index + ".myctg");
            }
            if (checkFile.createNewFile()) {
                System.out.print("Created save file for current catalog\n");
            } else {
                System.out.print("Could not create save file for current catalog\n");
                return;
            }
            Path test = Path.of("saved-catalogues", "catalog" + index + ".myctg");
            writer = Files.newBufferedWriter(test, Charset.defaultCharset());
            writer.write(fileContent);
            writer.close();
            System.out.print("Successfully saved current catalog\n");
        }
        catch (IOException e) {
            System.err.print("Catalog not saved, exception: ");
            System.err.print(e.getMessage() + "\n");
        }
    }

    public void load(String name) throws MyException {
        try {
            if(!name.startsWith("catalog")) {
                throw new MyException(name + " is a bad file name. Must start with \"catalog\" and be followed by an integer");
            }
            items = new ArrayList<>();
            if (!name.endsWith(".myctg")) {
                name += ".myctg";
            }
            File file = new File("saved-catalogues\\" + name);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.substring(0, line.length() - 1);
                String[] splat = line.split("\\{");
                String[] attributes = splat[1].split(",");
                switch (splat[0]) {
                    case "CatalogItem":
                        items.add(new CatalogItem((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1])));
                        break;
                    case "Book":
                        items.add(new Book((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (Integer) parseAttribute(attributes[3])));
                        break;
                    case "Image":
                        items.add(new Image((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (Double) parseAttribute(attributes[2]), (Double) parseAttribute(attributes[3])));
                        break;
                    case "Movie":
                        items.add(new Movie((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (LocalDate) parseAttribute(attributes[3]), (Double) parseAttribute(attributes[4])));
                        break;
                    case "Song":
                        items.add(new Song((String) parseAttribute(attributes[0]), (String) parseAttribute(attributes[1]), (String) parseAttribute(attributes[2]), (LocalDate) parseAttribute(attributes[3]), (Double) parseAttribute(attributes[4])));
                        break;
                }
            }
            br.close();
        }
        catch (IOException | NullPointerException e) {
            System.err.print("Catalog not loaded, exception: ");
            System.err.print(e.getMessage() + "\n");
        }
    }

    private String packData() {
        StringBuilder sb = new StringBuilder();
        items.forEach((item) -> sb.append("\n").append(item));
        return sb.toString();
    }

    private Object parseAttribute(String string) {
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
