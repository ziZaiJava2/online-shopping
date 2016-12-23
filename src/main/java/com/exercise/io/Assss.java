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
		try (InputStream input = new FileInputStream(Paths.get("./src/main/java/com/exercise/io/Assss.java").toFile());
				OutputStream os = new FileOutputStream(new File("./newTest1.log"), true);) {
			byte[] bytes = new byte[1024];
			int len = 0;
			while ((len = input.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printSelf2() {
		try (InputStream input = new FileInputStream(Paths.get("./src/main/java/com/exercise/io/Assss.java").toFile());
				Reader reader = new InputStreamReader(input, Charset.defaultCharset());

				OutputStream os = new FileOutputStream(new File("./newTest2.log"), true);
				Writer writer = new OutputStreamWriter(os);) {
			char[] line = new char[1000];
			int len = 0;
			while ((len = reader.read(line)) != -1) {
				writer.write(line, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printSelf3() {
		try (InputStream input = new FileInputStream(Paths.get("./src/main/java/com/exercise/io/Assss.java").toFile());
				Reader reader = new InputStreamReader(input, Charset.defaultCharset());
				BufferedReader bf = new BufferedReader(reader);
				OutputStream os = new FileOutputStream(new File("./newTest3.log"), true);
				Writer writer = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(writer)) {
			String line = null;
			while ((line = bf.readLine()) != null) {
				line = line + "\n";
				bw.write(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// printSelf1();
		// printSelf2();
		printSelf3();
	}
}