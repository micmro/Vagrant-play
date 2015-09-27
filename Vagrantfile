# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.hostname = "vagrant-play"
  config.vm.provider "virtualbox" do |v|
    v.name = "eng-soft-project"
    # max 75% CPU cap
    v.customize ["modifyvm", :id, "--cpuexecutioncap", "75"]
    # give vm max 3GB ram
    v.memory = 2048
  end

  config.vm.provision :shell, :privileged => false, :path => "vagrant-machine-setup.sh"
  config.vm.provision :shell, :privileged => false, :path => "vagrant-machine-run.sh",run: "always"

  config.vm.network :forwarded_port, guest: 9000, host: 9000
  config.vm.network :forwarded_port, guest: 9999, host: 9999

  config.vm.synced_folder "source-code/", "/home/vagrant/source-code", create: true

end
