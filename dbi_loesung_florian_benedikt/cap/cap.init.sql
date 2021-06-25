-- Init CAP-ExampleDatabase

CREATE SCHEMA dbi AUTHORIZATION u;

create table dbi.customers
(	cid 	serial,
	cname	varchar(20),
	city	varchar(20),
	discnt	dec(4,2),
	primary key (cid) );

create table dbi.agents
(	aid		serial,
	aname	varchar(40),
	city	varchar(20),
	percent	int,
	primary key (aid) );

create table dbi.products
(	pid			serial,
	pname		varchar(20),
	city		varchar(20),
	quantity 	int,
	price		dec(10,2),
	primary key (pid));

create table dbi.orders
(	ordno 	serial,
	month	char(3),
	cid		int,
	aid		int,
	pid		int,
	qty		int,
	dollars	dec(10,2),
	primary key (ordno),
	foreign key (cid) references dbi.customers,
	foreign key (aid) references dbi.agents,
	foreign key (pid) references dbi.products );

-- Grant rights
grant select on dbi.customers to public;
grant select on dbi.agents    to public;
grant select on dbi.products  to public;
grant select on dbi.orders    to public;

-- End transaction;

commit;
