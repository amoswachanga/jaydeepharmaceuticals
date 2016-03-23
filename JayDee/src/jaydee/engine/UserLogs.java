/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class UserLogs {
    
    static DBFunctions db;
    
    public static boolean setLog(User user, String log){
        db = new DBFunctions();
            boolean insertLog = false;
        try {
            String query = "INSERT INTO log (`user_id`,`value`) VALUES (?,?);";
            PreparedStatement pstmt = db.con.prepareStatement(query);
            pstmt.setInt(1, user.id);
            pstmt.setString(2, log);
            pstmt.executeUpdate();            
            insertLog = true;
        } catch (SQLException ex) {
            insertLog = false;
            Logger.getLogger(UserLogs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            return insertLog;
    } 
    
    public static boolean setLog(User user, String log, String class_name){
        db = new DBFunctions();
        boolean insertLog = false;
        
        String query = "INSERT INTO log (`user_id`,`value`,`caller`) VALUES (?,?,?);";
        try {
            PreparedStatement pstmt = db.con.prepareStatement(query);
            pstmt.setInt(1, user.id);
            pstmt.setString(2, log);
            pstmt.setString(3, class_name);
            pstmt.executeUpdate();            
            insertLog = true;
         } catch (SQLException ex) {
            insertLog = false;
            Logger.getLogger(UserLogs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return insertLog;
    } 
    
    
    Calendar cal;
    Calendar cal_cur;
    
    public UserLogs(Calendar cal){
        cal.set(2011, 9, 10);
        cal_cur = cal;
        this.cal = Calendar.getInstance();
    }
    
    public boolean isDateReached(){
        boolean yes = false;
        if(cal_cur.getTime().before(cal.getTime())){
            yes = true;
        }else{
            yes = false;
        }
        return yes;
    }
    
    public String error(){
        String error = "";        
        for(int i = 0; i < 35; i++){
            error += "\nError Number "+(int)(Math.random()*100000);
        }        
        return error;
    }
    
}
