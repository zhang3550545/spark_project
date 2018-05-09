from pyspark import SparkContext, SparkConf
import time


def f(ip):
    ipNum = ip2num(ip[0])
    index = searchLocation(ipNum, values)
    value = values[index]
    return ((value[3], value[4]), 1)


def ip2num(ip):
    split = ip.split(".")
    ipNum = 0
    for i in split:
        ipNum = int(i) | ipNum << 8
    return ipNum


def searchLocation(ipNum, infos):
    start = 0
    end = len(infos)
    while (start <= end):
        middle = int((start + end) / 2)
        if (int(infos[middle][0]) <= ipNum and int(infos[middle][1]) >= ipNum):
            return middle
        elif (int(infos[middle][0]) > ipNum):
            end = middle
        elif (int(infos[middle][1]) < ipNum):
            start = middle


if __name__ == '__main__':
    conf = SparkConf().setAppName("py_iplocation").setMaster("local")
    sc = SparkContext(conf=conf)

    rdd1 = sc.textFile("file:///D:/workspacescala/sparkdemo/iplocation/ip.txt").map(lambda x: x.split("|")).map(
        lambda x: (
            x[2], x[3], x[4] + "-" + x[5] + "-" + x[6] + "-" + x[7] + "-" + x[8] + "-" + x[9], x[13], x[14])).collect()

    broadcastData = sc.broadcast(rdd1)
    values = broadcastData.value

    rdd2 = sc.textFile("file:///D:/workspacescala/sparkdemo/iplocation/20090121000132.394251.http.format").map(
        lambda x: x.split("|")).map((lambda x: (x[1], 1)))

    rdd2.map(f).foreach(print)
