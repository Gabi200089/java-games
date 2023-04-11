package 打地鼠;

import java.applet.AudioClip; 
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class HitMouse extends JFrame implements ActionListener,MouseListener{
final JButton next,retry;
boolean isOver=false;//設定標記,遊戲是否結束
private String dir="./src/打地鼠/";//圖片目錄,當前工程下
JLabel jlbMouse;//地鼠
Timer timer;//時間定時器
Random random;//隨機數物件,即生成地鼠的位置
int delay=1100;//延遲時間
Toolkit tk;
Image image;
Cursor myCursor;
JLabel currentGrade,hitNum,speed;
int showNumber=0,hitNumber=0,currentGrades=1,miss=0;

public HitMouse(){
	super("打地鼠");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(800, 600);
	this.setLocationRelativeTo(null);//設定視窗在螢幕中心
	setbackground();//呼叫setbackground()
	this.getContentPane().setLayout(null);//設定框架佈局模式為空,只有這樣,才能知道圖片的真正位置
	
	//設定滑鼠為錘子圖片
	tk = Toolkit.getDefaultToolkit();
	image = tk.createImage(this.getClass().getResource("chui.png"));
	myCursor = tk.createCustomCursor(image, new Point(10,10), "xxx");
	this.setCursor(myCursor);
	
	setMessage();//呼叫setMessage()
	
	//在背景圖片的基礎上設定地鼠圖片
	ImageIcon imageMouse = new ImageIcon(this.getClass().getResource("海盜.png"));
	jlbMouse = new JLabel();
	jlbMouse.setSize(150,150);
	this.getContentPane().add(jlbMouse);
	jlbMouse.setVisible(false);
	jlbMouse.addMouseListener(this);//新增滑鼠監聽
	
	next = new JButton();
	next.setBounds(50, 100, 725, 275);
	next.setIcon(new ImageIcon(this.getClass().getResource("win_02.png")));
	next.setHorizontalTextPosition(JButton.CENTER);
	this.add(next);
	next.setVisible(false);
	next.setOpaque(false);
	next.setBorder(null);
	next.setContentAreaFilled(false);
	
	retry = new JButton();
	retry.setBounds(70, 100, 698, 246);
	retry.setIcon(new ImageIcon(this.getClass().getResource("lose_02.png")));
	retry.setHorizontalTextPosition(JButton.CENTER);
	this.add(retry);
	retry.setVisible(false);
	retry.setOpaque(false);
	retry.setBorder(null);
	retry.setContentAreaFilled(false);
	

	
	next.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try {
				Runtime.getRuntime().exec("cmd /c start C:\\Users\\Amy_Liu\\Desktop\\飛機.jar");
			} catch (IOException ee) {
				ee.printStackTrace();
			}

			String b3 = next.getActionCommand();
			
			System.exit(EXIT_ON_CLOSE);
		}
	});
	
	retry.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try {
				Runtime.getRuntime().exec("cmd /c start C:\\Users\\Amy_Liu\\Desktop\\飛機.jar");
			} catch (IOException ee) {
				ee.printStackTrace();
			}

			String b4 = retry.getActionCommand();
			
			System.exit(EXIT_ON_CLOSE);
		//	System.exit(1);

		}
	});
	
	//定時器
	timer = new Timer(delay,this);
	random = new Random();
	timer.start();

	
	this.setResizable(false);//設定視窗大小不能改變
	this.setVisible(true);
}



private void setbackground() {
	((JPanel)(this.getContentPane())).setOpaque(false);//如果為 true,則該元件繪製其邊界內的所有畫素。否則該元件可能不繪製部分或所有畫素,從而允許其底層畫素透視出來。
	ImageIcon bgImage = new ImageIcon(this.getClass().getResource("打地鼠背景.png"));
	JLabel bgLabel = new JLabel(bgImage);
	bgLabel.setBounds(0, -35, bgImage.getIconWidth(), bgImage.getIconHeight());
	this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));//設定背景圖片的層次最低

}

