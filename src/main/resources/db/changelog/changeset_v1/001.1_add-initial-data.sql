INSERT INTO assistance (assistance_type, description, executor)
VALUES ('GUIDE', 'Экскурсионный гид', 'ИП Иван Иванов'),
       ('AUTO_TOUR', 'Автобусные туры', 'Мария Петрова'),
       ('CATERING', 'Кейтеринг', 'ООО Вкусняшки');

INSERT INTO locality (name, region)
VALUES ('Москва', 'Москва'),
       ('Санкт-Петербург', 'Ленинградская область'),
       ('Екатеринбург', 'Свердловская область');

INSERT INTO attraction (name, created, description, attraction_type, locality_id)
VALUES ('Central Park', '2020-01-01 00:00:00', 'A large public park in NYC', 'PARK', 1),
       ('Universal Studios', '2019-06-01 00:00:00', 'A movie theme park in LA', 'CASTLE', 2),
       ('Willis Tower', '2018-03-01 00:00:00', 'A skyscraper in Chicago', 'MUSEUM', 3),
       ('Empire State Building', '2017-09-01 00:00:00', 'A iconic skyscraper in NYC', 'RESERVE', 1),
       ('Griffith Observatory', '2016-12-01 00:00:00', 'An observatory in LA', 'ARCHAEOLOGICAL_SITE', 2);

INSERT INTO assistance_attraction (assistance_id, attraction_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 5);