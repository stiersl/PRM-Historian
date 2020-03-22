--------------------------------------------------------------------------------------
-- PRM V3.0 Database Test Ssql
--------------------------------------------------------------------------------------
-- 
--------------------------------------------------------------------------------------
-- History
-- Initial Testing of PRMHistory				0.0.1	3/12/2020	Steven Stier

--------------------------------------------------------------------------------------

select varId,
    varName,
    serverId,
    varDesc,
    varDescG,
    varType,
    engUnits,
    precison,
    maxScale,
    minScale,
    snapshotRate ,
    snapshotTreshold,
    lastValueN,
    lastValueS,
    lastValueB,
    lastSampleTime,
    lastQuality,
    active
From Variable;

Select 
    varHistoryid,
    varId,
    sampleTime,
    varValue,
    quality
FROM VariableHistoryN;

SELECT v.varName,  v.varDesc, vh.sampleTime, vh.varvalue FROM Variable v
JOIN VariableHistoryN vh USING (varId);

SELECT  varhistoryID, varId, sampleTime, varvalue, quality FROM VariableHistoryN 
Where varId = (Select varID from variable where varname = 'HT1Temp');
SELECT  varId, sampleTime, varvalue FROM VariableHistoryN 

INSERT INTO Variable (varName) VALUES('xxx');

DELETE from VariableHistoryN where varId = 2;