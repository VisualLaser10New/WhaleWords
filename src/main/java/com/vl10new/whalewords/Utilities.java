package com.vl10new.whalewords;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class Utilities {
	final static String uploadDir = "E:/5Cin/TPS/Java";//getServletContext().getInitParameter("uploadDirectory");

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

	public static Iterable<String> readTextYield(String filePath) throws IOException
	{
		FileReader file = new FileReader(filePath);
		BufferedReader myReader = new BufferedReader(file);
		final String[] data = {myReader.readLine()};

		return new Iterable<String>()
		{

			@Override
			public Iterator<String> iterator()
			{
				return new Iterator<String>()
				{

					@Override
					public boolean hasNext() {
						// TODO code to check next
						try {
							data[0] = myReader.readLine();
							return (data[0] != null);
						}
						catch(Exception e)
						{
							try {
								myReader.close();
							} catch (IOException ex) {
								return false;
							}
							return false;
						}
					}

					@Override
					public String next() {
						// TODO code to go to next
						return data[0];
					}

					@Override
					public void remove() {
						try {
							myReader.close();
						} catch (IOException e) {

						}
						// TODO code to remove item or throw exception
					}

				};
			}
		};

	}

	/*public static Iterable<String> readTextYield(String filePath) throws IOException
	{
		FileReader file = new FileReader(filePath);

		BufferedReader myReader = new BufferedReader(file);

		String data = myReader.readLine();

		while (data != null)
		{
			yieldReturn(myReader.readLine());
		}
		myReader.close();
	}*/

}
