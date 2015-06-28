package workshop.controller;

import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import workshop.MainApp;
import workshop.model.Adres;
import workshop.model.Klant;

public class ClientTableViewController {
    
    private final class AdresPropertyRetriever<T> implements
            Callback<TableColumn.CellDataFeatures<Klant, T>, ObservableValue<T>> {
        
        private String adresProperty;
        
        public AdresPropertyRetriever(String adresProperty) {
            this.adresProperty = adresProperty;
        }
        
        @Override
        public ObservableValue<T> call(CellDataFeatures<Klant, T> param) {
            PropertyValueFactory<Adres, T> factory = new PropertyValueFactory<>(adresProperty);
            Klant klant = param.getValue();
            Adres adres = klant.getAdres();
            ObservableValue<T> value = factory.call(new CellDataFeatures<>(null, null, adres));
            return value;
        }
    }
    
    private final ObservableList<Klant> clientsList = FXCollections.observableArrayList();
    @FXML
    private TableView<Klant> table;
    
    @FXML
    private TableColumn<Klant, String> voornaamColumn;
    
    @FXML
    private TableColumn<Klant, String> achternaamColumn;
    
    @FXML
    private TableColumn<Klant, String> tussenvoegselColumn;
    
    @FXML
    private TableColumn<Klant, String> emailColumn;
    
    @FXML
    private TableColumn<Klant, String> postcodeColumn;
    
    @FXML
    private TableColumn<Klant, Integer> huisnummerColumn;
    
    @FXML
    private TableColumn<Klant, String> toevoegingColumn;
    
    @FXML
    private TableColumn<Klant, String> straatnaamColumn;
    
    @FXML
    private TableColumn<Klant, String> woonplaatsColumn;
    
    private MainApp mainApp;
    
    public ClientTableViewController(MainApp mainApp) {
        this.mainApp = mainApp;
        
    }
    
    @FXML
    public void initialize() {
        voornaamColumn.setCellValueFactory(new PropertyValueFactory<>("voornaam"));
        achternaamColumn.setCellValueFactory(new PropertyValueFactory<>("achternaam"));
        tussenvoegselColumn.setCellValueFactory(new PropertyValueFactory<>("tussenvoegsel"));
        emailColumn.setCellValueFactory(adresProperty("email"));
        postcodeColumn.setCellValueFactory(adresProperty("postcode"));
        huisnummerColumn.setCellValueFactory(adresProperty("huisnummer"));
        toevoegingColumn.setCellValueFactory(adresProperty("toevoeging"));
        straatnaamColumn.setCellValueFactory(adresProperty("straatnaam"));
        woonplaatsColumn.setCellValueFactory(adresProperty("woonplaats"));
        
        table.setItems(clientsList);
        clientsList.clear();
        Klant klant = new Klant();
        reloadClients(klant);
    }
    
    private <T> Callback<CellDataFeatures<Klant, T>, ObservableValue<T>> adresProperty(String adresProperty) {
        return new AdresPropertyRetriever<T>(adresProperty);
    }
    
    private void reloadClients(Klant filter) {
        List<Klant> list = mainApp.getClientService().search(filter);
        clientsList.addAll(list);
    }
    
    public void newClient() {
    }
    
    public void saveClient() {
    }
    
    public void deleteClient() {
    }
    
    public void generateClients() {
    }
}
