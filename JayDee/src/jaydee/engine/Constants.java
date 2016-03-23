/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jaydee.engine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class Constants {

    DBFunctions db;

    public double VALUE_ADDED_TAX;//Is a percentage
    public double DISCOUNT;

   // Calendar last_update;

    public Constants(){
        db = new DBFunctions();
        loadConstants();
    }

    public final void loadConstants(){
        try {
            String query = "SELECT * FROM constants;";
            ResultSet rs = db.stmt.executeQuery(query);
            while (rs.next()) {
                this.VALUE_ADDED_TAX = rs.getInt("tax") * 0.01;
                this.DISCOUNT = rs.getInt("discount") * 0.01;
                //this.last_update.setTimeInMillis(rs.getTimestamp("last_updated").getTime());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
        }

    }  

}
