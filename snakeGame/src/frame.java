import javax.swing.*;

public class frame extends JFrame {
    frame(){
        this.add(new panel());
        this.setTitle("SNAKE GAME");
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }


}
