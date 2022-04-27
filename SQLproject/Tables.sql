CREATE DATABASE it_sss;
USE it_sss;

CREATE TABLE THREAT(
	ThreatID INT,
	NameAtr VARCHAR(20),
	DescriptionAtr VARCHAR(50),
	PRIMARY KEY(ThreatID)
);

CREATE TABLE OPERATION_TYPE(
	OperationTypeID INT,
	Name VARCHAR(20),
	PRIMARY KEY(OperationTypeID)
);

CREATE TABLE PRIVILEGE_TYPE(
	PrivilegeTypeID INT PRIMARY KEY,
	NameAtr VARCHAR(20)
);

CREATE TABLE CLASSIFICATION(
        ClassificatoinID INT,
        Name VARCHAR(20),
        PRIMARY KEY (ClassificatoinID)
);

CREATE TABLE EVENT_TYPE(
	EventTypeID INT UNIQUE,
	NameAtr VARCHAR(20),
	PRIMARY KEY(EventTypeID)
);

CREATE TABLE OBJECT_TYPE(
		ObjectTypeID INT,
		Name VARCHAR(1),
		PRIMARY KEY (ObjectTypeID)
	);

CREATE TABLE EMPLOYEE(
	EmployeeID INT PRIMARY KEY,
    LastName VARCHAR(20),
    FirstName VARCHAR(20),
    Email VARCHAR(20),
    Phone VARCHAR(15),
    Position VARCHAR(20)
);

CREATE TABLE SYSTEM_TAB(
	SystemID INT UNIQUE,
	NameAtr VARCHAR(20),
	Hardware TEXT,
	TypeAtr CHAR(1),
    RelSystemID INT,
    EmployeeID INT,
	PRIMARY KEY(SystemID),
    FOREIGN KEY (RelSystemID) REFERENCES SYSTEM_TAB(SystemID),
    FOREIGN KEY (EmployeeID) REFERENCES EMPLOYEE(EmployeeID)
);

CREATE TABLE USER_TAB(
	UserID INT PRIMARY KEY,
    Username VARCHAR(20),
	AccountStatus VARCHAR(20),
    PasswordShort VARCHAR(20),
    DateAdded Date,
    DateDeleted Date,
    EmployeeID INT,
    SystemID INT,
    FOREIGN KEY(EmployeeID) REFERENCES EMPLOYEE(EmployeeID),
    FOREIGN KEY(SystemID) REFERENCES SYSTEM_TAB(SystemID)
);

CREATE TABLE OPERATION (
        operationID INT,
        NameAtr VARCHAR(20),
        SystemID INT,
        OperationTypeID INT,
        PRIMARY KEY (OperationID),
		FOREIGN KEY (SystemID) REFERENCES SYSTEM_TAB(SystemID),
		FOREIGN KEY (OperationTypeID) REFERENCES OPERATION_TYPE(OperationTypeID)
);

CREATE TABLE VULNERABILITY (
        VulnerabilityID INT,
        NameAtr VARCHAR(20),
        DescriptionAtr VARCHAR(50),
        DateIdentified DATE,
        OperationID INT,
        PRIMARY KEY (VulnerabilityID),
		FOREIGN KEY (OperationID) REFERENCES OPERATION(OperationID)
);

CREATE TABLE SUBJECT_TAB(
		SubjectID INT,
		NameAtr VARCHAR(20),
		UserID INT,
		PRIMARY KEY (SubjectID),
		FOREIGN KEY (UserID) REFERENCES USER_TAB(UserID)
	);

CREATE TABLE ROLE_TAB(
	RoleID 	INT UNIQUE,
	NameAtr VARCHAR(20),
	DescriptionAtr TEXT,
    SystemID INT,
	PRIMARY KEY(RoleID),
    FOREIGN KEY (SystemID) REFERENCES SYSTEM_TAB(SystemID)
);

CREATE TABLE OBJECT(
	ObjectID INT UNIQUE,
    ObjectTypeID INT,
    ClassificatoinID INT,
	PRIMARY KEY(ObjectID),
    FOREIGN KEY (ObjectTypeID) REFERENCES OBJECT_TYPE(ObjectTypeID),
    FOREIGN KEY (ClassificatoinID) REFERENCES CLASSIFICATION (ClassificatoinID)
);

CREATE TABLE BACKUP_TAB(
	BackUpID INT PRIMARY KEY,
    BackupType INT,
    DateAtr DATE,
    Location VARCHAR(20),
    ObjectID INT,
    FOREIGN KEY(ObjectID) REFERENCES OBJECT(ObjectID)
);

