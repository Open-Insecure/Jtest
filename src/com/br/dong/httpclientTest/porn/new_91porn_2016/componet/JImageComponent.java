/**
 * @changes v1.2.0 2014-07-08 <br>
 *          PaintImmediately methods overridden to facilitate optimised
 *          redrawing.
 * 
 * @changes v1.2.0 2014-07-08 <br>
 *          The bounds for the component are sent when creating an image. This
 *          helps the VM rendering engine do its job properly when the image,
 *          for example, is used in a scrollable panel.
 * 
 * @changes v1.1.0 2008-10-28 <br>
 *          The Graphics object returned is now directly linked to the "bitmap"
 *          image.
 */
package com.br.dong.httpclientTest.porn.new_91porn_2016.componet;

import com.br.dong.httpclientTest.CrawlerUtil;
import com.br.dong.httpclientTest.porn.VedioBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * The JImageComponent class is an object that eases the programming workload of
 * managing the display of a <code>BufferedImage</code>.
 * 
 * @author Osmund G. Francis
 * @version 1.2.0 <br>
 *          Created: 01 October 2008<br>
 *          Revised: 08 July 2014
 */
public class JImageComponent extends JComponent implements MouseListener {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 4590780102881395602L;

	/** Holds the BufferedImage for the image. */
	private BufferedImage bufferedImage = null;

	/** Holds the <code>Graphics</code> for the image. */
	private Graphics imageGraphics = null;

	/**
	 * Constructs a new JImageComponent object.
	 */
	public JImageComponent() {
		/**新增点击事件*/
		this.addMouseListener(this);
	}
	
	/**
	 * Constructs a new JImageComponent object.
	 * 
	 * @param bufferedImage
	 *        Image to load as default image.
	 */
	public JImageComponent(BufferedImage bufferedImage) {
		this.setBufferedImage(bufferedImage);
	}
	
	/**
	 * Constructs a new JImageComponent object.
	 * 
	 * @param bufferedImage
	 *        Image to load as default image.
	 * @param name
	 *        Name of image.
	 */
	public JImageComponent(BufferedImage bufferedImage, String name) {
		this(bufferedImage);
		super.setName(name);
	}
	
