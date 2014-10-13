INSERT INTO RGMS_DB.Groups (GroupName) VALUES ('Aggregates');
INSERT INTO RGMS_DB.Groups (GroupName) VALUES ('PcMasterRace');
INSERT INTO RGMS_DB.Groups (GroupName) VALUES ('ConsoleWars');

INSERT INTO RGMS_DB.Users (FirstName, LastName, Username, Passphrase, ImageReference, IsAdmin) VALUES('Tyler', 'Haigh', 'c3182929', 'tyler', '../Uploads/images/c3182929-image.jpg', true);
INSERT INTO RGMS_DB.Users (FirstName, LastName, Username, Passphrase, ImageReference, IsAdmin) VALUES('Simon', 'Hartcher', 'c3182930', 'simon', '../Uploads/images/c3182930-image.jpg', true);
INSERT INTO RGMS_DB.Users (FirstName, LastName, Username, Passphrase, ImageReference, IsAdmin) VALUES('Joshua', 'Crompton', 'c3182940', 'josh', '../Uploads/images/c3182940-image.jpg', true);
INSERT INTO RGMS_DB.Users (FirstName, LastName, Username, Passphrase, ImageReference, IsAdmin) VALUES('Beau', 'Gibson', 'c3146845', 'beau', '../Uploads/images/c3146845-image.jpg', false);
INSERT INTO RGMS_DB.Users (FirstName, LastName, Username, Passphrase, ImageReference, IsAdmin) VALUES('Robert', 'Logan', 'c3182960', 'rob', '../Uploads/images/c3182960-image.jpg', false);

INSERT INTO RGMS_DB.GroupUserMaps (GroupId, UserId) VALUES (1,1);
INSERT INTO RGMS_DB.GroupUserMaps (GroupId, UserId) VALUES (1,2);
INSERT INTO RGMS_DB.GroupUserMaps (GroupId, UserId) VALUES (1,3);
INSERT INTO RGMS_DB.GroupUserMaps (GroupId, UserId) VALUES (3,4);
INSERT INTO RGMS_DB.GroupUserMaps (GroupId, UserId) VALUES (2,5);