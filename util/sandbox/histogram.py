import sys
import os
import argparse
import cv2
import imutils
import numpy as np

VERB_OUTPUT = False
MAX_WIDTH = 500	   # images larger than this are downscaled to be this size

### Plot the image's histogram.
 #  
###
def histogramAnal(image):
	BRIGHT_MAX = 256
	BRIGHT_MIN = 100
	
	hist = cv2.calcHist([image], [0], None, [256], [0, 256])
	print(hist)
	
	return
	
### Maximise the image's dynamic range. 
 #  Uses a flat transform algorithm.
###	
def equalizeHist(image):
	hist,bins = np.histogram(image.flatten(),256,[0,256])
	
	cdf = hist.cumsum()
	cdf_normalized = cdf * hist.max() / cdf.max()
	
	cdf_m = np.ma.masked_equal(cdf,0)
	cdf_m = (cdf_m - cdf_m.min())*255/(cdf_m.max()-cdf_m.min())
	cdf = np.ma.filled(cdf_m,0).astype('uint8')
	img2 = cdf[image]
	
	return img2
	
### Maximise the image's dynamic range.
 #  Uses a smart adaptive eq algorithm.
###
def equalizeHistCLAHE(image):
	clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8))
	cl1 = clahe.apply(img)
	
	return cl1
	
def printlim(string):
	if VERB_OUTPUT:
		 print(string)
	return
	

### Load a given image, and resize if necessary
 # 	
###
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
		
	cv2.imshow(imagename, resized)
	cv2.waitKey(0)
	return image

def main():
	# construct the argument parse and parse the arguments
	ap = argparse.ArgumentParser()
	ap.add_argument("-v", "--verbose", action="store_true", help="show extended output")
	ap.add_argument("image", nargs="+",
		help="path to the input image")
	args = vars(ap.parse_args())
	VERB_OUTPUT = args["verbose"]

	for image in args["image"]:
		histogramAnal(loadImage(image))


if __name__ == __main__:
	main()
