#!/usr/bin/env bash

# Script to manage evercookie locations missed by resetting Safari
# You can list, delete or prevent cookies
# See http://samy.pl/evercookie for more, caution, it will set an evercookie
# Dominic White evercookie@singe.za.net
# Nov 2010

# TODO
# Parse out cookies
# Generate opt-out cookies
# Additional browser support
# Additional OS support

# Global Var Initialisation
action="echo" #set a sane default command to run
switches="" #post-fix for commands
fulllsoconf="flashconf/allsettings.sol"
lsoonlyconf="flashconf/lsosettings.sol"
verbose="false"

# Set up OSX locations, only OS currently supported
prefix="/Users/singe/Library/"

# Define Safari locations
safariprefix=$prefix"Safari/"
safaricookie=$prefix"Cookies/"
safaridb=$safariprefix"Databases" #Don't include / for final dirs
safarilocalstore=$safariprefix"LocalStorage"
safariarray=($safaricookie $safaridb $safarilocalstore)

# Define Flash locations
flashprefix=$prefix"Preferences/Macromedia/Flash Player/"
flashlso=$flashprefix"#SharedObjects"
flashlso2=$flashprefix"macromedia.com/support/flashplayer/sys"
flasharray=("$flashlso" "$flashlso2")

# Define SilverLight locations
silverprefix=$prefix"Application Support/Microsoft/Silverlight/"
silverlso=$silverprefix"is"
silverarray=( "$silverlso" )

fullarray=( "${safariarray[@]}" "${flasharray[@]}" "${silverarray[@]}" )

function usage() {
	echo Duh
}

# Recursive path traversal without relying on platform independant find implementations
function recurse() {
	foo=( "$2"/* )
	if [ "${foo[0]}" != "$2/*" ]; then
		for i in "${foo[@]}"; do
			if [ -d "$i" ]; then #If it's a directory then recurse
				recurse "$1" "$i"
			elif [ -f "$i" ]; then #No wait, it's a file, do something
				"$1" "$i"
			fi
		done
	fi
}

# Iterate through the locations performing the assigned action
function gopherit() {
	array=( "$@" )
	count=${#array[*]}
	for (( j = 0 ; j < count ; j++ )); do
		echo "Currently in: ${array[$j]}"
		$action "${array[$j]}"$switches
	done
}

# You can do whatever you like to an LSO, but flash will undo it, you need the settings file to prevent it
function prefset() {
	cp $1 "$flashlso2/settings.sol" # This prevents Flash apps from setting a cookie
	chmod 400 "$flashlso2/settings.sol"
	cd "$silverlso"/*/*/* # This dir changes, but appears to be unique
	touch disabled.dat # This prevents SL apps from setting a cookie
	cd -
}

# Set the action to find and run through the location arrays
function list() {
	action='recurse echo'
	switches=""	
	gopherit "${safariarray[@]}"
	gopherit "${flasharray[@]}"
	gopherit "${silverarray[@]}"
}

# The delete action
function delete() {
	action="rm -ir"
	switches="/*"
	gopherit "${safariarray[@]}"
	gopherit "${flasharray[@]}"
	gopherit "${silverarray[@]}"/*/*/* #Leave the dir struct so we can set disabled, unfortunately, this leaves a potential unique identifier
}

function prevent() {
	prefset $fulllsoconf # Set config setting to block SL cookies
}

# Meed to beef this up to know what are & are not cookie
function twiddle() {
	#ls -l "$1"
	#chmod 777 "$1"
	rm -rf "$1"
	touch "$1"
	#ln -s ~/secret "$1"
	#mkdir "$1"
	#chmod 0 "$1"
	SetFile -a l "$1" #Use OSX Dev tool to lock file
	#ls -l "$1"
}

# Go through each dir, find the cookies
function twiddlewrapper() {
	count=${#fullarray[*]}
	for (( j = 0 ; j < count ; j++ )); do
		recurse twiddle "${fullarray[$j]}"
	done
	#prefset $lsoonlyconf
}

function hardprevent() {
	echo "Warning, this currently breaks Safari, Flash & Silverlight"
	action="rm -ir"
	switches=""
	gopherit "${safariarray[@]}"
	gopherit "${flasharray[@]}"
	gopherit "${silverarray[@]}"
	action="ln -s /dev/null"
	switches="" #Need no trailing slash for link to work properly
	gopherit "${safariarray[@]}"
	gopherit "${flasharray[@]}"
	gopherit "${silverarray[@]}"
}

function restore() {
	action="rm -ri"
	switches=""
	gopherit "${safariarray[@]}"
	gopherit "${flasharray[@]}"
	gopherit "${silverarray[@]}"
	action="mkdir"
	switches=""
	gopherit "${safariarray[@]}"
	gopherit "${flasharray[@]}"
	gopherit "${silverarray[@]}"
	action="chmod 700" #Default Safari perm
	switches=""
	gopherit "${safariarray[@]}"
	action="chmod 755" #Default Flash & Silverlight perms
	gopherit "${flasharray[@]}"
	gopherit "${silverarray[@]}"
}

# Get CLI options and kick off appropriate path
while getopts "hldpPrtv" OPTIONS ; do
    case ${OPTIONS} in
        h|-help) usage;;
		v|-verbose) verbose=true;;
        l|-list) list;;
		d|-delete) delete;;
		p|-prevent) prevent;;
		P|-hardprevent) hardprevent;;
		r|-restore) restore;;
		t|-twiddle) twiddlewrapper;;
    esac
done
