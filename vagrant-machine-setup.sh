#!/usr/bin/env bash

#variables
activatorVersion="1.3.5"
sbtVersion="0.13.9"

echo "=========================================="
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
sudo apt-get -y install git

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
curl --silent --location https://deb.nodesource.com/setup_4.x | sudo bash -
sudo apt-get -y install nodejs
ln -s /usr/bin/nodejs /user/bin/node
# Add node_modules to environment variables
echo "export NODE_PATH=/usr/local/lib/node_modules" >> ~/.bashrc

###############################################
# Install NPM
###############################################
sudo apt-get -y install npm

###############################################
# Install CoffeeScript
###############################################
sudo npm install -g coffee-script

###############################################
# Install Bower
###############################################
sudo npm install -g bower

###############################################
# Install Sass
###############################################
sudo gem install sass

###############################################
# Install Redis
# More info about it: https://www.digitalocean.com/community/tutorials/how-to-install-and-use-redis
###############################################
echo "Download Redis..."
wget http://download.redis.io/releases/redis-stable.tar.gz
tar xzf redis-stable.tar.gz
cd redis-stable
make
make test
sudo make install
cd utils
sudo ./install_server.sh
cd /home/vagrant/
rm redis-stable.tar.gz
echo "Redis done."

###############################################
# Install PostgreSQL
###############################################
sudo apt-get -y install postgresql postgresql-contrib postgresql-client-common postgresql-common

###############################################
# Install SBT
###############################################
echo "Download SBT..."
wget http://dl.bintray.com/sbt/debian/sbt-$sbtVersion.deb
sudo dpkg -i sbt-$sbtVersion.deb
sudo apt-get update
sudo apt-get install sbt
rm sbt-$sbtVersion.deb

echo "SBT done."
# Use node as default JavaScript Engine
echo "export SBT_OPTS=\"\$SBT_OPTS -Dsbt.jse.engineType=Node\"" >> ~/.bashrc

###############################################
# Install typesafe activator
###############################################
cd /home/vagrant
echo "Download Typesafe Activator..."
wget http://downloads.typesafe.com/typesafe-activator/$activatorVersion/typesafe-activator-$activatorVersion-minimal.zip
unzip -d /home/vagrant typesafe-activator-$activatorVersion-minimal.zip
rm typesafe-activator-$activatorVersion-minimal.zip
echo "Typesafe Activator done."
# Add activator to environment variables
echo "export PATH=/home/vagrant/activator-$activatorVersion-minimal:\$PATH" >> ~/.bashrc

###############################################
# Reset bash
###############################################
source ~/.bashrc

###############################################
# Show installation summary
###############################################
echo "=========================================="
echo "Provision VM summary"
echo "=========================================="
echo "Dependencies installed:"
echo " "
echo "jdk version:"
javac -version
echo " "
# echo "Activator version:"
# activator --version
echo " "
echo "Redis version"
redis-server -v
echo " "
echo "NodeJS version:"
nodejs -v
echo " "
echo "CoffeeScript version:"
coffee --version
echo " "
echo "NPM version"
npm -v
echo " "
echo "Sass version:"
sass -v
echo " "
echo "PostgreSQL version"
psql -V
echo " "
echo "Git version"
git --version
echo " "
echo "=========================================="
echo "Provision VM finished"
echo "=========================================="
