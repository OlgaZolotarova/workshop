package workshop.model.fx;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Stores database URL, user name and password. When this object is serialized,
 * only user name and URL are persisted.
 * 
 * @author olga
 *
 */
public class ConnectionDetails implements Externalizable {
    
    private static final long serialVersionUID = -2569890081873696695L;
    
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
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(getUrl());
        out.writeUTF(getUsername());
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setUrl(in.readUTF());
        setUsername(in.readUTF());
    }
    
    // @Override
    // public boolean equals(Object obj) {
    // if (getClass() != obj.getClass())
    // return false;
    // ConnectionDetails other = (ConnectionDetails) obj;
    // return getUsername().equals(other.getUsername()) &&
    // getUrl().equals(other.getUrl());
    // }
    
    @Override
    public String toString() {
        return getUsername() + "@" + getUrl();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConnectionDetails other = (ConnectionDetails) obj;
        if (getUrl() == null) {
            if (other.getUrl() != null)
                return false;
        }
        else if (!getUrl().equals(other.getUrl()))
            return false;
        if (getUsername() == null) {
            if (other.getUsername() != null)
                return false;
        }
        else if (!getUsername().equals(other.getUsername()))
            return false;
        return true;
    }
}
