package yy1020;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
/**
 * �����ͼ�ļ�
 * @author yy
 *
 */
public class ReadMapFile {
	//���徲̬���������飬��������ӵ�ͼ�ļ��ж�ȡ����������ͼ����
	static int[][] map1;
	static int[][] map2;
	static int[][] map3;
	/**
	 * �����ͼ
	 * @param path ��ͼ�ļ�λ��
	 */
	static void readfile(String path){
		try{
			//��path·���µĵ�ͼ�ļ��еõ��ļ�������
			FileInputStream fis = new FileInputStream(path);
			//���ļ���������װ�ɻ�������������
			DataInputStream dis = new DataInputStream(fis);
			//������ʱ���˳�����ζ�����ͼ�ļ��е�������ͼ����
			int i = dis.readInt();
			int j = dis.readInt();
			map1 = new int[i][j];
			map2 = new int[i][j];
			map3 = new int[i][j];
			for(int ii=0;ii<i;ii++){
				for(int jj=0;jj<j;jj++){
					map1[ii][jj] = dis.readInt();
					map2[ii][jj] = dis.readInt();
					map3[ii][jj] = dis.readInt();
				}
			}
			dis.close();
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
