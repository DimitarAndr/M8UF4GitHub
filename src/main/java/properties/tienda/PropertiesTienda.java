package properties.tienda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class PropertiesTienda {
    private String urlBBBD;
    private String nameBBDD;
    private String user;
    private String password;

    public PropertiesTienda(String urlBBBD, String nameBBDD, String user, String password) {
        this.urlBBBD = urlBBBD;
        this.nameBBDD = nameBBDD;
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

    private static List<String> allInfo() {

        final String FILENAME = "PropertiesSystem";


        BufferedReader br = null;
        FileReader fr = null;
        try {
            
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            }catch (Exception e){
                Logger.getLogger("Error "+e);
            }
        }

        List<String> info=Collections.singletonList("a");
        return info;
    }

}

