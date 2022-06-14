/* 
* @version 1.0
* June 2022
*/
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;  
import java.awt.event.MouseListener;  
import java.awt.event.MouseMotionListener;

public class Player{
    
    /*Variables*/
    private double x;
    private double y;
    private double health;
    private double velocityX;
    private double velocityY;
    private double aimAngle;
    private double aimArrowX;
    private double aimArrowY;
    private Room presentRoom;
    private BufferedImage[] mainCharacterSprite = new BufferedImage[10];;
    private BufferedImage aimArrow;
    private BufferedImage aimArrowOriginal;
    private Rectangle hitBox;
    private Bullet bulletType;
    private PlayerKeyListener keyListener = new PlayerKeyListener();
    private PlayerMouseListener mouseListener = new PlayerMouseListener();
    private PlayerMouseMotionListener mouseMotionListener = new PlayerMouseMotionListener();
    //keydown directions are 1 - up, 2 - down, 3 - left, 4 - right
    private boolean[] keyDown = new boolean[]{false, false, false, false};
    private boolean[] lastKeyDown = new boolean[]{false, false};
    private int bulletCountDown = 0;
    private boolean shooting = false;
    
    /*Constructer*/  
    public Player(double x, double y,int health,double velocityX, double velocityY){
        this.x=x;
        this.y=y;
        this.health=health;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.hitBox = new Rectangle((int) x, (int) y, 27,35);
        
        for (int i = 0; i < 10; i++) {
            try{
                mainCharacterSprite[i] = ImageIO.read(new File("Resources/Main_Character" + i + ".png"));
            }catch(Exception e){
                System.out.println("COULD NOT FIND FILEPATH FOR MAIN CHARACTER " + i);
            }
        }
        try{
            aimArrowOriginal = ImageIO.read(new File("Resources/Aim_Arrow.png"));
        }catch(Exception e){
            System.out.println("COULD NOT FIND FILEPATH FOR AIM ARROW ");
        }
        aimArrow = rotateAimArrow(aimArrowOriginal, 0);
    }
    
    public void draw(Graphics g) {
        if (aimAngle < Math.PI) {
            g.drawImage(mainCharacterSprite[0], (int) (x - 4), (int) (y - 4), null);
        } else {
            g.drawImage(mainCharacterSprite[5], (int) (x - 4), (int) (y - 4), null);
        }
        g.drawImage(aimArrow, (int) aimArrowX - aimArrow.getWidth()/2,
        (int) aimArrowY - aimArrow.getHeight()/2, null);
        g.drawRect((int) hitBox.getX(), 
        (int) hitBox.getY(), 
        (int) hitBox.getWidth(), 
        (int) hitBox.getHeight());
    }
    
    public void shootBullet() {
        double accuracyCorrection = ((Math.random()*2*aimAngle - aimAngle)*bulletType.getAccuracy());
        presentRoom.getBulletList().add(bulletType.returnSelf(hitBox.getCenterX(), 
        hitBox.getCenterY(), Math.cos((aimAngle + accuracyCorrection) + -Math.PI/2)*bulletType.getMaxVelocity(),
        Math.sin((aimAngle + accuracyCorrection) -Math.PI/2)*bulletType.getMaxVelocity()));
        bulletCountDown = bulletType.getBulletInterval();
    }
    
    public void shootingBullet() {
        if (bulletCountDown > 0) {
            bulletCountDown --;
        }
        if ((shooting) && (bulletCountDown <= 0)) {
            shootBullet();
            bulletCountDown = bulletType.getBulletInterval();
        }
    }
    
