#!/bin/bash

# --- Stores location of label directory
LBL_DIR=/home/pi/spine-reader/files/labels
echo "File dir: $LBL_DIR"


# --- Stop execution unless directory contains files
fileCount=$(ls -1 $LBL_DIR | wc -l)
echo "Items in directory: $fileCount"
if [ $fileCount == 0 ]; then
	echo >&2 "Error: empty directory! $LBL_DIR"
	exit 1
fi


# --- Find oldest file in directory
filename=$(ls -rt $LBL_DIR | head -n 1)
echo "Loading: $filename"
infile=$(cat $LBL_DIR/$filename)  #Contents of file


# --- VARS for SQL connection
HOST=""
USER=""
PASSWORD=""
DB_NAME=""
TABLE="UploadTest"


# --- Query database
echo "Querying database..."
mysql -h$HOST -u$USER -p$PASSWORD -e'insert into UploadTest values('$filename','$infile');' $DB_NAME 


# --- Removal of label file
#echo "Removing used input file..."
#sudo rm $LBL_DIR/$filename


