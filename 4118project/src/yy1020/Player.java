package yy1020;

import java.awt.Graphics;
import java.util.concurrent.TimeUnit;


/**
 * ��ɫ��
 * @author yy
 *
 */
public class Player extends Thread implements gameConfig{
	//��ɫ�е������Ϸ����λ��(����Ϸ���ǲ����)
	static int px = panelX/2;
	static int py = panelY/2;
	//static int py = panelY;
	//��ɫ�е������ŵ�ͼ�е�λ��(�������ʼ�е��λ��һ��Ҫ��һ��Ԫ�����ĵ�λ�ã�Ҫ��Ȼ�����ƶ��ͻ������ - -��)
	static int x = 75;
	static int y = 75;

	//��ɫ��ƫ������ʵ�����ص��ƶ��ؼ��Ĳ���,һ��Ҫ������ʼֵ��Ҫ��Ȼ���߽���ָ��������������Ҵ�������һ�����ϣ�
	static int mx = 50;
	static int my = 50;
	//��ɫ�Ĳ���
	static int step = 5;
	static int runstep = 10;
	static int fallingSpeed = 0;
	static int gravity = 1;
	static int jumpPower = -10;
	static int times = 0;
	//static int timesrun = 0;

	//��ɫ�Ƿ��ƶ�
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


	//��ɫ�ĳ���    1,2,3,4�ֱ������������(���������ɫ���ƶ�ʱ�ĳ������⣬����Ҫд��npc�Ի�֮��Ĺ����õ���)
	static int towards = 4;
	//��ɫ���ƶ��ۻ��������������������ѭ���ı仯4�Ž�ɫͼƬ����ɶ�̬�ƶ��ģ�
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
					}else if(ReadMapFile.map2[y/elesize-1][x/elesize]==0){//�Ϸ�û���壬���Լ��������ƶ�
						y=y+fallingSpeed;
						my=my+fallingSpeed;
						fallingSpeed = fallingSpeed + gravity;
					}
	}

	
	
	/**
	 * ��ɫ�����ƶ��ķ���
	 */
	/**public void moveUD(){
		if(up&&!shift){
			//����ס�ϼ�ʱ����up1��1����up1����20ʱ������Ϊ0�����ѭ��
			up1++;
			if(up1>=20){
				up1=0;
			}
			//�����ɫ��ǰλ���Ϸ�������ֵ��Ϊ0����ɫ�Ϸ������嵲�ţ������ﴦ����ǽ�ɫһ�������ڲ����ƶ��������ƶ�������һ��
			if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0){
				int y1 = (y/elesize-1)*elesize+elesize/2;
				int x1 = (x/elesize)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y-step;
					my=my-step;
				}
			}else if(ReadMapFile.map2[y/elesize-1][x/elesize]==0){//�Ϸ�û���壬���Լ��������ƶ�
				y=y-step;
				my=my-step;
			}
		}else if(up&&shift){
			//����ס�ϼ�ʱ����up1��1����up1����20ʱ������Ϊ0�����ѭ��
			up1++;
			if(up1>=20){
				up1=0;
			}
			//�����ɫ��ǰλ���Ϸ�������ֵ��Ϊ0����ɫ�Ϸ������嵲�ţ������ﴦ����ǽ�ɫһ�������ڲ����ƶ��������ƶ�������һ��
			if(ReadMapFile.map2[y/elesize-1][x/elesize]!=0){
				int y1 = (y/elesize-1)*elesize+elesize/2;
				int x1 = (x/elesize)*elesize+elesize/2;
				if((y-y1)*(y-y1)>=elesize*elesize){
					y=y-runstep;
					my=my-runstep;
				}
			}else if(ReadMapFile.map2[y/elesize-1][x/elesize]==0){//�Ϸ�û���壬���Լ��������ƶ�
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
	 * ��ɫ�����ƶ��ķ���
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
		//�����ɫ�����ƶ���
		if(!up&&!down&&!left&&!right){
			if(towards==1){//�����ɫ�ƶ��������Ϊ��
				if(!isOnGround()) {
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*3, 96*2, 96*4, null);
				}
				else {
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*3, 96, 96*4, null);
				}
			}else if(towards==2){//����ƶ�������
				if(!isOnGround()) {
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 0, 96*2, 96, null);
				}
				else {
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 0, 96, 96, null);
				}
			}else if(towards==3){//����ƶ�������
				if(!isOnGround()) {
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96, 96*2, 96*2, null);
				}
				else {
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96, 96, 96*2, null);
				}
			}else if(towards==4){//����ƶ�������
				if(!isOnGround()) {
					g.drawImage(walk.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 96, 96*2, 96*2, 96*3, null);
				}
				else {
					g.drawImage(walk1.getImage(), Player.px-elesize/2-15, Player.py-elesize/2-25, Player.px-elesize/2+65, Player.py-elesize/2+55, 0, 96*2, 96, 96*3, null);
				}
			}
		}else {//�����ɫ���ƶ���
			/**if(up&&!shift){
				//ͨ��up1��ֵ������������һ��ͼƬ
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
				//ͨ��up1��ֵ������������һ��ͼƬ
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
	
	
	//�õ���ɫ�������е�λ��I
	public static int getI(){
		return (y-(playersize/2))/50;
	}
	//�õ���ɫ�������е�λ��J
	public static int getJ(){
		return (x-(playersize/2))/50;
	}
}
