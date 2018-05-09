def searchLocation(ipNum, infos):
    start = 0
    end = len(infos)
    while (start <= end):
        print("-----------")
        middle = int((start + end) / 2)
        print(middle)
        print(ipNum)
        print("==========")
        if (int(infos[middle]) <= ipNum and int(infos[middle]) >= ipNum):
            print("成功找到")
            return middle
        if (int(infos[middle]) > ipNum):
            end = middle
        if (int(infos[middle]) < ipNum):
            start = middle

# todo 二分查找测试
if __name__ == '__main__':
    list = range(1, 1000)
    searchLocation(679, list)
