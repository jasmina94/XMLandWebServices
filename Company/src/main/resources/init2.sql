INSERT INTO `quatrofantasticoxmlcompany2`.`tpodaci_subjekt` (`adresa`,`naziv`,`pib`,`racun_firme`, `company_url`) VALUES ("Pozeska BB", "Pionir d.o.o.", "11111111111", "265-2234522100014-72","http://localhost:8090/"), ("Zrenjaninski put 100", "Imlek", "22222222222", "265-2234522100015-72","http://localhost:8091/");

INSERT INTO `quatrofantasticoxmlcompany2`.`tpodaci_subjekt_poslovni_partneri`(`tpodaci_subjekt_id`,`poslovni_partneri_id`)VALUES(2, 1);

INSERT INTO `quatrofantasticoxmlcompany2`.`zaposleni` (`adresa`,`ime`,`jmbg`,`prezime`,`lozinka`,`korisnicko_ime`,`t_podaci_subjekt_id`) VALUES ("Kikindska 68", "Mika", 2104966345060,"Mikic","mika", "mika", 2);

INSERT INTO `quatrofantasticoxmlcompany2`.`roba_usluga`(`cena`, `jedinica_mere`, `naziv`, `procenat_rabata`, `procenat_poreza`, `tip`) VALUES(100.00, 'l', 'Cokoladno mleko', 2.00, 2.00, FALSE ), (110.00, 'l', 'Jogurt', 3.00, 3.00, FALSE );