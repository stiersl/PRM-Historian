package com.stevenstier.prm.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.stevenstier.prm.model.Variable;
import com.stevenstier.prm.model.Dao.JDBC.JDBCVariableDao;
import com.stevenstier.prm.model.Dao.JDBC.JDBCVariableHistoryNDao;

public class jdbcVariableHistoryNDaoIntegrationTest {

	private static final String TEST_VARNAME1 = "Test Var1";
	private static final Double TEST_VARVALUE1 = 123.45d;
	private static final LocalDateTime TEST_SAMPLETIME1 = LocalDateTime.of(2020, 2, 13, 15, 56, 12);
	private static final Integer TEST_QUALITY1 = 127;
	private static final String TEST_VARNAME2 = "Test Var2";
	
	private Long testvarId1;
	private Long testvarId2;
	/*
	 * Using this particular implementation of DataSource so that every database
	 * interaction is part of the same database session and hence the same database
	 * transaction
	 */
	private static SingleConnectionDataSource dataSource;
	private JDBCVariableHistoryNDao jdbcVariabeHistoryDao;
	private JDBCVariableDao jdbcVariableDao;
	private VariableHistoryN testVariableHistoryN;

	/*
	 * Before any tests are run, this method initializes the datasource for testing.
	 */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/prm");
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

		// jdbcTemplate.update("TRUNCATE Variables CASCADE"); //remove existing data

		String sqlInsert = "INSERT INTO Variable (varName) VALUES(?);";

		jdbcTemplate.update(sqlInsert, TEST_VARNAME1);

		String sqlSelect = "SELECT varId FROM variable WHERE varName = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect, TEST_VARNAME1);

		while (results.next()) {
			testvarId1 = results.getLong("varId");
		}

		sqlInsert = "INSERT INTO Variable (varName) VALUES(?);";

		jdbcTemplate.update(sqlInsert, TEST_VARNAME2);

		sqlSelect = "SELECT varId FROM variable WHERE varName = ?";
		results = jdbcTemplate.queryForRowSet(sqlSelect, TEST_VARNAME1);

		while (results.next()) {
			testvarId2 = results.getLong("varId");
		}
		jdbcVariabeHistoryDao = new JDBCVariableHistoryNDao(dataSource);
		jdbcVariableDao = new JDBCVariableDao(dataSource);
		testVariableHistoryN = new VariableHistoryN();

		testVariableHistoryN.setVarId(testvarId1);
		testVariableHistoryN.setSampleTime(TEST_SAMPLETIME1);
		testVariableHistoryN.setVarValue(TEST_VARVALUE1);
		testVariableHistoryN.setQuality(TEST_QUALITY1);
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
	public void does_getAllVarHistoryVarId_finds_anything() {

		List<VariableHistoryN> foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(1);

		assertNotNull(foundVariableHistory);
		assertEquals(3, foundVariableHistory.size());
	}

	@Test
	public void can_create_variableHistoryN_and_then_read_it_back_via_ID() {
		VariableHistoryN sut = new VariableHistoryN();

		Assert.assertTrue(jdbcVariabeHistoryDao.insertVarHistoryByVarId(testvarId1, TEST_SAMPLETIME1.toInstant(ZoneOffset.UTC), TEST_VARVALUE1,
				TEST_QUALITY1));

		List<VariableHistoryN> foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(testvarId1);
		sut = foundVariableHistory.get(0);

		assertEquals(testvarId1, sut.getVarId());
		assertEquals(TEST_SAMPLETIME1, sut.getSampleTime());
		assertEquals(TEST_VARVALUE1, sut.getVarValue());
		assertEquals(TEST_QUALITY1, sut.getQuality());
	}

	@Test
	public void can_create_a_historian_value_and_not_pass_sampletime() {
		VariableHistoryN sut = new VariableHistoryN();

		Assert.assertTrue(
				jdbcVariabeHistoryDao.insertVarHistoryByVarId(testvarId1, null, TEST_VARVALUE1, TEST_QUALITY1));

		List<VariableHistoryN> foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(testvarId1);
		sut = foundVariableHistory.get(0);

		assertEquals(testvarId1, sut.getVarId());
		Assert.assertNotNull(sut.getSampleTime());
		assertEquals(TEST_VARVALUE1, sut.getVarValue());
		assertEquals(TEST_QUALITY1, sut.getQuality());
	}

	@Test
	public void can_create_a_historian_value_and_not_pass_quality() {
		VariableHistoryN sut = new VariableHistoryN();

		Assert.assertTrue(
				jdbcVariabeHistoryDao.insertVarHistoryByVarId(testvarId1, TEST_SAMPLETIME1.toInstant(ZoneOffset.UTC), TEST_VARVALUE1, null));

		List<VariableHistoryN> foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(testvarId1);
		sut = foundVariableHistory.get(0);

		assertEquals(testvarId1, sut.getVarId());
		assertEquals(TEST_SAMPLETIME1, sut.getSampleTime());
		assertEquals(TEST_VARVALUE1, sut.getVarValue());
		assertEquals((Integer) 192, sut.getQuality());
	}

