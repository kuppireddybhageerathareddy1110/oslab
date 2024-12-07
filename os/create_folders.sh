#!/bin/bash

# Create the parent folder and subfolders
mkdir -p VITAP/SCOPE
mkdir -p VITAP/SENSE
mkdir -p VITAP/SMECH
mkdir -p VITAP/VISH

# Display all subfolders from the VITAP folder
echo "Subfolders in VITAP:"
ls -d VITAP/*/

# Take input from the user for folder names and display all subfolders and files from those folders
echo "Enter folder names separated by spaces:"
read -a folder_names

for folder in "${folder_names[@]}"; do
  echo "Contents of $folder:"
  if [ -d "$folder" ]; then
    ls -R "$folder"
  else
    echo "$folder is not a valid directory."
  fi
done
