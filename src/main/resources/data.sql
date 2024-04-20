insert into papel (id, nome)
values (1, 'ADM'),
       (2, 'PEDAGOGICO'),
       (3, 'RECRUITER'),
       (4, 'PROFESSOR'),
       (5, 'ALUNO')
on conflict (id) do nothing;

insert into usuario (login, senha, id_papel)
values ('admin', '$2a$08$2dFreuQvrRC.uM4yv/Y46OK13VpWUBUzODfruvFhNfvjB0pWZGhSW', 1)
on conflict (login) do nothing;

