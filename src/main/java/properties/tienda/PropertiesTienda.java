package properties.tienda;

public class PropertiesTienda {
    private  String urlBBBD;
    private  String user;
    private  String password;

    public PropertiesTienda(String urlBBBD, String user, String password) {
        this.urlBBBD = urlBBBD;
        this.user = user;
        this.password = password;
    }

    public String getUrlBBBD() {
        return this.urlBBBD;
    }

    public void setUrlBBBD(String urlBBBD) {
        this.urlBBBD = urlBBBD;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
