package io.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static ImageLoader instance;
	public static HashMap<String, BufferedImage> imageMap;

	/**
	 * 
	 */
	private ImageLoader(){
		ImageLoader.imageMap=new HashMap<>();
	}

	/**
	 * 
	 * @return
	 */
	public static ImageLoader getInstance(){
		if(ImageLoader.instance==null){
			ImageLoader.instance=new ImageLoader();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param name
	 * @param pathPrefix
	 * @param loadAsResource
	 * @return
	 */
	public BufferedImage getImage(String name){
		return imageMap.get(name);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean hasImage(String name){
		return imageMap.get(name)!=null;
	}
	
	/**
	 * 
	 * @param name
	 * @param folder
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public BufferedImage loadImage(String name, String folder, String path) throws IOException{
		if(path!=null&&!path.equals("")&&!path.endsWith("/")&&!path.endsWith("\\")){
			path+="/";
		}else if(path==null||path.equals("")){
			// TODO
			// does that do the trick?
			// : if engine is in other JAR file...?
			path=ImageLoader.class.getProtectionDomain().getCodeSource().getLocation().toString();
			path=path.substring(0, path.lastIndexOf("/"))+"/";
		}
		
		if(folder==null){
			folder="";
		}
		
		String filePath = path+folder+name;
		System.out.println(filePath);
		BufferedImage tmp = ImageIO.read(new File(URI.create(filePath)));
		imageMap.put(name, tmp);
		return tmp;
	}
	
	/**
	 * 
	 * @param name
	 * @param folder
	 * @throws IOException 
	 */
	public BufferedImage loadImage(String name, String folder) throws IOException{
		return loadImage(name, folder, null);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public BufferedImage loadImage(String name) throws IOException{
		return loadImage(name, null, null);
	}

}
