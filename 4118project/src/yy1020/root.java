package yy1020;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class root {
	private static Robot robot = null;

    public root() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟键盘输入abc
     */
    public void keyBoardDemo(String s) {
    	
    	
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);

        robot.keyPress(KeyEvent.VK_B);
        robot.keyRelease(KeyEvent.VK_B);

        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
    }

    /**
     * 移动鼠标位置
     */
    public static String read() {
    	Scanner reader = new Scanner(System.in);  // Reading from System.in
    	System.out.println("");
    	System.out.println("enter the mission:");
    	String s = reader.nextLine(); // Scans the next token of the input as an int.
    	//once finished

    	return s;
    }
    public static void fix(String s) {

    		//System.out.println(s);
    		//System.out.println(s.contains("enter"));
    	if(s.contains("enter")){
    		for(int i=0;i<s.length();i++)
    		{
    		char x=s.charAt(i);
    			if(Character.isDigit(x)==true){
    				//System.out.println(x);
    				int input=Character.getNumericValue(x);  
    				//System.out.println(input);

    				long startTime = System.currentTimeMillis();
    				for(int a=0;a<input;a++) {
    					if(s.contains("a")) {
    						for(int x1=0;x1<20;x1++) {
    						robot.keyPress(KeyEvent.VK_RIGHT);
    						robot.keyPress(KeyEvent.VK_B);
    				        }
    						robot.keyRelease(KeyEvent.VK_RIGHT);
    						robot.keyRelease(KeyEvent.VK_B);
    					}
    					else if(s.contains("b"))  {
    						robot.keyPress(KeyEvent.VK_B);
    				        robot.keyRelease(KeyEvent.VK_B);
    					}
    					
    				}
    				long endTime   = System.currentTimeMillis();
    				long TotalTime = endTime - startTime;  
    				System.out.println("");
    				System.out.println(TotalTime);
    			}
    		}
    	}

    }

    public void delay(int milliseconds) {
        robot.delay(milliseconds);
    }

    public static void main(String[] args) {
    	while(true) {
    	root demo = new root();
    	fix(read());
//        demo.delay(2000);
        //demo.keyBoardDemo(read());
    	}
    }
}
