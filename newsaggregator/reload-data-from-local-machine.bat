@echo off
echo Adding files...
git add data\final_articles.json
echo Committing changes...
git commit -m "Commit by batch file"
echo Checkout main
git checkout main
echo Pushing to remote repository...
git push origin main
echo Done!
