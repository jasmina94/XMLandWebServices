INSERT INTO `quatrofantasticoxmlcompany1`.`tpodaci_subjekt` (`adresa`,`naziv`,`pib`,`racun_firme`) VALUES ("Pozeska BB", "Pionir d.o.o.", "11111111111", "265-2234522100014-72"), ("Zrenjaninski put 100", "Imlek", "22222222222", "265-2234522100015-72"), ("Jovana Cvijica 13", "Dijamant d.o.o.", "33333333333", "205-2200148916622-71"), ("Bulevar Oslobodjenja 112", "3G Shop d.o-o.", "44444444444", "265-2234522100016-72");

INSERT INTO `quatrofantasticoxmlcompany1`.`tpodaci_subjekt_poslovni_partneri`(`tpodaci_subjekt_id`,`poslovni_partneri_id`)VALUES(1,2);

INSERT INTO `quatrofantasticoxmlcompany1`.`zaposleni` (`adresa`,`ime`,`jmbg`,`prezime`,`lozinka`,`korisnicko_ime`,`t_podaci_subjekt_id`) VALUES ("Pariskih komuna 10","Pera", 1204966345060, "Peric","pera", "pera", 1), ("Kikindska 68", "Mika", 2104966345060,"Mikic","mika", "mika", 2), ("Zelengorska 2a","zika", 2901975345161, "zikic","zika", "zika", 3), ("Ulica brestova 44","Mira", 1304888545961, "Miric","mira", "mira", 4);

INSERT INTO `quatrofantasticoxmlcompany1`.`faktura` (`broj_racuna`, `datum_racuna`, `datum_valute`, `id_poruke`, `iznos_za_uplatu`, `oznaka_valute`, `ukupan_porez`, `ukupan_rabat`, `ukupno_roba_i_usluga`, `uplata_na_racun`, `vrednost_robe`, `vrednost_usluga`, `podaciodobavljacu_id`, `podaciokupcu_id`, `poslato`, `kreiran_nalog`) VALUES (123456, "2017-05-10","2017-05-12", 2, 14000.00, "RSD", 7000.00, 4000.00, 14000.00, "840-1234567894754-12", 14000.00, 0.00, 1, 2, FALSE, FALSE ), (111111, "2017-05-22","2017-05-23", 2, 27000.00, "RSD", 12000.00, 5000.00, 270000.00, "840-1234567884754-12", 20000.00, 70000.00, 1, 2, FALSE, FALSE ),(222222, "2017-05-01","2017-05-05", 3, 2000.00, "RSD", 800.00, 5000.00, 20000.00, "840-1234444484754-12", 1500.00, 500.00, 2, 1, FALSE, FALSE ), (444444, "2017-05-04","2017-05-05", 2, 20000.00, "RSD", 8000.00, 5000.00, 20000.00, "840-1234444487774-12", 15000.00, 500.00, 2, 1, FALSE, FALSE );

INSERT INTO `quatrofantasticoxmlcompany1`.`roba_usluga`(`cena`, `jedinica_mere`, `naziv`, `procenat_rabata`) VALUES(100.00, 'gr', 'Plazma', 2.00), (110.00, 'l', 'Jogurt', 3.00);

INSERT INTO `quatrofantasticoxmlcompany1`.`tstavka_faktura` (`redni_broj`, `naziv_robe_usluge`, `kolicina`, `jedinica_mere`, `jedinicna_cena`, `vrednost`, `procenat_rabata`, `iznos_rabata`, `umanjeno_za_rabat`, `ukupan_porez`, `faktura_id` ) VALUES (1, "Cokoladno mleko", 15.00, "kg", 50.00, 750.00, 18.00, 100.00, 14.00, 2000.00, 1), (2, "Jogurt", 30.00, "litar", 100.00, 3000.00, 18.00, 200.00, 33.00, 20000.00, 1), (1, "Jogurt", 30.00, "litar", 100.00, 3000.00, 18.00, 200.00, 33.00, 20000.00, 3), (1, "Mleko", 30.00, "litar", 100.00, 3000.00, 18.00, 200.00, 33.00, 20000.00, 4);