var type = "全部";
var pageSize = 8;
var pageNumber = 0;
var options={
		"id":"page",//显示页码的元素
		"data":null,//显示数据
	    "maxshowpageitem":5,//最多显示的页码个数
	    "pagelistcount":8,//每页显示数据个数
	    "callBack":function(){}
};
$(function(){
	$('#keyword_info').addClass("active");
	var param ={type:type,pageSize:pageSize,pageNumber:pageNumber};
	getType(param);
	$('#addKeyword').after('<input type="file" id="load_xls" name="file" style="display:none" onchange ="uploadFile()">');
	$('#addKeyword').on('click',function(){
		$('#load_xls').click();
	});
});
function uploadFile(){
	 var myform = new FormData();
     myform.append('file',$('#load_xls')[0].files[0]);
     $.ajax({
    	 url: "/apis/keyInfo/ExcelDataUpload.json",
         type: "POST",
         data: myform,
         contentType: false,
         processData: false,
         success:function(res){
        	 if(res.data != null){
        		 layer.msg(res.data, {icon: 1});
        	 }else{
        		 layer.msg(res.message, {icon: 2});
        	 }
        	 var param ={type:type,pageSize:pageSize,pageNumber:pageNumber};
			  getType(param);
         }
     });
};
$(".search-box").on("click",".search-item-content>a",function(){
	$(this).addClass("active").siblings().removeClass("active");
	var _id = $(this).attr("id");
	type=_id;
	var param ={type:type,pageSize:pageSize,pageNumber:pageNumber};
	getType(param);
});

