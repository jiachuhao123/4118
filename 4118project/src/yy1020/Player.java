package yy1020;

import java.awt.Graphics;
import java.util.concurrent.TimeUnit;


/**
 * 角色类
 * @author yy
 *
 */
public class Player extends Thread implements gameConfig{
	//角色中点相对游戏面板的位置(在游戏中是不变的)
	static int px = panelX/2;
	static int py = panelY/2;
	//static int py = panelY;
	//角色中点在整张地图中的位置(设置人最开始中点的位置一定要是一个元素中心的位置，要不然这种移动就会出问题 - -！)
	static int x = 75;
	static int y = 75;

	//角色的偏移量（实现像素点移动关键的部分,一定要给个初始值，要不然到边界出现负数哭死，害我找错误找了一个晚上）
	static int mx = 50;
	static int my = 50;
	//角色的步长
	static int step = 5;
	static int runstep = 10;
	static int fallingSpeed = 0;
	static int gravity = 1;
	static int jumpPower = -10;
	static int times = 0;
	//static int timesrun = 0;

	//角色是否移动
	static boolean up = false;
	static boolean down = false;
	static boolean left = false;
	static boolean right = false;
	static boolean shift = false;
	static boolean space = false;
	static boolean walkr = false;
	static boolean walkl = false;
	static boolean runr = false;
	static boolean runl = false;


	//角色的朝向    1,2,3,4分别代表上下左右(用来处理角色不移动时的朝向问题，后面要写与npc对话之类的估计用得上)
	static int towards = 4;
	//角色的移动累积量（这个就是用来控制循环的变化4张角色图片来达成动态移动的）
	static int up1 = 0;
	static int down1 = 0;
	static int left1 = 0;
	static int right1 = 0;

