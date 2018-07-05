package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class CommonUtils {

    private CommonUtils() {

    }

    /**
     * Retorna o valor da propriedade contida no arquivo conf/config.properties
     * @param property uma propriedade existente no arquivo config/config.properties
     * @return o valor da propriedade informada
     */
    public static String getValueFromConfigFile(String property) {
        Properties properties;
        String valor = null;
        try {
            properties = new Properties();
            properties.load(new FileReader(new File("conf/config.properties")));

            valor =  properties.getProperty(property);
        } catch (IOException | NullPointerException e) {
            Log.log(Level.SEVERE, e.getMessage(), e);
        }
        return valor;
    }
}
