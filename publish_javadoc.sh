#!/bin/sh
# Publish the JavaDoc to GitHub pages 
GIT_REPO=`pwd`
pushd ~/Temp/
rm -rf game-framework 
git clone git@github.com:thomsmits/game-framework.git
cd game-framework
git checkout gh-pages
rsync -raz $GIT_REPO/javadoc/ javadoc/
git add javadoc
git commit -m "Updated JavaDoc"
cp $GIT_REPO/out/artifacts/game_framework_jar/game_framework.jar .
git add game_framework.jar
git commit -m "Updated binaries"
git push origin gh-pages
cd ..
rm -rf game-framework
popd

