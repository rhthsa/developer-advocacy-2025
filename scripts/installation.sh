#!/bin/bash



####################################################
# Main (Entry point)
####################################################

option=$1
export USER_NAMESPACE=$2

if [[ $option = "all" ]]; then

elif [[ $option = "" ]]; then

else
    echo "invalid argument!"
fi

exit 0