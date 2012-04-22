CREATE VIEW `hrsys`.`staff_detail` AS
select 
    t1.id as id,
    t1.name as name,
    t1.login as login,
    t1.psw as psw,
    t1.domainl1 as domainl1,
    (select name from domainl1 where id=t1.domainl1) as domainl1_name,
    t1.domainl2 as domainl2,
    (select name from domainl2 where id=t1.domainl2) as domainl2_name,
    t1.domainl3 as domainl3,
    (select name from domainl3 where id=t1.domainl3) as domainl3_name,
    t1.email as email,
    t1.role as role,
    (select name from role where id=t1.role) as role_name,
    t1.level as level,
    (select name from level where id=t1.level) as level_name,
    t1.salary as salary,
    t1.location as location,
    t1.phone as phone,
    t1.gender as gender,
    t1.age as age,
    t1.checkin as checkin
from staff t1