package control;

import model.IPAddress;
import model.ObjectWrapper;
import sun.applet.Main;
import view.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ClientCtr {
    private Socket mySocket;
    private ClientMainFrm view;
    private ClientListening myListening;                            // thread to listen the data from the server
    private ArrayList<ObjectWrapper> myFunction;                  // list of active client functions
    private IPAddress serverAddress = new IPAddress("localhost", 8888);  // default server host and port

    public ClientCtr(ClientMainFrm view) {
        super();
        this.view = view;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public ClientCtr(ClientMainFrm view, IPAddress serverAddr) {
        super();
        this.view = view;
        this.serverAddress = serverAddr;
        myFunction = new ArrayList<ObjectWrapper>();
    }


    public boolean openConnection() {
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
            view.showMessage("Connected to the server at host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error when connecting to the server!");
            return false;
        }
        return true;
    }

    public boolean sendData(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(obj);

        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error when sending data to the server!");
            return false;
        }
        return true;
    }

    public boolean closeConnection() {
        try {
            if (myListening != null)
                myListening.stop();
            if (mySocket != null) {
                mySocket.close();
                view.showMessage("Disconnected from the server!");
            }
            myFunction.clear();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error when disconnecting from the server!");
            return false;
        }
        return true;
    }


    public ArrayList<ObjectWrapper> getActiveFunction() {
        return myFunction;
    }


    class ClientListening extends Thread {

        public ClientListening() {
            super();
        }

        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    Object obj = ois.readObject();
                    if (obj instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) obj;
                        if (data.getPerformative() == ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER)
                            view.showMessage("Number of client connecting to the server: " + data.getData());
                        else {
                            for (int i = 0; i < myFunction.size(); i++) {
                                ObjectWrapper fto = myFunction.get(i);
                                if (fto.getPerformative() == data.getPerformative()) {
                                    switch (data.getPerformative()) {
                                        case ObjectWrapper.REPLY_LOGIN_USER:
                                            LoginFrm loginView = (LoginFrm) fto.getData();
                                            loginView.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_ONLINE_PLAYER:
                                            OnlinePlayerFrm opf = (OnlinePlayerFrm) fto.getData();
                                            opf.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_LOGOUT_USER:
                                            MainFrm mf = (MainFrm) fto.getData();
                                            mf.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_ADD_FRIEND:
                                            PlayerInfoFrm pif = (PlayerInfoFrm) fto.getData();
                                            pif.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_FRIEND_LIST:
                                        case ObjectWrapper.REPLY_FRIEND_REQUEST:
                                        case ObjectWrapper.REPLY_ACCEPT_FRIEND:
                                            FriendFrm ff = (FriendFrm) fto.getData();
                                            ff.receivedDataProcessing(data);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.showMessage("Error when receiving data from the server!");
                view.resetClient();
            }
        }
    }
}
