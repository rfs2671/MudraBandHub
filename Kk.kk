# 1) See the files in the repo (you should see your .zip here)
ls -lah

# 2) Unzip it into the repo root (use your exact filename)
unzip mudra-link-db7e7f1e.zip -d .

# 3) If the zip created a folder, move its contents up to the repo root
#    (this handles a typical case where the zip unpacks into a single dir)
FOLDER="$(ls -1d */ | head -n1)"
if [ -n "$FOLDER" ] && [ "$FOLDER" != "./" ] && [ "$FOLDER" != "app/" ] && [ "$FOLDER" != ".git/" ]; then
  echo "Moving contents of $FOLDER to repo root..."
  shopt -s dotglob
  mv "$FOLDER"* .
  rm -rf "$FOLDER"
  shopt -u dotglob
fi

# 4) Remove the zip (optional)
rm -f mudra-link-db7e7f1e.zip

# 5) Verify you now see real folders/files (src/, app/, android/, package.json, etc.)
ls -lah

# 6) Commit and push the extracted code to GitHub
git add -A
git commit -m "Unzip and add Mudra Link source code"
git push
