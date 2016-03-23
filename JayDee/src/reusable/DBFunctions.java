/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reusable;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wachanga
 */
public class DBFunctions {

    public DBManager dbmanage;
    public Connection con;
    public Statement stmt;

    public DBFunctions(){
        try {
            dbmanage = new DBManager();
            con = (Connection) dbmanage.connect();
            stmt = (Statement) con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Statement createAStatement() throws SQLException{
        stmt = (Statement) con.createStatement();
        return stmt;
    }
}
