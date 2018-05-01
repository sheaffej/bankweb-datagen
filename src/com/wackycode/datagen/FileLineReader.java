package com.wackycode.datagen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLineReader {
	private BufferedReader br;
	private File file;
	
	public FileLineReader(File file) {
		this.file = file;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.err.println("FileLineReader could not initialize on file: " + file);
			e.printStackTrace();
		}	
	} // constructor
	
	
	public String readLine() {
		try {
			return br.readLine();
		} catch (IOException e) {
			System.err.println("FileLineReader could read a line from file: " + file);
			e.printStackTrace();
		}
		return null;
	} // readLine()
	
	public void close() {
		try {
			br.close();
		} catch (IOException e) {
			System.err.println("FileLineReader: Exception when closing file");
			e.printStackTrace();
		}
	} // close()	
}
