#!/bin/bash

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

