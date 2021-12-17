INSERT INTO themes(id, created_at, updated_at, position, title) VALUES ('28fa3415-f898-4a27-93fc-b410ae5242f3', NOW(), NOW(), 1, 'Tematika 1');
INSERT INTO themes(id, created_at, updated_at, position, title) VALUES ('2f304a76-e062-4ad7-a26d-e0550b4716b2', NOW(), NOW(), 3, 'Tematika 2');
INSERT INTO themes(id, created_at, updated_at, position, title) VALUES ('6e3c260a-4918-4ea9-97c1-894146bcf48c', NOW(), NOW(), 2, 'Tematika 3');
INSERT INTO themes(id, created_at, updated_at, position, title) VALUES ('14c4f591-74e8-44c3-9de4-ec706f5912be', NOW(), NOW(), 4, 'Tematika 4');
INSERT INTO themes(id, created_at, updated_at, position, title) VALUES ('f56a2afa-e53e-40cb-9d18-fdc31eaab8d4', NOW(), NOW(), 5, 'Tematika 5');

INSERT INTO theme_questions(id, created_at, updated_at, html_content, text_content, theme_id) VALUES ('0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8', NOW(), NOW(), 'Vprašanje <strong>1</strong>', 'Vprašanje 1', '28fa3415-f898-4a27-93fc-b410ae5242f3');
INSERT INTO theme_questions(id, created_at, updated_at, html_content, text_content, theme_id) VALUES ('38a691ec-f123-4682-a96f-b5003930d31d', NOW(), NOW(), 'Vprašanje 2', 'Vprašanje 2', '28fa3415-f898-4a27-93fc-b410ae5242f3');
INSERT INTO theme_questions(id, created_at, updated_at, html_content, text_content, theme_id) VALUES ('62ece9d8-2722-4d24-8e9b-df7fa7e22812', NOW(), NOW(), 'Vprašanje 3', 'Vprašanje 3', '28fa3415-f898-4a27-93fc-b410ae5242f3');

INSERT INTO theme_questions(id, created_at, updated_at, html_content, text_content, theme_id) VALUES ('ee98bc10-564c-4490-ac01-916e72d70495', NOW(), NOW(), 'Vprašanje 11', 'Vprašanje 11', '2f304a76-e062-4ad7-a26d-e0550b4716b2');
INSERT INTO theme_questions(id, created_at, updated_at, html_content, text_content, theme_id) VALUES ('1bf912c4-10e4-408f-9a88-0201c544bf80', NOW(), NOW(), 'Vprašanje 22', 'Vprašanje 22', '2f304a76-e062-4ad7-a26d-e0550b4716b2');

INSERT INTO theme_questions(id, created_at, updated_at, html_content, text_content, theme_id) VALUES ('18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1', NOW(), NOW(), 'Vprašanje 111', 'Vprašanje 111', '6e3c260a-4918-4ea9-97c1-894146bcf48c');

INSERT INTO theme_questions(id, created_at, updated_at, html_content, text_content, theme_id) VALUES ('665ab09b-3d53-47d3-8738-bd6d9a93f32d', NOW(), NOW(), 'Vprašanje 1111', 'Vprašanje 1111', '14c4f591-74e8-44c3-9de4-ec706f5912be');

INSERT INTO theme_questions(id, created_at, updated_at, html_content, text_content, theme_id) VALUES ('774e3567-e184-46fb-9510-54f265e048d0', NOW(), NOW(), 'Vprašanje 11111', 'Vprašanje 11111', 'f56a2afa-e53e-40cb-9d18-fdc31eaab8d4');


INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('59220e26-9fac-4f0e-a0d1-1a835fe9e5d0', NOW(), NOW(), true, 'Odgovor 1', 'Odgovor 1', '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('9e4ede3a-d2de-47f3-a863-00f2daceeb33', NOW(), NOW(), false, 'Odgovor 2', 'Odgovor 2', '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('37454659-6886-4627-99fe-9aad8dc94d64', NOW(), NOW(), false, 'Odgovor 3', 'Odgovor 3', '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('945adf68-8bdd-4200-b1dc-96ce95872f98', NOW(), NOW(), false, 'Odgovor 4', 'Odgovor 4', '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8');

INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('07fc2181-081b-4ebe-89a4-7841309a11db', NOW(), NOW(), true, 'Odgovor 1', 'Odgovor 1', '38a691ec-f123-4682-a96f-b5003930d31d');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('420c30bd-beb3-44a0-b97d-f35c80cee0b1', NOW(), NOW(), false, 'Odgovor 2', 'Odgovor 2', '38a691ec-f123-4682-a96f-b5003930d31d');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('1d5056aa-8512-4fa1-a8b3-8daf85dc34cb', NOW(), NOW(), true, 'Odgovor 1', 'Odgovor 1', '62ece9d8-2722-4d24-8e9b-df7fa7e22812');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('82ca264e-b5a5-410e-bd77-c40c53402f65', NOW(), NOW(), false, 'Odgovor 2', 'Odgovor 2', '62ece9d8-2722-4d24-8e9b-df7fa7e22812');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('1b97c21e-1b57-4b59-9a98-45f70615931c', NOW(), NOW(), true, 'Odgovor 1', 'Odgovor 1', 'ee98bc10-564c-4490-ac01-916e72d70495');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('1efe343b-8147-4db3-a01b-f526449d5b5d', NOW(), NOW(), false, 'Odgovor 2', 'Odgovor 2', 'ee98bc10-564c-4490-ac01-916e72d70495');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('b6c2a6cf-b99c-47e7-a7a3-c06df2616e36', NOW(), NOW(), true, 'Odgovor 1', 'Odgovor 1', '1bf912c4-10e4-408f-9a88-0201c544bf80');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('594ad790-3dbe-4abd-905b-af954d0d8f27', NOW(), NOW(), false, 'Odgovor 2', 'Odgovor 2', '1bf912c4-10e4-408f-9a88-0201c544bf80');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('ce70d469-5e58-4ba8-993e-0121f88eda37', NOW(), NOW(), true, 'Odgovor 1', 'Odgovor 1', '18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('1b6eee44-44fc-4fc7-852e-25d4acf295b0', NOW(), NOW(), false, 'Odgovor 2', 'Odgovor 2', '18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('0b98d7ea-f011-488a-adb8-870ef1a2550b', NOW(), NOW(), true, 'Odgovor 1', 'Odgovor 1', '665ab09b-3d53-47d3-8738-bd6d9a93f32d');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('59b9c2fb-8e47-4c2b-a4a5-2bd48ba15cbf', NOW(), NOW(), false, 'Odgovor 2', 'Odgovor 2', '665ab09b-3d53-47d3-8738-bd6d9a93f32d');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('cfb0af32-7e13-46f9-8f83-178529128aec', NOW(), NOW(), true, 'Odgovor 1', 'Odgovor 1', '774e3567-e184-46fb-9510-54f265e048d0');
INSERT INTO question_answers(id, created_at, updated_at, correct, html_content, text_content, question_id) VALUES ('61af00d2-21cf-46c0-a61b-049d47686703', NOW(), NOW(), false, 'Odgovor 2', 'Odgovor 2', '774e3567-e184-46fb-9510-54f265e048d0');




INSERT INTO quiz_instances(id, created_at, updated_at, active, passkey, question_index) VALUES ('718fc702-7c50-45b1-be8e-cb1ef6e06c48', NOW(), NOW(), false, '123', 0);
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('502e2bda-9eea-4e12-a427-b2360dc6b00d', NOW(), NOW(), 1, '0b247aa7-e1ef-4015-a2b2-5e9f02bcc2c8', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('d8bb9197-8cda-4b98-9731-8c2a0b968092', NOW(), NOW(), 2, '1bf912c4-10e4-408f-9a88-0201c544bf80', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('4ef65cba-3116-449e-9106-34b3829f4abd', NOW(), NOW(), 3, '18e8d44c-32ab-4bbf-ac2c-d0dd38880dd1', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('7cc475a3-4358-4a15-8389-00c3ff523c78', NOW(), NOW(), 4, '665ab09b-3d53-47d3-8738-bd6d9a93f32d', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');
INSERT INTO quiz_questions(id, created_at, updated_at, position, question_id, quiz_id) VALUES ('1dfa061d-7458-44cb-a197-f8f23eaf7d60', NOW(), NOW(), 5, '774e3567-e184-46fb-9510-54f265e048d0', '718fc702-7c50-45b1-be8e-cb1ef6e06c48');