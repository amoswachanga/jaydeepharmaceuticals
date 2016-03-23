/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class Sales {
    
    int sid;
    int total;
    Calendar cur_time;
    
    ArrayList<POS_Query_Service> p;
    
    public Sales(int sid){
        this.sid = sid;
        p = new ArrayList<POS_Query_Service>();
    }
    
    public boolean getPOS_Engine(){        
    DBFunctions db = new DBFunctions();
        boolean found =false;
        
        ArrayList<Integer> pids = new ArrayList<Integer>();        
        String query = "SELECT pid FROM pos WHERE sid="+sid+";";
        
        try {
        ResultSet rs = db.stmt.executeQuery(query);
        while(rs.next()){
            pids.add(rs.getInt("pid"));
            found = true;
        }
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i = 0; i < pids.size(); i++){
            loadPOSEngines(pids.get(i));
        }
        return found;
    }
    
    public void loadPOSEngines(int pid){
        POS_Query_Service pos = new POS_Query_Service(pid);
        p.add(pos);
    }
    
    public boolean getSalesDetails(){ 
            boolean b = false;
        try {
            DBFunctions db = new DBFunctions();
            String query = "SELECT cur_time,total FROM sales WHERE sid="+sid+";";
            ResultSet rs = db.stmt.executeQuery(query);
            while(rs.next()){
                total = rs.getInt("total");
                cur_time.setTimeInMillis(rs.getTimestamp("cur_time").getTime());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;        
    }
    
    
    public static boolean insertSale(int total_amount){
        DBFunctions db = new DBFunctions();
        boolean success = false;
        try {
            String query = "INSERT INTO sales (total) VALUES ?;";            
            PreparedStatement pstmt = (PreparedStatement) db.con.prepareStatement(query);
            pstmt.setInt(1, total_amount);
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
}
