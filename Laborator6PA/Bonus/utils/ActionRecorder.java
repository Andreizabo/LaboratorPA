import java.util.ArrayList;
import java.util.List;

public class ActionRecorder {
    private static final long MAX_ACTIONS = 20;
    private static List<Action> actions = new ArrayList<>();
    private static List<Action> undoneActions = new ArrayList<>();

    public static void addAction(Action a) {
        if(actions.size() >= MAX_ACTIONS) {
            actions.remove(0);
        }
        actions.add(a);
        undoneActions = new ArrayList<>();
    }

    public static boolean isLastActionFinished() {
        if(actions.get(actions.size() - 1) instanceof ActionTransformed) {
            return ((ActionTransformed)actions.get(actions.size() - 1)).isFinished();
        }
        else {
            return true;
        }
    }

    public static Action getLastAction() {
        return actions.get(actions.size() - 1);
    }

    public static void undoAction() {
        if(actions.isEmpty()) {
            return;
        }
        actions.get(actions.size() - 1).undo();
        undoneActions.add(actions.get(actions.size() - 1));
        actions.remove(actions.size() - 1);
    }

    public static void redoAction() {
        if(undoneActions.isEmpty()) {
            return;
        }
        undoneActions.get(undoneActions.size() - 1).redo();
        actions.add(undoneActions.get(undoneActions.size() - 1));
        undoneActions.remove(undoneActions.size() - 1);
    }

    public static void reset() {
        actions = new ArrayList<>();
        undoneActions = new ArrayList<>();
    }
}
