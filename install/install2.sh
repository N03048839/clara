#!/bin/bash

# This is the second of five scripts that install CLARA on a
# Raspberry Pi, assumed to be running Raspbian Stretch. 
#
# They _should_ work in other Raspbian environments, but have not
# been tested. Proceed at your own risk.
# 
# For more information, read the guide below:
# https://www.pyimagesearch.com/2017/09/04/raspbian-stretch-install-opencv-3-python-on-your-raspberry-pi/

# Download OpenCV source
cd ~
wget -O opencv.zip https://github.com/Itseez/opencv/archive/3.3.1.zip
unzip opencv.zip

# Download source for OpenCV tools
wget -O opencv_contrib.zip https://github.com/Itseez/opencv_contrib/archive/3.3.1.zip
unzip opencv_contrib.zip

# Create build directory
cd opencv-3.3.1
sudo mkdir build
cd ~

# Remove to save disk space
sudo rm opencv.zip
sudo rm opencv_contrib.zip

