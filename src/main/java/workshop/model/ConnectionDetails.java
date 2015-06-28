package workshop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConnectionDetails {
    
    private final StringProperty urlProperty = new SimpleStringProperty();
    
    private final StringProperty usernameProperty = new SimpleStringProperty();
    
    private final StringProperty passwordProperty = new SimpleStringProperty();
    
    public StringProperty urlProperty() {
        return urlProperty;
    }
    
    public StringProperty passwordProperty() {
        return passwordProperty;
    }
    
    public StringProperty usernameProperty() {
        return usernameProperty;
    }
    
    public String getUrl() {
        return urlProperty.get();
    }
    
    public void setUrl(String url) {
        this.urlProperty.set(url);
    }
    
    public String getUsername() {
        return usernameProperty.get();
    }
    
    public void setUsername(String username) {
        this.usernameProperty.set(username);
    }
    
    public String getPassword() {
        return passwordProperty.get();
    }
    
    public void setPassword(String password) {
        this.passwordProperty.get();
    }
    
    public boolean isValid() {
        return getUrl() != null && getUsername() != null;
    }
    
}
