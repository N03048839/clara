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


/**
 * This file is used for testing simple image recogition techniques using the open-source
 * OpenCV computer vision library.
 */
public class OpenCVTest
{	

   public static void main( String[] args ) throws IOException
   {
      System.out.println("Loading library...");
	  System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
      
      /* Run contents of this loop once for each file in current directory. */
      for (String filename : listFileNames())
      {
    	  System.out.println("loading " + filename + " with opencv...");
          Mat image = Imgcodecs.imread(filename); // <-- The original image, as read from file
          
          // --- sandbox: test code goes here
          //displayImage(image, "Original Image: " + filename);
          
	      
          int bf = 7;	// Size of pixel matrix used for blur operation. MUST be odd. 7 seems to work best.
          Mat blur = blur(image, bf, 1.3);
          //displayImage(blur, "Blurred Image: " + filename);
          
          Mat canny = canny(blur, 80, 60);
          //displayImage(canny, "Image Edges: " + filename);
          
          Mat cannyColor = canny.clone();
          Imgproc.cvtColor(canny, cannyColor, Imgproc.COLOR_GRAY2BGR);
          Mat cannyOv = canny.clone();
          Core.add(image, cannyColor, cannyOv);
          //displayImage(cannyOv, "Edge Overlay: " + filename);
          
          Mat hough = getHoughTransform(canny, 
        		  (Math.PI / 3), 
        		  ((2 * Math.PI) / 3)
        		  );
          
          Mat houghOv = canny.clone();
          displayImage(hough, "Hough (boundary) Lines: " + filename);
         // Core.add(image, hough, houghOv);
          
          //displayImage(houghOv, "Boundary Line Overlay: " + filename);
          //writeImage(hough, filename + "_hough.jpg");
          
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
   public static Mat getHoughTransform(Mat image, double minTheta, double maxTheta) {
	   double RHO_RESOLUTION = 1.0;
	   
	   double THETA_VERT = Math.PI + (Math.PI / 2);		// Only check vert lines
	   double THETA_HORZ = Math.PI;						// Only check horiz lines
       double THETA_ALL = Math.PI / 180;				// Check all lines (1 degree resolution)
	   
	   System.out.println("   Running Hough Transform...");
	    Mat result = new Mat(image.size(), image.type());
	    Imgproc.cvtColor(image, result, Imgproc.COLOR_GRAY2BGR);
	    Mat lines = new Mat();
	    
	    Imgproc.HoughLines(image, lines, 
	    		RHO_RESOLUTION, 						// resolution of rho in pixels
	    		THETA_ALL, 								// resolution of theta in rads
	    		1, 										// threshold of line detection
	    		0, 0, 									// zero by default
	    		minTheta + (Math.PI / 2),	 			// Minimum angle. PI/2 needs to be added for some reason
	    		maxTheta + (Math.PI / 2)				// Maximum angle  PI/2 needs to be added for some reason
	    		);
	    

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
   
   
   /**
    * Runs a Canny Edge detection on the given image.
    * @param image	The image that will be scanned
    * @param thr1	The upper threshold.
    * 				Points brighter than this are automatically selected as edges.
    * @param thr2	The lower threshold.
    * 				Points brighter than this selected as edges only if they 
    * 				connect to existing edges. Points dimmer are discarded.
    * @return		A Mat containing the transformed image
    */
   public static Mat canny(Mat image, double thr1, double thr2) {
	   Mat dest = image.clone();
	   Imgproc.Canny(image, dest, thr1, thr2, 3, true);
	   return dest;
   }
   
   
   /**
    * Runs a Gaussian Blur on the given image.
    * @param image	The image to be blurred
    * @param ksize	Size of the Gaussian filter. This MUST be an odd number
    * @param sigma	Standard deviation allowed for input values
    * @return
    */
   public static Mat blur(Mat image, int ksize, double sigma) {
	   Mat dest = image.clone();
	   Size size = new Size(ksize, ksize);
	   Imgproc.GaussianBlur(image, dest, size, sigma);
	   return dest;
   }
   
   
   /**
    * Convert a Mat to a BufferedImage.
    * @param m
    * @return
    */
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
   
   
   /**
    * Display a window containing the image.
    * @param img2
    * @param title
    */
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
   
   
   /**
    * Display a window containing the image.
    * @param image
    * @param title
    */
   public static void displayImage(Mat image, String title) {
	   displayImage(Mat2BufferedImage(image), title);
   }
   
   
   /**
    * Write the image to file.
    * @param image
    * @param filename
    */
   public static void writeImage(Mat image, String filename) {
	   Imgcodecs.imwrite(filename, image);
   }
   
   
   /**
    * List the names of all files in the running directory.
    * @return
    */
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
