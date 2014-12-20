# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  config.vm.box = "mesoscon"
  config.vm.box_url = "http://downloads.mesosphere.io/demo/mesoscon.box"

  # Create a private network, which allows host-only access to the machine
  # using a specific IP.
  config.vm.network :private_network, ip: "10.141.141.10"

  # Enable agent forwarding.
  config.ssh.forward_agent = true

  # Forward ports for Mesos, Zookeeper, ElasticSearch
  config.vm.network "forwarded_port", guest: 5050, host: 5050  
  config.vm.network "forwarded_port", guest: 2181, host: 2181
  config.vm.network "forwarded_port", guest: 9200, host: 9200

  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  config.vm.synced_folder ".", "/vagrant", :disabled => true
  config.vm.synced_folder "./", "/home/vagrant/sandbox"

  # Provider-specific configuration
  config.vm.provider :virtualbox do |vb|
    vb.customize ["modifyvm", :id, "--ioapic", "on"]
    vb.customize ["modifyvm", :id, "--cpus", "2"]
    vb.customize ["modifyvm", :id, "--memory", "2048"]
  end

  config.vm.provision "shell",
    inline: "cd /home/vagrant/sandbox; tar zxvf elasticsearch-mesos-*; ./bin/elasticsearch-mesos"

end

