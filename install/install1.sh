#!/bin/bash

# One of the core components of CLARA
sudo apt-get install tesseract

# Install image IO tools required by OpenCV
sudo apt-get install libavcodec-dev libavformat-dev libswscale-dev libv4l-dev libxvidcore-dev libx264-dev

# Install image display tools required by OpenCV
sudo apt-get install libgtk2.0-dev libgtk-3-dev libatlas-base-dev gfortran

# Ensure that both versions of python are installed
sudo apt-get install python2.7-dev python3-dev

# Install Cmake, which is needed to compile OpenCV
sudo apt-get install build-essential cmake pkg-config
