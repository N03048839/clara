#!/bin/bash

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
