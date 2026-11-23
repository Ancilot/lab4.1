INSERT INTO users (username, password, role)
SELECT 'user', '$2a$10$fOkoDM.r2d0Dimc2zX3F9eDISJF.NejKMFXoGu8RXtTmhSV0UN8si', 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user');

INSERT INTO users (username, password, role)
SELECT 'admin', '$2a$10$U4lpuBqO80kQv.4YoKdI6.NPpjkAhDK51rFoHDWwRI4hjs.9TphZi', 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');