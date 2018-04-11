# SteamSalesWalker
A Steam game market sales information walker.一个Steam的促销商品信息的爬虫工具
该项目采用了JSoup和HtmlUnit作为网页的爬虫工具。本来打算在安卓端写的，但是HtmlUnit不支持安卓端。单纯使用JSOUP,遇到js加载的网页会有局限。所以才写了这个工具。
爬虫的地址是steam db.自己写了一套注解的数据库框架，数据库采用mysql.目前的bug就是，如果网站出现夏季，春季大促销，导致商品数量激增的话(可能有上万条)，会报内存溢出。
本人能力有限，尚不知如何解决。
