import java.io.File;

public abstract class CatalogItem {
    protected String name;
    protected String path;

    public CatalogItem(String name, String path) throws MyException {
        File file = new File(path);
        if(!file.exists()) {
            throw new MyException(name + " file does not exist at " + path);
        }
        if(file.isDirectory()) {
            throw new MyException(name + " is a directory.");
        }
        this.name = name;
        this.path = path;
    }

    public CatalogItem() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) throws MyException {
        File file = new File(path);
        if(!file.exists()) {
            throw new MyException(name + " file does not exist at " + path);
        }
        if(file.isDirectory()) {
            throw new MyException(name + " is a directory.");
        }
        this.path = path;
    }
}
