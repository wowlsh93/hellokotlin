
package sqlparser
import sqlparser.lexer.ParseFailure
import sqlparser.lexer.Scanner
import sqlparser.parser.Parser
import sqlparser.table.InterimTable
import sqlparser.table.Table
import sqlparser.table.TableContext
import sqlparser.table.TableFactory
import java.io.IOException
import java.text.NumberFormat

class LedgerQL {

  val parser = Parser(TableContext())

  fun setTableSchema(tablename: String, columns: List<String>){
    parser.createTable(tablename,columns)
  }

  fun addRow(tablename: String, row: List<String>) {
    parser.addRow(tablename,row)
  }

  fun sql(expression: String): Table?{
    return parser.execute(expression)
  }

  fun test() {

    setTableSchema("client", listOf("id","name","couponid","count"))
    setTableSchema("coupon", listOf("couponid", "kind", "quantity"))

    addRow("client", listOf("client1","juli", "coupon1", "100"))
    addRow("client", listOf("client2","hama", "coupon2", "200"))
    addRow("client", listOf("client3","john", "coupon3", "300"))
    addRow("client", listOf("client4","paul", "coupon4", "400"))

    addRow("coupon", listOf("coupon1","tv", "100"))
    addRow("coupon", listOf("coupon2","tv", "200"))
    addRow("coupon", listOf("coupon3","computer", "300"))


    val cursor = sql("select id from client where id = 'client1' and name = 'juli'" )?.rows()
   // val cursor = sql("select * from client where count between '200' and '300' " )?.rows()

    while(cursor!!.advance()){
        val iterator = cursor.columns()
        while(iterator.hasNext()){
          print(iterator.next() + ", ")
        }

        println("")
    }
  }

}

