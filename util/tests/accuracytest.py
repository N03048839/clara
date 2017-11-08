# import necessary dependencies
import argparse
import sys
from os import listdir
from os.path import isfile, join
import subprocess

RUN_DIR = os.path.dirname(os.path.abspath(__file__))

# construct arg parser and parse arguments
ap = argparse.ArgumentParser()
ap.add_argument("-i", "--image", required=False, help="path to the input image")
ap.add_argument("-p", "--path", required=True, help="directory of test images")
args = vars(ap.parse_args())


def allImagesInDir(directory = RUN_DIR):
	inputs = [f for f in listdir(directory) if isfile(join(directory, f))]
	subprocess.call(["python", "foo.py %i" % f])

def singleImage(image):
	subprocess.call(["python", "foo.py %i" % image])
