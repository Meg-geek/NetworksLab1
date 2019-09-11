import java.io.IOException;
import java.net.*;
import java.util.Date;

public class App {
    public static final int PORT = 7777;
    public static final int TIMEOUT = 2000;
    public static final int RECV_BUF_SIZE = 1000;
    private MulticastSocket socket;
    private InetAddress group;
    private View view;

    public App(){
        view = new SwingView();
    }

    private void configureSocket (String multicastGroup) throws IOException{
        socket = new MulticastSocket(PORT);
        socket.bind(new InetSocketAddress(PORT));
        group = InetAddress.getByName(multicastGroup);
        socket.joinGroup(group);
    }

    public void work(String multicastGroup) throws IOException {
        configureSocket(multicastGroup);
        String sendMsg = "send";
        DatagramPacket packetSend = new DatagramPacket(sendMsg.getBytes(), sendMsg.length(),
                group, PORT);
        while(true) {
            socket.send(packetSend);
            socket.setSoTimeout(TIMEOUT);
            boolean timeout = true;
            while(timeout){
                try{
                    byte[] recvMsg = new byte[RECV_BUF_SIZE];
                    DatagramPacket packetRecv = new DatagramPacket(recvMsg, recvMsg.length);
                    socket.receive(packetRecv);
                } catch(SocketTimeoutException ex){
                    timeout = false;
                }
            }
        }
    }
}
