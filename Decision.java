package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Decision implements Serializable {

    private String title;
    private Map<String, Integer> optionSuccess;

    public Decision(String title) {
        this.title = title;
        this.optionSuccess = new HashMap<>();
    }

    // Add a new option
    public boolean addOption(String option) {
        if (option == null || option.trim().isEmpty()) {
            return false; // Cannot add empty option
        }
        if (optionSuccess.containsKey(option)) {
            return false; // Option already exists
        }
        optionSuccess.put(option, 0);
        return true;
    }

    // Record success for a particular option
    public boolean recordSuccess(String option) {
        if (!optionSuccess.containsKey(option)) {
            return false; // Option does not exist
        }
        optionSuccess.put(option, optionSuccess.get(option) + 1);
        return true;
    }

    // Undo last success (decrement)
    public boolean undoSuccess(String option) {
        if (!optionSuccess.containsKey(option)) {
            return false; // Option does not exist
        }
        int current = optionSuccess.get(option);
        if (current > 0) {
            optionSuccess.put(option, current - 1);
            return true;
        }
        return false; // Already zero, cannot decrement
    }

    public String getTitle() {
        return title;
    }

    public Map<String, Integer> getOptionSuccess() {
        return optionSuccess;
    }

    // Optional: Get best option based on success count
    public String getBestOption() {
        if (optionSuccess.isEmpty()) return "No options available";
        return optionSuccess.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Decision: ").append(title).append("\n");
        for (var entry : optionSuccess.entrySet()) {
            sb.append("  Option: ").append(entry.getKey())
                    .append(" | Success: ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
