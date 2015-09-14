




	/**
	  * 求取两个时间的秒数的差值
      * time1 为较小时间 格式： yyyy-MM-dd HH:mm:ss
      * time2 为较大时间 格式： yyyy-MM-dd HH:mm:ss
      */
  function getSec(time1, time2){
    var now = new Date();
    var a = time1.split(" ")[0].split("-");
    now.setFullYear(parseInt(a[0]), parseInt(a[1]) - 1, parseInt(a[2]));
    a = time1.split(" ")[1].split(":");
    now.setHours(parseInt(a[0]), parseInt(a[1]), parseInt(a[2]), 0);
    
    var end = new Date();
    a = time2.split(" ")[0].split("-");
    end.setFullYear(parseInt(a[0]), parseInt(a[1]) - 1, parseInt(a[2]));
    a = time2.split(" ")[1].split(":");
    end.setHours(parseInt(a[0]), parseInt(a[1]), parseInt(a[2]), 0);
    
    var sec = (end.getTime() - now.getTime()) / 1000;
    return sec;
 }




