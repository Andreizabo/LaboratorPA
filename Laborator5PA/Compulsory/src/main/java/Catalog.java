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
    private String name;
    private String path;
    private List<CatalogItem> items;

    public Catalog(String name, String path, List<CatalogItem> items) {
        this.name = name;
        this.path = path;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public CatalogItem getCatalogItem(String name) {
        return items.stream().filter((item) -> item.getName().equals(name)).findFirst().orElse(null);
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
}
