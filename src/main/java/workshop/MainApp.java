package workshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import workshop.controller.ClientTableViewController;
import workshop.controller.DataSourceScreenController;
import workshop.service.ClientService;

public class MainApp extends Application {
    
    private Stage primaryStage;
    private BorderPane root;
    private ClientService clientService;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Workshop application");
        try {
            initRootLayout();
            chooseDataSource();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
    private void chooseDataSource() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new DataSourceScreenController(this));
        loader.setLocation(getClass().getResource("/workshop/view/DataSourceScreen.fxml"));
        AnchorPane dataSourceScreen = loader.load();
        root.setCenter(dataSourceScreen);
        BorderPane.setAlignment(dataSourceScreen, Pos.CENTER);
    }
    
    private void initRootLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/workshop/view/RootLayout.fxml"));
        root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void showWarning(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Please fix the following errors");
        alert.setContentText(message);
        
        alert.showAndWait();
    }
    
    public void showError(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An application error occured");
        alert.setContentText(e.getMessage());
        
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();
        
        Label label = new Label("The exception stacktrace was:");
        
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        
        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        
        alert.showAndWait();
    }
    
    public void setClientService(ClientService service) {
        this.clientService = service;
        
    }
    
    public ClientService getClientService() {
        return clientService;
    }
    
    public void showClientView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/workshop/view/ClientTableView.fxml"));
        loader.setController(new ClientTableViewController(this));
        try {
            Node node = loader.load();
            root.setCenter(node);
        }
        catch (IOException e) {
            showError(e);
        }
    }
}
