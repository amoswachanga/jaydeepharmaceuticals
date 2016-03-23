package jaydee.com.testors;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.Vector;
import javax.swing.table.*;


/**
 *
 * @author Wachanga
 */
class QueryTableModelTestor extends AbstractTableModel {
    Vector cache;  // Will hold String[] objects
  int colCount;
  String[] headers;
  Connection db;
  Statement statement;
  String currentURL;

  public QueryTableModelTestor( ) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    cache = new Vector( );
    Class.forName("com.mysql.jdbc.Driver").newInstance();
  }
   
  public String getColumnName(int i) { return headers[i]; }
  public int getColumnCount( ) { return colCount; }
  public int getRowCount( ) { return cache.size( );}

  public Object getValueAt(int row, int col) { 
    return ((String[])cache.elementAt(row))[col];
  }

  public void setHostURL(String url) {
    if (url.equals(currentURL)) {
      // Same database; we can leave the current connection open
      return;
    }
    // Oops . . . new connection required
    closeDB( );
    initDB(url);
    currentURL = url;
  }

  // All the real work happens here; in a real application, we'd probably perform the
  // query in a separate thread.
  public void setQuery(String q) {
    cache = new Vector( );
    try {
      // Execute the query and store the result set and its metadata.
      ResultSet rs = statement.executeQuery(q);
      ResultSetMetaData meta = rs.getMetaData( );
      colCount = meta.getColumnCount( );

      // Now we must rebuild the headers array with the new column names.
      headers = new String[colCount];
      for (int h=1; h <= colCount; h++) {
        headers[h-1] = meta.getColumnName(h);
      }

      // Now we must file the cache with the records from our query. This would not
      // be practical if we were expecting a few million records in response to our
      // query, but we aren't, so we can do this.
      while (rs.next( )) {
        String[] record = new String[colCount];
        for (int i=0; i < colCount; i++) {
          record[i] = rs.getString(i + 1);
        }
        cache.addElement(record);
      }
      fireTableChanged(null); // Notify everyone that we have a new table.
    }
    catch(Exception e) {
      cache = new Vector( );   // Blank it out and keep going.
      e.printStackTrace( );
    }
  }

  public void initDB(String url) {
    try {
      db = DriverManager.getConnection(url,"root",null);
      statement = db.createStatement( );
    }
    catch(Exception e) {
      System.out.println("Could not initialize the database.");
      e.printStackTrace( );
    }
  }
  //jdbc:mysql://localhost/jaydee
  //select * from drug_constants

  public void closeDB( ) {
    try {
      if (statement != null) { statement.close( ); }
      if (db != null) {        db.close( ); }
    }
    catch(Exception e) {
      System.out.println("Could not close the current connection.");
      e.printStackTrace( );
    }
  }

}
