#!/bin/bash

DATE=$(date +"%Y-%m-%d_%H%M")

sudo fswebcam -r 1280x1024 --no-banner --log /home/pi/clara/script/camera/imgcapt.log /home/pi/clara/files/images/$DATE.jpg


