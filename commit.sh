#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
echo "Location of [$(basename "${BASH_SOURCE[0]}")] is: $SCRIPT_DIR"

git config pull.rebase true
git config init.defaultBranch main
git config core.autocrlf false
git config push.autoSetupRemote true
git config user.name "Andy"
git config user.email "duyanhnn@outlook.com"
git fetch &> /dev/null
[[ "$1" != "--no-stash" ]] && git stash
git pull &> /dev/null
[[ "$1" != "--no-stash" ]] && git stash pop
./clean.sh
git add $SCRIPT_DIR
commit_msg="Auto-generated commit message: /dev/null"
[[ ! -z "$2" && "$2" != "" ]] && commit_msg="$2"
git commit -m "$commit_msg"
git push
