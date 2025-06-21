package org.notionclone.utils;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowResizer {
    private static final int RESIZE_MARGIN = 6;

    public static void makeResizable(Stage stage, Scene scene) {
        scene.setOnMouseMoved(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            double width = stage.getWidth();
            double height = stage.getHeight();

            boolean left = mouseX < RESIZE_MARGIN;
            boolean right = mouseX > width - RESIZE_MARGIN;
            boolean top = mouseY < RESIZE_MARGIN;
            boolean bottom = mouseY > height - RESIZE_MARGIN;

            if (left && top) scene.setCursor(Cursor.NW_RESIZE);
            else if (left && bottom) scene.setCursor(Cursor.SW_RESIZE);
            else if (right && top) scene.setCursor(Cursor.NE_RESIZE);
            else if (right && bottom) scene.setCursor(Cursor.SE_RESIZE);
            else if (left) scene.setCursor(Cursor.W_RESIZE);
            else if (right) scene.setCursor(Cursor.E_RESIZE);
            else if (top) scene.setCursor(Cursor.N_RESIZE);
            else if (bottom) scene.setCursor(Cursor.S_RESIZE);
            else scene.setCursor(Cursor.DEFAULT);
        });

        scene.setOnMouseDragged(event -> {
            Cursor cursor = scene.getCursor();

            double mouseX = event.getScreenX();
            double mouseY = event.getScreenY();
            double stageX = stage.getX();
            double stageY = stage.getY();
            double stageWidth = stage.getWidth();
            double stageHeight = stage.getHeight();

            switch (cursor.toString()) {
                case "NW_RESIZE" -> {
                    double newWidth = stageWidth - (mouseX - stageX);
                    double newHeight = stageHeight - (mouseY - stageY);
                    if (newWidth > stage.getMinWidth()) {
                        stage.setX(mouseX);
                        stage.setWidth(newWidth);
                    }
                    if (newHeight > stage.getMinHeight()) {
                        stage.setY(mouseY);
                        stage.setHeight(newHeight);
                    }
                }
                case "NE_RESIZE" -> {
                    double newWidth = mouseX - stageX;
                    double newHeight = stageHeight - (mouseY - stageY);
                    if (newWidth > stage.getMinWidth()) stage.setWidth(newWidth);
                    if (newHeight > stage.getMinHeight()) {
                        stage.setY(mouseY);
                        stage.setHeight(newHeight);
                    }
                }
                case "SW_RESIZE" -> {
                    double newWidth = stageWidth - (mouseX - stageX);
                    double newHeight = mouseY - stageY;
                    if (newWidth > stage.getMinWidth()) {
                        stage.setX(mouseX);
                        stage.setWidth(newWidth);
                    }
                    if (newHeight > stage.getMinHeight()) stage.setHeight(newHeight);
                }
                case "SE_RESIZE" -> {
                    double newWidth = mouseX - stageX;
                    double newHeight = mouseY - stageY;
                    if (newWidth > stage.getMinWidth()) stage.setWidth(newWidth);
                    if (newHeight > stage.getMinHeight()) stage.setHeight(newHeight);
                }
                case "E_RESIZE" -> {
                    double newWidth = mouseX - stageX;
                    if (newWidth > stage.getMinWidth()) stage.setWidth(newWidth);
                }
                case "W_RESIZE" -> {
                    double newWidth = stageWidth - (mouseX - stageX);
                    if (newWidth > stage.getMinWidth()) {
                        stage.setX(mouseX);
                        stage.setWidth(newWidth);
                    }
                }
                case "N_RESIZE" -> {
                    double newHeight = stageHeight - (mouseY - stageY);
                    if (newHeight > stage.getMinHeight()) {
                        stage.setY(mouseY);
                        stage.setHeight(newHeight);
                    }
                }
                case "S_RESIZE" -> {
                    double newHeight = mouseY - stageY;
                    if (newHeight > stage.getMinHeight()) stage.setHeight(newHeight);
                }
            }
        });
    }
}

