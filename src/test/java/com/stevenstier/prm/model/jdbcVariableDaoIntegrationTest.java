package com.stevenstier.prm.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.stevenstier.prm.model.Variable;
import com.stevenstier.prm.model.Dao.JDBC.JDBCVariableDao;

public class jdbcVariableDaoIntegrationTest {

	private static final long TEST_VARID = -999;
	private static final String TEST_VARNAME = "Test Var";
	private static final Integer TEST_SERVERID = 1;
	private static final String TEST_VARDESC = "Test Var Desc";
	private static final String TEST_VARDESCG = "Sempar Sunt molesta";
	private static final String TEST_VARTYPE = "N";
	private static final String TEST_ENGUNITS = "deg F";
	private static final Integer TEST_PRECISION = 2;
	private static final Double TEST_MAXSCALE = 999.99d;
	private static final Double TEST_MINSCALE = 111.11d;
	private static final Integer TEST_SNAPSHOTRATE = 120;
	private static final Double TEST_SNAPSHOTTRESHOLD = 0.2d;
	private static final String TEST_LASTVALUE = "123.45";
	private static LocalDateTime TEST_LASTSAMPLETIME = LocalDateTime.of(2017, 2, 13, 15, 56, 12);
	private static final Integer TEST_LASTQUALITY = 127;
	private static boolean TEST_ACTIVE = true;

	/*
	 * Using this particular implementation of DataSource so that every database
	 * interaction is part of the same database session and hence the same database
	 * transaction
	 */
	private static SingleConnectionDataSource dataSource;
	private JDBCVariableDao dao;
	private Variable testVariable;

	/*
	 * Before any tests are run, this method initializes the datasource for testing.
	 */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		
		String dburl = System.getenv("JDBC_DATABASE_URL");
      
