Typesafe Activator / play! Framework Vagrant File
=========

Sets up a trusty64 Ubuntu box (with 2GB RAM) with Java and the [Typesafe Activator](https://typesafe.com/activator) to get started with [play!](playframework.com) and rest of the [Typesafe Reactive Platform](https://typesafe.com/platform). Even though included in the activator this vagrant file also provision a stand-alone version of Scala and sbt - to use them seperatly.

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

###### Shut down box
```Shell
vagrant halt
```

###### Tear down box
```Shell
vagrant destroy
```

More documentation on http://www.vagrantup.com/

Running your existing app in Vagrant
===================
Store your existing app in a folder named `activator-project` as a sibling of the one containing the Vagrant files. `vagrant up` the box and `vagrant ssh` into it. Now you can `cd /activator-project/` and start it via `activator run`.

You might have to restart vagrant box before running your app the first time, if the activator hangs on installing sbt.