<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="UTF-8">
    <title>慧数招商-智能推荐</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="慧数招商平台，是一个关于园区产业招商的大数据管理平台">
    <meta name="keywords" content="慧数，招商，慧数招商，招商平台，园区，园区招商，园区招商平台，科技园，产业园，大数据，产业">
    <meta name="author" content="张鑫，慧数科技，中科点击">
    <meta name="application-name" content="慧数招商">
    <#include "/common/link.ftl"/>
    <!-- js 兼容低版本IE start -->
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- js 兼容低版本IE end -->
</head>
<body class="bg2">
<#include "/common/header2.ftl"/>
<div class="wrapper">
    <div class="page-content">
       <#include "/common/searchSidebar.ftl"/>
         <div class="right-content">
            <div class="charts" id="charts"></div>
            <div class="layer-person" id="layer-person-info">
             </div>
         </div>
    </div>
</div>
<#include "/common/footer.ftl"/>
<#include "/common/script.ftl"/>
<script src="/js/intelligentRecommendation/intelligentRecommendation.js"></script>
</body>
</html>