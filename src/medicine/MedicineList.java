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
public class MedicineList extends JFrame {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pst;
    private boolean mEnable;
    ImageIcon[] images;
    String[] icoStrings = {"Add", "Delete", "Update"};

    public MedicineList() {
        super("Medicine List");
        initComponents();
        //Fillcombo();
    }

    public MedicineList(Connection mConnect, PreparedStatement pStatement, ResultSet rSet) {
        super("Medicine List");
        this.conn = mConnect;
        this.pst = pStatement;
        this.rs = rSet;
        initComponents();
    }
    
    public MedicineList(Connection mConnect, PreparedStatement pStatement, ResultSet rSet, boolean mEnable) {
        super("Medicine List");
        this.conn = mConnect;
        this.pst = pStatement;
        this.rs = rSet;
        this.mEnable = mEnable;
        initComponents();
    }

    public static void main(String[] args) {
        WebLookAndFeel.install();

        invokeLater(() -> {
            new MedicineList().setVisible(true);
        });
    }

    private void initComponents() {

        setSize(1500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 80);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(51, 153, 102));

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
        panel.setBounds(0, 0, 1500, 600);
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
        tablePanel.setBounds(350, 40, 550, 440);
        tablePanel.setBackground(new Color(51, 153, 102));
        JScrollPane scrollPane = new JScrollPane();
        JTable dataTable = new JTable();

