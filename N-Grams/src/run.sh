#!/usr/bin/env bash
if [ -z "$3" ] ; then
	echo "Missing Input test sentences."
	echo "USAGE: sh run.sh <Path to corpus.txt> <Setence one: in quotes> <Sentence two: in quotes>"
else
	source ./compile.sh
	bash ./compile.sh
	java main.ngrams "$1" "$2" "$3"
fi

################
# EXAMPLE USAGE#
################
# sh run.sh /Volumes/Mahesh/Programming/NLP/Projects/N-Grams/src/Corpus/Corpus.txt "Paul Allen and Bill Gates are the founders of Microsoft software company" "Windows Phone Microsoft Office and Microsoft Surface are the products of the company"
