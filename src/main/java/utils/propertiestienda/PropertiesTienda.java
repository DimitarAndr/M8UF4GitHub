package utils.propertiestienda;

import utils.constants.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;


public class PropertiesTienda {

    PropertiesTienda() {
    }

    public static Properties getPropertiesDDBB() {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream("E:\\DAW2\\M8\\UF2-NEW\\src\\main\\resources\\PropertiesSystem");
            prop.load(is);

        } catch (IOException e) {
            Logger.getLogger(e + Constants.ERRORBD);
        }
        return prop;
    }

}
