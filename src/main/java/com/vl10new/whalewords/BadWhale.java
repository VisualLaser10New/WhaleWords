package com.vl10new.whalewords;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import static com.vl10new.whalewords.WordsAnalysis.*;

@WebServlet(name = "badwhale", value = "/badwhale")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 100,      // 10 MB
		maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class BadWhale extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		String filePath = "";

		try
		{
			filePath = Utilities.fileUpload(request, response);

		}
		catch(ServletException e)
		{
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}

		if(filePath.isEmpty())
		{
			//errors
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}

		ArrayList<String> badWords = Utilities.readFileFromResources("sheetWords.txt");

		ArrayList<String> worstComment = WordsAnalysis.phrasesWithWords(filePath, badWords);

		//call the jsp to generate the page
		genPage(worstComment, response, request);


	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.sendRedirect(request.getContextPath() + "/bad-sentence-upload.jsp");
	}

	public void genPage(ArrayList<String> wordsList, HttpServletResponse response, HttpServletRequest request) throws IOException
	{
		try
		{
			request.setAttribute("sentences_list", StoString(wordsList));
			request.getRequestDispatcher("badwhale-viewer.jsp").forward(request, response);
		}
		catch (ServletException e)
		{
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}

	public void destroy() {
	}
}