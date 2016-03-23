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
import jaydee.com.gui.SelectItem;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class POS_Engine {
    
    public Item item;
    ArrayList<Item> list;
    DBFunctions db;
    public int total_amount_received;
    public int total_sale_amount;
    public int change;
    
    
    public POS_Engine() {
        list = new ArrayList<Item>();
    }
    
    public final int checkItem(String query_name){
        String query = "SELECT * FROM drug WHERE name LIKE '"+query_name+"' OR  bar_code LIKE '"+query_name+"';";
        db = new DBFunctions();
        ResultSet rs;
        int count = -1;
        try {
            rs = db.stmt.executeQuery(query);
        while(rs.next()){
            item = new Item(rs.getInt("id"), rs.getString("name"),rs.getString("code"),rs.getInt("gty"),rs.getInt("price"));
            count++;
        }
         } catch (SQLException ex) {
            Logger.getLogger(POS_Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return count;
    }
    
    public final void addItemToList(final String item_name){
        int count = checkItem(item_name);
        final POS_Engine pos = this;
           if(count < 0){//No items were found
               java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    new SelectItem(pos).setVisible(true);
                }
                });
           }else if(count == 0){//Only one item was found
               list.add(item);
           }else if(count > 0){//Many items were found
                java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    new SelectItem(item_name, pos).setVisible(true);
                }
                });
           }else{
               System.out.println("The function addItemToList() in POS_Engine has a problem Amos");
           }
           printList();
    }
    
    
    public void printList(){
        for(int i = 0; i < list.size(); i++){
            System.out.println("Item number "+i+" is: "+list.get(i).name);
        }
    }
    
    
    public void clearLists(){
        
    }
}
