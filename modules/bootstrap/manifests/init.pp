class bootstrap {

	Exec {
path => [
'/usr/bin',
'/bin', '/usr/sbin',
'/sbin',
'/usr/local/bin',
'/usr/local/sbin'
],
}
exec {
'apt-get update':
command => 'apt-get update && apt-get upgrade -y',
timeout => 0,
}
Exec['apt-get update'] -> Package <| |>
}