package service;

import model.Decision;
import model.Action;
import util.FileManager;

import java.util.*;

public class DecisionEngine {

    private Map<String, Decision> decisions = new HashMap<>();
    private Stack<Action> undoStack = new Stack<>();

    // Constructor: load existing decisions from file
    public DecisionEngine() {
        List<Decision> loaded = FileManager.load();
        for (Decision d : loaded) {
            decisions.put(d.getTitle(), d);
        }
    }

    // Add a new decision
    public boolean addDecision(String title) {
        if (title == null || title.trim().isEmpty() || decisions.containsKey(title)) {
            return false; // Invalid or duplicate decision
        }
        decisions.put(title, new Decision(title));
        saveToFile();
        return true;
    }

    // Add an option to a decision
    public boolean addOption(String title, String option) {
        Decision d = decisions.get(title);
        if (d == null) return false; // Decision not found
        boolean added = d.addOption(option);
        if (added) saveToFile();
        return added;
    }

    // Record success for a decision's option
    public boolean recordOutcome(String title, String option) {
        Decision d = decisions.get(title);
        if (d == null) return false;
        boolean success = d.recordSuccess(option);
        if (success) {
            undoStack.push(new Action(title, option));
            saveToFile();
        }
        return success;
    }

    // Undo last action
    public boolean undoLastAction() {
        if (undoStack.isEmpty()) return false;
        Action action = undoStack.pop();
        Decision d = decisions.get(action.decisionTitle);
        if (d != null) {
            boolean undone = d.undoSuccess(action.option);
            if (undone) {
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // Suggest best option for a decision
    public String suggestBestOption(String title) {
        Decision d = decisions.get(title);
        if (d == null || d.getOptionSuccess().isEmpty()) return "No data available";
        return d.getBestOption();
    }

    // View history of all decisions
    public String viewHistory() {
        if (decisions.isEmpty()) return "No decision history available";
        StringBuilder sb = new StringBuilder();
        for (Decision d : decisions.values()) {
            sb.append(d.toString()).append("\n");
        }
        return sb.toString();
    }

    // Get all decisions (for GUI ComboBox or ChoiceDialog)
    public Set<String> getDecisionTitles() {
        return decisions.keySet();
    }

    // Get all decisions map (for accessing getOptionSuccess safely)
    public Map<String, Decision> getDecisions() {
        return decisions;
    }


    // Internal: save decisions to file
    private void saveToFile() {
        FileManager.save(decisions.values());
    }
}
