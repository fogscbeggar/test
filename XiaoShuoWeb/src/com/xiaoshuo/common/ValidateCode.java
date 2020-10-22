package com.xiaoshuo.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class ValidateCode {

	private int count=4; //字符数
	private int width; //验证码宽度
	private int height=32;  //验证码
	private String str="1234567890ABCDEFGHIJKMNOPQRSTUVWXYZ";
	private String[] font={"宋体","华文楷体","黑体","华文隶书","微软雅黑","楷体_GB2312","隶书"};
	private int fontSize=26; //字体大小
	private int lineCount=10;  //干扰线条个数
	private int pointCount=200;  //干扰点个数
	private Boolean usePoint=true;  //是否添加干扰点
	private Random rand=new Random();  //随机对象
	private Graphics2D g;  //画板
	private String text;  //验证码字符串
	
	/**
	 * 生成验证码
	 * @param out  输出对象
	 * @param imageType  图片格式：png,jpg,bmp,gif
	 */
	public ValidateCode(OutputStream out,String imageType) {
		outImage(out, imageType);
	}
	
	/**
	 * 生成验证码
	 * @param out 输出对象
	 * @param imageType  图片格式：png,jpg,bmp,gif
	 * @param count 字符个数
	 */
	public ValidateCode(OutputStream out,String imageType,int count) {
		this.count=count;
		outImage(out, imageType);
	}
	
	public ValidateCode(OutputStream out,String imageType,Boolean usePoint) {
		this.usePoint=usePoint;
		outImage(out, imageType);
	}
	
	/**
	 * 生成验证码
	 * @param out 输出对象
	 * @param imageType 图片格式：png,jpg,bmp,gif
	 * @param count 字符个数
	 * @param usePoint 是否使用干扰点
	 */
	public ValidateCode(OutputStream out,String imageType,int count,boolean usePoint) {
		this.count=count;
		this.usePoint=usePoint;
		outImage(out, imageType);
	}
	
	//输出图像
	private void outImage(OutputStream out,String imageType) {
		BufferedImage im=getImage();  //获得图像对象
		try {
			ImageIO.write(im, imageType, out); //输出图像对象
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//生成图像
	private BufferedImage getImage() {
		width=fontSize*count+10; //计算图像宽度
		//创建图像对象，并设置宽，高，图像使用的颜色格式
		BufferedImage im=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//从图像对象中获得画板
		g=(Graphics2D)im.getGraphics();
		//设置画板背景
		g.setColor(new Color(255,255,255));
		//填充背景
		g.fillRect(0, 0, width, height);
		//创建字符串构建器
		StringBuilder s=new StringBuilder();
		
		for (int i = 0; i < count; i++) {
			g.setColor(getColor());//获得随机颜色
			//设置随机字全
			g.setFont(new Font(getFont(), getFontStyle(), rand.nextInt(5)+fontSize));
			//获得一个字符
			String c=getChar();
			s.append(c); //将当前字符添加到字符串构建器中
			//画当前字符
			g.drawString(c, i*fontSize, (height-fontSize)/2+fontSize);
		}
		
		drawRandLine(); //画干扰线
		
		if(usePoint)drawRandPoint();  //判断是否使用干扰点
		
		text=s.toString();  //将验证码保存
		
		return im;
	}

	//随机生成一个字符
	private String getChar() {
		return str.charAt(rand.nextInt(str.length()))+"";
	}
	
	//随机生成一个颜色对象
	private Color getColor() {
		return new Color(rand.nextInt(180),rand.nextInt(180),rand.nextInt(180));
	}
	
	//随机获得字体名称
	private String getFont() {
		return font[rand.nextInt(font.length)];
	}
	
	//随机获得字体样式
	private int getFontStyle() {
		return rand.nextInt(4);
	}
	
	//画随机干扰线
	private void drawRandLine() {
		for (int i = 0; i < lineCount; i++) {
			g.setColor(getColor());
			g.drawLine(rand.nextInt(width), rand.nextInt(height), rand.nextInt(width), rand.nextInt(height));
		}
	}
	
	//画随机干扰点
	private void drawRandPoint() {
		for (int i = 0; i < pointCount; i++) {
			g.setColor(getColor());
			g.drawOval(rand.nextInt(width), rand.nextInt(height), 1, 1);
		}
	}
	
	public String getText() {
		return text;
	}
	
	
}
