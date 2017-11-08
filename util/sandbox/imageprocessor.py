import cv2
import argparse

class ImageProcessor:
	def __init__(self):
		pass

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
		
		
