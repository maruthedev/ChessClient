package view;

import control.ClientCtr;
import model.ObjectWrapper;
import model.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class OnlinePlayerFrm extends JFrame implements ActionListener{
    private Player player;
    private ArrayList<Player> listPlayer;
    private JTextField txtKey;
    private JButton btnRefresh;
    private JTable tblResult;
    private ClientCtr myControl;
    private JTextArea mainText;

    public OnlinePlayerFrm(ClientCtr socket, Player p){
        super("Online Player");
        this.player = p;
        myControl = socket;
        listPlayer = new ArrayList<Player>();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));

        JLabel lblHome = new JLabel("Online Player");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0,20)));

        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1,BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width-5, 20);

        btnRefresh = new JButton("Online Player");
        btnRefresh.addActionListener(this);
        pn1.add(btnRefresh);
        pnMain.add(pn1);
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2,BoxLayout.Y_AXIS));
        tblResult = new JTable();
        JScrollPane scrollPane= new  JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 250));

        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the column of the button
                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button

                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    //search and delete all existing previous view
                    ObjectWrapper existed = null;
                    for(ObjectWrapper func: myControl.getActiveFunction())
                        if(func.getData() instanceof PlayerInfoFrm) {
                            ((PlayerInfoFrm)func.getData()).dispose();
                            existed = func;
                        }
                    if(existed != null)
                        myControl.getActiveFunction().remove(existed);

                    //create new instance
                    (new PlayerInfoFrm(myControl, player ,listPlayer.get(row))).setVisible(true);
                    //dispose();
                }
            }
        });
        pn2.add(scrollPane);


        JScrollPane jScrollPane1 = new JScrollPane();
        mainText = new JTextArea("");
        jScrollPane1.setBounds(new Rectangle(10, 100, 610, 400));
        pn2.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mainText, null);


        pnMain.add(pn2);
        this.add(pnMain);
        this.setSize(600,300);
        this.setLocation(200,10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ONLINE_PLAYER, this));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton)e.getSource();
        if(btnClicked.equals(btnRefresh)){
            myControl.sendData(new ObjectWrapper(ObjectWrapper.ONLINE_PLAYER,player));
        }
    }

    /**
     * Treatment of search result received from the server
     * @param data
     */
    public void receivedDataProcessing(ObjectWrapper data) {
        if(data.getData() instanceof ArrayList<?>) {
            listPlayer = (ArrayList<Player>)data.getData();

            String[] columnNames = {"id","username","status","wins","loses"};
            String[][] value = new String[listPlayer.size()][columnNames.length];
            for(int i=0; i<listPlayer.size(); i++){
                value[i][0] = listPlayer.get(i).getId() +"";
                value[i][1] = listPlayer.get(i).getUsername();
                value[i][2] = listPlayer.get(i).getStatus();
                value[i][3] = listPlayer.get(i).getWins() + "";
                value[i][4] = listPlayer.get(i).getLoses() + "";
            }
            DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tblResult.setModel(tableModel);
        }else {
            tblResult.setModel(null);
        }
    }
}