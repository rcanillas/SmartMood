package MyTest;






import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.ufl.digitalworlds.j4k.J4KSDK;


public class KinectVideoManager extends J4KSDK {

	int windowWidth;
	int windowHeight;
	byte[] videoBuffer;
	FileOutputStream f;
	SmileDetector smiley = new SmileDetector();
	int smile_counter =0;
	String serverUrl;
	
	public void setServerUrl(String url){
		serverUrl = url;
	}
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	public byte[] getVideoBuffer() {
		return videoBuffer;
	
	}
	

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
	    BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
	    Graphics2D g = dimg.createGraphics();  
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	    RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
	    g.drawImage(img, 0, 0, newW, newH, null);  
	    g.dispose();  
	    return dimg;  
	}  
	
	
	public void writeVideoFramePNG(int video_width, int video_height ,byte array[])
	{
		try {
			f = new FileOutputStream("D:\\xampp\\htdocs\\img\\captured_img.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			
			BufferedImage img=new BufferedImage(video_width,video_height,BufferedImage.TYPE_INT_RGB);
			int idx=0;
			for(int y=0;y<video_height;y++)
			for(int x=0;x<video_width;x++)
			{
				img.setRGB(x,y,(new Color(array[idx+2]&0xFF,array[idx+1]&0xFF,array[idx+0]&0xFF)).getRGB());
				idx+=4;
			}
			
			BufferedImage scaledImg = resize(img,1920/2,1080/2);
			ImageIO.write(scaledImg,"PNG", f);
		} catch (IOException e) {
        	e.printStackTrace();
        }
        System.out.println("Image saved");
        // Upload image here
        
	}
	
	@Override
	public void onColorFrameEvent(byte[] color_frame) {
		
		windowWidth = getColorWidth();
		windowHeight = getColorHeight();
		
		//windowWidth = 800;
		//windowHeight = 600;
		
		videoBuffer = color_frame;
		
	
		writeVideoFramePNG(windowWidth,windowHeight,color_frame);
		
		smiley.detectSmile(serverUrl + "//img//captured_img.png");
        if (smiley.getSmile_value()>50)
        {
        	smile_counter = smile_counter +1;
        	System.out.println("Smile");
        	System.out.println(smile_counter+" smile(s) detected");
        	smile_counter = 0;
        }
        else
        {
        	System.out.println("No smile");
        	smile_counter = 0;
        }
		
		
		
	}

	@Override
	public void onDepthFrameEvent(short[] arg0, byte[] arg1, float[] arg2,
			float[] arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkeletonFrameEvent(boolean[] arg0, float[] arg1,
			float[] arg2, byte[] arg3) {
		// TODO Auto-generated method stub
		
	}

	public void setColorSize(int i, int j) {
		// TODO Auto-generated method stub
		windowWidth=i;
		windowHeight=j;
	}
	
	

}

