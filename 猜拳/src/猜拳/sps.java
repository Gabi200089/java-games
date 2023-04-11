package 猜拳;

import   java.applet.*;   
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class sps extends JFrame {


	final JLabel jl2, jl3,back,knightlabel,pirate;
	JLabel gameNum, winNum;
	JLabel gameLabel,winLabel;
	final JButton next,retry;
	JButton btn,btn1,btn2;
	JLabel jl4,jl5;
	public String person;
	public String m;
	private JOptionPane jop;
	int j;
	int i;
	int t = 0;
	int gameNumber = 0, winNumber = 0;

	final String[] arr = new String[3];

	public void Win(String person) {// 傳入person字串
		arr[0] = "石頭";
		arr[1] = "剪刀";
		arr[2] = "布";
		person = arr[j];
		i = (int) (Math.random() * 3);
		String c = arr[i];// 玩家
//判斷輸贏
		if (c.equals(person)) {// c有無=person
			gameNumber++;
			gameNum.setBackground(Color.white); // 背景色
			gameNum.setFont(new java.awt.Font("Dialog", 1, 40)); // 18代表字号，其他查一下api
			gameNum.setForeground(Color.white); // 前景颜色，就是文字颜色了
			gameNum.setText("" + gameNumber);

			jl4.setIcon(new ImageIcon(this.getClass().getResource("tie.png")));// 平手or輸贏的lable

		} else if (c.equals("剪刀") && person.equals("石頭") || c.equals("石頭") && person.equals("布")
				|| c.equals("布") && person.equals("剪刀")) {
			winNumber++;
			winNum.setText("" + winNumber);
			gameNum.setBackground(Color.white); // 背景色
			gameNum.setFont(new java.awt.Font("Dialog", 1, 40)); // 18代表字号，其他查一下api
			gameNum.setForeground(Color.white);
			gameNumber++;
			gameNum.setText("" + gameNumber);

			jl4.setIcon(new ImageIcon(this.getClass().getResource("win.png")));

		} else {
			gameNumber++;
			gameNum.setBackground(Color.white); // 背景色
			gameNum.setFont(new java.awt.Font("Dialog", 1, 40)); // 18代表字号，其他查一下api
			gameNum.setForeground(Color.white);
			gameNum.setText("" + gameNumber);

			jl4.setIcon(new ImageIcon(this.getClass().getResource("lose.png")));

		}

		if (gameNumber == 3&&winNumber>=2) {
			next.setVisible(true);
			jl4.setVisible(false);
//			back.setVisible(false);
			knightlabel.setVisible(false);
			pirate.setVisible(false);
			jl2.setVisible(false);
			jl3.setVisible(false);
			jl5.setVisible(false);
			btn.setVisible(false);
			btn1.setVisible(false);
			btn2.setVisible(false);
			gameLabel.setVisible(false);
			winLabel.setVisible(false);
			gameNum.setVisible(false);
			winNum.setVisible(false);
			this.add(jl5);
		}
		if (gameNumber == 3&&winNumber<2) {
			retry.setVisible(true);
			jl4.setVisible(false);
//			back.setVisible(true);
			knightlabel.setVisible(false);
			pirate.setVisible(false);
			jl2.setVisible(false);
			jl3.setVisible(false);
			jl5.setVisible(false);
			btn.setVisible(false);
			btn1.setVisible(false);
			btn2.setVisible(false);
			gameLabel.setVisible(false);
			winLabel.setVisible(false);
			gameNum.setVisible(false);
			winNum.setVisible(false);
			this.add(jl5);
		}
	}

	public sps() {

        
		JFrame jf = new JFrame();
		this.setSize(800, 600);
		this.setTitle("猜拳遊戲");
		this.setLayout(null);


//背景圖
		back = new JLabel();
		ImageIcon icon = new ImageIcon(this.getClass().getResource("猜拳背景.jpg"));
		back.setIcon(icon);
		back.setBounds(0, 0, 800, 600);



//電腦出的拳圖案
		jl2 = new JLabel();
		jl2.setBounds(350, 30, 180, 180);
		this.add(jl2);

//我出的拳圖案
		jl3 = new JLabel();
		jl3.setBounds(350, 320, 180, 180);
		this.add(jl3);

		jl4 = new JLabel("");
		jl4.setBounds(220, 210, 390, 100);// 平手標籤
		this.add(jl4);
		
		jl5 = new JLabel("");
		jl5.setBounds(0, 0, 800, 600);// 平手標籤
		jl5.setIcon(new ImageIcon(this.getClass().getResource("space.png")));

// 按鈕
		btn = new JButton();
		btn.setBounds(300, 505, 90, 50);
		btn.setIcon(new ImageIcon(this.getClass().getResource("stone.png")));
		btn.setHorizontalTextPosition(JButton.CENTER);
		this.add(btn);

		btn1 = new JButton();
		btn1.setBounds(400, 505, 90, 50);
		btn1.setIcon(new ImageIcon(this.getClass().getResource("scissors.png")));
		btn1.setHorizontalTextPosition(JButton.CENTER);
		this.add(btn1);

		btn2 = new JButton();
		btn2.setBounds(500, 505, 90, 50);
		btn2.setIcon(new ImageIcon(this.getClass().getResource("papper.png")));
		btn2.setHorizontalTextPosition(JButton.CENTER);
		this.add(btn2);

		next = new JButton();
		next.setBounds(50, 100, 725, 275);
		next.setIcon(new ImageIcon(this.getClass().getResource("win_01.png")));
		next.setHorizontalTextPosition(JButton.CENTER);
		this.add(next);
		next.setVisible(false);
		next.setOpaque(false);
		next.setBorder(null);
		next.setContentAreaFilled(false);
		
		retry = new JButton();
		retry.setBounds(70, 100, 698, 246);
		retry.setIcon(new ImageIcon(this.getClass().getResource("lose_01.png")));
		retry.setHorizontalTextPosition(JButton.CENTER);
		this.add(retry);
		retry.setVisible(false);
		retry.setOpaque(false);
		retry.setBorder(null);
		retry.setContentAreaFilled(false);
		
//騎士的圖片
		knightlabel = new JLabel();
		knightlabel.setIcon(new ImageIcon(this.getClass().getResource("騎士.png")));
		knightlabel.setBounds(10, 320, 180, 230);
		this.add(knightlabel);

//海盜的圖片
		pirate = new JLabel();
		pirate.setIcon(new ImageIcon(this.getClass().getResource("猜拳海盜.png")));
		pirate.setBounds(600, 0, 180, 230);
		this.add(pirate);

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				// 如果你鼠標點擊的是石頭按鈕
				j = 0;// 石頭
				String b = btn.getActionCommand();

				jl3.setIcon(new ImageIcon(this.getClass().getResource(j + ".png")));
				Win(person);// 判斷輸贏
				t = t++;
				sps.this.jl2.setIcon(new ImageIcon(this.getClass().getResource(i + ".png")));
				sps.this.jl2.setText(arr[i]);
				System.out.println(arr[i]);
			}
		});
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {



				j = 1;// 剪刀
				String bl = btn1.getActionCommand();

				jl3.setIcon(new ImageIcon(this.getClass().getResource(j + ".png")));
				Win(person);
				t = t++;
				sps.this.jl2.setIcon(new ImageIcon(this.getClass().getResource(i + ".png")));
				sps.this.jl2.setText(arr[i]);
				System.out.println(arr[i]);

			}
		});

		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				j = 2; // 布
				String b2 = btn2.getActionCommand();


				jl3.setIcon(new ImageIcon(this.getClass().getResource(j + ".png")));
				Win(person);
				t = t++;
				sps.this.jl2.setText(arr[i]);
				System.out.println(arr[i]);
				sps.this.jl2.setIcon(new ImageIcon(this.getClass().getResource(i + ".png")));

			}
		});
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
					Runtime.getRuntime().exec("cmd /c start C:\\Users\\Amy_Liu\\Desktop\\猜拳.jar");
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				System.exit(EXIT_ON_CLOSE);
			//	System.exit(1);
   
			}
		});
		this.setVisible(true);// 視窗出現與否
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		gameLabel = new JLabel();
		gameLabel.setIcon(new ImageIcon(this.getClass().getResource("round.png")));
		gameLabel.setBounds(8, 12, 110, 50);
		this.add(gameLabel);

		gameNum = new JLabel();
		gameNum.setBackground(Color.white); // 背景色
		gameNum.setFont(new java.awt.Font("Dialog", 1, 40)); // 18代表字号，其他查一下api
		gameNum.setForeground(Color.white); // 前景颜色，就是文字颜色了
		gameNum.setText("1");
		gameNum.setBounds(125, 0, 92, 80);
		this.getContentPane().add(gameNum);

		winLabel = new JLabel();
		winLabel.setIcon(new ImageIcon(this.getClass().getResource("win_0.png")));
		winLabel.setBounds(170, 12, 110, 50);
		this.add(winLabel);
		winNum = new JLabel();
		winNum.setBackground(Color.white); // 背景色
		winNum.setFont(new java.awt.Font("Dialog", 1, 40)); // 18代表字号，其他查一下api
		winNum.setForeground(Color.white); // 前景颜色，就是文字颜色了
		winNum.setText("0");
		winNum.setBounds(287, 0, 92, 80);
		this.getContentPane().add(winNum);


		this.getContentPane().add(back);/// 加背景
		
	}


	
	public static void main(String[] args) {
		sps t = new sps();
		new music();
	}
}