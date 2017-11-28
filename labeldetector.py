# import the necessary packages
from shapedetector import ShapeDetector
import imutils
import cv2
import numpy as np

DRAW_CONTOURS = False
QUIET_OUTPUT = False
VERB_OUTPUT = False

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

class LabelDetector:
	def __init__(self):
		pass
		
	def detectLabels(self, image, binImg, ratio):
		sd = ShapeDetector()
		printverb (" - Detecting contours...")
		conts = cv2.findContours(binImg, cv2.RETR_EXTERNAL,
			cv2.CHAIN_APPROX_SIMPLE)
		conts = conts[0] if imutils.is_cv2() else conts[1]
		printverb (" - Detecting contours...done [found: " + str(len(conts)) + "]")

		labels = []
		for c in conts:
			# compute the center of the contour, then detect the name of the
			# shape using only the contour
	#temp		M = cv2.moments(c)
			#cX = int((M["m10"] / M["m00"]) * ratio)
			#cY = int((M["m01"] / M["m00"]) * ratio)
	#temp		if (M["m00"] != 0):
	#temp			cX = int(M["m10"] / M["m00"] * ratio)
	#temp			cY = int(M["m01"] / M["m00"] * ratio)
	#temp		else:
	#temp			cX,cY = 0,0

			shape = sd.detect(c)
		 
			# multiply the contour (x, y)-coordinates by the resize ratio,
			# then draw the contours and the name of the shape on the image
			c = c.astype("float")
			c *= ratio
			c = c.astype("int")
			if DRAW_CONTOURS:
				cv2.drawContours(image, [c], -1, (0, 255, 0), 2)
		 
			# show the output image
		#	showImage(resized, "Image with single contour added")

			if shape == "rectangle":

				#idx = -1, (0, 255, 0), 2 # The index of the contour that surrounds your object
			#	mask = np.zeros_like(image) # Create mask where white is what we want, black otherwise
				#cv2.drawContours(mask, [c], -1, (0, 255, 0), 2) # Draw filled contour in mask
			#	out = np.zeros_like(image) # Extract out the object and place into output image
			#	out[mask == 255] = image[mask == 255]
			#	oresized = imutils.resize(out, width=200)

				#Crops the image inside contours and saves it to a new file
				rect = cv2.boundingRect(c)
				x,y,w,h = rect
				if h > (image.shape[0] / 6):
					#box = cv2.rectangle(image, (x,y), (x+w,y+h), (255,255,255), 2)
					lbl = image[y : y+h, x : x+w]
					labels.append(lbl)
		return labels
