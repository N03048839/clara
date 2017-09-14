#!/bin/bash

DATE=$(date +"%Y-%m-%d_%H%M")

fswebcam -r 1280x1024 --no-banner --log /home/pi/spine-reader/script/camera/imgcapt.log /home/pi/spine-reader/files/images/$DATE.jpg


