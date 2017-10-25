# spine-reader
A fully autonomous library cataloging device.
### Introduction

For years, libraries have been seen as centers of knowledge within our culture. Long before search engines, one could visit a library to access a wealth of information on any given topic.  In the age of the internet, however, the relatively slow speed of access to this information has created the need for many libraries to utilize new technologies to streamline the book search process.

This software is intended to be used by libraries that need help keeping track of their book locations.  It will take images of bookshelves using a USB camera. It will then read the call numbers of each book in the picture. Based on the tags it reads, it will then verify that the books are either in the right order or in the right location. The program will notify the user about any books being in the wrong place as it finds them. To start with, however, we will have the program read the tag and provide the text file of that tag. With this text file we can then look at a database and have the program tell us what book that is.


### Project Structure

| folder   | description   |
| ---------| --------------|
| files    | Used as temporary storage for files the program operates on |
| scripts  | Stores all scripting files. |
| util     | Extra utilities, mostly for debugging |

### Setup
#### Materials Needed
  * A [Raspberry Pi](https://www.raspberrypi.org/products/). Both a Pi 2B and a Pi 3 were used in the development of this project, but any pi should work.
  * A micro SD card with _at least_ double the storage capacity as the size of the OS that you're planning to use. I used a [Samsung Evo Select 32GB](https://www.amazon.com/Samsung-Select-Memory-MB-ME32DA-AM/dp/B01DOB6Y5Q), but 16GB should be sufficient.
  * A wifi dongle (if you don't have a Pi 3). I used an Ourlink 802.11n, but any dongle should work.
  * USB keyboard/mouse (needed for setup).
  * HDMI cable and compatible monitor (needed for setup).
  * Access to a wifi network (if you want to work through SSH).
  * A _good_ micro-usb power adapter/cable.  I used a spare 2-amp [Samsung EP-TA10JWE](https://www.amazon.com/Samsung-SAM-EP-TA10JWE-Universal-Charger-Adapter/dp/B00R8NLZNS) that I had lying around.
  
  IMPORTANT NOTE: Pis do not feature onboard regulation of current draw through their USB ports. If you overdraw (e.g. plug in too many USB devices simultaneously), then some devices can, without warning, exhibit spotty or unreliable behavior (e.g. your wifi dongle). Investing in a good power supply _will_ save you time and from headaches down the road. It can also help to use a USB power meter like [this one](https://www.amazon.com/PowerJive-Voltage-Multimeter-chargers-capacity/dp/B013FANC9W/).
  
#### Provision Pi
  This guide is written with the use of [Raspbian](https://www.raspberrypi.org/downloads/raspbian/) in mind. You should be able to use another linux-based OS if you want, but configuring other systems is out of the scope of this guide. It's suggested that you use the Raspbian desktop environment; you _can_ run the Pi headless (shell environment only), but you won't be able to use all of the image debugging tools which display images on screen.
  
  [A lot of good guides](https://www.raspberrypi.org/documentation/installation/installing-images/README.md) exist for installing Raspbian onto an sd card, so I won't reiterate how to do this in detail here. [Etcher](https://etcher.io/) is a great utility, but if you're working in a linux environment you could always use `df`.
  
  Once you write the OS to the card, plug in your keyboard, mouse, and monitor, and boot your Pi.
* Setup the Pi environment by running `sudo raspi-config` from a terminal:
  * Your keystrokes might produce unexpected character input; you'll have to set the keyboard localization.
  * Raspbian can't access all of the card's storage space by default; you'll have to expand it manually.
  * By default, Raspbian blocks all incoming SSH traffic. If you want to work remotely, you'll have to enable SSH connections.
* Connect to the internet
  * The easy way: clicking the 'network' icon on the toolbar, and follow the prompts
  * The hard way: manually edit your supplicant (`/etc/wpa-supplicant/wpa-supplicant.conf`) and interface(`/etc/network/interfaces`) files. Back these up first, just in case.
  
  At this point you should be connected to the internet. You can do the rest of the setup remotely if you want, using `ssh` (linux/mac) or [Putty](http://www.putty.org/) (windows). Keep in mind, though, that several of the image debugging tools in this software use GUI output, so having a remote desktop utility like [VNC](https://www.raspberrypi.org/documentation/remote-access/vnc/) can be handy.
  
#### Install Software
As with any fresh linux install you should start by updating your environment with `sudo apt-get update` and `sudo apt-get upgrade`.

### Usage


### Further Information
</i>This section will be expanded as time allows.</i>
