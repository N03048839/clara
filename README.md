# spine-reader
<h4>A fully autonomous library cataloging device.</h4><br><br>
<h3>Introduction</h3>
<i>
For years, libraries have been seen as centers of knowledge within our culture. Long before search engines, one could visit a library to access a wealth of information on any given topic.  In the age of the internet, however, the relatively slow speed of access to this information has created the need for many libraries to utilize new technologies to streamline the book search process.
<br><br>
This software is intended to be used by libraries that need help keeping track of their book locations.  It will take images of bookshelves using a USB camera. It will then read the call numbers of each book in the picture. Based on the tags it reads, it will then verify that the books are either in the right order or in the right location. The program will notify the user about any books being in the wrong place as it finds them. To start with, however, we will have the program read the tag and provide the text file of that tag. With this text file we can then look at a database and have the program tell us what book that is.
</i>
<br><br><h3>Project Structure</h3><br>
<ul><li>
<ul style="list-style-type: none;"><li><b>files</b></li><li>used as temporary storage for files the program operates on.</li></ul>
</li><li>
<ul style="list-style-type: none;"><li><b>scripts</b></li><li>stores all scripting files.</li></ul>
</li><li>
<ul style="list-style-type: none;"><li><b>util</b></li><li>Extra utilities, mostly for debugging</li></ul>
</li></ul>
