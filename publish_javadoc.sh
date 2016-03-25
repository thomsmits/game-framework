#!/bin/sh
GIT_REPO=`pwd`
pushd ~/Temp/
rm -rf game-framework 
git clone git@github.com:thomsmits/game-framework.git
cd game-framework
git checkout gh-pages
rsync -raz $GIT_REPO/javadoc/ javadoc/
git add javadoc
git commit -m "Updated JavaDoc"
git push origin master
cd ..
rm -rf game-framework
popd
