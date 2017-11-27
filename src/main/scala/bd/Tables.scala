package bd

import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape


object TableQueryProductos {

  private[TableQueryProductos] final class Productos(tag: Tag) extends Table[(Int, String, Double, Int)](tag, "PRODUCTOS") {
    def id: Rep[Int] = column[Int]("PRODUCTO_ID", O.PrimaryKey)

    def nombre = column[String]("NOMBRE")

    def valor = column[Double]("VALOR")

    def proveedorID = column[Int]("PROVEEDOR_ID")

    def * : ProvenShape[(Int, String, Double, Int)] = (id, nombre, valor, proveedorID)

    def proveedor = foreignKey("PROVEEDOR_FK", proveedorID, TableQuery[Proveedores])(_.id)
  }

}

trait TableQueryProductos {

  import TableQueryProductos._

  val productos = TableQuery[Productos]

}

final class Proveedores(tag: Tag) extends Table[(Int, String)](tag, "PROVEEDORES") {
  def id = column[Int]("PROVEEDOR_ID", O.PrimaryKey)

  def nombre = column[String]("NOMBRE")

  def * : ProvenShape[(Int, String)] = (id, nombre)

}


  trait TableQueryProveedores {
    val proveedores = TableQuery[Proveedores]
  }