	/**
	 * Constructs a new JImageComponent object.
	 * 
	 * @param imageIcon
	 *        ImageIcon to load as default image.
	 */
	public JImageComponent(ImageIcon imageIcon) {
		this.bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		this.imageGraphics = this.bufferedImage.createGraphics();
		this.imageGraphics.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight(), null);
	}
	
	/**
	 * Constructs a new JImageComponent object.
	 * 
	 * @param imageIcon
	 *        ImageIcon to load as default image.
	 * @param name
	 *        Name of image.
	 */
	public JImageComponent(ImageIcon imageIcon, String name) {
		this(imageIcon);
		super.setName(name);
	}
	
	/**
	 * Constructs a new JImageComponent object.<br>
	 * 
	 * @param width
	 *        Width of image.
	 * @param height
	 *        Height of image.
	 * @param imageType
	 *        Image type.
	 * @throws IllegalArgumentException
	 *         When width <= 0, height <= 0, or imageType invalid.
	 * @see BufferedImage#BufferedImage(int, int, int)
	 */
	public JImageComponent(int width, int height, int imageType) throws IllegalArgumentException {
		BufferedImage bufferedImage = new BufferedImage(width, height, imageType);
		this.setBufferedImage(bufferedImage);
	}
	
	/**
	 * Constructs a new JImageDevice object.
	 * 
	 * @param width
	 *        Width of image.
	 * @param height
	 *        Height of image.
	 * @param type
	 *        Image type.
	 * @param name
	 *        Name of image.
	 * @throws IllegalArgumentException
	 *         When width <= 0, height <= 0, or imageType invalid.
	 * @see BufferedImage#BufferedImage(int, int, int)
	 */
	public JImageComponent(int width, int height, int type, String name) {
		this(width, height, type);
		this.setName(name);
	}
	
	/**
	 * Returns the <code>BufferedImage</code> object for the image.
	 * 
	 * @return The buffered image.
	 */
	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}
	
	/**
	 * Returns the <code>Graphics</code> object for the image.
	 */
	@Override
	public Graphics getGraphics() {
		return this.imageGraphics;
	}
	
	/**
	 * Returns the height of the image.
	 */
	@Override
	public int getHeight() {
		if (this.bufferedImage == null) {
			return 0;
		}
		
		return this.bufferedImage.getHeight();
	}
	
	/**
	 * Returns the size of the image.
	 */
	@Override
	public Dimension getPreferredSize() {
		if (this.bufferedImage == null) {
			return new Dimension(0, 0);
		}
		
		return new Dimension(this.bufferedImage.getWidth(), this.bufferedImage.getHeight());
	}
	
	/**
	 * Returns the size of the image.
	 */
	@Override
	public Dimension getSize() {
		if (this.bufferedImage == null) {
			return new Dimension(0, 0);
		}
		
		return new Dimension(this.bufferedImage.getWidth(), this.bufferedImage.getHeight());
	}
	
	/**
	 * Returns the width of the image.
	 */
	@Override
	public int getWidth() {
		if (this.bufferedImage == null) {
			return 0;
		}
		
		return this.bufferedImage.getWidth();
	}
	
	/**
	 * Loads image from a file.
	 * 
	 * @param imageLocation
	 *        File to image
	 * @throws IOException
	 *         Throws an <code>IOException</code> if file cannot be loaded
	 */
	public void loadImage(File imageLocation) throws IOException {
		this.bufferedImage = ImageIO.read(imageLocation);
		this.setBufferedImage(this.bufferedImage);
	}

	/***
	 * 从一个验证host的网址加载一张图片
	 * @param bean 视频信息
	 * @throws Exception
	 */
	public void loadImage(VedioBean bean,CrawlerUtil crawlerUtil) throws Exception {
		HttpResponse res= crawlerUtil.noProxyGetUrl(bean.getPreImgSrc()) ;
		System.out.println(res.getStatusLine().getStatusCode());
 		/**有的时候会报出404错误 这个时候需要替换成一张本地的默认图片*/
		if (HttpStatus.SC_OK == res.getStatusLine().getStatusCode()) {
			//请求成功
			HttpEntity entity = res.getEntity();
			if (entity != null && entity.isStreaming()) {
				InputStream is = entity.getContent();
				this.bufferedImage = ImageIO.read(is);
				this.setBufferedImage(this.bufferedImage);
			}
		}else{
			/**获取图片失败加载默认图片*/
			loadImage(Object.class.getResource("/com/br/dong/httpclientTest/porn/new_91porn_2016/img/default.png"));
		}
	}
	/**
	 * 从网络连接中加载一张图片,此种方式只适合纯粹的网络图片，即不会验证请求的host
 	 * @param imageLocation
	 * @throws IOException
	 */
	public void loadImage(URL imageLocation) throws IOException {
		this.bufferedImage = ImageIO.read(imageLocation);
		this.setBufferedImage(this.bufferedImage);
	}
	
	/**
	 * Paints the image onto the component.
	 * 
	 * @param g
	 *        The <code>Graphics</code> object of the component onto which the
	 *        image region will be painted.
	 * @param x
	 *        The x value of the region to be painted.
	 * @param y
	 *        The y value of the region to be painted.
	 * @param width
	 *        The width of the region to be painted.
	 * @param height
	 *        The width of the region to be painted.
	 */
	private void paintImmediately(Graphics g, int x, int y, int width, int height) {
		
		// Exit if no image is loaded.
		if (this.bufferedImage == null) {
			return;
		}
		
		int imageWidth = this.bufferedImage.getWidth();
		int imageHeight = this.bufferedImage.getHeight();
		
		// Exit if the dimension is beyond that of the image.
		if (x >= imageWidth || y >= imageHeight) {
			return;
		}
		
		// Calculate the rectangle of the image that should be rendered.
		int x1 = x < 0 ? 0 : x;
		int y1 = y < 0 ? 0 : y;
		int x2 = x + width - 1;
		int y2 = y + height - 1;
		
		if (x2 >= imageWidth) {
			x2 = imageWidth - 1;
		}
		
		if (y2 >= imageHeight) {
			y2 = imageHeight - 1;
		}
		
		// Draw the image.
		g.drawImage(this.bufferedImage, x1, y1, x2, y2, x1, y1, x2, y2, null);
	}
	
	/*
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		
		// Exit if no image is loaded.
		if (this.bufferedImage == null) {
			return;
		}
		
		// Paint the visible region.
		Rectangle rectangle = this.getVisibleRect();
		paintImmediately(g, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	};
	
	/*
	 * @see javax.swing.JComponent#paintImmediately(int, int, int, int)
	 */
	@Override
	public void paintImmediately(int x, int y, int width, int height) {
		
		// Exit if no image is loaded.
		if (this.bufferedImage == null) {
			return;
		}
		
		// Paint the region specified.
		this.paintImmediately(super.getGraphics(), x, y, width, height);
	}
	
	/*
	 * @see javax.swing.JComponent#paintImmediately(java.awt.Rectangle)
	 */
	@Override
	public void paintImmediately(Rectangle rectangle) {
		
		// Exit if no image is loaded.
		if (this.bufferedImage == null) {
			return;
		}
		
		// Paint the region specified.
		this.paintImmediately(super.getGraphics(), rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	/**
	 * Resizes the image to the given dimensions and type. <br>
	 * Note that the image is "cropped" from the left top corner).
	 * 
	 * @param width
	 *        The new width of the image.
	 * @param height
	 *        The new height of the image.
	 * @param imageType
	 *        The new image type (<code>BufferedImage</code> type)
	 */
	public void resize(int width, int height, int imageType) {
		
		// Create a new image if none is loaded.
		if (this.bufferedImage == null) {
			setBufferedImage(new BufferedImage(width, height, imageType));
			return;
		}
		
		// Create a new temporary image.
		BufferedImage tempImage = new BufferedImage(width, height, imageType);
		int w = this.bufferedImage.getWidth();
		int h = this.bufferedImage.getHeight();
		
		// Crop width if necessary.
		if (width < w) {
			w = width;
		}
		
		// Crop height if necessary.
		if (height < h) {
			h = height;
		}
		
		// Copy if the type is the same.
		if (this.bufferedImage.getType() == imageType) {
			
			Graphics g = tempImage.getGraphics();
			g.drawImage(this.bufferedImage, 0, 0, w, h, null);
		}
		
		// Copy pixels to force conversion.
		else {
			
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					tempImage.setRGB(x, y, this.bufferedImage.getRGB(x, y));
				}
			}
		}
		
		// Set the new image.
		setBufferedImage(tempImage);
	}
	
	/**
	 * Sets the buffered image, and updates the components bounds.
	 * 
	 * @param bufferedImage
	 *        The buffered image to set to.
	 */
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		
		// Clear the graphics object if null image specified.
		// Clear the component bounds if null image specified.
		if (this.bufferedImage == null) {
			this.imageGraphics = null;
			this.setBounds(0, 0, 0, 0);
		}
		// Set the graphics object.
		// Set the component's bounds.
		else {
			int width =this.bufferedImage.getWidth();
			int height = this.bufferedImage.getHeight();
			this.imageGraphics = this.bufferedImage.createGraphics();
			this.setBounds(0, 0, width,height );
		}
	}

	/**
	 * 鼠标单击触发
	 * */
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("aaaaaaaaaa");
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * 鼠标划过触发
	 * */
	@Override
	public void mouseEntered(MouseEvent e) {
	  this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	/**
	 * 鼠标划出触发
	 * */
	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}
