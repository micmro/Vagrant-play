#!/usr/bin/env bash

#variables
activatorVersion="1.2.2"


echo "Provision VM START"
echo "=========================================="

sudo apt-get update

#install prerequisits
sudo apt-get install -y openjdk-7-jdk
sudo apt-get install -y scala
sudo apt-get install -y unzip
sudo apt-get install -y nodejs
sudo apt-get update

#install typesafe activator
cd /home/vagrant
wget http://downloads.typesafe.com/typesafe-activator/$activatorVersion/typesafe-activator-$activatorVersion-minimal.zip

unzip -d /home/vagrant typesafe-activator-$activatorVersion-minimal.zip
rm typesafe-activator-$activatorVersion-minimal.zip

#export PATH=/home/vagrant/activator-1.2.2-minimal:$PATH >> ~/.bashrc

wget http://dl.bintray.com/sbt/debian/sbt-0.13.5.deb
sudo dpkg -i sbt-0.13.5.deb
sudo apt-get update
sudo apt-get install sbt
rm sbt-0.13.5.deb

#add activator to environment variables
echo "export PATH=/home/vagrant/activator-$activatorVersion-minimal:\$PATH" >> ~/.bashrc
#use node as default JavaScript Engine
echo "export SBT_OPTS=\"\$SBT_OPTS -Dsbt.jse.engineType=Node\"" >> ~/.bashrc

#reset bash
source ~/.bashrc

#download dependencies and show activator help - so we don't need to wait later
/home/vagrant/activator-$activatorVersion-minimal/activator help

echo ""
echo "=========================================="
#init and shop activator help
echo "jdk version:"
javac -version


echo ""
echo "Provision VM finished"