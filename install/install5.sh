#!/bin/bash

# This is the fifth of five scripts that install CLARA on a
# Raspberry Pi, assumed to be running Raspbian Stretch. 
#
# They _should_ work in other Raspbian environments, but have not
# been tested. Proceed at your own risk.
# 
# For more information, read the guide below:
# https://www.pyimagesearch.com/2017/09/04/raspbian-stretch-install-opencv-3-python-on-your-raspberry-pi/
# 
# Note that this installation is left deliberately incomplete. After running this script,
# follow the procedure given in the above guide for 'Step 5: Compile and Install OpenCV' 
# using the instructions for Python 3.


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

