package com.company.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class StreamTest {

	public static void main(String[] args) throws Exception {
		
		/* 문자열 보내고 받기 */
//		FileReader fr = new FileReader("D:\\Temp\\sample.txt");
//		int c ;
//		while((c = fr.read() ) != -1 ) {
//			System.out.println((char)c);
//		}
//		fr.close();
		
//		BufferedReader br = new BufferedReader (new FileReader("D:\\Temp\\sample.txt"));
//		BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Temp\\sample2.txt"));
//		String line;
//		while(true) {
//			 line = br.readLine();
//			 if(line==null) break;
//			 bw.write(line+"\n");
//		}
//		 br.close();
//		 bw.close();
		
		/*이미지파일 보내고 받기*/
		
		BufferedInputStream br = new BufferedInputStream(new FileInputStream("D:\\Temp\\flower.jpg"));
		BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("D:\\Temp\\flower2.jpg"));
		int cnt;
		byte[] b = new byte[100];
		while(true) {
			cnt = br.read(b);
			if(cnt == -1) break;
			bw.write(b);
		}
		br.close();
		bw.close();
	}

}
