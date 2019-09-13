import java.util.ArrayList;
import java.util.Date;

public class AppsInfo {
    private ArrayList<AppRecord> appsInfo = new ArrayList<>();
    private int MAX_MILSEC_DELAY;
    private View view;

    public AppsInfo(int delay){
        MAX_MILSEC_DELAY = delay;
        view = new SwingView();
    }

    public void add(String ip, String msg){
        appsInfo.add(new AppRecord(ip, msg));
    }

    public void updateInfo(){
        Date nowDate = new Date();
        for(AppRecord record : appsInfo){
            if(record.getRecvDate().getTime() - nowDate.getTime() > MAX_MILSEC_DELAY){
                appsInfo.remove(record);
            }
        }
        view.update(appsInfo);
    }
}


class AppRecord {
    private String msg, ipAddress;
    private Date recvDate;

    public AppRecord(String ip, String msg){
        this.msg = msg;
        ipAddress = ip;
        this.recvDate = new Date();
    }

    public String getMsg(){
        return msg;
    }

    public String getIpAddress(){
        return ipAddress;
    }

    public Date getRecvDate(){
        return recvDate;
    }
}