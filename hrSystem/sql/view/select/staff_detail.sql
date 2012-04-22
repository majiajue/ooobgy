select
    t9.id as id,
    t9.name as name,
    t9.login as login,
    t9.psw as psw,
    t9.domainl1 as domainl1,
    t9.domainl1_name as domainl1_name,
    t9.domainl2 as domainl2,
    t9.domainl2_name as domainl2_name,
    t9.domainl3 as domainl3,
    t9.domainl3_name as domainl3_name,
    t9.email as email,
    t9.role as role,
    t9.role_name as role_name,
    t9.level as level,
    t10.name as level_name,
    t9.salary as salary,
    t9.location as location,
    t9.phone as phone,
    t9.gender as gender,
    t9.age as age,
    t9.checkin as checkin
from(
        select
            t7.id as id,
            t7.name as name,
            t7.login as login,
            t7.psw as psw,
            t7.domainl1 as domainl1,
            t7.domainl1_name as domainl1_name,
            t7.domainl2 as domainl2,
            t7.domainl2_name as domainl2_name,
            t7.domainl3 as domainl3,
            t7.domainl3_name as domainl3_name,
            t7.email as email,
            t7.role as role,
            t8.name as role_name,
            t7.level as level,
            t7.salary as salary,
            t7.location as location,
            t7.phone as phone,
            t7.gender as gender,
            t7.age as age,
            t7.checkin as checkin
        from(
                select
                    t5.id as id,
                    t5.name as name,
                    t5.login as login,
                    t5.psw as psw,
                    t5.domainl1 as domainl1,
                    t5.domainl1_name as domainl1_name,
                    t5.domainl2 as domainl2,
                    t5.domainl2_name as domainl2_name,
                    t5.domainl3 as domainl3,
                    t6.name as domainl3_name,
                    t5.email as email,
                    t5.role as role,
                    t5.level as level,
                    t5.salary as salary,
                    t5.location as location,
                    t5.phone as phone,
                    t5.gender as gender,
                    t5.age as age,
                    t5.checkin as checkin
                from(
                        select
                            t3.id as id,
                            t3.name as name,
                            t3.login as login,
                            t3.psw as psw,
                            t3.domainl1 as domainl1,
                            t3.domainl1_name as domainl1_name,
                            t3.domainl2 as domainl2,
                            t4.name as domainl2_name,
                            t3.domainl3 as domainl3,
                            t3.email as email,
                            t3.role as role,
                            t3.level as level,
                            t3.salary as salary,
                            t3.location as location,
                            t3.phone as phone,
                            t3.gender as gender,
                            t3.age as age,
                            t3.checkin as checkin
                        from(
                                select 
                                    t1.id as id,
                                    t1.name as name,
                                    t1.login as login,
                                    t1.psw as psw,
                                    t1.domainl1 as domainl1,
                                    t2.name as domainl1_name,
                                    t1.domainl2 as domainl2,
                                    t1.domainl3 as domainl3,
                                    t1.email as email,
                                    t1.role as role,
                                    t1.level as level,
                                    t1.salary as salary,
                                    t1.location as location,
                                    t1.phone as phone,
                                    t1.gender as gender,
                                    t1.age as age,
                                    t1.checkin as checkin
                                from staff t1
                                left outer join domainl1 t2
                                on
                                    t1.domainl1 = t2.id
                        ) t3
                        left outer join domainl2 t4
                        on
                                t3.domainl2 = t4.id
                ) t5  
                left outer join domainl3 t6
                on
                        t5.domainl3 = t6.id
        ) t7
        left outer join role t8
        on
                t7.role = t8.id
) t9
left outer join level t10
on
    t9.level = t10.id   
        