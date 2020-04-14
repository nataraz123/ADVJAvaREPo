package com.nt.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;


@WebServlet("/downloadurl")
public class DownloadServlet extends HttpServlet {
	

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("DownloadServlet.doGet()");
		File file=null;
		 String fname=null;
		 ServletContext sc=null;
		 String mimeType=null;
		 InputStream is=null;
		 OutputStream os=null;
		
		 
		 //read file name to be downloded as additonal req param value from hyperlinks
		 fname=req.getParameter("filename");
		//create File object locating and holding the file to be downloaded
		 file=new File("E:/store/"+fname);
		 //get the lenght of the file and  make it as response content length
		 res.setContentLengthLong(file.length());
		 //get Servletcontext object
		 sc=getServletContext();
		 //get MIME type of the file and make it as response MIME type
		 mimeType=sc.getMimeType("E:/store/"+fname);
		 res.setContentType(mimeType!=null?mimeType:"application/octet-stream");
		 //create Inputstream pointing to the file to be downloaded
		 is=new FileInputStream(file);
		 //create OutputStream Pointing to response object
		 os=res.getOutputStream();
		 //set values to content-disposition response header
		 res.setHeader("Content-Disposition", "attachment;fileName="+fname);
		 //copy the file content to response object
		 IOUtils.copy(is, os);
		 ///close streams
		 is.close();
		 os.close();
		 
		
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("DownloadServlet.doPost()");
		doGet(req,res);
	}

}
