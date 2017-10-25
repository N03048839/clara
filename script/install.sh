#!/bin/bash

PTRG="www.google.com"

# Enforce internet connection
echo "Downloading prerequisite utilities..."
if [ping -q -c 1 $PTRG]
  # Tools for compiling opencv
  sudo apt-get install build-essential git cmake pkg-config
  
  # Image I/O utilities
  sudo apt-get install libjpeg8-dev libtiff4-dev libjasper-dev libpng12-dev
  
  # Video I/O utilities
  sudo apt-get install libavcodec-dev libavformat-dev libswscale-dev libv4l-dev
  
  # opencv GUI operations
  sudo apt-get install libgtk2.0-dev
  
  # extra/optimized matrix operations
  sudo apt-get install libatlas-base-dev gfortran
else  
  echo "ERROR: no internet connection!"
  exit 1
fi

echo "Cloning Opencv..."
cd ~
if [ping -q -c 1 $PTRG]
  git clone https://github.com/Itseez/opencv.git
  cd opencv
  git checkout 3.3.1
else  
  echo "ERROR: no internet connection!"
  exit 1
fi

echo "Installing Python 2.7..."