	@Test
	public void can_create_variableHistoryN_and_then_read_it_back_via_name() {
		VariableHistoryN sut = new VariableHistoryN();

		Assert.assertTrue(jdbcVariabeHistoryDao.insertVarHistoryByVarName(TEST_VARNAME1, TEST_SAMPLETIME1.toInstant(ZoneOffset.UTC),
				TEST_VARVALUE1, TEST_QUALITY1));

		List<VariableHistoryN> foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(testvarId1);
		sut = foundVariableHistory.get(0);

		assertEquals(testvarId1, sut.getVarId());
		assertEquals(TEST_SAMPLETIME1, sut.getSampleTime());
		assertEquals(TEST_VARVALUE1, sut.getVarValue());
		assertEquals(TEST_QUALITY1, sut.getQuality());
	}

	@Test
	public void can_read_back_historian_data_using_timestamp_and_id() {

		jdbcVariabeHistoryDao.insertVarHistoryByVarId(testvarId1,LocalDateTime.of(2020, 2, 13, 15, 56, 12).toInstant(ZoneOffset.UTC),
				TEST_VARVALUE1, TEST_QUALITY1);
		LocalDateTime startTime = LocalDateTime.of(2020, 2, 11, 15, 56, 12);
		LocalDateTime endTime = LocalDateTime.of(2020, 2, 12, 15, 56, 12);

		List<VariableHistoryN> foundHistory = jdbcVariabeHistoryDao.getVarHistoryByVarId(testvarId1, startTime,
				endTime);

		assertEquals(0, foundHistory.size());

		startTime = LocalDateTime.of(2020, 2, 11, 15, 56, 12);
		endTime = LocalDateTime.of(2020, 2, 14, 15, 56, 12);

		foundHistory = jdbcVariabeHistoryDao.getVarHistoryByVarId(testvarId1, startTime, endTime);

		assertEquals(1, foundHistory.size());

		startTime = LocalDateTime.of(2020, 2, 14, 15, 56, 12);
		endTime = LocalDateTime.of(2020, 2, 15, 15, 56, 12);

		foundHistory = jdbcVariabeHistoryDao.getVarHistoryByVarId(testvarId1, startTime, endTime);

		assertEquals(0, foundHistory.size());
	}

	@Test
	public void can_insert_a_variableHistory_and_then_read_it_back_from_variable_using_ID() {
		Variable sut = new Variable();

		Assert.assertTrue(jdbcVariabeHistoryDao.insertVarHistoryByVarId(testvarId1, TEST_SAMPLETIME1.toInstant(ZoneOffset.UTC), TEST_VARVALUE1,
				TEST_QUALITY1));

		sut = jdbcVariableDao.getVariablebyID(testvarId1);

		assertEquals(testvarId1, sut.getVarId());
		assertEquals(TEST_SAMPLETIME1, sut.getLastSampleTime());
		assertEquals(TEST_VARVALUE1, sut.getLastValue());
		assertEquals(TEST_QUALITY1, sut.getLastQuality());
	}

	@Test
	public void can_insert_a_variableHistory_and_then_read_it_back_from_variable_using_Name() {
		Variable sut = new Variable();

		Assert.assertTrue(jdbcVariabeHistoryDao.insertVarHistoryByVarName(TEST_VARNAME1, TEST_SAMPLETIME1.toInstant(ZoneOffset.UTC),
				TEST_VARVALUE1, TEST_QUALITY1));

		sut = jdbcVariableDao.getVariablebyName(TEST_VARNAME1);

		assertEquals(testvarId1, sut.getVarId());
		assertEquals(TEST_SAMPLETIME1, sut.getLastSampleTime());
		assertEquals(TEST_VARVALUE1, sut.getLastValue());
		assertEquals(TEST_QUALITY1, sut.getLastQuality());
	}

	@Test
	public void can_delete_variableHistoryN_via_ID() {

		Assert.assertTrue(jdbcVariabeHistoryDao.insertVarHistoryByVarId(testvarId1, TEST_SAMPLETIME1.toInstant(ZoneOffset.UTC),
				TEST_VARVALUE1, TEST_QUALITY1));

		List<VariableHistoryN> foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(testvarId1);
		
		assertEquals(1,foundVariableHistory.size());
		
		Assert.assertTrue(jdbcVariabeHistoryDao.deleteVarHistoryByVarId(testvarId1));
		
		foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(testvarId1);
		
		assertEquals(0,foundVariableHistory.size());
		
	}

	@Test
	public void can_delete_variableHistoryN_via_Name() {

		Assert.assertTrue(jdbcVariabeHistoryDao.insertVarHistoryByVarId(testvarId1, TEST_SAMPLETIME1.toInstant(ZoneOffset.UTC),
				TEST_VARVALUE1, TEST_QUALITY1));

		List<VariableHistoryN> foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(testvarId1);
		
		assertEquals(1,foundVariableHistory.size());
		
		Assert.assertTrue(jdbcVariabeHistoryDao.deleteVarHistoryByVarName(TEST_VARNAME1));
		
		foundVariableHistory = jdbcVariabeHistoryDao.getAllVarHistoryVarId(testvarId1);
		
		assertEquals(0,foundVariableHistory.size());
		
	}
	
	
	
}