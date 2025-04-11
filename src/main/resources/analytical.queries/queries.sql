# MOST RELIABLE GUEST(GET TOP 5)
# if the first one is needed just do this: select top 1 g.* and don't use LIMIT
select g.*, count(event_attendance_id) AS attendance_count
from event_attendance ev
join guest g ON g.guest_id = ev.guest_fk
where confirmed = 1 and showed_up = 1
group by ev.guest_fk
order by attendance_count desc
limit 5;

# FREQUENT NO-SHOW OFFENDERS
# guests who have confirmed at least 3 times, but failed to attend at least 2 of them
select g.*
from event_attendance ev
join guest g ON g.guest_id = ev.guest_fk
where confirmed = 1
group by ev.guest_fk
having count(ev.event_attendance_id) >= 3
   and sum(case when ev.showed_up = 0 then 1 else 0 end) >= 2;

# UPCOMING EVENTS WITH LOW ATTENDANCE
# fewer than 5 guests have confirmed upcoming event
select *, count(ea.event_attendance_id) as attendance
from event_schedule es
join event_attendance ea on es.event_schedule_id = ea.event_schedule_fk
where ea.confirmed = 1 and es.date > now()
having attendance > 5;

# THEME LOYALTY
# guests who attended every event with a given theme (e.g. "Black Tie Gala")
select g.*
from event_schedule es
join event e on e.event_id = es.event_fk
join event_attendance ea on es.event_schedule_id = ea.event_schedule_fk
join guest g on ea.guest_fk = g.guest_id
where e.theme like 'Svaki'
group by guest_fk
having count(*) = sum(case when showed_up = true then 1 else 0 end)


