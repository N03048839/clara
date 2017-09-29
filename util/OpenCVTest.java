package spineReader;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;

import java.math.*;

public class OpenCVTest
{
	static String filename = "spines9.jpg";
	
   public static void main( String[] args ) throws IOException
   {
      System.out.println("Loading library...");
	  System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
      
      for (String filename : listFileNames())
      {
    	  System.out.println("loading " + filename + " with opencv...");
          Mat image = Imgcodecs.imread(filename);
          
          // --- sandbox: test code goes here
          //displayImage(Mat2BufferedImage(image), "original");
          
          int bf = 7;
          
          Mat blur = blur(image, bf, 1.3);
          //displayImage(Mat2BufferedImage(blur), "blur");
          
          Mat blurgs = blur.clone();
          //Imgproc.cvtColor(blur, blurgs, Imgproc.COLOR_BGR2GRAY);
          //displayImage(Mat2BufferedImage(blurgs), "threshold");
          
          Mat canny = canny(blurgs, 80, 60);
          //displayImage(Mat2BufferedImage(canny), "canny");
          
          Mat cannyColor = canny.clone();
          Imgproc.cvtColor(canny, cannyColor, Imgproc.COLOR_GRAY2BGR);
          Mat cannyOv = canny.clone();
          Core.add(image, cannyColor, cannyOv);
          //displayImage(Mat2BufferedImage(cannyOv), "canny overlay");
          
          double VERTICAL = Math.PI + (Math.PI / 2);
          double HORIZONTAL = Math.PI;
          double ALL = Math.PI / 180;
          
          Mat hough = getHoughTransform(canny, 1.0, ALL, 1);
          
          Mat houghOv = canny.clone();
          displayImage(Mat2BufferedImage(hough), "hough");
          Core.add(image, hough, houghOv);
          
          //displayImage(Mat2BufferedImage(houghOv), "hough overlay");
          //writeMat(hough, filename + "_hough.jpg");
          
      }
   }
   
   
   /** 
    * Get a set of Hough lines for the current image.
    * @param image The image to scan
    * @param rho Resolution of Rho, in pixels.
    * 
    * 				Rho corresponds to a line between the origin (upper left)
    * 				and the resultant Hough line, with which it forms a right angle.
    * 				Rho is the length of this line.
    * 				
    * @param theta Resolution of Theta, in radians.
    * 
    * 				Theta corresponds to a line between the origin (upper left)
    * 				and the resultant Hough line, with which it forms a right angle.
    * 				Theta is the angle between this line and the horizontal.
    * 
    * @param threshold The number of matches in Hough space needed to constitute a line
    * @return
    */
   public static Mat getHoughPTransform(Mat image, double rho, double theta, int threshold) {
	    
	   System.out.println("   Running Hough Pred. Transform...");
	   Mat result = image.clone();								// initialize 'result'
	   Imgproc.cvtColor(image, result, Imgproc.COLOR_GRAY2BGR); // change type of 'result' to color
	    
	   Mat lines = new Mat();								
	   Imgproc.HoughLinesP(image, lines, rho, theta, threshold);
	    
	    System.out.println("      Lines found: " + lines.cols());
	    for (int i = 0; i < lines.cols(); i++) {
	        double[] val = lines.get(0, i);
	        Imgproc.line(result, new Point(val[0], val[1]), new Point(val[2], val[3]), new Scalar(0, 0, 255), 2);
	    }
	    return result;
	}
   
   
   /** 
    * Get a set of Hough lines for the current image.
    * @param image The image to scan
    * @param rho Resolution of Rho, in pixels.
    * 
    * 				Rho corresponds to a line between the origin (upper left)
    * 				and the resultant Hough line, with which it forms a right angle.
    * 				Rho is the length of this line.
    * 				
    * @param theta Resolution of Theta, in radians.
    * 
    * 				Theta corresponds to a line between the origin (upper left)
    * 				and the resultant Hough line, with which it forms a right angle.
    * 				Theta is the angle between this line and the horizontal.
    * 
    * @param threshold The number of matches in Hough space needed to constitute a line
    * @return
    */
   public static Mat getHoughTransform(Mat image, double rho, double theta, int threshold) {
	   System.out.println("   Running Hough Transform...");
	    Mat result = image.clone();
	    Imgproc.cvtColor(image, result, Imgproc.COLOR_GRAY2BGR);
	    Mat lines = new Mat();
	    Imgproc.HoughLines(image, lines, rho, theta, threshold);

	    System.out.println("      Lines found: " + lines.cols());
	    for (int i = 0; i < lines.cols(); i++) {
	        double data[] = lines.get(0, i);
	        double rho1 = data[0];
	        double theta1 = data[1];
	        double cosTheta = Math.cos(theta1);
	        double sinTheta = Math.sin(theta1);
	        double x0 = cosTheta * rho1;
	        double y0 = sinTheta * rho1;
	        Point pt1 = new Point(x0 + 10000 * (-sinTheta), y0 + 10000 * cosTheta);
	        Point pt2 = new Point(x0 - 10000 * (-sinTheta), y0 - 10000 * cosTheta);
	        Imgproc.line(result, pt1, pt2, new Scalar(0, 0, 255), 2);
	    }
	    return result;
	}
   
   
   public static Mat canny(Mat image, double thr1, double thr2) {
	   Mat dest = image.clone();
	   Imgproc.Canny(image, dest, thr1, thr2, 3, true);
	   return dest;
   }
   
   
   public static Mat blur(Mat image, int ksize, double sigma) {
	   Mat dest = image.clone();
	   Size size = new Size(ksize, ksize);
	   Imgproc.GaussianBlur(image, dest, size, sigma);
	   return dest;
   }
   
   
   public static BufferedImage Mat2BufferedImage(Mat m){
	// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
	// Fastest code
	// The output can be assigned either to a BufferedImage or to an Image

	    int type = BufferedImage.TYPE_BYTE_GRAY;
	    if ( m.channels() > 1 ) {
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    }
	    int bufferSize = m.channels()*m.cols()*m.rows();
	    byte [] b = new byte[bufferSize];
	    m.get(0,0,b); // get all the pixels
	    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
	    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    System.arraycopy(b, 0, targetPixels, 0, b.length);  
	    return image;

	}
   
   
   public static void displayImage(Image img2, String title)
   {   
       //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
       ImageIcon icon=new ImageIcon(img2);
       JFrame frame=new JFrame();
       frame.setLayout(new FlowLayout());        
       frame.setSize(img2.getWidth(null)+50, img2.getHeight(null)+50);     
       JLabel lbl=new JLabel();
       lbl.setIcon(icon);
       frame.add(lbl);
       frame.setTitle(title);
       frame.setVisible(true);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   }
   
   public static void displayMat(Mat image, String title) {
	   displayImage(Mat2BufferedImage(image), title);
   }
   
   public static void writeMat(Mat image, String filename) {
	   Imgcodecs.imwrite(filename, image);
   }
   
   public static String[] listFileNames() {
	   String path = System.getProperty("user.dir");
	   
	   File directory = new File(path);
	   File[] contents = directory.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.matches(".*([Jj][Pp][Gg])|.*([Pp][Nn][Gg])|.*([Bb][Mm][Pp])"))
						return true;
					return false;
				}
	   });
	   
	   String[] filenameList = new String[contents.length];
	   for (int i = 0; i < contents.length; i++) {
		   filenameList[i] = contents[i].getName();
	   }
	   
	   return filenameList;
   }
}
