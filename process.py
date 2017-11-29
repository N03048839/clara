# import the necessary packages
from labeldetector import LabelDetector
import argparse
import imutils
import cv2
import sys

VERB_OUTPUT = False
QUIET_OUTPUT = False
DRAW_CONTOURS = False
SHOW_IMAGE = False
NORESIZE = False

GAUSS_SIZE = 5     # size of blur filter. must be odd. '5' recommended
THRESH_VAL = 150   # min pixel brightness to be considered white. (0-255)
MAX_WIDTH = 500	   # images larger than this are downscaled to be this size

# Used to supress console output. Replaces standard 'print' command.
def printstd(string):
	if QUIET_OUTPUT == False:
		print(string)
	return

# Used to provide extra console output.
def printverb(string):
	if VERB_OUTPUT:
		 printstd(string)
	return

# Display an image on screen, in a window with the given title.
def showImage(image, title):
	if SHOW_IMAGE:	
		printstd('SHOWIMAGE: displaying image ' + title)
		cv2.imshow(title, image)
		cv2.waitKey(0)			# Pause for any keystroke
		cv2.destroyAllWindows()	# Close existing opencv windows
	return
	
def loadimage(imagename):
	printstd("\n========= Image: %s =====" % imagename)
	printverb(" - Loading image...")
	image = cv2.imread(imagename)
	printverb(" - Loading image...done")
	width_org = image.shape[1]	# OpenCV uses row, column coords
	height_org = image.shape[0]	# OpenCV uses row, column coords
	printverb("     [W: " + str(width_org)
		+ "  H: " + str(height_org) + "]")

	if (width_org > MAX_WIDTH) and NORESIZE:
		printverb (" - Resizing image...")
		resized = imutils.resize(image, width=MAX_WIDTH)
		ratio = float(height_org) / float(resized.shape[0])
		printverb (" - Resizing image...done")
		print ("      [W: " + str(resized.shape[1]) 
				+ "  H: " + str(resized.shape[0]) + "]")
	else:
		resized = image
		ratio = 1.0 
	
	showImage(resized, "original")
	return resized, ratio
	
def preprocess(image):
	printverb (" - Converting to greyscale...")
	gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
	printverb (" - Converting to greyscale...done")
	printverb (" - Performing Gaussian blur... [filter size: "
			 + str(GAUSS_SIZE) + "px]")
	blurred = cv2.GaussianBlur(gray, (GAUSS_SIZE, GAUSS_SIZE), 0)
	printverb (" - Performing Gaussian blur...done")
	printverb (" - Thresholding... [thresh: " + str(THRESH_VAL) + "/255]")
	thresh = cv2.threshold(blurred, THRESH_VAL, 255, cv2.THRESH_BINARY)[1]
	printverb (" - Thresholding...done")
	return thresh
	
	
# Process an image.
def main(imagename):
	img, ratio = loadimage(imagename)
	ppi = preprocess(img)
	LD = LabelDetector()
	labels = LD.detectLabels(img, ppi, ratio)
	printstd(" Label regions found: " + str(len(labels)))
	for i in range(0, len(labels)):
		fn = OUT_DIR + "/" + imagename + "_out" + str(i) + ".png"
		printstd(" - Writing output to file \'" + fn + "\'")	
		cv2.imwrite(fn, labels[i])
		printverb(" - Writing output to file...done")
		
	return
	
	 
if (len(sys.argv) < 2):
	sys.exit('ERROR: Must pass argument (path_to_image_to_be_processed)')
	
# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-v", "--verbose", action="store_true", help="show extended console output")
ap.add_argument("-q", "--quiet", action="store_true", help="supress console output")
ap.add_argument("-I", "--showimage", action="store_true", help="display images in a window")
ap.add_argument("--out", nargs="?", help="output directory")
ap.add_argument("image", nargs="+", help="path to the input image")
args = vars(ap.parse_args())

VERB_OUTPUT = args["verbose"]
QUIET_OUTPUT = args["quiet"]
SHOW_IMAGE = args["showimage"]
OUT_DIR = args["out"]

for image in args["image"]:
	main(image)


