drop database if exists ems;

create database ems;
use ems;


create table department
(
	dept_id int auto_increment,
    dept_name varchar(30) unique,
    dept_phone char(10),
    primary key (dept_id)
);
alter table department auto_increment = 1;

insert into department values(null, 'IT', '1234567890');
insert into department values(null, 'Sales', '1234567891');
insert into department values(null, 'Accounting', '1234567892');
insert into department values(null, 'Management', '1234567893');
insert into department values(null, 'Marketing', '1234567894');
insert into department values(null, 'Finance', '1234567895');
insert into department values(null, 'HR', '1234567896');
insert into department values(null, 'Production', '1234567897');
insert into department values(null, 'Graphic Design', '1234567898');
insert into department values(null, 'Operations', '1234567899');
insert into department values(null, 'Purchasing', '1234567810');
insert into department values(null, 'Budget', '1234567811');
insert into department values(null, 'R&D', '1234567812');
insert into department values(null, 'HR Management', '1234567813');
insert into department values(null, 'Investment', '1234567814');
insert into department values(null, 'Research', '1234567815');
insert into department values(null, 'Feedback', '1234567816');
insert into department values(null, 'Statistics', '1234567817');
insert into department values(null, 'Distribution', '1234567818');

create table address 
(
	address_id int auto_increment,
    street varchar(100) not null,
    city varchar(100) not null,
    state char(2) not null,
    zip_code varchar(10) not null,
    primary key (address_id)
);
alter table address auto_increment = 1;

insert into address values (null, '1711 Valley Drive', 'Philadelphia', 'PA', '19103');
insert into address values (null, '4595 Andell Road', 'Nashville', 'TN', '37201');
insert into address values (null, '1750 Sunny Glen Lane', 'Warrensville Heights', 'OH', '44128');
insert into address values (null, '3630 Layman Court', 'Atlanta', 'GA', '30303');
insert into address values (null, '1016 Adamsville Road', 'Laredo', 'TX', '78040');
insert into address values (null, '3857 Dungalow Road', 'Niobrara', 'NE', '68760');
insert into address values (null, '2982 Happy Hollow Road', 'Wilmington', 'NC', '28405');
insert into address values (null, '1223 Hall Place', 'Rosewood', 'TX', '75644');
insert into address values (null, '62 Maple Court', 'Cape Girardeau', 'MO', '63701');

create table employee
(
	employee_id int auto_increment,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    gender char(1) not null,
    date_of_birth date not null,
    salary int not null,
    address_id int,
    dept_id int,
    company_id int,
    primary key (employee_id)
);
alter table employee auto_increment = 1;

insert into employee values(null, 'Chris', 'Lee', 'M', '1992-05-11', 80, 1, 1, 1);
insert into employee values(null, 'Anna', 'Kim', 'F', '1996-09-19', 31, 2, 1, 1);
insert into employee values(null, 'Kimberly', 'Clark', 'F', '1998-12-07', 42, 3, 1, 1);
insert into employee values(null, 'Glen', 'Felts', 'M', '1998-10-18', 17, 4, 1, 1);
insert into employee values(null, 'Joyce', 'Valdez', 'F', '1997-12-30', 0, 5, 2, 1);
insert into employee values(null, 'Jarrell', 'Stokes', 'M', '1998-03-08', 79, 6, 3, 2);
insert into employee values(null, 'Librada', 'Lyles', 'F', '1999-07-04', 23, 7, 4, 2);
insert into employee values(null, 'Zach', 'Wiggins', 'M', '2000-04-23', 4, 8, 4, 2);
insert into employee values(null, 'Frank', 'Rader', 'M', '1997-01-09', 90, 9, 5, 2);

#SELECT * FROM employee;
create table company
(
	company_id int auto_increment,
    name varchar(30) not null unique,
    primary key (company_id)
    
);
alter table company auto_increment = 0;
insert into company values(null, 'Microsoft');
insert into company values(null, 'Google');

create table company_department
(
	dept_id int not null,
    company_id int not null,
    primary key(dept_id, company_id),
    KEY idx_fk_company_id(`company_id`),
	CONSTRAINT fk_company_department_department FOREIGN KEY (dept_id) REFERENCES department (dept_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT fk_company_department_company FOREIGN KEY (company_id) REFERENCES company (company_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

insert into company_department values (1,1), (2,1), (1,2);

/* test a joint database, don't need it due to many to one relationship
create table company_employee
(
	employee_id int not null,
    company_id int not null,
    primary key(employee_id, company_id),
    KEY idx_fk_company_id(`company_id`),
	CONSTRAINT fk_company_employee_employee FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT fk_company_employee_company FOREIGN KEY (company_id) REFERENCES company (company_id) ON DELETE RESTRICT ON UPDATE CASCADE
);
*/

/* test a joint database, don't need it due to many to one relationship
create table department_employee
(
	employee_id int not null,
    dept_id int not null,
    primary key(employee_id, dept_id),
    KEY idx_fk_dept_id(`dept_id`),
	CONSTRAINT fk_dept_employee_employee FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT fk_dept_employee_department FOREIGN KEY (dept_id) REFERENCES department (dept_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

insert into department_employee values (1,1),(2,1),(3,1),(4,1),(5,2),(6,3),(7,4),(8,4),(9,5);
*/

/* update an employee with a new department
update department_employee set dept_id = 19 where employee_id = 1;
update employee set dept_id = 19 where employee_id = 1;
*/
/* delete a company
delete from company_department where company_id = 1;
delete from company where company_id = 1;
select * from company;
*/

/* delete an employee
delete from department_employee where employee_id = 1;
delete from employee where employee_id = 1;
*/

/* delete a department
delete from company_department where dept_id = 1;
delete from department_employee where dept_id = 1;
delete from department where dept_id = 1;
*/
/* find all employees in a department
select * from employee 
where employee_id in (
	select employee_id from department_employee 
    where dept_id = 1
);
*/
/* find all departments in a company
select * from department 
where dept_id in (
	select dept_id from company_department 
    where company_id = 1
);
*/
