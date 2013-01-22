Padlock 2.2.X
=============

This is a fork of Padlock and my objective is to clean up the code and simplify it so that it can be more easily be used as part of
a maven tool chain.  Thus the UI will no longer be supported in this fork.

Command Line Tools
------------------

This distribution is now focused on command line tools only.  There is a dist tar ball created that has 3 scripts and the related jars,
just untar it somewhere and you can execute the scripts directly.

1) Key Maker 

$ ./keymaker.sh 
Error: Option "-k" is required
 -j          : Print Key Java Fragment
 -k Key File : Key File

$ ./keymaker.sh -k my.key -j
Your public key code: 

	 private static final String publicKey = 
		"308201b73082012c06072a8648ce3804013082011f02818100fd7f53811d" + 
		"75122952df4a9c2eece4e7f611b7523cef4400c31e3f80b6512669455d40" + 
		"2251fb593d8d58fabfc5f5ba30f6cb9b556cd7813b801d346ff26660b76b" + 
		"9950a5a49f9fe8047b1022c24fbba9d7feb7c61bf83b57e7c6a8a6150f04" + 
		"fb83f6d3c51ec3023554135a169132f675f3ae2b61d72aeff22203199dd1" + 
		"4801c70215009760508f15230bccb292b982a2eb840bf0581cf502818100" + 
		"f7e1a085d69b3ddecbbcab5c36b857b97994afbbfa3aea82f9574c0b3d07" + 
		"82675159578ebad4594fe67107108180b449167123e84c281613b7cf0932" + 
		"8cc8a6e13c167a8b547c8d28e0a3ae1e2bb3a675916ea37f0bfa213562f1" + 
		"fb627a01243bcca4f1bea8519089a883dfe15ae59f06928b665e807b5525" + 
		"64014c3bfecf492a038184000281800cc40b67b8498857146da220733623" + 
		"a82da924d162413f9db5660014ee15e1754542eb34aecb5d17a61f9fc9b4" + 
		"c81c1873d145bb43af18f8749c930cb99bb3230678733ad56efca4b2ba49" + 
		"238739f25577f5153422902f9a987e5d809639509c69afa118b4c7139d76" + 
		"34be327bb1a874614390532b672fc69e689a5bc76a39eb";

2) License Maker

$ ./licensemaker.sh 
Error: Option "-k" is required
 -E Expiration       : License expriation date. If this option is omitted the
                       license is perpetual. The date format is yyyy/MM/dd.
 -O                  : License File Output to standard out instead of a file
 -P Properties File  : License properties file
 -S Start            : The start of the license validity period, if different
                       then the current date.  The date format is yyyy/MM/dd.
 -e Expiration       : License expiration date. If this option is omitted the
                       license is perpetual. In ms since the epoch (1/1/1970)
 -h Addresses        : Hardware locked addresses, in the form of mac1, mac2,
                       mac3
 -k Key File         : Key File
 -o License File     : Output License File
 -p Properties       : License properties, Expressed as options of the form:
                       key1=value1, key2=value2
 -s Start            : The start of the license validity period, different than
                       the current date.  In ms since the epoch (1/1/1970)
 -x Expiration Float : Number of ms to expire after the initial run

./licensemaker.sh -k my.key -o my.lic

3) License Validator

$ ./licensevalidator.sh 
Error: Option "-k" is required
 -k Key File     : Key File
 -l License File : License File

./licensevalidator.sh -k my.key -l my.lic

	