        dataTable.setModel(new DefaultTableModel(
                new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Company", "Type", "Place", "Price"
            }
        ));
        scrollPane.setViewportView(dataTable);
        tablePanel.add(scrollPane);
        panel.add(tablePanel);

        //creating 2nd table
        JPanel tablePanel2 = new JPanel();
        tablePanel2.setBounds(850, 40, 550, 440);
        tablePanel2.setBackground(new Color(51, 153, 102));
        JScrollPane scrollPane2 = new JScrollPane();
        JTable dataTable2 = new JTable();

        dataTable2.setModel(new DefaultTableModel(
                new Object [][] {
            },
            new String [] {
                "Id", "Name", "Company", "Type", "Place", "Price"
            }
        ));
        scrollPane2.setViewportView(dataTable2);
        tablePanel2.add(scrollPane2);
        panel.add(tablePanel2);
        
        //add Button
        JButton addBtn = new JButton();
        addBtn.setBounds(880, 450, 40, 40);
        addBtn.setIcon(new ImageIcon(MedicineList.class.getResource("Add.png")));
        addBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        addBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        tablePanel.add(addBtn);
        
        addBtn.addActionListener((ActionEvent e) -> {
            TableModel model1=dataTable.getModel();
            int[] indexs = dataTable.getSelectedRows();
            Object[] row = new Object[6];
            DefaultTableModel model2 = (DefaultTableModel) dataTable2.getModel();
            for(int i=0;i<indexs.length;i++){
                row[0]=model1.getValueAt(indexs[i], 0);
                row[1]=model1.getValueAt(indexs[i], 1);
                row[2]=model1.getValueAt(indexs[i], 2);
                row[3]=model1.getValueAt(indexs[i], 3);
                row[4]=model1.getValueAt(indexs[i], 4);
                row[5]=model1.getValueAt(indexs[i], 5);

                model2.addRow(row);

            }
        });
        
        //Save Button
        JButton saveBtn = new JButton();
        saveBtn.setBounds(1250, 493, 75, 70);
        saveBtn.setText("Save");
        saveBtn.setIcon(new ImageIcon(SalesFrame.class.getResource("ic_save.png")));
        saveBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        saveBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel.add(saveBtn);
        
        saveBtn.addActionListener((ActionEvent e) -> {
            TableModel model2=dataTable2.getModel();
            int[] indexs = dataTable2.getSelectedRows();
            try{
                for(int j=0;j<indexs.length;j++){
                    String row0=model2.getValueAt(indexs[j], 0).toString();
                    String row1=model2.getValueAt(indexs[j], 1).toString();
                    String row2=model2.getValueAt(indexs[j], 2).toString();
                    String row3=model2.getValueAt(indexs[j], 3).toString();
                    String row4=model2.getValueAt(indexs[j], 4).toString();
                    String row5=model2.getValueAt(indexs[j], 5).toString();

                    String sql="insert into Sale_list (Id,Name,Company,Type,Place,Price) values (?,?,?,?,?,?)";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1, row0);
                    pst.setString(2, row1);
                    pst.setString(3, row2);
                    pst.setString(4, row3);
                    pst.setString(5, row4);
                    pst.setString(6, row5);

                    pst.execute();
                }
                JOptionPane.showMessageDialog(null, "Data Saved");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ""+e);
            }
        });
        
        //adding CardLayout
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new CardLayout());
        headerPanel.setBounds(35, 60, 310, 380);
        headerPanel.setBackground(new Color(31, 143, 102, 50));
        panel.add(headerPanel);

        JLabel headerLabel = new JLabel("Add New Medicine");
        headerLabel.setBounds(80, 20, 150, 40);
        headerLabel.setForeground(new Color(240, 240, 240));
        panel.add(headerLabel);

        //adding Sign Out button
        JButton mSignOut = new JButton();
        mSignOut.setText("Sign Out");
        mSignOut.setBounds(1270, 3, 80, 38);
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
        createPanel.setBackground(new Color(31, 143, 102, 50));
        headerPanel.add(createPanel);
        createPanel.setVisible(true);

        JLabel Label1 = new JLabel("Medicine ID");
        Label1.setBounds(30, 20, 150, 40);
        Label1.setForeground(Color.WHITE);
        createPanel.add(Label1);

        JTextField medicineText = new JTextField();
        medicineText.setBounds(150, 20, 130, 40);
        createPanel.add(medicineText);

        JLabel Label2 = new JLabel("Name");
        Label2.setBounds(30, 70, 150, 40);
        Label2.setForeground(Color.WHITE);
        createPanel.add(Label2);

        JTextField nameText = new JTextField();
        nameText.setBounds(150, 70, 130, 40);
        createPanel.add(nameText);

        JLabel Label3 = new JLabel("Company");
        Label3.setBounds(30, 120, 150, 40);
        Label3.setForeground(Color.WHITE);
        createPanel.add(Label3);

        JTextField companyText = new JTextField();
        companyText.setBounds(150, 120, 130, 40);
        createPanel.add(companyText);

        JLabel Label4 = new JLabel("Type");
        Label4.setBounds(30, 170, 150, 40);
        Label4.setForeground(Color.WHITE);
        createPanel.add(Label4);

        JTextField typeText = new JTextField();
        typeText.setBounds(150, 170, 130, 40);
        createPanel.add(typeText);

        JLabel Label5 = new JLabel("Place");
        Label5.setBounds(30, 220, 150, 40);
        Label5.setForeground(Color.WHITE);
        createPanel.add(Label5);

        JTextField placeText = new JTextField();
        placeText.setBounds(150, 220, 130, 40);
        createPanel.add(placeText);

        JLabel Label6 = new JLabel("Price");
        Label6.setBounds(30, 270, 150, 40);
        Label6.setForeground(Color.WHITE);
        createPanel.add(Label6);

        JTextField priceText = new JTextField();
        priceText.setBounds(150, 270, 130, 40);
        createPanel.add(priceText);

        //adding Create Button
        JButton createButton = new JButton("Add");
        createButton.setBounds(150, 320, 80, 40);
        createPanel.add(createButton);

        createButton.addActionListener((ActionEvent e) -> {
            try {
                if(medicineText.getText().isEmpty() 
                        && nameText.getText().isEmpty() 
                        && companyText.getText().isEmpty()
                        && typeText.getText().isEmpty()
                        && placeText.getText().isEmpty()
                        && priceText.getText().isEmpty()
                        ){
                    JOptionPane.showMessageDialog(null, "Please Insert Data");
                }
                else{
                    String sql = "insert into Medicine (Id,Name,Company,Type,Place,Price) values (?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, medicineText.getText());
                    pst.setString(2, nameText.getText());
                    pst.setString(3, companyText.getText());
                    pst.setString(4, typeText.getText());
                    pst.setString(5, placeText.getText());
                    pst.setString(6, priceText.getText()); 

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
        deletePanel.setBounds(30, 60, 310, 280);
        deletePanel.setBackground(new Color(31, 143, 102, 200));

        JLabel dLabel = new JLabel("Delete by");
        dLabel.setBounds(30, 20, 150, 40);
        dLabel.setForeground(Color.WHITE);
        deletePanel.add(dLabel);

        JComboBox mDeleteBox = new JComboBox();
        mDeleteBox.setModel(
                new DefaultComboBoxModel<>(
                        new String[]{
                            "Id",
                            "Name"
                        }
                ));
        mDeleteBox.setBounds(150, 20, 130, 40);
        deletePanel.add(mDeleteBox);

        JLabel dLabel2 = new JLabel("Medicine ID");
        dLabel2.setBounds(30, 70, 150, 40);
        dLabel2.setBackground(new Color(31, 143, 102, 50));
        dLabel2.setForeground(Color.WHITE);
        deletePanel.add(dLabel2);

        mDeleteBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (mDeleteBox.getSelectedIndex()) {
                    case 0:
                        dLabel2.setText("Medicine ID");
                        break;
                    case 1:
                        dLabel2.setText("Medicine Name");
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
                    String sql = "delete from Medicine where " + selection + "='" + deText.getText() + "' ";
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
        upPanel.setBackground(new Color(31, 143, 102, 50));

        JLabel uLabel1 = new JLabel("Medicine ID");
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
            String sql = "select * from Medicine";
            Statement statement = conn.createStatement();
            //pst=conn.prepareStatement(sql);
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Id");
                uCombo.addItem(id);

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

        JLabel uLabel3 = new JLabel("Company");
        uLabel3.setBounds(30, 120, 150, 40);
        uLabel3.setForeground(Color.WHITE);
        upPanel.add(uLabel3);

        JTextField upAGText = new JTextField();
        upAGText.setBounds(150, 120, 130, 40);
        upPanel.add(upAGText);

        JLabel uLabel4 = new JLabel("Type");
        uLabel4.setBounds(30, 170, 150, 40);
        uLabel4.setForeground(Color.WHITE);
        upPanel.add(uLabel4);

        JTextField upUSText = new JTextField();
        upUSText.setBounds(150, 170, 130, 40);
        upPanel.add(upUSText);

        JLabel uLabel5 = new JLabel("Place");
        uLabel5.setBounds(30, 220, 150, 40);
        uLabel5.setForeground(Color.WHITE);
        upPanel.add(uLabel5);

        JTextField upPSText = new JTextField();
        upPSText.setBounds(150, 220, 130, 40);
        upPanel.add(upPSText);

        JLabel uLabel6 = new JLabel("Price");
        uLabel6.setBounds(30, 270, 150, 40);
        uLabel6.setForeground(Color.WHITE);
        upPanel.add(uLabel6);

        JTextField upPRText = new JTextField();
        upPRText.setBounds(150, 270, 130, 40);
        upPanel.add(upPRText);

        //JComboBox item selected Listener
        uCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = (String) uCombo.getSelectedItem();
                try {
                    String sql = "select Name,Company,Type,Place,Price from Medicine where Id =" + item;
                    Statement statement = conn.createStatement();
                    rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        String name = rs.getString("Name");
                        upNMText.setText("" + name);
                        String age = rs.getString("Company");
                        upAGText.setText("" + age);
                        String username = rs.getString("Type");
                        upUSText.setText("" + username);
                        String password = rs.getString("Place");
                        upPSText.setText("" + password);
                        String price = rs.getString("Price");
                        upPRText.setText("" + price);
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
        upButton.setBounds(150, 320, 80, 40);
        upPanel.add(upButton);

        upButton.addActionListener((ActionEvent e) -> {
            try {
                if(upNMText.getText().isEmpty() 
                        && upAGText.getText().isEmpty() 
                        && upUSText.getText().isEmpty()
                        && upPSText.getText().isEmpty()
                        && upPRText.getText().isEmpty()
                        ){
                        JOptionPane.showMessageDialog(null, "Please Insert Data");
                    }
                else{
                    String item = (String) uCombo.getSelectedItem();
                    String sql = "update Medicine set Name='"
                            + upNMText.getText() + "' ,Company='"
                            + upAGText.getText() + "', Type='"
                            + upUSText.getText() + "', Place='"
                            + upPSText.getText() + "',  Price='"
                            + upPRText.getText() + "' where id='"
                            + item + "' ";
                    pst = conn.prepareStatement(sql);

                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Data Updated");
                }
            } catch (Exception ev) {
                JOptionPane.showMessageDialog(null, "Database not connected or\n" + ev);
            }
        });

        //adding Refresh Button
        JButton refreshBtn = new JButton();
        refreshBtn.setText("Refresh");
        refreshBtn.setBounds(125, 450, 75, 70);
        refreshBtn.setIcon(new ImageIcon(UserInfo.class.getResource("ic_refresh.png")));
        refreshBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        refreshBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        refreshBtn.setVisible(false);
        panel.add(refreshBtn);

        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MedicineList(conn,pst,rs).setVisible(true);
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
        loadBtn.setIcon(new ImageIcon(UserInfo.class.getResource("ic_load.png")));
        loadBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        loadBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel.add(loadBtn);

        loadBtn.addActionListener((ActionEvent e) -> {
            try {
                String sql = "select * from Medicine";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                dataTable.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception ev) {
                JOptionPane.showMessageDialog(null, "Database not connected or\n" + ev);
            }
        });

        //creating ComboBox and MenuLabel
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setBounds(350, 510, 80, 40);
        menuLabel.setForeground(new Color(240, 240, 240));
        panel.add(menuLabel);

        JComboBox icoList = new JComboBox(intArray);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        renderer.setPreferredSize(new Dimension(100, 40));
        icoList.setRenderer(renderer);
        icoList.setMaximumRowCount(3);
        icoList.setBounds(400, 510, 150, 40);
        panel.add(icoList);

        icoList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (icoList.getSelectedIndex()) {
                    case 0:
                        refreshBtn.setVisible(false);
                        headerLabel.setText("Add New Medicine");
                        headerPanel.removeAll();
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        headerPanel.add(createPanel);
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        break;
                    case 1:
                        refreshBtn.setVisible(false);
                        headerLabel.setText("Delete Medicine");
                        headerPanel.removeAll();
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        headerPanel.add(deletePanel);
                        headerPanel.repaint();
                        headerPanel.revalidate();
                        break;
                    case 2:
                        refreshBtn.setVisible(true);
                        headerLabel.setText("Update Medicine");
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

        //adding searchField and searchBy ComboBox
        JPanel pCombo = new JPanel();
        pCombo.setBounds(620, 490, 150, 80);
        pCombo.setBackground(new Color(204, 204, 204));
        pCombo.setLayout(new BoxLayout(pCombo, BoxLayout.Y_AXIS));

        JLabel boxLabel = new JLabel();
        boxLabel.setText("Search");
        boxLabel.setForeground(new Color(240, 240, 240));
        boxLabel.setBounds(530, 510, 100, 40);
        boxLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JComboBox mComboBox = new JComboBox();
        mComboBox.setModel(
                new DefaultComboBoxModel<>(
                        new String[]{
                            "ID",
                            "Name",
                            "Company",
                            "Type",
                            "Place",
                            "Price"
                        }
                ));
        panel.add(boxLabel);
        pCombo.add(mComboBox);
        panel.add(pCombo);

        JTextField searchField = new JTextField();
        pCombo.add(searchField);

        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                try {
                    String selection = (String) mComboBox.getSelectedItem();
                    String SQL = "select * from Medicine where " + selection + "= ?";
                    pst = conn.prepareStatement(SQL);
                    pst.setString(1, searchField.getText());
                    rs = pst.executeQuery();
                    dataTable.setModel(DbUtils.resultSetToTableModel(rs));
                    pst.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });

        //adding Background Image into panel
        ImageIcon medImage = new ImageIcon(UserInfo.class.getResource("ic_med.png"));
        JLabel medLabel1 = new JLabel(medImage);
        medLabel1.setBounds(20, -5, 80, 80);
        panel.add(medLabel1);

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
