import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    private class Tile{   //Only SnakeGame can access
        int x;
        int y;

        Tile(int x, int y){
        this.x = x;
        this.y = y;
    }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //Snake
    Tile snakeMouth;
    ArrayList<Tile> snakeBody;

    //Apple
    Tile apple;
    Random random;

    //Game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

      SnakeGame(int boardWidth, int boardHeight){ 
        this.boardWidth = boardWidth; //this keyword seperates the boardwidth that is a parameter or a field
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeMouth = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        apple = new Tile(5,5);
        random = new Random();
        placeApple();

        velocityX = 2;
        velocityY = 1;

        gameLoop = new Timer(100,this); //moves every 100ms
        gameLoop.start();
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //Grid background
        for(int i = 0; i < boardWidth/tileSize; i++){ //boardwidth/tilesize gives 24 columns/rows

            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }  

        //Apple
        g.setColor(Color.ORANGE);
        g.fillArc(apple.x*tileSize, apple.y*tileSize, tileSize, tileSize, 180, 180);

        //Snake where x = snakeMouth.x * tileSize
        g.setColor(Color.GREEN);
        g.fill3DRect(snakeMouth.x*tileSize,snakeMouth.y*tileSize,tileSize,tileSize, true);

        //Snake body
        for(int i = 0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fillOval(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
        }

        //Game score
        if(gameOver){
            g.setFont(new Font("Sans_serif", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER!", tileSize - -140, tileSize - -20);

            g.setFont(new Font("Sans_serif", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("\n\nFinal Score: " + String.valueOf(snakeBody.size()), tileSize - -185, tileSize - -550);
    
        }
        else{
            g.setFont(new Font("Sans_serif", Font.BOLD, 20));
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeApple(){
        apple.x = random.nextInt(boardWidth/tileSize);
        apple.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collide(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){

        //eat food
        if(collide(snakeMouth, apple)){
            snakeBody.add(new Tile(apple.x, apple.y));
            placeApple();
        }

        //Snake body
        for(int i = snakeBody.size()-1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeMouth.x;
                snakePart.y = snakeMouth.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake mouth
        snakeMouth.x += velocityX;
        snakeMouth.y += velocityY;

        //Game over
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //Collision with head
            if(collide(snakeMouth, snakePart)){
                gameOver = true;
            }
        }

        if(snakeMouth.x*tileSize < 0 || snakeMouth.x*tileSize > boardWidth ||
        snakeMouth.y*tileSize < 0 || snakeMouth.y*tileSize > boardHeight){
         gameOver = true;
    }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); // does draw on repeat
        if(gameOver){
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    //unneccesary

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
