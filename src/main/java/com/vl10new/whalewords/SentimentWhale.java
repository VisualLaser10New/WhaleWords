package com.vl10new.whalewords;

import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import static com.vl10new.whalewords.WordsAnalysis.*;

@WebServlet(name = "sentwhale", value = "/sentwhale")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10,      // 10 MB
		maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class SentimentWhale extends HttpServlet
{
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

		Map<String, Integer> weightWords = WordsUtilities.fromArrayListoToHashMap(Utilities.readFileFromResources("sentiment-words.txt"),"\t");

		ArrayList<String> polarizationSentences = WordsAnalysis.polarizeText(filePath, weightWords);

		//call the jsp to generate the page
		genPage(polarizationSentences, response, request);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	public void genPage(ArrayList<String> wordsList, HttpServletResponse response, HttpServletRequest request) throws IOException
	{
		try
		{
			if(wordsList.size() > 0)
				request.setAttribute("sentences_list", StoString(wordsList));
			else
				request.setAttribute("sentences_list", "No matches");

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