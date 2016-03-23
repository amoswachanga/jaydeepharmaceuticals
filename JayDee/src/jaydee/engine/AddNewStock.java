/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine;

import java.util.Calendar;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class AddNewStock {    
    
    public Drug drug;
    public int batch_number;
    public Calendar ts;//Last modified timestamp
    public Calendar expiry;
    public int qty;
    
    public Supplier s;
    
    public DrugConstants d_const;

    public AddNewStock(Drug drug, int batch_number, Calendar ts, Calendar expiry, int qty, Supplier s) {
        this.drug = drug;
        this.batch_number = batch_number;
        this.ts = ts;
        this.expiry = expiry;
        this.qty = qty;
        this.s = s;
        d_const = new DrugConstants(drug);
    }
    
    
    
    
}
