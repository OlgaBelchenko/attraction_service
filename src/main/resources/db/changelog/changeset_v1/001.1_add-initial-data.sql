INSERT INTO assistance (id, assistance_type, description, executor)
VALUES (1, 'Medical', 'Medical assistance', 'Dr. Smith'),
       (2, 'Technical', 'Technical support', 'John Doe'),
       (3, 'Financial', 'Financial aid', 'Jane Doe'),
       (4, 'Logistical', 'Logistical support', 'Bob Johnson'),
       (5, 'Emotional', 'Emotional support', 'Sarah Lee');

INSERT INTO locality (id, name, region)
VALUES (1, 'New York City', 'North East'),
       (2, 'Los Angeles', 'West Coast'),
       (3, 'Chicago', 'Mid West');

INSERT INTO attraction (id, name, created, description, attraction_type, locality_id)
VALUES (1, 'Central Park', '2020-01-01 00:00:00', 'A large public park in NYC', 'Park', 1),
       (2, 'Universal Studios', '2019-06-01 00:00:00', 'A movie theme park in LA', 'Theme Park', 2),
       (3, 'Willis Tower', '2018-03-01 00:00:00', 'A skyscraper in Chicago', 'Landmark', 3),
       (4, 'Empire State Building', '2017-09-01 00:00:00', 'A iconic skyscraper in NYC', 'Landmark', 1),
       (5, 'Griffith Observatory', '2016-12-01 00:00:00', 'An observatory in LA', 'Museum', 2),
       (6, 'Navy Pier', '2015-07-01 00:00:00', 'A lakefront attraction in Chicago', 'Pier', 3),
       (7, 'Statue of Liberty', '2014-04-01 00:00:00', 'A iconic statue in NYC', 'Landmark', 1),
       (8, 'Santa Monica Pier', '2013-02-01 00:00:00', 'A beachfront pier in LA', 'Pier', 2);

INSERT INTO assistance_attraction (assistance_id, attraction_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 8)