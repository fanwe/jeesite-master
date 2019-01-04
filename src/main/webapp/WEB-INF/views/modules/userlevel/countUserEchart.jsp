<%@ page contentType="text/html;charset=UTF-8"%>


<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="height: 100%">
<head>
<meta charset="utf-8">
<script src="https://cdn.staticfile.org/jquery/2.0.3/jquery.min.js"></script>
</head>
<body style="height: 100%; margin: 0">
	<div id="container" style="height: 100%"></div>
	<script type="text/javascript"
		src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
	<script type="text/javascript"
		src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
	<script type="text/javascript"
		src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
	<script type="text/javascript"
		src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
	<script type="text/javascript"
		src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
	<script type="text/javascript"
		src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
	<script type="text/javascript"
		src="https://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
	<script type="text/javascript"
		src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
	<script type="text/javascript"
		src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
	<script type="text/javascript">
	
		//var dom = document.getElementById("container");
		var myChart = echarts.init(document.getElementById('container'));
		// 显示标题，图例和空的坐标轴
		myChart.setOption({
		    title: {
		        text: '异步数据加载示例'
		    },
		    tooltip: {},
		    legend: {
		        data:['销量']
		    },
		    xAxis: {
		        data: []
		    },
		    yAxis: {},
		    series: [{
		        name: '销量',
		        type: 'bar',
		        data: []
		    }]
		});

		// 异步加载数据
		$.get('${ctx}/userlevel/shopUserlevel/userEchartjsondata').done(function (data) {
			var obj = eval('(' + data + ')');
			console.log(obj.data);
			
		    // 填入数据
		    myChart.setOption({
		        xAxis: {
		            data: obj.categories
		        },
		        series: [{
		            // 根据名字对应到相应的系列
		            name: '销量',
		            data: obj.data
		        }]
		    });
		});
// 		$.ajax({
// 			url:"${ctx}/userlevel/shopUserlevel/userEchartjsondata",
// 			type:"GET",
// 			success:function(result){
// 				alert(result);
// 				alert("aaaaa");
// 				console.log(result);
// 			}
// 		});
     </script>
</body>
