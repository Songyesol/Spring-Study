<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);
      //google.charts.setOnLoadCallback(monthSaleChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', '일별');
        data.addColumn('number', '판매금액');
        var arr = [];
        //ajax 
        $.ajax({
        	url: "getDailySaleList",
        	dataType: "json", 
        	async:false, 
        		// 동기식 (비동기식으로 하면 .addRows가 데이터를 받아오지않아도 .addRow를 실행하기 때문이다.)
        	success : function(result){
        		for(value of result){ 
        		//서버에서 받아온 data구조는 [{},{}]로 되어있기때문에 for문을 돌려서 [[],[]] 구조로 바꾸기위함.
        		console.log(value.sum_price);
        			arr.push([value.sale_date, value.sum_price ]);
        		}
        	}
        })
 		data.addRows(arr);

        // Set chart options
        var options = {'title':'일별 판매액',
                       'width':400,
                       'height':300,
                       colors: ['#e0440e', '#e6693e', '#ec8f6e', '#f3b49f', '#f6c7b6'],
                       is3D: true,
                       hAxis: { format:'$#,###' , gridlines:{count:10}} 
        };

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
      
      function monthSaleChart() {

          // Create the data table.
          var data = new google.visualization.DataTable();
          data.addColumn('string', '월별');
          data.addColumn('number', '판매금액');
          var arr = [];
          //ajax 
          $.ajax({
          	url: "getMonthSaleList",
          	dataType: "json", 
          	async:false, 
          		// 동기식 (비동기식으로 하면 .addRows가 데이터를 받아오지않아도 .addRow를 실행하기 때문이다.)
          	success : function(result){
          		for(value of result){ 
          		//서버에서 받아온 data구조는 [{},{}]로 되어있기때문에 for문을 돌려서 [[],[]] 구조로 바꾸기위함.
          		console.log(value.SALESDATE);
          			arr.push([value.SALESDATE, parseInt(value.SALE_PRICE)]);
          		}
          	}
          })
   		data.addRows(arr);

          // Set chart options
          var options = {'title':'월별 판매액',
                         'width':400,
                         'height':300};

          // Instantiate and draw our chart, passing in some options.
          var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
          chart.draw(data, options);
        }
    </script>
  </head>

  <body>
    <!--Div that will hold the pie chart-->
    <div id="chart_div"></div>
  </body>
</html>