package view;

import control.ClientCtr;
import model.IPAddress;
import model.ObjectWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientMainFrm extends JFrame implements ActionListener{
    private JMenuBar mnbMain;
    private JMenu mnUser;
    private JMenuItem mniLogin, mniSignUp;

    private JTextField txtServerHost;
    private JTextField txtServerPort;
    private JButton btnConnect;
    private JButton btnDisconnect;
    private JTextArea mainText;
    private ClientCtr myControl;

    public ClientMainFrm(){
        super("Client view");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        mnbMain = new JMenuBar();
        mnUser = new JMenu("User");
        mniLogin = new JMenuItem("Login");
        mniLogin.addActionListener(this);
        mniSignUp = new JMenuItem("Sign up");
        mniSignUp.addActionListener(this);
        mnUser.add(mniLogin);
        mnUser.add(mniSignUp);
        mnbMain.add(mnUser);

        this.setJMenuBar(mnbMain);
        mniLogin.setEnabled(false);
        mniSignUp.setEnabled(false);

        JLabel lblTitle = new JLabel("Client TCP/IP");
        lblTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lblTitle.setBounds(new Rectangle(150, 20, 200, 30));
        mainPanel.add(lblTitle, null);

        JLabel lblHost = new JLabel("Server host:");
        lblHost.setBounds(new Rectangle(10, 70, 150, 25));
        mainPanel.add(lblHost, null);

        txtServerHost = new JTextField(50);
        txtServerHost.setBounds(new Rectangle(170, 70, 150, 25));
        mainPanel.add(txtServerHost,null);

        JLabel lblPort = new JLabel("Server port:");
        lblPort.setBounds(new Rectangle(10, 100, 150, 25));
        mainPanel.add(lblPort, null);

        txtServerPort = new JTextField(50);
        txtServerPort.setBounds(new Rectangle(170, 100, 150, 25));
        mainPanel.add(txtServerPort,null);

        btnConnect = new JButton("Connect");
        btnConnect.setBounds(new Rectangle(10, 150, 150, 25));
        btnConnect.addActionListener(this);
        mainPanel.add(btnConnect,null);

        btnDisconnect = new JButton("Disconnect");
        btnDisconnect.setBounds(new Rectangle(170, 150, 150, 25));
        btnDisconnect.addActionListener(this);
        btnDisconnect.setEnabled(false);
        mainPanel.add(btnDisconnect,null);


        JScrollPane jScrollPane1 = new JScrollPane();
        mainText = new JTextArea("");
        jScrollPane1.setBounds(new Rectangle(10, 200, 610, 240));
        mainPanel.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mainText, null);
        //mainPanel.add(mainText,null);

        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(new Dimension(640, 480));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener( new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                if(myControl != null) {
                    myControl.closeConnection();
                }
                System.exit(0);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        // TODO Auto-generated method stub
        if(ae.getSource() instanceof JButton) {
            JButton btn = (JButton)ae.getSource();
            if(btn.equals(btnConnect)) {// connect button
                if(!txtServerHost.getText().isEmpty() && (txtServerHost.getText().trim().length() > 0) &&
                        !txtServerPort.getText().isEmpty() && (txtServerPort.getText().trim().length() > 0)) {//custom port
                    int port = Integer.parseInt(txtServerPort.getText().trim());
                    myControl = new ClientCtr(this, new IPAddress(txtServerHost.getText().trim(), port));
                    // new ClientCtr(this, port);
                }else {
                    myControl = new ClientCtr(this);
                }
                if(myControl.openConnection()) {
                    btnDisconnect.setEnabled(true);
                    btnConnect.setEnabled(false);
                    mniLogin.setEnabled(true);
                    mniSignUp.setEnabled(true);
                }else {
                    resetClient();
                }
            }else if(btn.equals(btnDisconnect)) {// disconnect button
                resetClient();
            }
        }else if(ae.getSource() instanceof JMenuItem) {
            JMenuItem mni = (JMenuItem)ae.getSource();
            if(mni.equals(mniLogin)) {// login function
                LoginFrm lf = new LoginFrm(myControl);
                lf.setVisible(true);
            }else if (mni.equals(mniSignUp)){
                SignUpFrm suf = new SignUpFrm(myControl);
                suf.setVisible(true);
            }
        }
    }

    public void showMessage(String s){
        mainText.append("\n"+s);
        mainText.setCaretPosition(mainText.getDocument().getLength());
    }

    public void resetClient() {
        if(myControl != null) {
            myControl.closeConnection();
            myControl.getActiveFunction().clear();
            myControl = null;
        }
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
        mniLogin.setEnabled(false);
    }

    public static void main(String[] args) {
        ClientMainFrm view = new ClientMainFrm();
        view.setVisible(true);
    }
}