package view;

import control.ClientCtr;
import model.ObjectWrapper;
import model.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Administrator
 */
public class FriendFrm extends javax.swing.JFrame {
    private ClientCtr myControl;
    private Player player;
    private ArrayList<Player> friendList;
    private ArrayList<Player> requestList;
    private Player other;

    /**
     * Creates new form FriendFrm
     */
    public FriendFrm(ClientCtr socket, Player p) {
        myControl = socket;
        player = p;
        initComponents();

        myControl.sendData(new ObjectWrapper(ObjectWrapper.FRIEND_LIST,player));

        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_FRIEND_LIST,this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_FRIEND_REQUEST, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_FRIEND, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_FRIEND,this));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblfriendlist = new javax.swing.JTable();
        btnback = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblrequestlist = new javax.swing.JTable();
        btnacp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnref = new javax.swing.JButton();
        btndel = new javax.swing.JButton();
        btninf = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblfriendlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int column = tblfriendlist.getColumnModel().getColumnIndexAtX(e.getX()); // get the column of the button
                int row = e.getY() / tblfriendlist.getRowHeight(); // get the row of the button

                if (row < tblfriendlist.getRowCount() && row >= 0 && column < tblfriendlist.getColumnCount() && column >= 0) {
                    other = friendList.get(row);
                }
            }
        });

        jScrollPane3.setViewportView(tblfriendlist);

        btndel.setText("Delete");
        btndel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelActionPerformed(evt);
            }
        });

        btnback.setText("Back");
        btnback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbackActionPerformed(evt);
            }
        });

        btninf.setText("Information");
        btninf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninfActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Friends");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(173, 173, 173)
                                                .addComponent(jLabel4))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(150, 150, 150)
                                                .addComponent(btnback, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(154, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btninf, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btndel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(153, 153, 153))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(btninf)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btndel)
                                .addGap(9, 9, 9)
                                .addComponent(btnback)
                                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Friends", jPanel1);

        tblrequestlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int column = tblrequestlist.getColumnModel().getColumnIndexAtX(e.getX()); // get the column of the button
                int row = e.getY() / tblrequestlist.getRowHeight(); // get the row of the button
                //for(int i = 0;i<3;i++){
                other = requestList.get(row);
                //}
                System.out.println("click");
            }
        });

        jScrollPane4.setViewportView(tblrequestlist);

        btnacp.setText("Accept");
        btnacp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnacpActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Requests");

        btnref.setText("Refuse");
        btnref.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefActionPerformed(evt);
            }
        });

        jPanel3.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel3ComponentShown(evt);
            }
        });

        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(150, 150, 150)
                                                .addComponent(btnacp, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(173, 173, 173)
                                                .addComponent(jLabel5))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(150, 150, 150)
                                                .addComponent(btnref, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(btnacp)
                                .addGap(18, 18, 18)
                                .addComponent(btnref)
                                .addGap(26, 26, 26))
        );

        jTabbedPane1.addTab("Requests", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );

        pack();
    }

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.dispose();
    }

    private void btnacpActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        ArrayList<Player> befriend = new ArrayList<>();
        befriend.add(other);
        System.out.println(other);
        befriend.add(player);
        myControl.sendData(new ObjectWrapper(ObjectWrapper.ACCEPT_FRIEND, befriend));
        this.dispose();
    }

    private void btnrefActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        ArrayList<Player> befriend = new ArrayList<>();
        befriend.add(other);
        befriend.add(player);
        befriend.add(new Player());
        myControl.sendData(new ObjectWrapper(ObjectWrapper.ACCEPT_FRIEND, befriend));
        this.dispose();
    }

    private void jPanel3ComponentShown(java.awt.event.ComponentEvent evt) {
        // TODO add your handling code here:
        myControl.sendData(new ObjectWrapper(ObjectWrapper.FRIEND_REQUEST, player));
    }

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {
        // TODO add your handling code here:
        myControl.sendData(new ObjectWrapper(ObjectWrapper.FRIEND_LIST,player));
    }

    private void btndelActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        ArrayList<Player> bf = new ArrayList<>();
        bf.add(player);
        bf.add(other);
        myControl.sendData(new ObjectWrapper(ObjectWrapper.DELETE_FRIEND,bf));
    }

    private void btninfActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        ObjectWrapper existed = null;
        for (ObjectWrapper func : myControl.getActiveFunction())
            if (func.getData() instanceof PlayerInfoFrm) {
                ((PlayerInfoFrm) func.getData()).dispose();
                existed = func;
            }
        if (existed != null)
            myControl.getActiveFunction().remove(existed);
        (new PlayerInfoFrm(myControl, player, other)).setVisible(true);
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnacp;
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnref;
    private javax.swing.JButton btndel;
    private javax.swing.JButton btninf;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblfriendlist;
    private javax.swing.JTable tblrequestlist;

    // End of variables declaration

    public void receivedDataProcessing(ObjectWrapper data) {
        switch (data.getPerformative()) {
            case ObjectWrapper.REPLY_FRIEND_LIST:
                friendList = (ArrayList<Player>) data.getData();
                String[] columnNamesFriend = {"username", "status", "wins", "loses"};
                String[][] valueFriend = new String[friendList.size()][columnNamesFriend.length];
                for (int i = 0; i < friendList.size(); i++) {
                    valueFriend[i][0] = friendList.get(i).getUsername();
                    valueFriend[i][1] = friendList.get(i).getStatus();
                    valueFriend[i][2] = friendList.get(i).getWins() + "";
                    valueFriend[i][3] = friendList.get(i).getLoses() + "";
                }

                DefaultTableModel tableFriendModel = new DefaultTableModel(valueFriend, columnNamesFriend) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                tblfriendlist.setModel(tableFriendModel);
                break;

            case ObjectWrapper.REPLY_FRIEND_REQUEST:
                requestList = (ArrayList<Player>) data.getData();
                String[] columnNames = {"id", "username", "status", "wins", "loses"};
                String[][] value = new String[requestList.size()][columnNames.length];
                for (int i = 0; i < requestList.size(); i++) {
                    value[i][0] = requestList.get(i).getId() + "";
                    value[i][1] = requestList.get(i).getUsername();
                    value[i][2] = requestList.get(i).getStatus();
                    value[i][3] = requestList.get(i).getWins() + "";
                    value[i][4] = requestList.get(i).getLoses() + "";
                }
                DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                tblrequestlist.setModel(tableModel);
                break;

            case ObjectWrapper.REPLY_ACCEPT_FRIEND:
                if (data.getData().equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Accept");
                } else JOptionPane.showMessageDialog(rootPane, "Refuse");
                myControl.sendData(new ObjectWrapper(ObjectWrapper.FRIEND_LIST, player));
                break;
            case ObjectWrapper.REPLY_DELETE_FRIEND:
                if (data.getData().equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Delete");
                } else JOptionPane.showMessageDialog(rootPane, "Error");
                myControl.sendData(new ObjectWrapper(ObjectWrapper.FRIEND_LIST, player));
                break;
        }
    }
}
