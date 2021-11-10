package view;

import control.ClientCtr;
import model.ObjectWrapper;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpFrm extends JFrame implements ActionListener{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnSignUp;
    private ClientCtr myControl;

    public SignUpFrm(ClientCtr socket){
        super("Sign up");
        myControl = socket;

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnSignUp = new JButton("Sign up");

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Username:"));
        content.add(txtUsername);
        content.add(new JLabel("Password:"));
        content.add(txtPassword);
        content.add(btnSignUp);
        btnSignUp.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SIGNUP_USER,this));
    }


    public void actionPerformed(ActionEvent e) {
        if((e.getSource() instanceof JButton) && (((JButton)e.getSource()).equals(btnSignUp))) {
            //pack the entity
            Player player = new Player();
            player.setUsername(txtUsername.getText());
            player.setPassword(txtPassword.getText());

            //sending data
            myControl.sendData(new ObjectWrapper(ObjectWrapper.SIGNUP_USER, player));
        }
    }


    public void receivedDataProcessing(ObjectWrapper data){
        if(data.getData() instanceof Player) {
            Player player = (Player) data.getData();
            new MainFrm(player,myControl).setVisible(true);
            this.dispose();
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Username already exist");
        }
    }
}