      if (dburl != null){
        dataSource.setUrl(dburl);
       }
       else {
         dataSource.setUrl("jdbc:postgresql://localhost:5432/prm");
       }
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/*
		 * The following line disables autocommit for connections returned by this
		 * DataSource. This allows us to rollback any changes after each test
		 */
		dataSource.setAutoCommit(false);
	}

	/*
	 * After all tests have finished running, this method will close the DataSource
	 */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update("TRUNCATE Variable CASCADE"); // remove existing data

		String sqlInsert = "INSERT INTO Variable (varName,serverID,varDesc,engUnits,precison,maxScale,minScale) "
				+ " VALUES('HT1Temp',1,'Hot Tub 1 Temperature','deg F',1,0.11,100.11);";

		jdbcTemplate.update(sqlInsert);
		sqlInsert = "INSERT INTO Variable (varName,serverID,varDesc,engUnits,precison,maxScale,minScale) "
				+ "VALUES('Grow1Temp',1,'Grow Area 1 Temperature','deg F',1,0.22,100.22);";
		jdbcTemplate.update(sqlInsert);

		dao = new JDBCVariableDao(dataSource);

		testVariable = new Variable();

		testVariable.setVarId(TEST_VARID);
		testVariable.setVarName(TEST_VARNAME);
		testVariable.setServerId(TEST_SERVERID);
		testVariable.setVarDesc(TEST_VARDESC);
		testVariable.setVarDescG(TEST_VARDESCG);
		testVariable.setVarType(TEST_VARTYPE);
		testVariable.setEngUnits(TEST_ENGUNITS);
		testVariable.setPrecison(TEST_PRECISION);
		testVariable.setMaxScale(TEST_MAXSCALE);
		testVariable.setMinScale(TEST_MINSCALE);
		testVariable.setSnapshotRate(TEST_SNAPSHOTRATE);
		testVariable.setSnapshotTreshold(TEST_SNAPSHOTTRESHOLD);
		testVariable.setLastValue(TEST_LASTVALUE);
		testVariable.setLastSampleTime(TEST_LASTSAMPLETIME);
		testVariable.setLastQuality(TEST_LASTQUALITY);
		testVariable.setActive(TEST_ACTIVE);
	}

	/*
	 * After each test, we rollback any changes that were made to the database so
	 * that everything is clean for the next test
	 */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void does_getAllVariables_find_anything() {

		List<Variable> foundVariables = dao.getAllVariables();

		assertNotNull(foundVariables);
		assertEquals(2, foundVariables.size());
	}

	@Test
	public void can_deactivate_a_variable() {
		List<Variable> foundVariables = dao.getAllVariables();

		Variable sut = foundVariables.get(0);
		Assert.assertTrue(sut.getActive());
		dao.deactivateVariable(sut.getVarId());
		Variable var1 = dao.getVariablebyID(sut.getVarId());
		Assert.assertFalse(var1.getActive());
	}

	@Test
	public void can_update_a_variable_last_values() {
		List<Variable> foundVariables = dao.getAllVariables();

		Variable sut = foundVariables.get(0);
		dao.updatelastValue(sut.getVarId(), TEST_LASTVALUE, TEST_LASTSAMPLETIME, TEST_LASTQUALITY);

		Variable testVar = dao.getVariablebyID(sut.getVarId());

		assertEquals(TEST_LASTVALUE, testVar.getLastValue());
		assertEquals(TEST_LASTSAMPLETIME, testVar.getLastSampleTime());
		assertEquals(TEST_LASTQUALITY, testVar.getLastQuality());

	}

	

	@Test
	public void can_update_a_variable() {
		List<Variable> foundVariables = dao.getAllVariables();

		Variable sut = foundVariables.get(0);
		sut.setVarName("Updated Var");
		sut.setServerId(TEST_SERVERID);
		sut.setVarDesc(TEST_VARDESC);
		sut.setVarDescG(TEST_VARDESCG);
		sut.setVarType(TEST_VARTYPE);
		sut.setEngUnits(TEST_ENGUNITS);
		sut.setPrecison(TEST_PRECISION);
		sut.setMaxScale(TEST_MAXSCALE);
		sut.setMinScale(TEST_MINSCALE);
		sut.setSnapshotRate(TEST_SNAPSHOTRATE);
		sut.setSnapshotTreshold(TEST_SNAPSHOTTRESHOLD);
		sut.setActive(TEST_ACTIVE);
		dao.updateVariable(sut);

		Variable testVar = dao.getVariablebyID(sut.getVarId());

		assertEquals("Updated Var", testVar.getVarName());
		assertEquals(TEST_SERVERID, testVar.getServerId());
		assertEquals(TEST_VARDESC, testVar.getVarDesc());
		assertEquals(TEST_VARDESCG, testVar.getVarDescG());
		assertEquals(TEST_VARTYPE, testVar.getVarType());
		assertEquals(TEST_ENGUNITS, testVar.getEngUnits());
		assertEquals(TEST_PRECISION, testVar.getPrecison());
		assertEquals(TEST_MAXSCALE, testVar.getMaxScale());
		assertEquals(TEST_MINSCALE, testVar.getMinScale());
		assertEquals(TEST_SNAPSHOTRATE, testVar.getSnapshotRate());
		assertEquals(TEST_SNAPSHOTTRESHOLD, testVar.getSnapshotTreshold());
		assertEquals(TEST_ACTIVE, sut.getActive());
	}

	@Test
	public void can_create_variable_and_then_read_it_back_via_name() {
		Variable sut = new Variable();

		dao.createVariable(testVariable);

		sut = dao.getVariablebyName(TEST_VARNAME);
		assertEquals(TEST_VARNAME, sut.getVarName());
		assertEquals(TEST_SERVERID, sut.getServerId());
		assertEquals(TEST_VARDESC, sut.getVarDesc());
		assertEquals(TEST_VARDESCG, sut.getVarDescG());
		assertEquals(TEST_VARTYPE, sut.getVarType());
		assertEquals(TEST_ENGUNITS, sut.getEngUnits());
		assertEquals(TEST_PRECISION, sut.getPrecison());
		assertEquals(TEST_MAXSCALE, sut.getMaxScale());
		assertEquals(TEST_MINSCALE, sut.getMinScale());
		assertEquals(TEST_SNAPSHOTRATE, sut.getSnapshotRate());
		assertEquals(TEST_SNAPSHOTTRESHOLD, sut.getSnapshotTreshold());

	}



}