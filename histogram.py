
import cv2 
import numpy as np
from matplotlib import pyplot as plt

class Histogram:
	def __init__(self):
		pass

	# Plot the image's histogram. 
	def showHistogram(self, image):
		plt.hist(image.ravel(), 256, [0,256])
		plt.show()	
		return
		
	# Calculates the histogram of an image, and writes its contents to a file.
	def writeHistogram(self, image, outfilename):
		hist = cv2.calcHist([image], [0], None, [256], [0, 256])
		outfile = open(outfilename, 'w')
		outfile.write(hist)
		outfile.close()		
		return
	
	# Maximise the image's dynamic range. Uses a flat transform algorithm.
	def equalizeHist(self, image):
		hist,bins = np.histogram(image.flatten(),256,[0,256])
		
		cdf = hist.cumsum()
		cdf_normalized = cdf * hist.max() / cdf.max()
		
		cdf_m = np.ma.masked_equal(cdf,0)
		cdf_m = (cdf_m - cdf_m.min())*255/(cdf_m.max()-cdf_m.min())
		cdf = np.ma.filled(cdf_m,0).astype('uint8')
		img2 = cdf[image]
		
		return img2
	
	# Maximise the image's dynamic range. Uses a smart adaptive eq algorithm.
	def equalizeHistCLAHE(self, image):
		clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8))
		cl1 = clahe.apply(img)
		
		return cl1

