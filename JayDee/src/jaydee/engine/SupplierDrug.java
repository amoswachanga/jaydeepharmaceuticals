/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine;

/**
 *
 * @author Wachanga
 */
public class SupplierDrug {
    public int sid;
    public int did;
    public double cost;
    
    
    public Drug drug;
    
    public SupplierDrug(int sid,int did,double cost){
        this.cost = cost;
        this.did = did;
        this.sid = sid;
        
        drug = new Drug(did);
    }
    
}
