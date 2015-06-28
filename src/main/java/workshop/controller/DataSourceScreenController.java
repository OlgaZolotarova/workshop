package workshop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import workshop.MainApp;
import workshop.dao.JDBCDao;
import workshop.model.ConnectionDetails;
import workshop.service.DefaultClientService;

public class DataSourceScreenController {
    
    @FXML
    ComboBox<ConnectionDetails> datasource;
    
    ConnectionDetails connection = new ConnectionDetails();
    
    @FXML
    TextField url;
    
    @FXML
    TextField username;
    
    @FXML
    TextField password;
    
    @FXML
    Button connectButton;
    
    private MainApp mainApp;
    
    public DataSourceScreenController(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    public void initialize() {
        url.textProperty().bindBidirectional(connection.urlProperty());
        username.textProperty().bindBidirectional(connection.usernameProperty());
        password.textProperty().bindBidirectional(connection.passwordProperty());
        

    }

    @FXML
    public void datasourceChanged() {
        
    }
    
    @FXML
    public void connect() {
        if (connection.isValid()) {            
            try {
                JDBCDao dao = new JDBCDao(connection.getUrl(), connection.getUsername(), connection.getPassword());
                DefaultClientService service = new DefaultClientService(dao);
                mainApp.setClientService(service);
                mainApp.showClientView();
                
            }
            catch (Exception e) {
                mainApp.showError(e);
            }
        } else {
           mainApp.showWarning("Please fill URL, user name and password");
        }
    }
    
}
