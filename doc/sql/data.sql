INSERT INTO `code-harbor`.user (id, username, nickname, password, mobile, id_card_name, id_card_no, gender,
                                date_of_birth, address, status, sort, deleted, last_login, created_by, created_at,
                                modified_by, modified_at)
VALUES (100001, 'admin', '小张', '$2a$08$L9kTRiEVBZqGQ3zmOQhfVeuhC3XGaFrRGJsy/5uJle6MxsxNIluVK', '18110036635', null,
        null, 'M', '2024-06-13 19:59:14', null, 1, 0, 0, null, 100001, '2024-06-13 11:59:48', 100001,
        '2024-06-14 15:30:27');
INSERT INTO `code-harbor`.application (app_id, app_type, authorization_grant_types, scopes, state, issued_at,
                                       app_secret, app_secret_expires_at, app_name, redirect_uri,
                                       post_logout_redirect_uri, token_settings, remark)
VALUES (10001, 0, 'password,authorization_code,refresh_token', 'read', 0, '2024-06-12 18:28:12', '123',
        '2025-12-12 18:29:12', '内部系统', 'http://localhost:8080', 'http://localhost:8080',
        '{"@class":"java.util.HashMap","settings.token.reuse-refresh-tokens":true,"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.refresh-token-time-to-live":["java.time.Duration",15552000.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}',
        '内部系统');
