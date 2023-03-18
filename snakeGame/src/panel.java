import com.sun.source.tree.Scope;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {
    static int width = 1200;
    static int  height = 600;
    static int unit = 50;
    Timer timer;
    Random random;
    //food spawn coordinates
    int foodx,foody;
    int score;
    //initial length of snake
    int length = 3;
    //initial direction var
    char dir = 'R';
    //game over screen initiator
    boolean flag = false;
    static  int delay = 160;

    //to store x and y coordinates of the snake
    int[] xSnake = new int[288];
    int[] ySnake = new int[288];

    panel(){
        this.setPreferredSize(new Dimension(width , height));
        this.setBackground(Color.black);
        //allows keyboard inputs
        this.setFocusable(true);
        random = new Random();
        //input processing
        this.addKeyListener(new myKey());

        gameStart();
    }

    public void gameStart(){
        spawnFood();
        flag = true;
        timer = new Timer(delay , this);
        timer.start();
    }

    public void spawnFood(){
        foodx = random.nextInt(width/unit) * unit;
        foody = random.nextInt(height/unit) * unit;
    }

     public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
     }

     public void draw(Graphics graphic){
        if(flag){
            graphic.setColor(Color.red);
            graphic.fillOval(foodx , foody , unit , unit);

            for(int i = 0 ; i < length ; i++){
                if(i == 0){
                    graphic.setColor(Color.orange);
                }else{
                    graphic.setColor(Color.green);
                }

                graphic.fillRect(xSnake[i] , ySnake[i] , unit , unit);
            }

            graphic.setColor(Color.cyan);
            graphic.setFont(new Font("Comic Sans" , Font.BOLD , 40));
            FontMetrics f = getFontMetrics(graphic.getFont());
            graphic.drawString("Score : " + score , (width - f.stringWidth("Score : " + score)) / 2 , graphic.getFont().getSize());



        }
        else{
            gameOver(graphic);
        }

     }
     public void gameOver(Graphics graphic){
         //score
         graphic.setColor(Color.cyan);
         graphic.setFont(new Font("Comic Sans" , Font.BOLD , 40));
         FontMetrics f = getFontMetrics(graphic.getFont());
         graphic.drawString("Score : " + score , (width - f.stringWidth("Score : " + score)) / 2 , graphic.getFont().getSize());

         //gameOver text
         graphic.setColor(Color.red);
         graphic.setFont(new Font("Comic Sans" , Font.BOLD , 80));
         FontMetrics f2 = getFontMetrics(graphic.getFont());
         graphic.drawString("GAME OVER " + score , (width - f2.stringWidth("GAME OVER")) / 2 , height / 2);

         //replay prompt
         graphic.setColor(Color.green);
         graphic.setFont(new Font("Comic Sans" , Font.BOLD , 40));
         graphic.drawString("press R to replay", (width - f.stringWidth("press R to replay")) / 2 , height / 2 + 150);

     }

     //to Check if snake is hit (WALLS + ITS BODY)
     public void checkIt(){

        //walls
        if(xSnake[0] < 0)flag = false;

        else if (xSnake[0] > 1200)flag = false;

        else if (ySnake[0] < 0)flag = false;

        else if(ySnake[0] > 600)flag = false;

        for(int i = length ; i > 0 ; i--){
            if((xSnake[0] == xSnake[i]) && (ySnake[0] == ySnake[i])) {
                flag = false;
            }
        }

        if(!flag)timer.stop();
     }

     public void eat(){
        if(xSnake[0] == foodx && ySnake[0] == foody){
            length++;
            score++;
            spawnFood();
        }
     }

     public void move(){
        //update Body one by one taking the next element
         for(int i = length ; i > 0 ;i--){
             xSnake[i] = xSnake[i - 1];
             ySnake[i] = ySnake[i - 1];
         }

         switch (dir){
             case 'R' :
                 xSnake[0] += unit;
                 break;
             case 'L' :
                 xSnake[0] -= unit;
                 break;
             case 'D' :
                 ySnake[0] += unit;
                 break;
             case 'U' :
                 ySnake[0] -= unit;
                 break;
         }
     }

     public class myKey extends KeyAdapter{
        public void keyPressed(KeyEvent evt){
            switch(evt.getKeyCode()){
                case KeyEvent.VK_UP:
                    //check for opp dir
                    if(dir != 'D') {
                        dir = 'U';
                        break;
                    }
                case KeyEvent.VK_DOWN:
                    if(dir != 'U'){
                        dir = 'D';
                        break;
                    }
                case KeyEvent.VK_RIGHT:
                    if(dir != 'L'){
                        dir = 'R';
                        break;
                    }
                case KeyEvent.VK_LEFT:
                    if(dir != 'R') {
                        dir = 'L';
                        break;
                    }
                case KeyEvent.VK_R:
                    if(!flag){
                        score = 0;
                        length = 3;
                        dir = 'R';
                        Arrays.fill(xSnake , 0);
                        Arrays.fill(ySnake , 0);

                        gameStart();

                    }
                    break;
            }

        }

     }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(flag){
            move();
            eat();
            checkIt();
        }

        repaint();

    }
}
