MAC0332 - Software Engineering Project (IME-USP) [2015]
=========
For original description of this repository see ORIGINAL_README.md.  

Setup
========
First of all you need the virtualbox >= 5 installed on your machine and the newest version of vagrant. For this run the following commands (Linux-like):  

    sudo apt-get update
    sudo apt-get install virtualbox-5.0

Then [install vagrant](http://www.vagrantup.com/downloads) according to your SO. After that clone this repository and inside the project folder run the following commands : 

    vagrant up
    
This will start the installation of vagrant box, could take some time to finished. When the installation ends run : 

    vagrant ssh

This will connect your terminal to vagrant box. All the stuffs that you need to run your application will be installed inside the box. The folder **source_code** is a synced folder between vagrant and your machine. You could edit the source code outside the vagrant box and all the changes will automatically applied inside the box.  

For more informations about [vagrant](https://docs.vagrantup.com/v2/) and [play-framework](https://www.playframework.com/documentation) check the official documentation.  
