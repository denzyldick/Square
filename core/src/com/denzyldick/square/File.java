package com.denzyldick.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class File {
	
	private FileHandle file;
	
	public File(String filepath)
	{		
		 this.file = Gdx.files.local(filepath);
	}
	public void writeString(String data, boolean append)
	{
		this.file.writeString(data, append);
	}
	
	public FileHandle readFile()
	{
		return this.file;
	}
}
