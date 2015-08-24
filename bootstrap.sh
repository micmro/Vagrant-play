#!/usr/bin/env bash

#variables
activatorVersion="1.3.5"
sbtVersion="0.13.9"

echo "Provision VM START"
echo "=========================================="

sudo apt-get update

###############################################
# install prerequisits
###############################################
sudo apt-get -y -q upgrade
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get -y -q update
sudo apt-get -y -q install software-properties-common htop
sudo apt-get -y -q install build-essential
sudo apt-get -y -q install tcl8.5

###############################################
# Install Java 8
###############################################
# sudo apt-get install -y openjdk-8-jdk
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo apt-get -y -q install oracle-java8-installer
sudo update-java-alternatives -s java-8-oracle

###############################################
# In case you need Java 7
###############################################
# sudo apt-get install -y openjdk-7-jdk
#echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
#sudo apt-get -y -q install oracle-java7-installer

###############################################
# Install Git
###############################################
#sudo apt-get -y install git-core

###############################################
# Install imagemagick
###############################################
#sudo apt-get -y install imagemagick

###############################################
# Install Scala
###############################################
sudo apt-get -y install scala

###############################################
# Install Unzip
###############################################
sudo apt-get -y install unzip

###############################################
# Install NodeJS
###############################################
sudo apt-get -y install nodejs
ln -s /usr/bin/nodejs /user/bin/node

###############################################
# Install NPM
###############################################
sudo apt-get -y install npm

###############################################
#install CoffeeScript
###############################################
sudo npm install coffee-script

###############################################
#install Bower
###############################################
npm install bower -g

###############################################
#install SBT
###############################################
wget http://dl.bintray.com/sbt/debian/sbt-$sbtVersion.deb
sudo dpkg -i sbt-$sbtVersion.deb
sudo apt-get update
sudo apt-get install sbt
rm sbt-$sbtVersion.deb

###############################################
#install Redis
# More info about it: https://www.digitalocean.com/community/tutorials/how-to-install-and-use-redis
###############################################
echo "Download redis..."
wget http://download.redis.io/releases/redis-stable.tar.gz
tar xzf redis-stable.tar.gz
echo "Init install..."
cd redis-stable
make
make test
sudo make install
cd utils
sudo ./install_server.sh
echo "Redis done"

###############################################
#install MongDB
###############################################
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
echo "deb http://repo.mongodb.org/apt/ubuntu "$(lsb_release -sc)"/mongodb-org/3.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.0.list
sudo apt-get update
sudo apt-get -y install mongodb-org

###############################################
#install typesafe activator
###############################################
cd /home/vagrant
wget http://downloads.typesafe.com/typesafe-activator/$activatorVersion/typesafe-activator-$activatorVersion-minimal.zip
unzip -d /home/vagrant typesafe-activator-$activatorVersion-minimal.zip
rm typesafe-activator-$activatorVersion-minimal.zip
#export PATH=/home/vagrant/activator-${activatorVersion}-minimal:$PATH >> ~/.bashrc

###############################################
#add activator to environment variables
###############################################
echo "export PATH=/home/vagrant/activator-$activatorVersion-minimal:\$PATH" >> ~/.bashrc

###############################################
#use node as default JavaScript Engine
###############################################
echo "export SBT_OPTS=\"\$SBT_OPTS -Dsbt.jse.engineType=Node\"" >> ~/.bashrc

###############################################
#reset bash
###############################################
source ~/.bashrc

###############################################
# Download dependencies and show activator help
# So we don't need to wait later
###############################################
/home/vagrant/activator-$activatorVersion-minimal/activator help

echo ""
echo ""
echo "=========================================="
echo "=========================================="
echo "Dependencies installed:"
echo "jdk version:"
javac -version
echo ""
echo "Activator version:"
activator --version
echo ""
echo "Redis version"
redis-server -v
echo ""
echo "NodeJS version:"
nodejs -v
echo ""
echo "NPM version"
npm -v
echo "=========================================="
echo "=========================================="
echo ""
echo ""
echo "Provision VM finished"
