#!/bin/sh
#  stashshowall.sh
for stash in `git stash list | sed 's/\(\w*\)\:.*/\1/'`
do
    echo
    echo "$stash"
    git stash show $stash
done