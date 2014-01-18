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
	 * @throws IOException
	 */
	public BufferedImage getImage(String name, String pathPrefix, boolean loadAsResource) throws IOException{
		BufferedImage tmp = imageMap.get(name);

		if(tmp==null){
			if(!pathPrefix.endsWith("/")&&!pathPrefix.endsWith("\\")){
				pathPrefix+="/";
			}
			String path = pathPrefix+name;

			if(loadAsResource){
				//TODO
				// is that even correct?
				tmp=ImageIO.read(ImageLoader.class.getResourceAsStream(path));
			}else{
				tmp = ImageIO.read(new File(URI.create(path)));
			}
		}

		return tmp;
	}

	/**
	 * 
	 * @param name
	 * @param pathPrefix
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getImage(String name, String pathPrefix) throws IOException{
		return this.getImage(name, pathPrefix, false);
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getImage(String name) throws IOException{
		return this.getImage(name, this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), true);
	}
}
