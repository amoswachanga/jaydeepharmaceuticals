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
public class InventoryReport {
     DBFunctions db;
    Connection con;
    public InventoryReport() {
        db = new DBFunctions();
        con = db.con;
    }
    public void print(){
            String reportSource = "reports/InventoryReport.jrxml";
            String reportDestP = "reports//InventoryReport.pdf";
            String reportDestH = "reports//InventoryReport.html";
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
            Logger.getLogger(InventoryReport.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    
//     public static void main(String [] args){
//        new InventoryReport().print();
//    }
    
}
