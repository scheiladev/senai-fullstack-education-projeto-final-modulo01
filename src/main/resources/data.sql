insert into papel (id, nome)
values (1, 'ADM'),
       (2, 'PEDAGOGICO'),
       (3, 'RECRUITER'),
       (4, 'PROFESSOR'),
       (5, 'ALUNO');

insert into usuario (id, login, senha, id_papel)
values (1, 'admin', '$2a$08$2dFreuQvrRC.uM4yv/Y46OK13VpWUBUzODfruvFhNfvjB0pWZGhSW', 1);