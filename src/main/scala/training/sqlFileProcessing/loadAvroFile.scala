package training.sqlFileProcessing

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object loadAvroFile {
  def main (args:Array[String]): Unit = {
    val prop = ConfigFactory.load()
    val envprop = prop.getConfig(args(0))
    val inputdir = envprop.getString("dev.input.base.dir")
    val config  = new SparkConf().setAppName("Load Avro Data")
    val sc = new SparkContext(config)
    val sqlContext = new SQLContext(sc)
    sqlContext.read.format("com.databricks.spark.avro")
                    .load(inputdir)
                    .registerTempTable("AVRO_DATA")
    val df = sqlContext.sql("select * from AVRO_DATA")
    df.show(10)

  }

}