	@Override
	public void run() {
		while(true){
			//moveUD();
			//moveLR();
			if(isOnGround()) {
				walkright();
				walkleft();
				runright();
				runleft();
				if(space&&times>0) {
					jump();
					times--;
					System.out.println(times);
				}
				else if(space&&times<=0) {
					space = false;
				}
			}else {
				fall();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isOnGround() {
		if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0){
			int y1 = (y/elesize+1)*elesize+elesize/2;
			int x1 = (x/elesize)*elesize+elesize/2;
			if((y-y1)*(y-y1)>=elesize*elesize){
				return false;
			}
		}else if(ReadMapFile.map2[y/elesize+1][x/elesize]==0){
			return false;
		}

		return true;
	}
	
	public static void jump(){
				
				
				fallingSpeed = jumpPower;
				fall();
				

		
	}
	public static void fall() {
					if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0){
						int y1 = (y/elesize-1)*elesize+elesize/2;
						int x1 = (x/elesize)*elesize+elesize/2;
						if((y-y1)*(y-y1)>=elesize*elesize){
							y=y+fallingSpeed;
							my=my+fallingSpeed;
							fallingSpeed = fallingSpeed + gravity;
						}
					}else if(ReadMapFile.map2[y/elesize-1][x/elesize]==0){//上方没物体，可以继续向上移动
						y=y+fallingSpeed;
						my=my+fallingSpeed;
						fallingSpeed = fallingSpeed + gravity;
					}
	}

	
	
	/**
	 * 角色上下移动的方法
	 */
	/**public void moveUD(){
		if(up&&!shift){
			//当按住上键时，给up1加1，当up1大于20时候又置为0，达成循环
			up1++;
			if(up1>=20){
				up1=0;
			}
			//如果角色当前位置上方的数组值不为0（角色上方有物体挡着）：这里处理的是角色一个格子内部的移动，不能移动到上面一格
			if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0){
				int y1 = (y/elesize-1)*elesize+elesize/2;
				int x1 = (x/elesize)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y-step;
					my=my-step;
				}
			}else if(ReadMapFile.map2[y/elesize-1][x/elesize]==0){//上方没物体，可以继续向上移动
				y=y-step;
				my=my-step;
			}
		}else if(up&&shift){
			//当按住上键时，给up1加1，当up1大于20时候又置为0，达成循环
			up1++;
			if(up1>=20){
				up1=0;
			}
			//如果角色当前位置上方的数组值不为0（角色上方有物体挡着）：这里处理的是角色一个格子内部的移动，不能移动到上面一格
			if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0){
				int y1 = (y/elesize-1)*elesize+elesize/2;
				int x1 = (x/elesize)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y-runstep;
					my=my-runstep;
				}
			}else if(ReadMapFile.map2[y/elesize-1][x/elesize]==0){//上方没物体，可以继续向上移动
				y=y-runstep;
				my=my-runstep;
			}
		}else if(down&&!shift){
			down1++;
			if(down1>=20){
				down1=0;
			}
			if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0){
				int y1 = (y/elesize+1)*elesize+elesize/2;
				int x1 = (x/elesize)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y+step;
					my=my+step;
				}
			}else if(ReadMapFile.map2[y/elesize+1][x/elesize]==0){
				y=y+step;
				my=my+step;
			}
		}else if(down&&shift){
			down1++;
			if(down1>=20){
				down1=0;
			}
			if(ReadMapFile.map2[y/elesize+1][x/elesize]!=0){
				int y1 = (y/elesize+1)*elesize+elesize/2;
				int x1 = (x/elesize)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y+runstep;
					my=my+runstep;
				}
			}else if(ReadMapFile.map2[y/elesize+1][x/elesize]==0){
				y=y+runstep;
				my=my+runstep;
			}
		}
	}
	
	/**
	 * 角色左右移动的方法
	 */
	public void walkright() {
		
		
		 if(walkr&&(times>0)){
			right1++;
			if(right1==10) {
				times--;
				System.out.println(times+"in 1 step");
				if(times==0) {
					right1=0;
				}
			}
			if(right1>=20){
				right1=0;
				times--;
				System.out.println(times+"in 2 step");
			}
			if(ReadMapFile.map2[y/elesize][x/elesize+1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize+1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x+step;
					mx=mx+step;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize+1]==0){
				x=x+step;
				mx=mx+step;
			}
		 }
		 else if (walkr&&times<=0){
			 walkr = false;
			 right = false;
			 right1 = 0;
		 }
		 else {
			 
		 }
		 
		
	}
	
	public void runright() {
		
		
		 if(runr&&(times>0)){
			right1++;
			if(right1==10) {
				times--;
				System.out.println(times+"in 1 step");
				if(times==0) {
					right1=0;
				}
			}
			if(right1>=20){
				right1=0;
				times--;
				System.out.println(times+"in 2 step");
			}
			if(ReadMapFile.map2[y/elesize][x/elesize+1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize+1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x+runstep;
					mx=mx+runstep;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize+1]==0){
				x=x+runstep;
				mx=mx+runstep;
			}
		 }
		 else if (runr&&times<=0){
			 runr = false;
			 right = false;
			 right1 = 0;
			 shift = false;
		 }
		 else {
			 
		 }
		 
		
	}
	
	public void walkleft() {
		
		
		if(walkl&&(times>0)){
			left1++;
			if(left1==10) {
				times--;
				System.out.println(times+"in 1 step");
				if(times==0) {
					left1=0;
				}
			}
			if(left1>=20){
				left1=0;
				times--;
				System.out.println(times+"in 2 step");
			}
			if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize-1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x-step;
					mx=mx-step;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize-1]==0){
				x=x-step;
				mx=mx-step;
			}
		}
		else if(walkl&&times<=0){
			walkl = false;
			left = false;
			left1 = 0;
		}
		else {
			
		}
		
		
	}
	
	public void runleft() {
		
		
		 if(runl&&(times>0)){
			left1++;
			if(left1==10) {
				times--;
				System.out.println(times+"in 1 step");
				if(times==0) {
					left1=0;
				}
			}
			if(left1>=20){
				left1=0;
				times--;
				System.out.println(times+"in 2 step");
			}
			if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize-1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x-runstep;
					mx=mx-runstep;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize-1]==0){
				x=x-runstep;
				mx=mx-runstep;
			}
		 }
		 else if(runl&&times<=0){
			 runl = false;
			 left = false;
			 left1 = 0;
			 shift = false;
			 
		 }
		 else {
			 
		 }
		 
		
	}
	
	public void moveLR(){
		if(left&&!shift){
			left1++;
			if(left1>=20){
				left1=0;
			}
			if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize-1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x-step;
					mx=mx-step;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize-1]==0){
				x=x-step;
				mx=mx-step;
			}
		}else if(left&&shift){
			left1++;
			if(left1>=20){
				left1=0;
			}
			if(ReadMapFile.map2[y/elesize][x/elesize-1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize-1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x-runstep;
					mx=mx-runstep;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize-1]==0){
				x=x-runstep;
				mx=mx-runstep;
			}
		}else if(right&&!shift){
			right1++;
			if(right1>=20){
				right1=0;
			}
			if(ReadMapFile.map2[y/elesize][x/elesize+1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize+1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x+step;
					mx=mx+step;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize+1]==0){
				x=x+step;
				mx=mx+step;
			}
		}else if(right&&shift){
			right1++;
			if(right1>=20){
				right1=0;
			}
			if(ReadMapFile.map2[y/elesize][x/elesize+1]!=0){
				int y1 = (y/elesize)*elesize+elesize/2;
				int x1 = (x/elesize+1)*elesize+elesize/2;
				if((x-x1)*(x-x1)>=elesize*elesize){
					x=x+runstep;
					mx=mx+runstep;
				}
			}else if(ReadMapFile.map2[y/elesize][x/elesize+1]==0){
				x=x+runstep;
				mx=mx+runstep;
			}
		}
	}
	
	
	public static void draw(Graphics g){
		//如果角色不在移动中
		if(!up&&!down&&!left&&!right){
			if(towards==1){//如果角色移动的最后朝向为上
				if(!isOnGround()) {
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*3, 96*2, 96*4, null);
				}
				else {
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*3, 96, 96*4, null);
				}
			}else if(towards==2){//最后移动朝向下
				if(!isOnGround()) {
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 0, 96*2, 96, null);
				}
				else {
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 0, 96, 96, null);
				}
			}else if(towards==3){//最后移动朝向左
				if(!isOnGround()) {
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96, 96*2, 96*2, null);
				}
				else {
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96, 96, 96*2, null);
				}
			}else if(towards==4){//最后移动朝向右
				if(!isOnGround()) {
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*2, 96*2, 96*3, null);
				}
				else {
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*2, 96, 96*3, null);
				}
			}
		}else {//如果角色在移动中
			/**if(up&&!shift){
				//通过up1的值，来决定画哪一张图片
				if(up1<5){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*3, 96, 96*4, null);
				}else if(up1<10){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*3, 96*2, 96*4, null);
				}else if(up1<15){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96*3, 96*3, 96*4, null);
				}else{
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96*3, 96*4, 96*4, null);
				}
			}else if(up&&shift){
				//通过up1的值，来决定画哪一张图片
				if(up1<5){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*3, 96, 96*4, null);
				}else if(up1<10){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*3, 96*2, 96*4, null);
				}else if(up1<15){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96*3, 96*3, 96*4, null);
				}else{
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96*3, 96*4, 96*4, null);
				}
			}else if(down&&!shift){
				if(down1<5){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 0, 96, 96, null);
				}else if(down1<10){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 0, 96*2, 96, null);
				}else if(down1<15){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 0, 96*3, 96, null);
				}else{
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 0, 96*4, 96, null);
				}
			}else if(down&&shift){
				if(down1<5){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 0, 96, 96, null);
				}else if(down1<10){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 0, 96*2, 96, null);
				}else if(down1<15){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 0, 96*3, 96, null);
				}else{
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 0, 96*4, 96, null);
				}
			}else **/if(left&&!shift){
				if(left1<5){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96, 96, 96*2, null);
				}else if(left1<10){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96, 96*2, 96*2, null);
				}else if(left1<15){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96, 96*3, 96*2, null);
				}else{
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96, 96*4, 96*2, null);
				}
				
				
			}else if(left&&shift){
				if(left1<5){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96, 96, 96*2, null);
				}else if(left1<10){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96, 96*2, 96*2, null);
				}else if(left1<15){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96, 96*3, 96*2, null);
				}else{
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96, 96*4, 96*2, null);
				}
				
				
			}else if(right&&!shift){
				if(right1<5){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*2, 96, 96*3, null);
				}else if(right1<10){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*2, 96*2, 96*3, null);
				}else if(right1<15){
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96*2, 96*3, 96*3, null);
				}else{
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96*2, 96*4, 96*3, null);
				}
			}else if(right&&shift){
				if(right1<5){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*2, 96, 96*3, null);
				}else if(right1<10){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*2, 96*2, 96*3, null);
				}else if(right1<15){
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*2, 96*2, 96*3, 96*3, null);
				}else{
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96*3, 96*2, 96*4, 96*3, null);
				}
			}

			}
	}
	
	
	//得到角色在数组中的位置I
	public static int getI(){
		return (y-(playersize/2))/50;
	}
	//得到角色在数组中的位置J
	public static int getJ(){
		return (x-(playersize/2))/50;
	}
}
