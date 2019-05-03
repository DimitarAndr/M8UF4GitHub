package utils.constants;


import org.junit.Test;
import static org.junit.Assert.*;
public class ConstantsTest {

    public static final String ERRORBD = "ERROR EN LA BASE DE DATOS";


    @Test
    public void getERRORBD() {
        assertSame("ERROR EN LA BASE DE DATOS",ERRORBD);
    }
}