package workshop.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import workshop.MainApp;
import workshop.dao.JDBCDao;
import workshop.model.fx.ConnectionDetails;
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
    public void initialize() throws ClassNotFoundException, IOException, BackingStoreException {
        url.textProperty().bindBidirectional(connection.urlProperty());
        username.textProperty().bindBidirectional(connection.usernameProperty());
        password.textProperty().bindBidirectional(connection.passwordProperty());
        
        List<ConnectionDetails> connectionDetails = loadAllConnectionDetails(getUserPreferences());
        datasource.setItems(FXCollections.observableArrayList(connectionDetails));
    }
    
    @FXML
    public void datasourceSelected() {
        ConnectionDetails value = datasource.getValue();
        connection.setUrl(value.getUrl());
        connection.setUsername(value.getUsername());
        connection.setPassword(null);
    }
    
    @FXML
    public void connect() {
        if (connection.isValid()) {
            try {
                JDBCDao dao = new JDBCDao(connection.getUrl(), connection.getUsername(), connection.getPassword());
                saveConnectionDetails(connection);
                DefaultClientService service = new DefaultClientService(dao);
                mainApp.setClientService(service);
                mainApp.showClientView();
                
            }
            catch (Exception e) {
                mainApp.showError(e);
            }
        }
        else {
            mainApp.showWarning("Please fill URL, user name and password");
        }
    }
    
    private void saveConnectionDetails(ConnectionDetails connection) throws Exception {
        Preferences preferences = getUserPreferences();
        List<ConnectionDetails> connectionDetails = loadAllConnectionDetails(preferences);
        if (!connectionDetails.contains(connection)) {
            connectionDetails.add(connection);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (ObjectOutputStream o = new ObjectOutputStream(out)) {
                o.writeObject(connectionDetails);
            }
            
            preferences.putByteArray("connections", out.toByteArray());
            preferences.flush();
        }
    }
    
    private Preferences getUserPreferences() {
        Preferences preferences = Preferences.userNodeForPackage(getClass());
        return preferences;
    }
    
    List<ConnectionDetails> loadAllConnectionDetails(Preferences preferences) throws IOException,
            ClassNotFoundException {
        byte[] existingConnections = preferences.getByteArray("connections", null);
        if (existingConnections == null) { return new ArrayList<>(); }
        
        try (ObjectInputStream o = new ObjectInputStream(new ByteArrayInputStream(existingConnections))) {
            return (List<ConnectionDetails>) o.readObject();
        }
    }
    
}
