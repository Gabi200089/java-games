package com.tarena.fly;
import java.awt.Font;  
import java.awt.Color;  
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.util.Arrays;  
import java.util.Random;  
import java.util.Timer;  
import java.util.TimerTask;  
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;  
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


  
public class ShootGame extends JPanel {  

    public static final int WIDTH = 800; // 面板寬
    public static final int HEIGHT = 600; // 面板高  
    /** 游戲的當前狀態: START RUNNING PAUSE GAME_OVER */  
    private int state;  
    private static final int START = 0;  
    private static final int RUNNING = 1;  
    private static final int PAUSE = 2;  
    private static final int GAME_OVER = 3;
	protected static final int EXIT_ON_CLOSE = 0;  
  
    private int score = 0; // 得分  
    private Timer timer; // 定時器  
    private int intervel = 1000 / 100; // 时間間隔(毫秒)  
  
    public static BufferedImage background;  
    public static BufferedImage start;  
    public static BufferedImage airplane;  
    public static BufferedImage bee;  
    public static BufferedImage bullet_1;
    public static BufferedImage bullet_2; 
    public static BufferedImage bullet_3; 
    public static BufferedImage hero0;  
    public static BufferedImage hero1;  
    public static BufferedImage pause;  
    public static BufferedImage gameover;  
  
    private FlyingObject[] flyings = {}; // 敵機數組
    private Bullet[] bullets = {}; // 子弹數組
    private Hero hero = new Hero(); // 英雄機
  
