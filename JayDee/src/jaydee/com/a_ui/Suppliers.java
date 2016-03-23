/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Suppliers.java
 *
 * Created on Jul 19, 2011, 8:05:38 PM
 */
package jaydee.com.a_ui;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import jaydee.engine.Supplier;
import jaydee.engine.User;
import jaydee.engine.UserLogs;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class Suppliers extends javax.swing.JInternalFrame {

    /** Creates new form Suppliers */
     DBFunctions db;
    ArrayList<Supplier> supplierList;
    int index = -1;
    boolean add_new = false;
    User user;
    public Suppliers(User user) {
        this.user = user;
        db = new DBFunctions();
        initComponents();
        if (!loadSuppliers()) {
            JOptionPane.showMessageDialog(rootPane, "Sorry, there are either no suppliers or I could not retrieve any from the database.");
            btnEdit.setSelected(false);
            btnDelete.setSelected(false);
            btnPrev.setSelected(false);
            btnNext.setSelected(false);
            index = -1;
        } else {
            index = 0;
            loadSupplierToDisplay(index);
        }
    }

    
     public final void loadSupplierToDisplay(int i) {
        if (i > -1) {
            Supplier s = supplierList.get(i);
            txtName.setText(s.name);
            txtPhoneNo.setText(s.phone_no);
            txtEmail.setText(s.email);
            txtLocation.setText(s.location);
            txtDue_Ksh.setText(String.valueOf(s.due_ksh));
            txtIndex.setText((i+1)+"");
            lbl_total_no_of_suppliers.setText(supplierList.size()+"");
            lbl_info.setText("Information Label");
        } else {
            lbl_info.setText("I could not find a supplier to load!");
        }
    }

    public final boolean loadSuppliers() {
        /**
         * The ingenious piece of code from Wachanga
         */
        boolean ok = false;
        try {
            Supplier s;
                supplierList = new ArrayList<Supplier>();
            String query = "SELECT * FROM supplier;";
            ResultSet rs = db.stmt.executeQuery(query);
            while (rs.next()) {
                s = new Supplier(rs.getInt("sid"), rs.getString("name"), rs.getString("phone_no"), rs.getString("email"), rs.getString("location"), rs.getInt("due_ksh"));
                supplierList.add(s);
                ok = true;
                index = 0;
            }
        } catch (SQLException ex) {
            index = -1;
            Logger.getLogger(Suppliers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }

     public boolean enableEditing(boolean enable) {
        boolean enabled = false;
        txtName.setEditable(enable);
        txtEmail.setEditable(enable);
        txtLocation.setEditable(enable);
        txtPhoneNo.setEditable(enable);
        btnPrev.setEnabled(!enable);
        btnNext.setEnabled(!enable);
        btnSave.setEnabled(enable);
        btnCancel.setEnabled(enable);
        btnDelete.setEnabled(!enable);
        btnAddNew.setEnabled(!enable);
        btnEdit.setEnabled(!enable);
        enabled = true;
        return enabled;
    }

    public void clearTextFields() {
        txtName.setText("");
        txtPhoneNo.setText("");
        txtEmail.setText("");
        txtLocation.setText("");
        txtDue_Ksh.setText("");
        txtIndex.setText("");
    }
    
    public boolean saveDetails() {
        boolean success = false;


            if (add_new) {
                try {
                    String query = "INSERT INTO supplier (`name`, `phone_no`, `email`, `location`) VALUES (?,?,?,?);";
                    PreparedStatement pstmt = (PreparedStatement) db.con.prepareStatement(query);
                    pstmt.setString(1, txtName.getText());
                    pstmt.setString(2, txtPhoneNo.getText());
                    pstmt.setString(3, txtEmail.getText());
                    pstmt.setString(4, txtLocation.getText());
                    pstmt.executeUpdate();
                    UserLogs.setLog(user, "Inserted a new supplier: "+txtName.getText(), Suppliers.class.getName());
                    success = true;
                } catch (SQLException ex) {
                    Logger.getLogger(Suppliers.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                try {
                    Supplier s = supplierList.get(index);
                    String query = "UPDATE suppliers SET `name`='" + s.name + "' ,`phone_no`='" + s.phone_no + "', `email`='" + s.email + "', `location`='" + s.location + "' WHERE sid=" + s.sid + ";";
                    UserLogs.setLog(user, "Updated supplier: "+s.name, Suppliers.class.getName());
                    db.stmt.executeQuery(query);
                    success = true;
                } catch (SQLException ex) {
                    Logger.getLogger(Suppliers.class.getName()).log(Level.SEVERE, null, ex);
                
            }

        }
        return success;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtPhoneNo = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtLocation = new javax.swing.JTextField();
        txtDue_Ksh = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtIndex = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnAddNew = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lbl_info = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_total_no_of_suppliers = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Suppliers");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/jdp16.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Phone Number:");

        jLabel1.setText("Name: ");

        txtPhoneNo.setEditable(false);

        txtName.setEditable(false);

        txtLocation.setEditable(false);

        txtDue_Ksh.setEditable(false);

        txtEmail.setEditable(false);

        jLabel4.setText("Location: ");

        jLabel3.setText("Email: ");

        jLabel5.setText("Due Ksh:");

        jLabel7.setText("Index:");

        txtIndex.setEditable(false);
        txtIndex.setToolTipText("The supplier's");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDue_Ksh, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(txtLocation, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(txtPhoneNo, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(txtIndex))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addComponent(txtDue_Ksh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Action"));

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/edit16.jpg"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnAddNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Add16.png"))); // NOI18N
        btnAddNew.setText("Add New");
        btnAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/delete16.jpg"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/order-24.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddNew)
                .addGap(30, 30, 30)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addGap(59, 59, 59)
                .addComponent(btnRefresh)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddNew)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/navigate-left-24.png"))); // NOI18N
        btnPrev.setText("Previous");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/navigate-right24.png"))); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/Save24.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/res/cancel (2).png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setEnabled(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPrev)
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addGap(50, 50, 50)
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        lbl_info.setText("Information Label");

        jLabel6.setText("Total Number of Suppliers:");

        lbl_total_no_of_suppliers.setText("No.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(lbl_info, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(lbl_total_no_of_suppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(293, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_total_no_of_suppliers))
                .addGap(1, 1, 1)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lbl_info))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        enableEditing(true);
        add_new = false;
}//GEN-LAST:event_btnEditActionPerformed

    private void btnAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewActionPerformed
        enableEditing(true);
        clearTextFields();
        add_new = true;
}//GEN-LAST:event_btnAddNewActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Supplier s = supplierList.get(index);
        String query = "DELETE FROM supplier WHERE sid=" + s.sid + ";";
        
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete supplier:'" + s.name + "?'", "Confirm Delete", JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                db.stmt.executeUpdate(query);
                index = 0;
                enableEditing(false);
                if (!loadSuppliers()) {
                    JOptionPane.showMessageDialog(rootPane, "Sorry, there are either no suppliers or I could not retrieve any from the database.");
                    btnEdit.setSelected(false);
                    btnDelete.setSelected(false);
                    btnPrev.setSelected(false);
                    btnNext.setSelected(false);
                } else {
                    loadSupplierToDisplay(index);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Suppliers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        index = 0;
        enableEditing(false);
        if (!loadSuppliers()) {
            JOptionPane.showMessageDialog(rootPane, "Sorry, there are either no suppliers or I could not retrieve any from the database.");
            btnEdit.setSelected(false);
            btnDelete.setSelected(false);
            btnPrev.setSelected(false);
            btnNext.setSelected(false);
        } else {
            loadSupplierToDisplay(index);
        }
}//GEN-LAST:event_btnRefreshActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (index > 0) {
            loadSupplierToDisplay(index - 1);
            index--;
            lbl_info.setText("Information Label");
        } else {
            lbl_info.setText("No more suppliers");
        }
}//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (index < (supplierList.size() - 1)) {
            loadSupplierToDisplay(index + 1);
            index++;
            lbl_info.setText("Information Label");
        } else {
            lbl_info.setText("No more suppliers");
        }
}//GEN-LAST:event_btnNextActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Pattern pattern = Pattern.compile("(\\w)+@\\w+(\\.\\w+)");
        String email = txtEmail.getText();
        
        Matcher match = pattern.matcher(email);
        if (match.matches()) {
            System.out.println("Pattern matches!!!! Congrats Amos");
            
            
            if (saveDetails()) {
                JOptionPane.showMessageDialog(rootPane, "I have saved successfully");
                //Refresh the form
                index = 0;
                enableEditing(false);
                if (!loadSuppliers()) {
                    JOptionPane.showMessageDialog(rootPane, "Sorry, there are either no suppliers or I could not retrieve any from the database.");
                    btnEdit.setSelected(false);
                    btnDelete.setSelected(false);
                    btnPrev.setSelected(false);
                    btnNext.setSelected(false);
                } else {
                    loadSupplierToDisplay(index);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "I did not save successfully");
            }
            
            
        } else {
            System.out.println("Pattern DOESNOT matches!!!! Congrats Amos");
            lbl_info.setText("The email you entered is not correct");
        }
}//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        enableEditing(false);
        if (index < supplierList.size() && index <= 0) {
            loadSupplierToDisplay(index);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JLabel lbl_total_no_of_suppliers;
    private javax.swing.JTextField txtDue_Ksh;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIndex;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhoneNo;
    // End of variables declaration//GEN-END:variables
}
