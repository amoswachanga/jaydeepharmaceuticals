/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine;

/**
 *
 * @author Wachanga
 */
public class Item {
    
    int id;
    String name;
    String code;
    int qty;
    int price;
    
    public Item(int id, String name, String code, int qty, int price){
        this.id = id;
        this.name = name;
        this.code = code;
        this.qty = qty;
        this.price = price;
    }
    
}
