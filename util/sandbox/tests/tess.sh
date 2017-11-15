#!/bin/bash

for file in "$@"
do
	echo "- OCR: file $file"
	sudo tesseract $file $file
done
