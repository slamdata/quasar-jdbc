package slamdata.jdbc

import argonaut._, Argonaut._

import java.{sql => jdbc}

class SlamDataDriver extends jdbc.Driver {
  def acceptsURL(url: String): Boolean = ???

  def connect(url: String, info: java.util.Properties): jdbc.Connection = new SlamDataConnection(new Client(url))

  def getMajorVersion: Int = 0
  def getMinorVersion: Int = 1

  def getPropertyInfo(url: String, info: java.util.Properties): Array[jdbc.DriverPropertyInfo] = new Array(0)

  def jdbcCompliant: Boolean = false
  
  def getParentLogger: java.util.logging.Logger = throw new jdbc.SQLFeatureNotSupportedException
}

class SlamDataConnection(client: Client) extends jdbc.Connection { cxn =>
  def getCatalog: String = "catalog"

  def getMetaData(): jdbc.DatabaseMetaData = new jdbc.DatabaseMetaData {
    def empty = new SlamDataResultSet(Nil)
    
    // TODO: pull from build configuration
    def getDriverMajorVersion(): Int = 0
    def getDriverMinorVersion(): Int = 1
    def getDriverName(): String = "SlamData SlamEngine JDBC"
    def getDriverVersion(): String = "0.1-SNAPSHOT"

    // 4.1 is the version included with Java 1.7
    def getJDBCMajorVersion(): Int = 4
    def getJDBCMinorVersion(): Int = 1
    



    def getConnection(): jdbc.Connection = cxn
    def getURL(): String = client.url
    def getUserName(): String = null

    // TODO: expose this info in the API
    def getDatabaseMajorVersion(): Int = 0
    def getDatabaseMinorVersion(): Int = 7
    def getDatabaseProductName(): String = "SlamData SlamEngine"
    def getDatabaseProductVersion(): String = "0.7-SNAPSHOT"



    // Syntax and nomenclature:

    def getCatalogTerm(): String = "catalog"
    def getProcedureTerm(): String = "procedure"
    def getSchemaTerm(): String = "db"

    def getCatalogSeparator(): String = "/"
    def getExtraNameCharacters(): String = ""
    def getIdentifierQuoteString(): String = "\""
    def getSearchStringEscape(): String = "%"

    def getSQLKeywords(): String = ""
    def getNumericFunctions(): String = ""
    def getStringFunctions(): String = ""
    def getTimeDateFunctions(): String = ""
    def getSystemFunctions(): String = ""
    

    def allProceduresAreCallable(): Boolean = false
    def allTablesAreSelectable(): Boolean = true
    def autoCommitFailureClosesAllResultSets(): Boolean = false
    def dataDefinitionCausesTransactionCommit(): Boolean = false
    def dataDefinitionIgnoredInTransactions(): Boolean = true
    def deletesAreDetected(typ: Int): Boolean = false
    def doesMaxRowSizeIncludeBlobs(): Boolean = true
    def generatedKeyAlwaysReturned(): Boolean = false
    def getAttributes(catalog: String, schemaPattern: String, typeNamePattern: String, attributeNamePattern: String): jdbc.ResultSet = ???
    def getBestRowIdentifier(catalog: String, schema: String, table: String, scope: Int, nullable: Boolean): jdbc.ResultSet = ???
    def getCatalogs(): jdbc.ResultSet = new SlamDataResultSet(Json("path" := "/") :: Nil)
    def getClientInfoProperties(): jdbc.ResultSet = empty
    def getColumnPrivileges(catalog: String, schemaPattern: String, table: String, columnNamePattern: String): jdbc.ResultSet = empty
    def getColumns(catalog: String, schemaPattern: String, tableNamePattern: String, columnNamePattern: String): jdbc.ResultSet = empty
    def getCrossReference(parentCatalog: String, parentSchema: String, parentTable: String, foreignCatalog: String, foreignSchema: String, foreignTable: String): jdbc.ResultSet = empty
    def getDefaultTransactionIsolation(): Int = jdbc.Connection.TRANSACTION_NONE
    def getExportedKeys(catalog: String, schema: String, table: String): jdbc.ResultSet = empty
    def getFunctionColumns(catalog: String, schema: String, functionNamePattern: String, columnNamePattern: String): jdbc.ResultSet = empty
    def getFunctions(catalog: String, schemaPattern: String, functionNamePattern: String): jdbc.ResultSet = empty
    def getImportedKeys(catalog: String, schema: String, table: String): jdbc.ResultSet = empty
    def getIndexInfo(catalog: String, schema: String, table: String, unique: Boolean, approximate: Boolean): jdbc.ResultSet = empty
    def getMaxBinaryLiteralLength(): Int = 0
    def getMaxCatalogNameLength(): Int = 0
    def getMaxCharLiteralLength(): Int = 0
    def getMaxColumnNameLength(): Int = 0
    def getMaxColumnsInGroupBy(): Int = 0
    def getMaxColumnsInIndex(): Int = 0
    def getMaxColumnsInOrderBy(): Int = 0
    def getMaxColumnsInSelect(): Int = 0
    def getMaxColumnsInTable(): Int = 0
    def getMaxConnections(): Int = 0
    def getMaxCursorNameLength(): Int = 0
    def getMaxIndexLength(): Int = 0
    def getMaxProcedureNameLength(): Int = 0
    def getMaxRowSize(): Int = 0
    def getMaxSchemaNameLength(): Int = 0
    def getMaxStatementLength(): Int = 0
    def getMaxStatements(): Int = 0
    def getMaxTableNameLength(): Int = 120
    def getMaxTablesInSelect(): Int = 0
    def getMaxUserNameLength(): Int = 0
    def getPrimaryKeys(catalog: String, schema: String, table: String): jdbc.ResultSet = empty
    def getProcedureColumns(catalog: String, schemaPattern: String, procedureNamePattern: String, columnNamePattern: String): jdbc.ResultSet = empty
    def getProcedures(x$1: String,x$2: String,x$3: String): jdbc.ResultSet = empty
    def getPseudoColumns(x$1: String,x$2: String,x$3: String,x$4: String): jdbc.ResultSet = empty
    def getResultSetHoldability(): Int = jdbc.ResultSet.HOLD_CURSORS_OVER_COMMIT
    def getRowIdLifetime(): jdbc.RowIdLifetime = jdbc.RowIdLifetime.ROWID_VALID_FOREVER
    def getSQLStateType(): Int = ???
    def getSchemas(catalog: String, schemaPattern: String): jdbc.ResultSet = empty
    def getSchemas(): jdbc.ResultSet = empty
    def getSuperTables(catalog: String, schemaPattern: String, tableNamePattern: String): jdbc.ResultSet = empty
    def getSuperTypes(catalog: String, schemaPattern: String, typeNamePattern: String): jdbc.ResultSet = empty
    def getTablePrivileges(catalog: String, schemaPattern: String, tableNamePattern: String): jdbc.ResultSet = empty
    def getTableTypes(): jdbc.ResultSet = new SlamDataResultSet(Json("type" := "collection") :: Nil)
    def getTables(catalog: String, schemaPattern: String, tableNamePattern: String, types: Array[String]): jdbc.ResultSet = empty  // TODO
    def getTypeInfo(): jdbc.ResultSet = empty
    def getUDTs(catalog: String, schemaPattern: String, typeNamePattern: String, types: Array[Int]): jdbc.ResultSet = empty
    def getVersionColumns(catalog: String, schema: String, table: String): jdbc.ResultSet = empty
    def insertsAreDetected(`type`: Int): Boolean = false
    def isCatalogAtStart(): Boolean = false
    def isReadOnly(): Boolean = false
    def locatorsUpdateCopy(): Boolean = false
    def nullPlusNonNullIsNull(): Boolean = false
    def nullsAreSortedAtEnd(): Boolean = false
    def nullsAreSortedAtStart(): Boolean = false
    def nullsAreSortedHigh(): Boolean = false
    def nullsAreSortedLow(): Boolean = true
    def othersDeletesAreVisible(typ: Int): Boolean = true
    def othersInsertsAreVisible(typ: Int): Boolean = true
    def othersUpdatesAreVisible(typ: Int): Boolean = true
    def ownDeletesAreVisible(typ: Int): Boolean = true
    def ownInsertsAreVisible(typ: Int): Boolean = true
    def ownUpdatesAreVisible(typ: Int): Boolean = true
    def storesLowerCaseIdentifiers(): Boolean = false
    def storesLowerCaseQuotedIdentifiers(): Boolean = false
    def storesMixedCaseIdentifiers(): Boolean = true
    def storesMixedCaseQuotedIdentifiers(): Boolean = true
    def storesUpperCaseIdentifiers(): Boolean = false
    def storesUpperCaseQuotedIdentifiers(): Boolean = false
    
    def supportsANSI92EntryLevelSQL(): Boolean = true
    def supportsANSI92FullSQL(): Boolean = true
    def supportsANSI92IntermediateSQL(): Boolean = true
    def supportsAlterTableWithAddColumn(): Boolean = false
    def supportsAlterTableWithDropColumn(): Boolean = false
    def supportsBatchUpdates(): Boolean = true
    def supportsCatalogsInDataManipulation(): Boolean = false
    def supportsCatalogsInIndexDefinitions(): Boolean = false
    def supportsCatalogsInPrivilegeDefinitions(): Boolean = false
    def supportsCatalogsInProcedureCalls(): Boolean = false
    def supportsCatalogsInTableDefinitions(): Boolean = false
    def supportsColumnAliasing(): Boolean = true
    def supportsConvert(fromType: Int, toType: Int): Boolean = true
    def supportsConvert(): Boolean = true
    def supportsCoreSQLGrammar(): Boolean = true
    def supportsCorrelatedSubqueries(): Boolean = true
    def supportsDataDefinitionAndDataManipulationTransactions(): Boolean = false
    def supportsDataManipulationTransactionsOnly(): Boolean = true
    def supportsDifferentTableCorrelationNames(): Boolean = true
    def supportsExpressionsInOrderBy(): Boolean = true
    def supportsExtendedSQLGrammar(): Boolean = false
    def supportsFullOuterJoins(): Boolean = true
    def supportsGetGeneratedKeys(): Boolean = false
    def supportsGroupBy(): Boolean = true
    def supportsGroupByBeyondSelect(): Boolean = true
    def supportsGroupByUnrelated(): Boolean = true
    def supportsIntegrityEnhancementFacility(): Boolean = false
    def supportsLikeEscapeClause(): Boolean = true
    def supportsLimitedOuterJoins(): Boolean = true
    def supportsMinimumSQLGrammar(): Boolean = true
    def supportsMixedCaseIdentifiers(): Boolean = true
    def supportsMixedCaseQuotedIdentifiers(): Boolean = true
    def supportsMultipleOpenResults(): Boolean = true
    def supportsMultipleResultSets(): Boolean = true
    def supportsMultipleTransactions(): Boolean = true
    def supportsNamedParameters(): Boolean = true
    def supportsNonNullableColumns(): Boolean = false
    def supportsOpenCursorsAcrossCommit(): Boolean = false
    def supportsOpenCursorsAcrossRollback(): Boolean = false
    def supportsOpenStatementsAcrossCommit(): Boolean = false
    def supportsOpenStatementsAcrossRollback(): Boolean = false
    def supportsOrderByUnrelated(): Boolean = true
    def supportsOuterJoins(): Boolean = true
    def supportsPositionedDelete(): Boolean = false
    def supportsPositionedUpdate(): Boolean = false
    def supportsResultSetConcurrency(typ: Int, concurrency: Int): Boolean = concurrency match {
      case jdbc.ResultSet.CONCUR_READ_ONLY => supportsResultSetType(typ)
      case jdbc.ResultSet.CONCUR_UPDATABLE => supportsResultSetType(typ)
    }
    def supportsResultSetHoldability(holdability: Int): Boolean = holdability match {
      case jdbc.ResultSet.HOLD_CURSORS_OVER_COMMIT => false
      case jdbc.ResultSet.CLOSE_CURSORS_AT_COMMIT  => false
    }
    def supportsResultSetType(typ: Int): Boolean = typ match {
      case jdbc.ResultSet.TYPE_FORWARD_ONLY       => true
      case jdbc.ResultSet.TYPE_SCROLL_INSENSITIVE => false
      case jdbc.ResultSet.TYPE_SCROLL_SENSITIVE   => false
    }
    def supportsSavepoints(): Boolean = false
    def supportsSchemasInDataManipulation(): Boolean = false
    def supportsSchemasInIndexDefinitions(): Boolean = false
    def supportsSchemasInPrivilegeDefinitions(): Boolean = false
    def supportsSchemasInProcedureCalls(): Boolean = false
    def supportsSchemasInTableDefinitions(): Boolean = false
    def supportsSelectForUpdate(): Boolean = false
    def supportsStatementPooling(): Boolean = false
    def supportsStoredFunctionsUsingCallSyntax(): Boolean = false
    def supportsStoredProcedures(): Boolean = false
    def supportsSubqueriesInComparisons(): Boolean = true
    def supportsSubqueriesInExists(): Boolean = true
    def supportsSubqueriesInIns(): Boolean = true
    def supportsSubqueriesInQuantifieds(): Boolean = true
    def supportsTableCorrelationNames(): Boolean = true
    def supportsTransactionIsolationLevel(level: Int): Boolean = false
    def supportsTransactions(): Boolean = false
    def supportsUnion(): Boolean = false
    def supportsUnionAll(): Boolean = false
    def updatesAreDetected(typ: Int): Boolean = false
    def usesLocalFilePerTable(): Boolean = false
    def usesLocalFiles(): Boolean = false
    
    // Members declared in jdbc.Wrapper
    def isWrapperFor(x$1: Class[_]): Boolean = ???
    def unwrap[T](x$1: Class[T]): T = ???
  }

