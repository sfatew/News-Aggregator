@echo off
echo Adding files...
git add data\final_articles.json
echo Committing changes...
git commit -m "Commit by batch file"
echo Checkout reload-data-from-local-machine
git checkout reload-data-from-local-machine
echo Pushing to remote repository...
git push origin reload-data-from-local-machine
echo Done!
