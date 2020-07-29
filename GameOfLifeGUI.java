/* 
 * Based on this implementation:
 * https://steemit.com/programming/@satoshio/conway-s-game-of-life-implementation-in-javafx
 * Modified to separate view from model
 */

package game_of_life;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class GameOfLifeGUI extends Application {

    private static final int width = 500;
    private static final int height = 500;
    private static final int cellSize = 10;
    int rows;
    int cols;
    
    private LifeModel lifeModel;
    private TextField percentAliveField;
    private Canvas canvas;
            
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // update every second
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(500)) {
                    next();
                    lastUpdate = now;
                }
            }
        };
        
        VBox root = new VBox(10);
        Scene scene = new Scene(root, width, height + 100);
        
        canvas = new Canvas(width, height);

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(v -> resetSimulation());
        
        Button nextGenBtn = new Button("Next Gen");
        nextGenBtn.setOnAction(v -> next());
        
        Button runBtn = new Button("Run");
        runBtn.setOnAction(v -> runAnimation.start());
        
        Button stopBtn = new Button("Stop");
        stopBtn.setOnAction(v -> runAnimation.stop());
        
        percentAliveField = new TextField("50");
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(canvas, new HBox(10, percentAliveField, resetBtn), new HBox(10, nextGenBtn, runBtn, stopBtn));
        
        
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();

        rows = (int) Math.floor(height / cellSize);
        cols = (int) Math.floor(width / cellSize);

        lifeModel = new LifeModel(rows, cols);
        lifeModel.initialize(50);
        draw();

    }
    
    
    private void resetSimulation() {
        int probability = Integer.parseInt(percentAliveField.getText());
        lifeModel.initialize(probability);
        draw();
    }
    
    
    private void next() {
        lifeModel.nextGeneration();
        draw();
    }
    
    private void draw() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        
        // clear graphics
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Draw border 
                graphics.setFill(Color.LIGHTGRAY);
                graphics.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);

                if (lifeModel.cellAt(i, j) == LifeModel.ALIVE) {
                    graphics.setFill(Color.BROWN);
                    graphics.fillOval((j * cellSize) + 1, (i * cellSize) + 1, cellSize - 2, cellSize - 2);
                }
                else {
                    graphics.setFill(Color.LIGHTCYAN);
                    graphics.fillRect((j * cellSize) + 1, (i * cellSize) + 1, cellSize - 2, cellSize - 2);
                }
            }
        }
    }

}