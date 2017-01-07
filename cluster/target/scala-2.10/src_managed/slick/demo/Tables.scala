package demo
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = CondMeteo.schema ++ DateTraffic.schema ++ PerioadaZi.schema ++ StareaDrumului.schema ++ Strazi.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table CondMeteo
   *  @param id Database column Id SqlType(INT), AutoInc, PrimaryKey
   *  @param descriere Database column Descriere SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
  case class CondMeteoRow(id: Int, descriere: Option[String] = None)
  /** GetResult implicit for fetching CondMeteoRow objects using plain SQL queries */
  implicit def GetResultCondMeteoRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[CondMeteoRow] = GR{
    prs => import prs._
    CondMeteoRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table cond_meteo. Objects of this class serve as prototypes for rows in queries. */
  class CondMeteo(_tableTag: Tag) extends Table[CondMeteoRow](_tableTag, "cond_meteo") {
    def * = (id, descriere) <> (CondMeteoRow.tupled, CondMeteoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), descriere).shaped.<>({r=>import r._; _1.map(_=> CondMeteoRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column Descriere SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val descriere: Rep[Option[String]] = column[Option[String]]("Descriere", O.Length(2147483647,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table CondMeteo */
  lazy val CondMeteo = new TableQuery(tag => new CondMeteo(tag))

  /** Entity class storing rows of table DateTraffic
   *  @param id Database column Id SqlType(INT), AutoInc, PrimaryKey
   *  @param idStrada Database column Id_strada SqlType(INT)
   *  @param dataOra Database column Data_ora SqlType(DATETIME)
   *  @param idCondMeteo Database column Id_cond_meteo SqlType(INT)
   *  @param idPerioadaZi Database column Id_perioada_zi SqlType(INT)
   *  @param nrMediuAutovehicule Database column Nr_mediu_autovehicule SqlType(FLOAT)
   *  @param vitezaMedieAutovehicule Database column Viteza_medie_autovehicule SqlType(FLOAT)
   *  @param idStareaDrumului Database column Id_starea_drumului SqlType(INT) */
  case class DateTrafficRow(id: Int, idStrada: Int, dataOra: java.sql.Timestamp, idCondMeteo: Int, idPerioadaZi: Int, nrMediuAutovehicule: Float, vitezaMedieAutovehicule: Float, idStareaDrumului: Int)
  /** GetResult implicit for fetching DateTrafficRow objects using plain SQL queries */
  implicit def GetResultDateTrafficRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[Float]): GR[DateTrafficRow] = GR{
    prs => import prs._
    DateTrafficRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp], <<[Int], <<[Int], <<[Float], <<[Float], <<[Int]))
  }
  /** Table description of table date_traffic. Objects of this class serve as prototypes for rows in queries. */
  class DateTraffic(_tableTag: Tag) extends Table[DateTrafficRow](_tableTag, "date_traffic") {
    def * = (id, idStrada, dataOra, idCondMeteo, idPerioadaZi, nrMediuAutovehicule, vitezaMedieAutovehicule, idStareaDrumului) <> (DateTrafficRow.tupled, DateTrafficRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(idStrada), Rep.Some(dataOra), Rep.Some(idCondMeteo), Rep.Some(idPerioadaZi), Rep.Some(nrMediuAutovehicule), Rep.Some(vitezaMedieAutovehicule), Rep.Some(idStareaDrumului)).shaped.<>({r=>import r._; _1.map(_=> DateTrafficRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column Id_strada SqlType(INT) */
    val idStrada: Rep[Int] = column[Int]("Id_strada")
    /** Database column Data_ora SqlType(DATETIME) */
    val dataOra: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("Data_ora")
    /** Database column Id_cond_meteo SqlType(INT) */
    val idCondMeteo: Rep[Int] = column[Int]("Id_cond_meteo")
    /** Database column Id_perioada_zi SqlType(INT) */
    val idPerioadaZi: Rep[Int] = column[Int]("Id_perioada_zi")
    /** Database column Nr_mediu_autovehicule SqlType(FLOAT) */
    val nrMediuAutovehicule: Rep[Float] = column[Float]("Nr_mediu_autovehicule")
    /** Database column Viteza_medie_autovehicule SqlType(FLOAT) */
    val vitezaMedieAutovehicule: Rep[Float] = column[Float]("Viteza_medie_autovehicule")
    /** Database column Id_starea_drumului SqlType(INT) */
    val idStareaDrumului: Rep[Int] = column[Int]("Id_starea_drumului")

    /** Foreign key referencing CondMeteo (database name FK_to_cond_meteo) */
    lazy val condMeteoFk = foreignKey("FK_to_cond_meteo", idCondMeteo, CondMeteo)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing PerioadaZi (database name FK_to_perioada_zi) */
    lazy val perioadaZiFk = foreignKey("FK_to_perioada_zi", idPerioadaZi, PerioadaZi)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing StareaDrumului (database name FK_to_starea_drumului) */
    lazy val stareaDrumuluiFk = foreignKey("FK_to_starea_drumului", idStareaDrumului, StareaDrumului)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Strazi (database name FK_to_strazi) */
    lazy val straziFk = foreignKey("FK_to_strazi", idStrada, Strazi)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table DateTraffic */
  lazy val DateTraffic = new TableQuery(tag => new DateTraffic(tag))

  /** Entity class storing rows of table PerioadaZi
   *  @param id Database column Id SqlType(INT), AutoInc, PrimaryKey
   *  @param descriere Database column Descriere SqlType(LONGTEXT), Length(2147483647,true) */
  case class PerioadaZiRow(id: Int, descriere: String)
  /** GetResult implicit for fetching PerioadaZiRow objects using plain SQL queries */
  implicit def GetResultPerioadaZiRow(implicit e0: GR[Int], e1: GR[String]): GR[PerioadaZiRow] = GR{
    prs => import prs._
    PerioadaZiRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table perioada_zi. Objects of this class serve as prototypes for rows in queries. */
  class PerioadaZi(_tableTag: Tag) extends Table[PerioadaZiRow](_tableTag, "perioada_zi") {
    def * = (id, descriere) <> (PerioadaZiRow.tupled, PerioadaZiRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(descriere)).shaped.<>({r=>import r._; _1.map(_=> PerioadaZiRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column Descriere SqlType(LONGTEXT), Length(2147483647,true) */
    val descriere: Rep[String] = column[String]("Descriere", O.Length(2147483647,varying=true))
  }
  /** Collection-like TableQuery object for table PerioadaZi */
  lazy val PerioadaZi = new TableQuery(tag => new PerioadaZi(tag))

  /** Entity class storing rows of table StareaDrumului
   *  @param id Database column Id SqlType(INT), AutoInc, PrimaryKey
   *  @param descriere Database column Descriere SqlType(LONGTEXT), Length(2147483647,true) */
  case class StareaDrumuluiRow(id: Int, descriere: String)
  /** GetResult implicit for fetching StareaDrumuluiRow objects using plain SQL queries */
  implicit def GetResultStareaDrumuluiRow(implicit e0: GR[Int], e1: GR[String]): GR[StareaDrumuluiRow] = GR{
    prs => import prs._
    StareaDrumuluiRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table starea_drumului. Objects of this class serve as prototypes for rows in queries. */
  class StareaDrumului(_tableTag: Tag) extends Table[StareaDrumuluiRow](_tableTag, "starea_drumului") {
    def * = (id, descriere) <> (StareaDrumuluiRow.tupled, StareaDrumuluiRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(descriere)).shaped.<>({r=>import r._; _1.map(_=> StareaDrumuluiRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column Descriere SqlType(LONGTEXT), Length(2147483647,true) */
    val descriere: Rep[String] = column[String]("Descriere", O.Length(2147483647,varying=true))
  }
  /** Collection-like TableQuery object for table StareaDrumului */
  lazy val StareaDrumului = new TableQuery(tag => new StareaDrumului(tag))

  /** Entity class storing rows of table Strazi
   *  @param id Database column Id SqlType(INT), AutoInc, PrimaryKey
   *  @param localitatea Database column Localitatea SqlType(LONGTEXT), Length(2147483647,true)
   *  @param strada Database column Strada SqlType(LONGTEXT), Length(2147483647,true)
   *  @param tara Database column Tara SqlType(LONGTEXT), Length(2147483647,true)
   *  @param nrBenziCirculatie Database column Nr_benzi_circulatie SqlType(FLOAT)
   *  @param latitudineGps Database column Latitudine_GPS SqlType(LONGTEXT), Length(2147483647,true), Default(None)
   *  @param longitudineGps Database column Longitudine_GPS SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
  case class StraziRow(id: Int, localitatea: String, strada: String, tara: String, nrBenziCirculatie: Float, latitudineGps: Option[String] = None, longitudineGps: Option[String] = None)
  /** GetResult implicit for fetching StraziRow objects using plain SQL queries */
  implicit def GetResultStraziRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Float], e3: GR[Option[String]]): GR[StraziRow] = GR{
    prs => import prs._
    StraziRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[Float], <<?[String], <<?[String]))
  }
  /** Table description of table strazi. Objects of this class serve as prototypes for rows in queries. */
  class Strazi(_tableTag: Tag) extends Table[StraziRow](_tableTag, "strazi") {
    def * = (id, localitatea, strada, tara, nrBenziCirculatie, latitudineGps, longitudineGps) <> (StraziRow.tupled, StraziRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(localitatea), Rep.Some(strada), Rep.Some(tara), Rep.Some(nrBenziCirculatie), latitudineGps, longitudineGps).shaped.<>({r=>import r._; _1.map(_=> StraziRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column Localitatea SqlType(LONGTEXT), Length(2147483647,true) */
    val localitatea: Rep[String] = column[String]("Localitatea", O.Length(2147483647,varying=true))
    /** Database column Strada SqlType(LONGTEXT), Length(2147483647,true) */
    val strada: Rep[String] = column[String]("Strada", O.Length(2147483647,varying=true))
    /** Database column Tara SqlType(LONGTEXT), Length(2147483647,true) */
    val tara: Rep[String] = column[String]("Tara", O.Length(2147483647,varying=true))
    /** Database column Nr_benzi_circulatie SqlType(FLOAT) */
    val nrBenziCirculatie: Rep[Float] = column[Float]("Nr_benzi_circulatie")
    /** Database column Latitudine_GPS SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val latitudineGps: Rep[Option[String]] = column[Option[String]]("Latitudine_GPS", O.Length(2147483647,varying=true), O.Default(None))
    /** Database column Longitudine_GPS SqlType(LONGTEXT), Length(2147483647,true), Default(None) */
    val longitudineGps: Rep[Option[String]] = column[Option[String]]("Longitudine_GPS", O.Length(2147483647,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Strazi */
  lazy val Strazi = new TableQuery(tag => new Strazi(tag))
}