  def getAutoCommit(): Boolean = true

  def createStatement(): jdbc.Statement = new SlamDataStatement(client)
  
  def prepareStatement(x$1: String): jdbc.PreparedStatement = ???

  def close(): Unit = ()

  def abort(x$1: java.util.concurrent.Executor): Unit = ???
  def clearWarnings(): Unit = ???
  def commit(): Unit = ???
  def createArrayOf(x$1: String,x$2: Array[Object]): jdbc.Array = ???
  def createBlob(): jdbc.Blob = ???
  def createClob(): jdbc.Clob = ???
  def createNClob(): jdbc.NClob = ???
  def createSQLXML(): jdbc.SQLXML = ???
  def createStatement(x$1: Int,x$2: Int,x$3: Int): jdbc.Statement = ???
  def createStatement(x$1: Int,x$2: Int): jdbc.Statement = ???
  def createStruct(x$1: String,x$2: Array[Object]): jdbc.Struct = ???
  def getClientInfo(): java.util.Properties = ???
  def getClientInfo(x$1: String): String = ???
  def getHoldability(): Int = ???
  def getNetworkTimeout(): Int = ???
  def getSchema(): String = ???
  def getTransactionIsolation(): Int = ???
  def getTypeMap(): java.util.Map[String,Class[_]] = ???
  def getWarnings(): jdbc.SQLWarning = ???
  def isClosed(): Boolean = ???
  def isReadOnly(): Boolean = ???
  def isValid(x$1: Int): Boolean = ???
  def nativeSQL(x$1: String): String = ???
  def prepareCall(x$1: String,x$2: Int,x$3: Int,x$4: Int): jdbc.CallableStatement = ???
  def prepareCall(x$1: String,x$2: Int,x$3: Int): jdbc.CallableStatement = ???
  def prepareCall(x$1: String): jdbc.CallableStatement = ???
  def prepareStatement(x$1: String,x$2: Array[String]): jdbc.PreparedStatement = ???
  def prepareStatement(x$1: String,x$2: Array[Int]): jdbc.PreparedStatement = ???
  def prepareStatement(x$1: String,x$2: Int): jdbc.PreparedStatement = ???
  def prepareStatement(x$1: String,x$2: Int,x$3: Int,x$4: Int): jdbc.PreparedStatement = ???
  def prepareStatement(x$1: String,x$2: Int,x$3: Int): jdbc.PreparedStatement = ???
  def releaseSavepoint(x$1: jdbc.Savepoint): Unit = ???
  def rollback(x$1: jdbc.Savepoint): Unit = ???
  def rollback(): Unit = ???
  def setAutoCommit(x$1: Boolean): Unit = ???
  def setCatalog(x$1: String): Unit = ???
  def setClientInfo(x$1: java.util.Properties): Unit = ???
  def setClientInfo(x$1: String,x$2: String): Unit = ???
  def setHoldability(x$1: Int): Unit = ???
  def setNetworkTimeout(x$1: java.util.concurrent.Executor,x$2: Int): Unit = ???
  def setReadOnly(x$1: Boolean): Unit = ???
  def setSavepoint(x$1: String): jdbc.Savepoint = ???
  def setSavepoint(): jdbc.Savepoint = ???
  def setSchema(x$1: String): Unit = ???
  def setTransactionIsolation(x$1: Int): Unit = ???
  def setTypeMap(x$1: java.util.Map[String,Class[_]]): Unit = ???
  
