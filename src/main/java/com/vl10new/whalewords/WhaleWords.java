package com.vl10new.whalewords;

import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
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
public class WhaleWords extends HttpServlet {

    final String uploadDir = "E:/5Cin/TPS/Java";//getServletContext().getInitParameter("uploadDirectory");


    public String fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Part filePart = request.getPart("uploadedFile");
        String fileName = filePart.getSubmittedFileName();

        filePart.write(uploadDir + "/tmp/" + fileName);

        return uploadDir + "/tmp/" + fileName;
    }

    public ArrayList<String> readFileFromResources(String fileName) throws IOException, FileNotFoundException
    {
        InputStream file = getClass().getResource(fileName).openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));

        ArrayList<String> words = new ArrayList<>();

        String data;
        while((data = reader.readLine())!= null){
            words.add(data);
            //Logger.getGlobal().warning(data);
        }

        return words;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String filePath = "";
        try
        {
            filePath = fileUpload(request, response);
        }
        catch(ServletException e)
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }

        if(filePath.isEmpty())
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }

        ArrayList<String> stopWords = readFileFromResources("ita-stopWords.txt");
        SortedSet<Map.Entry<String, Long>> output = occurences(filePath, stopWords);
        output = limitTo(output, 100, ORDER.ASC);

        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<p>" + StoString(output) + "</p>");

        //generate the image whale
        ImageGen img = new ImageGen(output, new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        img.imageFromMask("/whale-mask.png", new Dimension(600,600));
        String image = img.imageToB64();
        out.println("<img src='data:image/png;base64,"+image+"' alt='cloud words image'>");

        out.println("<a href='./index.jsp'>home</a><h2>Mitiche Produzioni SRL&#169;</h2><p>Produciamo cose mitiche<p></body></html>");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    public void destroy() {
    }
}