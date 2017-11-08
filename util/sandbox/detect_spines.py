# import the necessary packages
from shapedetector import ShapeDetector
#from matplotlib import pyplot as plt
import argparse
import imutils
import cv2
import numpy as np

VERB_OUTPUT = False
GAUSS_SIZE = 5     # size of blur filter. must be odd. '5' recommended
THRESH_VAL = 150   # min pixel brightness to be considered white. (0-255)
MAX_WIDTH = 500	   # images larger than this are downscaled to be this size




def printlim(string):
	if VERB_OUTPUT:
		 print(string)
	return
	
def showHistogram(image):
	#plt.hist(image.ravel(), 256, [0,256])
	#plt.show()
	return
	
def displayImage(image, title):
	cv2.imshow(title, image)
	cv2.waitKey(0)
	cv2.destroyAllWindows()
	return
	
	

def loadImage(imagename):
	# load the image and resize it to a smaller factor so that
	# the shapes can be approximated better
	print("\n========= Image: %s =====" % imagename)

	printlim(" - Loading image...")
	image = cv2.imread(imagename)
	printlim(" - Loading image...done")
	width_org = image.shape[1]	# OpenCV uses row, column coords
	height_org = image.shape[0]	# OpenCV uses row, column coords
	printlim("     [W: " + str(width_org)
		+ "  H: " + str(height_org) + "]")
		
	if (width_org > MAX_WIDTH):
		printlim (" - Resizing image...")
		resized = imutils.resize(image, width=MAX_WIDTH)
		ratio = float(height_org) / float(resized.shape[0])
		printlim (" - Resizing image...done")
		print ("      [W: " + str(resized.shape[1]) 
				+ "  H: " + str(resized.shape[0]) + "]")
	else:
		resized = image
		ratio = 1.0 
		
	displayImage(resized, "original: " + imagename)
	return resized	
	
	
def grayscale(image):
	printlim (" - Converting to greyscale...")
	gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
	printlim (" - Converting to greyscale...done")
	return gray
	
	
def blur(image):
	printlim (" - Performing Gaussian blur... [filter size: "
			 + str(GAUSS_SIZE) + "px]")
	blurred = cv2.GaussianBlur(image, (GAUSS_SIZE, GAUSS_SIZE), 0)
	printlim (" - Performing Gaussian blur...done")
	return blurred
	
	
def threshold(image, thresholdVal):
	printlim (" - Thresholding... [thresh: " + str(THRESH_VAL) + "/255]")
	thresh = cv2.threshold(image, thresholdVal, 255, cv2.THRESH_BINARY)[1]
	printlim (" - Thresholding...done")
	return thresh
	
	
def canny(image):
	printlim (" - Detecting edges...")
	cannyimg = cv2.Canny(image, 80, 60)
	printlim (" - Detecting edges...done")
	return cannyimg
	
	
def houghlines(image):
	RHO_RES = 1
	THETA_RES = np.pi / 180
	minLineLength = 10
	# maxLineGap = 10

	printlim (" - Computing Hough lines...")
	hough = image
	lines = cv2.HoughLines( canny(image), RHO_RES, THETA_RES, minLineLength)
	for rho,theta in lines[0]:
		a = np.cos(theta)
		b = np.sin(theta)
		x0 = a*rho
		y0 = b*rho
		x1 = int(x0 + 1000*(-b))
		y1 = int(y0 + 1000*(a))
		x2 = int(x0 - 1000*(-b))
		y2 = int(y0 - 1000*(a))

	cv2.line(hough,(x1,y1),(x2,y2),(0,0,255),2)

	printlim (" - Computing Hough lines...done")
	return hough
	

def process(imagename):
	image = loadImage(imagename)

	# convert the resized image to grayscale, blur it slightly,
	# and threshold it
	gray = grayscale(image)
	blurred = blur(gray)
	thresh = threshold(blurred, THRESH_VAL)
	cannyimg = canny(blurred)
	displayImage(cannyimg, "canny edges: " + imagename)
	displayImage(houghlines(blurred), "hough lines: " + imagename)
	 
	# find contours in the thresholded image and initialize the
	# shape detector
	sd = ShapeDetector()
	printlim (" - Detecting contours...")
	cnts = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL,
		cv2.CHAIN_APPROX_SIMPLE)
	cnts = cnts[0] if imutils.is_cv2() else cnts[1]
	printlim (" - Detecting contours...done [found: " + str(len(cnts)) + "]")
	#this will be used for saving the image
	number = 1

	# loop over the contours
	for c in cnts:
		# compute the center of the contour, then detect the name of the
		# shape using only the contour
		M = cv2.moments(c)
		#cX = int((M["m10"] / M["m00"]) * ratio)
		#cY = int((M["m01"] / M["m00"]) * ratio)
		if (M["m00"] != 0):
			cX = int(M["m10"] / M["m00"] * ratio)
			cY = int(M["m01"] / M["m00"] * ratio)
		else:
			cX,cY = 0,0

		shape = sd.detect(c)
	 
		# multiply the contour (x, y)-coordinates by the resize ratio,
		# then draw the contours and the name of the shape on the image
		c = c.astype("float")
		c *= ratio
		c = c.astype("int")
		cv2.drawContours(image, [c], -1, (0, 255, 0), 2)
		#v2.putText(image, shape, (cX, cY), cv2.FONT_HERSHEY_SIMPLEX,
			#.5, (255, 255, 255), 2)
	 
		# show the output image
		#cv2.imshow("Image with single contour added", resized)
		#cv2.waitKey(0)

		if shape == "rectangle":

			#idx = -1, (0, 255, 0), 2 # The index of the contour that surrounds your object
			mask = np.zeros_like(image) # Create mask where white is what we want, black otherwise
			cv2.drawContours(mask, [c], -1, (0, 255, 0), 2) # Draw filled contour in mask
			out = np.zeros_like(image) # Extract out the object and place into output image
			out[mask == 255] = image[mask == 255]
			oresized = imutils.resize(out, width=500)

			# Show the output image
			#cv2.imshow("Output with rect contour added", oresized)
			#cv2.waitKey(0)


			#Crops the image inside contours and saves it to a new file
			rect = cv2.boundingRect(c)
			x,y,w,h = rect
			if h > (height_org / 6):
				box = cv2.rectangle(image, (x,y), (x+w,y+h), (0,0,255), 2)
				cropped = image[y : y+h, x : x+w]
				cresized = imutils.resize(cropped, height=500)
				cv2.imshow("Contour region detail", cresized)
				cv2.waitKey(0)
			#	cv2.imwrite("cropped"+str(number)+".png", cropped)
				number += 1	
 

# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-v", "--verbose", action="store_true", help="show extended output")
ap.add_argument("image", nargs="+",
	help="path to the input image")
args = vars(ap.parse_args())
VERB_OUTPUT = args["verbose"]

for image in args["image"]:
	process(image)



