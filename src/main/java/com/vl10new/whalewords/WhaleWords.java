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

    final String uploadDir = "D:/5Cin/TPS/Java";//getServletContext().getInitParameter("uploadDirectory");

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
        int limitNo = 0;
        try
        {
            filePath = fileUpload(request, response);
            limitNo = Integer.parseInt(request.getParameter("limitNo"));
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
        output = limitTo(output, limitNo, ORDER.ASC);


        //generate the image whale
        ImageGen img = new ImageGen(output, new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        img.imageFromMask(getClass().getResource("whale-mask.png").getPath(), new Dimension(680,680));
        String image = img.imageToB64();

        //print page
        String body = "";
        body += "<img src='data:image/png;base64,"+image+"' alt='cloud words image'>";
        body+= "<p>"+StoString(output)+"</p>";

        genPage(body, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    public void genPage(String body, HttpServletResponse response) throws IOException
    {
        //standardize the pages
        PrintWriter out = response.getWriter();
        out.println("<html>" +
                "<head>" +
                "<title>Whale Words</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "    <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>\n" +
                "</head>" +
                "<body>");

        //define pages header here
        out.println("<header></header>");


        //print body page here
        out.println(body);


        //define pages footer here
        out.println("<footer>" +
                "<a  class='btn btn-primary' href='./index.jsp'>home</a><h2>Mitiche Produzioni SRL&#169;</h2><p>Produciamo cose mitiche<p>" +
                "</footer>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}