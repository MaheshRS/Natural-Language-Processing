import sys
import re
import os

def readFile(filePath, tagDateAndTime, shouldFilterYear):
	fileHandle = open(filePath, 'r');
	content = fileHandle.read()
	#print (content)
	matches, content = findDateAndTime(content, tagDateAndTime, shouldFilterYear)
	fileHandle.close();

	# Tag the named entity.
	tagDateForNER(content, matches, filePath);

def tagDateForNER(content, matches, filePath):
	# Create a output file 'date_NER_corpus.txt' in the same directory and write the output to the file.
	(head, tail) = os.path.split(filePath);
	outputFilePath = head + "/" + "TAGGED_DATE_NER_CORPUS.txt"

	# Write the content to the output file.
	outputFile = open(outputFilePath, "w");
	outputFile.write(content)
	outputFile.close()

def findDateAndTime(content, tagDateAndTime, shouldFilterYear):
	contentString = content
	dateAndTimeList = []

	# Create regular expression pattern
	monthRE = "(?:%s)" % (monthNamePattern()) # The month name.
	monthDayYearRE = r"(?:\s*%s\s*,?\s*%s)" % (dayPattern(), yearPattern()) # Here the pattern handles "month, day", "day, year", "day year" and "month day" format.
	longDateExpression = r"(?:\b%s%s\b)" % (monthRE, monthDayYearRE) # Prefixes "month" to monthDayRE.
	monthDayRE = r"(?:\b%s\s*%s\b)" % (monthRE, yearPattern())
	yearRangeRE = r"(?:\b%s\b)" % (yearRangePattern()) # Captures "year-year" range.
	singleYearRE = r"(?:\b%s\b)" % (yearPattern()) # Captures "year".
	
	# If filter is enabled, the years are filtered based on prefixes like, 'In' 'Since'
	if shouldFilterYear == 1:
		singleYearRE = r"(?:(?<=[Ii]n\s)|(?<=[Ss]ince\s))(?:\s*%s\b)" % (yearPattern())

	regularExpression = r"(%s|%s|%s|%s)" %(longDateExpression, monthDayRE, yearRangeRE, singleYearRE);

	matches = re.findall(regularExpression, contentString, flags=re.MULTILINE)
	matches.reverse()

	total = 0
	printTotal = 1;

	for date in matches:
		dateAndTimeList.append(str(date))
		total+=1
	
	# Sort the data
	dateAndTimeList.sort()

	# Print total if necessary.
	if printTotal:
		print("Total captured Date and Time Count: %s\n" % (total))

	# Print captured date and time.
	printDate(dateAndTimeList)

	if tagDateAndTime:
		content = re.sub(regularExpression, r"%s \1%s" % ("[DATE", "]"), content)

	return (dateAndTimeList, content)

#Functions returning regular expressions.
def monthNamePattern():
	monthRE = "[Jj]an(?:uary)?|[Ff]eb(?:ruary)?|[Mm]ar(?:ch)?|[Aa]pr(?:il)?|[Mm]ay|[Jj]un(?:e)?|[Jj]ul(?:y)?|[Aa]ug(?:ust)?|[Ss]ept(?:ember)?|[Oo]ct(?:ober)?|[Nn]ov(?:ember)?|[Dd]ec(?:ember)?"
	return monthRE

def dayPattern():
	dayRE = "\d{1,2}"
	return dayRE;

def yearPattern():
	return "\d{4}"

def yearRangePattern():
	return "\d{4}–\d{4}"

#Function to print arrays.
def printDate(dateComponents):
	print("Captured date and time:")
	
	index = 1;
	for date in dateComponents:
		print("(%s.) %s" % (str(index), date))
		index+=1

#Main function
def main():
	# Copy the list of command line arguments.
	argumentList = sys.argv[:]
	if len(argumentList) > 2:
		print ("\nInput Corpus: %s" % (argumentList[1]));
		shouldFilterYear = int(argumentList[2])
		readFile(argumentList[1], 1, shouldFilterYear);
	elif len(argumentList) > 1:
		print ("\nInput Corpus: %s" % (argumentList[1]));
		readFile(argumentList[1], 1, 0);
	else:
		print ("\nNO FILE PATH PROVIDED.\nUSAGE: $python HelloWorld1 <path to the corpus.txt file>\n");


if __name__ == "__main__":
	main()
