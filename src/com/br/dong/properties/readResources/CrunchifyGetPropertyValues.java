package com.br.dong.properties.readResources;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
/**
 * Created with IntelliJ IDEA.
 * User: Dong
 * Date: 14-11-21
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class CrunchifyGetPropertyValues {
    public String getPropValues() throws IOException {

        String result = "";
        Properties prop = new Properties();
        String propFileName = "/com/br/dong/properties/readResources/resources/c.properties";

        InputStream inputStream = Object.class.getResourceAsStream(propFileName);
        prop.load(inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        Date time = new Date(System.currentTimeMillis());

        // get the property value and print it out
        String user = prop.getProperty("user");
        String company1 = prop.getProperty("company1");
        String company2 = prop.getProperty("company2");
        String company3 = prop.getProperty("company3");

        result = "Company List = " + company1 + ", " + company2 + ", " + company3;
        System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
        return result;
    }
}
