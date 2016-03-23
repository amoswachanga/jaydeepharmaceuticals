/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jaydee;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 *
 * @author Wachanga
 */
public class SplashScreen extends JWindow {

    //Create the window for the splash screen
    public SplashScreen() {
        showSplash();
        setSplashVisible();
    }

    public final void showSplash() {
        //Create the Panel that holds the splash screen and set it in the main window
        JPanel content = (JPanel) this.getContentPane();
        //Create an instance of the panel which is the splash screen
        SplashUI splash =new SplashUI();
        int width = 320;
        int height = 214;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        this.setBounds(x, y, width, height);

        content.add(splash);
    }

    public final void setSplashVisible() {
        this.setVisible(true);
    }

    public void closeSplash(){
        this.setVisible(false);
        this.dispose();
    }

    public void setActiveThread(int duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
        }
    }
   
//     public static void main(String[] args) {
//         
//        SplashScreen scr = new SplashScreen();
//        scr.setAlwaysOnTop(true);
//        scr.setActiveThread(7500);
//        scr.closeSplash();
//    }
}
