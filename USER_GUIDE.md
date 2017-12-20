CLARA Library Management Automation System

This is a guide showing the basic use of CLARA.

Note that /clara/ folder must be installed in home directory for
default configuration. (If you change this, you may have to 
redefine the paths specified in `clara.sh` and `timer.sh`.


1) IMAGE CAPTURE

	To capture an image from an attached USB webcam,
	invoke the following script:
	
	 `./imgcapt.sh` 
	
	in directory ~/clara/script/camera`
	
	The image will be saved in ~/clara/, with the capture
	timestamp as the filename, and in .jpg format.

	Note that a webcam may produce unseen errors if the Pi
	is underpowered.

2) IMAGE ANALYSIS
	
	To analyze images with CLARA, invoke the following script:

	 `./clara.sh`

	in `~/clara` 

	CLARA will scan all .jpg or .bmp images in this
	folder.

	CLARA can be run in standard, quiet, or verbose mode. To specify
	one of these, edit `clara.sh` and insert one of the following 
	switches after the `sudo python process.py` command, but before
	the --out output directory specification:

	[none]		standard output
	-q		quiet (suppressed) output
  	-v		verbose output

3) CLARA OUTPUT

	CLARA image analysis character output will be displayed in the 
	terminal output, but it is also written to file for use with
	other scripts.

	Text output of call-number region analysis is stored in the 
	`files/ocr` folder.  Note that each output file corresponds
	with a single C-NL region, rather than a single bookshelf 
	image.

	For debugging purposes, the C-NL regions are also stored to disk.
	CLARA stores each one in a png image in the `files/labels` folder.