  def isWrapperFor(x$1: Class[_]): Boolean = ???
  def unwrap[T](x$1: Class[T]): T = ???


  class SlamDataStatement(client: Client) extends jdbc.Statement {
    var maxRows: Option[Int] = None
    
    def getConnection(): jdbc.Connection = cxn

    def setMaxRows(max: Int): Unit = {
      maxRows = Some(max).filter(_ > 0)
    }

    def getMaxRows(): Int = maxRows.getOrElse(0)

    def executeQuery(sql: String): jdbc.ResultSet = {
      client.query(sql, maxRows=maxRows).fold(
        err => throw new jdbc.SQLException(err),
        json => new SlamDataResultSet(json)
      )
    }
  
    var resultSet: Option[jdbc.ResultSet] = None
  
    def execute(sql: String): Boolean = {
      resultSet = Some(executeQuery(sql))
      true
    }

    def getResultSet(): jdbc.ResultSet = resultSet.getOrElse(throw new jdbc.SQLException())

    def getMoreResults(): Boolean = {
      resultSet = None
      false
    }
    def getMoreResults(current: Int): Boolean = getMoreResults

    def close(): Unit = ()

    def addBatch(x$1: String): Unit = ???
    def cancel(): Unit = ???
    def clearBatch(): Unit = ???
    def clearWarnings(): Unit = ???
    def closeOnCompletion(): Unit = ???
    def execute(x$1: String,x$2: Array[String]): Boolean = ???
    def execute(x$1: String,x$2: Array[Int]): Boolean = ???
    def execute(x$1: String,x$2: Int): Boolean = ???
    def executeBatch(): Array[Int] = ???
    def executeUpdate(x$1: String,x$2: Array[String]): Int = ???
    def executeUpdate(x$1: String,x$2: Array[Int]): Int = ???
    def executeUpdate(x$1: String,x$2: Int): Int = ???
    def executeUpdate(x$1: String): Int = ???
    def getFetchDirection(): Int = ???
    def getFetchSize(): Int = ???
    def getGeneratedKeys(): jdbc.ResultSet = ???
    def getMaxFieldSize(): Int = ???
    def getQueryTimeout(): Int = ???
    def getResultSetConcurrency(): Int = ???
    def getResultSetHoldability(): Int = ???
    def getResultSetType(): Int = jdbc.ResultSet.TYPE_FORWARD_ONLY
    def getUpdateCount(): Int = 0
    def getWarnings(): jdbc.SQLWarning = null
    def isCloseOnCompletion(): Boolean = ???
    def isClosed(): Boolean = false
    def isPoolable(): Boolean = true
    def setCursorName(x$1: String): Unit = ???
    def setEscapeProcessing(x$1: Boolean): Unit = ???
    def setFetchDirection(x$1: Int): Unit = ???
    def setFetchSize(x$1: Int): Unit = ???
    def setMaxFieldSize(x$1: Int): Unit = ???
    def setPoolable(x$1: Boolean): Unit = ???
    def setQueryTimeout(x$1: Int): Unit = ???

