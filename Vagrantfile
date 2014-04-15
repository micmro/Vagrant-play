# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"


Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  # box to build virtual evironment to build off of.
  config.vm.box = "precise64"
  # config.vm.box = "precise32"
  
  # The url from where the 'config.vm.box' box will be fetched if it
  # doesn't already exist on the user's system.
  # http://www.vagrantbox.es/
  config.vm.box_url = "http://files.vagrantup.com/precise64.box"
  # config.vm.box_url = "http://files.vagrantup.com/precise32.box"

  # configurating the vm 
  config.vm.provider "virtualbox" do |v|
    v.name = "play_hackathon"
    # max 75% CPU cap
    v.customize ["modifyvm", :id, "--cpuexecutioncap", "75"]
    # 4GB ram
    v.memory = 4094
  end

  # run "bootstrap.sh" shell script when setting up our machine
  config.vm.provision :shell, :privileged => false, :path => "bootstrap.sh"


  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine. In the example below,
  # accessing "localhost:8080" will access port 80 on the guest machine.
  config.vm.network :forwarded_port, guest: 9000, host: 9000
  config.vm.network :forwarded_port, guest: 9999, host: 9999 

  # Create a private network, which allows host-only access to the machine
  # using a specific IP.
  # config.vm.network :private_network, ip: "192.168.33.10"


  # Create a public network, which generally matched to bridged network.
  # Bridged networks make the machine appear as another physical device on
  # your network.
  # config.vm.network :public_network


  # If true, then any SSH connections made will enable agent forwarding.
  # Default value: false
  # config.ssh.forward_agent = true


  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  config.vm.synced_folder "data", "/vagrant_data"

end