    static { // 靜態代碼塊  初始化圖片資源
        try {  
            background = ImageIO.read(ShootGame.class  
                    .getResource("background.png"));  
//            start = ImageIO.read(ShootGame.class.getResource("start.png"));  
            airplane = ImageIO  
                    .read(ShootGame.class.getResource("airplane.png"));  
            bee = ImageIO.read(ShootGame.class.getResource("bee.png"));  
            bullet_1 = ImageIO.read(ShootGame.class.getResource("bullet_1.png")); 
            bullet_2 = ImageIO.read(ShootGame.class.getResource("bullet_2.png")); 
            bullet_3 = ImageIO.read(ShootGame.class.getResource("bullet_3.png")); 
            hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));  
            hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));  
            pause = ImageIO.read(ShootGame.class.getResource("pause.png"));  
            gameover = ImageIO  
                    .read(ShootGame.class.getResource("gameover.png"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 畫 */  
    @Override  
    public void paint(Graphics g) {  
        g.drawImage(background, 0, 0, null); // 畫背景圖
        paintHero(g); // 畫英雄機
        paintBullets(g); // 畫子彈  
        paintFlyingObjects(g); // 畫飛行物 
        paintScore(g); // 畫分數  
        paintState(g); // 畫遊戲分數  
    }  
  
    /** 畫英雄機 */  
    public void paintHero(Graphics g) {  
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);  
    }  
  
    /** 畫子彈 */  
    public void paintBullets(Graphics g) {  
        for (int i = 0; i < bullets.length; i++) {  
            Bullet b = bullets[i];  
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),  
                    null);  
        }  
    }  
  
    /** 畫飛行物 */  
    public void paintFlyingObjects(Graphics g) {  
        for (int i = 0; i < flyings.length; i++) {  
            FlyingObject f = flyings[i];  
            g.drawImage(f.getImage(), f.getX(), f.getY(), null);  
        }  
    }  
  
    /** 畫分數 */  
    public void paintScore(Graphics g) {  
        int x = 10; // x坐標
        int y = 25; // y坐標  
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // 字體  
        g.setColor(new Color(0xFF9900));  
        g.setFont(font); // 設置字體
        g.drawString("SCORE:" + score, x, y); // 畫分數  
        y=y+20; // y坐標增20  
        g.drawString("LIFE:" + hero.getLife(), x, y); // 畫命  
    }  
  
    /** 畫遊戲狀態 */  
    public void paintState(Graphics g) {  
        switch (state) {  
        case START: // 啟動狀態  
            g.drawImage(start, 200, 0, null);  
            break;  
        case PAUSE: // 暫停狀態  
            g.drawImage(pause, 200, 0, null);  
            break;  
        case GAME_OVER: // 遊戲終止狀態  
            g.drawImage(gameover, 200, 0, null);  
            break;  
        }  
    }  
  
    public static void main(String[] args) {  
    	
    	
        JFrame frame = new JFrame("Fly");  
        ShootGame game = new ShootGame(); // 面板對象  
        frame.add(game); // 將面板添加到JFrame中  
        frame.setSize(WIDTH, HEIGHT); // 設置大小  
        frame.setAlwaysOnTop(true); // 設置其總在最上  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默認關閉操作  
        frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // 設置窗体的圖標  
        frame.setLocationRelativeTo(null); // 設置窗體初始位置  
        frame.setVisible(true); // 盡快调用paint  
  
        game.action(); // 啟動執行
        new music();  
        
    }  
  
    /** 啟動執行代碼 */  
    public void action() {  
        // 鼠標監聽事件  
        MouseAdapter l = new MouseAdapter() {  
            @Override  
            public void mouseMoved(MouseEvent e) { // 鼠標移动  
                if (state == RUNNING) { // 運行狀態下移动英雄機--隨鼠標位置  
                    int x = e.getX();  
                    int y = e.getY();  
                    hero.moveTo(x, y);  
                }  
            }  
  
            @Override  
            public void mouseEntered(MouseEvent e) { // 鼠標進入  
                if (state == PAUSE) { // 暫停狀態下運行  
                    state = RUNNING;  
                }  
            }  
  
            @Override  
            public void mouseExited(MouseEvent e) { // 鼠標退出  
                if (state == RUNNING) { // 游戲未结束，則設置其為暫停  
                    state = PAUSE;  
                }  
            }  
  
            @Override  
            public void mouseClicked(MouseEvent e) { // 鼠標點擊  
                switch (state) {  
                case START:  
                    state = RUNNING; // 啟動狀態下運行 
                    break;  
                case GAME_OVER: // 游戲结束，清理現場  
                    flyings = new FlyingObject[0]; // 清空飛行物  
                    bullets = new Bullet[0]; // 清空子彈 
                    hero = new Hero(); // 重新創建英雄機 
                    score = 0; // 清空成績  
                    state = START; // 狀態設置為啟動
                    break;  
                }  
            }  
        };  
        this.addMouseListener(l); // 處理鼠標點擊操作  
        this.addMouseMotionListener(l); // 處理鼠標滑動操作  
  
        timer = new Timer(); // 主流程控制  
        timer.schedule(new TimerTask() {  
            @Override  
            public void run() {  
                if (state == RUNNING) { // 運行状态  
                    enterAction(); // 飛行物入场  
                    stepAction(); // 走一步  
                    shootAction(); // 英雄機射击  
                    bangAction(); // 子弹打飛行物  
                    outOfBoundsAction(); // 删除越界飛行物及子弹  
                    checkGameOverAction(); // 檢查游戲结束  
                }  
                repaint(); // 重繪，調用paint()方法  
            }  
  
        }, intervel, intervel);  
    }  
  
    int flyEnteredIndex = 0; // 飛行物入場計數  
  
    /** 飛行物入场 */  
    public void enterAction() {  
        flyEnteredIndex++;  
        if (flyEnteredIndex % 40 == 0) { // 400毫秒生成一個飛行物--10*40  
            FlyingObject obj = nextOne(); // 随機生成一個飛行物  
            flyings = Arrays.copyOf(flyings, flyings.length + 1);  
            flyings[flyings.length - 1] = obj;  
        }  
    }  
  
    /** 走一步 */  
    public void stepAction() {  
        for (int i = 0; i < flyings.length; i++) { // 飛行物走一步  
            FlyingObject f = flyings[i];  
            f.step();  
        }  
  
        for (int i = 0; i < bullets.length; i++) { // 子彈走一步  
            Bullet b = bullets[i];  
            b.step();  
        }  
        hero.step(); // 英雄機走一步  
    }  
  
    /** 飛行物走一步 */  
    public void flyingStepAction() {  
        for (int i = 0; i < flyings.length; i++) {  
            FlyingObject f = flyings[i];  
            f.step();  
        }  
    }  
  
    int shootIndex = 0; // 射擊計數 
  
    /** 射击 */  
    public void shootAction() {  
        shootIndex++;  
        if (shootIndex % 30 == 0) { // 300毫秒發一颗  
            Bullet[] bs = hero.shoot(); // 英雄打出子彈 
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 擴容  
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length,  
                    bs.length); // 追加數组  
        }  
    }  
  
    /** 子弹與飛行物碰撞檢測 */  
    public void bangAction() {  
        for (int i = 0; i < bullets.length; i++) { // 遍歷所有子彈
            Bullet b = bullets[i];  
            bang(b); // 子彈和飛行物之間的碰撞檢查  
        }  
    }  
  
    /** 删除越界飛行物及子彈 */  
    public void outOfBoundsAction() {  
        int index = 0; // 索引  
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 活著的飛行物  
        for (int i = 0; i < flyings.length; i++) {  
            FlyingObject f = flyings[i];  
            if (!f.outOfBounds()) {  
                flyingLives[index++] = f; // 不越界的留著
            }  
        }  
        flyings = Arrays.copyOf(flyingLives, index); // 將不越界的飛行物都留著  
  
        index = 0; // 索引重置為0  
        Bullet[] bulletLives = new Bullet[bullets.length];  
        for (int i = 0; i < bullets.length; i++) {  
            Bullet b = bullets[i];  
            if (!b.outOfBounds()) {  
                bulletLives[index++] = b;  
            }  
        }  
        bullets = Arrays.copyOf(bulletLives, index); // 將不越界的子彈留著  
    }  
  
    /** 檢查游戲结束 */  
    public void checkGameOverAction() { 

        if (isGameOver()==true) {  
            state = GAME_OVER; // 改變狀態     
            JOptionPane.showMessageDialog(this, "繼續請按“確定”","挑戰失敗QQ", JOptionPane.PLAIN_MESSAGE,new ImageIcon(ShootGame.class.getResource("lose_03.png")));
			try {
				Runtime.getRuntime().exec("cmd /c start C:\\Users\\Amy_Liu\\Desktop\\飛機.jar");
			} catch (IOException ee) {
				ee.printStackTrace();
			}
			System.exit(EXIT_ON_CLOSE);
        }  
        if(score==150)
        {
        	state = GAME_OVER; // 改變狀態
       	JOptionPane.showMessageDialog(this, "繼續請按“確定”","恭喜過關!!!", JOptionPane.PLAIN_MESSAGE,new ImageIcon(ShootGame.class.getResource("win_03.png")));
        //score>10就會跳出視窗結束
		try {
			Runtime.getRuntime().exec("cmd /c start C:\\Users\\Amy_Liu\\Desktop\\飛機.jar");
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		System.exit(EXIT_ON_CLOSE);
        }
    }  
  
    /** 檢查游戲是否结束 */  
    public boolean isGameOver() {  
          
        for (int i = 0; i < flyings.length; i++) {  
            int index = -1;  
            FlyingObject obj = flyings[i];  
            if (hero.hit(obj)) { // 檢查英雄機與飛行物是否碰撞  
                hero.subtractLife(); // 減命  
                hero.setDoubleFire(0); // 雙倍火力解除  
                index = i; // 計錄碰上的飛行物索引  
            }  
            if (index != -1) {  
                FlyingObject t = flyings[index];  
                flyings[index] = flyings[flyings.length - 1];  
                flyings[flyings.length - 1] = t; // 碰上的與最后一個飛行物交换  
  
                flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除碰上的飛行物  
            } 
        }  
          
        return hero.getLife() <= 0;  
    }  
  
    /** 子彈和飛行物之間的碰撞檢查 */  
    public void bang(Bullet bullet) {  
        int index = -1; // 擊中的飛行物索引  
        for (int i = 0; i < flyings.length; i++) {  
            FlyingObject obj = flyings[i];  
            if (obj.shootBy(bullet)) { // 判斷是否擊中  
                index = i; // 紀錄被擊中的飛行物的索引  
                break;  
            }  
        }  
        if (index != -1) { // 有擊中的飛行物  
            FlyingObject one = flyings[index]; // 紀錄被擊中的飛行物  
  
            FlyingObject temp = flyings[index]; // 被擊中的飛行物與最后一個飛行物交换  
            flyings[index] = flyings[flyings.length - 1];  
            flyings[flyings.length - 1] = temp;  
  
            flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除最後一個飛行物(即被擊中的)  
  
            // 檢查one的類型(敵人加分，獎勵獲取)  
            if (one instanceof Enemy) { // 檢查類型，是敵人，則加分  
                Enemy e = (Enemy) one; // 強制類型轉換  
                score += e.getScore(); // 加分  
            } else { // 若為獎勵，設置獎勵  
                Award a = (Award) one;  
                int type = a.getType(); // 獲取獎勵類型  
                switch (type) {  
                case Award.DOUBLE_FIRE:  
                    hero.addDoubleFire(); // 設置雙倍火力  
                    break;  
                case Award.LIFE:  
//                    hero.addLife(); // 設置加命  (stop)
                    break;  
                }  
            }  
        }  
    }  
  
    /** 
     * 隨機生成飛行物 
     *  
     * @return 飛行物對象 
     */  
    public static FlyingObject nextOne() {  
        Random random = new Random();  
        int type = random.nextInt(20); // [0,20)  
        if (type < 4) {  
            return new Bee();  
        } else {  
            return new Airplane();  
        }  
    }  
  
}  