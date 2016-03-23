package jaydee.com.testors;

// DatabaseTest.java
//
import jaydee.com.testors.QueryTableModelTestor;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;

public class QueryModelTest extends JFrame {
  
  JTextField hostField;
  JTextField queryField;
  QueryTableModelTestor qtm;

  public QueryModelTest( ) {
    super("Database Test Frame");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(350, 200);
        try {
            qtm = new QueryTableModelTestor( );
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QueryModelTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(QueryModelTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(QueryModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    JTable table = new JTable(qtm);
    JScrollPane scrollpane = new JScrollPane(table);
    JPanel p1 = new JPanel( );
    p1.setLayout(new GridLayout(3, 2));
    p1.add(new JLabel("Enter the Host URL: "));
    p1.add(hostField = new JTextField( ));
    p1.add(new JLabel("Enter your query: "));
    p1.add(queryField = new JTextField( ));
    p1.add(new JLabel("Click here to send: "));

    JButton jb = new JButton("Search");
    jb.addActionListener(new ActionListener( ) {
      public void actionPerformed(ActionEvent e) {
        qtm.setHostURL(hostField.getText( ).trim( ));
        qtm.setQuery(queryField.getText( ).trim( ));
      }
    } );
    p1.add(jb);
    getContentPane( ).add(p1, BorderLayout.NORTH);
    getContentPane( ).add(scrollpane, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    QueryModelTest tt = new QueryModelTest();
    tt.setVisible(true);
  }
}