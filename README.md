MEAN Stack Vagrant File
=========

Setups a precise64 box with the latest node.js, MongoDB, grunt and bower

Follow installation guide on http://docs.vagrantup.com/v2/installation/index.html to install Vagrant and VirtualBox

Control Vagrant
===================

###### Start box
```Shell
vagrant up
```

###### SSH into box
```Shell
vagrant ssh
```

###### Tear down box
```Shell
vagrant destroy
```

More documentation on http://www.vagrantup.com/

Update Play version
===================
Simply update the ```playVersion``` variable in bootstrap.sh