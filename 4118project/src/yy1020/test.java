package yy1020;
/**
 * 开始游戏
 * @author yy
 *
 */
public class test {
	public static void main(String[] args) {
		//首先从地图文件中读入地图数组
		ReadMapFile.readfile("D:\\mygame\\map\\map1.map");
		//用读到的地图数组创建游戏窗体，开始游戏
		mainFrame mf = new mainFrame();
	}
}
