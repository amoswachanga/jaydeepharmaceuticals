/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine.print;

import com.mysql.jdbc.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class Supplier_Drug_List {
    
    
    DBFunctions db;
    Connection con;
    public Supplier_Drug_List() {
        db = new DBFunctions();
        con = db.con;
    }
    public void print(){
            String reportSource = "reports/SupplierPrices.jrxml";
            String reportDestP = "reports//SupplierPrices.pdf";
            String reportDestH = "reports//SupplierPrices.html";
            try{
            Map<String, Object> params = new HashMap<String, Object>();
            
            System.out.println("Compiling report...");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            System.out.println("Done!");

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                    jasperReport, params, con);

            System.out.println("Now to print.....");
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDestH);


            JasperExportManager.exportReportToPdfFile(jasperPrint, reportDestP);
            System.out.println("Printing done, now to view in jasperviewer");

            JasperViewer  jv= new JasperViewer(jasperPrint, false);
            jv.setVisible(true);
            }catch(JRException ex) {
            Logger.getLogger(Supplier_Drug_List.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
//    private JRDataSource createReportDataSource() {
//        //System.out.println("JRDataSource has been called!");
//        JRMapCollectionDataSource dataSource;
//        //System.out.println("JRDataSource:: :::: successfully initialized the datasource!");
//        Collection reportRows = initializeReportData();
//        dataSource = new JRMapCollectionDataSource(reportRows);
//        return dataSource;
//    }
//    
//    
//    public List<HashMap> initializeReportData() {
//        //System.out.println("Hash Map has been called!");
//        List<HashMap> myMaps = new ArrayList<HashMap>();  
//        
//        for(int i=0; i < pos.pos.size(); i++){
//            HashMap m = new HashMap();
//            m.put("item_name", pos.pos.get(i).drug.name);
//            m.put("item_qty", pos.pos.get(i).qty);
//            m.put("item_price", pos.pos.get(i).drug.price);       
//            myMaps.add(m);
//        }
//        return myMaps;
//    }
    
//    public static void main(String [] args){
//        new Supplier_Drug_List().print();
//    }
    
    
}
