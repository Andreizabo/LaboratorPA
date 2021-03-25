import java.util.concurrent.atomic.AtomicBoolean;

public class AddCommand implements Command {
    private CatalogItem entry;

    public AddCommand(CatalogItem entry) {
        this.entry = entry;
    }

    public CatalogItem getEntry() {
        return entry;
    }

    public void setEntry(CatalogItem entry) {
        this.entry = entry;
    }

    @Override
    public void run(Catalog catalog) {
        int logIndex = CommandLogger.log("Command: Add entry to catalog " + catalog.getName());

        try {
            AtomicBoolean exists = new AtomicBoolean(false);
            catalog.getItems().forEach((item) -> exists.set(exists.get() || item.equals(entry)));
            if (exists.get()) {
                CommandLogger.logResult(logIndex, "Item already exists in catalog", false);
                return;
            } else {
                catalog.getItems().add(entry);
            }
        }
        catch (NullPointerException e) {
            CommandLogger.logResult(logIndex, "Null Pointer Exception", false);
            return;
        }

        CommandLogger.logResult(logIndex, "Success", true);
    }

    @Override
    public void runWithoutLogger(Catalog catalog) throws MyException {
        try {
            AtomicBoolean exists = new AtomicBoolean(false);
            catalog.getItems().forEach((item) -> exists.set(exists.get() || item.equals(entry)));
            if (exists.get()) {
                throw new MyException("The item already exists in this catalog");
            } else {
                catalog.getItems().add(entry);
            }
        }
        catch (NullPointerException e) {
            System.err.print(e.getMessage() + "\n");
        }
    }
}
