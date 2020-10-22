package com.xiaoshuo.servlet;

import java.io.IOException;
import java.util.logging.FileHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.xiaoshuo.common.FileHandle;

/**
 * Servlet implementation class upload
 */
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/**需要导入jspsmartupload.jar包
		 * smartUpload组件，上传文件组件，可以获得上传文件和提交的数据 上传组件使用步骤：
		 *  1、创建组件对象
		 * 2、初始化（当前上下文，request,response） 
		 * 3、设置上传的限制（文件大小，文件类型，多个文件上传个数） 
		 * 4、调用上传方法 
		 * 5、获得上传文件
		 * 6、存储文件（设置文件路径 和文件名）
		 * 
		 * 注意：检查保存目录是否存在
		 */
//		SmartUpload up=new SmartUpload();
//		up.initialize(getServletConfig(), request, response);
//		
//		//设置文件大小
//		up.setMaxFileSize(2*1024*1024);
//		up.setAllowedFilesList("jpg,png,jpeg,gif,bmp");
//		
//		
//		try {
//			up.upload();		//调用上传文件
//			String uname=up.getRequest().getParameter("uname");
//			Files files=up.getFiles(); //获得所有文件
//			String dir="upload/";
//			if(files.getCount()>0) {
//				String filename=files.getFile(0).getFileName();
//				String path=request.getServletContext().getRealPath("/");
//				System.out.println(path);
//				java.io.File f=new java.io.File(path+dir);
//				if(!f.exists()) {
//					f.mkdir();
//				}
//				//保存文件到指定目录
//				files.getFile(0).saveAs(path+dir+filename, File.SAVEAS_AUTO);
//			}else {
//				throw new Exception("请上传文件");
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		FileHandle upload=new FileHandle();
		System.out.println(upload.upload(request, response, "jpg,png,jpeg")); 
		String path=upload.getUploadPath();
		System.out.println(path);
	}

}
