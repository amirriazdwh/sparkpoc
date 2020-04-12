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
//
//--conf PROP=VALUE           Arbitrary Spark configuration property.
//--properties-file FILE      Path to a file from which to load extra properties. If not
//specified, this will look for conf/spark-defaults.conf.

//import java.util.Base64 // for base64
//import java.nio.charset.StandardCharsets // for base64
//val properties = new java.util.Properties()
//properties.put("driver", "com.mysql.jdbc.Driver")
//properties.put("url", "jdbc:mysql://mysql-host:3306")
//properties.put("user", "test_user")
//val password = new String(Base64.getDecoder().decode(spark.conf.get("spark.jdbc.b64password")), StandardCharsets.UTF_8)
//properties.put("password", password)
//val models = spark.read.jdbc(properties.get("url").toString, "ml_models", properties)

