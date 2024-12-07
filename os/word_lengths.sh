#!/bin/bash

# Ask the user to type a number of words
echo "Enter words separated by spaces:"
read -a words

# Print the length of each word
for word in "${words[@]}"; do
  echo "Length of '$word': ${#word}"
done

