/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jaydee.engine;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class Drug {

    DBFunctions db = new DBFunctions();

    int did;
    public String name;
    public String desc;
    public String code;
    public double price;//Sale price
    
    int limit = -1;//Limit not set

    int cost;//Purchase cost
    
    public Drug(int did){
       this.did = did;
       setDrugDetails(did);
    }

    public final boolean setDrugDetails(int did){
            boolean found = false;
        try {
            String query = "SELECT * FROM drugs WHERE did=" + did + ";";
            ResultSet rs = db.stmt.executeQuery(query);
            while (rs.next()) {
                this.did = rs.getInt("did");
                this.name = rs.getString("name");
                this.desc = rs.getString("desc");
                this.code = rs.getString("code");
                this.price = rs.getDouble("price");
                found = true;
                //System.out.println("The drug name is: "+name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Drug.class.getName()).log(Level.SEVERE, null, ex);
        }

            return found;
    }

    public int getDid() {
        return did;
    }

    
}
