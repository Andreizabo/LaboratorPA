import java.util.ArrayList;
import java.util.List;

public class CommandLogger {
    public static List<MyPair<String, Boolean>> commands;
    //Commands will hold all the logs, containing pairs of strings and booleans
    //The string will be the log of a certain command, while the boolean will specify whether or not it created an exception or failed.

    /**
     * Initializes the logger
     */
    public static void init() {
        commands = new ArrayList<>(); // ArrayList because we will never remove anything, and ArrayList is fast for add/get
        int index = log("Logger initialized");
        logResult(index, "Success", true);
    }

    /**
     * Logs a command, and returns the index of its log
     * @param string The command
     * @return The index, which will be used to add the result after the command is ran
     */
    public static int log(String string) {
        commands.add(new MyPair<>("\n" + string + " -> Result: ", false));
        return commands.size() - 1;
    }

    /**
     * Logs the result of a command, the index is obtained from the "log" method
     * @param index The index of the command
     * @param result The result
     */
    public static void logResult(int index, String result, boolean success) {
        commands.set(index, new MyPair<>(commands.get(index).getFirst() + result, success));
    }

    /**
     * Logs an exception
     * @param string The exception message
     */
    public static void logException(String string) {
        commands.add(new MyPair<>("\n" + string, false));
    }

    /**
     * Prints the log
     */
    public static void printLog() {
        System.out.print("\nStart of logger: \n");
        commands.forEach((logEntry) -> {
            if(logEntry.getSecond()) {
                System.out.print(logEntry.getFirst());
            }
            else {
                System.err.print(logEntry.getFirst());
            }
        });
        System.out.print("\n\nEnd of logger. Exceptions:\n");
    }
}
