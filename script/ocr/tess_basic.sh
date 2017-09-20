#!/bin/bash

# ====== VARIABLES ======

logFN="tess_basic.log"


# --- Stores location of image directory ---
FILE_DIR=/home/pi/spine-reader/files/images
echo "File dir: $FILE_DIR"


# --- Stores location of output directory ---
OUT_DIR=/home/pi/spine-reader/files/labels
#echo "Output dir: $OUT_DIR"


# --- Stop execution unless directory contains images
imgCount=$(ls -1 $FILE_DIR | wc -l)
echo "Images found in dir: $imgCount"
if [ $imgCount == 0 ]; then 
	echo >&2 "Error: directory /files/images/ is empty!"
	exit 1
fi


# --- Find oldest image in directory (last edited)
IMAGE=$(ls -rt $FILE_DIR | head -n 1)
echo "Image name: $IMAGE"


# --- Analyze image ---
echo " === Analyzing image $IMAGE..."
sudo tesseract $FILE_DIR/$IMAGE $OUT_DIR/$IMAGE
echo "File results saved in $IMAGE.txt"


# --- Remove processed image ---
echo "Removing processed image..."
sudo rm $FILE_DIR/$IMAGE


# --- Display OCR output
#cat $OUT_DIR/$IMAGE.txt
