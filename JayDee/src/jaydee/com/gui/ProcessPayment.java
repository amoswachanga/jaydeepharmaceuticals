/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProcessPayment.java
 *
 * Created on Jun 23, 2011, 10:33:25 AM
 */
package jaydee.com.gui;

import com.mysql.jdbc.PreparedStatement;
import com.nitido.utils.toaster.Toaster;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jaydee.engine.DrugConstants;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class ProcessPayment extends javax.swing.JFrame {

    /** Creates new form ProcessPayment */
    DBFunctions db;
    jaydee.com.a_ui.POS pos;
    double change = 0.0;
    double total_sale;
    double amount_received = 0.0;
    public ProcessPayment(jaydee.com.a_ui.POS pos) {
        db = new DBFunctions();
        this.pos = pos;
        total_sale = total();
        initComponents();   
        setTheBounds(getWidth(), getHeight());   
        setAlwaysOnTop(true);
        lbl_TotalSale.setText(total_sale+"");
    }
    public final void initActionKeys(){
        this.addKeyListener(new KeyAdapter() {
            @Override
        public void keyPressed(java.awt.event.KeyEvent evt) {
                
        if(evt.getKeyCode() == KeyEvent.VK_F2){
            lblChange.setText(computeChange()+"");
            saveAction();
        }
            
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        chbxPrint = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_TotalSale = new javax.swing.JLabel();
        lblChange = new javax.swing.JLabel();
        txtAmountPaid = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lbl_info = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Process Payment");
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chbxPrint.setBackground(new java.awt.Color(255, 255, 255));
        chbxPrint.setSelected(true);
        chbxPrint.setText("Print Receipt");
        chbxPrint.setToolTipText("Do you wish to print a receipt?");

        jLabel1.setText("Total Sale:");

        jLabel2.setText("Cash Amount:");

        jLabel3.setText("Change:");

        lbl_TotalSale.setFont(new java.awt.Font("Tahoma", 1, 24));
        lbl_TotalSale.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_TotalSale.setText("0.00");

        lblChange.setFont(new java.awt.Font("Tahoma", 1, 23));
        lblChange.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChange.setText("0.00");

        txtAmountPaid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtAmountPaid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAmountPaid.setText("0.00");
        txtAmountPaid.setToolTipText("Enter the amount received");
        txtAmountPaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAmountPaidFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAmountPaidFocusLost(evt);
            }
        });
        txtAmountPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAmountPaidKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(276, Short.MAX_VALUE)
                .addComponent(chbxPrint)
                .addGap(51, 51, 51))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAmountPaid, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(lblChange, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(lbl_TotalSale, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                .addGap(59, 59, 59))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_TotalSale, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChange, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(chbxPrint)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Save16.png"))); // NOI18N
        btnSave.setText("Save (F2)");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/cancel (1).png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(btnSave)
                .addGap(39, 39, 39)
                .addComponent(btnCancel)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbl_info.setText("Information Label");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_info, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(lbl_info))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveAction();
    }//GEN-LAST:event_btnSaveActionPerformed

    
    public void saveAction(){
        insertIntoCash();
        boolean low_level = false;
        String text = "Success in saving";
        for(int i = 0; i < pos.pos.size(); i++){
         insertIntoPOS(i);
         
        DrugConstants d = new DrugConstants(pos.pos.get(i).drug);
        if(d.isLimitReached()){
            text+="\nLow levels of stock for :"+pos.pos.get(i).drug.name+" ";
            low_level = true;
        }
        }
        insertIntoSales();
        printReceipt();
        if(low_level)
            JOptionPane.showMessageDialog(this, text);
        Toaster toast = new Toaster();
        toast.showToaster("Success in Saving");
        pos.clearFields();
        dispose();
    }
    private void txtAmountPaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmountPaidFocusGained
        txtAmountPaid.setText("");
        lblChange.setText(computeChange()+"");
    }//GEN-LAST:event_txtAmountPaidFocusGained

    private void txtAmountPaidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountPaidKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
