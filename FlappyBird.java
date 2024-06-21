


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;


public class FlappyBird extends JPanel implements ActionListener , KeyListener {
    
         int BoardWidth = 360;
         int BoardHeight = 640;

        Image backgroundImg;
        Image birdImg;
        Image toppipeImg;
        Image bottompipeImg;
         

        int birdX = BoardWidth/8;
        int birdY = BoardHeight/2;
        int birdWidth = 30;
        int birdHeight = 40;
     
        public class Bird {
         
          int x = birdX;
          int y = birdY;
          int width = birdWidth;
          int height = birdHeight;
          Image img ;


        public Bird(Image img){

               this.img = img;
          }
           
        }

        int pipeX = BoardWidth;
        int pipeY = 0;
        int pipeWidth = 60;
        int pipeHeight = 300;

         public class Pipe {
         
          int x = pipeX;
          int y = pipeY;
          int width = pipeWidth;
          int height = pipeHeight;
          Image img;
          boolean passed = false;


          Pipe(Image img){
         this.img = img;
          }

         }
        
      
         //Game Logic

         int score;
         Bird bird;
         
         int velocityX = -4 ; //moves pipes from left to right
         int velocityY = 0;  //moves bird updown
         int gravity = 1 ;            

          ArrayList <Pipe> pipes;

 
         Timer gameloop;
         Timer placecPipesTimer; 
    
         Boolean gameOver = false;


        public FlappyBird(){

                setPreferredSize(new Dimension(BoardWidth,BoardHeight ));
                setFocusable(true);
                addKeyListener(this);
          try {
            backgroundImg = ImageIO.read(getClass().getResourceAsStream("/flappybirdbg.png"));
            birdImg = ImageIO.read(getClass().getResourceAsStream("/flappybird.png"));
            toppipeImg = ImageIO.read(getClass().getResourceAsStream("/toppipe.png"));
            bottompipeImg = ImageIO.read(getClass().getResourceAsStream("/bottompipe.png"));
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
        }

              
          bird = new Bird(birdImg);
       
          pipes = new ArrayList<>();
        
           gameloop = new Timer(1000/60, this);   
           gameloop.start(); 

          placecPipesTimer = new Timer(1500, new ActionListener(){
          
          
           @Override
           public void actionPerformed(ActionEvent e){

            placePipes();
           }

          });
           placecPipesTimer.start();

           }




           public void placePipes(){

            int openingSpace = BoardHeight/4;
            int randomPipeY = (int )((pipeY - pipeHeight/4)  - Math.random() * (pipeHeight/2)); 
           
             
             Pipe topPipe= new Pipe(toppipeImg);
             topPipe.y = randomPipeY;

            Pipe bottomPipe = new Pipe(bottompipeImg);
            bottomPipe.y  = topPipe.y +  pipeHeight +  openingSpace ;
              pipes.add(bottomPipe);
             pipes.add(topPipe);

           }
 
    public void paintComponent(Graphics g){

        // System.out.println("Dinesh");
         super.paintComponent(g); 
         fill(g);
    }
  
    public void fill(Graphics g){
       
         g.drawImage(backgroundImg, 0, 0, BoardWidth, BoardHeight, null);        
 
         g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.width, null);

         for (int i = 0; i < pipes.size(); i++) {
          
            Pipe pipe = pipes.get(i);
             
        g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
         }

         g.setColor(Color.white);
         g.setFont(new Font("Arial", Font.PLAIN, 32));

         if (gameOver) {
          g.drawString("Game Over"+ String.valueOf((int)score), 10,20);
        }
          else{

           g.drawString(String.valueOf((int)score), 10,20);
     
          
         }

    }
    public void move(){
        
      velocityY += gravity;
      bird.y += velocityY;
      
      bird.y  = Math.max(bird.y,0 );


      for (int i = 0; i < pipes.size(); i++) {
        
      Pipe pipe = pipes.get(i);
      pipe.x +=velocityX;

      if (!pipe.passed && bird.x > pipe.x + pipe.width) {
        
          pipe.passed = true;
          score += 1;
        
      }
 


      if (collision(bird, pipe)) {
        
        gameOver = true;
        break;
      }
    
      }

    
      
     
      if (bird.y > BoardHeight) {
        
        gameOver = true;
      }

          
      }
   
 
      public Boolean collision(Bird a, Pipe b){

        
      return a.x < b.x+b.width && //a's top left corner doesn't reach b's right corner
      a.x + a.width > b.x && // a's top right corner passes b's top right corner
      a.y < b.y+b.height && //a's top left corner doesn't reach b's bottom left corner
      a.y+ a.height > b.y  ;//a's bottom left corner passes b's top left corner

       }


@Override
public void actionPerformed(ActionEvent e) {
     
     
     repaint();
     move();


     if (gameOver) {
      
      placecPipesTimer.stop();
      gameloop.stop();
     }
  
}


@Override
public void keyTyped(KeyEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
}



@Override
public void keyPressed(KeyEvent e) {

  if (e.getKeyCode() == KeyEvent.VK_SPACE) {
     
      velocityY = -9 ; 
  }


   if (gameOver) {
    
     //Restart the game by resetting the condition

     bird.y = birdY;
     velocityY = 0;
     pipes.clear();
     score = 0;
     gameOver = false;
     gameloop.start();
     placecPipesTimer.start();


   }

}



@Override
public void keyReleased(KeyEvent e) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
}

}
