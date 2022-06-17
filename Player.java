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
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;  
import java.awt.event.MouseListener;  
import java.awt.event.MouseMotionListener;

public class Player implements Entity{
    
    /*Variables*/
    private double x;
    private double y;
    private double health;
    private double maxHealth;
    private double velocityX;
    private double velocityY;
    private double aimAngle;
    private double aimArrowX;
    private double aimArrowY;
    private Room presentRoom;
    private BufferedImage[] mainCharacterSprite = new BufferedImage[12];;
    private BufferedImage aimArrow;
    private BufferedImage aimArrowOriginal;
    private Rectangle hitBox;
    private Bullet primaryBulletType;
    private Bullet secondaryBulletType;
    private PlayerKeyListener keyListener = new PlayerKeyListener();
    private PlayerMouseListener mouseListener = new PlayerMouseListener();
    private PlayerMouseMotionListener mouseMotionListener = new PlayerMouseMotionListener();
    //keydown directions are 1 - up, 2 - down, 3 - left, 4 - right
    private boolean[] keyDown = new boolean[]{false, false, false, false};
    private boolean[] lastKeyDown = new boolean[]{false, false};
    private int bulletCountDown = 0;
    private int hitCountDown = 0;
    private int killCountDown = 0;
    private int deathCountDown = 27;
    private boolean alive = true;
    private boolean shooting = false;
    private int clickCoolDown = 0;
    
