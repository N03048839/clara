#!/bin/bash

# ====== VARIABLES ======

# --- Stores location of image directory ---
FILE_DIR=/home/pi/spine-reader/files/images
#echo "File dir: $FILE_DIR"


# --- Stores location of output directory ---
OUT_DIR=/home/pi/spine-reader/files/labels
#echo "Output dir: $OUT_DIR"


# --- Oldest image in directory (last edited)
IMAGE=$(ls -rt $FILE_DIR | head -n 1)
#echo "Image name: $IMAGE"
#echo "Image path: $FILE_DIR/$IMAGE"



# --- Analyze image ---
echo "Analyzing image $IMAGE..."
sudo tesseract $FILE_DIR/$IMAGE $OUT_DIR/$IMAGE
echo "File results saved in $IMAGE.txt"


# --- Remove processed image ---
echo "Removing processed image..."
sudo rm $FILE_DIR/$IMAGE


#cat $OUT_DIR/$IMAGE.txt
