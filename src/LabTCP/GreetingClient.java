package LabTCP;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Client TCP-Socket
 *
 * @author Titorenko Dmitry
 * @since 2016-05-20
 */

public class GreetingClient extends Application {
    private static String serverName = "localhost";
    private static int port = 6066;
    private static String name;
    private static String ServerAnswer;

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * This method is used to make client Socket
     */
    private static void clientSocket() {
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(name);
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            ServerAnswer = in.readUTF();
            System.out.println("Server says: "+ServerAnswer);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is make JavaFX
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client");


        //Create a GridPane Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Add Text, Labels, and Text Fields
        Text scenetitle = new Text("Welcome to TCP client!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Full Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        //Add  Button
        Button btn = new Button("Send");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        //Add Text
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //set Button Event
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                name = userTextField.getText();
                clientSocket();
                actiontarget.setText("Server says: " + ServerAnswer);
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}