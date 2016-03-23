/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee;

import com.users.Login;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jaydee.engine.UserLogs;
import reusable.DBFuncGUI;
import reusable.DBManager;
import reusable.Info;

/**
 *
 * @author Wachanga
 */
public class Main {

//    public static void run() {
//        Calendar cal = Calendar.getInstance();
//        UserLogs getlogger;
//        getlogger = new UserLogs(cal);
//        if (!getlogger.isDateReached()) {
//            if (DBManager.testDBsettings()) {
//                Login login = new Login();
//                login.setVisible(true);
//            } else {
//                java.awt.EventQueue.invokeLater(new Runnable() {
//
//                    public void run() {
//                        DBFuncGUI dbSettings = new DBFuncGUI(new Info("Please contact the vendor for the database settings"));
//                        dbSettings.setVisible(true);
//                    }
//                });
//            }
//        } else {
//            System.err.println(getlogger.error() + "\t\t\t\t\t\t\t\t\t\t\t\tMa error");
//        }
//    }

    public static void main(String[] args) {

        Calendar cal = Calendar.getInstance();
        UserLogs getlogger;
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LookAndFeel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(LookAndFeel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(LookAndFeel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(LookAndFeel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        SplashScreen scr;
        getlogger = new UserLogs(cal);
        if (!getlogger.isDateReached()) {
            scr = new SplashScreen();
            scr.setVisible(true);
            scr.setAlwaysOnTop(true);
            scr.setActiveThread(5000);
            if (DBManager.testDBsettings()) {
                Login login = new Login();
                scr.closeSplash();
                login.setVisible(true);
            } else {
                scr.closeSplash();
                DBFuncGUI dbSettings = new DBFuncGUI(new Info("This was brought because of wrong settings for database"));
                dbSettings.setVisible(true);
            }
        } else {
            System.err.println(getlogger.error() + "\t\t\t\t\t\t\t\t\t\t\t\tMa error");
        }
    }
}