    /*Constructer*/  
    public Player(double x, double y,int health,double velocityX, double velocityY){
        this.x=x;
        this.y=y;
        this.health=health;
        maxHealth = health;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.hitBox = new Rectangle((int) x, (int) y, 27,35);
        
        for (int i = 0; i < 12; i++) {
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
        if (alive) {
            double heightCorrection = Math.round(Math.sin(BulletHellMain.getGameCounter()/9)*1.4);
            if (aimAngle < Math.PI) {
                if (hitCountDown > 0) {
                    if (hitCountDown%4 > 2) {
                        g.drawImage(mainCharacterSprite[10], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    } else {
                        g.drawImage(mainCharacterSprite[3], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    }
                } else if (killCountDown > 0) {
                    g.drawImage(mainCharacterSprite[4], (int) (x - 2), 
                    (int) (y + heightCorrection - 4), null);
                } else if ((!keyDown[0]) && (!keyDown[1]) && (!keyDown[2]) && (!keyDown[3])) {
                    if (BulletHellMain.getGameCounter()%80 > 60) {
                        g.drawImage(mainCharacterSprite[2], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    } else if ((BulletHellMain.getGameCounter()%80 > 20) && 
                    (BulletHellMain.getGameCounter()%80 < 40)) {
                        g.drawImage(mainCharacterSprite[1], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    } else {
                        g.drawImage(mainCharacterSprite[0], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    }
                } else {
                    g.drawImage(mainCharacterSprite[0], (int) (x - 2), (int) (y + heightCorrection - 4), null);
                }
            } else {
                if (hitCountDown > 0) {
                    if (hitCountDown%4 > 2) {
                        g.drawImage(mainCharacterSprite[11], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    } else {
                        g.drawImage(mainCharacterSprite[8], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    }
                } else if (killCountDown > 0) {
                    g.drawImage(mainCharacterSprite[9], (int) (x - 2), 
                    (int) (y + heightCorrection - 4), null);
                } else if ((!keyDown[0]) && (!keyDown[1]) && (!keyDown[2]) && (!keyDown[3])) {
                    if (BulletHellMain.getGameCounter()%80 > 60) {
                        g.drawImage(mainCharacterSprite[7], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    } else if ((BulletHellMain.getGameCounter()%80 > 20) && 
                    (BulletHellMain.getGameCounter()%80 < 40)) {
                        g.drawImage(mainCharacterSprite[6], (int) (x - 2),
                        (int) (y + heightCorrection - 4), null);
                    } else {
                        g.drawImage(mainCharacterSprite[5], (int) (x - 2), 
                        (int) (y + heightCorrection - 4), null);
                    }
                } else {
                    g.drawImage(mainCharacterSprite[5], (int) (x - 2), (int) 
                    (y + heightCorrection - 4), null);
                }
            }
            
            g.drawImage(aimArrow, (int) aimArrowX - aimArrow.getWidth()/2,
            (int) aimArrowY - aimArrow.getHeight()/2, null);
            /* 
            g.drawRect((int) hitBox.getX(), 
            (int) hitBox.getY(), 
            (int) hitBox.getWidth(), 
            (int) hitBox.getHeight());
            */
            g.setColor(Color.BLACK);
            if ((primaryBulletType.getBulletInterval() > 49) && 
            (bulletCountDown > 0)) {
                g.drawArc((int) (x - 23), (int) (y - 19), 75, 75, 90, 
                (int) -(360*((1.0*primaryBulletType.getBulletInterval() - bulletCountDown)
                /primaryBulletType.getBulletInterval())));
            }
        } else {
            deathCountDown--;
            if (deathCountDown%5 > 3) {
                if (aimAngle < Math.PI) {
                    g.drawImage(mainCharacterSprite[3], (int) (x - 2), 
                    (int) (y - 4), null);
                } else {
                    g.drawImage(mainCharacterSprite[8], (int) (x - 2), 
                    (int) (y - 4), null);
                }
            }
            if (deathCountDown <= 0 ) {
                BulletHellMain.characterDeath();
            }
        }
    }
    
    public void shootBullet() {
        double accuracyCorrection = ((Math.random()*2*aimAngle - aimAngle)*primaryBulletType.getAccuracy());
        presentRoom.getBulletList().add(primaryBulletType.returnSelf(hitBox.getCenterX(), 
        hitBox.getCenterY(), Math.cos((aimAngle + accuracyCorrection) + -Math.PI/2)*primaryBulletType.getMaxVelocity(),
        Math.sin((aimAngle + accuracyCorrection) -Math.PI/2)*primaryBulletType.getMaxVelocity()));
        bulletCountDown = primaryBulletType.getBulletInterval();
    }
    
    public void shootingBullet() {
        if (bulletCountDown > 0) {
            bulletCountDown--;
        }
        if (killCountDown > 0) {
            killCountDown--;
        }
        if (hitCountDown > 0) {
            hitCountDown --;
        }
        if ((shooting) && (bulletCountDown <= 0)) {
            shootBullet();
            bulletCountDown = primaryBulletType.getBulletInterval();
        }
    }
    
    public void takeDamage(Bullet bullet) {
        health -= bullet.getDamage();
        if (bullet.getDamage() > 0) {
            hitCountDown = 20;
            if (health <= 0) {
                alive = false;
                double deathAngle = Math.atan2(bullet.getVelocityY(), bullet.getVelocityX());
                velocityX = Math.cos(deathAngle);
                velocityY = Math.sin(deathAngle);
            }
        }
    }
    
    public void takeDamage(double damage) {
        health -= damage;
        if (damage > 0) {
            hitCountDown = 20;
            if (health <= 0) {
                alive = false;
                double deathAngle = Math.atan2(velocityY, velocityX);
                velocityX = Math.cos(deathAngle);
                velocityY = Math.sin(deathAngle);
            }
        }
    }
    
    public void kill() {
        killCountDown = 10; 
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
        if (clickCoolDown > 0) {
            clickCoolDown--;
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
    
    public void setMaxHealth(double maxHealth){
        this.maxHealth=maxHealth;
    }
    
    public double getMaxHealth(){
        return maxHealth;
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
    
    public Rectangle getHitBox() {
        return hitBox;
    }
    
    public void setPresentRoom(Room presentRoom){
        this.presentRoom=presentRoom;
        primaryBulletType = new PlayerBasicBullet(x, y, 0, 0, presentRoom);
        secondaryBulletType = new Tomato(x, y, 0, 0, presentRoom);
        this.presentRoom.setPrimaryWeapon(primaryBulletType);
        this.presentRoom.setSecondaryWeapon(secondaryBulletType);
        this.presentRoom.addX(-900);
        this.presentRoom.addY(-1600);
        hitBox.setLocation((int) (hitBox.getX() + 900), 
        (int) (hitBox.getY() + 1600));
    }
    
    public void switchWeapons() {
        Bullet tempBulletType = primaryBulletType;
        primaryBulletType = secondaryBulletType;
        secondaryBulletType = tempBulletType;
        presentRoom.switchWeapons();
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
    
    public int getHitCountDown() {
        return hitCountDown;
    }
    
    public BufferedImage[] getSprites() {
        return mainCharacterSprite;
    }
    class PlayerKeyListener implements KeyListener{
        /*Methods*/
        public void keyTyped(KeyEvent e){
        }
        
        public void keyPressed(KeyEvent e){
            int keyCode = e.getKeyCode();
            if (alive) {
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
            
        }
        
        public void mousePressed(MouseEvent e){
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (alive) {
                    shooting = true;
                }
            } else if ((e.getButton() == MouseEvent.BUTTON3) && (clickCoolDown <= 0)) {
                switchWeapons();
                clickCoolDown = 5;
            }
        }
        
        
        public void mouseReleased(MouseEvent e){
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (alive) {
                    shooting = false;
                }
            }
        }
        
        
        public void mouseEntered(MouseEvent e){
        }
        public void mouseExited(MouseEvent e){
        }
    }
    
    
    
    class PlayerMouseMotionListener implements MouseMotionListener{
        public void mouseMoved(MouseEvent e){
            if (alive) {
                aimAngle = -(Math.atan2(e.getX() - (x - mainCharacterSprite[0].getWidth()/2 + 30)
                , e.getY() - (y - mainCharacterSprite[0].getHeight()/2 + 39))) + Math.PI;
                aimArrow = rotateAimArrow(aimArrowOriginal, aimAngle);
            }
        }
        public void mouseDragged(MouseEvent e){
            if (alive) {
                aimAngle = -(Math.atan2(e.getX() - (x - mainCharacterSprite[0].getWidth()/2 + 30)
                , e.getY() - (y - mainCharacterSprite[0].getHeight()/2 + 39))) + Math.PI;
                aimArrow = rotateAimArrow(aimArrowOriginal, aimAngle);
            }
        }         
    } 
}