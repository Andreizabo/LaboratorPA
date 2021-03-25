import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveCommand implements Command {
    private boolean overwrite;

    public SaveCommand(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    @Override
    public void run(Catalog catalog) {
        int logIndex = CommandLogger.log("Command: Save catalog " + catalog.getName());
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
            CommandLogger.logResult(logIndex, "IO Exception", false);
            return;
        }

        CommandLogger.logResult(logIndex, "Success", true);
    }

    @Override
    public void runWithoutLogger(Catalog catalog) {
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

    private static String packData(Catalog catalog) {
        StringBuilder sb = new StringBuilder(catalog.getName());
        catalog.getItems().forEach((item) -> sb.append("\n").append(item));
        return sb.toString();
    }
}
