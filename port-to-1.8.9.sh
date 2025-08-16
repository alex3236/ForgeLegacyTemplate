#!/bin/bash

# Check if the current directory is a git repository
if ! git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
  echo "Error: The current directory is not a git repository." >&2
  exit 1
fi

# Get current branch name
current_branch=$(git symbolic-ref --short HEAD 2>/dev/null)
if [ -z "$current_branch" ]; then
  echo "Error: Unable to get the current branch name." >&2
  exit 1
fi

# Check for uncommitted changes
if ! git diff --quiet || ! git diff --cached --quiet; then
  echo "Uncommitted changes detected. They will remain on the current branch ($current_branch)."
else
  echo "No uncommitted changes."
fi

# Create the new branch '1.8.9' from the current branch if it does not exist
if git show-ref --verify --quiet refs/heads/1.8.9; then
  echo "Branch '1.8.9' already exists. Skipping creation."
else
  git branch 1.8.9
  if [ $? -ne 0 ]; then
    echo "Error: Failed to create branch '1.8.9'." >&2
    exit 1
  fi
  echo "Branch '1.8.9' created from $current_branch."
fi

# Switch to the new branch '1.8.9'
git checkout 1.8.9
if [ $? -ne 0 ]; then
  echo "Error: Failed to switch to branch '1.8.9'." >&2
  exit 1
fi
echo "Switched to branch '1.8.9'."

# Check if gradle.properties exists
if [ ! -f gradle.properties ]; then
  echo "Error: gradle.properties file not found." >&2
  exit 1
fi

# Backup gradle.properties
cp gradle.properties gradle.properties.bak

# Update version entries in gradle.properties
sed -i \
  -e 's/^mcVersion.*/mcVersion = 1.8.9/' \
  -e 's/^forgeVersion.*/forgeVersion = 1.8.9-11.15.1.2318-1.8.9/' \
  -e 's/^mcpVersion.*/mcpVersion = 22-1.8.9/' \
  gradle.properties

echo "gradle.properties has been updated on branch '1.8.9'."

echo "Script completed.
- You are now on branch: 1.8.9
- gradle.properties version info updated
- Uncommitted changes remain on the original branch ($current_branch)."