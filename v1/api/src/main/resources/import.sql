INSERT INTO rooms(id, created_at, updated_at, admin_id, room_number) VALUES ('418a3424-a827-471b-b189-f0f91c3a65ed', NOW(), NOW(), '', '123');
-- INSERT INTO students(id, created_at, updated_at, device_id, full_name) VALUES ('67497408-0a90-4881-aca2-3f190f852e9b', NOW(), NOW(), '3194635b457ad1f4', 'Zvone');

INSERT INTO themes(id, created_at, updated_at, position, title, latitude, longitude, area) VALUES ('28fa3415-f898-4a27-93fc-b410ae5242f3', NOW(), NOW(), 1, 'Regio Carsica Militaris', 46.05020349076014, 14.487366891776727, 2.0);
INSERT INTO themes(id, created_at, updated_at, position, title, latitude, longitude, area) VALUES ('2f304a76-e062-4ad7-a26d-e0550b4716b2', NOW(), NOW(), 3, 'JNA vozila', 46.05039988170667, 14.487465462549464, 2.0);
INSERT INTO themes(id, created_at, updated_at, position, title, latitude, longitude, area) VALUES ('6e3c260a-4918-4ea9-97c1-894146bcf48c', NOW(), NOW(), 2, 'Partizanske tankovske enote', 0.0, 0.0, 2.0);
INSERT INTO themes(id, created_at, updated_at, position, title, latitude, longitude, area) VALUES ('14c4f591-74e8-44c3-9de4-ec706f5912be', NOW(), NOW(), 4, 'Oklep svobode', 0.0, 0.0, 2.0);
INSERT INTO themes(id, created_at, updated_at, position, title, latitude, longitude, area) VALUES ('f56a2afa-e53e-40cb-9d18-fdc31eaab8d4', NOW(), NOW(), 5, 'Plato', 0.0, 0.0, 2.0);

-- INSERT INTO discovered_themes(id, created_at, updated_at, student_id, theme_id) VALUES ('8792de9d-a7c6-43dc-951a-14346abfa2cb', NOW(), NOW(), '67497408-0a90-4881-aca2-3f190f852e9b', '28fa3415-f898-4a27-93fc-b410ae5242f3');

INSERT INTO theme_questions(id, created_at, updated_at, content, theme_id) VALUES ('0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8', NOW(), NOW(), 'Regija povezuje katera dva dela Evrope?', '28fa3415-f898-4a27-93fc-b410ae5242f3');
INSERT INTO theme_questions(id, created_at, updated_at, content, theme_id) VALUES ('38a691ec-f123-4682-a96f-b5003930d31d', NOW(), NOW(), 'Vprašanje 2', '28fa3415-f898-4a27-93fc-b410ae5242f3');
INSERT INTO theme_questions(id, created_at, updated_at, content, theme_id) VALUES ('62ece9d8-2722-4d24-8e9b-df7fa7e22812', NOW(), NOW(), 'Vprašanje 3', '28fa3415-f898-4a27-93fc-b410ae5242f3');

INSERT INTO theme_questions(id, created_at, updated_at, content, theme_id) VALUES ('ee98bc10-564c-4490-ac01-916e72d70495', NOW(), NOW(), 'Odgovor 11', '2f304a76-e062-4ad7-a26d-e0550b4716b2');
INSERT INTO theme_questions(id, created_at, updated_at, content, theme_id) VALUES ('1bf912c4-10e4-408f-9a88-0201c544bf80', NOW(), NOW(), 'Katero vozilo je Sovjetske izdelave?', '2f304a76-e062-4ad7-a26d-e0550b4716b2');

INSERT INTO theme_questions(id, created_at, updated_at, content, theme_id) VALUES ('18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1', NOW(), NOW(), 'Kdo je uril partizanske tankovske enote?', '6e3c260a-4918-4ea9-97c1-894146bcf48c');

INSERT INTO theme_questions(id, created_at, updated_at, content, theme_id) VALUES ('665ab09b-3d53-47d3-8738-bd6d9a93f32d', NOW(), NOW(), 'Kako se imenuje ameriški M4A3 tank?', '14c4f591-74e8-44c3-9de4-ec706f5912be');

INSERT INTO theme_questions(id, created_at, updated_at, content, theme_id) VALUES ('774e3567-e184-46fb-9510-54f265e048d0', NOW(), NOW(), 'Kako se reče tipu podmornice Zeta?', 'f56a2afa-e53e-40cb-9d18-fdc31eaab8d4');


INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('59220e26-9fac-4f0e-a0d1-1a835fe9e5d0', NOW(), NOW(), true, 'Srednjo in južno', '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('9e4ede3a-d2de-47f3-a863-00f2daceeb33', NOW(), NOW(), false, 'Severno in zahodno', '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('37454659-6886-4627-99fe-9aad8dc94d64', NOW(), NOW(), false, 'Južno in severno', '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('945adf68-8bdd-4200-b1dc-96ce95872f98', NOW(), NOW(), false, 'Vzhodno in srednjo', '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8');

INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('07fc2181-081b-4ebe-89a4-7841309a11db', NOW(), NOW(), true, 'Odgovor 1', '38a691ec-f123-4682-a96f-b5003930d31d');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('420c30bd-beb3-44a0-b97d-f35c80cee0b1', NOW(), NOW(), false, 'Odgovor 2', '38a691ec-f123-4682-a96f-b5003930d31d');

INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('1d5056aa-8512-4fa1-a8b3-8daf85dc34cb', NOW(), NOW(), true, 'Odgovor 1', '62ece9d8-2722-4d24-8e9b-df7fa7e22812');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('82ca264e-b5a5-410e-bd77-c40c53402f65', NOW(), NOW(), false, 'Odgovor 2', '62ece9d8-2722-4d24-8e9b-df7fa7e22812');

INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('1b97c21e-1b57-4b59-9a98-45f70615931c', NOW(), NOW(), false, 'Tank M-84', '1bf912c4-10e4-408f-9a88-0201c544bf80');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('1efe343b-8147-4db3-a01b-f526449d5b5d', NOW(), NOW(), false, 'Letalo Thunderjet', '1bf912c4-10e4-408f-9a88-0201c544bf80');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('b1aabdcc-749c-41c0-9335-1a6413335930', NOW(), NOW(), true, 'Tank T-72', '1bf912c4-10e4-408f-9a88-0201c544bf80');

INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('b6c2a6cf-b99c-47e7-a7a3-c06df2616e36', NOW(), NOW(), true, 'Odgovor 1', 'ee98bc10-564c-4490-ac01-916e72d70495');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('594ad790-3dbe-4abd-905b-af954d0d8f27', NOW(), NOW(), false, 'Odgovor 2', 'ee98bc10-564c-4490-ac01-916e72d70495');

INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('ce70d469-5e58-4ba8-993e-0121f88eda37', NOW(), NOW(), false, 'Italijani in francozi', '18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('1b6eee44-44fc-4fc7-852e-25d4acf295b0', NOW(), NOW(), false, 'Nemci in američani', '18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('f6f08013-74a7-4ab2-a984-f4ddbbb94b37', NOW(), NOW(), true, 'Američani in sovjeti', '18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('fdb02911-d5fd-4d23-ba31-bd2b14346249', NOW(), NOW(), false, 'Nemci in italijani', '18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1');

INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('0b98d7ea-f011-488a-adb8-870ef1a2550b', NOW(), NOW(), true, 'Sherman', '665ab09b-3d53-47d3-8738-bd6d9a93f32d');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('59b9c2fb-8e47-4c2b-a4a5-2bd48ba15cbf', NOW(), NOW(), false, 'George', '665ab09b-3d53-47d3-8738-bd6d9a93f32d');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('7f7680ab-74d6-41e5-b8ac-aa5818a63e01', NOW(), NOW(), false, 'Patton', '665ab09b-3d53-47d3-8738-bd6d9a93f32d');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('e9ceceee-f43e-4ca6-9c13-b576d9afd0e9', NOW(), NOW(), false, 'Matilda', '665ab09b-3d53-47d3-8738-bd6d9a93f32d');

INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('cfb0af32-7e13-46f9-8f83-178529128aec', NOW(), NOW(), false, 'Nahrbtna', '774e3567-e184-46fb-9510-54f265e048d0');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('61af00d2-21cf-46c0-a61b-049d47686703', NOW(), NOW(), true, 'Žepna', '774e3567-e184-46fb-9510-54f265e048d0');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('ef895288-d571-487a-bdaa-9ba5d37cbab0', NOW(), NOW(), true, 'Podvodna', '774e3567-e184-46fb-9510-54f265e048d0');
INSERT INTO question_answers(id, created_at, updated_at, correct, content, question_id) VALUES ('3e968a1d-3270-4866-ae05-b17e6db3b616', NOW(), NOW(), true, 'Sladkovodna', '774e3567-e184-46fb-9510-54f265e048d0');




INSERT INTO quiz_instances(id, created_at, updated_at, active, passkey, question_index, room_id, state) VALUES ('718fc702-7c50-45b1-be8e-cb1ef6e06c48', NOW(), NOW(), true, '123', 0, '418a3424-a827-471b-b189-f0f91c3a65ed', 'ACTIVE');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('502e2bda-9eea-4e12-a427-b2360dc6b00d', NOW(), NOW(), 1, '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('d8bb9197-8cda-4b98-9731-8c2a0b968092', NOW(), NOW(), 2, '1bf912c4-10e4-408f-9a88-0201c544bf80', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('4ef65cba-3116-449e-9106-34b3829f4abd', NOW(), NOW(), 3, '18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('7cc475a3-4358-4a15-8389-00c3ff523c78', NOW(), NOW(), 4, '665ab09b-3d53-47d3-8738-bd6d9a93f32d', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('1dfa061d-7458-44cb-a197-f8f23eaf7d60', NOW(), NOW(), 5, '774e3567-e184-46fb-9510-54f265e048d0', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');