CREATE TABLE PRIVILEGE (
        PrivilegeID INT,
        PrivilegeTypeID INT,
        OperationID INT,
        RoleID INT,
        ObjectID INT,
        PRIMARY KEY (PrivilegeID),
        FOREIGN KEY (PrivilegeTypeID) REFERENCES PRIVILEGE_TYPE(PrivilegeTypeID),
        FOREIGN KEY (OperationID) REFERENCES OPERATION(OperationID),
		FOREIGN KEY (RoleID) REFERENCES ROLE_TAB(RoleID),
		FOREIGN KEY (ObjectID) REFERENCES OBJECT(ObjectID)
);

CREATE TABLE REP_PRIVILEGE(
	PrivilegeID INT PRIMARY KEY,
    SubjectID INT,
    ObjectID INT,
    PrivilegeTypeID INT,
    FOREIGN KEY(SubjectID) REFERENCES SUBJECT_TAB(SubjectID),
    FOREIGN KEY(ObjectID) REFERENCES OBJECT(ObjectID),
    FOREIGN KEY(PrivilegeTypeID) REFERENCES PRIVILEGE_TYPE(PrivilegeTypeID)
);

CREATE TABLE EVENT_TAB(
	EventID INT PRIMARY KEY,
    SessionID VARCHAR(20),
    AttempResult VARCHAR(20),
    DateAtr DATE,
    Message VARCHAR(50),
    NewValue VARCHAR(20),
    OldValue VARCHAR(20),
    EventTypeID INT,
    ObjectID INT,
    SystemID INT,
    UserID INT,
    RoleID INT,
    ObjectID2 INT,
    OperationID INT,
    FOREIGN KEY(EventTypeID) REFERENCES EVENT_TYPE(EventTypeID),
    FOREIGN KEY(ObjectID) REFERENCES OBJECT(ObjectID),
    FOREIGN KEY(SystemID) REFERENCES SYSTEM_TAB(SystemID),
    FOREIGN KEY(UserID) REFERENCES USER_TAB(UserID),
    FOREIGN KEY(RoleID) REFERENCES ROLE_TAB(RoleID),
    FOREIGN KEY(ObjectID2) REFERENCES OBJECT(ObjectID),
    FOREIGN KEY(OperationID) REFERENCES OPERATION(OperationID)
);

CREATE TABLE INCIDENT(
	IncidentID 	INT UNIQUE,
	DateAdded	DATE,
	Priority VARCHAR(20),
	StatusAtr VARCHAR(20),
    EmployeeID INT,
    EventID INT,
    SystemID INT,
    VulnerabilityID INT,
    ThreatID INT,
	PRIMARY KEY(IncidentID),
    FOREIGN KEY (EmployeeID) REFERENCES EMPLOYEE(EmployeeID),
    FOREIGN KEY (EventID) REFERENCES EVENT_TAB(EventID),
    FOREIGN KEY (SystemID) REFERENCES SYSTEM_TAB(SystemID),
    FOREIGN KEY (VulnerabilityID) REFERENCES VULNERABILITY(VulnerabilityID),
    FOREIGN KEY (ThreatID) REFERENCES THREAT(ThreatID)
);

CREATE TABLE ALERT(
		AlertID INT,
		DateGenerated DATE,
		NotifcationType CHAR(1),
		EmployeeID INT,
		IncidentID INT,
		PRIMARY KEY (AlertID),
		FOREIGN KEY (EmployeeID) REFERENCES EMPLOYEE(EmployeeID),
		FOREIGN KEY (IncidentID) REFERENCES INCIDENT(IncidentID)
	);

CREATE TABLE PROBLEM(
		ProblemID INT,
		NameAtr VARCHAR(20),
		DescriptionAtr VARCHAR(50),
		DateAdded DATE,
		EmployeeID INT,
		IncidentID INT,
		PRIMARY KEY (ProblemID),
		FOREIGN KEY (EmployeeID) REFERENCES EMPLOYEE(EmployeeID),
		FOREIGN KEY (IncidentID) REFERENCES INCIDENT(IncidentID)
	);

CREATE TABLE RISK(
	RiskID INT,
	NameAtr VARCHAR(20),
	DescriptionAtr VARCHAR(50),
	DateAdded DATE,
	StatusAtr VARCHAR(20),
	ProblemID INT,
	PRIMARY KEY(RiskID),
	FOREIGN KEY(ProblemID) REFERENCES PROBLEM(ProblemID)
);

SHOW TABLES;
