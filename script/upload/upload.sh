#!/bin/bash

# --- Stores location of label directory
LBL_DIR=/home/pi/spine-reader/files/labels
echo "File dir: $LBL_DIR"


# --- Stop execution unless directory contains files
fileCount=$(ls -1 $LBL_DIR | wc -l)
echo "Files found: $fileCount"
if [ $fileCount == 0 ]; then
	echo >&2 "Error: empty directory! $LBL_DIR"
	exit 1
fi


# --- Find oldest file in directory
filename=$(ls -rt $LBL_DIR | head -n 1)
echo "File name: $filename"
infile=$(cat $filename)  #Contents of file


# --- VARS for SQL connection
HOST="********"
USER="********"
PASSWORD="********"
DB_NAME="********"


# --- Query database
echo "Querying database..."
mysql --host=$HOST --user=$USER --password=$PASSWORD $DB_NAME << EOF
insert into UploadTest values('$filename','$infile');
EOF


