#!/bin/bash


QUIET=false
DEBUG=false


# Command reference: https://www.digitalocean.com/community/tutorials/maven-commands-options-cheat-sheet

mvn_options=""

# Add optional parameters
[ "$QUIET" = true ] && mvn_options="$mvn_options -q"
[ "$DEBUG" = true ] && mvn_options="$mvn_options -X"

# Construct final Maven command
mvn_command="mvn clean package $mvn_options"

echo "Executing: $mvn_command"
$mvn_command
