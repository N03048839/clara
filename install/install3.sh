#!/bin/bash

# This is the third of five scripts that install CLARA on a
# Raspberry Pi, assumed to be running Raspbian Stretch. 
#
# They _should_ work in other Raspbian environments, but have not
# been tested. Proceed at your own risk.
# 
# For more information, read the guide below:
# https://www.pyimagesearch.com/2017/09/04/raspbian-stretch-install-opencv-3-python-on-your-raspberry-pi/

# Install latest version of pip 
cd ~
wget https://bootstrap.pypa.io/get-pip.py
sudo python get-pip.py
sudo python3 get-pip.py

# Install utilities needed by CLARA
sudo pip install matplotlib imutils pytesseract

# Install virtual environment for installation
sudo pip install virtualenv virtualenvwrapper
sudo rm -rf ~/.cache/pip
sudo rm get-pip.py
