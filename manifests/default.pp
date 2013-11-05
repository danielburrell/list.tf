include bootstrap
include stdlib

class { '::mysql::server':
  root_password    => 'blah',
  override_options => {
    'mysqld' => {

      bind_address => '0.0.0.0',
    }
  }
}

mysql::db { 'wanted':
  user     => 'wanted',
  password => 'wanted',
  host     => '10.0.2.2',
  ensure   => 'present',
  charset  => 'utf8',

}

/*
 * mysql_grant { 'wanted@localhost/wanted.details':
 * ensure     => 'present',
 * options    => ['GRANT'],
 * privileges => ['ALL'],
 * table      => '*.*',
 * user       => 'root@localhost',
 *}
 */
