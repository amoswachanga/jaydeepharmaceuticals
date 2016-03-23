/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Main_User.java
 *
 * Created on May 24, 2011, 10:34:25 PM
 */

package jaydee.com;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jaydee.com.gui.DrugEditor;
import jaydee.com.gui.Suppliers;
import jaydee.engine.Drug;
import jaydee.engine.POS_Engine;
import reusable.DBFunctions;



/**
 *
 * @author Wachanga
 */
public class Main_User extends javax.swing.JFrame {

    /** Creates new form Main */
    POS_Engine pos;
    DBFunctions db;
    Vector<Drug> druglist;
    int index = 0;
    public Main_User() {
        db = new DBFunctions();
        druglist = new Vector<Drug>();
        pos = new POS_Engine();
        initComponents();
        setTheBounds(getWidth(), getHeight());
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent ev){
                if(JOptionPane.showConfirmDialog(rootPane, "Are you sure you wish to exit? \nAny unsaved changes will be lost!","Exiting...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                     System.exit(0);
                }
            }
        });
        
        URL url = Main_User.class.getResource("/com/res/jdp24.png");
        setIconImage(Toolkit.getDefaultToolkit().createImage(url));
        
        initDrugsList();
    }
    
    public final void setTheBounds(int a,int b){
        int width = a;
        int height = b;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
    }
    
    public final void initDrugsList(){
        boolean found = false;
            String query = "SELECT did FROM drugs;";
        try {
            ResultSet rs = db.stmt.executeQuery(query);            
            while(rs.next()){
                druglist.add(new Drug(rs.getInt("did")));
                found = true;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DrugEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vector<String> stringDrug = new Vector<String>();
        for(int i = 0; i < druglist.size(); i++){
            stringDrug.add(druglist.get(i).name);
        }
        if(found){
            jListAllDrugs.setListData(stringDrug);            
            jListAllDrugs.setSelectedIndex(index);
        }else{
            String [] just_some_data = {"nothing", "was","found"};
            jListAllDrugs.setListData(just_some_data);            
            DrugEditor d = new DrugEditor(true, "Please enter a new drug. There are no drugs to sell!");
            d.setVisible(true);
            //d.setAlwaysOnTop(true);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtItem = new javax.swing.JTextField();
        btnAddItem = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListAllDrugs = new javax.swing.JList();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnPayCash1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnRefreshLists = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnFindItem = new javax.swing.JButton();
        btnRemoveItem1 = new javax.swing.JButton();
        btnDiscount = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnCustomer1 = new javax.swing.JButton();
        btnClear1 = new javax.swing.JButton();
        lbl_info = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemViewSuppliers = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItemAddNewSuppliers = new javax.swing.JMenuItem();
        jMenuItemRemoveSupplier = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemViewDrugs = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItemAddNewDrug = new javax.swing.JMenuItem();
        jMenuItemModifyDrug = new javax.swing.JMenuItem();
        jMenuItemRemoveDrug = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        menuContent = new javax.swing.JMenuItem();
        menuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JayDee Pharmaceuticals");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Item"));

        jLabel7.setText("Item:");

        txtItem.setToolTipText("Enter item code");
        txtItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItemFocusLost(evt);
            }
        });
        txtItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItemKeyPressed(evt);
            }
        });

        btnAddItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/shopping-bag.png"))); // NOI18N
        btnAddItem.setText("Add Item");
        btnAddItem.setToolTipText("Find or select an item");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        jListAllDrugs.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListAllDrugs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAllDrugsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListAllDrugs);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddItem)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddItem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Process Payment"));

        jLabel1.setText("Sub-total:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("0.00");

        jLabel2.setText("Tax:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("16.5%");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("0.00");
        jLabel6.setToolTipText("Grand Total");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel3.setText("Total: ");

        jLabel8.setText("Discount: ");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("10%");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                        .addGap(12, 12, 12))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnPayCash1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/checkout.png"))); // NOI18N
        btnPayCash1.setText("Pay - Cash (F2)");
        btnPayCash1.setToolTipText("Pay using Cash");
        btnPayCash1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPayCash1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPayCash1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayCash1ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPayCash1);
        jToolBar1.add(jSeparator1);

        btnRefreshLists.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/new.png"))); // NOI18N
        btnRefreshLists.setText("RefreshDrugs(F5)");
        btnRefreshLists.setToolTipText("Refresh Drugs List");
        btnRefreshLists.setFocusable(false);
        btnRefreshLists.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefreshLists.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefreshLists.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshListsActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefreshLists);
        jToolBar1.add(jSeparator3);

        btnFindItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/shopping-bag.png"))); // NOI18N
        btnFindItem.setText("Find Item (F6)");
        btnFindItem.setToolTipText("Find or select an item");
        btnFindItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFindItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFindItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindItemActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFindItem);

        btnRemoveItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/secure-payment.png"))); // NOI18N
        btnRemoveItem1.setText("Remove (F7)");
        btnRemoveItem1.setToolTipText("Remove an item");
        btnRemoveItem1.setFocusable(false);
        btnRemoveItem1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemoveItem1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveItem1ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRemoveItem1);

        btnDiscount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/discount.png"))); // NOI18N
        btnDiscount.setText("Return (F8)");
        btnDiscount.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDiscount.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnDiscount);
        jToolBar1.add(jSeparator2);

        btnCustomer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/user2 (3).png"))); // NOI18N
        btnCustomer1.setText("Customer (F11)");
        btnCustomer1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCustomer1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCustomer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomer1ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCustomer1);

        btnClear1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/empty-shopping-cart.png"))); // NOI18N
        btnClear1.setText("Clear (F12)");
        btnClear1.setToolTipText("Empty shopping cart");
        btnClear1.setFocusable(false);
        btnClear1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClear1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnClear1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lbl_info.setText("Information Label");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Item Name", "Item Code", "Qty", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_info, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_info))
        );

        jScrollPane2.setViewportView(jPanel7);

        fileMenu.setText("File");

        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save As ...");
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        jMenu1.setText("Suppliers");

        jMenuItemViewSuppliers.setText("View Suppliers");
        jMenuItemViewSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemViewSuppliersActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemViewSuppliers);
        jMenu1.add(jSeparator4);

        jMenuItemAddNewSuppliers.setText("Add New Supplier");
        jMenuItemAddNewSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddNewSuppliersActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemAddNewSuppliers);

        jMenuItemRemoveSupplier.setText("Remove Supplier");
        jMenuItemRemoveSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoveSupplierActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemRemoveSupplier);

        menuBar.add(jMenu1);

        jMenu2.setText("Drugs");

        jMenuItemViewDrugs.setText("View Drugs");
        jMenuItemViewDrugs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemViewDrugsActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemViewDrugs);
        jMenu2.add(jSeparator5);

        jMenuItemAddNewDrug.setText("Add New Drug");
        jMenuItemAddNewDrug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddNewDrugActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemAddNewDrug);

        jMenuItemModifyDrug.setText("Modify Drug");
        jMenuItemModifyDrug.setToolTipText("Modify Drug Details");
        jMenuItemModifyDrug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemModifyDrugActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemModifyDrug);

        jMenuItemRemoveDrug.setText("Remove Drug");
        jMenuItemRemoveDrug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoveDrugActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemRemoveDrug);

        menuBar.add(jMenu2);

        helpMenu.setText("Help");

        menuContent.setText("Contents");
        helpMenu.add(menuContent);

        menuAbout.setText("About");
        menuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAboutActionPerformed(evt);
            }
        });
        helpMenu.add(menuAbout);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        if(JOptionPane.showConfirmDialog(rootPane, "Are you sure you wish to exit? \nAny unsaved changes will be lost!","Exiting...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                     System.exit(0);
           }
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
            if(JOptionPane.showConfirmDialog(rootPane, "Are you sure you wish to exit? \nAny unsaved changes will be lost!","Exiting...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                     System.exit(0);
           }
    }//GEN-LAST:event_menuAboutActionPerformed

    private void txtItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            pos.addItemToList(txtItem.getText());
        }
    }//GEN-LAST:event_txtItemKeyPressed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        pos.addItemToList(txtItem.getText());
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void txtItemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemFocusLost
        if(!txtItem.getText().isEmpty()){
            txtItem.setText("");
        }
    }//GEN-LAST:event_txtItemFocusLost

    private void btnFindItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindItemActionPerformed
        pos.addItemToList(txtItem.getText());
    }//GEN-LAST:event_btnFindItemActionPerformed

    private void btnRemoveItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveItem1ActionPerformed

    private void btnCustomer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCustomer1ActionPerformed

    private void btnRefreshListsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshListsActionPerformed
        initDrugsList();
    }//GEN-LAST:event_btnRefreshListsActionPerformed

    private void jMenuItemViewDrugsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewDrugsActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DrugEditor(false,"Information Message").setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItemViewDrugsActionPerformed

    private void jMenuItemAddNewDrugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddNewDrugActionPerformed
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DrugEditor(true,"Information Message").setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItemAddNewDrugActionPerformed

    private void jMenuItemModifyDrugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModifyDrugActionPerformed
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DrugEditor(true,"Information Message").setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItemModifyDrugActionPerformed

    private void jMenuItemRemoveDrugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoveDrugActionPerformed
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DrugEditor(false,"Information Message").setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItemRemoveDrugActionPerformed

    private void jMenuItemViewSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewSuppliersActionPerformed
         java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Suppliers().setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItemViewSuppliersActionPerformed

    private void jMenuItemAddNewSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddNewSuppliersActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Suppliers().setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItemAddNewSuppliersActionPerformed

    private void jMenuItemRemoveSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoveSupplierActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Suppliers().setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItemRemoveSupplierActionPerformed

    private void jListAllDrugsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAllDrugsValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jListAllDrugsValueChanged

    private void btnPayCash1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayCash1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPayCash1ActionPerformed

    
   
    /**
    * @param args the command line arguments
    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Main_User().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnClear1;
    private javax.swing.JButton btnCustomer1;
    private javax.swing.JButton btnDiscount;
    private javax.swing.JButton btnFindItem;
    private javax.swing.JButton btnPayCash1;
    private javax.swing.JButton btnRefreshLists;
    private javax.swing.JButton btnRemoveItem1;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jListAllDrugs;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItemAddNewDrug;
    private javax.swing.JMenuItem jMenuItemAddNewSuppliers;
    private javax.swing.JMenuItem jMenuItemModifyDrug;
    private javax.swing.JMenuItem jMenuItemRemoveDrug;
    private javax.swing.JMenuItem jMenuItemRemoveSupplier;
    private javax.swing.JMenuItem jMenuItemViewDrugs;
    private javax.swing.JMenuItem jMenuItemViewSuppliers;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JMenuItem menuAbout;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuContent;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JTextField txtItem;
    // End of variables declaration//GEN-END:variables

}