import org.apache.spark.{ SparkContext, SparkConf }

object WordCount {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("WordCount").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val inputFile = sc.textFile("file:///home/hadoop/Documents/Spark_Test_Files/WordCount").cache()
    val counts = inputFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
    counts.saveAsTextFile("file:///home/hadoop/Documents/Spark_Test_Files/output_wordcount")
    System.exit(0)
  }

}