package utils.propertiestienda;

import org.junit.Test;
import utils.constants.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class PropertiesTienda {

    public PropertiesTienda() {
    }

    public static  Properties getPropertiesDDBB() {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream("E:\\DAW2\\M8\\UF2-NEW\\src\\main\\java\\utils\\propertiestienda\\PropertiesSystem");
            prop.load(is);

        } catch (IOException e) {
            Logger.getLogger(e + Constants.ERRORBD);
        }
        return prop;
    }
    @Test
    public void sum(){
        int a = 5;
        assertEquals(5,a);
    }
}
