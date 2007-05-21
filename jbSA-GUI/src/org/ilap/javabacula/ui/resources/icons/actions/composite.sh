#!/bin/sh


	convert job_h16.png ok.png -gravity center -composite job_ok_h16.png
	convert job_h16.png okp.png -gravity center -composite job_okp_h16.png
	convert job_h16.png warning.png -gravity center -composite job_warning_h16.png
	convert job_h16.png error.png -gravity center -composite job_error_h16.png
	convert job_h16.png running.png -gravity center -composite job_running_h16.png


