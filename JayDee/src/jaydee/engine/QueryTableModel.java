/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee.engine;

import com.mysql.jdbc.ResultSetMetaData;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import reusable.DBFunctions;

/**
 *
 * @author Wachanga
 */
public class QueryTableModel extends AbstractTableModel{
    
    Vector cache;
    int colCount;
  String[] headers;
  
  DBFunctions db;

    public QueryTableModel(String query) {
        db = new DBFunctions();
        this.cache = new Vector();
        setQuery(query);
    }

    @Override
    public String getColumnName(int i) { return headers[i]; }

    public int getRowCount() {
       return cache.size();
    }

    public int getColumnCount() {
       return colCount;
    }

    public Object getValueAt(int row, int col) {
        return ((String[])cache.elementAt(row))[col];
    }
    
    
    
    public final void setQuery(String q) {
    cache = new Vector( );
    try {
      // Execute the query and store the result set and its metadata.
      ResultSet rs = db.stmt.executeQuery(q);
      ResultSetMetaData meta = (ResultSetMetaData) rs.getMetaData( );
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

    
}
