package consultas

import bd.{TableQueryProductos, TableQueryProveedores}
import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContext.Implicits.global


object Consultas extends App with  TableQueryProveedores with TableQueryProductos {


  val db = Database.forConfig("h2mem1")

  val setupProveedores = proveedores.schema.create

  db.run(setupProveedores).flatMap { _ =>

    val queryInset = proveedores ++= Seq(
      (1, "Maicito"),
      (2, "Roa")
    )

    db.run(queryInset).map(_ => println("Insertado"))

  }.flatMap { _ =>
    db.run(proveedores.result).map(rows => println(s"Proveedores: $rows"))
  }.flatMap { _ =>

    val setupActionProductos = DBIO.seq(
      productos.schema.create,
      productos ++= Seq(
        (1, "Arroz", 5200, 2),
        (2, "Maiz", 2201, 1)
      )
    )

    db.run(setupActionProductos)


  }.flatMap { _ =>
    db.run(productos.filter(_.valor > 3000.0).result.map(println))
  }.onFailure {
    case t => println("Ha ocurrido un error: " + t.getMessage)
  }


}
