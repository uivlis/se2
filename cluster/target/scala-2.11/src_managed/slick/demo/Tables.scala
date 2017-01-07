package demo
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(CondMeteo.schema, Cotitures.schema, DateTraffic.schema, Intersections.schema, PerioadaZi.schema, StareaDrumului.schema, Strazi.schema).reduceLeft(_ ++ _)
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
  class CondMeteo(_tableTag: Tag) extends profile.api.Table[CondMeteoRow](_tableTag, Some("se"), "cond_meteo") {
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

  /** Entity class storing rows of table Cotitures
   *  @param uniqueCotitureId Database column Unique_cotiture_id SqlType(INT), AutoInc, PrimaryKey
   *  @param latitudineGps Database column Latitudine_GPS SqlType(DOUBLE)
   *  @param longitudineGps Database column Longitudine_GPS SqlType(DOUBLE) */
  case class CotituresRow(uniqueCotitureId: Int, latitudineGps: Double, longitudineGps: Double)
  /** GetResult implicit for fetching CotituresRow objects using plain SQL queries */
  implicit def GetResultCotituresRow(implicit e0: GR[Int], e1: GR[Double]): GR[CotituresRow] = GR{
    prs => import prs._
    CotituresRow.tupled((<<[Int], <<[Double], <<[Double]))
  }
  /** Table description of table cotitures. Objects of this class serve as prototypes for rows in queries. */
  class Cotitures(_tableTag: Tag) extends profile.api.Table[CotituresRow](_tableTag, Some("se"), "cotitures") {
    def * = (uniqueCotitureId, latitudineGps, longitudineGps) <> (CotituresRow.tupled, CotituresRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uniqueCotitureId), Rep.Some(latitudineGps), Rep.Some(longitudineGps)).shaped.<>({r=>import r._; _1.map(_=> CotituresRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Unique_cotiture_id SqlType(INT), AutoInc, PrimaryKey */
    val uniqueCotitureId: Rep[Int] = column[Int]("Unique_cotiture_id", O.AutoInc, O.PrimaryKey)
    /** Database column Latitudine_GPS SqlType(DOUBLE) */
    val latitudineGps: Rep[Double] = column[Double]("Latitudine_GPS")
    /** Database column Longitudine_GPS SqlType(DOUBLE) */
    val longitudineGps: Rep[Double] = column[Double]("Longitudine_GPS")
  }
  /** Collection-like TableQuery object for table Cotitures */
  lazy val Cotitures = new TableQuery(tag => new Cotitures(tag))

  /** Entity class storing rows of table DateTraffic
   *  @param id Database column Id SqlType(INT), AutoInc, PrimaryKey
   *  @param idStrada Database column Id_strada SqlType(INT)
   *  @param dataOra Database column Data_ora SqlType(DATETIME)
   *  @param idCondMeteo Database column Id_cond_meteo SqlType(INT)
   *  @param idPerioadaZi Database column Id_perioada_zi SqlType(INT)
   *  @param nrMediuAutovehicule Database column Nr_mediu_autovehicule SqlType(DOUBLE)
   *  @param vitezaMedieAutovehicule Database column Viteza_medie_autovehicule SqlType(DOUBLE)
   *  @param idStareaDrumului Database column Id_starea_drumului SqlType(INT) */
  case class DateTrafficRow(id: Int, idStrada: Int, dataOra: java.sql.Timestamp, idCondMeteo: Int, idPerioadaZi: Int, nrMediuAutovehicule: Double, vitezaMedieAutovehicule: Double, idStareaDrumului: Int)
  /** GetResult implicit for fetching DateTrafficRow objects using plain SQL queries */
  implicit def GetResultDateTrafficRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[Double]): GR[DateTrafficRow] = GR{
    prs => import prs._
    DateTrafficRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp], <<[Int], <<[Int], <<[Double], <<[Double], <<[Int]))
  }
  /** Table description of table date_traffic. Objects of this class serve as prototypes for rows in queries. */
  class DateTraffic(_tableTag: Tag) extends profile.api.Table[DateTrafficRow](_tableTag, Some("se"), "date_traffic") {
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
    /** Database column Nr_mediu_autovehicule SqlType(DOUBLE) */
    val nrMediuAutovehicule: Rep[Double] = column[Double]("Nr_mediu_autovehicule")
    /** Database column Viteza_medie_autovehicule SqlType(DOUBLE) */
    val vitezaMedieAutovehicule: Rep[Double] = column[Double]("Viteza_medie_autovehicule")
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

  /** Entity class storing rows of table Intersections
   *  @param id Database column Id SqlType(INT), AutoInc, PrimaryKey
   *  @param idStrada1 Database column Id_strada1 SqlType(INT)
   *  @param idStrada2 Database column Id_strada2 SqlType(INT)
   *  @param latitudineGps Database column Latitudine_GPS SqlType(DOUBLE), Default(None)
   *  @param longitudineGps Database column Longitudine_GPS SqlType(DOUBLE), Default(None)
   *  @param uniqueIntersectionId Database column Unique_intersection_id SqlType(INT) */
  case class IntersectionsRow(id: Int, idStrada1: Int, idStrada2: Int, latitudineGps: Option[Double] = None, longitudineGps: Option[Double] = None, uniqueIntersectionId: Int)
  /** GetResult implicit for fetching IntersectionsRow objects using plain SQL queries */
  implicit def GetResultIntersectionsRow(implicit e0: GR[Int], e1: GR[Option[Double]]): GR[IntersectionsRow] = GR{
    prs => import prs._
    IntersectionsRow.tupled((<<[Int], <<[Int], <<[Int], <<?[Double], <<?[Double], <<[Int]))
  }
  /** Table description of table intersections. Objects of this class serve as prototypes for rows in queries. */
  class Intersections(_tableTag: Tag) extends profile.api.Table[IntersectionsRow](_tableTag, Some("se"), "intersections") {
    def * = (id, idStrada1, idStrada2, latitudineGps, longitudineGps, uniqueIntersectionId) <> (IntersectionsRow.tupled, IntersectionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(idStrada1), Rep.Some(idStrada2), latitudineGps, longitudineGps, Rep.Some(uniqueIntersectionId)).shaped.<>({r=>import r._; _1.map(_=> IntersectionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column Id_strada1 SqlType(INT) */
    val idStrada1: Rep[Int] = column[Int]("Id_strada1")
    /** Database column Id_strada2 SqlType(INT) */
    val idStrada2: Rep[Int] = column[Int]("Id_strada2")
    /** Database column Latitudine_GPS SqlType(DOUBLE), Default(None) */
    val latitudineGps: Rep[Option[Double]] = column[Option[Double]]("Latitudine_GPS", O.Default(None))
    /** Database column Longitudine_GPS SqlType(DOUBLE), Default(None) */
    val longitudineGps: Rep[Option[Double]] = column[Option[Double]]("Longitudine_GPS", O.Default(None))
    /** Database column Unique_intersection_id SqlType(INT) */
    val uniqueIntersectionId: Rep[Int] = column[Int]("Unique_intersection_id")

    /** Foreign key referencing Cotitures (database name FK_UCot) */
    lazy val cotituresFk = foreignKey("FK_UCot", uniqueIntersectionId, Cotitures)(r => r.uniqueCotitureId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Strazi (database name FK_ID_S1) */
    lazy val straziFk2 = foreignKey("FK_ID_S1", idStrada1, Strazi)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Strazi (database name FK_ID_S2) */
    lazy val straziFk3 = foreignKey("FK_ID_S2", idStrada2, Strazi)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Intersections */
  lazy val Intersections = new TableQuery(tag => new Intersections(tag))

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
  class PerioadaZi(_tableTag: Tag) extends profile.api.Table[PerioadaZiRow](_tableTag, Some("se"), "perioada_zi") {
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
  class StareaDrumului(_tableTag: Tag) extends profile.api.Table[StareaDrumuluiRow](_tableTag, Some("se"), "starea_drumului") {
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
   *  @param nrBenziCirculatie Database column Nr_benzi_circulatie SqlType(INT)
   *  @param startLatitudineGps Database column Start_Latitudine_GPS SqlType(DOUBLE), Default(None)
   *  @param startLongitudineGps Database column Start_Longitudine_GPS SqlType(DOUBLE), Default(None)
   *  @param stopLatitudineGps Database column Stop_Latitudine_GPS SqlType(DOUBLE), Default(None)
   *  @param stopLongitudineGps Database column Stop_Longitudine_GPS SqlType(DOUBLE), Default(None) */
  case class StraziRow(id: Int, localitatea: String, strada: String, tara: String, nrBenziCirculatie: Int, startLatitudineGps: Option[Double] = None, startLongitudineGps: Option[Double] = None, stopLatitudineGps: Option[Double] = None, stopLongitudineGps: Option[Double] = None)
  /** GetResult implicit for fetching StraziRow objects using plain SQL queries */
  implicit def GetResultStraziRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Double]]): GR[StraziRow] = GR{
    prs => import prs._
    StraziRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[Int], <<?[Double], <<?[Double], <<?[Double], <<?[Double]))
  }
  /** Table description of table strazi. Objects of this class serve as prototypes for rows in queries. */
  class Strazi(_tableTag: Tag) extends profile.api.Table[StraziRow](_tableTag, Some("se"), "strazi") {
    def * = (id, localitatea, strada, tara, nrBenziCirculatie, startLatitudineGps, startLongitudineGps, stopLatitudineGps, stopLongitudineGps) <> (StraziRow.tupled, StraziRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(localitatea), Rep.Some(strada), Rep.Some(tara), Rep.Some(nrBenziCirculatie), startLatitudineGps, startLongitudineGps, stopLatitudineGps, stopLongitudineGps).shaped.<>({r=>import r._; _1.map(_=> StraziRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column Localitatea SqlType(LONGTEXT), Length(2147483647,true) */
    val localitatea: Rep[String] = column[String]("Localitatea", O.Length(2147483647,varying=true))
    /** Database column Strada SqlType(LONGTEXT), Length(2147483647,true) */
    val strada: Rep[String] = column[String]("Strada", O.Length(2147483647,varying=true))
    /** Database column Tara SqlType(LONGTEXT), Length(2147483647,true) */
    val tara: Rep[String] = column[String]("Tara", O.Length(2147483647,varying=true))
    /** Database column Nr_benzi_circulatie SqlType(INT) */
    val nrBenziCirculatie: Rep[Int] = column[Int]("Nr_benzi_circulatie")
    /** Database column Start_Latitudine_GPS SqlType(DOUBLE), Default(None) */
    val startLatitudineGps: Rep[Option[Double]] = column[Option[Double]]("Start_Latitudine_GPS", O.Default(None))
    /** Database column Start_Longitudine_GPS SqlType(DOUBLE), Default(None) */
    val startLongitudineGps: Rep[Option[Double]] = column[Option[Double]]("Start_Longitudine_GPS", O.Default(None))
    /** Database column Stop_Latitudine_GPS SqlType(DOUBLE), Default(None) */
    val stopLatitudineGps: Rep[Option[Double]] = column[Option[Double]]("Stop_Latitudine_GPS", O.Default(None))
    /** Database column Stop_Longitudine_GPS SqlType(DOUBLE), Default(None) */
    val stopLongitudineGps: Rep[Option[Double]] = column[Option[Double]]("Stop_Longitudine_GPS", O.Default(None))
  }
  /** Collection-like TableQuery object for table Strazi */
  lazy val Strazi = new TableQuery(tag => new Strazi(tag))
}
