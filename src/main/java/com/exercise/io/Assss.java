package com.exercise.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class Assss {

	public static void printSelf1() {
		try (	
				InputStream input = new FileInputStream(Paths.get("./src/main/java/com/io/Output.java").toFile());
				
				OutputStream os = new FileOutputStream(new File("./newTest2.log"), true);
				Writer writer = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(writer)
		) {	
			byte[] bytes = new byte[1024];		
			int len = 0;
			String a = new String();
			while ((len = input.read(bytes)) != 0) {
				a = a + "\n" + new String(bytes, 0 ,len );
				System.out.println(bytes);
			}
			bw.write(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printSelf2() {
		try (	
				InputStream input = new FileInputStream(Paths.get("./src/main/java/com/io/Output.java").toFile());
				Reader reader = new InputStreamReader(input, Charset.defaultCharset());

				OutputStream os = new FileOutputStream(new File("./newTest2.log"), true);
				Writer writer = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(writer)
		) {	
			char[] line  = new char[1000];
			String a = new String();
			int len = 0;
			while ((len = reader.read(line)) != -1) {
				a = a + "\n" + new String(line, 0 ,len );
				System.out.println(line);
			}
			bw.write(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printSelf3() {
		try (	
				InputStream input = new FileInputStream(Paths.get("./src/main/java/com/io/Output.java").toFile());
				Reader reader = new InputStreamReader(input, Charset.defaultCharset());
				BufferedReader bf = new BufferedReader(reader);

				OutputStream os = new FileOutputStream(new File("./newTest2.log"), true);
				Writer writer = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(writer)
		) {	
			String line = null;
			String a = new String();
			while ((line = bf.readLine())!= null) {
				a = a + "\n" + line;
				System.out.println(line);
			}
			bw.write(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		printSelf1();
		printSelf2();
		printSelf3();
	}
}
