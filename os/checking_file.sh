#!/bin/bash

# Function to check if a file exists
check_file_exists() {
  local file=$1
  if [ -e "$file" ]; then
    echo "File exists."
  else
    echo "File does not exist."
  fi
}

# Function to check if a file has executable permissions
check_executable_permissions() {
  local file=$1
  if [ -x "$file" ]; then
    echo "File has executable permissions."
  else
    echo "File does not have executable permissions."
  fi
}

# Take the file name as input
echo "Enter the file name:"
read file_name

# Call the functions
check_file_exists "$file_name"
check_executable_permissions "$file_name"
