INSERT INTO destinations
VALUES ((SELECT nextval('destinations_id_seq')), 'Quintessential Japan', 'Japan', 'Life-changing-experience');
INSERT INTO destination_facts VALUES
  ((SELECT currval('destinations_id_seq')),
   'There are over 5.5 million vending machines in Japan selling everything from umbrellas and cigarettes to canned bread and hot noodles.'),
  ((SELECT currval('destinations_id_seq')),
   'Japan''s birth rate has plummeted so significantly that adult nappies (diapers) outsell babies'' nappies, which are also sold in vending machines.'),
  ((SELECT currval('destinations_id_seq')),
   'It is estimated that more paper is used for manga comics than for toilet paper in Japan. (Surprise: both are sold in vending machines as well.)'),
  ((SELECT currval('destinations_id_seq')),
   'One of the world''s most famous pilgrimage routes after the Camino de Santiago is Japan''s Kumano Kodo near Osaka.');

INSERT INTO travel_agents (id, name, country, postal_code, email, website) VALUES
  ((SELECT nextval('travel_agents_id_seq')), 'Shoestring', 'NL', '1114 AA', 'info@shoestring.nl',
   'https://shoestring.nl');

INSERT INTO holiday_packages (id, destination, depart_from, package_info) VALUES
  ((SELECT nextval('holiday_packages_id_seq')), 1, 'Amsterdam Schipol', 'New horizons in the land of the rising sun');

INSERT INTO travel_agent_holiday_packages
VALUES ((SELECT currval('travel_agents_id_seq')), (SELECT currval('holiday_packages_id_seq')));

