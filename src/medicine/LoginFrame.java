/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicine;

import com.alee.laf.WebLookAndFeel;
import java.awt.Color;
import static java.awt.EventQueue.invokeLater;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author hp
 */
public class LoginFrame extends JFrame {

    //global variable declear
    private Connection mConnect;
    private ResultSet rSet;
    private PreparedStatement pStatement;

    public LoginFrame() {
        super("Sign In");
        initComponents();
    }

    public static void main(String[] args) {
        WebLookAndFeel.install();

        invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    private void initComponents() {
        setSize(900, 600);
        setLayout(null);
        setBackground(new Color(41, 143, 102));
        setLocation(200, 80);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adding window Icon
        ImageIcon windowIcon = new ImageIcon(LoginFrame.class.getResource("medbg.png"));
        setIconImage(windowIcon.getImage());

        //calling Main Panel and set the layout as Border
        JPanel panel = new JPanel();
        add(panel);

        //panel object pass by a mathod
        placeComponents(panel);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);
        panel.setBounds(-100, 0, 1000, 600);
        panel.setBackground(new Color(41, 143, 102));

        //adding Toggle Button
        JToggleButton mToggle = new JToggleButton("Connect");
        mToggle.setBounds(720, 80, 120, 50);
        mToggle.setForeground(Color.DARK_GRAY);
        mToggle.setText("Connect");

        //adding ProgressBar
        ImageIcon progessImg = new ImageIcon(LoginFrame.class.getResource("ic_pro.gif"));

        JLabel proLabel = new JLabel(progessImg);
        proLabel.setBounds(700, 50, 160, 20);
        proLabel.setVisible(false);
        panel.add(proLabel);

        //Connect Button Listener
        mToggle.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                if (mToggle.isSelected()) {
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            for (int x = 0; x <= 100; x += 10) {
                                try {
                                    proLabel.setVisible(true);
                                    Thread.sleep(70);
                                } catch (InterruptedException e) {
                                    e.getMessage();
                                }
                            }
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    mConnect = SQLConnection.connectDB();
                                    proLabel.setVisible(false);
                                    mToggle.setText("Disconnect");
                                }
                            });
                        }
                    };
                    t.start();
                } else {
                    mConnect = null;
                    mToggle.setText("Connect");
                    proLabel.setVisible(false);
                }
            }
        });
        panel.add(mToggle);

        //calling LOG IN panel
        JComponent tab1 = makeTextPanel("Panel #1");
        tab1.setBackground(new Color(43, 143, 102));

        JComponent tab2 = makeTextPanel("Panel #2");
        tab2.setBackground(new Color(53, 153, 102));

        //Creating Tab
        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.setBackground(new Color(235, 235, 235));
        tabPanel.setBorder(BorderFactory.createBevelBorder(0, new Color(41, 143, 102), new Color(0, 0, 0)));
        tabPanel.setBounds(540, 250, 350, 220);

        tabPanel.addTab("   Admin   ", null, tab1, null);
        tabPanel.addTab("   User   ", null, tab2, null);
        panel.add(tabPanel);

        //adding components into Tab1
        //Label for User_Name
        JLabel uLabel = new JLabel("Username");
        uLabel.setBounds(70, 18, 80, 30);
        uLabel.setForeground(Color.WHITE);
        tab1.add(uLabel);

        //Typing user_name
        JTextField uText = new JTextField(20);
        uText.setBounds(160, 20, 160, 30);
        uText.setToolTipText("Enter Your Username");
        tab1.add(uText);

        //Label for password
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(70, 53, 80, 30);
        passLabel.setForeground(Color.WHITE);
        tab1.add(passLabel);

        JPasswordField passText = new JPasswordField(20);
        passText.setBounds(160, 55, 160, 30);
        tab1.add(passText);

        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(160, 95, 80, 40);
        loginButton.setBackground(new Color(185, 223, 147));
        tab1.add(loginButton);

        loginButton.addActionListener((ActionEvent e) -> {
            //writting SQL
            String SQL = "Select * from Admin where Username=? and Password=?";
            try {
                if (mConnect != null) {
                    pStatement = mConnect.prepareStatement(SQL);
                    pStatement.setString(1, uText.getText());
                    pStatement.setString(2, passText.getText());
                    rSet = pStatement.executeQuery();
                    if (rSet.next()) {
                        new MenuFrame(mConnect, pStatement, rSet,false).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username and Password", "Access Denied",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please connect your database!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //adding components into Tab2
        //Label for User_Name
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(70, 18, 80, 30);
        userLabel.setForeground(Color.WHITE);
        tab2.add(userLabel);

        //Typing user_name
        JTextField userText = new JTextField(20);
        userText.setBounds(160, 20, 160, 30);
        tab2.add(userText);

        //Label for password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(70, 53, 80, 30);
        passwordLabel.setForeground(Color.WHITE);
        tab2.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(160, 55, 160, 30);
        tab2.add(passwordText);

        JButton logButton = new JButton("Log In");
        logButton.setBounds(160, 95, 80, 40);
        loginButton.setBackground(new Color(185, 223, 147));
        tab2.add(logButton);
        
        logButton.addActionListener((ActionEvent e) -> {
            //writting SQL
            String SQL = "Select * from EmployeeInfo where Username=? and Password=?";
            try {
                if (mConnect != null) {
                    pStatement = mConnect.prepareStatement(SQL);
                    pStatement.setString(1, userText.getText());
                    pStatement.setString(2, passwordText.getText());
                    rSet = pStatement.executeQuery();
                    if (rSet.next()) {
                        new MenuFrame2(mConnect, pStatement, rSet,true).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username and Password", "Access Denied",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please connect your database!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //adding Background Image into panel
        ImageIcon img = new ImageIcon(LoginFrame.class.getResource("ic_bg.png"));
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(110, 0, 700, 600);
        panel.add(imgLabel);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(null);
        return panel;
    }
}
