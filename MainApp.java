package ui;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Decision;
import service.DecisionEngine;

import java.util.Optional;

public class MainApp extends Application {

    private DecisionEngine engine = new DecisionEngine();

    @Override
    public void start(Stage stage) {

        // ---------- Buttons ----------
        Button addDecisionBtn = new Button("Add Decision");
        Button recordOutcomeBtn = new Button("Record Outcome");
        Button undoBtn = new Button("Undo Last Action");
        Button suggestBtn = new Button("Suggest Best Option");
        Button viewHistoryBtn = new Button("View History");

        // ---------- Colors ----------
        String textColor = "#3333cc";       // blue text
        String hoverTextColor = "#000099";  // darker blue on hover

        // ---------- Shadow ----------
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.25)); // subtle gray shadow
        shadow.setRadius(8);
        shadow.setOffsetX(0);
        shadow.setOffsetY(4);

        // ---------- Button Styles and Hover Animations ----------
        for (Button btn : new Button[]{addDecisionBtn, recordOutcomeBtn, undoBtn, suggestBtn, viewHistoryBtn}) {

            // Normal gradient style
            String normalStyle = "-fx-background-color: linear-gradient(to bottom, #F9C0C0, #F4A7A7);"
                    + "-fx-text-fill: " + textColor + ";"
                    + "-fx-font-weight: bold;"
                    + "-fx-font-size: 15;"
                    + "-fx-font-family: 'Verdana';"
                    + "-fx-background-radius: 12;"
                    + "-fx-cursor: hand;";

            // Hover gradient style
            String hoverStyle = "-fx-background-color: linear-gradient(to bottom, #F4A7A7, #F08080);"
                    + "-fx-text-fill: " + hoverTextColor + ";"
                    + "-fx-font-weight: bold;"
                    + "-fx-font-size: 15;"
                    + "-fx-font-family: 'Verdana';"
                    + "-fx-background-radius: 12;"
                    + "-fx-cursor: hand;";

            btn.setStyle(normalStyle);
            btn.setMinWidth(220);
            btn.setEffect(shadow); // initial shadow

            // ---------- Hover Effect with Lift Animation and Shadow ----------
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), btn);
            scaleUp.setToX(1.05);
            scaleUp.setToY(1.05);

            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), btn);
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);

            DropShadow hoverShadow = new DropShadow();
            hoverShadow.setColor(Color.rgb(0, 0, 0, 0.4)); // darker shadow on hover
            hoverShadow.setRadius(12);
            hoverShadow.setOffsetX(0);
            hoverShadow.setOffsetY(6);

            btn.setOnMouseEntered(e -> {
                btn.setStyle(hoverStyle);
                btn.setEffect(hoverShadow);
                scaleUp.playFromStart();
            });

            btn.setOnMouseExited(e -> {
                btn.setStyle(normalStyle);
                btn.setEffect(shadow);
                scaleDown.playFromStart();
            });
        }

        // ---------- Title Label ----------
        Label titleLabel = new Label("Digital Life Decisions Engine");

        // Gradient color for the title text
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;"
                + "-fx-text-fill: linear-gradient(to right, #3333cc, #000099);"
                + "-fx-font-family: 'Verdana';");

        // Pulsing animation
        ScaleTransition pulse = new ScaleTransition(Duration.millis(1200), titleLabel);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.05);
        pulse.setToY(1.05);
        pulse.setCycleCount(ScaleTransition.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();

        // ---------- Button Actions ----------
        addDecisionBtn.setOnAction(e -> handleAddDecision());
        recordOutcomeBtn.setOnAction(e -> handleRecordOutcome());
        undoBtn.setOnAction(e -> handleUndo());
        suggestBtn.setOnAction(e -> handleSuggestBestOption());
        viewHistoryBtn.setOnAction(e -> handleViewHistory());

        // ---------- Layout ----------
        VBox root = new VBox(15,
                titleLabel,
                addDecisionBtn,
                recordOutcomeBtn,
                undoBtn,
                suggestBtn,
                viewHistoryBtn
        );
        root.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #f0f8ff;"); // light blue background

        stage.setScene(new Scene(root, 480, 450));
        stage.setTitle("Digital Life Decision Engine");
        stage.show();
    }

    // ---------- Handlers ----------
    private void handleAddDecision() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter Decision Title:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(title -> {
            boolean added = engine.addDecision(title.trim());
            if (added) {
                addOptionsLoop(title.trim());
                showAlert(Alert.AlertType.INFORMATION, "Decision added successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Decision already exists or invalid title!");
            }
        });
    }

    private void addOptionsLoop(String title) {
        boolean addingOptions = true;
        while (addingOptions) {
            TextInputDialog optionDialog = new TextInputDialog();
            optionDialog.setHeaderText("Enter an Option for '" + title + "' (Leave empty to finish):");
            Optional<String> optionResult = optionDialog.showAndWait();
            if (optionResult.isPresent()) {
                String option = optionResult.get().trim();
                if (option.isEmpty()) {
                    addingOptions = false;
                } else {
                    boolean optAdded = engine.addOption(title, option);
                    if (!optAdded) {
                        showAlert(Alert.AlertType.WARNING, "Option already exists or invalid!");
                    }
                }
            } else {
                addingOptions = false;
            }
        }
    }

    private void handleRecordOutcome() {
        if (engine.getDecisionTitles().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No decisions available!");
            return;
        }

        String decisionTitle = selectDecision();
        if (decisionTitle == null) return;

        Decision decision = engine.getDecisions().get(decisionTitle);
        if (decision.getOptionSuccess().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No options available for this decision!");
            return;
        }

        String option = selectOption(decision);
        if (option == null) return;

        boolean success = engine.recordOutcome(decisionTitle, option);
        if (success) showAlert(Alert.AlertType.INFORMATION, "Outcome recorded successfully!");
        else showAlert(Alert.AlertType.ERROR, "Failed to record outcome!");
    }

    private void handleUndo() {
        boolean undone = engine.undoLastAction();
        if (undone) showAlert(Alert.AlertType.INFORMATION, "Last action undone successfully!");
        else showAlert(Alert.AlertType.WARNING, "No action to undo!");
    }

    private void handleSuggestBestOption() {
        if (engine.getDecisionTitles().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No decisions available!");
            return;
        }

        String decisionTitle = selectDecision();
        if (decisionTitle == null) return;

        String bestOption = engine.suggestBestOption(decisionTitle);
        showAlert(Alert.AlertType.INFORMATION, "Best Option: " + bestOption);
    }

    private void handleViewHistory() {
        String history = engine.viewHistory();

        // ---------- History TextArea ----------
        TextArea area = new TextArea(history);
        area.setEditable(false);
        area.setWrapText(true);
        area.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14; -fx-control-inner-background: #fdfdfd; -fx-text-fill: #3333cc;");

        // ---------- ScrollPane ----------
        ScrollPane scrollPane = new ScrollPane(area);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: #f0f8ff; -fx-background-color: #f0f8ff;");

        // ---------- Gradient Title ----------
        Label historyTitle = new Label("Decision History");
        historyTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold;"
                + "-fx-text-fill: linear-gradient(to right, #3333cc, #000099);"
                + "-fx-font-family: 'Verdana';");

        VBox content = new VBox(10, historyTitle, scrollPane);
        content.setStyle("-fx-padding: 15; -fx-background-color: #f0f8ff; -fx-alignment: center;");

        // ---------- Alert Dialog ----------
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Decision History");
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    // ---------- Helper Methods ----------
    private String selectDecision() {
        ChoiceDialog<String> decisionDialog = new ChoiceDialog<>(
                engine.getDecisionTitles().iterator().next(),
                engine.getDecisionTitles()
        );
        decisionDialog.setHeaderText("Select a Decision:");
        Optional<String> selectedDecision = decisionDialog.showAndWait();
        return selectedDecision.map(String::trim).orElse(null);
    }

    private String selectOption(Decision decision) {
        ChoiceDialog<String> optionDialog = new ChoiceDialog<>(
                decision.getOptionSuccess().keySet().iterator().next(),
                decision.getOptionSuccess().keySet()
        );
        optionDialog.setHeaderText("Select an Option:");
        Optional<String> selectedOption = optionDialog.showAndWait();
        return selectedOption.map(String::trim).orElse(null);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
