--------------------------------------------------------------------------------------
-- PRM V3.0 Database Load Script
--------------------------------------------------------------------------------------
-- This Script will create the Required Database for the PRM Suite - It is initially
-- written for loading into a Postgres(v11) database
--------------------------------------------------------------------------------------
-- All rights, including copyrights and patent rights, are reserved by
-- Stier Automation LLC. 
--------------------------------------------------------------------------------------
-- History
-- Initial Testing of variables and variable History	0.0.1	3/12/2020  Steven Stier
-- Removed Not Null on ServerID and VarDec		        0.0.2	3/15/2020  Steven Stier
-- changed Timestamp to TimestampTZ                     0.0.3   3/21/2020  Steven stier
--------------------------------------------------------------------------------------
--NOTES:
--This script is for new installations only because it drops database objects
--(tables, views, etc.) before creating them.
--------------------------------------------------------------------------------------

START TRANSACTION;

DROP TABLE IF EXISTS VariableHistoryN;

DROP TABLE IF EXISTS Variable;

-- Table: Variable (holds all the definitions of all the Variable)
CREATE TABLE Variable
(
    varId SERIAL,
    varName VARCHAR NOT NULL,
    serverID INTEGER,
    varDesc VARCHAR,
    varDescG  VARCHAR,
    varType  VARCHAR  DEFAULT 'N',
    engUnits  VARCHAR,
    precison INTEGER DEFAULT 0,
    maxScale NUMERIC,
    minScale NUMERIC,
    snapshotRate NUMERIC  DEFAULT 60,
    snapshotTreshold NUMERIC DEFAULT 0,
    lastValue VARCHAR,
    lastSampleTime TIMESTAMPTZ,
    lastQuality INTEGER,
    active BOOLEAN DEFAULT true,
    CONSTRAINT pk_variable_varId PRIMARY KEY (varId),
    CONSTRAINT unq_variable_varName UNIQUE (varName),
    CONSTRAINT chk_varType CHECK (varType in ('N','S','B'))
);


COMMENT ON COLUMN Variable.varName
    IS 'variable name';

COMMENT ON COLUMN Variable.serverID
    IS 'server id - points to server collecting variable';

COMMENT ON COLUMN Variable.varDesc
    IS 'variable description';

COMMENT ON COLUMN Variable.varDescG
    IS 'variable description in other language';

COMMENT ON COLUMN Variable.varType
    IS 'data varType numeric,string,boolean';

COMMENT ON COLUMN Variable.precison
    IS 'number of significant digits to right of decimal point';

COMMENT ON COLUMN Variable.maxScale
    IS 'maximum value for trending';

COMMENT ON COLUMN Variable.minScale
    IS 'minimum value for trending';

COMMENT ON COLUMN Variable.snapshotRate
    IS 'Number of Seconds between read attempts';

COMMENT ON COLUMN Variable.snapshotTreshold
    IS 'amount the value must change before recording a new value to the historian';

COMMENT ON COLUMN Variable.lastValue
    IS 'last historian snapshot value recorded';

COMMENT ON COLUMN Variable.lastSampleTime
    IS 'Sample time of last Data historian snapshot';

COMMENT ON COLUMN Variable.lastQuality
    IS 'last reading quality ';

COMMENT ON COLUMN Variable.active
    IS 'indicates if the variable is active- set to false to disable reads';


-- Table: VariableHistoryN (holds the variable history data for all Variable of varType=numeric)

CREATE TABLE VariableHistoryN
(
    varHistoryId BIGSERIAL,
    varId integer NOT NULL,
    sampleTime TIMESTAMPTZ NOT NULL,
    varValue NUMERIC,
    quality INTEGER,
    CONSTRAINT pk_VariableHistoryN_id PRIMARY KEY (varHistoryid),
    CONSTRAINT unq_VariableHistoryN_carId_sampleTime UNIQUE (varId,sampleTime),
    CONSTRAINT fk_variable_varId FOREIGN KEY (varId) REFERENCES Variable (varId)
);


COMMENT ON COLUMN VariableHistoryN.varId
    IS 'foriegn key which points to the variable in the Variable table';

COMMENT ON COLUMN VariableHistoryN.sampleTime
    IS 'time stamp that sample was taken';

COMMENT ON COLUMN VariableHistoryN.VarValue
    IS 'value of variable at time of sample';

COMMENT ON COLUMN VariableHistoryN.quality
    IS 'quality of value reading at sample time';

INSERT INTO Variable (varName,serverID,varDesc,engUnits,precison,minScale,maxScale)
  VALUES('HT1Temp',1,'Hot Tub 1 Temperature','deg F',1,0.11,100.11);
INSERT INTO Variable (varName,serverID,varDesc,engUnits,precison,minScale,maxScale)
  VALUES('Grow1Temp',1,'Grow Area 1 Temperature','deg F',1,0.22,100.22);

INSERT INTO VariableHistoryN (varId,sampleTime,varValue)
  VALUES((SELECT varId from Variable where varName='HT1Temp'),'1/1/2020 07:00:01',42.7);
INSERT INTO VariableHistoryN (varId,sampleTime,varValue)
  VALUES((SELECT varId from Variable where varName='HT1Temp'),'1/1/2020 08:30:03',56.2);
INSERT INTO VariableHistoryN (varId,sampleTime,varValue)
  VALUES((SELECT varId from Variable where varName='HT1Temp'),'1/1/2020 09:27:05',70.9);

INSERT INTO VariableHistoryN (varId,sampleTime,varValue)
  VALUES((SELECT varId from Variable where varName='Grow1Temp'),'2/1/2020 07:00:01',69.23);
INSERT INTO VariableHistoryN (varId,sampleTime,varValue)
  VALUES((SELECT varId from Variable where varName='Grow1Temp'),'2/1/2020 08:30:03',72.34);
INSERT INTO VariableHistoryN (varId,sampleTime,varValue)
  VALUES((SELECT varId from Variable where varName='Grow1Temp'),'2/1/2020 09:27:05',75.45);

COMMIT;

Select count(*) from Variable;
Select count(*) from VariableHistoryN;
