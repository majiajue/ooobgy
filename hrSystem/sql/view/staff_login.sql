CREATE VIEW `hrsys`.`staff_login` AS
select 
    t1.id as id,
    t1.name as name,
    t1.login as login,
    t1.psw as psw
from staff t1