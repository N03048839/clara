#!/bin/bash

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

