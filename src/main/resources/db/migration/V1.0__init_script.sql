create table if not exists event
(
    event_id           int primary key auto_increment,
    theme              text not null unique,
    location           text not null,

    #audit
    last_modified_by   varchar(128) default 'system',
    last_modified_date datetime     default current_timestamp(),
    record_status      int          default 1,
    created_date       datetime     default current_timestamp()
);

create table if not exists guest
(
    guest_id           int primary key auto_increment,
    name               varchar(64)  not null,
    email              text         not null unique,
    phone              varchar(128) null,

    #audit
    last_modified_by   varchar(128) default 'system',
    last_modified_date datetime     default current_timestamp(),
    record_status      int          default 1,
    created_date       datetime     default current_timestamp()
);

create table if not exists event_schedule
(
    event_schedule_id  int primary key auto_increment,
    event_fk           int        not null,
    date               datetime   not null,
    completed          tinyint(1) not null,
    cancelled          tinyint(1) not null,

    #audit
    last_modified_by   varchar(128) default 'system',
    last_modified_date datetime     default current_timestamp(),
    record_status      int          default 1,
    created_date       datetime     default current_timestamp(),

    foreign key (event_fk) references event (event_id)
);

create table if not exists event_attendance
(
    event_attendance_id int primary key auto_increment,
    event_schedule_fk   int        not null,
    guest_fk            int        not null,
    confirmed           tinyint(1) not null,
    showed_up           tinyint(1) null,

    #audit
    last_modified_by    varchar(128) default 'system',
    last_modified_date  datetime     default current_timestamp(),
    record_status       int          default 1,
    created_date        datetime     default current_timestamp(),

    foreign key (event_schedule_fk) references event_schedule (event_schedule_id),
    foreign key (guest_fk) references guest (guest_id)
);