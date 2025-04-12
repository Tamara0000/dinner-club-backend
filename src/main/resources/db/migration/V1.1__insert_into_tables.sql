#GUEST
insert
    ignore
into guest (guest_id, name, surname, email, phone)
values (1, 'Tamara', 'Misic', 'tamara.misic119@gmail.com','0656280456'),
       (2, 'John', 'Powell', 'tamara.misic.4104@metropolitan.ac.rs', null),
       (3, 'Ana', 'Misic', 'ana.misic.design@gmail.com', null),
       (4, 'Zorica', 'Misic', 'zorica.misicc@gmai.com', null);

#EVENT
insert
    ignore
into event (event_id, theme, location)
values (1, 'Masquerade Ball', 'Nis'),
       (2, 'Futuristic Banquet', 'Belgrade'),
       (3, '80s Retro Night', 'New york'),
       (4, 'Black Tie Gala', 'Bali');

#EVENT_SCHEDULE
insert
    ignore
into event_schedule (event_schedule_id, event_fk, date, completed, cancelled)
values (1,1, '2025-06-11', 0, 0),
       (2,1, '2025-01-01', 1, 0),
       (3,2, '2025-01-11', 1, 0),
       (4,3, '2025-02-02', 0 ,1),
       (5,4, '2025-01-03', 1 ,1),
       (6,4, '2025-01-04', 1 ,1);

#EVENT_ATTENDANCE
insert
    ignore
into event_attendance (event_attendance_id, event_schedule_fk, guest_fk, confirmed, showed_up)
values (1, 2, 1, 1, 1),
       (2, 2, 2, 1, 1),
       (3, 3, 2, 1, 1),
       (4, 3, 3, 1, 1),
       (5, 4, 3, 1, 0),
       (6, 2, 3, 1, 0),
       (7, 1, 1, 0, null),
       (8, 1, 2, 0, null),
       (9, 1, 3, 0, null),
       (10, 5, 1, 1, 1),
       (11, 5, 2, 1, 1),
       (12, 5, 3, 1, 1),
       (13, 6, 2, 1, 1),
       (14, 6, 1, 1, 1);








