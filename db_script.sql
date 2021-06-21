create table users (
	userid serial,
	username varchar(50) unique not null,
	"password" text not null,
	email varchar(255) unique not null,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	"age" int check(age > 0) not null,
	constraint pk_users primary key (userid)
);

create table account (
	accountid serial primary key,
	account_name varchar(20) not null,
	balance decimal(10,2)
);

create table users_account (
	accountid int not null,
	userid int not null,
	
	constraint pk_users_account
	primary key(accountid, userid),
	
	constraint fk_user
	foreign key(userid)
	references users(userid)
	on delete cascade,
	
	constraint fk_account
	foreign key(accountid)
	references account(accountid)
	on delete cascade
);

create table transactions (
	transactionid serial,
	sender_name varchar(50) not null,
	sender_account int not null,
	recipient_name varchar(50) not null,
	recipient_account int not null,
	transaction_type varchar(10),
	amount decimal(10,2) not null,
	constraint pk_transaction primary key(transactionid),
	
	constraint fk_account2
	foreign key(recipient_account)
	references account(accountid)
	on delete cascade,
	
	constraint fk_account --scuffed, I know
	foreign key(sender_account)
	references account(accountid)
	on delete cascade

);
