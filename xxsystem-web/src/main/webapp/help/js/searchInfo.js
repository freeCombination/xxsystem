var skArr = new Array();
var sfi   = new Array();
skArr[1]  = ["0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r","s","t","u","v","w","x","y","z"," ","®","‑","”","一","上","下","不","与","业","东","个","中","临","为","主","义","之","也","了","二","于","些","人","介","从","代","以","件","任","企","会","传","位","低","体","何","作","使","例","供","侧","侯","便","保","信","修","值","做","停","入","全","公","关","其","具","典","内","册","再","写","况","出","击","分","切","列","则","创","初","删","利","别","到","制","刷","前","剔","办","功","加","务","动","勾","包","化","区","单","即","历","参","及","发","取","变","叠","口","可","台","史","右","叶","号","司","各","合","同","名","后","否","启","员","和","器","回","围","图","在","地","址","块","型","域","基","填","境","增","处","备","复","多","够","大","好","如","始","姓","子","字","存","安","完","定","实","审","客","容","密","对","导","封","将","尔","就","层","展","属","工","左","己","已","带","平","并","庆","序","应","建","开","弃","式","引","弹","强","当","录","形","往","待","心","必","志","忘","快","态","性","息","悉","情","成","或","户","所","手","打","扭","批","技","折","护","拉","择","括","持","指","按","挥","换","据","授","排","接","控","推","描","提","操","支","收","改","放","效","数","文","料","新","方","无","日","时","明","是","显","景","暂","更","最","有","服","本","术","机","权","李","条","来","板","构","析","果","架","某","查","标","栏","树","根","格","框","档","桥","模","次","正","此","步","毕","永","法","注","流","浏","消","涉","添","清","源","满","点","然","照","熟","版","特","状","率","环","现","理","用","由","界","登","白","的","监","盘","目","直","相","看","短","码","础","硬","确","示","禁","种","称","程","空","窗","章","端","符","第","等","管","箱","类","糊","系","级","组","细","织","绍","经","结","绘","络","统","维","编","网","置","群","考","者","职","背","能","自","至","色","节","若","英","范","荐","菜","行","表","被","装","要","见","规","览","角","解","言","认","记","设","证","识","话","询","该","详","误","说","请","调","责","质","资","起","超","足","车","轨","软","载","辑","输","边","过","运","返","还","这","进","连","述","迹","适","选","途","通","邮","部","都","配","里","重","量","金","针","钮","锁","错","键","门","闭","问","间","际","限","除","集","需","面","页","顶","项","验","高","鼠","；"]; 
sfi[1] = [[0,7,8,12,24,26,35,45,56,66,71,82,92],[0,7,8,12,14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,36,40,45,48,56,57,66,69,72,75,80,81,82,83,84,85,87,93,95,97,98,99,101],[0,7,8,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,82,84,86,87,88,89,90,91,94,99,100,101],[0,18,19,22,27,29,34,38,39,40,41,42,43,44,45,46,48,49,50,51,52,53,54,58,59,66,71,73,77,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,102],[0,7,14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102],[0,20,21,22,29,36,40,44,50,52,55,56,57,58,59,60,61,62,63,64,66,67,69,70,71,73,77,85,87,91,98],[7,8,21,22,30,37,41,45,51,53,61,65,66,67,69,71,72,73,75,76,77,78,80,81,82,83,88,99],[0,21,23,32,38,42,46,52,54,62,68,69,70,71,72,73,78,82,83,84,85,86,87,88,89,90,91,101],[0,8,22,24,33,43,53,63,70,74,75,76,77,78,80,90,92,93,94,95,96,98,99,101,102],[0,23,25,34,44,54,64,71,81,82,83,91],[8,40,66],[0,40,66,88,102],[0,7,8,23,24,25],[0,8,20],[8,14,20,23,24,25,40],[0],[0,7,8],[0,7],[8,14,20],[0],[0],[8,14,23,24,25],[7,8],[0,8,14,88,102],[8,14],[0,7,14,20,40,88,102],[8,14,20,40],[0,8,88,102],[8,14,40,66],[7],[0,8,12],[8],[14,23,24,25],[0],[7],[0,4,5,7,11,39,41],[7],[14,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,35,36,37,38,40,41,42,43,44,45,46,48,49,50,51,52,53,54,56,57,58,59,60,61,62,63,64,66,69,70,71,72,73,75,76,77,78,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,98,99,101,102],[14,20,22,23,24,25,26,27,28,29,35,36,37,38,40,41,42,43,44,45,51,52,53,54,59,61,62,63,64,66,67,69,70,71,72,73,77,79,80,82,83,86,87,88,89,92,94,96],[19,20,21,23,34,35,39,40,41,50,51,58,59,60,61,66,69,76,77,78,87,93,96],[17,20,21,22,23,24,25,26,27,28,29,32,35,36,37,38,40,41,42,43,48,51,52,53,54,56,59,60,61,62,63,66,67,69,70,71,72,77,78,82,87,88,89,91],[16,17,20,21,24,25,30,31,32,35,36,39,40,47,48,51,52,55,56,59,60,65,66,68,69,73,74,77,78,84,87],[26,41,61,70,92,94],[0,11,23],[31,92],[0],[0,2,18,19,33,34,36,39,41,49,50,52,57,58,75,76,96],[0,2,16,20,22,23,24,26,28,29,31,35,37,38,40,41,43,44,45,46,47,51,54,55,59,61,63,64,65,66,67,68,69,70,72,73,74,77,79,82,83,85,87,88,90,91,93,95,96],[30],[2,22,23,24,25,26,27,28,29,30,37,38,41,42,43,44,46,53,54,61,62,63,64,66,67,70,71,72,73,82,83,88,89,96],[2,14,92,94,96],[0,4,92,93],[93],[66],[2,25,96,100],[20,21,60,78],[22,92,94],[20],[0,2,16,96],[92,94],[28,29,38,43,44,45,46,54,63,67,72,73,85,90],[12,61],[35,40,46,51,59,66,69,77,80,81,87,96],[0,2,7,8,9,21,23,24,25,44,60,73,78,91],[0,96,97,98,100,102],[31],[21,30,36,52,60,66,78],[23],[31],[7],[20,27,35,36,40,41,42,45,46,51,52,53,59,62,69,71,77,87,89],[0],[0,2,13,16,20,22,23,24,25,26,27,28,29,30,35,36,37,38,40,41,42,43,44,45,46,51,52,53,54,59,61,62,63,64,66,67,69,70,71,72,73,77,82,83,84,85,87,88,89,90,92,94],[0,2,30,80],[92,93,94,95,96],[23,24,25,41,96],[66],[0],[2,80],[24,25,26,27,36,37,41,42,52,53,61,62,66,70,71,82,83,88,89],[0,3,17,18,20,23,24,26,27,28,32,33,35,36,37,38,40,41,42,43,44,49,51,52,53,54,57,59,61,62,63,66,69,70,71,72,73,75,77,82,83,84,85,87,88,89,90],[0,27,37,42,53,62,71,83,86,89,92,94],[69,70],[25,92,94],[30],[14,16,18,20,21,23,24,25,26,31,33,35,39,40,41,47,49,51,55,57,59,60,61,65,68,69,70,74,77,78,79,80,82,86,87,88,91,92,94,96],[18,33,49,57,75],[0,3,14],[17,23,26,27,32,36,37,41,42,48,52,53,56,61,62,66,70,71,82,83,88,89],[2,17,23,32,36,40,48,52,56],[20,27,35,36,40,41,42,45,46,51,52,53,59,62,69,71,77,81,87,89,91,93,95],[68,69,70,71,72,73],[0,7,14,21,23,31,39,47,55,60,64,65,68,74,78],[0,2],[46],[0,2,26,36,44,52,61,70,73,82,83,88],[95],[21,22,23,24,25,26,27,28,29,36,37,38,41,42,43,52,53,54,60,61,62,63,64,66,67,70,71,72,78,82,83,88,89],[14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,82,83,86,87,88,89,91,92,93,94,95,96],[3,20,35,40,51,59,64,66,69,77,79,80,81,82,83,84,85,87],[66],[20,21,22,23,24,25,26,27,28,29,35,36,37,38,40,41,42,43,44,51,52,53,54,59,60,61,62,63,66,67,69,70,71,72,73,77,78,87,88,89,91],[0,12,14,23,24,25,36,52,66],[0],[0,10,11,22],[28,38,43,45,46,54,63,66,67,72,85,86,90,92,94],[2],[20],[16,18,19,20,23,24,25,29,31,33,34,35,39,40,45,46,47,49,50,51,55,57,58,59,65,66,68,69,74,76,77,87],[66,92,94],[20,35,40,51,59,69,77,81,87],[17,20,21,27,28,32,35,37,38,40,42,43,48,51,53,54,56,59,62,63,66,67,69,71,72,77,87,89],[67],[96,97,100],[0,2,13,16,17,20,22,24,26,28,29,30,32,35,37,38,40,41,43,46,48,51,54,56,59,61,63,66,67,69,70,72,77,82,85,86,87,88,90,94,97,100,102],[21,26,36,39,41,45,46,52,60,61,66,70,78,80,82,86,88,92],[14,20,92,96,97,98,100,102],[23,26,41,61,66,70,82,88,92,93],[46,64,66],[20,35,40,51,59,69,77,79,86,87],[0,10,11,93],[82],[16,31,39,47,55,65,68,74,79,82,83,86,87,88,89,90,91,92,94,96],[21,45,60,61,78,80,86,92,93,94],[0],[0,5,12,14,20,66,82,83],[0,10,14,17,23,32,40,48,56,92,94],[0,2,3,14,27,42,53,62,71,79,83,89,93,94,96,102],[24,25,46,66],[0],[18,19,33,34,49,50,57,58,76,81],[14,26,27,29,36,37,41,42,52,53,61,62,64,66,70,71,82,83,88,89,96],[14,16,17,18,19,20,21,32,33,34,41,48,49,50,56,57,58,60,66,73,75,76,78,79,80,81,83,86,92,93,94,95,96,97],[29],[0],[30,66,81,82,83],[61],[0,20],[0,3,14],[2,18,19,33,34,49,50,57,58,75,76,92],[21,45,46,60,67,78,91],[29,39,66],[14,17,20,26,32,35,40,41,44,48,51,56,59,61,69,70,73,77,87],[14,18,20,21,22,23,26,27,30,33,35,36,37,40,41,42,44,49,51,52,53,57,59,60,61,62,66,69,70,71,73,77,78,82,83,87,88,89,91,92,93,94,95],[14,22,24,28,29,38,43,54,63,67,72],[30,92,93],[2,16,40,45,46,65,66,67],[2,29,40,66,79,94,97],[14,23],[14,20,35,40,51,59,69,77,87],[66],[14,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,35,36,37,38,40,41,42,43,44,45,46,48,49,50,51,52,53,54,56,57,58,59,60,61,62,63,64,66,67,69,70,71,72,73,75,76,77,78,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,98,99,101,102],[14,20,21,22,30,35,40,51,59,60,64,69,72,77,78,87,91,95,96,97],[2,14,24,25,79,86,87,88,89,90,91],[14,79,86,87,88,89,90,91],[79,96],[20,26,35,56,57,58,60,61,69,70,71,72,73,75,76,78],[82],[79,80,86],[26,36,44,52,61,70,73,82,83,88],[0,6],[26,41,61,66,70,82,88],[92,94,96,97,100],[0,7],[21,27,28,37,38,42,43,46,53,54,62,63,66,67,71,72,89],[39],[25],[0,3,12],[2,23,26,27,36,37,42,52,53,61,62,66,70,71,82,83,88,89,93],[14,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,35,36,37,38,40,41,42,43,44,45,46,48,49,50,51,52,53,54,56,57,58,59,60,61,62,63,64,66,67,69,70,71,72,75,76,77,78,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96],[0,10,11,14,22],[20,26],[61],[68,69,70,71,72,73],[7,24,25,26,27,36,37,41,42,52,53,61,62,66,70,71,72,82,83,88,89],[0,9,10,11],[41],[0,4,20,22,23,26,27,35,40,41,42,51,53,59,61,62,64,66,69,70,71,77,82,87,88,89,92,93],[14,80,92,93,94,95,96,97],[79,97,98,100],[0,9],[0,23],[0,14,22,26],[16,20,21,22,24,25,27,28,30,31,36,37,38,42,43,46,47,52,53,54,55,60,62,63,64,65,66,67,68,71,72,74,78,79,81,83,86,89,92,94,95,97,100,102],[23,24,25],[0],[14,18,19,23,24,25,28,29,33,34,38,39,41,43,44,45,46,49,50,54,57,58,63,67,72,73,75,76,85,90],[7],[39,46,86],[19,34,50,58,61,76,82],[17,18,19,32,33,34,35,40,46,48,49,50,51,56,57,58,59,69,75,76,77,80,81,86,87,91,95],[20,35,61,92,94],[0,2,3,41,81,91,93,95],[21,60,78,81],[96,97,102],[82,83,94,96,100,102],[46],[29],[23,37,41,66,80,86],[0,3,12],[24,25,70],[27,28,30,38,42,43,53,54,62,63,64,67,71,72,89,91,95],[0],[0,2,3,14,17,18,19,24,25,32,33,34,48,49,50,56,57,58,75,76,80,81,82,83,86,92],[22,23,24,25,28,29,38,43,54,63,66,67,72],[17,23,26,32,35,40,41,48,51,56,59,61,66,69,70,77,82,87,88],[0,1],[22,23,24,25,26,27,28,29,36,37,38,41,42,43,52,53,54,61,62,63,64,66,67,70,71,72,82,83,88,89],[7],[19,20,25,27,34,35,40,42,50,51,53,58,59,62,69,71,76,77,87,89,93,95],[0,14,20,26,41,61,70,82,88,93,96,97,100,102],[17,32,35,40,48,51,56,59,64,69,77,81,82,87],[26,41,61,66,70,82,88],[96,97],[0,96],[27,42,53,62,71,83,89],[74,75,76,77,78],[22],[2],[46,94],[20,92,94],[0,3,17,18,20,23,24,26,27,28,32,33,35,36,37,38,40,41,42,43,44,49,51,52,53,54,57,59,61,62,63,66,69,70,71,72,73,75,77,82,83,84,85,87,88,89,90],[2],[95],[0,3,29,40,45,46,65,66,67],[14,17,26,27,30,32,39,42,45,46,48,53,56,61,62,66,70,71,83,89],[0,2,9,14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,36,37,39,40,45,48,49,50,52,54,55,56,57,58,60,62,66,67,75,76,78,93,96,97,100,102],[14,17,18,20,21,24,25,32,33,35,36,40,48,49,52,56,57,60,61,75,78,79,81],[0,2,20,26,41,61,66,70,82,88],[14,24,25,82,83],[25],[79,97,98,100],[0,3],[18,19,33,34,49,50,57,58,76,81],[79,86],[73],[22,23,27,28,36,38,42,43,52,53,54,61,62,63,66,67,71,72,73,89,95],[20,35,40,51,59,69,77,79,86,87],[0,8,25,81],[17,20,23,24,26,28,29,31,32,35,36,37,38,39,40,41,43,44,47,48,51,52,54,55,56,59,61,63,64,65,66,67,68,69,70,72,73,74,77,82,85,87,88,90],[14,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,35,36,37,38,40,41,42,43,44,48,49,50,51,52,53,54,56,57,58,59,60,61,62,63,64,66,67,69,70,71,72,73,75,76,77,78,80,81,82,83,87,88,89,91,93,95],[2],[66],[18,23,29,33,44,49,57,66,70,71,72,73,75,79,80,81,82,83,86,88,89,91],[65],[70],[7,14],[66,79,94,95],[7],[40,41,51,59,77],[2,14,23,25,28,29,30,38,41,43,54,63,67,72,96],[0,13,16,20,22,23,24,25,26,27,28,29,30,35,36,37,38,40,41,42,43,44,45,46,51,52,53,54,59,61,62,63,64,66,67,69,70,71,72,73,77,82,83,84,85,87,88,89,90,92,94],[0,8,25,81],[17,19,32,34,48,50,56,58,76],[0,27,37,42,53,62,71,83,86,89,92,94],[21,22,23,24,25,28,29,38,43,54,63,66,67,72],[2],[20,29,35,40,44,51,59,66,69,70,71,72,73,77,79,80,81,82,83,86,87,88,89,91],[0,23,24,25],[0,5],[20,22,35,40,51,59,69,77,81,87],[2,22,23,24,25,26,27,28,29,36,37,38,41,42,43,52,53,54,60,61,62,63,66,67,70,71,72,78,80,82,88,89,91],[4,5,11,72],[74,75,76,77,78],[0,19,20,22,25,27,30,34,35,40,42,46,50,51,53,58,59,62,66,69,71,72,76,77,83,87,89,95],[0,13],[2,16,17,18,21,22,23,24,26,28,29,30,31,32,33,35,36,37,38,39,40,41,43,44,46,47,48,49,51,52,54,55,56,57,59,60,61,63,64,65,66,67,68,69,70,72,73,74,77,78,79,80,82,85,86,87,88,90,92,94,97,100,102],[20,21,35,40,46,51,59,60,69,77,78,87],[0,3],[30],[0,2],[7,20,35,40,51,59,61,69,77,82,87],[0,3,17,18,21,24,25,27,32,33,42,48,49,53,56,57,60,62,71,75,78,81,83,89],[14],[0,2,24,25,29],[0,3],[20,24,25,31],[65,66],[0],[21,44,60,66,73,78,91,93],[66],[23,25,79,80,81,82,83,84,85,92,93],[0,14,17,31,32,48,56,64,81],[23,25],[30,66],[0,14],[36,52,66],[17,18,20,21,32,33,35,40,44,48,49,51,56,57,59,60,69,73,75,77,78,81,84,86,87,91,92,94,95,96,97,99,100,101,102],[0,81,82,83],[14,31,47,55,64,65,68,74,81,91,93,95],[17,18,19,21,32,33,34,36,40,46,48,49,50,52,56,57,58,60,61,64,66,75,76,78,80,81,82,83,86],[23,44,73],[23,24],[20,21,22,24,25,27,28,35,37,38,40,42,43,46,51,53,54,59,60,62,63,66,67,69,71,72,77,78,87,89],[0],[0,3,12],[21,23,25,44,60,73,78,79,80,81,82,83,84,85,92,93,96],[46],[0,14,25],[18,24,25,33,46,49,57,75,92,94],[29],[41],[0],[72],[66],[0,79,80,81,82,83,84,85,86,87,92,93,94,95,96,97,99,101],[14],[24,25,46,66],[92,94],[26,36,39,41,45,46,52,61,66,70,82,86,88,92],[28,38,43,54,63,72,82,83,85,90],[20,47,48,49,50,51,52,53,54,64],[21,60,78],[14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,86,87,88,89,91,92,93,94,95,96],[44,73,91,95],[20,66,80,82,83],[2],[0],[7],[46,94],[2],[0,6],[27,42,53,62,71,83,89,94,97],[0,15,16,18,31,33,39,41,45,46,47,55,65,66,68,74,79,80,82,83,86,92,94,96,97,100],[0,2,14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,36,37,39,40,45,48,49,50,52,54,55,56,57,58,60,62,66,67,75,76,78,80,81,87,93,96,97,100,102],[92,94],[14,39],[0,14,20,26,93,96,97,100,102],[82],[0,2,14,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,85,86,87,88,89,90,91,92,93,94,95,96,97,100,102],[79,94,95],[7,20,35,40,51,59,69,77,87],[0,2],[19,34,50,58,76],[23,64,91],[17,18,20,32,33,48,49,56,57,75,81,84,92,94,95,96,97,99,100,101,102],[20],[0,12,14,22,26,35,51,59,61,77],[79,80,86],[0,7],[14,22,25,26,27,28,29,38,41,42,43,53,54,61,62,63,66,67,70,71,72,82,88,89],[14,20,21,28,29,30,35,38,40,41,43,46,51,54,59,60,63,66,67,69,72,77,78,81,87,91],[30],[82,83,87],[17,32,35,40,41,44,48,51,56,59,61,69,70,73,77,87],[0,3,24,25,79,80,81,82,83,84,85,86,87,92,93,94,95,96,97,99,101],[26,41,61,70,82,83],[14,26,27,29,36,37,41,42,52,53,61,62,64,66,70,71,82,83,88,89,96],[20],[0,9,81],[23,91],[19,20,34,35,40,50,51,58,59,69,76,77,87],[16,20,35,40,51,59,66,69,77,86,87,92,94],[0,15,16,18,31,33,39,41,45,46,47,55,65,66,68,74,79,80,82,83,86,92,94,96],[40],[20,26,35,56,57,58,60,61,69,70,71,72,73,75,76,78,79,80,81,82,83,84,85,87],[21,44,60,73,78],[0,2,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,36,37,38,41,42,43,46,47,48,49,50,52,53,54,55,56,57,58,60,61,62,63,64,65,66,67,68,70,71,72,74,75,76,78,82,83,85,88,89,90,91],[0,17,19,32,34,35,36,48,50,52,56,58,76],[17,18,19,21,26,29,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,51,59,60,66,67,77,78],[84],[17,18,19,21,26,29,31,32,33,34,35,36,37,38,40,46,51,59,77],[92,94],[96,100,102],[17,30,32,48,56,64,81],[92,94],[7],[0,2,13,14,15,16,18,19,20,21,22,23,24,25,26,27,28,29,30,31,33,34,36,37,38,41,42,43,46,47,49,50,52,53,54,55,57,58,60,61,62,63,64,65,66,67,68,70,71,72,74,75,76,78,82,83,85,88,89,90,91,96],[79,86],[0,2,12,35,37,51,59,61,77,81],[7,14],[0,7,22,82,83,92,94],[39,40,41,42,43,44,45,46,66,67],[0,5,12],[3,39],[20],[0,3],[0,2,13,16,17,20,22,24,25,26,28,29,30,32,35,37,38,40,41,43,48,51,54,56,59,61,63,66,67,69,70,72,77,82,85,86,87,88,90,94,97,100,102],[23,96,97,102],[7],[20,55,56,57,58,59,60,61,62,63,64,65,66,67],[17,18,19,20,21,32,33,34,36,48,49,50,52,56,57,58,60,61,64,66,75,76,78,80,81,83,86,87,92,94],[14,72],[7],[66],[7],[16,31,39,47,55,65,68,74,79,82,83,92,94,96],[0,6,16,19,20,21,22,24,25,26,27,29,30,31,34,35,36,37,40,42,46,47,50,51,52,53,55,58,59,60,61,62,65,66,68,69,70,71,74,76,77,78,80,82,83,86,87,88,89,92,94,97,100,102],[20,21,22,23,24,25,26,27,28,29,35,36,37,38,40,41,42,43,44,46,51,52,53,54,59,60,61,62,63,66,67,69,70,71,72,73,77,78,79,82,83,86,87,88,89,90,91],[22,25],[0,9,10,11],[2,18,19,22,23,25,27,28,30,33,34,36,37,38,42,43,44,49,50,52,53,54,57,58,61,62,63,64,66,67,71,72,73,76,83,89,92,93,95,97],[14],[0,12],[14],[20,30,55,56,57,58,59,60,61,62,63,64,65,66,67],[20,23,25,30],[0,1],[22,28,29,38,43,54,63,67,72],[22],[0,7,22,92,94],[14],[0],[24,25],[20,21,24,35,40,44,51,59,60,69,73,77,78,86,87,91],[17,21,23,24,25,32,37,46,48,56,60,72,78,82,93],[84],[14,27,42,53,62,71,83,89],[0,13],[14,66],[24,25,81],[0],[12],[0,5,20,47,48,49,50,51,52,53,54,64],[17,19,32,34,48,50,56,58,76,79,93,94,96,102],[0],[21,60,78],[14,20,35,40,51,59,69,77,87],[97,99,101],[0,2,8,9],[24,25],[37,81],[14,20,21,35,40,41,51,59,60,69,77,78,87,91],[21,60,78],[16,17,20,21,26,31,32,35,39,40,41,47,48,51,55,56,59,60,61,65,66,68,69,70,74,77,78,82,87,88,93],[0,6,80,86,94],[14],[72,96],[96,97],[14,16,18,19,20,21,22,24,25,26,27,29,30,31,33,34,35,36,37,39,40,42,46,47,49,50,51,52,53,55,57,58,59,60,61,62,65,66,68,69,70,71,74,76,77,78,79,80,82,83,86,87,88,89,92,94,95,96,97,100,102],[7,14],[40,41,51,59,77],[97,99,101],[87],[21,22,23,27,28,36,37,38,42,43,46,52,53,54,60,61,62,63,64,66,67,71,72,73,78,89,93,95],[80],[16,17,20,21,26,31,32,35,39,40,41,47,48,51,55,56,59,60,61,65,66,68,69,70,74,77,78,82,87,88,93],[40],[0,18,20,26,33,39,40,46,49,57,66,75,79],[3],[0,7,20,64,66],[39,96,97],[22,82,83],[12],[0,3,12],[36,52],[14,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,33,34,35,36,37,38,40,41,42,43,44,48,49,50,51,52,53,54,56,57,58,59,60,61,62,63,64,66,67,69,70,71,72,73,75,76,77,78,81,82,83,87,88,89,91,93,95],[20],[14],[14,20,35,40,51,59,69,77,81,82,83,87],[20,26,39,40,46,66],[23,26,27,36,37,41,42,52,53,61,62,66,70,71,82,83,88,89],[24],[0],[14,80],[0,3,66],[28,38,43,45,46,54,63,66,67,72,85,86,90,92,94],[29,45,46,67],[18,19,22,25,27,28,30,33,34,37,38,42,43,44,49,50,53,54,57,58,62,63,64,66,67,71,72,73,76,79,83,89,92,93,95,97],[0,14,16,17,18,28,30,31,32,33,37,38,39,43,47,48,49,54,55,56,57,63,65,66,67,68,72,74,80,86,92,94,95],[14,16,18,20,30,31,33,35,40,47,49,51,55,57,59,65,66,68,69,74,77,80,86,87,92,94,95],[79],[16,82,83],[14],[2],[81,82,83],[7,14,66,72,81]];
skArr[2]  = ["01","04","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","cd"]; 
sfi[2] = [[0],[0],[0],[0,7,24,26],[8,25,27],[26,28],[27,29],[28,30],[29],[30],[32],[33],[34],[7,35],[36],[37],[38],[40],[40],[41],[0,42],[43],[44],[45],[0,45],[0,46],[0,48],[49],[50],[51],[52],[53],[54],[7,56],[57],[58],[59],[0,60],[60],[61],[62],[0,63],[64],[66],[66],[66],[66],[67],[67],[69],[70],[70],[71],[71],[72],[72],[73],[75,76],[77],[78],[78],[80],[81,82,83],[82],[83],[84],[85],[86],[87],[88],[89],[90],[91],[92],[93],[94],[95],[96],[98],[99],[101],[102],[0]];
skArr[3]  = ["394","cpu","erp","ghz","mhz","tab"]; 
sfi[3] = [[0],[7],[40],[7],[7],[40,66]];
skArr[4]  = ["1066","2008","2014","2015","nbsp","yhsc"]; 
sfi[4] = [[7],[8],[0],[0],[0,88,102],[0]];
skArr[5]  = ["123456","erpid","excel","explorer","gskfjg","internet","oracle","tomcat","windowsserver"]; 
sfi[5] = [[22],[20],[23,24,25],[14],[0],[14],[8],[8],[8]];
