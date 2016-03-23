/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine;

/**
 *
 * @author Wachanga
 */
public class Engine_POS {
    
    public Drug drug;
    public int qty;
    public double tax = 0.0;
    public double discount = 0.0;
    
    
    public Engine_POS(Drug drug, int qty) {
        this.drug = drug;
        this.qty = qty;
    }
    
    public double computeSum(){
        return (drug.price - tax - discount);
    }
    
    
    
}