    def isWrapperFor(x$1: Class[_]): Boolean = ???
    def unwrap[T](x$1: Class[T]): T = ???
  }
}

class SlamDataResultSet(result: List[Json]) extends jdbc.ResultSet {
  // println("result: " + result)
  val columns = (for {
    first <- result.headOption
    obj   <- first.obj
  } yield obj.fields).getOrElse(Nil)
  
  var cur: Int = -1
  
  def next(): Boolean = {
    cur += 1
    cur < result.size
  }
  
  def getString(columnLabel: String): String = (for {
    o <- result(cur).obj
    v <- o(columnLabel)
  } yield v.toString).getOrElse(null)

  def getString(columnIndex: Int): String = getString(columns(columnIndex-1))
  
  def getInt(columnLabel: String): Int = ???
  
  def wasNull(): Boolean = false  // TODO
  

  def close(): Unit = ()


  def absolute(x$1: Int): Boolean = ???
  def afterLast(): Unit = ???
  def beforeFirst(): Unit = ???
  def cancelRowUpdates(): Unit = ???
  def clearWarnings(): Unit = ???
  def deleteRow(): Unit = ???
  def findColumn(x$1: String): Int = ???
  def first(): Boolean = ???
  def getArray(x$1: String): jdbc.Array = ???
  def getArray(x$1: Int): jdbc.Array = ???
  def getAsciiStream(x$1: String): java.io.InputStream = ???
  def getAsciiStream(x$1: Int): java.io.InputStream = ???
  def getBigDecimal(x$1: String): java.math.BigDecimal = ???
  def getBigDecimal(x$1: Int): java.math.BigDecimal = ???
  def getBigDecimal(x$1: String,x$2: Int): java.math.BigDecimal = ???
  def getBigDecimal(x$1: Int,x$2: Int): java.math.BigDecimal = ???
  def getBinaryStream(x$1: String): java.io.InputStream = ???
  def getBinaryStream(x$1: Int): java.io.InputStream = ???
  def getBlob(x$1: String): jdbc.Blob = ???
  def getBlob(x$1: Int): jdbc.Blob = ???
  def getBoolean(x$1: String): Boolean = ???
  def getBoolean(x$1: Int): Boolean = ???
  def getByte(x$1: String): Byte = ???
  def getByte(x$1: Int): Byte = ???
  def getBytes(x$1: String): Array[Byte] = ???
  def getBytes(x$1: Int): Array[Byte] = ???
  def getCharacterStream(x$1: String): java.io.Reader = ???
  def getCharacterStream(x$1: Int): java.io.Reader = ???
  def getClob(x$1: String): jdbc.Clob = ???
  def getClob(x$1: Int): jdbc.Clob = ???
  def getConcurrency(): Int = ???
  def getCursorName(): String = ???
  def getDate(x$1: String,x$2: java.util.Calendar): jdbc.Date = ???
  def getDate(x$1: Int,x$2: java.util.Calendar): jdbc.Date = ???
  def getDate(x$1: String): jdbc.Date = ???
  def getDate(x$1: Int): jdbc.Date = ???
  def getDouble(x$1: String): Double = ???
  def getDouble(x$1: Int): Double = ???
  def getFetchDirection(): Int = ???
  def getFetchSize(): Int = ???
  def getFloat(x$1: String): Float = ???
  def getFloat(x$1: Int): Float = ???
  def getHoldability(): Int = ???
  def getInt(x$1: Int): Int = ???
  def getLong(x$1: String): Long = ???
  def getLong(x$1: Int): Long = ???
  def getNCharacterStream(x$1: String): java.io.Reader = ???
  def getNCharacterStream(x$1: Int): java.io.Reader = ???
  def getNClob(x$1: String): jdbc.NClob = ???
  def getNClob(x$1: Int): jdbc.NClob = ???
  def getNString(x$1: String): String = ???
  def getNString(x$1: Int): String = ???
  def getObject[T](x$1: String,x$2: Class[T]): T = ???
  def getObject[T](x$1: Int,x$2: Class[T]): T = ???
  def getObject(x$1: String,x$2: java.util.Map[String,Class[_]]): Object = ???
  def getObject(x$1: Int,x$2: java.util.Map[String,Class[_]]): Object = ???
  def getObject(x$1: String): Object = ???
  def getObject(x$1: Int): Object = ???
  def getRef(x$1: String): jdbc.Ref = ???
  def getRef(x$1: Int): jdbc.Ref = ???
  def getRow(): Int = ???
  def getRowId(x$1: String): jdbc.RowId = ???
  def getRowId(x$1: Int): jdbc.RowId = ???
  def getSQLXML(x$1: String): jdbc.SQLXML = ???
  def getSQLXML(x$1: Int): jdbc.SQLXML = ???
  def getShort(x$1: String): Short = ???
  def getShort(x$1: Int): Short = ???
  def getStatement(): jdbc.Statement = ???
  def getTime(x$1: String,x$2: java.util.Calendar): jdbc.Time = ???
  def getTime(x$1: Int,x$2: java.util.Calendar): jdbc.Time = ???
  def getTime(x$1: String): jdbc.Time = ???
  def getTime(x$1: Int): jdbc.Time = ???
  def getTimestamp(x$1: String,x$2: java.util.Calendar): jdbc.Timestamp = ???
  def getTimestamp(x$1: Int,x$2: java.util.Calendar): jdbc.Timestamp = ???
  def getTimestamp(x$1: String): jdbc.Timestamp = ???
  def getTimestamp(x$1: Int): jdbc.Timestamp = ???
  def getType(): Int = ???
  def getURL(x$1: String): java.net.URL = ???
  def getURL(x$1: Int): java.net.URL = ???
  def getUnicodeStream(x$1: String): java.io.InputStream = ???
  def getUnicodeStream(x$1: Int): java.io.InputStream = ???
  def getWarnings(): jdbc.SQLWarning = null
  def insertRow(): Unit = ???
  def isAfterLast(): Boolean = ???
  def isBeforeFirst(): Boolean = ???
  def isClosed(): Boolean = ???
  def isFirst(): Boolean = ???
  def isLast(): Boolean = ???
  def last(): Boolean = ???
  def moveToCurrentRow(): Unit = ???
  def moveToInsertRow(): Unit = ???
  def previous(): Boolean = ???
  def refreshRow(): Unit = ???
  def relative(x$1: Int): Boolean = ???
  def rowDeleted(): Boolean = ???
  def rowInserted(): Boolean = ???
  def rowUpdated(): Boolean = ???
  def setFetchDirection(x$1: Int): Unit = ???
  def setFetchSize(x$1: Int): Unit = ???
  def updateArray(x$1: String,x$2: jdbc.Array): Unit = ???
  def updateArray(x$1: Int,x$2: jdbc.Array): Unit = ???
  def updateAsciiStream(x$1: String,x$2: java.io.InputStream): Unit = ???
  def updateAsciiStream(x$1: Int,x$2: java.io.InputStream): Unit = ???
  def updateAsciiStream(x$1: String,x$2: java.io.InputStream,x$3: Long): Unit = ???
  def updateAsciiStream(x$1: Int,x$2: java.io.InputStream,x$3: Long): Unit = ???
  def updateAsciiStream(x$1: String,x$2: java.io.InputStream,x$3: Int): Unit = ???
  def updateAsciiStream(x$1: Int,x$2: java.io.InputStream,x$3: Int): Unit = ???
  def updateBigDecimal(x$1: String,x$2: java.math.BigDecimal): Unit = ???
  def updateBigDecimal(x$1: Int,x$2: java.math.BigDecimal): Unit = ???
  def updateBinaryStream(x$1: String,x$2: java.io.InputStream): Unit = ???
  def updateBinaryStream(x$1: Int,x$2: java.io.InputStream): Unit = ???
  def updateBinaryStream(x$1: String,x$2: java.io.InputStream,x$3: Long): Unit = ???
  def updateBinaryStream(x$1: Int,x$2: java.io.InputStream,x$3: Long): Unit = ???
  def updateBinaryStream(x$1: String,x$2: java.io.InputStream,x$3: Int): Unit = ???
  def updateBinaryStream(x$1: Int,x$2: java.io.InputStream,x$3: Int): Unit = ???
  def updateBlob(x$1: String,x$2: java.io.InputStream): Unit = ???
  def updateBlob(x$1: Int,x$2: java.io.InputStream): Unit = ???
  def updateBlob(x$1: String,x$2: java.io.InputStream,x$3: Long): Unit = ???
  def updateBlob(x$1: Int,x$2: java.io.InputStream,x$3: Long): Unit = ???
  def updateBlob(x$1: String,x$2: jdbc.Blob): Unit = ???
  def updateBlob(x$1: Int,x$2: jdbc.Blob): Unit = ???
  def updateBoolean(x$1: String,x$2: Boolean): Unit = ???
  def updateBoolean(x$1: Int,x$2: Boolean): Unit = ???
  def updateByte(x$1: String,x$2: Byte): Unit = ???
  def updateByte(x$1: Int,x$2: Byte): Unit = ???
  def updateBytes(x$1: String,x$2: Array[Byte]): Unit = ???
  def updateBytes(x$1: Int,x$2: Array[Byte]): Unit = ???
  def updateCharacterStream(x$1: String,x$2: java.io.Reader): Unit = ???
  def updateCharacterStream(x$1: Int,x$2: java.io.Reader): Unit = ???
  def updateCharacterStream(x$1: String,x$2: java.io.Reader,x$3: Long): Unit = ???
  def updateCharacterStream(x$1: Int,x$2: java.io.Reader,x$3: Long): Unit = ???
  def updateCharacterStream(x$1: String,x$2: java.io.Reader,x$3: Int): Unit = ???
  def updateCharacterStream(x$1: Int,x$2: java.io.Reader,x$3: Int): Unit = ???
  def updateClob(x$1: String,x$2: java.io.Reader): Unit = ???
  def updateClob(x$1: Int,x$2: java.io.Reader): Unit = ???
  def updateClob(x$1: String,x$2: java.io.Reader,x$3: Long): Unit = ???
  def updateClob(x$1: Int,x$2: java.io.Reader,x$3: Long): Unit = ???
  def updateClob(x$1: String,x$2: jdbc.Clob): Unit = ???
  def updateClob(x$1: Int,x$2: jdbc.Clob): Unit = ???
  def updateDate(x$1: String,x$2: jdbc.Date): Unit = ???
  def updateDate(x$1: Int,x$2: jdbc.Date): Unit = ???
  def updateDouble(x$1: String,x$2: Double): Unit = ???
  def updateDouble(x$1: Int,x$2: Double): Unit = ???
  def updateFloat(x$1: String,x$2: Float): Unit = ???
  def updateFloat(x$1: Int,x$2: Float): Unit = ???
  def updateInt(x$1: String,x$2: Int): Unit = ???
  def updateInt(x$1: Int,x$2: Int): Unit = ???
  def updateLong(x$1: String,x$2: Long): Unit = ???
  def updateLong(x$1: Int,x$2: Long): Unit = ???
  def updateNCharacterStream(x$1: String,x$2: java.io.Reader): Unit = ???
  def updateNCharacterStream(x$1: Int,x$2: java.io.Reader): Unit = ???
  def updateNCharacterStream(x$1: String,x$2: java.io.Reader,x$3: Long): Unit = ???
  def updateNCharacterStream(x$1: Int,x$2: java.io.Reader,x$3: Long): Unit = ???
  def updateNClob(x$1: String,x$2: java.io.Reader): Unit = ???
  def updateNClob(x$1: Int,x$2: java.io.Reader): Unit = ???
  def updateNClob(x$1: String,x$2: java.io.Reader,x$3: Long): Unit = ???
  def updateNClob(x$1: Int,x$2: java.io.Reader,x$3: Long): Unit = ???
  def updateNClob(x$1: String,x$2: jdbc.NClob): Unit = ???
  def updateNClob(x$1: Int,x$2: jdbc.NClob): Unit = ???
  def updateNString(x$1: String,x$2: String): Unit = ???
  def updateNString(x$1: Int,x$2: String): Unit = ???
  def updateNull(x$1: String): Unit = ???
  def updateNull(x$1: Int): Unit = ???
  def updateObject(x$1: String,x$2: Any): Unit = ???
  def updateObject(x$1: String,x$2: Any,x$3: Int): Unit = ???
  def updateObject(x$1: Int,x$2: Any): Unit = ???
  def updateObject(x$1: Int,x$2: Any,x$3: Int): Unit = ???
  def updateRef(x$1: String,x$2: jdbc.Ref): Unit = ???
  def updateRef(x$1: Int,x$2: jdbc.Ref): Unit = ???
  def updateRow(): Unit = ???
  def updateRowId(x$1: String,x$2: jdbc.RowId): Unit = ???
  def updateRowId(x$1: Int,x$2: jdbc.RowId): Unit = ???
  def updateSQLXML(x$1: String,x$2: jdbc.SQLXML): Unit = ???
  def updateSQLXML(x$1: Int,x$2: jdbc.SQLXML): Unit = ???
  def updateShort(x$1: String,x$2: Short): Unit = ???
  def updateShort(x$1: Int,x$2: Short): Unit = ???
  def updateString(x$1: String,x$2: String): Unit = ???
  def updateString(x$1: Int,x$2: String): Unit = ???
  def updateTime(x$1: String,x$2: jdbc.Time): Unit = ???
  def updateTime(x$1: Int,x$2: jdbc.Time): Unit = ???
  def updateTimestamp(x$1: String,x$2: jdbc.Timestamp): Unit = ???
  def updateTimestamp(x$1: Int,x$2: jdbc.Timestamp): Unit = ???
  
