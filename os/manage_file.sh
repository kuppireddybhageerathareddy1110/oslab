#!/bin/bash

# Define the file path
file_path="VITAP/SCOPE/your_name.txt"

# Create the file with your name and write initial content
echo "Your Name: [KUPPIREDDY BHAGEERATHA REDDY]" > "$file_path"
echo "Your Address: [VITAP]" >> "$file_path"

# Display the file contents to the user
echo "File Contents:"
cat "$file_path"

# Ask the user if they want to add an alternate address
echo "Do you want to add an alternate address? (yes/no)"
read user_response

if [ "$user_response" == "yes" ]; then
  # Ask the user to enter the alternate address
  echo "Enter the alternate address:"
  read alternate_address

  # Append the new address to the file
  echo "Alternate Address: $alternate_address" >> "$file_path"

  # Display the updated file contents
  echo "Updated File Contents:"
  cat "$file_path"
else
  echo "No changes made. Exiting script."
fi