    public void move() {
        if (keyDown[0]) {
            if (!keyDown[1]) {
                //y -= velocityY;
                //hitBox.setLocation((int) x, (int) y);
                presentRoom.addY(velocityY);
                hitBox.setLocation((int) hitBox.getX(), (int) (hitBox.getY() - velocityY));
                lastKeyDown[0] = true;
            }
        } else if (keyDown[1]) {
            if (!keyDown[0]) {
                //y += velocityY;
                //hitBox.setLocation((int) x, (int) y);
                presentRoom.addY(-velocityY);
                hitBox.setLocation((int) hitBox.getX(), (int) (hitBox.getY() + velocityY));
                lastKeyDown[0] = false;
            }
        }
        if (keyDown[2]) {
            if (!keyDown[3]) {
                //x -= velocityX;
                //hitBox.setLocation((int) x, (int) y);
                presentRoom.addX(velocityX);
                hitBox.setLocation((int) (hitBox.getX() - velocityX), (int) hitBox.getY());
                lastKeyDown[1] = true;
            }
        } else if (keyDown[3]) {
            if (!keyDown[2]) {
                //x += velocityX;
                presentRoom.addX(-velocityX);
                hitBox.setLocation((int) (hitBox.getX() + velocityX), (int) hitBox.getY());
                //hitBox.setLocation((int) x, (int) y);
                lastKeyDown[1] = false;
            }
        }
    }
    
    public void wallCollision(Rectangle wallHitBox) {
        if (lastKeyDown[1]) {
            //x += velocityX;
            presentRoom.addX(-velocityX);
            hitBox.setLocation((int) (hitBox.getX() + velocityX), (int) hitBox.getY());
            if (collidingWithRectangle(wallHitBox)) {
                //x -= velocityX;
                presentRoom.addX(velocityX);
                hitBox.setLocation((int) (hitBox.getX() - velocityX), (int) hitBox.getY());
            }
        } else {
            //x -= velocityX;
            presentRoom.addX(velocityX);
            hitBox.setLocation((int) (hitBox.getX() - velocityX), (int) hitBox.getY());
            if (collidingWithRectangle(wallHitBox)) {
                //x += velocityX;
                presentRoom.addX(-velocityX);
                hitBox.setLocation((int) (hitBox.getX() + velocityX), (int) hitBox.getY());
            } 
        }
        if (collidingWithRectangle(wallHitBox)) {
            if (lastKeyDown[0]) {
                //y += velocityY;
                presentRoom.addY(-velocityY);
                hitBox.setLocation((int) hitBox.getX(), (int) (hitBox.getY() + velocityY));
                if (collidingWithRectangle(wallHitBox)) {
                    //y -= velocityY;
                    presentRoom.addY(velocityY);
                    hitBox.setLocation((int) hitBox.getX(), (int) (hitBox.getY() - velocityY));
                }
            } else {
                //System.out.println("GOING DOWN");
                //y -= velocityY;
                presentRoom.addY(velocityY);
                hitBox.setLocation((int) hitBox.getX(), (int) (hitBox.getY() - velocityY));
                if (collidingWithRectangle(wallHitBox)) {
                    //System.out.println("NOT DOING ANYTHING");
                    //y += velocityY;
                    presentRoom.addY(-velocityY);
                    hitBox.setLocation((int) hitBox.getX(), (int) (hitBox.getY() + velocityY));
                }
            }
        }
    }
    
    public boolean collidingWithRectangle(Rectangle rectangleHitBox) {
        if ((rectangleHitBox.contains(hitBox.getX() ,hitBox.getY())) || 
        (rectangleHitBox.contains(hitBox.getX() + hitBox.getWidth(), hitBox.getY())) || 
        (rectangleHitBox.contains(hitBox.getX(), hitBox.getY() + hitBox.getHeight())) || 
        (rectangleHitBox.contains(hitBox.getX() + hitBox.getWidth(), hitBox.getY() + hitBox.getHeight()))){
            return true;
        }
        return false;
    }
    
    private BufferedImage rotateAimArrow(BufferedImage image, double angle){
        BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), 
        image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(angle, image.getWidth()/2, image.getHeight()/2);
        g2d.drawImage(image, null, 0, 0);
        g2d.dispose();
        //x + 13 - (x - mainCharacterSprite.getWidth()/2 + 30)
        //13 + mainCharacterSprite.getWidth()/2 - 30
        double x1 = 13 + mainCharacterSprite[0].getWidth()/2 - 30;
        //y - 20 - (y - mainCharacterSprite.getHeight()/2 + 39)
        //-20 + mainCharacterSprite.getHeight()/2 - 39
        double y1 = -20 + mainCharacterSprite[0].getHeight()/2 - 39;
        
