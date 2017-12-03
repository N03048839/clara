#!/bin/bash

inputImages=$(ls -1 $(pwd) | grep ".png")
for image in $inputImages
do
	python process.py -q --out $(pwd)/files/labels $image
	labels=$(ls -1 "$(pwd)/files/labels" | grep "$image")
	for label in $labels
	do
		echo " === $label"
		tesseract $(pwd)/files/labels/$label $(pwd)/files/ocr/$label -c "tessedit_char_whitelist=abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890. "
	done
done

read -n 1 -p "Press any key to exit." p