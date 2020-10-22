package com.xiaoshuo.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class FileHandle {
	private String uploadPath = ""; // 存储放入数据库时的路径

	private String fileFullName = "";
	private String fileName = "";
	private String extType = "";
	private static List<String> fileDate;

	public static List<String> getFileDate() {
		return fileDate;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileFullName() {
		return fileFullName;
	}

	public String getExtType() {
		return extType;
	}

	// 存储除文件之外其它数据
	private Map<String, String[]> parameterMap = new HashMap<String, String[]>();

	/**
	 * 获得除文件之外的其它参数
	 * 
	 * @return
	 */
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
	 * @param limit    文件扩展名数组，格式为“.扩展名”
	 * @return
	 */
	public boolean upload(HttpServletRequest request, HttpServletResponse response, String limit) {
		response.setContentType("text/html; charset=UTF-8");
		boolean isSuccess = false;
		try {
			File tmpDir = null;// 初始化上传文件的临时存放目录
			File saveDir = null;// 初始化上传文件后的保存目录
			File dateDir = null;
			ServletContext application = request.getServletContext();
			String path = application.getRealPath("/");
			tmpDir = new File(path + "/temp");
			saveDir = new File(path + "/upload");
			dateDir = new File(
					path + "/upload" + "/" + (new SimpleDateFormat("yyyyMMdd").format(new Date())).toString());

			mkDir(new File[] { tmpDir, saveDir });

			if (saveDir.exists()) {
				mkDir(dateDir);
			} else {
				throw new IllegalArgumentException("文件夹不存在！");
			}

			if (ServletFileUpload.isMultipartContent(request)) {
				// 定义限制的文件类型
				String[] ext=limit.split(",");
				SuffixFileFilter filter = new SuffixFileFilter(ext);

				DiskFileItemFactory dff = new DiskFileItemFactory();// 创建该对象
				dff.setRepository(tmpDir);// 指定上传文件的临时目录
				dff.setSizeThreshold(1024000);// 指定在内存中缓存数据大小,单位为byte
				ServletFileUpload sfu = new ServletFileUpload(dff);// 创建该对象
				sfu.setHeaderEncoding("utf-8");
				sfu.setSizeMax(2 * 1024 * 1024);// 指定单个上传文件的最大尺寸
//				sfu.setFileSizeMax(10000000);// 指定一次上传多个文件的总尺寸
				FileItemIterator fii = sfu.getItemIterator(request);// 解析request 请求,并返回FileItemIterator集合
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();// 从集合中获得一个文件流
					if (!fis.isFormField() && fis.getName().length() > 0) {// 过滤掉表单中非文件域
						fileFullName = fis.getName();// 获得上传文件的文件名
						BufferedInputStream in = new BufferedInputStream(fis.openStream());// 获得文件输入流
						fileName = getFileName(fileFullName); // 文件名
						extType = getFileExt(fileFullName); // 扩展名
						fileFullName = fileName + "." + extType; // 拼接文件名
						File file = new File(dateDir + "/" + fileFullName); // 拼接物理路径
						boolean flag = filter.accept(file); // 设置可以接受的文件类型
						if (flag) {
							BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));// 获得文件输出流
							Streams.copy(in, out, true);// 开始把文件写到你指定的上传文件夹
							this.uploadPath = "/upload" + "/"
									+ (new SimpleDateFormat("yyyyMMdd").format(new Date())).toString() + "/"
									+ fileFullName;
							isSuccess = true;// 上传成功
							out.close();
						} else {

							isSuccess = false;
						}
						in.close();
					} else {
						byte[] b = new byte[1024]; // 存储数据的字节数组
						String paraName = fis.getFieldName(); // 获得提交的名称
						fis.openStream().read(b); // 打开流，并进行读取字节存入字节数组
						String value = new String(b, 0, b.length).trim(); // 将字节数组转为字符串
						parameterMap.put(paraName, value.split(",")); // 将读取的数据存入map
					}
				}
			}else {
				parameterMap=HtmlHandle.getParameterValues(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @param filename 设置upload文件夹下的路径 ，开头带“/”
	 */
	public void download(HttpServletRequest request, HttpServletResponse response, String filename) {
		try {
			// 获取文件路径
			String fileSaveRootPath = request.getServletContext().getRealPath("/upload");
			// 获取真实的文件名
			String realname = filename;
			// 得到要下载的文件
			File file = new File(fileSaveRootPath + "/" + filename);
			// 如果文件不存在
			if (!file.exists()) {
				return;
			}

			// 文件名编码，解决中文乱码问题
			String userAgent = request.getHeader("User-Agent").toLowerCase();
			if (userAgent.contains("msie") || userAgent.contains("trident") || userAgent.contains("like gecko")
					|| userAgent.contains("edge")) {// IE浏览器

				realname = URLEncoder.encode(realname, "UTF-8");
				realname = realname.replaceAll("\\+", "%20");// 处理文件名多余的加号（+）
			} else {// 其它浏览器
				realname = new String(realname.getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setCharacterEncoding("UTF-8");
			System.out.println(1);
			// 设置响应头，控制浏览器下载该文件 可通过URLEncoder.encode(realname, "UTF-8")实现对下载的文件进行重命名
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));

			// 获取输入输出流
			InputStream is = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			// 调用common-io下面的静态方法，用于实现文件复制（从服务器端复制到本地）
			IOUtils.copy(is, os);
			is.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 获得文件名称
	 * 
	 * @param filename
	 * @return
	 */
	private String getFileName(String filename) {
		return filename.substring(0, filename.lastIndexOf("."));
	}

	/**
	 * 获得文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	private String getFileExt(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	/**
	 * 创建目录
	 * 
	 * @param dir 目录对象
	 * @return
	 */
	public static boolean mkDir(File dir) {
		boolean isOK = false;
		if (!dir.isDirectory()) {
			isOK = dir.mkdir();
		}
		return isOK;
	}

	/**
	 * 同时创建多个同级目录
	 * 
	 * @param dirs 目录对象数组
	 * @return
	 */
	public static boolean mkDir(File[] dirs) {
		boolean isOK = false;
		for (File dir : dirs) {
			if (!dir.isDirectory()) {
				if (!dir.mkdir()) {
					isOK = false;
					break;
				}
				isOK = true;
			} else {
				isOK = false;
				break;
			}
		}
		return isOK;
	}

	/**
	 * 获得目录下所有文件
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getFiles(String path) {
		List<String> files = new ArrayList<String>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				// files.add(tempList[i].toString());
				// 文件名，不包含路径
				String fileName = tempList[i].getName();
				Date date = new Date(tempList[i].lastModified());
				fileDate.add(date.toString());
				files.add(fileName);
			}
			if (tempList[i].isDirectory()) {
				// 这里就不递归了，
			}
		}
		return files;
	}

	/**
	 * 读取文件中的内容
	 * 
	 * @param realpath 绝对路径
	 * @return 返回字符串列表
	 * @throws IOException
	 */
	public static List<String> readFile(String realpath) throws IOException {
		List<String> list = new ArrayList<String>();

		java.io.File file = new java.io.File(realpath);
		// 创建一个文件字节流对象
		FileInputStream inputStream = new FileInputStream(file);

		InputStreamReader ISReader = new InputStreamReader(inputStream);

		java.io.BufferedReader reader = new java.io.BufferedReader(ISReader);

		String tempString = null;

		while ((tempString = reader.readLine()) != null)
			list.add(tempString);
		reader.close();
		return list;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param realpath 绝对路径
	 * @return
	 */
	public static boolean deleteFile(String realpath) {
		java.io.File file = new java.io.File(realpath);
		if (file.exists()) {
			return file.delete();
		} else {
			return false;
		}

	}
}
