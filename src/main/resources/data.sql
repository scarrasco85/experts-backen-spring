

INSERT INTO tags (name, creator, created_at) values ('Power BI','Antonio Hernández', '2021-04-07 08:35:50');
INSERT INTO tags (name, creator, created_at) values ('Spring', 'Fernando Alonso','2021-04-07 08:35:50');
INSERT INTO tags (name, creator, created_at) values ('Angular','Macario González', '2021-04-07 08:35:50');
INSERT INTO tags (name, creator,created_at) values ('NodeJS','Luis Vázquez','2021-04-07 08:35:50');
INSERT INTO tags (name, creator, created_at) values ('React','Antonio Hernández','2021-04-07 08:35:50');

INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status, score) values ('Miguel Morato','2021-04-07 08:35:50', 'mañanas', '666010101','miguel@example.com', 'Cádiz',10.00, 15.00, '12321232K', 'pendiente', '100');
INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status, score) values ('Antonio Rodríguez','2021-04-07 08:35:50', 'tardes', '123654789','antonio@example.com', 'Madrid',15.00, 15.00, '41212515L', 'pendiente', '75');
INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status, score) values ('Juan Fernández','2021-04-07 08:35:50', 'tardes', '412587459','jf@example.com', 'Madrid',15.00, 15.00, '12546587F', 'pendiente', '55');

INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status, score) values ('Juan Manuel','2021-04-07 08:35:50', 'mañanas', '232565987','juanma@example.com', 'Sevilla',10.00, 15.00, '12548557L', 'validado', '100');
INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status, score) values ('Will Smith','2021-04-07 08:35:50', 'tardes', '652326585','will@example.com', 'Madrid',15.00, 15.00, '41252536K', 'validado', '55');
INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status, score) values ('Jennifer López','2021-04-07 08:35:50', 'tardes', '412568574','jlopez@example.com', 'Los Ángles',15.00, 15.00, '65323265P', 'validado', '25');

INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status, score) values ('María García','2021-04-07 08:35:50', 'mañanas', '652325214','lamari@example.com', 'Málaga',10.00, 15.00, '74582531H', 'pendiente', '100');
INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status) values ('Raúl García','2021-04-07 08:35:50', 'tardes', '421565895','raulito@example.com', 'Granada',15.00, 15.00, '52326598G', 'validado');
INSERT INTO experts (name, created_at,availability,contact_phone, contact_email, contact_city, conditions_percent,conditions_price_hour, nif, status, score) values ('Fernando Alonso','2021-04-07 08:35:50', 'tardes', '523659874','falonso@example.com', 'Oviedo',15.00, 15.00, '74586952S', 'pendiente', '75');

INSERT INTO experts_tags (id_expert, id_tag) values (1,2);
INSERT INTO experts_tags (id_expert, id_tag) values (2,2);
INSERT INTO experts_tags (id_expert, id_tag) values (2,3);
INSERT INTO experts_tags (id_expert, id_tag) values (2,4);

INSERT INTO experts_tags (id_expert, id_tag) values (4,1);
INSERT INTO experts_tags (id_expert, id_tag) values (4,3);
INSERT INTO experts_tags (id_expert, id_tag) values (5,5);
INSERT INTO experts_tags (id_expert, id_tag) values (5,1);
INSERT INTO experts_tags (id_expert, id_tag) values (6,3);
INSERT INTO experts_tags (id_expert, id_tag) values (6,5);

INSERT INTO experts_tags (id_expert, id_tag) values (7,1);
INSERT INTO experts_tags (id_expert, id_tag) values (7,2);
INSERT INTO experts_tags (id_expert, id_tag) values (8,5);
INSERT INTO experts_tags (id_expert, id_tag) values (8,1);
INSERT INTO experts_tags (id_expert, id_tag) values (9,3);
INSERT INTO experts_tags (id_expert, id_tag) values (9,5);

INSERT INTO users (username, email, password) values ( 'Usuario Prueba', 'usuarioprueba@example.com', '$2a$10$oAnwNHxXfUryv.DWhxFkiOTURvHVxB8cbARZaEniLripNYBRaP482');