        double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
        double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);
        
        aimArrowX = x2 + x - mainCharacterSprite[0].getWidth()/2 + 30;
        aimArrowY = y2 + y - mainCharacterSprite[0].getHeight()/2 + 39;
        return rotatedImage;
    }  
    
    /*Setters and Getters*/  
    public void setX(double x){
        this.x=x;
    }
    
    public double getX(){
        return x;
    }
    
    public void setY(double y){
        this.y=y;
    }
    
    public double getY(){
        return y;
    }
    
    public void setHealth(double health){
        this.health=health;
    }
    
    public double getHealth(){
        return health;
    }
    
    public void setVelocityX(double velocityX){
        this.velocityX=velocityX;
    }
    
    public double getVelocityX(){
        return velocityX;
    }
    
    public void setVelocityY(double velocityY){
        this.velocityY=velocityY;
    }
    
    public double getVelocityY(){
        return velocityY;
    }
    
    public void setPresentRoom(Room presentRoom){
        this.presentRoom=presentRoom;
        this.bulletType = new PlayerBasicBullet(x, y, 0, 0, 
        presentRoom.getBulletList());
    }
    
    public Room getPresentRoom(){
        return presentRoom;
    }
    
    public PlayerMouseListener getMouseListener() {
        return mouseListener;
    }
    
    public PlayerMouseMotionListener getMouseMotionListener() {
        return mouseMotionListener;
    }
    
    public PlayerKeyListener getKeyListener() {
        return keyListener;
    }
    
    public boolean[] getKeyDown() {
        return keyDown;
    }
    
    class PlayerKeyListener implements KeyListener{
        /*Methods*/
        public void keyTyped(KeyEvent e){
        }
        
        public void keyPressed(KeyEvent e){
            int keyCode = e.getKeyCode();
            
            if(keyCode==KeyEvent.VK_W){
                keyDown[0] = true;
            }
            if(keyCode==KeyEvent.VK_S){
                keyDown[1] = true;
            }
            if(keyCode==KeyEvent.VK_A){
                keyDown[2] = true;
            }
            if(keyCode==KeyEvent.VK_D){
                keyDown[3] = true;
            }
        }
        
        public void keyReleased(KeyEvent e){
            int keyCode = e.getKeyCode();
            if(keyCode==KeyEvent.VK_W){
                keyDown[0] = false;
            }
            if(keyCode==KeyEvent.VK_S){
                keyDown[1] = false;
            }
            if(keyCode==KeyEvent.VK_A){
                keyDown[2] = false;
            }
            if(keyCode==KeyEvent.VK_D){
                keyDown[3] = false;
            }
        }
        
    }
    
    class PlayerMouseListener implements MouseListener{
        
        public void mouseClicked(MouseEvent e){  
            //MyMouseListener.mouseDown = true;
        }
        
        public void mousePressed(MouseEvent e){
            shooting = true;
        }
        
        
        public void mouseReleased(MouseEvent e){
            shooting = false;
        }
        
        
        public void mouseEntered(MouseEvent e){
        }
        public void mouseExited(MouseEvent e){
        }
    }
    
    
    
    class PlayerMouseMotionListener implements MouseMotionListener{
        public void mouseMoved(MouseEvent e){
            aimAngle = -(Math.atan2(e.getX() - (x - mainCharacterSprite[0].getWidth()/2 + 30)
            , e.getY() - (y - mainCharacterSprite[0].getHeight()/2 + 39))) + Math.PI;
            aimArrow = rotateAimArrow(aimArrowOriginal, aimAngle);
        }
        public void mouseDragged(MouseEvent e){
            aimAngle = -(Math.atan2(e.getX() - (x - mainCharacterSprite[0].getWidth()/2 + 30)
            , e.getY() - (y - mainCharacterSprite[0].getHeight()/2 + 39))) + Math.PI;
            aimArrow = rotateAimArrow(aimArrowOriginal, aimAngle);
        }         
    } 
}
