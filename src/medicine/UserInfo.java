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
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
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
import net.proteanit.sql.DbUtils;

/**
 *
 * @author hp
 */
public class UserInfo extends JFrame {

    //decleare global variable
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pst;

    ImageIcon[] images;
    String[] icoStrings = {"Add", "Delete", "Update"};

    public UserInfo() {
        super("User Information");
        initComponents();
    }

    public UserInfo(Connection mConnect, PreparedStatement pStatement, ResultSet rSet) {
        super("User Information");
        this.conn = mConnect;
        this.pst = pStatement;
        this.rs = rSet;
        initComponents();
    }

    public static void main(String[] args) {
        WebLookAndFeel.install();

        invokeLater(() -> {
            new UserInfo().setVisible(true);
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
        ImageIcon windowIcon = new ImageIcon(UserInfo.class.getResource("medbg.png"));
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

        //adding icon/strings into menu comboBox
        images = new ImageIcon[icoStrings.length];
        Integer[] intArray = new Integer[icoStrings.length];
        for (int i = 0; i < icoStrings.length; i++) {
            intArray[i] = new Integer(i);
            images[i] = createImageIcon("" + icoStrings[i] + ".png");
            if (images[i] != null) {
                images[i].setDescription(icoStrings[i]);
            }
        }

        //calling scrollPane and adding JTable into it
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(350, 40, 600, 440);
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

        //adding CardLayout
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new CardLayout());
        headerPanel.setBounds(35, 60, 310, 350);
        panel.add(headerPanel);

        JLabel headerLabel = new JLabel("Add New USER");
        headerLabel.setBounds(80, 20, 150, 40);
        headerLabel.setForeground(new Color(240, 240, 240));
        panel.add(headerLabel);

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

        //Add New User Entry panel
        JPanel createPanel = new JPanel();
        createPanel.setLayout(null);
        createPanel.setBorder(BorderFactory.createBevelBorder(0, new Color(51, 153, 102), new Color(31, 143, 102)));
        createPanel.setBounds(30, 60, 310, 340);
        createPanel.setBackground(new Color(31, 143, 102));
        headerPanel.add(createPanel);
        createPanel.setVisible(true);

        JLabel Label1 = new JLabel("Employee ID");
        Label1.setBounds(30, 20, 150, 40);
        Label1.setForeground(Color.WHITE);
        createPanel.add(Label1);

        JTextField employeeText = new JTextField();
        employeeText.setBounds(150, 20, 130, 40);
        createPanel.add(employeeText);

        JLabel Label2 = new JLabel("Name");
        Label2.setBounds(30, 70, 150, 40);
        Label2.setForeground(Color.WHITE);
        createPanel.add(Label2);

        JTextField nameText = new JTextField();
        nameText.setBounds(150, 70, 130, 40);
        createPanel.add(nameText);

        JLabel Label3 = new JLabel("Age");
        Label3.setBounds(30, 120, 150, 40);
        Label3.setForeground(Color.WHITE);
        createPanel.add(Label3);

        JTextField ageText = new JTextField();
        ageText.setBounds(150, 120, 130, 40);
        createPanel.add(ageText);

        JLabel Label4 = new JLabel("Username");
        Label4.setBounds(30, 170, 150, 40);
        Label4.setForeground(Color.WHITE);
        createPanel.add(Label4);

        JTextField userText = new JTextField();
        userText.setBounds(150, 170, 130, 40);
        createPanel.add(userText);

        JLabel Label5 = new JLabel("Password");
        Label5.setBounds(30, 220, 150, 40);
        Label5.setForeground(Color.WHITE);
        createPanel.add(Label5);

        JTextField passText = new JTextField();
        passText.setBounds(150, 220, 130, 40);
        createPanel.add(passText);

        //adding Create Button
        JButton createButton = new JButton("Create");
        createButton.setBounds(150, 280, 80, 40);
        createPanel.add(createButton);

        createButton.addActionListener((ActionEvent e) -> {
            try {
                if(employeeText.getText().isEmpty() 
                        && nameText.getText().isEmpty() 
                        && ageText.getText().isEmpty()
                        && userText.getText().isEmpty()
                        && passText.getText().isEmpty()
                        ){
                    JOptionPane.showMessageDialog(null, "Please Insert Data");
                }
                else{
                    String sql = "insert into EmployeeInfo (employee_id,name,age,username,password) values (?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, employeeText.getText());
                    pst.setString(2, nameText.getText());
                    pst.setString(3, ageText.getText());
                    pst.setString(4, userText.getText());
                    pst.setString(5, passText.getText());

                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Data Created");
                }
            } catch (Exception ev) {
                JOptionPane.showMessageDialog(null, "Database not connected or\n" + ev);
            }
        });

        //Delete panel
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(null);
        deletePanel.setBorder(BorderFactory.createBevelBorder(0, new Color(51, 153, 102), new Color(31, 143, 102)));
        deletePanel.setBounds(30, 60, 310, 340);
        deletePanel.setBackground(new Color(31, 143, 102));

        JLabel dLabel = new JLabel("Delete by");
        dLabel.setBounds(30, 20, 150, 40);
        dLabel.setForeground(Color.WHITE);
        deletePanel.add(dLabel);

        JComboBox mDeleteBox = new JComboBox();
        mDeleteBox.setModel(
                new DefaultComboBoxModel<>(
                        new String[]{
                            "employee_id",
                            "name"
                        }
                ));
        mDeleteBox.setBounds(150, 20, 130, 40);
        deletePanel.add(mDeleteBox);

        JLabel dLabel2 = new JLabel("Employee ID");
        dLabel2.setBounds(30, 70, 150, 40);
        dLabel2.setForeground(Color.WHITE);
        deletePanel.add(dLabel2);

        mDeleteBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (mDeleteBox.getSelectedIndex()) {
                    case 0:
                        dLabel2.setText("Employee ID");
                        break;
                    case 1:
                        dLabel2.setText("Employee Name");
                        break;
                }
            }
        });

        JTextField deText = new JTextField();
        deText.setBounds(150, 70, 130, 40);
        deletePanel.add(deText);

        //adding Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(150, 120, 80, 40);
        deletePanel.add(deleteButton);

        deleteButton.addActionListener((ActionEvent e) -> {
            try {
                if(deText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please Insert Data");
                }
                else{
                    String selection = (String) mDeleteBox.getSelectedItem();
                    String sql = "delete from EmployeeInfo where " + selection + "='" + deText.getText() + "' ";
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Data Deleted");
                }
            } catch (Exception ev) {
                JOptionPane.showMessageDialog(null, "Database not connected or\n" + ev);
            }
        });

        //Update panel
        JPanel upPanel = new JPanel();
        upPanel.setLayout(null);
        upPanel.setBorder(BorderFactory.createBevelBorder(0, new Color(51, 153, 102), new Color(31, 143, 102)));
        upPanel.setBounds(30, 60, 310, 340);
        upPanel.setBackground(new Color(31, 143, 102));

        JLabel uLabel1 = new JLabel("Employee ID");
        uLabel1.setBounds(30, 20, 150, 40);
        uLabel1.setForeground(Color.WHITE);
        upPanel.add(uLabel1);

        //Retreive Employee Id Column from database into JComboBox
        JComboBox uCombo = new JComboBox();
        uCombo.setBounds(150, 20, 150, 40);
        uCombo.setForeground(Color.DARK_GRAY);
        uCombo.addItem("  Select ID");
        upPanel.add(uCombo);

        //Retreive id
        try {
            String sql = "select employee_id from EmployeeInfo";
            Statement statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("employee_id");
                uCombo.addItem("  " + id);
            }
            statement.close();
        } catch (Exception ex) {
            uCombo.addItem("  No Data");
        }

        JLabel uLabel2 = new JLabel("Name");
        uLabel2.setBounds(30, 70, 150, 40);
        uLabel2.setForeground(Color.WHITE);
        upPanel.add(uLabel2);

        JTextField upNMText = new JTextField();
        upNMText.setBounds(150, 70, 130, 40);
        upPanel.add(upNMText);

        JLabel uLabel3 = new JLabel("Age");
        uLabel3.setBounds(30, 120, 150, 40);
        uLabel3.setForeground(Color.WHITE);
        upPanel.add(uLabel3);

        JTextField upAGText = new JTextField();
        upAGText.setBounds(150, 120, 130, 40);
        upPanel.add(upAGText);

        JLabel uLabel4 = new JLabel("Username");
        uLabel4.setBounds(30, 170, 150, 40);
        uLabel4.setForeground(Color.WHITE);
        upPanel.add(uLabel4);

        JTextField upUSText = new JTextField();
        upUSText.setBounds(150, 170, 130, 40);
        upPanel.add(upUSText);

        JLabel uLabel5 = new JLabel("Password");
        uLabel5.setBounds(30, 220, 150, 40);
        uLabel5.setForeground(Color.WHITE);
        upPanel.add(uLabel5);

        JTextField upPSText = new JTextField();
        upPSText.setBounds(150, 220, 130, 40);
        upPanel.add(upPSText);

        //JComboBox item selected Listener
        uCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = (String) uCombo.getSelectedItem();
                try {
                    String sql = "select name,age,username,password from EmployeeInfo where employee_id =" + item;
                    Statement statement = conn.createStatement();
                    rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        String name = rs.getString("name");
                        upNMText.setText("" + name);
                        String age = rs.getString("age");
                        upAGText.setText("" + age);
                        String username = rs.getString("username");
                        upUSText.setText("" + username);
                        String password = rs.getString("password");
                        upPSText.setText("" + password);
                    }
                } catch (Exception ex) {
                    upNMText.setText("");
                    upAGText.setText("");
                    upUSText.setText("");
                    upPSText.setText("");
                }
            }
        });

        //adding Create Button
        JButton upButton = new JButton("Update");
        upButton.setBounds(150, 280, 80, 40);
        upPanel.add(upButton);

        upButton.addActionListener((ActionEvent e) -> {
            try {
                if(upNMText.getText().isEmpty() 
                        && upAGText.getText().isEmpty() 
                        && upUSText.getText().isEmpty()
                        && upPSText.getText().isEmpty()
                        ){
                    JOptionPane.showMessageDialog(null, "Please Insert Data");
                }
                else{
                    String item = (String) uCombo.getSelectedItem();
                    String sql = "update EmployeeInfo set name='"
                            + upNMText.getText() + "' ,age='"
                            + upAGText.getText() + "', username='"
                            + upUSText.getText() + "', password='"
                            + upPSText.getText() + "' where employee_id='"
                            + item + "' ";
                    pst = conn.prepareStatement(sql);

                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Data Updated");
                }
            } catch (SQLException ev) {
                JOptionPane.showMessageDialog(null, "Database not connected or\n" + ev);
            }
        });

        //adding Refresh Button
        JButton refreshBtn = new JButton();
        refreshBtn.setText("Refresh");
        refreshBtn.setBounds(725, 493, 75, 70);
        refreshBtn.setIcon(new ImageIcon(UserInfo.class.getResource("ic_refresh.png")));
        refreshBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        refreshBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        refreshBtn.setVisible(false);
        panel.add(refreshBtn);

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserInfo(conn,pst,rs).setVisible(true);
                dispose();
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
                new MenuFrame(conn, pst, rs).setVisible(true);
                dispose();
            }
        });

        //adding Load Button
        JButton loadBtn = new JButton();
        loadBtn.setText("Load");
        loadBtn.setBounds(805, 493, 75, 70);
        loadBtn.setIcon(new ImageIcon(UserInfo.class.getResource("ic_load.png")));
        loadBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        loadBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel.add(loadBtn);

        loadBtn.addActionListener((ActionEvent e) -> {
            try {
                String sql = "select * from EmployeeInfo";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                dataTable.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception ev) {
                JOptionPane.showMessageDialog(null, "Database not connected or\n" + ev);
            }
        });

        //creating ComboBox and MenuLabel
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setBounds(370, 510, 80, 40);
        menuLabel.setForeground(new Color(240, 240, 240));
        panel.add(menuLabel);

        JComboBox icoList = new JComboBox(intArray);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        renderer.setPreferredSize(new Dimension(100, 40));
        icoList.setRenderer(renderer);
        icoList.setMaximumRowCount(3);
        icoList.setBounds(420, 510, 150, 40);
        panel.add(icoList);

        icoList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (icoList.getSelectedIndex()) {
                    case 0:
                        refreshBtn.setVisible(false);
                        headerLabel.setText("Add New USER");
                        headerPanel.removeAll();
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        headerPanel.add(createPanel);
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        break;
                    case 1:
                        refreshBtn.setVisible(false);
                        headerLabel.setText("Delete USER");
                        headerPanel.removeAll();
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        headerPanel.add(deletePanel);
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        break;
                    case 2:
                        refreshBtn.setVisible(true);
                        headerLabel.setText("Update USER");
                        headerPanel.removeAll();
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        headerPanel.add(upPanel);
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        break;
                    default:
                        break;
                }
            }
        });

        //adding Background Image into panel
        ImageIcon userImage = new ImageIcon(UserInfo.class.getResource("ic_user.png"));
        JLabel imgLabel1 = new JLabel(userImage);
        imgLabel1.setBounds(30, 15, 40, 40);
        panel.add(imgLabel1);

        //adding Background Image into panel
        ImageIcon img = new ImageIcon(UserInfo.class.getResource("ic_useb.png"));
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0, 0, 900, 600);
        panel.add(imgLabel);
    }

    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = UserInfo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public class ComboBoxRenderer extends JLabel
            implements ListCellRenderer {

        private Font uhOhFont;

        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        /*
         * This method finds the image and text corresponding
         * to the selected value and returns the label, set up
         * to display the text and image.
         */
        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            //Get the selected index. (The index param isn't
            //always valid, so just use the value.)
            int selectedIndex = ((Integer) value).intValue();

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            //Set the icon and text.  If icon was null, say so.
            ImageIcon icon = images[selectedIndex];
            String ico = icoStrings[selectedIndex];
            setIcon(icon);
            if (icon != null) {
                setText(ico);
                setFont(list.getFont());
            } else {
                setUhOhText(ico + " (no image available)",
                        list.getFont());
            }

            return this;
        }

        //Set the font and text when no image was found.
        protected void setUhOhText(String uhOhText, Font normalFont) {
            if (uhOhFont == null) { //lazily create this font
                uhOhFont = normalFont.deriveFont(Font.ITALIC);
            }
            setFont(uhOhFont);
            setText(uhOhText);
        }
    }
}
