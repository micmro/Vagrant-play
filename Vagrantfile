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

  forward_port = ->(guest, host = guest) do
      config.vm.network :forwarded_port,
        guest: guest,
        host: host,
        auto_correct: true
  end

  forward_port[9000]           # activator run
  forward_port[8888]           # activator ui     
  forward_port[9999]           # unknow     

  config.vm.synced_folder "source-code/", "/home/vagrant/source-code", create: true
  config.vm.synced_folder "teste/", "/home/vagrant/teste", create: true

end
