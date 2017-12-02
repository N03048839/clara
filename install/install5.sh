#!/bin/bash

source ~/.profile
workon cv

# Prepare OpenCV for compilation.
# MAKE SURE to verify that output from this step
# is correct before proceeding. See guide. 

cd ~/opencv-3.3.1/build
sudo cmake -D CMAKE_BUILD_TYPE=RELEASE \
	-D CMAKE_INSTALL_PREFIX=/usr/local \
	-D INSTALL_PYTHON_EXAMPLES=ON \
	-D OPENCV_EXTRA_MODULES_PATH=~/opencv_contrib-3.3.1/modules \
	-D BUILD_EXAMPLES=ON ..