private void setMessage() {
	
	/*速度*/
	JLabel speedLabel = new JLabel(new ImageIcon(this.getClass().getResource("speed.png")));
	speedLabel.setBounds(242, -8, 92, 60);
	this.getContentPane().add(speedLabel);
	speed=new JLabel();
	speed.setBounds(332, -9, 92, 60);
	speed.setFont(new java.awt.Font("Dialog",1,30));
	speed.setForeground(Color.ORANGE);
	speed.setText(""+delay);
	this.getContentPane().add(speed);
	
	/*打中次數*/
	JLabel hitLabel = new JLabel(new ImageIcon(this.getClass().getResource("hit.png")));
	hitLabel.setBounds(8, -8, 92, 60);
	this.getContentPane().add(hitLabel);
	hitNum = new JLabel("0");
	hitNum.setBounds(98, -10, 92, 60);
	hitNum.setFont(new java.awt.Font("Dialog",1,30));
	hitNum.setForeground(Color.ORANGE);
	this.getContentPane().add(hitNum);
	
	/*當前等級*/
	JLabel gradeLabel = new JLabel(new ImageIcon(this.getClass().getResource("grade.png")));
	gradeLabel.setBounds(123, -8, 92, 65);
	this.getContentPane().add(gradeLabel);
	currentGrade = new JLabel("1");
	currentGrade.setBounds(208, -10, 92, 60);
	currentGrade.setFont(new java.awt.Font("Dialog",1,30));
	currentGrade.setForeground(Color.ORANGE);
	this.getContentPane().add(currentGrade);
}


public static void main(String[] args) {
	new HitMouse();
	new music();
}

public void actionPerformed(ActionEvent e) {



	ImageIcon imageMouse = new ImageIcon(this.getClass().getResource("海盜.png"));//保證每次隨機生成的地鼠圖片都是為沒被打時的圖片
	jlbMouse.setIcon(imageMouse);
	
	int ran=random.nextInt(9);//隨機生成一個0~9(不包括9)的隨機數
	switch(ran){
		case 0:jlbMouse.setLocation(50 , 20);break;//左上
		case 1:jlbMouse.setLocation(315, 20);break;//中上
		case 2:jlbMouse.setLocation(558, 20);break;//右上
		case 3:jlbMouse.setLocation(50 ,200);break;//左中
		case 4:jlbMouse.setLocation(315,200);break;//中中
		case 5:jlbMouse.setLocation(562,200);break;//右中
		case 6:jlbMouse.setLocation(44 ,378);break;//左下
		case 7:jlbMouse.setLocation(315,378);break;//中下
		case 8:jlbMouse.setLocation(562,378);break;//右下
	}
	
	jlbMouse.setVisible(true);
	
	showNumber++;//出現次數
	//showNum.setText(""+showNumber);

	
	if( !gamePlan() ){//判斷遊戲是否結束,並顯示遊戲程序
		timer.stop();
	}

}


private boolean gamePlan() {//遊戲規則
	miss=showNumber-hitNumber;
	if(miss> 5){//一個關卡可以miss掉6次
		timer.stop();
		jlbMouse.setVisible(false);
		retry.setVisible(true);

//		JOptionPane.showMessageDialog(this, "Game Over !");//產生對話框
		isOver=true;
		return false;
	}
	if(hitNumber > 4){//打到五次可以晉級
		hitNumber=0;
		showNumber=0;
		miss=0;
		if(currentGrades==5)
			currentGrades=5;
		currentGrades++;
	if(delay>800){
		delay-=50;
	}
	if(currentGrades==5) {//玩五關獲勝
		timer.stop();
		jlbMouse.setVisible(false);
		next.setVisible(true);
//		JOptionPane.showMessageDialog(this, "WIN !");
	}
	
	speed.setText(""+delay);
	
	timer.setDelay(delay);
	//hitNum.setText(""+hitNumber);不用也可以跑
	//showNum.setText(""+showNumber);不用也可以跑
	currentGrade.setText(""+currentGrades);
	}
	return true;
}
//public void mouseMoved(MouseEvent e) { // 鼠标移动  
//        int x = e.getX();  
//        int y = e.getY();  
//        hero.moveTo(x, y);  
//    }  
//}
public void mouseClicked(MouseEvent e) {

}

public void mousePressed(MouseEvent e) {
	if(isOver){
	return ;
	}
	image = tk.createImage(this.getClass().getResource("chan.png"));
	myCursor = tk.createCustomCursor(image, new Point(10,10), "xxx");
	this.setCursor(myCursor);//滑鼠按下時,滑鼠顯示打下去的圖片,模擬打的動作
	//如果打中地鼠,則地鼠換成被打中的圖片,模擬地鼠被打
	if(e.getSource()==jlbMouse){
	ImageIcon imageIconHit = new ImageIcon(this.getClass().getResource("海盜boom.png"));
	jlbMouse.setIcon(imageIconHit);
	jlbMouse.setVisible(true);
	}
	
	hitNumber++;
	hitNum.setText(""+hitNumber);
}

public void mouseReleased(MouseEvent e) {
	if(isOver){
	return ;
	}
	//當滑鼠放鬆以後,滑鼠變回原來沒按下時的圖片
	image = tk.createImage(this.getClass().getResource("chui.png"));
	myCursor = tk.createCustomCursor(image, new Point(10,10), "xxx");
	this.setCursor(myCursor);
}

public void mouseEntered(MouseEvent e) {

}

public void mouseExited(MouseEvent e) {

}


} 