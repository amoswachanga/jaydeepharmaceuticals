/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Drug_Supplier.java
 *
 * Created on Jun 28, 2011, 11:08:17 PM
 */
package jaydee.com.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jaydee.engine.Drug;
import jaydee.engine.Supplier;
import jaydee.engine.SupplierDrug;
import jaydee.engine.User;
import jaydee.engine.UserLogs;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class Drug_Supplier extends javax.swing.JFrame {

    /** Creates new form Drug_Supplier */
    DBFunctions db;
    Vector<Supplier> supplierlist;
    Vector<SupplierDrug> supplier_drug_list;
    ArrayList<Integer> list_to_insert;
    ArrayList<Integer> list_to_update;
    ArrayList<Integer> list_to_delete;
    Vector<Drug> druglist;
    User user;
    boolean isFound;
    int index;
    int index_Supplier;
    Drug drug;
    Supplier supplier;

    public Drug_Supplier(User user) {
        this.user = user;
        db = new DBFunctions();
        initComponents();
        setTheBounds(getWidth(), getHeight());
        refresh();
    }

    public final void refresh() {
        isFound = false;
        supplierlist = new Vector<Supplier>();
        supplier_drug_list = new Vector<SupplierDrug>();

        list_to_insert = new ArrayList<Integer>();
        list_to_update = new ArrayList<Integer>();
        list_to_delete = new ArrayList<Integer>();
        
        
        index = 0;
        index_Supplier = 0;

        initSupplierList();
        initDrugsList();
        
    }

    public final void setTheBounds(int a, int b) {
        int width = a;
        int height = b;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        URL url = Drug_Supplier.class.getResource("/com/res/jdp24.png");
        setIconImage(Toolkit.getDefaultToolkit().createImage(url));
    }

    public final void initSupplierList() {
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
            Logger.getLogger(Drug_Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vector<String> string = new Vector<String>();
        for (int i = 0; i < supplierlist.size(); i++) {
            string.add(supplierlist.get(i).name);
        }
        if (found) {
            jListAllSuppliers.removeAll();
            jListAllSuppliers.setListData(string);
            jListAllSuppliers.setSelectedIndex(index_Supplier);
        } else {
            String[] just_some_data = {"nothing", "was", "found"};
            jListAllSuppliers.setListData(just_some_data);
        }
    }

    public final void initDrugsList() {
        druglist = new Vector<Drug>();
        boolean found = false;
        String query = "SELECT did FROM drugs ORDER BY name;";
        try {
            ResultSet rs = db.con.createStatement().executeQuery(query);
            druglist.removeAllElements();
            while (rs.next()) {
                druglist.add(new Drug(rs.getInt("did")));
                found = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DrugEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vector<String> stringDrug = new Vector<String>();
        for (int i = 0; i < druglist.size(); i++) {
            stringDrug.add(druglist.get(i).name);
        }
        if (found) {
            jListAllDrugs.removeAll();
            jListAllDrugs.setListData(stringDrug);
            jListAllDrugs.setSelectedIndex(index);

        } else {
            String[] just_some_data = {"nothing", "was", "found"};
            jListAllDrugs.setListData(just_some_data);
        }
    }
    ArrayList<Integer> ssid;//Used for deleting the supplier drug relations that have been removed

    public final void initSupplierDrugList(int sup) {
        isFound = false;
        ssid = new ArrayList<Integer>();
        String query = "SELECT sid,did,cost,ssid FROM supplier_stock WHERE sid=" + sup + ";";
        try {
            ResultSet rs = db.con.createStatement().executeQuery(query);
            supplier_drug_list.removeAllElements();
            while (rs.next()) {
                supplier_drug_list.add(new SupplierDrug(rs.getInt("sid"), rs.getInt("did"), rs.getDouble("cost")));
                ssid.add(rs.getInt("ssid"));
                isFound = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Drug_Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vector<String> stringDrug = new Vector<String>();
        for (int i = 0; i < supplier_drug_list.size(); i++) {
            stringDrug.add(supplier_drug_list.get(i).drug.name);
        }
        jListSupplierDrug.removeAll();
        if (isFound) {
            jListSupplierDrug.setListData(stringDrug);
            jListSupplierDrug.setSelectedIndex(index);
            lbl_info.setText("Information Label");
        } else {
            String[] just_some_data = {"nothing", "was", "found"};
            jListSupplierDrug.setListData(just_some_data);
            lbl_info.setText("The supplier has not yet been enlisted to supply us anything.");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListSupplierDrug = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListAllDrugs = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListAllSuppliers = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtnameDrug = new javax.swing.JTextField();
        txtpriceDrug = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtdesc = new javax.swing.JTextArea();
        txtdrug_no = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtSupplierCost = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtnameSupplier = new javax.swing.JTextField();
        btnSaveSupplierCost = new javax.swing.JButton();
        lbl_info = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnSaveList = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Supplier's List of Drugs - JayDee Pharmaceuticals");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/order-24.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setToolTipText("Refresh the supplier's drug list");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/navigate-left-24.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.setToolTipText("Remove the selected drugs from thee supplier's list");
        btnRemove.setEnabled(false);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/navigate-right24.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setToolTipText("Add the selected drug to the supplier");
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jListSupplierDrug.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier's Drugs"));
        jListSupplierDrug.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListSupplierDrug.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListSupplierDrug.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListSupplierDrugValueChanged(evt);
            }
        });
        jListSupplierDrug.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jListSupplierDrugFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jListSupplierDrugFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(jListSupplierDrug);

        jListAllDrugs.setBorder(javax.swing.BorderFactory.createTitledBorder("Drug's List"));
        jListAllDrugs.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListAllDrugs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListAllDrugs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jListAllDrugsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jListAllDrugsFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(jListAllDrugs);

        jListAllSuppliers.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier's List"));
        jListAllSuppliers.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListAllSuppliers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListAllSuppliers.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAllSuppliersValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListAllSuppliers);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Drug Details"));

        jLabel2.setText("Drug Name: ");

        txtnameDrug.setEditable(false);

        txtpriceDrug.setEditable(false);

        jLabel5.setText("Description:");

        jLabel3.setText("Price:");

        jLabel4.setText("Drug No:");

        txtdesc.setColumns(20);
        txtdesc.setEditable(false);
        txtdesc.setRows(5);
        jScrollPane4.setViewportView(txtdesc);

        txtdrug_no.setEditable(false);

        jLabel1.setText("SupplierCost:");

        txtSupplierCost.setText("0.0");
        txtSupplierCost.setToolTipText("Theis is the cost the supplier sells the drug");
        txtSupplierCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSupplierCostFocusGained(evt);
            }
        });

        jLabel7.setText("Supplier:");

        txtnameSupplier.setEditable(false);

        btnSaveSupplierCost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Save16.png"))); // NOI18N
        btnSaveSupplierCost.setText("Save");
        btnSaveSupplierCost.setEnabled(false);
        btnSaveSupplierCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveSupplierCostActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSupplierCost, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveSupplierCost))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtnameSupplier, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtnameDrug, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                        .addComponent(txtpriceDrug, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                        .addComponent(txtdrug_no, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtnameDrug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtpriceDrug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtdrug_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtnameSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSupplierCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveSupplierCost))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );

        lbl_info.setText("Please make any changes and finally click save button in the action section.");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Action - Click this to save to database"));

        btnSaveList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Save24.png"))); // NOI18N
        btnSaveList.setText("Save");
        btnSaveList.setToolTipText("<html><em>Click to save the supplier's drugs list to the database</em></html>");
        btnSaveList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(btnSaveList, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnSaveList)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_info, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRemove, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(btnRefresh)
                        .addGap(100, 100, 100)
                        .addComponent(btnAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemove))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_info))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListAllSuppliersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAllSuppliersValueChanged
        //Set drug details in the values.
        index_Supplier = jListAllSuppliers.getSelectedIndex();
        if (index_Supplier > -1) {
            txtnameSupplier.setText(supplierlist.get(index_Supplier).name);
            initSupplierDrugList(supplierlist.get(index_Supplier).sid);
        }
}//GEN-LAST:event_jListAllSuppliersValueChanged

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int index_remove = jListSupplierDrug.getSelectedIndex();
        if (jListSupplierDrug.getSelectedIndex() < ssid.size()) {
            list_to_delete.add(ssid.get(index_remove));
        }
        System.out.println("Index marked for removal "+index_remove);
        if(index_remove < supplier_drug_list.size() && index_remove > -1)
            supplier_drug_list.remove(index_remove);


        Vector<String> stringDrug = new Vector<String>();
        for (int j = 0; j < supplier_drug_list.size(); j++) {
            stringDrug.add(supplier_drug_list.get(j).drug.name);
        }
        jListSupplierDrug.removeAll();
        jListSupplierDrug.setListData(stringDrug);

        btnRemove.setEnabled(false);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refresh();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void jListSupplierDrugValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListSupplierDrugValueChanged
        index = jListSupplierDrug.getSelectedIndex();
        if (index > -1 && isFound) {
            txtnameDrug.setText(supplier_drug_list.get(index).drug.name);
            txtdesc.setText(supplier_drug_list.get(index).drug.desc);
            txtpriceDrug.setText(supplier_drug_list.get(index).drug.price + "");
            txtdrug_no.setText(supplier_drug_list.get(index).drug.code + "");
            txtSupplierCost.setText(supplier_drug_list.get(index).cost + "");
        }
    }//GEN-LAST:event_jListSupplierDrugValueChanged

    private void btnSaveListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveListActionPerformed
       boolean success_delete = true;
       boolean success_insert = true;
       boolean success_update = true;
        //Remove drugs
        for(int i = 0; i < list_to_delete.size(); i++){
            if(deleteValues(list_to_delete.get(i))){
                success_delete = true;
            }else{
                success_delete = false;
                break;
            }
        }
        //Add drugs
         for(int i = 0; i < list_to_insert.size(); i++){
            if(insertValues(supplier_drug_list.get(list_to_insert.get(i)).sid, supplier_drug_list.get(list_to_insert.get(i)).did, supplier_drug_list.get(list_to_insert.get(i)).cost)){
                success_insert = true;
            }else{
                success_insert = false;
                break;
            }
        }
         
         //Update drug values
         for(int i = 0; i < list_to_update.size(); i++){
            if(insertValues(supplier_drug_list.get(list_to_update.get(i)).cost, supplier_drug_list.get(list_to_update.get(i)).sid, supplier_drug_list.get(list_to_update.get(i)).did)){
                success_update = true;
            }else{
                success_update = false;
                break;
            }
        }
         
         if(success_insert && success_update && success_delete){
             lbl_info.setText("All changes have been saved successfully.");
             UserLogs.setLog(user, "User successfully changed supplier drug values.", Drug_Supplier.class.getName());
         }else{
             lbl_info.setText("Some changes were lost and there was an error saving them to the database");
         }
         
         refresh();
    }//GEN-LAST:event_btnSaveListActionPerformed

       
    public boolean insertValues(double cost, int sid,int did){
        boolean success = false;
         try {
            String query = "UPDATE supplier_stock SET  `cost` = ? WHERE `sid` = ? AND `did` = ?;";
            PreparedStatement pstmt = db.con.prepareStatement(query);
            pstmt.setInt(2, sid);
            pstmt.setInt(3, did);
            pstmt.setDouble(1, cost);
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            success = false;
            Logger.getLogger(Drug_Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return success;
    }
    
    public boolean insertValues(int sid,int did, double cost){
        boolean success = false;
        try {
            String query = "INSERT INTO supplier_stock (`sid`,`did`,`cost`) VALUES (?,?,?);";
            PreparedStatement pstmt = db.con.prepareStatement(query);
            pstmt.setInt(1, sid);
            pstmt.setInt(2, did);
            pstmt.setDouble(3, cost);
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            success = false;
            Logger.getLogger(Drug_Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return success;           
    }
    
    public boolean deleteValues(int ssid){
        boolean success = false;
        try {
            String query = "DELETE FROM supplier_stock WHERE `ssid`=?;";
            PreparedStatement pstmt = db.con.prepareStatement(query);
            pstmt.setInt(1, ssid);
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            success = false;
            Logger.getLogger(Drug_Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return success;
    }
    private void jListSupplierDrugFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jListSupplierDrugFocusGained
        btnRemove.setEnabled(true);
}//GEN-LAST:event_jListSupplierDrugFocusGained

    private void jListAllDrugsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jListAllDrugsFocusGained
        btnAdd.setEnabled(true);
}//GEN-LAST:event_jListAllDrugsFocusGained

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        boolean found = false;
        for (int i = 0; i < supplier_drug_list.size(); i++) {
            if (druglist.get(jListAllDrugs.getSelectedIndex()).getDid() == supplier_drug_list.get(i).did) {
                found = true;
                break;
            }
        }
        if (!found) {
            SupplierDrug s = new SupplierDrug(supplierlist.get(jListAllSuppliers.getSelectedIndex()).sid, druglist.get(jListAllDrugs.getSelectedIndex()).getDid(), 0.0);
            supplier_drug_list.add(s);
            list_to_insert.add(supplier_drug_list.indexOf(s));
            Vector<String> stringDrug = new Vector<String>();
            for (int i = 0; i < supplier_drug_list.size(); i++) {
                stringDrug.add(supplier_drug_list.get(i).drug.name);
            }
            jListSupplierDrug.removeAll();
            jListSupplierDrug.setListData(stringDrug);
        }
        btnAdd.setEnabled(false);
}//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveSupplierCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSupplierCostActionPerformed
        lbl_info.setText("Information Label");
        int index_selected = jListSupplierDrug.getSelectedIndex();
        list_to_update.add(index_selected);
        try{
        supplier_drug_list.elementAt(index_selected).cost = Double.parseDouble(txtSupplierCost.getText());
        lbl_info.setText("Success in saving the drug cost");
        }catch(NumberFormatException e){
            lbl_info.setText("Please enter a number as the supplier cost for that drug!");
        }
        btnSaveSupplierCost.setEnabled(false);
    }//GEN-LAST:event_btnSaveSupplierCostActionPerformed

    private void txtSupplierCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSupplierCostFocusGained
        btnSaveSupplierCost.setEnabled(true);
    }//GEN-LAST:event_txtSupplierCostFocusGained

    private void jListSupplierDrugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jListSupplierDrugFocusLost
        btnRemove.setEnabled(false);
    }//GEN-LAST:event_jListSupplierDrugFocusLost

    private void jListAllDrugsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jListAllDrugsFocusLost
        btnAdd.setEnabled(false);
    }//GEN-LAST:event_jListAllDrugsFocusLost

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new Drug_Supplier(new User(1)).setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSaveList;
    private javax.swing.JButton btnSaveSupplierCost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jListAllDrugs;
    private javax.swing.JList jListAllSuppliers;
    private javax.swing.JList jListSupplierDrug;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JTextField txtSupplierCost;
    private javax.swing.JTextArea txtdesc;
    private javax.swing.JTextField txtdrug_no;
    private javax.swing.JTextField txtnameDrug;
    private javax.swing.JTextField txtnameSupplier;
    private javax.swing.JTextField txtpriceDrug;
    // End of variables declaration//GEN-END:variables
}
