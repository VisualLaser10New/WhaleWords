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

@WebServlet(name = "wwords", value = "/wwords")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10,      // 10 MB
		maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class WhaleWords extends HttpServlet
{
	public String getOutImg(SortedSet<Map.Entry<String, Long>> wordList) throws IOException
	{
		//generate the image whale
		String maskPath = getClass().getResource("whale-mask.png").getPath();
		BufferedImage maskImg = ImageIO.read(new File(maskPath));

		ImageGen imgout = new ImageGen(wordList, new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		imgout.imageFromMask(maskPath, new Dimension(maskImg.getWidth(),maskImg.getHeight()));
		return imgout.imageToB64();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		String filePath = "";
		int limitNo = 0;
		try
		{
			filePath = Utilities.fileUpload(request, response);
			limitNo = Integer.parseInt(request.getParameter("limitNo"));
		}
		catch(ServletException e)
		{
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}

		if(filePath.isEmpty() || limitNo < 1)
		{
			//errors
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}

		ArrayList<String> stopWords = Utilities.readFileFromResources("ita-stopWords.txt");
		//ArrayList<String> stopWords = new ArrayList<>();
		SortedSet<Map.Entry<String, Long>> outputList = occurences(filePath, stopWords);
		outputList = limitTo(outputList, limitNo, ORDER.ASC);

		String image = getOutImg(outputList);

		//call the jsp to generate the page
		genPage(image, outputList, response, request);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	public void genPage(String image, SortedSet<Map.Entry<String, Long>> wordsList, HttpServletResponse response, HttpServletRequest request) throws IOException
	{
		try
		{
			request.setAttribute("cloud_image", "data:image/png;base64,"+image);
			request.setAttribute("words_list", StoString(wordsList));
			request.getRequestDispatcher("npl-imgview.jsp").forward(request, response);
		}
		catch (ServletException e)
		{
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}

	public void destroy() {
	}
}