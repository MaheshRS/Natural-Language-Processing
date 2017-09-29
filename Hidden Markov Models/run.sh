#!/usr/bin/env bash
if [ -z "$1" ] ; then
	echo "Missing Observed Events."
	echo "USAGE: sh run.sh <Observed Events - Without space>"
else
	source ./compile.sh
	bash ./compile.sh
	java HMM.HMM "$1"
fi

################
# EXAMPLE USAGE#
################
# sh run.sh /Volumes/Mahesh/Programming/NLP/Projects/N-Grams/src/Corpus/Corpus.txt "Paul Allen and Bill Gates are the founders of Microsoft software company" "Windows Phone Microsoft Office and Microsoft Surface are the products of the company"
