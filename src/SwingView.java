import javax.swing.*;
import java.util.Map;
import java.util.Set;

public class SwingView implements View<Map.Entry<String, AppRecord>> {
    private JFrame frame;
    public static String title = "NETWORKS LAB 1";
    private JPanel panel;
    private JTextArea infoArea;
    public static final int FRAME_WIDTH = 500, FRAME_HIGHT = 400;
    public static final int INFO_WIDTH = FRAME_WIDTH - 40, INFO_HIGHT = FRAME_HIGHT - 60;
    private JScrollPane infoScrollPane;

    private void createFrame(){
        frame = new JFrame(title);
        frame.setSize(FRAME_WIDTH, FRAME_HIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        createPanel();
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void createPanel(){
        panel = new JPanel();
        panel.setOpaque(true);
        panel.setLayout(null);
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoScrollPane = new JScrollPane(infoArea);
        infoScrollPane.setBounds(10, 10, INFO_WIDTH, INFO_HIGHT);
        panel.add(infoScrollPane);
    }

    public SwingView(){
        createFrame();
    }


    @Override
    public void update(Set<Map.Entry<String, AppRecord>> parameters){
        infoArea.setText(null);
        for(Map.Entry<String, AppRecord> record : parameters){
            infoArea.append("IP: " + record.getKey() +
                    " MESSAGE: " + record.getValue().getMsg() +
                    " DATE: " + record.getValue().getRecvDate() +
                    System.lineSeparator());
        }
    }
}
