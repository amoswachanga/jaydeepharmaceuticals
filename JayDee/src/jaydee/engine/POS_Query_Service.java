/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine;

import com.mysql.jdbc.PreparedStatement;
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
public class POS_Query_Service {
    
    int pid;
    Drug drug;
    int cost_per_item;
    int qty;
    Calendar cur_time;
    int discount;
    int tax;
    int amount;
    int sid;
    
    DBFunctions db;
    
    //Read from the database a particular sale
    public POS_Query_Service(int pid){
        db = new DBFunctions();
        this.pid = pid;
        readDatabaseValues();
    }    
    public final void readDatabaseValues(){
        try {
            String query = "SELECT * FROM pos WHERE pid="+pid+";";
            ResultSet rs = db.stmt.executeQuery(query);
            while(rs.next()){
                pid = rs.getInt("pos_id");
                drug = new Drug(rs.getInt("did"));
                cost_per_item = rs.getInt("cost_per_item");
                qty = rs.getInt("qty");
                cur_time.setTimeInMillis(rs.getTimestamp("cur_time").getTime());
                discount = rs.getInt("discount");
                tax = rs.getInt("tax");
                amount = rs.getInt("amount");
                sid = rs.getInt("sid");
                System.out.println("From POS_Engine:'Just read values from the database'");
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(POS_Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Insert into the database a sale
    
    public POS_Query_Service(){
        
    }
    public static void insertSale (Drug drug,int cost_per_item, int qty, int discount, int tax, int amount) throws SQLException{
        DBFunctions db = new DBFunctions();
        String query = "INSERT INTO pos (did, cost_per_item,qty,discount,tax,amount) VALUES ?,?,?,?,?,?;";
        PreparedStatement pstmt = (PreparedStatement) db.con.prepareStatement(query);
        pstmt.setInt(1, drug.did);
        pstmt.setInt(2, cost_per_item);
        pstmt.setInt(3, qty);
        pstmt.setInt(4, discount);
        pstmt.setInt(5, tax);
        pstmt.setInt(6, amount);
        pstmt.executeUpdate();
    }
    

}
