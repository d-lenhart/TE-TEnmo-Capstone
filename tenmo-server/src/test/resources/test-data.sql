BEGIN TRANSACTION;

INSERT INTO tenmo_user (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO tenmo_user (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO tenmo_user (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');

COMMIT TRANSACTION;
