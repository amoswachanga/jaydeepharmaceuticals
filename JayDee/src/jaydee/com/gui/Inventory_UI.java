/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Inventory_UI.java
 *
 * Created on May 24, 2011, 10:35:27 PM
 */

package jaydee.com.gui;

import com.mysql.jdbc.PreparedStatement;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import jaydee.engine.AddNewStock;
import jaydee.engine.Drug;
import jaydee.engine.DrugConstants;
import jaydee.engine.Inventory;
import jaydee.engine.QueryTableModel;
import jaydee.engine.Supplier;
import jaydee.engine.SupplierDrug;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class Inventory_UI extends javax.swing.JInternalFrame {

    /** Creates new form Inventory_UI */

    DBFunctions db;
    
    //STOCK LEVELS VIEWER
    Vector<DrugConstants> druglist_stock;
    QueryTableModel qtm;
    
    //SET DRUG LIMT
    int index_setlimit;    
    
    //ADD NEW STOCK
    Vector<Supplier> supplierlist;
    Vector<SupplierDrug> druglist_Supplier;
 
    ArrayList<AddNewStock> to_insert;
    
    int index_suppliers;
    int index_drugs;
    
    int index_batch_list = 0;
    
    boolean end_of_init = false;
    
    //PURCHASE RETURN
    ArrayList<Supplier> suppliers;
    ArrayList<Drug> drugs;
    
    ArrayList<Inventory> inventory;
    
    public Inventory_UI() {    
        end_of_init = false;
        db = new DBFunctions();
        initComponents();
        initTableStockListView();
        initSetDrugLimit();
        initAddNewStockForm();
        initPurchaseReturn();
        end_of_init = true;
    }
    //PURCHASE RETURN
    public final void initPurchaseReturn(){
        suppliers = new ArrayList<Supplier>();
        drugs = new ArrayList<Drug>();
        inventory = new ArrayList<Inventory>();
        
        initSuppliersPurchaseReturn();
        initDrugsPurchaseReturn();
         
        jListPurchaseReturn.removeAll();
        jListPurchaseReturn.setListData(inventory.toArray());
        
    }
    
    public void initDrugsPurchaseReturn(){
        boolean found = false;
        jcboxSelectDrug_PurchaseReturn.removeAllItems();
        String query = "SELECT did FROM drugs ORDER BY name;";
        try {
            ResultSet rs = db.con.createStatement().executeQuery(query);
            for(int i = 0; i < drugs.size(); i++){
                drugs.remove(i);
            }
            while (rs.next()) {
                drugs.add(new Drug(rs.getInt("did")));
                found = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (found) {
        for (int i = 0; i < drugs.size(); i++) {
            jcboxSelectDrug_PurchaseReturn.addItem(drugs.get(i).name);
        }
        jcboxSelectDrug_PurchaseReturn.setSelectedIndex(0);
        } else {
            lbl_info.setText("The database has no suppliers");
        }     
    }
    
    public void initSuppliersPurchaseReturn(){
        boolean found = false;
        jcboxSupplier_PurchaseReturn.removeAllItems();
        String query = "SELECT sid FROM supplier ORDER BY name;";
        try {
            ResultSet rs = db.con.createStatement().executeQuery(query);
            for(int i = 0; i < suppliers.size(); i++){
                suppliers.remove(i);
            }
            while (rs.next()) {
                suppliers.add(new Supplier(rs.getInt("sid")));
                found = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (found) {
        for (int i = 0; i < suppliers.size(); i++) {
            jcboxSupplier_PurchaseReturn.addItem(suppliers.get(i).name);
        }
        jcboxSupplier_PurchaseReturn.setSelectedIndex(0);
        } else {
            lbl_info.setText("The database has no suppliers");
        }     
    }
    
    public boolean searchResults(String query){
        boolean found = false;
        for(int i = 0; i < inventory.size(); i++){
            inventory.remove(i);
       }
        try {
            ResultSet rs = db.stmt.executeQuery(query);
            while(rs.next()){
                inventory.add(new Inventory(rs.getInt("iid")));
                found = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (found) {
            String [] batch = new String[inventory.size()];
            for(int i = 0; i < inventory.size(); i++){
                batch[i] = inventory.get(i).batch_number + " ";
            }
            jListPurchaseReturn.removeAll();
            jListPurchaseReturn.setListData(batch);
            jListPurchaseReturn.setSelectedIndex(0);
        } else {
            lbl_info.setText("The database has no values in inventory");
        } 
        
        return found;
    }
    
    
    
    
    
    
    //ADD NEW STOCK
    public final void initAddNewStockForm(){ 
        Calendar cal = Calendar.getInstance();
        jDateChooserCurDate.setDate(cal.getTime());
        jDateChooserExpiry.setDate(cal.getTime());
        jDateChooserExpiry.setMinSelectableDate(cal.getTime());
        jListBatchList.removeAll();
        String [] empty = new String[2];
        jListBatchList.setListData(empty);
        initSuppliersANDDrugs();
        txtBatchNumber_Stock.setText(loadBatchNumber()+"");
        to_insert = new ArrayList<AddNewStock>();
    }    
    public void initSuppliersANDDrugs(){
        index_suppliers = 0;
        index_drugs = 0;
        supplierlist = new Vector<Supplier>();
        //Remove all items
        jcbox_Suppliers.removeAllItems();
        //Init the suppliers
        boolean found = false;
        String query = "SELECT sid FROM supplier ORDER BY name;";
        try {
            ResultSet rs = db.con.createStatement().executeQuery(query);
            supplierlist.removeAllElements();
            while (rs.next()) {
                supplierlist.add(new Supplier(rs.getInt("sid")));
                found = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (found) {
        for (int i = 0; i < supplierlist.size(); i++) {
            jcbox_Suppliers.addItem(supplierlist.get(i).name);
        }
        jcbox_Suppliers.setSelectedIndex(index_suppliers);
        } else {
            lbl_info.setText("The database has no suppliers");
        }        
       initSupplierDrug(supplierlist.get(jcbox_Suppliers.getSelectedIndex()).sid);         
    }
    public void initSupplierDrug(int sup){
        jcbox_Drugs.removeAllItems();
        druglist_Supplier = new Vector<SupplierDrug>();
        //Now to init the drugs
        boolean found = false;
        String query = "SELECT s.sid,s.did,s.cost,s.ssid FROM supplier_stock s,drugs d WHERE sid=" + sup + " AND d.did=s.did ORDER BY d.name;";
        try {
            ResultSet rs = db.con.createStatement().executeQuery(query);
            druglist_Supplier.removeAllElements();
            while (rs.next()) {
                druglist_Supplier.add(new SupplierDrug(rs.getInt("sid"), rs.getInt("did"), rs.getDouble("cost")));
                found = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DrugEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (found) {for (int i = 0; i < druglist_Supplier.size(); i++) {
            jcbox_Drugs.addItem(druglist_Supplier.get(i).drug.name);
        }
        jcbox_Drugs.setSelectedIndex(index_drugs);
        } else {
            lbl_info.setText("The supplier has no drugs set in the database");
        }
    }    
    public final int loadBatchNumber(){
        int batch_number = -1;
        String query = "SELECT MAX(`batch_number`) FROM inventory;";
        try{
            ResultSet rs = db.stmt.executeQuery(query);
        while(rs.next()){
            batch_number = rs.getInt(1);
        }
        }catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (batch_number+1);
    }
    
  
    //SET DRUG LIMT
    public final void initSetDrugLimit(){
        index_setlimit = 0;
        druglist_stock = new Vector<DrugConstants>(); 
        boolean found = initDrugsList();
        Vector<String> string = new Vector<String>();
        for (int i = 0; i < druglist_stock.size(); i++) {
            string.add(druglist_stock.get(i).drug.name);
        }
        if (found) {
            jListDrugsListSetLimit.removeAll();
            jListDrugsListSetLimit.setListData(string);
            jListDrugsListSetLimit.setSelectedIndex(index_setlimit);
        } else {
            String[] just_some_data = {"nothing", "was", "found"};
            jListDrugsListSetLimit.setListData(just_some_data);
        }
    }
    
    
    //STOCK LEVELS VIEWER
    public final void initTableStockListView(){
       String query = "SELECT d.name,d.code,s.limit,SUM(i.qty)-SUM(p.qty) AS qty FROM pos p, stock s, inventory i, drugs d WHERE d.did = s.did AND p.did=i.did AND i.did=d.did AND p.did=d.did ORDER BY d.name;";
       qtm = new QueryTableModel(query) ;  
       jTable1.setModel(qtm);
    }
    public final boolean initDrugsList(){
        boolean found = false;
            String query = "SELECT did FROM drugs ORDER BY name;";
        try {
            ResultSet rs = db.con.createStatement().executeQuery(query);
            //druglist.removeAllElements();
            druglist_stock.removeAllElements();
            while(rs.next()){
                found = true;
                Drug d = new Drug(rs.getInt("did"));
                druglist_stock.add(new DrugConstants(d));
            }            
        } catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane8 = new javax.swing.JScrollPane();
        jPanel19 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        btnRefreshDrugsList_StockLevels = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDrugName_setlimit = new javax.swing.JTextField();
        txtCurrentLimit_setlimit = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        txtNewLimit_setLimit = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnSave_setlimit = new javax.swing.JButton();
        btnCancel_setlimit = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListDrugsListSetLimit = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnSave_addnewstock = new javax.swing.JButton();
        btnCancel_addnewstock = new javax.swing.JButton();
        btnRemoveItem_addstock = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jcbox_Drugs = new javax.swing.JComboBox();
        jcbox_Suppliers = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnAddToBatch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListBatchList = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooserExpiry = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtQty_AddNewStock = new javax.swing.JTextField();
        jDateChooserCurDate = new com.toedter.calendar.JDateChooser();
        btnSaveAddNewStock = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtStockLeveAddNewStock = new javax.swing.JTextField();
        txtDrugNameAddNewStock = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSupplierAddNewStock = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtBatchNumber_Stock = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jListPurchaseReturn = new javax.swing.JList();
        jPanel15 = new javax.swing.JPanel();
        jcboxSelectDrug_PurchaseReturn = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jcboxSupplier_PurchaseReturn = new javax.swing.JComboBox();
        btnSearch_Drug = new javax.swing.JButton();
        btnSearch_Supplier = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtQuantityRemainingFromBatch_PR = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTotalQtyInBatch_PR = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        btnSavePurchaseReturn = new javax.swing.JButton();
        btnCancelPurchaseReturn = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jDateChooserPurchaseDate = new com.toedter.calendar.JDateChooser();
        jDateChooserEXP_Date = new com.toedter.calendar.JDateChooser();
        txtDrugName_PR = new javax.swing.JTextField();
        txtSupplier_PR = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lbl_info = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Inventory");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/jdp16.png"))); // NOI18N

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Drug Name", "Drug Code", "Qty", "Limit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Click to refresh the data"));

        btnRefreshDrugsList_StockLevels.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Refresh24.png"))); // NOI18N
        btnRefreshDrugsList_StockLevels.setText("Refresh");
        btnRefreshDrugsList_StockLevels.setToolTipText("Refresh theDrug's list");
        btnRefreshDrugsList_StockLevels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshDrugsList_StockLevelsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(btnRefreshDrugsList_StockLevels)
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(btnRefreshDrugsList_StockLevels)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(552, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Stock Levels", jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Drug Details"));

        jLabel11.setText("Current Limit:");

        jLabel10.setText("Drug name:");

        txtDrugName_setlimit.setEditable(false);

        txtCurrentLimit_setlimit.setEditable(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(29, 29, 29)
                        .addComponent(txtDrugName_setlimit, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtCurrentLimit_setlimit)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDrugName_setlimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtCurrentLimit_setlimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));

        jLabel12.setText("New Limit:");

        btnSave_setlimit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Save24.png"))); // NOI18N
        btnSave_setlimit.setText("Save");
        btnSave_setlimit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave_setlimitActionPerformed(evt);
            }
        });

        btnCancel_setlimit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/cancel (2).png"))); // NOI18N
        btnCancel_setlimit.setText("Cancel");
        btnCancel_setlimit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel_setlimitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(34, 34, 34)
                        .addComponent(txtNewLimit_setLimit, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnSave_setlimit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btnCancel_setlimit)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNewLimit_setLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel_setlimit)
                    .addComponent(btnSave_setlimit))
                .addContainerGap())
        );

        jListDrugsListSetLimit.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListDrugsListSetLimit.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListDrugsListSetLimitValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListDrugsListSetLimit);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jScrollPane5.setViewportView(jPanel13);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Set Drug Limit", jPanel4);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));

        btnSave_addnewstock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Save24.png"))); // NOI18N
        btnSave_addnewstock.setText("Save");
        btnSave_addnewstock.setToolTipText("Save new batch to database");
        btnSave_addnewstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave_addnewstockActionPerformed(evt);
            }
        });

        btnCancel_addnewstock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/cancel (2).png"))); // NOI18N
        btnCancel_addnewstock.setText("Cancel");
        btnCancel_addnewstock.setToolTipText("Resets the whole form");
        btnCancel_addnewstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel_addnewstockActionPerformed(evt);
            }
        });

        btnRemoveItem_addstock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Undo16.png"))); // NOI18N
        btnRemoveItem_addstock.setText("Remove Item");
        btnRemoveItem_addstock.setEnabled(false);
        btnRemoveItem_addstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveItem_addstockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemoveItem_addstock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel_addnewstock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave_addnewstock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(btnSave_addnewstock)
                .addGap(30, 30, 30)
                .addComponent(btnCancel_addnewstock)
                .addGap(28, 28, 28)
                .addComponent(btnRemoveItem_addstock, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Select the Supplier and drug"));

        jcbox_Drugs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jcbox_Suppliers.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbox_Suppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbox_SuppliersActionPerformed(evt);
            }
        });

        jLabel2.setText("Select Supplier:");

        jLabel1.setText("Select Drug:");

        btnAddToBatch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Add16.png"))); // NOI18N
        btnAddToBatch.setText("Add");
        btnAddToBatch.setToolTipText("Add drug to batch");
        btnAddToBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToBatchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbox_Suppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcbox_Drugs, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnAddToBatch)
                .addGap(36, 36, 36))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbox_Suppliers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbox_Drugs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnAddToBatch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jListBatchList.setBorder(javax.swing.BorderFactory.createTitledBorder("Batch List"));
        jListBatchList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListBatchList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListBatchList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListBatchListValueChanged(evt);
            }
        });
        jListBatchList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jListBatchListFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jListBatchList);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Stock Entry Details"));

        jLabel7.setText("Entry Date:");

        jLabel9.setText("Quantity:");

        jLabel8.setText("Expiry Date:");

        txtQty_AddNewStock.setToolTipText("Enter the quantity");
        txtQty_AddNewStock.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQty_AddNewStockFocusLost(evt);
            }
        });
        txtQty_AddNewStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQty_AddNewStockKeyPressed(evt);
            }
        });

        jDateChooserCurDate.setToolTipText("Enter today's date, or date the batch came in");

        btnSaveAddNewStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Save16.png"))); // NOI18N
        btnSaveAddNewStock.setText("Save");
        btnSaveAddNewStock.setToolTipText("Click to save current stock entry details");
        btnSaveAddNewStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAddNewStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtQty_AddNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveAddNewStock))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jDateChooserCurDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooserExpiry, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel8))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jDateChooserCurDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooserExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtQty_AddNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveAddNewStock))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Stock Details"));

        jLabel5.setText("Supplier:");

        txtStockLeveAddNewStock.setEditable(false);
        txtStockLeveAddNewStock.setToolTipText("");

        txtDrugNameAddNewStock.setEditable(false);

        jLabel4.setText("Current Stock Level:");

        jLabel3.setText("Drug Name:");

        txtSupplierAddNewStock.setEditable(false);

        jLabel6.setText("Batch Number:");

        txtBatchNumber_Stock.setEditable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(268, 268, 268))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(27, 27, 27)
                                .addComponent(txtDrugNameAddNewStock, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtStockLeveAddNewStock, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                    .addComponent(txtSupplierAddNewStock, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)))
                            .addComponent(txtBatchNumber_Stock, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDrugNameAddNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtStockLeveAddNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSupplierAddNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBatchNumber_Stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane4.setViewportView(jPanel12);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Add New Stock", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jListPurchaseReturn.setBorder(javax.swing.BorderFactory.createTitledBorder("List of batch numbers"));
        jListPurchaseReturn.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListPurchaseReturn.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListPurchaseReturn.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListPurchaseReturnValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(jListPurchaseReturn);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Drug"));

        jcboxSelectDrug_PurchaseReturn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Supplier:");

        jLabel13.setText("Select Drug:");

        jcboxSupplier_PurchaseReturn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSearch_Drug.setText("Search");
        btnSearch_Drug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch_DrugActionPerformed(evt);
            }
        });

        btnSearch_Supplier.setText("Search");
        btnSearch_Supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch_SupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcboxSupplier_PurchaseReturn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcboxSelectDrug_PurchaseReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch_Supplier)
                    .addComponent(btnSearch_Drug))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcboxSelectDrug_PurchaseReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch_Drug))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcboxSupplier_PurchaseReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch_Supplier)
                            .addComponent(jLabel15))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Drug Details"));

        jLabel16.setText("Quantity remaining from batch:");

        txtQuantityRemainingFromBatch_PR.setEditable(false);

        jLabel14.setText("Total Quantity of Drug:");

        txtTotalQtyInBatch_PR.setEditable(false);
        txtTotalQtyInBatch_PR.setToolTipText("Total Quantity of drugs");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtQuantityRemainingFromBatch_PR, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(39, 39, 39)
                        .addComponent(txtTotalQtyInBatch_PR, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtQuantityRemainingFromBatch_PR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txtTotalQtyInBatch_PR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Return Quantity"));

        jLabel19.setText("Quantity to return:");

        btnSavePurchaseReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Save24.png"))); // NOI18N
        btnSavePurchaseReturn.setText("Save");
        btnSavePurchaseReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePurchaseReturnActionPerformed(evt);
            }
        });

        btnCancelPurchaseReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/cancel (2).png"))); // NOI18N
        btnCancelPurchaseReturn.setText("Cancel");
        btnCancelPurchaseReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelPurchaseReturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(btnSavePurchaseReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelPurchaseReturn)
                .addGap(152, 152, 152))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSavePurchaseReturn)
                        .addComponent(btnCancelPurchaseReturn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setText("Drug Name:");

        jLabel21.setText("Supplier:");

        jLabel22.setText("Expiry Date:");

        jLabel23.setText("Date of purchase:");

        jDateChooserPurchaseDate.setEnabled(false);

        jDateChooserEXP_Date.setEnabled(false);

        txtDrugName_PR.setEnabled(false);

        txtSupplier_PR.setEnabled(false);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDrugName_PR)
                    .addComponent(jDateChooserEXP_Date, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooserPurchaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSupplier_PR)))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(txtDrugName_PR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSupplier_PR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooserPurchaseDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooserEXP_Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(jLabel23)))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel17.setText("Reason for return:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane9.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel17)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6))
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane7.setViewportView(jPanel17);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 820, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Purchase Returns", jPanel3);

        lbl_info.setText("Information Label");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(lbl_info, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_info))
        );

        jScrollPane8.setViewportView(jPanel19);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshDrugsList_StockLevelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshDrugsList_StockLevelsActionPerformed
        initTableStockListView();
    }//GEN-LAST:event_btnRefreshDrugsList_StockLevelsActionPerformed

    private void jListDrugsListSetLimitValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListDrugsListSetLimitValueChanged
        //Set drug details in the values.
        index_setlimit = jListDrugsListSetLimit.getSelectedIndex();
        if(index_setlimit > -1){
            txtCurrentLimit_setlimit.setText(druglist_stock.get(index_setlimit).LIMIT+"");
            txtDrugName_setlimit.setText(druglist_stock.get(index_setlimit).drug.name);
        }
    }//GEN-LAST:event_jListDrugsListSetLimitValueChanged

    private void btnSave_setlimitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave_setlimitActionPerformed
        boolean ok_to_save = false;
        int limit = 0;
        int cur_limit = Integer.parseInt(txtCurrentLimit_setlimit.getText());
        
        try{
            limit = Integer.parseInt(txtNewLimit_setLimit.getText());
            lbl_info.setText("Information Label");
            ok_to_save = true;
        }catch(NumberFormatException e){
            ok_to_save = false;
            lbl_info.setText("Please enter correct text for the new limit, such as 0. Should be an integer.");
        }
        if(limit < 0 || txtNewLimit_setLimit.getText().isEmpty()){
            ok_to_save = false;            
           lbl_info.setText("Please enter correct text for the new limit, such as 5. Should be an integer."); 
        }
        boolean inserted = false;
        if(ok_to_save){
        String query;
        if(cur_limit >= 0){
            query = " UPDATE stock SET `limit`=? WHERE `did` = ?;";             
        }
        else{
            query = " INSERT INTO stock  (`limit`,`did`) VALUES (?,?) ;";            
        }
         try {
            PreparedStatement pstmt = (PreparedStatement) db.con.prepareStatement(query);
            pstmt.setInt(1, limit);
            pstmt.setInt(2, druglist_stock.get(jListDrugsListSetLimit.getSelectedIndex()).drug.getDid());
            pstmt.executeUpdate();
            inserted = true;
            lbl_info.setText("Saving successful");
            initSetDrugLimit();
            txtNewLimit_setLimit.setText("");
            jListDrugsListSetLimit.setSelectedIndex(index_setlimit);
        } catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
            lbl_info.setText("Error saving to the database.");
        }
        }
    }//GEN-LAST:event_btnSave_setlimitActionPerformed

    private void btnCancel_setlimitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel_setlimitActionPerformed
        initSetDrugLimit();
        txtNewLimit_setLimit.setText("");
    }//GEN-LAST:event_btnCancel_setlimitActionPerformed

    private void btnCancel_addnewstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel_addnewstockActionPerformed
        URL url = Inventory_UI.class.getResource("/com/res/jdp24.png");
        Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(url));
        if(JOptionPane.showConfirmDialog(this, "Are you sure you wish to reset the form\nYou will lose any unsaved data","Cancel All Changes", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,icon) == JOptionPane.YES_OPTION){
            initAddNewStockForm();
        }
    }//GEN-LAST:event_btnCancel_addnewstockActionPerformed
    
    private void btnAddToBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToBatchActionPerformed
        int qty = 0;
        if(!txtQty_AddNewStock.getText().isEmpty()){
        try{
            qty = Integer.parseInt(txtQty_AddNewStock.getText());
            lbl_info.setText("Information Label");
        }catch(NumberFormatException e){
            e.printStackTrace();
            lbl_info.setText("Please enter a number for the quantity");
        }     
        }
        to_insert.add(new AddNewStock(druglist_Supplier.get(jcbox_Drugs.getSelectedIndex()).drug, Integer.parseInt(txtBatchNumber_Stock.getText()),jDateChooserCurDate.getCalendar(),jDateChooserExpiry.getCalendar(),qty, supplierlist.get(jcbox_Suppliers.getSelectedIndex())));
        jListBatchList.removeAll();
        Vector<String> stringDrug = new Vector<String>();
        for (int i = 0; i < to_insert.size(); i++) {
            stringDrug.add(to_insert.get(i).drug.name);
        }
        jListBatchList.setListData(stringDrug);
    }//GEN-LAST:event_btnAddToBatchActionPerformed

    private void jcbox_SuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbox_SuppliersActionPerformed
        int index = jcbox_Suppliers.getSelectedIndex();
        if(end_of_init && index >= 0)
            initSupplierDrug(supplierlist.get(jcbox_Suppliers.getSelectedIndex()).sid);  
    }//GEN-LAST:event_jcbox_SuppliersActionPerformed

    private void btnRemoveItem_addstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveItem_addstockActionPerformed
        if(to_insert.size() > 0){
            to_insert.remove(jListBatchList.getSelectedIndex());
            jListBatchList.removeAll();
            jListBatchList.setListData(to_insert.toArray());
        }
        btnRemoveItem_addstock.setEnabled(false);
    }//GEN-LAST:event_btnRemoveItem_addstockActionPerformed

    private void jListBatchListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jListBatchListFocusGained
        btnRemoveItem_addstock.setEnabled(true);
    }//GEN-LAST:event_jListBatchListFocusGained

    private void jListBatchListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListBatchListValueChanged
        index_batch_list = jListBatchList.getSelectedIndex();
        if (index_batch_list > -1) {
            txtDrugNameAddNewStock.setText(to_insert.get(index_batch_list).drug.name);
            txtBatchNumber_Stock.setText(to_insert.get(index_batch_list).batch_number+"");
            int difference = to_insert.get(index_batch_list).d_const.difference();
            txtStockLeveAddNewStock.setText(difference+"");
            txtSupplierAddNewStock.setText(to_insert.get(index_batch_list).s.name);
            txtQty_AddNewStock.setText(to_insert.get(index_batch_list).qty+"");
            jDateChooserCurDate.setCalendar(to_insert.get(index_batch_list).ts);
            jDateChooserExpiry.setCalendar(to_insert.get(index_batch_list).expiry);
        }
    }//GEN-LAST:event_jListBatchListValueChanged

    private void btnSave_addnewstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave_addnewstockActionPerformed
        boolean success = false;
        for(int i = 0; i < to_insert.size(); i ++){
            if(!insertIntoDB(i)){
                success = false;
                break;
            }else{
                success = true;
            }
        }
        if(success){
            lbl_info.setText("All new stock has been saved successfully.");
        }else{
            lbl_info.setText("Some changes were lost and there was an error saving them to the database");
        }
    }//GEN-LAST:event_btnSave_addnewstockActionPerformed

    public boolean insertIntoDB(int i){
        boolean success = false;
        String query = "INSERT INTO inventory (`did`,`sid`,`batch_number`,`cur_date`,`expiry_date`,`qty`) VALUES (?,?,?,?,?,?);";
        try {
        PreparedStatement pstmt = (PreparedStatement) db.con.prepareStatement(query);
            pstmt.setInt(1, to_insert.get(i).drug.getDid());
            pstmt.setInt(2, to_insert.get(i).s.sid);
            Timestamp ts = new Timestamp(to_insert.get(i).ts.getTimeInMillis());
            pstmt.setTimestamp(4, ts);
            pstmt.setInt(3, to_insert.get(i).batch_number);
            java.sql.Date d1 = new java.sql.Date(to_insert.get(i).expiry.getTimeInMillis());
            pstmt.setDate(5, d1);
            pstmt.setInt(6, to_insert.get(i).qty);
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            success = false;
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return success;
    }
    private void txtQty_AddNewStockFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQty_AddNewStockFocusLost
        int qty = 0;
        if(!txtQty_AddNewStock.getText().isEmpty()){
        try{
            qty = Integer.parseInt(txtQty_AddNewStock.getText());
            lbl_info.setText("Information Label");
        }catch(NumberFormatException e){
            lbl_info.setText("Please enter a number for the quantity");
        }     
        }to_insert.get(jListBatchList.getSelectedIndex()).qty = qty;
    }//GEN-LAST:event_txtQty_AddNewStockFocusLost

    private void txtQty_AddNewStockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQty_AddNewStockKeyPressed
        int index = jcbox_Suppliers.getSelectedIndex();
        int qty = 0;
        if(!txtQty_AddNewStock.getText().isEmpty()){
        try{
            qty = Integer.parseInt(txtQty_AddNewStock.getText());
            lbl_info.setText("Information Label");
        }catch(NumberFormatException e){
            lbl_info.setText("Please enter a number for the quantity");
        }     
        }
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && index > -1 && qty > 0){
            to_insert.get(jListBatchList.getSelectedIndex()).qty = qty;
            to_insert.get(jListBatchList.getSelectedIndex()).expiry = jDateChooserExpiry.getCalendar();
            to_insert.get(jListBatchList.getSelectedIndex()).ts = jDateChooserCurDate.getCalendar();
        }
    }//GEN-LAST:event_txtQty_AddNewStockKeyPressed

    private void btnSaveAddNewStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAddNewStockActionPerformed
        int index = jcbox_Suppliers.getSelectedIndex();
        int qty = 0;
        if(!txtQty_AddNewStock.getText().isEmpty()){
        try{
            qty = Integer.parseInt(txtQty_AddNewStock.getText());
            lbl_info.setText("Information Label");
        }catch(NumberFormatException e){
            lbl_info.setText("Please enter a number for the quantity");
        }     
        }    
        to_insert.get(jListBatchList.getSelectedIndex()).qty = qty;
            to_insert.get(jListBatchList.getSelectedIndex()).expiry = jDateChooserExpiry.getCalendar();
            to_insert.get(jListBatchList.getSelectedIndex()).ts = jDateChooserCurDate.getCalendar();
    }//GEN-LAST:event_btnSaveAddNewStockActionPerformed

    private void btnSearch_DrugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch_DrugActionPerformed
        String query = "SELECT iid FROM inventory WHERE `did`="+drugs.get(jcboxSelectDrug_PurchaseReturn.getSelectedIndex()).getDid()+";";
        searchResults(query);
    }//GEN-LAST:event_btnSearch_DrugActionPerformed

    private void btnSearch_SupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch_SupplierActionPerformed
        String query = "SELECT iid FROM inventory WHERE `sid`="+suppliers.get(jcboxSupplier_PurchaseReturn.getSelectedIndex()).sid+";";
        searchResults(query);
    }//GEN-LAST:event_btnSearch_SupplierActionPerformed

    private void jListPurchaseReturnValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListPurchaseReturnValueChanged
        int index = jListPurchaseReturn.getSelectedIndex();
        if(index > -1){
            String query = "SELECT SUM(`qty`) FROM inventory WHERE `batch_number`="+inventory.get(index).batch_number+";";//Total quantity from batch
            try {
            ResultSet rs = db.stmt.executeQuery(query);
            while(rs.next()){
               txtTotalQtyInBatch_PR.setText(rs.getInt(1)+"");                 
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        }  
            
            query = "SELECT SUM(i.`qty` - p.`qty`) FROM inventory i, pos p WHERE i.`did`=p.`did` AND i.`batch_number`="+inventory.get(index).batch_number+";";//Qty remaining from batch
            try {
            ResultSet rs = db.stmt.executeQuery(query);
            while(rs.next()){
                txtQuantityRemainingFromBatch_PR.setText(rs.getInt(1)+"");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inventory_UI.class.getName()).log(Level.SEVERE, null, ex);
        }  
            
            txtDrugName_PR.setText(inventory.get(index).drug.name);
            txtSupplier_PR.setText(inventory.get(index).s.name);
            
            jDateChooserEXP_Date.setDate(inventory.get(index).expiry.getTime());    
            jDateChooserPurchaseDate.setDate(inventory.get(index).ts.getTime());  
        }
    }//GEN-LAST:event_jListPurchaseReturnValueChanged

    private void btnCancelPurchaseReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelPurchaseReturnActionPerformed
        URL url = Inventory_UI.class.getResource("/com/res/jdp24.png");
        Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(url));
        if(JOptionPane.showConfirmDialog(this, "Are you sure you wish to reset the form\nYou will lose any unsaved data","Cancel All Changes", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,icon) == JOptionPane.YES_OPTION){
        
            initPurchaseReturn();
        
        }
    }//GEN-LAST:event_btnCancelPurchaseReturnActionPerformed

    private void btnSavePurchaseReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePurchaseReturnActionPerformed
        int index = jListPurchaseReturn.getSelectedIndex();
        
        inventory.get(index);
        lbl_info.setText("Success in saving the purchase return values");
    }//GEN-LAST:event_btnSavePurchaseReturnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToBatch;
    private javax.swing.JButton btnCancelPurchaseReturn;
    private javax.swing.JButton btnCancel_addnewstock;
    private javax.swing.JButton btnCancel_setlimit;
    private javax.swing.JButton btnRefreshDrugsList_StockLevels;
    private javax.swing.JButton btnRemoveItem_addstock;
    private javax.swing.JButton btnSaveAddNewStock;
    private javax.swing.JButton btnSavePurchaseReturn;
    private javax.swing.JButton btnSave_addnewstock;
    private javax.swing.JButton btnSave_setlimit;
    private javax.swing.JButton btnSearch_Drug;
    private javax.swing.JButton btnSearch_Supplier;
    private com.toedter.calendar.JDateChooser jDateChooserCurDate;
    private com.toedter.calendar.JDateChooser jDateChooserEXP_Date;
    private com.toedter.calendar.JDateChooser jDateChooserExpiry;
    private com.toedter.calendar.JDateChooser jDateChooserPurchaseDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jListBatchList;
    private javax.swing.JList jListDrugsListSetLimit;
    private javax.swing.JList jListPurchaseReturn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JComboBox jcboxSelectDrug_PurchaseReturn;
    private javax.swing.JComboBox jcboxSupplier_PurchaseReturn;
    private javax.swing.JComboBox jcbox_Drugs;
    private javax.swing.JComboBox jcbox_Suppliers;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JTextField txtBatchNumber_Stock;
    private javax.swing.JTextField txtCurrentLimit_setlimit;
    private javax.swing.JTextField txtDrugNameAddNewStock;
    private javax.swing.JTextField txtDrugName_PR;
    private javax.swing.JTextField txtDrugName_setlimit;
    private javax.swing.JTextField txtNewLimit_setLimit;
    private javax.swing.JTextField txtQty_AddNewStock;
    private javax.swing.JTextField txtQuantityRemainingFromBatch_PR;
    private javax.swing.JTextField txtStockLeveAddNewStock;
    private javax.swing.JTextField txtSupplierAddNewStock;
    private javax.swing.JTextField txtSupplier_PR;
    private javax.swing.JTextField txtTotalQtyInBatch_PR;
    // End of variables declaration//GEN-END:variables

}
