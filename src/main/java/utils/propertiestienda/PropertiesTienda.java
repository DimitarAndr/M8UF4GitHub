package utils.propertiestienda;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class PropertiesTienda {
    private PropertiesTienda() {
    }
    public static Properties getPropertiesDDBB() throws IOException {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream("E:\\DAW2\\M8\\UF2-NEW\\PropertiesSystem");
            prop.load(is);
        } catch(IOException e) {
            Logger.getLogger("Error "+e);
        }

        Properties props=null;
        StringBuilder  sb = new StringBuilder();
        sb.append(prop.getProperty("servidor.usuario")+",");
        sb.append(prop.getProperty("servidor.password"));
        String properties = sb.toString();
        props.load(new StringReader(properties)) ;

        return props;
    }
}
