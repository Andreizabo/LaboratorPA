public class ListCommand implements Command {
    private String prefix, suffix;

    public ListCommand(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public void run(Catalog catalog) {
        int logIndex = CommandLogger.log("Command: List catalog " + catalog.getName() + " contents");
        try {
            final StringBuilder sb = new StringBuilder(prefix);
            sb.append("Catalog = {");
            catalog.getItems().forEach((item) -> sb.append("\n\t").append(item));
            sb.append("\n}");
            sb.append(suffix);
            System.out.print(sb.toString());
        }
        catch (NullPointerException e) {
            CommandLogger.logResult(logIndex, "Null Pointer Exception", false);
            return;
        }

        CommandLogger.logResult(logIndex, "Success", true);
    }

    @Override
    public void runWithoutLogger(Catalog catalog) {
        try {
            final StringBuilder sb = new StringBuilder(prefix);
            sb.append("Catalog = {");
            catalog.getItems().forEach((item) -> sb.append("\n\t").append(item));
            sb.append("\n}");
            sb.append(suffix);
            System.out.print(sb.toString());
        }
        catch (NullPointerException e) {
            System.err.print(e.getMessage() + "\n");
        }
    }
}
