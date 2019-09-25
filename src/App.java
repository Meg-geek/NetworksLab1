import java.io.IOException;
import java.net.*;
import java.util.Date;

public class App {
    public static final int PORT = 7777;
    public static final int TIMEOUT = 2000;
    public static final int TIMEOUT_WAIT = TIMEOUT*2;
    public static final int RECV_BUF_SIZE = 1000;
    public static final int UPDATE_LIST_DELAY = 10000;
    private MulticastSocket socket;
    private InetAddress group;
    private AppsInfo appsInfo = new AppsInfo(UPDATE_LIST_DELAY);

    private void configureSocket (String multicastGroup) throws IOException{
        socket = new MulticastSocket(PORT);
        group = InetAddress.getByName(multicastGroup);
        socket.joinGroup(group);
        socket.setSoTimeout(TIMEOUT);
    }

    public void work(String multicastGroup) throws IOException {
        configureSocket(multicastGroup);
        String sendMsg = "send";
        DatagramPacket packetSend = new DatagramPacket(sendMsg.getBytes(), sendMsg.length(),
                group, PORT);
        while(true) {
            socket.send(packetSend);
            boolean timeout = true;
            long lastTime = new Date().getTime();
            while(timeout && new Date().getTime() - lastTime < TIMEOUT_WAIT){
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
