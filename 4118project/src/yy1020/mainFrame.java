package yy1020;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 游戏主窗体
 * @author yy
 *
 */
public class mainFrame extends JFrame implements gameConfig{
	//游戏面板
	JPanel panel;
	
	private static Robot robot = null;
	
	public mainFrame() {
		init();

	}

	/**
	 * 设置窗体
	 */
	public void init(){
		
		final TextField input = new TextField();
		input.setColumns(100);
		input.setEditable(true);
		this.add(input);
		input.setText("right");
		
		
		Button go = new Button("go");
		this.add(go);
		go.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	String src = input.getText();
            	if(src.equals("right")) {
            		Player.times = 3;
            		Player.right = true;
    				Player.towards = 4;
            		System.out.println("yes");
                	Player.walkr=true;
            	}
            	else if(src.equals("rightrun")){
            		Player.shift = true;
            		Player.times = 3;
            		Player.right = true;
            		Player.towards = 4;
            		System.out.println("run");
                	Player.runr=true;
            	}
            	else if(src.equals("left")){
            		Player.times = 3;
            		Player.left = true;
            		Player.towards = 3;
            		System.out.println("left");
                	Player.walkl=true;
            	}
            	
            	else if(src.equals("leftrun")){
            		Player.shift = true;
            		Player.times = 3;
            		Player.left = true;
            		Player.towards = 3;
            		System.out.println("run");
                	Player.runl=true;
            	}
            	else {
            		Player.times=3;
            		Player.space=true;
            	}
            	
            }
        });
		
		this.setTitle(title);
		this.setSize(frameX, frameY);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(3);
		//创建游戏面板
		panel = setpanel();
		
		this.add(panel);
		this.setVisible(true);
		//安装键盘监听器
		PanelListenner plis = new PanelListenner();
		this.addKeyListener(plis);
		
		//启动人物移动线程
		Player player = new Player();
		player.start();
		
		//启动刷新面板线程
		UpdateThread ut = new UpdateThread(panel);
		ut.start();
	}
	
	/**
	 * 设置游戏面板
	 */
	public JPanel setpanel(){
		JPanel panel = new MyPanel();
		panel.setPreferredSize(new Dimension(panelX, panelY));
		panel.setLayout(null);
		panel.setBackground(Color.black);

		return panel;
	}
	
	/**
	 * 内部游戏按键监听类
	 * @author yy
	 *
	 */
	class PanelListenner extends KeyAdapter{
		//当按键按下
		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_UP:
				//Player.up = true;
				//Player.towards = 1;
				break;
			case KeyEvent.VK_DOWN:
				//Player.down = true;
				//Player.towards = 2;
				break;
			case KeyEvent.VK_LEFT:
				Player.left = true;
				Player.towards = 3;
				break;
			case KeyEvent.VK_RIGHT:
				Player.right = true;
				Player.towards = 4;
				break;
			case KeyEvent.VK_SHIFT:
				Player.shift = true;
				break;
			case KeyEvent.VK_SPACE:
				Player.space = true;

				break;

			default:
				break;
			}
		}
		//当按键释放
		public void keyReleased(KeyEvent e){
			int code = e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_UP:
				Player.up = false;
				Player.up1 = 0;
				break;
			case KeyEvent.VK_DOWN:
				Player.down = false;
				Player.down1 = 0;
				break;
			case KeyEvent.VK_LEFT:
				Player.left = false;
				Player.left1 = 0;
				break;
			case KeyEvent.VK_RIGHT:
				Player.right = false;
				Player.right1 = 0;
				break;
			case KeyEvent.VK_SHIFT:
				Player.shift = false;
				break;
			case KeyEvent.VK_SPACE:
				Player.space = false;

				break;

			default:
				break;
			}
		}
	}
	/**
	 * 自定义内部游戏面板类
	 * @author yy
	 *
	 */
	class MyPanel extends JPanel{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			//找到角色旁边的素材，上下左右各5格
			for(int i=Player.getI()-11;i<=Player.getI()+11;i++){
				for(int j=Player.getJ()-11;j<=Player.getJ()+11;j++){
					//如果这一格没有超界
					if(i>=0&&j>=0&&i<ReadMapFile.map1.length&&j<ReadMapFile.map1[0].length){
						//画第一层元素
						ImageIcon icon = GetMap.int2icon(ReadMapFile.map1[i][j]);
						g.drawImage(icon.getImage(), (Player.px-elesize/2)+((j-Player.getJ())*elesize)-(Player.mx%elesize), (Player.py-elesize/2)+((i-Player.getI())*elesize)-(Player.my%elesize), elesize, elesize, null);
						//第二层
						ImageIcon icon2 = GetMap.int2icon(ReadMapFile.map2[i][j]);
						g.drawImage(icon2.getImage(), (Player.px-elesize/2)+((j-Player.getJ())*elesize)-(Player.mx%elesize), (Player.py-elesize/2)+((i-Player.getI())*elesize)-(Player.my%elesize), elesize, elesize, null);
						//第三层
//						ImageIcon icon3 = GetMap.int2icon(ReadMapFile.map3[i][j]);
//						g.drawImage(icon3.getImage(), (Player.px-elesize/2)+((j-Player.getJ())*elesize)-(Player.mx%elesize), (Player.py-elesize/2)+((i-Player.getI())*elesize)-(Player.my%elesize), elesize, elesize, null);
					}
				}
			}
//			g.setColor(Color.black);
//			g.fillRect(0, 0, 50, 650);
//			g.fillRect(0, 0, 650, 50);
//			g.fillRect(600, 0, 50, 650);
//			g.fillRect(0, 600, 650, 50);
			
			//由于暂时还没弄好游戏角色的移动图，所以角色先用一个黑色的小球代替一下....  = =！
//			g.fillOval(Player.px-elesize/2, Player.py-elesize/2, elesize, elesize);
			Player.draw(g);
			//个人的一个小想法，做一个黑色的图片，然后中间挖空一个圆，加上模糊效果，来模拟人的视野
			//g.drawImage(shadow2.getImage(), 0, 0, 650, 650, null);
		}
	}
}
