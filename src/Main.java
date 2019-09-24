import java.io.IOException;

public class Main {
    public static void main(String[] args){
        App app = new App();
        try{
            app.work("224.0.0.1");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
