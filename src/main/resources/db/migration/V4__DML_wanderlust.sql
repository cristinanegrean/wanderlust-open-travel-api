INSERT INTO destinations VALUES (1, 'Quintessential Japan', 'Japan', 'Life-changing-experience');
INSERT INTO destination_facts VALUES
  (1, 'There are over 5.5 million vending machines in Japan selling everything from umbrellas and cigarettes to canned bread and hot noodles.', 1),
  (1, 'Japan''s birth rate has plummeted so significantly that adult nappies (diapers) outsell babies'' nappies, which are also sold in vending machines.', 2),
  (1, 'It is estimated that more paper is used for manga comics than for toilet paper in Japan. (Surprise: both are sold in vending machines as well.)', 3),
  (1, 'One of the world''s most famous pilgrimage routes after Spain''s Camino de Santiago is Japan''s Kumano Kodo near Osaka.', 4);

INSERT INTO travel_agents(id, name, country, email, website) VALUES
  (1, 'Shoestring', 'NL', 'info@shoestring.nl', 'https://shoestring.nl');

INSERT INTO holiday_packages (id, destination, depart_from, package_info) VALUES
  (1, 1, 'Amsterdam Schipol', 'New horizons in the land of the rising sun');

INSERT INTO travel_agent_holiday_packages VALUES (1, 1);