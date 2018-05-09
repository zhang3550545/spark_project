/**
  * Created by zhang on 2018/5/9.
  */
object Util {

  def ip2num(ip: String): Long = {
    val split = ip.split("[.]")
    var ipNum = 0L
    for (i <- split) {
      ipNum = i.toLong | ipNum << 8
    }
    ipNum
  }

  /**
    * 通过ip在广播变量中查找对应的地理位置，采用2分查找的方式
    *
    * @param ipNum ip数字
    * @param value 广播变量中的值
    */
  def searchLocation(ipNum: Long, value: Array[(String, String, String, String, String)]): Int = {
    var start = 0
    var end = value.length
    while (start <= end) {
      var middle = (start + end) / 2
      if (value(middle)._1.toLong <= ipNum && ipNum <= value(middle)._2.toLong) {
        return middle
      }
      if (value(middle)._1.toLong > ipNum) {
        end = middle
      }
      if (value(middle)._2.toLong < ipNum) {
        start = middle
      }
    }
    -1
  }
}
