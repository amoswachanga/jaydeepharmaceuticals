/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jaydee.engine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import reusable.DBFunctions;

/**
 * @author Wachanga
 */
public class DrugConstants {
    //TODO Call this class to ensure that any updates are reflected in the database

    public Drug drug;
    DBFunctions db;

    public int LIMIT;//minimum amount as specified for the drug
    public int CURRENT_TOTAL;//current total for the drugs
    
    public DrugConstants(Drug drug) {
        this.drug = drug;
        db = new DBFunctions();
         //System.out.println("The drug is: " + drug.name);
         setConstants();
    }

    public final boolean setConstants(){
            boolean found = false;
            boolean found1 = false;
        try {
            String query = "SELECT `limit` FROM stock WHERE `did`="+drug.did+";";
            ResultSet rs = db.stmt.executeQuery(query);
            LIMIT = CURRENT_TOTAL = -1;
            while(rs.next()){
                LIMIT = rs.getInt("limit");
                //System.out.println("The limit for the drug "+drug.name+" is "+LIMIT);
                found = true;
            }  
            query = "SELECT SUM(i.qty)-SUM(p.qty) FROM pos p, inventory i WHERE p.did=i.did && i.did="+drug.did+" && p.did="+drug.did+";";
            rs = db.stmt.executeQuery(query);
            while(rs.next()){
                CURRENT_TOTAL = rs.getInt(1);
                //System.out.println("The current total for the drug:"+drug.name+" is "+CURRENT_TOTAL);
                found1 = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DrugConstants.class.getName()).log(Level.SEVERE, null, ex);
        }
            if(found && found1){
                found =true;
            }else{
                found = false;
            }
            return found;
    }

    public boolean isLimitReached(){
        boolean yes = false;
        if(LIMIT > CURRENT_TOTAL)
            yes = true;        
        return yes;
    }

    public int difference(){        
        return (CURRENT_TOTAL - LIMIT);
    }

    
    public String warnLowLevel(){
        String warning = "The drug "+drug.name+" has low level.\nLimit is set at: "+LIMIT+".\nThe total available in stock is: "+CURRENT_TOTAL+"."
                + "\nYou are warned!";
        
        if(isLimitReached()){
            return warning;
        }else{
            return "The drug "+drug.name+" is sufficient.";
        }
    }
    
    public void warn(){
        JOptionPane.showMessageDialog(null, warnLowLevel(),"Drug Stock Level warning message",JOptionPane.WARNING_MESSAGE);
    }

//public static void main(String [] args){
//    new DrugConstants(new Drug(1));
//}

}
