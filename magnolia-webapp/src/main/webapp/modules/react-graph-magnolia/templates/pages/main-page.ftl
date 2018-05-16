<!DOCTYPE html>
<html xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}">
  <head>
    [@cms.page /]
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>${content.windowTitle!content.title!}</title>
    <meta name="description" content="${content.description!""}" />
    <meta name="keywords" content="${content.keywords!""}" />
    <script src="${ctx.contextPath}/.resources/react-graph-magnolia/webresources/js/js.cookie.js"></script>
    <script src="${ctx.contextPath}/.resources/react-graph-magnolia/webresources/js/side.force.cookie.js"></script>
	<script src="${ctx.contextPath}/.resources/react-graph-magnolia/webresources/js/Chart.bundle.js"></script>
	<script src="${ctx.contextPath}/.resources/react-graph-magnolia/webresources/js/utils.js"></script>
	${resfn.css(["/react-graph-magnolia/webresources/css/bootstrap-list.css"])!}
	${resfn.css(["/react-graph-magnolia/webresources/css/skeleton.css"])!}
	${resfn.css(["/react-graph-magnolia/webresources/css/bootstrap.min.css"])!}
	${resfn.js(["/react-graph-magnolia/.*js"])!}
  </head>
  <body class="my-page ${cmsfn.language()}" onload="setSideForceCookie()">

    <div class="container-fluid">
     
	    <div class="row">
		  <div class="col-md-12 div-header">
			<center>
				[#assign headerImageName = content.headerImgName!'header_stormtrooper']
				[#assign urlCabecera = ctx.contextPath + "/.resources/react-graph-magnolia/webresources/img/" + headerImageName + ".jpg"]
			  <img src="${urlCabecera!}" alt="header_techday" class="header_techday img-responsive">
			  </center>
			  </div>
			</div>
			<div class="row">
			  <div class="col-md-12">


			  </div>
        </div>
		<div class="row">
			[@cms.area name="main"/]
		</div>
    </div>
  </body>
  
  <footer>
	<div>
	  [#assign footerImageName = content.footerImgName!'footer_stormtrooper']
	  [#assign urlFooter = ctx.contextPath + "/.resources/react-graph-magnolia/webresources/img/" + footerImageName + ".jpg"]
	  <img src="${urlFooter!}" alt="footer_techday" class="img-responsive footer-img">
	</div>
  </footer>
</html>