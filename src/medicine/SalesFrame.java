/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicine;

import com.alee.laf.WebLookAndFeel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import static java.awt.EventQueue.invokeLater;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author hp
 */
public class SalesFrame extends JFrame {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pst;
    private boolean mEnable;
    ImageIcon[] images;
    String[] icoStrings = {"Add", "Delete", "Update"};

    public SalesFrame() {
        super("Sales Cart");
        initComponents();
        //Fillcombo();
    }

    public SalesFrame(Connection mConnect, PreparedStatement pStatement, ResultSet rSet) {
        super("Sales Cart");
        this.conn = mConnect;
        this.pst = pStatement;
        this.rs = rSet;
        initComponents();
    }
    
    public SalesFrame(Connection mConnect, PreparedStatement pStatement, ResultSet rSet, boolean mEnable) {
        super("Sales Cart");
        this.conn = mConnect;
        this.pst = pStatement;
        this.rs = rSet;
        this.mEnable = mEnable;
        initComponents();
    }

    public static void main(String[] args) {
        WebLookAndFeel.install();

        invokeLater(() -> {
            new SalesFrame().setVisible(true);
        });
    }

    private void initComponents() {

        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(200, 80);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(158, 158, 158));

        //adding window Icon
        ImageIcon windowIcon = new ImageIcon(MedicineList.class.getResource("medbg.png"));
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
        panel.setBounds(0, 0, 900, 600);
        panel.setBackground(new Color(51, 153, 102));

        

        //calling scrollPane and adding JTable into it
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new CardLayout());
        tablePanel.setBounds(320, 40, 570, 440);
        tablePanel.setBackground(new Color(51, 153, 102));
        JScrollPane scrollPane = new JScrollPane();
        JTable dataTable = new JTable();

        dataTable.setModel(new DefaultTableModel(
                new Object[][]{
                    {null, null},
                    {null, null}

                },
                null
        ));
        scrollPane.setViewportView(dataTable);
        tablePanel.add(scrollPane);
        panel.add(tablePanel);
        
        
        //adding Sign Out button
        JButton mSignOut = new JButton();
        mSignOut.setText("Sign Out");
        mSignOut.setBounds(790, 3, 80, 38);
        panel.add(mSignOut);

        mSignOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst.close();
                    rs.close();
                    conn.close();
                    new LoginFrame().setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Database not connected or\n" + ex);
                }
            }
        });

        
        //adding Back button
        JButton backButton = new JButton();
        backButton.setBounds(20, 500, 50, 50);
        backButton.setIcon(new ImageIcon(MedicineList.class.getResource("ic_back.png")));
        panel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mEnable == true){
                    new MenuFrame2(conn, pst, rs,true).setVisible(true);
                    dispose();
                }
                else if (mEnable == false){
                    new MenuFrame(conn, pst, rs,false).setVisible(true);
                    dispose();
                }
            }
        });

        
        
        
        
        //adding Load Button
        JButton loadBtn = new JButton();
        loadBtn.setText("Load");
        loadBtn.setBounds(805, 493, 75, 70);
        loadBtn.setIcon(new ImageIcon(SalesFrame.class.getResource("ic_load.png")));
        loadBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        loadBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel.add(loadBtn);

        loadBtn.addActionListener((ActionEvent e) -> {
            try {
                String sql = "select * from Sale_list";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                dataTable.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception ev) {
                JOptionPane.showMessageDialog(null, "Database not connected or\n" + ev);
            }
        });

        
        //adding Background Image into panel
        ImageIcon img = new ImageIcon(SalesFrame.class.getResource("ic_useb.png"));
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0, 0, 900, 600);
        panel.add(imgLabel);
    }
}
