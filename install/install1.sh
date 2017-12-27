#!/bin/bash

# This is the first of five scripts that install CLARA on a
# Raspberry Pi, assumed to be running Raspbian Stretch. 
#
# They _should_ work in other Raspbian environments, but have not
# been tested. Proceed at your own risk.
# 
# For more information, read the guide below:
# https://www.pyimagesearch.com/2017/09/04/raspbian-stretch-install-opencv-3-python-on-your-raspberry-pi/


# Standard system update
sudo apt-get update
sudo apt-get upgrade

# One of the core components of CLARA
sudo apt-get install tesseract fswebcam

# Install image IO tools required by OpenCV
sudo apt-get install libavcodec-dev libavformat-dev libswscale-dev libv4l-dev libxvidcore-dev libx264-dev

# Install image display tools required by OpenCV
sudo apt-get install libgtk2.0-dev libgtk-3-dev libatlas-base-dev gfortran

# Ensure that both versions of python are installed
sudo apt-get install python2.7-dev python3-dev

# Install Cmake, which is needed to compile OpenCV
sudo apt-get install build-essential cmake pkg-config
