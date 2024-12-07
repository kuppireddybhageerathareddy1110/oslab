#!/bin/bash

# Take input from the user as file path
echo "Enter the file path:"
read file_path

# Check if the path exists
if [ -e "$file_path" ]; then
  echo "Valid Path"

  # Check if the file has execute permissions
  if [ -x "$file_path" ]; then
    echo "Executable"
  else
    echo "Cannot execute"
  fi

  # Check if the file is a regular file, block special file, or character special file
  if [ -f "$file_path" ]; then
    echo "Regular File"
  elif [ -b "$file_path" ]; then
    echo "Block Special File"
  elif [ -c "$file_path" ]; then
    echo "Character Special File"
  else
    echo "Other type of file or directory"
  fi
else
  echo "Path is invalid"
fi
