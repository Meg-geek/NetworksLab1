import java.io.IOException;
import java.net.*;

public class App {
    public static final int PORT = 7777;
    public static final int TIMEOUT = 2000;
    public static final int RECV_BUF_SIZE = 1000;
    public static final int UPDATE_LIST_DELAY = 10000;
    private MulticastSocket socket;
    private InetAddress group;
    private AppsInfo appsInfo = new AppsInfo(UPDATE_LIST_DELAY);

    private void configureSocket (String multicastGroup) throws IOException{
        socket = new MulticastSocket(PORT);
        //socket = new MulticastSocket(new InetSocketAddress("192.168.56.1", PORT));
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
                    DatagramPacket packetRecv = new DatagramPacket(new byte[RECV_BUF_SIZE], RECV_BUF_SIZE);
                    socket.receive(packetRecv);
                    appsInfo.add(packetRecv.getAddress().getHostAddress(), new String(packetRecv.getData()));
                } catch(SocketTimeoutException ex){
                    timeout = false;
                }
            }
            appsInfo.updateInfo();
        }
    }
}
