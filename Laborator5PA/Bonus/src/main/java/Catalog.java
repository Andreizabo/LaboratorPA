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

    public Catalog() {
    }

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

    public void setItems(List<CatalogItem> items) {
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

    public CatalogItem getCatalogItem(String name) {
        return items.stream().filter((item) -> item.getName().equals(name)).findFirst().orElse(null);
    }

    public List<CatalogItem> getItems() {
        return items;
    }

}
