import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zhang on 2018/5/9.
  */
object IpLocation {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("iplocation").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rddList = sc.textFile("file:///D:/workspacescala/sparkdemo/iplocation/ip.txt").map(_.split("\\|")).
      map(x => (x(2), x(3), x(4) + "-" + x(5) + "-" + x(6) + "-" + x(7) + "-" + x(8) + "-" + x(9), x(13), x(14))).collect()

    // 广播变量
    val broadcast = sc.broadcast(rddList)
    val values = broadcast.value

    val rddIps = sc.textFile("file:///D:/workspacescala/sparkdemo/iplocation/20090121000132.394251.http.format").map(_.split("\\|")).map(_ (1))
    //获取ip
    val result = rddIps.mapPartitions(iter => {
      iter.map(ip => {
        // 将 ip 地址转成 long数值
        val ipNum = Util.ip2num(ip)
        // 通过2分查找法，找到对应的索引
        val index = Util.searchLocation(ipNum, values)
        // 通过索引获取对应的值
        val value = values(index)
        // 返回一个元组，类型（（lat,lng）,1）
        ((value._4, value._5), 1)
      })
    }).reduceByKey(_ + _).collect()

    result.foreach(println)
    sc.stop()
  }
}
