import argparse
import cv2
import imutils
import numpy as np

VERB_OUTPUT = False
MAX_WIDTH = 500	   # images larger than this are downscaled to be this size


def histogramAnal(image):
	BRIGHT_MAX = 256
	BRIGHT_MIN = 100
	
	hist = cv2.calcHist([image], [0], None, [256], [0, 256])
	print(hist)
	
	return
	
	
def printlim(string):
	if VERB_OUTPUT:
		 print(string)
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
