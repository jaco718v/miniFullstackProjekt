DROP DATABASE IF EXISTS projekt_ønskeseddel;
CREATE DATABASE projekt_ønskeseddel;
use projekt_ønskeseddel;
create table brugere (
                         bruger_id int auto_increment NOT NULL,
                         bruger_navn varchar(255),
                         bruger_password varchar(255),
                         PRIMARY KEY(bruger_id)
);

create table ønskesedler (
                             seddel_id int auto_increment NOT NULL,
                             seddel_navn varchar(255),
                             bruger_id int,
                             PRIMARY KEY(seddel_id),
                             FOREIGN KEY (bruger_id) REFERENCES brugere(bruger_id)
);
create table ønsker (
                       ønske_id int auto_increment NOT NULL,
                       seddel_id int,
                       ønske_navn varchar(255),
                       ønske_pris varchar(255),
                       PRIMARY KEY (ønske_id),
                       FOREIGN KEY (seddel_id) REFERENCES ønskesedler(seddel_id)
);