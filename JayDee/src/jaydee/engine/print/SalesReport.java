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
public class SalesReport {
    
    DBFunctions db;
    Connection con;
    public SalesReport() {
        db = new DBFunctions();
        con = db.con;
    }
    public void print(){
            String reportSource = "reports/SalesReport.jrxml";
            String reportDestP = "reports//SalesReport.pdf";
            String reportDestH = "reports//SalesReport.html";
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
            Logger.getLogger(SalesReport.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
//    
//     public static void main(String [] args){
//        new SalesReport().print();
//    }
    
}