function getType(e){
	
	$.ajax({
		type:'POST',
		url:'/apis/keyInfo/findKeyWordInfo.json',
		asynyc:false,
		contentType:'application/json',
		data:JSON.stringify(e),
		success:function(res){
			if(res.message != null){
				$('#manage_keyword').html('<div class="not-data"><img src="/images/notData.png" /><p class="tips-text">暂无数据</p></div>');
			}else{
				$('#manage_keyword').html(ShowInfo(res.data.dataList));
				if(res.data.totalPage>1){
					page.init(res.data.totalNumber,res.data.pageNumber,options);
					$("#"+page.pageId +">li[class='pageItem']").on("click",function(){
	            		var param={type:type,pageNumber:$(this).attr("page-data")-1,pageSize:pageSize};
	            		getType(param);
	                });
				}else{
					$('#page').html("");
				}
			}
			initPage();
		}
	});
};
function initPage(){
	$('#addKeywordToTable').on('click',function(){
		window.location.href="/apis/keyInfo/addThesaurus.html";
	});
	$('.editinfo').on('click',function(){
		 var _id =  $(this).parents('.gradeX').find('td').eq(0).text();
		window.location.href="/apis/keyInfo/ThesaurusRelatedManage.html?id="+_id;
	});
	$('.btn-default').on('click',function(){
		 var _id =  $(this).parents('.gradeX').find('td').eq(0).text();
		 $.ajax({
			 	type:'GET',
				url:'/apis/keyInfo/findRelatedInfoById.json?id='+_id,
				asynyc:false,
				success:function(res){
					if(res.message!=null){
						layer.msg(res.message, {icon: 2});
					}else{
						if(res.data.result==true){
							$('#demo-form').html(ShowRelatedInfo(res.data));
							$('#myModal').modal("show");
						}
					}
				}
		 });});
	$('.removeinfo').on('click',function(){
		var _id =  $(this).parents('.gradeX').find('td').eq(0).text();
		$.ajax({
			type:'GET',
			url:'/apis/keyInfo/deleteRelatedInfoById.json?id='+_id,
			asynyc:false,
			success:function(res){
				if(res.message!=null){
					  layer.msg(res.message, {icon: 2});
				}else{
					  layer.msg(res.data, {icon: 1});
					  var param ={type:type,pageSize:pageSize,pageNumber:pageNumber};
					  getType(param);
				}
			}
		});
	});
	
};
function ShowInfo(e){
	var arr =e;
	var html='';
	for(var i =0;i<	arr.length;i++){
		console.log(arr);
		html += '<tr class="gradeX"><input type="hidden" class="form-control input-block" value="'+arr[i].entntity.id+'"/><td>';
		html += arr[i].entntity.keyword+ '</td><td>';
		html +=  arr[i].entntity.type + '</td><td>';
		html +=  arr[i].entntity.describe + '</td><td>';
		if(arr[i].info.length==0){
			html += '无</td>';
		}else{
			var a = arr[i].info;
			var hh='<div>';
			for(var j =0;j<a.length;j++){
				console.log(a[j]);
				hh += '<p><span>'+a[j].attributeName+'</span><input type="text" name="name"  placeholder="'+a[j].attributeValue+'" value="'+a[j].attributeValue+'" required/></p>'
			}
			 
			html += hh+'</div></td>';
		}
		html += '<td class="actions">';
		html +='<a href="javascript:void(0);" class="on-default editinfo"><i class="fa fa-pencil"></i></a>';
		html +='<a href="javascript:void(0);" class="on-default removeinfo modal-basic"><i class="fa fa-trash-o"></i></a>';
		html +='<a class="bk-margin-top-10 bk-margin-bottom-10 modal-with-form btn btn-default" href="#modalForm">查看</a>';	            
		html +='</td></tr>';
	}
	return html;
	/*var arr=[];
	$.each(e,function(index,item){
		arr.push(
				'<tr class="gradeX"><input type="hidden" class="form-control input-block" value="'+item.id+'"/><td>' 
				+item.id+ '</td><td>'
				+item.keyword+ '</td><td>'
				+item.type + '</td><td>'
				+item.describe+ '</td>'
				+ '<td class="actions">'
	            +'<a href="javascript:void(0);" class="on-default editinfo"><i class="fa fa-pencil"></i></a>'
	            +'<a href="javascript:void(0);" class="on-default removeinfo modal-basic"><i class="fa fa-trash-o"></i></a>'
	            +'<a class="bk-margin-top-10 bk-margin-bottom-10 modal-with-form btn btn-default" href="#modalForm">查看</a>'	            
	            +'</td></tr>'
		);
		
	});
	var inner = arr.join('');
	return inner;*/
};
function ShowRelatedInfo(e){
	var arr = [];
	$.each(e,function(index,item){
		if(index=="keyword"){
			arr.push(
					'<div class="form-group mt-lg"><label class="col-sm-3 control-label">关键词</label>'
					+'<div class="col-sm-9">'
					+'<input type="text" name="name" class="form-control" placeholder="'+item+'" value="'+item+'" required/>'
					+'</div></div>'		
			);
		}else if(index=="type"){
			arr.push(
					'<div class="form-group"><label class="col-sm-3 control-label">词性</label>'
					+'<div class="col-sm-9">'
					+'<input type="text" name="name" class="form-control" placeholder="'+item+'" value="'+item+'" required/>'
					+'</div></div>'		
			);
		}else if(index=="relate"){
			if(item==false){
			}else{
				for(var i =0;i<item.length;i++){
					arr.push(
						'<div class="form-group"><label class="col-sm-3 control-label">关联词</label>'
						+'<div class="col-sm-9">'
						+'<input type="text" name="name" class="form-control" placeholder="'+item[i].rkeyword+'" value="'+item[i].rkeyword+'" required/>'
						+'</div></div>'	
						+'<div class="form-group"><label class="col-sm-3 control-label">关联关系</label>'
						+'<div class="col-sm-9">'
						+'<input type="text" name="name" class="form-control" placeholder="'+item[i].related+'" value="'+item[i].related+'" required/>'
						+'</div></div>'	
						+'<div class="form-group"><label class="col-sm-3 control-label">关联词词性</label>'
						+'<div class="col-sm-9">'
						+'<input type="text" name="name" class="form-control" placeholder="'+item[i].rtype+'" value="'+item[i].rtype+'" required/>'
						+'</div></div>'	
					);
				}
			}
		}else if(index=="desc"){
			arr.push(
					'<div class="form-group"><label class="col-sm-3 control-label">关键词描述</label>'
					+'<div class="col-sm-9">'
					+'<input type="text" name="name" class="form-control" placeholder="'+item+'" value="'+item+'" required/>'
					+'</div></div>'		
			);
		}
	});
	var inner = arr.join('');
	return inner;
};