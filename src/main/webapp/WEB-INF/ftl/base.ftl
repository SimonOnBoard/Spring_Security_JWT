<#macro content></#macro>
<#macro title></#macro>
<#macro head></#macro>
<#macro display_page>
    <html>
    <head>
        <base href="/">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <meta charset="utf-8">
        <@title />
        <@head />

    </head>

    <body>
    <@content />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    </body>
    </html>
</#macro>