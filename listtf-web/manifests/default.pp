# java
class { 'jdk_oracle': }

file { 'datadir':
  path   => '/data',
  ensure => 'directory',
  mode   => 777,
}

# mongodb
class { '::mongodb::server':
  dbpath  => '/data/mongodb',
  bind_ip => '0.0.0.0',
  port    => 27017,
  require => File['datadir']
}

