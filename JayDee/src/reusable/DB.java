/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reusable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wachanga
 */
public class DB {

    String hostname;
    String database;
    String username;
    String password;
    String exist = "false";
    public Properties properties;
    public File f;

    public DB() {
        f = new File("lib/db.properties");
        properties = new Properties();
        loadProperties();
    }

    public final void loadProperties() {
        if (!f.exists()) {
            System.err.println("File doesn't exist."); 
            try {
                f.createNewFile();
                f.setWritable(true);
                f.setReadable(true);
                System.out.println("I have created the file f .....");  
            } catch (IOException ex) {
                System.err.println("Sorry I cannot create a new database library file :: Calling class DB.java");  
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
                loadProperties();
            }
        } else {
            try {
                FileInputStream input = new FileInputStream(f);
                properties.load(input);
                input.close();
                    getVariablesFromProperties();
                //System.out.println("After loading properties");
                //listProperties(); // display property values
            } catch (IOException ex) {
                System.err.println("Sorry I cannot create a new database library file :: Calling class DB.java");  
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Check if the properties that have been loaded actually exist
        if(exist.isEmpty()){ 
            System.out.println("Exist is empty");
            hostname = "jdbc:mysql://localhost/";
            database = "jaydee";
            username = "root";
            password = "";
            exist = "true";
            setPropertiesFromVariables();
                saveProperties(f);
                loadProperties();
        }
        
        if(!exist.equals("true") ){
            System.out.println("Exist is  NOT equal to TRUE");
            hostname = "jdbc:mysql://localhost/";
            database = "jaydee";
            username = "root";
            password = "";
            setPropertiesFromVariables();
                saveProperties(f);
                loadProperties();
        }
        
        
        if(!hostname.contains("jdbc:mysql://") ){
            System.out.println("Hostname does not contain the required characters");
            hostname = "jdbc:mysql://localhost/";
            database = "jaydee";
            username = "root";
            password = "";
            setPropertiesFromVariables();
                saveProperties(f);
                loadProperties();
        }
            
    }

    public final boolean saveProperties(File f) {
        boolean saved = false;
        // save contents of table
        try {             
            exist = "true";
            setPropertiesFromVariables();
            FileOutputStream output = new FileOutputStream(f);
            properties.store(output, "DB properties by Wachanga");// save properties
            output.close();
            System.out.println("I have saved the properties...");
            //listProperties();
            saved = true;
        } // end try
        catch (IOException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } // end catch
        return saved;
    } // end method saveProperties

    public final void setPropertiesFromVariables() {
        properties.setProperty("hostname", hostname);
        properties.setProperty("database", database);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        properties.setProperty("exist",    exist);
    }
    
    public final void getVariablesFromProperties() {
        hostname = properties.getProperty("hostname");
        database = properties.getProperty("database");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        exist = properties.getProperty("exist");
    }

    public final ArrayList<PropType> getListProperties() {
        ArrayList<PropType> s = new ArrayList<PropType>();
        Set< Object> keys = properties.keySet(); // get property names
        // output name/value pairs
        for (Object key : keys) {
            System.out.printf("%s\t%s\n", key, properties.getProperty((String) key));
            s.add(new PropType(key, properties.getProperty((String) key)));
        } // end for
        System.out.println();
        return s;
    } // end method listProperties

    public final void listProperties() {
        Set< Object> keys = properties.keySet(); // get property names
        // output name/value pairs
        for (Object key : keys) {
            System.out.printf("%s\t%s\n", key, properties.getProperty((String) key));
        } // end for
        System.out.println();
    } // end method listProperties

//    public static void main(String[] args) {
//        DB db = new DB();
//        db.listProperties();
//        
//    }
}//END CLASS

