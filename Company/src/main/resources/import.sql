INSERT INTO `quatrofantasticoxmlcompany`.`lokacija` (`naziv`,`ptt`) VALUES ("Beograd","11000"), ("Novi Sad","21000"), ("Zrenjanin","23000"),  ("Sremska Mitrovica","22000"), ( "Kragujevac","34000");

INSERT INTO `quatrofantasticoxmlcompany`.`firma` (`adresa`,`naziv`,`pib`,`lokacija_id`) VALUES ("Pozeska BB", "Pionir d.o-o.", 111111111, 1), ("Zrenjaninski put 100", "Imlek", 222222222, 1), ("Jovana Cvijica 13", "Dijamant d.o.o.", 333333333, 3), ("Bulevar Oslobodjenja 112", "3G Shop d.o-o.", 444444444, 2);

INSERT INTO `quatrofantasticoxmlcompany`.`poslovni_partner` (`adresa`,`naziv`,`pib`,`firma_id`,`lokacija_id`) VALUES ("Janka Cmelika 29", "MAX IMPORT-EXPORT",12121212, 1, 2), ("Gunduliceva 100", "4x4 OPREMA D.O.O",12121333, 2, 1), ("Stevana Sremca 13", "DEXI SHPED", 121245676, 1, 4), ("Prva proleterska 1", "MAXI",133345675, 1, 5), ("Zelengorska 13", "UNIVEREXPORT D.O.O.", 555555555, 1, 2);

INSERT INTO `quatrofantasticoxmlcompany`.`zaposleni` (`adresa`,`ime`,`jmbg`,`prezime`,`lozinka`,`korisnicko_ime`,`firma_id`,`lokacija_id`) VALUES ("Pariskih komuna 10","Pera", 1204966345060, "Peric","pera", "pera", 1, 1), ("Kikindska 68", "Mika", 2104966345060,"Mikic","mika", "mika", 1, 1), ("Zelengorska 2a","zika", 2901975345161, "zikic","zika", "zika", 2, 1);

INSERT INTO `quatrofantasticoxmlcompany`.`tpodaci_subjekt` (`adresa`, `naziv`, `pib`) VALUES ('Pozeska PB', 'Pionir d.o.o', 111111111), ("Zrenjaninski put 100", "Imlek", 222222222);

INSERT INTO `quatrofantasticoxmlcompany`.`faktura` (`broj_racuna`, `datum_racuna`, `datum_valute`, `id_poruke`, `iznos_za_uplatu`, `oznaka_valute`, `ukupan_porez`, `ukupan_rabat`, `ukupno_roba_i_usluga`, `uplata_na_racun`, `vrednost_robe`, `vrednost_usluga`, `podaciodobavljacu_id`, `podaciokupcu_id`) VALUES (123456, "2017-05-10","2017-05-12", 2, 14000.00, "RSD", 7000.00, 4000.00, 14000.00, "840-1234567894754-12", 14000.00, 0.00, 1, 2);

INSERT INTO `quatrofantasticoxmlcompany`.`tstavka_faktura` (`redni_broj`, `naziv_robe_usluge`, `kolicina`, `jedinica_mere`, `jedinicna_cena`, `vrednost`, `procenat_rabata`, `iznos_rabata`, `umanjeno_za_rabat`, `ukupan_porez`, `faktura_id` ) VALUES (1, "Cokoladno mleko", 15.00, "kg", 50.00, 750.00, 18.00, 100.00, 14.00, 2000.00, 1), (2, "Jogurt", 30.00, "litar", 100.00, 3000.00, 18.00, 200.00, 33.00, 20000.00, 1);