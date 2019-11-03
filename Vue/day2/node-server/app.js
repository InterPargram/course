//导入 http 内置模块
const http = require('http')

//创建一个 http 服务器
const server = http.createServer()
//监听 http 服务器的 request 请求
server.on('request', function(req, res) {
	const url = req.url
	if (url === '/getscript') {
		//拼接一个合法的JS脚本
		var scriptStr = 'show()'
		//客户端拿到之后将 字符串当做 JS脚本来执行
		res.end(scriptStr)
	} else {
		res.end('404')
	}
})

//启动指定端口并监听
server.listen(3000, function() {
	console.log("server listen at http://127.0.0.1:3000")
})
