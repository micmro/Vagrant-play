#!/usr/bin/env bash

#variable
playVersion="2.1.4"
#"2.2.1"


echo "Provision VM START"
echo "=========================================="

sudo apt-get update

#install prerequisits
sudo apt-get install -y openjdk-7-jdk
sudo apt-get install -y scala
sudo apt-get install -y unzip

#install play
cd /home/vagrant
wget http://downloads.typesafe.com/play/$playVersion/play-$playVersion.zip
unzip -d /home/vagrant play-$playVersion.zip
rm play-$playVersion.zip
sudo ln -s /home/vagrant/play-$playVersion/play /usr/local/bin/play

echo ""
echo "=========================================="
echo javac -version
echo play -version
echo ""
echo "Play $playVersion set up"
echo "Provision VM finished"