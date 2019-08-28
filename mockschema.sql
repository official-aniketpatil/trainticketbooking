create database mocktrainbooking;
use mocktrainbooking;
create table passenger(id bigint auto_increment primary key, name varchar(40), gender varchar(20), mobile varchar(20));
create table train(id bigint,source_id varchar(40), destination_id varchar(20),distance bigint);
create table availability(train_id bigint references trains(id),date date, ac_seats int, sleeper_seats int);
create table bookings(id bigint auto_increment primary key, passenger_id bigint references passengers(id),train_id bigint references trains(id),source_id bigint,destination_id bigint, fare double,date date,travel_class varchar(20));
create table stations(id bigint, name varchar(40));
create table routes(train_id bigint, station_id bigint,distance bigint); 
insert into stations values(1,"hyderabad");
insert into stations values(5,"delhi");
insert into stations values(3,"pune");
insert into stations values(4,"bhopal");
insert into stations values(6,"chennai");

insert into trains values(1,1,5,4);
insert into trains values(2,6,5,7);
  
insert into availability values(1,"2019-08-12",10,10);
insert into availability values(2,"2019-08-12",10,10);


insert into routes values(1,1,0);
insert into routes values(1,3,1);
insert into routes values(1,4,3);
insert into routes values(1,5,4);

insert into routes values(2,6,0);
insert into routes values(2,1,3);
insert into routes values(2,3,4);
insert into routes values(2,4,6);
insert into routes values(2,5,7);

-- query to get all available trains from route train_id 
-- select train_id from routes where station_id = 6 and train_id in (select train_id from routes where station_id = 4);
-- select * from trains where id in (select train_id from availability where date = "12-08-2019" and ac_seats> 0 and sleeper_seats>0);
-- select * from availability;