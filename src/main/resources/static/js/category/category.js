function loadlistCategory(){
	
	var tableData = "";
			 $.ajax({  
			 	 type: "GET",   
			     url : "/admin/category/list",
			     success : function(response) {
			    console.log(response);
			    
			    response.forEach(function(item){
			    	tableData += '<tr>' +
		      	  		'<td id = "name'+ item.id +'">' + item.name + '</td>' +
		      	  	    '<td>' +
		      	  	    '<button type = "button" id = "editButton'+item.id+'" class = "btn btn-success btn-md editButton" onclick = "editUser('+item.id+')">Edit</button>&nbsp;'+
		       	  	   '<button type = "button" id = "updateButton'+item.id+'" style = "display:none;" class = "btn btn-success btn-md updateButton" onclick = "updateUser('+item.id+')">Save</button>'+

		      	  	    '<button type = "button" id = "deleteButton "class = "btn btn-danger btn-md" onclick = "deleteUser('+item.id+')">Delete</button>'+
		      	  	    '</td>'+
								
		      	  		'</tr>';
		      	  		i++;
			    	
			    	
		        });
		      $("#fetchData").html(tableData);		    	 
			     },
			     error: function (response){
		         console.log(response);
			      }
		   });
}