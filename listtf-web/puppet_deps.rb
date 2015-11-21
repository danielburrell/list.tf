def install_dep(name, install_dir = nil)
    install_dir ||= '/etc/puppet/modules'
    "mkdir -p #{install_dir} && (puppet module list | grep #{name}) || puppet module install #{name}"
end