  def isWrapperFor(x$1: Class[_]): Boolean = ???
  def unwrap[T](x$1: Class[T]): T = ???
  
  def getMetaData(): jdbc.ResultSetMetaData = new jdbc.ResultSetMetaData {
    val PRECISION_NA = 0
    val SCALE_NA = 0
    
    def getColumnCount(): Int = columns.length
    
    // Note: column indexes are one-based
    
    def isAutoIncrement(column: Int): Boolean = false
    def isCaseSensitive(column: Int): Boolean = true
    def isSearchable(column: Int): Boolean = true
    def isCurrency(column: Int): Boolean = false
    def isNullable(column: Int): Int = jdbc.ResultSetMetaData.columnNullable
    def isSigned(column: Int): Boolean = false  // TODO
    def getColumnDisplaySize(column: Int): Int = 20  // TODO
    def getColumnLabel(column: Int): String = getColumnName(column)
    def getColumnName(column: Int): String = columns(column-1)
    def getSchemaName(column: Int): String = ""
    def getPrecision(column: Int): Int = PRECISION_NA
    def getScale(column: Int): Int = SCALE_NA
    def getTableName(column: Int): String = "" // TODO
    def getCatalogName(column: Int): String = "/"  // TODO
    def getColumnType(column: Int): Int = jdbc.Types.VARCHAR  // TODO
    def getColumnTypeName(column: Int): String = "Text"  // TODO
    def isReadOnly(column: Int): Boolean = true
    def isWritable(column: Int): Boolean = false
    def isDefinitelyWritable(column: Int): Boolean = false

    def getColumnClassName(column: Int): String = "java.lang.String"

    // Members declared in java.sql.Wrapper
    def isWrapperFor(column: Class[_]): Boolean = ???
    def unwrap[T](column: Class[T]): T = ???
  }
}
