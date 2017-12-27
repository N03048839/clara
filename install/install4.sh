#!/bin/bash

# This is the fourth of five scripts that install CLARA on a
# Raspberry Pi, assumed to be running Raspbian Stretch. 
#
# They _should_ work in other Raspbian environments, but have not
# been tested. Proceed at your own risk.
# 
# For more information, read the guide below:
# https://www.pyimagesearch.com/2017/09/04/raspbian-stretch-install-opencv-3-python-on-your-raspberry-pi/



# Set system variables to handle virtual environment
echo -e "\n# virtualenv and virtualenvwrapper" >> ~/.profile
echo "export WORKON_HOME=$HOME/.virtualenvs" >> ~/.profile
echo "source /usr/local/bin/virtualenvwrapper.sh" >> ~/.profile
source ~/.profile

# Setup virtual environment
mkvirtualenv cv -p python3

read -n 1 -p "press any key to continue..." p1

workon cv
# Install numpy in virtualenvironment for OpenCV
pip install numpy

