package yy1020;

import javax.swing.ImageIcon;
/**
 * 游戏面板通过读取数组中的int来匹配到相应的元素图片方法类
 * @author yy
 *
 */
public class GetMap implements gameConfig{
	//通过数字匹配图片
		static ImageIcon int2icon(int num){
			if(num==0){
				return icon0;
			}else if(num==1){
				return icon1;
			}else if(num==2){
				return icon2;
			}else if(num==3){
				return icon3;
			}else if(num==100){
				return icon100;
			}else if(num==101){
				return icon101;
			}else if(num==102){
				return icon102;
			}else if(num==103){
				return icon103;
			}else if(num==150){
				return icon150;
			}
			return null;
		}
}
