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
public class Inventory {

    public int iid;
    public int sid;
    public Drug drug;
    public int batch_number;
    public Calendar ts;//Last modified timestamp
    public Calendar expiry;
    public int qty;
    
    public Supplier s;
    
    public DrugConstants d_const;

    DBFunctions db = new DBFunctions();
    
    public Inventory(int iid){
        loadInventoryDetails(iid);
        d_const = new DrugConstants(drug);
    }

    public final boolean loadInventoryDetails(int iid){
        String query = "SELECT * FROM inventory where `iid`="+iid+";";
        boolean found = false;
        try{
            ResultSet rs = db.stmt.executeQuery(query);
        while(rs.next()){
            found = true;
            this.iid = rs.getInt("iid");
            this.sid = rs.getInt("sid");
            s = new Supplier(sid);
            this.drug = new Drug(rs.getInt("did"));
            this.batch_number = rs.getInt("batch_number");
            this.ts = Calendar.getInstance();
            this.ts.setTimeInMillis(rs.getTimestamp("cur_date").getTime());
            this.expiry = Calendar.getInstance();
            this.expiry.setTime(rs.getDate("expiry_date"));
            this.qty = rs.getInt("qty");
        }
        }catch (SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }


}
