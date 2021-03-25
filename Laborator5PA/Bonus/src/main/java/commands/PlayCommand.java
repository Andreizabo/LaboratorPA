import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayCommand implements Command {
    private int entryIndex;

    public PlayCommand(int entryIndex) {
        this.entryIndex = entryIndex;
    }

    public int getEntryIndex() {
        return entryIndex;
    }

    public void setEntryIndex(int entryIndex) {
        this.entryIndex = entryIndex;
    }

    @Override
    public void run(Catalog catalog) {
        int logIndex = CommandLogger.log("Command: Play item in catalog " + catalog.getName() + " at index " + entryIndex);
        try {
            Desktop.getDesktop().open(new File(catalog.getItems().get(entryIndex).getPath()));
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
            Desktop.getDesktop().open(new File(catalog.getItems().get(entryIndex).getPath()));
        }
        catch (IOException | NullPointerException e) {
            System.err.print(e.getMessage() + "\n");
        }
    }
}
