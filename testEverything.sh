#!/bin/bash
cd ./out/production/AEDBoleias
rm App.ser
for f in ../../../Tests/*/input*
do
	cat "$f" | java Main > "$(echo "$f" | sed "s/input/myout/")"
	echo -en "\e[92m"
	diffout=$(diff "$(echo "$f" |sed "s/input/output/")" "$(echo "$f"|sed "s/input/myout/")")
	if [[ $diffout ]] ; then
		echo -en "\e[91m"
	fi
	echo -e "$f\e[39m"
	diff "$(echo "$f" |sed "s/input/output/")" "$(echo "$f"|sed "s/input/myout/")"
done
