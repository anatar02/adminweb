$(document).ready(function() {
	
	alert(1);
	$.getJSON("/api/ticket-status-chart", function (json) { // callback function which gets called when your request completes. 
        Morris.Donut({
            element: 'ticket-start-area-chart',
            data: json // use returned data to plot the graph
        });
    });
});