/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicine;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class SQLConnection {
    static final String JDBC_CLASS = "org.sqlite.JDBC";
    static final String SQ_RES = "jdbc:sqlite::resource:medicine/res/project123.sqlite";
    
    public static Connection connectDB(){
        try{
            Class.forName(JDBC_CLASS);
            Connection mConnect = DriverManager.getConnection(SQ_RES);
            return mConnect;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
