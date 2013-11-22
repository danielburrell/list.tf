include bootstrap
include stdlib

class { '::mysql::server':
  root_password    => 'root',
  override_options => {
    'mysqld' => {

      bind_address => '0.0.0.0',
    }
  }
}


mysql_user { 'qwanted@%':
  password_hash => '*6013804288C90692CAD2C56A77B2BCBF38E4C848',
  ensure                   => 'present',
  max_connections_per_hour => '0',
  max_queries_per_hour     => '0',
  max_updates_per_hour     => '0',
  max_user_connections     => '0',
}

mysql_grant { 'qwanted@%/*.*':
  ensure     => 'present',
  options    => ['GRANT'],
  privileges => ['ALL'],
  table      => '*.*',
  user       => 'qwanted@%',
}
#grant =>
/*mysql::db { 'wanted':
  user     => 'pwanted',
  password => 'pwanted',
  host     => '10.0.2.2',
  ensure   => 'present',
  charset  => 'utf8',

}*/

/*
 * mysql_grant { 'wanted@localhost/wanted.details':
 * ensure     => 'present',
 * options    => ['GRANT'],
 * privileges => ['ALL'],
 * table      => '*.*',
 * user       => 'root@localhost',
 *}
 */
