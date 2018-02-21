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
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author hp
 */
public class MenuFrame extends JFrame {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pst;
    private boolean disable;

    public MenuFrame() {
        super("MENU");
        initComponents();
    }

    public MenuFrame(Connection mConnect, PreparedStatement pStatement, ResultSet rSet) {
        super("MENU");
        this.conn = mConnect;
        this.pst = pStatement;
        this.rs = rSet;
        initComponents();
    }
    
    public MenuFrame(Connection mConnect, PreparedStatement pStatement, ResultSet rSet, boolean disable) {
        super("MENU");
        this.conn = mConnect;
        this.pst = pStatement;
        this.rs = rSet;
        this.disable = disable;
        initComponents();
    }

    public static void main(String[] args) {
        WebLookAndFeel.install();

        invokeLater(() -> {
            new MenuFrame().setVisible(true);
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
        ImageIcon windowIcon = new ImageIcon(MenuFrame.class.getResource("medbg.png"));
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

        //adding Sign Out button
        JButton mSignOut = new JButton();
        mSignOut.setText("Sign Out");
        mSignOut.setBounds(790, 10, 80, 40);
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

        //Menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBorder(BorderFactory.createBevelBorder(0, new Color(51, 153, 102), new Color(0, 0, 0)));
        menuPanel.setBounds(420, 60, 450, 450);
        menuPanel.setBackground(new Color(31, 143, 102, 50));
        panel.add(menuPanel);

        //adding User Button
        JButton userBtn = new JButton();
        userBtn.setBackground(new Color(51, 153, 102));
        userBtn.setOpaque(true);
        userBtn.setBounds(80, 20, 127, 127);
        userBtn.setIcon(new ImageIcon(UserInfo.class.getResource("user.png")));
        userBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        userBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        menuPanel.add(userBtn);

        userBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserInfo(conn, pst,rs).setVisible(true);
                dispose();
            }
        });

        ImageIcon userImg = new ImageIcon(MenuFrame.class.getResource("user_text.png"));
        JLabel userLabel = new JLabel(userImg);
        userLabel.setBounds(240, 70, 160, 60);
        menuPanel.add(userLabel);

        //adding Medicine Button
        JButton medBtn = new JButton();
        medBtn.setBackground(new Color(51, 153, 102));
        medBtn.setOpaque(true);
        medBtn.setBounds(80, 160, 127, 127);
        medBtn.setIcon(new ImageIcon(UserInfo.class.getResource("medicine.png")));
        medBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        medBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        menuPanel.add(medBtn);

        medBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MedicineList(conn, pst,rs,false).setVisible(true);
                dispose();
            }
        });

        ImageIcon medImg = new ImageIcon(MenuFrame.class.getResource("med_text.png"));
        JLabel medLabel = new JLabel(medImg);
        medLabel.setBounds(240, 200, 160, 60);
        menuPanel.add(medLabel);

        //adding Sales Button
        JButton salBtn = new JButton();
        salBtn.setBackground(new Color(51, 153, 102));
        salBtn.setOpaque(true);
        salBtn.setBounds(80, 300, 127, 127);
        salBtn.setIcon(new ImageIcon(UserInfo.class.getResource("sales.png")));
        salBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        salBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        menuPanel.add(salBtn);

        salBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SalesFrame(conn, pst,rs,false).setVisible(true);
                dispose();
            }
        });

        ImageIcon salesImg = new ImageIcon(MenuFrame.class.getResource("sales_text.png"));
        JLabel salLabel = new JLabel(salesImg);
        salLabel.setBounds(240, 330, 160, 60);
        menuPanel.add(salLabel);

        //adding Background Image into panel
        ImageIcon img2 = new ImageIcon(UserInfo.class.getResource("medbg.png"));
        JLabel imgLabel2 = new JLabel(img2);
        imgLabel2.setBounds(70, 270, 256, 256);
        panel.add(imgLabel2);

        //adding Background Image into panel
        ImageIcon img = new ImageIcon(UserInfo.class.getResource("menu.png"));
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(-50, -220, 900, 600);
        panel.add(imgLabel);
    }

}
