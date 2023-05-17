package com.vl10new.whalewords;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Utilities {
	final static String uploadDir = "D:/5Cin/TPS/Java";//getServletContext().getInitParameter("uploadDirectory");

	public static String fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Part filePart = request.getPart("uploadedFile");
		String fileName = filePart.getSubmittedFileName();

		filePart.write(uploadDir + "/tmp/" + fileName);

		return uploadDir + "/tmp/" + fileName;
	}

	public static ArrayList<String> readFileFromResources(String fileName) throws IOException, FileNotFoundException
	{
		InputStream file = com.vl10new.whalewords.WhaleWords.class.getResource(fileName).openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));

		ArrayList<String> words = new ArrayList<>();

		String data;
		while((data = reader.readLine())!= null){
			words.add(data);
			//Logger.getGlobal().warning(data);
		}

		return words;
	}



}
