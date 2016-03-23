/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jaydee.engine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class Supplier {

    public int sid;
    public String name;
    public String phone_no;
    public String email;
    public String location;
    public double due_ksh;

    DBFunctions db;
    public Supplier(int sid,String name,String phone_no, String email, String location, int due_ksh){
        db = new DBFunctions();
        this.sid = sid;
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;
        this.location = location;
        this.due_ksh = due_ksh;       
    }

    public Supplier(int sid){
        db = new DBFunctions();
        this.sid = sid;
        loadSupplier();
    }    
    
    public final void loadSupplier(){
         String query = "SELECT * FROM supplier WHERE sid="+this.sid+";";
        ResultSet rs;
            try {
                rs = db.stmt.executeQuery(query);
        while(rs.next()){
            this.name = rs.getString("name");
            this.phone_no = rs.getString("phone_no");
            this.email = rs.getString("email");
            this.location = rs.getString("location");
            this.due_ksh = rs.getDouble("due_ksh");    
       }
          } catch (SQLException ex) {
                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        
    ArrayList<Integer> dids;

    ArrayList<Drug> drugs;
    ArrayList<Integer> drug_cost;

    //Load the supplier's stock and the prices
    public boolean selectDrugIDs(){
        dids = new ArrayList<Integer>();
        boolean found = false;
        String query = "SELECT * FROM supplier_stock WHERE sid="+this.sid+";";
        ResultSet rs;
            try {
                rs = db.stmt.executeQuery(query);
        while(rs.next()){
            dids.add(rs.getInt("did"));
            drug_cost.add(rs.getInt("cost"));
            found = true;
       }
          } catch (SQLException ex) {
                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            }

        return found;
    }
    public boolean loadSupplierStock(){
        boolean found = false;
        drugs = new ArrayList<Drug>();
        if(selectDrugIDs()){
            System.out.println("At least one drug has been found");
            found = true;
            for(int i = 0; i < dids.size() ; i++){
                drugs.add(new Drug(dids.get(i)));
            }
        }else{
            found = false;
           System.out.println("No drugs for the supplier haave been found");
        }
        return found;
    }

    

}