//            txtAmountPaid.setBackground(Color.red);
            lblChange.setText(computeChange()+"");
            
            System.out.println("Congrats Amos! YOU ARE A GENIUS!!!");
        }        
    }//GEN-LAST:event_txtAmountPaidKeyPressed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtAmountPaidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmountPaidFocusLost
        computeChange();
    }//GEN-LAST:event_txtAmountPaidFocusLost

    public void setValues(){
        total_sale = total();
        amount_received = moneyReceived();
        change = amount_received - total_sale;
    }
    
    public final double total(){
        double sum = 0;
        sum = pos.sum;
        System.out.println("total() :: The sum is: "+sum);
        return sum;
    }
    
    public double moneyReceived(){
        String amount = txtAmountPaid.getText();
        
        double money = 0.0;
        if(!amount.isEmpty()){
        try{
            money = Double.parseDouble(amount);
            System.out.println("moneyReceived() "+money);
            lbl_info.setText("Information Label");
        }catch(NumberFormatException e){
            lbl_info.setText("Please enter a number");
        }
    }
        return money;
    }
    
    public double computeChange(){
        double cash = moneyReceived();
        double total = total();
        double diff = (cash-total);
        System.out.println("ComputeChange() "+total +" "+cash+"\nDifference is: "+diff);
        setValues();
        return (cash - total);
    }
    
    public void insertIntoCash(){
        String query = "INSERT INTO cash (`amount`) VALUES (?);";
        try {
            PreparedStatement pstmt = (PreparedStatement) db.con.prepareStatement(query);
            pstmt.setDouble(1, amount_received);
            pstmt.executeUpdate();
            lbl_info.setText("Saving successful");
        } catch (SQLException ex) {
            Logger.getLogger(ProcessPayment.class.getName()).log(Level.SEVERE, null, ex);
            lbl_info.setText("Error saving to the database.");
        }
    }
    
    public void insertIntoPOS(int id){
        String query = "INSERT INTO pos (`did`,`cost_per_item`,`qty`,`discount`,`tax`,`amount`,`sid`) VALUES (?,?,?,?,?,?,?);";
         try {
            PreparedStatement pstmt = (PreparedStatement) db.con.prepareStatement(query);
            pstmt.setInt(1, pos.pos.get(id).drug.getDid());
            pstmt.setDouble(2, pos.pos.get(id).drug.price);
            pstmt.setInt(3, pos.pos.get(id).qty);
            pstmt.setDouble(4, pos.pos.get(id).discount);
            pstmt.setDouble(5, pos.pos.get(id).tax);
            pstmt.setDouble(6, pos.pos.get(id).drug.price);
            pstmt.setInt(7, getSalesID());
            pstmt.executeUpdate();
            lbl_info.setText("Saving successful");
        } catch (SQLException ex) {
            Logger.getLogger(ProcessPayment.class.getName()).log(Level.SEVERE, null, ex);
            lbl_info.setText("Error saving to the database.");
        }
    }
    
    public void insertIntoSales(){
        String query = "INSERT INTO sales (`sid`,`total`, `user_id`) VALUES (?,?,?);";
        try {
            PreparedStatement pstmt = (PreparedStatement) db.con.prepareStatement(query);
            pstmt.setInt(1, getSalesID());
            pstmt.setDouble(2, amount_received);
            pstmt.setInt(3, pos.user.id);
            pstmt.executeUpdate();
            lbl_info.setText("Saving successful");
        } catch (SQLException ex) {
            Logger.getLogger(ProcessPayment.class.getName()).log(Level.SEVERE, null, ex);
            lbl_info.setText("Error saving to the database.");
        }
    }
    
    
    public int getSalesID(){
        int sid = 0;
        String query = "SELECT sid FROM sales;";
        try{
        ResultSet rs = db.stmt.executeQuery(query);
        while(rs.next()){
            sid = rs.getInt("sid");
        }
        }catch(SQLException ex){
            Logger.getLogger(ProcessPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sid+1;
    }
    
    public void printReceipt(){
        if(chbxPrint.isSelected()){
            String reportSource = "reports/Receipt.jrxml";
            String reportDestP = "reports//Receipt.pdf";
            String reportDestH = "reports//Receipt.html";
            try{
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("served_by", pos.user.fullname);
            params.put("sum", pos.sum);
            
            System.out.println("Compiling report...");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            System.out.println("Done!");

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                    jasperReport, params, createReportDataSource());

            System.out.println("Now to print.....");
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDestH);


            JasperExportManager.exportReportToPdfFile(jasperPrint, reportDestP);
            System.out.println("Printing done, now to view in jasperviewer");

            JasperViewer  jv= new JasperViewer(jasperPrint, false);
            jv.setVisible(true);
            }catch(JRException ex) {
            Logger.getLogger(ProcessPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    private JRDataSource createReportDataSource() {
        //System.out.println("JRDataSource has been called!");
        JRMapCollectionDataSource dataSource;
        //System.out.println("JRDataSource:: :::: successfully initialized the datasource!");
        Collection reportRows = initializeReportData();
        dataSource = new JRMapCollectionDataSource(reportRows);
        return dataSource;
    }
    
    
    public List<HashMap> initializeReportData() {
        //System.out.println("Hash Map has been called!");
        List<HashMap> myMaps = new ArrayList<HashMap>();  
        
        for(int i=0; i < pos.pos.size(); i++){
            HashMap m = new HashMap();
            m.put("item_name", pos.pos.get(i).drug.name);
            m.put("item_qty", pos.pos.get(i).qty);
            m.put("item_price", pos.pos.get(i).drug.price);       
            myMaps.add(m);
        }
        return myMaps;
    }
    
    public final void setTheBounds(int a, int b) {
        int width = a;
        int height = b;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        URL url = ProcessPayment.class.getResource("/com/res/jdp24.png");
        setIconImage(Toolkit.getDefaultToolkit().createImage(url));
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new ProcessPayment(new jaydee.com.a_ui.POS(new User(1))).setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox chbxPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lbl_TotalSale;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JTextField txtAmountPaid;
    // End of variables declaration//GEN-END:variables
}
