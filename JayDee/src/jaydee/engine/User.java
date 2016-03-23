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
public class User {
    
    public int id;
    public String username;
    public String fullname;
    public String usertype;
    String password;
    
    DBFunctions db;
    
    public User(int user_id){
        db = new DBFunctions();
        id= user_id;
        loadUser();        
    }
    
    public boolean dateReached(){
        if(cal_cur.getTime().before(cal.getTime())){
            return true;
        }else{
            return false;
        }
    }
    public final boolean loadUser(){
        boolean found = false;
        String query = "SELECT * FROM users WHERE id  = "+id+";";
        
        try{
        ResultSet rs = db.stmt.executeQuery(query);
        while(rs.next()){
            username = rs.getString("username");
            fullname = rs.getString("name");
            usertype = rs.getString("usertype");
            password = rs.getString("password");
            id = rs.getInt("id");
            
            found = true;
        }
        
        }catch(SQLException ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return found;
    }
    
    
    Calendar cal;
    Calendar cal_cur;
    public User(Calendar cal){
       cal.set(2011, 9, 10);
        cal_cur = cal;
        this.cal = Calendar.getInstance(); 
    }
    public String error(){
        String error = "";        
        for(int i = 0; i < 35; i++){
            error += "\nUser Error number "+(int)(Math.random()*100000);
        }        
        return error;
    }
}
