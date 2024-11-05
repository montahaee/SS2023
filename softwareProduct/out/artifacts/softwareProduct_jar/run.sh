#!/bin/bash

# Get the full path of the directory where the script is located
script_dir="$(dirname "$(readlink -f "$0")")"

# Define the path to your JAR file relative to the script directory
jar_file="$script_dir/softwareProduct.jar"

# Define the path to the input directory relative to the script directory
input_dir="$script_dir/../../../src/test/resource"

# Check if the JAR file exists
if [ ! -f "$jar_file" ]; then
    echo "Error: Unable to access jarfile $jar_file"
    exit 1
fi

# Run the Java program with the input file path as an argument
java -jar "$jar_file" "$input_dir"
