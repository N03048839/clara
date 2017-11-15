import sys
import os
import cv2
import numpy as np

def getHoughRegions(image):
	gray = cv2.cvtColor(image,cv2.COLOR_BGR2GRAY)
	edges = cv2.Canny(gray,50,150,apertureSize = 3)
	
	regions = []

	lines = cv2.HoughLines(edges,1,np.pi/180,50)
	for rho,theta in lines[0]:
		# translate polar to cartesian: line defined by (x1,y1) to (x2,y2)
		a = np.cos(theta)
		b = np.sin(theta)
		x0 = a*rho
		y0 = b*rho
		x1 = int(x0 + 1000*(-b))
		y1 = int(y0 + 1000*(a))
		x2 = int(x0 - 1000*(-b))
		y2 = int(y0 - 1000*(a))
		
		if True:
	#	if (np.pi/3.0) < theta < (np.pi * 2/3.0):
			newx = x1 if x1 > x2 else x2
			newy = y1 if y1 > y2 else y2
			
			lastx = 0 if not regions else regions[-1][1]
			lasty = 0 if not regions else regions[-1][3]
			
			subregion = (lastx, newx, lasty, newy)
			# copy region to new image
			#subregion = image[lasty:newy, lastx:newx]
			regions.append(subregion)
	
	return regions

def main():
	print('\nhoughregiondetection.py')
	print('		A book spine splitting script')
	
	if (len(sys.argv) < 2):
		print(' (ERROR) You must call this script with an argument (path_to_image_to_be_processed)\n')
		quit()
		
	img = cv2.imread(str(sys.argv[1]))
	regions = getHoughRegions(img)
	print('regions : ' + str(len(regions)))
	for r in regions:
		cv2.line(img,(r[0],r[2]),(r[1],r[3]),(0,0,255),2)
	cv2.imwrite('houghlines.jpg',img)
		
if __name__ == "__main__":
	main()
