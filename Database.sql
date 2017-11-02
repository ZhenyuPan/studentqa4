use CourseQA2;
Create table Userlist
(
	UID	  int primary key,
	UName varchar(50) not null,
	UKey  varchar(50) not null,
	Role  varchar(50)
);
Create Table Task
(
    TaskID  int Auto_increment primary key,
    TaskDescription varchar(200) not null,
    TaskTile		varchar(200) not null,
    DueYear			int,
    DueMonth		int,
    DueDay			int
);
create Table StudentSubmit
(
	SID int,
    TaskID int,
	SubmitYear	int,
    SubmitMonth int,
    SubmitDay	int 
);
Create Table QuestionAnswerPair
(
	QAPairID 	int auto_increment primary key,
    Question	varchar(500) not null,
    Answer	    text not null,
    StudentID	int,
    TaskID		int,
    Commences   text
);
insert into Task(TaskDescription,Tasktile,DueYear,DueMonth,DueDay) value ("you need to submit at least 20 question","Chapter one",2017,6,1);
insert into Task(TaskDescription,Tasktile,DueYear,DueMonth,DueDay) value ("you need to submit at least 20 question","Chapter two",2017,6,8);
insert into Task(TaskDescription,Tasktile,DueYear,DueMonth,DueDay) value ("you need to submit at least 20 question","Chapter three",2018,7,8);
