#!/bin/sh


	convert file.png marked.png -gravity center -composite file_marked.png
	convert file.png partmarked.png -gravity center -composite file_partmarked.png

	convert folder.png marked.png -gravity center -composite folder_marked.png
	convert folder.png partmarked.png -gravity center -composite folder_partmarked.png

	convert folder_open.png marked.png -gravity center -composite folder_open_marked.png
	convert folder_open.png partmarked.png -gravity center -composite folder_open_partmarked.png

