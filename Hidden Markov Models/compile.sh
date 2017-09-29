rm -rf ./output
mkdir output

export CLASSPATH=./output

javac -classpath $CLASSPATH -d ./output ./*/*/*.java